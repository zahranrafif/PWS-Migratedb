/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package migratedb.senamjantung;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import migratedb.senamjantung.exceptions.IllegalOrphanException;
import migratedb.senamjantung.exceptions.NonexistentEntityException;
import migratedb.senamjantung.exceptions.PreexistingEntityException;

/**
 *
 * @author Zahran Rafif Pc
 */
public class PemilikJpaController implements Serializable {

    public PemilikJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("migratedb_senamjantung_jar_0.0.1-SNAPSHOTPU");

    public PemilikJpaController() {
    }

    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pemilik pemilik) throws PreexistingEntityException, Exception {
        if (pemilik.getKucingCollection() == null) {
            pemilik.setKucingCollection(new ArrayList<Kucing>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Kucing> attachedKucingCollection = new ArrayList<Kucing>();
            for (Kucing kucingCollectionKucingToAttach : pemilik.getKucingCollection()) {
                kucingCollectionKucingToAttach = em.getReference(kucingCollectionKucingToAttach.getClass(), kucingCollectionKucingToAttach.getIdKucing());
                attachedKucingCollection.add(kucingCollectionKucingToAttach);
            }
            pemilik.setKucingCollection(attachedKucingCollection);
            em.persist(pemilik);
            for (Kucing kucingCollectionKucing : pemilik.getKucingCollection()) {
                Pemilik oldIdPemilikOfKucingCollectionKucing = kucingCollectionKucing.getIdPemilik();
                kucingCollectionKucing.setIdPemilik(pemilik);
                kucingCollectionKucing = em.merge(kucingCollectionKucing);
                if (oldIdPemilikOfKucingCollectionKucing != null) {
                    oldIdPemilikOfKucingCollectionKucing.getKucingCollection().remove(kucingCollectionKucing);
                    oldIdPemilikOfKucingCollectionKucing = em.merge(oldIdPemilikOfKucingCollectionKucing);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPemilik(pemilik.getIdPemilik()) != null) {
                throw new PreexistingEntityException("Pemilik " + pemilik + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pemilik pemilik) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pemilik persistentPemilik = em.find(Pemilik.class, pemilik.getIdPemilik());
            Collection<Kucing> kucingCollectionOld = persistentPemilik.getKucingCollection();
            Collection<Kucing> kucingCollectionNew = pemilik.getKucingCollection();
            List<String> illegalOrphanMessages = null;
            for (Kucing kucingCollectionOldKucing : kucingCollectionOld) {
                if (!kucingCollectionNew.contains(kucingCollectionOldKucing)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Kucing " + kucingCollectionOldKucing + " since its idPemilik field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Kucing> attachedKucingCollectionNew = new ArrayList<Kucing>();
            for (Kucing kucingCollectionNewKucingToAttach : kucingCollectionNew) {
                kucingCollectionNewKucingToAttach = em.getReference(kucingCollectionNewKucingToAttach.getClass(), kucingCollectionNewKucingToAttach.getIdKucing());
                attachedKucingCollectionNew.add(kucingCollectionNewKucingToAttach);
            }
            kucingCollectionNew = attachedKucingCollectionNew;
            pemilik.setKucingCollection(kucingCollectionNew);
            pemilik = em.merge(pemilik);
            for (Kucing kucingCollectionNewKucing : kucingCollectionNew) {
                if (!kucingCollectionOld.contains(kucingCollectionNewKucing)) {
                    Pemilik oldIdPemilikOfKucingCollectionNewKucing = kucingCollectionNewKucing.getIdPemilik();
                    kucingCollectionNewKucing.setIdPemilik(pemilik);
                    kucingCollectionNewKucing = em.merge(kucingCollectionNewKucing);
                    if (oldIdPemilikOfKucingCollectionNewKucing != null && !oldIdPemilikOfKucingCollectionNewKucing.equals(pemilik)) {
                        oldIdPemilikOfKucingCollectionNewKucing.getKucingCollection().remove(kucingCollectionNewKucing);
                        oldIdPemilikOfKucingCollectionNewKucing = em.merge(oldIdPemilikOfKucingCollectionNewKucing);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pemilik.getIdPemilik();
                if (findPemilik(id) == null) {
                    throw new NonexistentEntityException("The pemilik with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pemilik pemilik;
            try {
                pemilik = em.getReference(Pemilik.class, id);
                pemilik.getIdPemilik();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pemilik with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Kucing> kucingCollectionOrphanCheck = pemilik.getKucingCollection();
            for (Kucing kucingCollectionOrphanCheckKucing : kucingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pemilik (" + pemilik + ") cannot be destroyed since the Kucing " + kucingCollectionOrphanCheckKucing + " in its kucingCollection field has a non-nullable idPemilik field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pemilik);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pemilik> findPemilikEntities() {
        return findPemilikEntities(true, -1, -1);
    }

    public List<Pemilik> findPemilikEntities(int maxResults, int firstResult) {
        return findPemilikEntities(false, maxResults, firstResult);
    }

    private List<Pemilik> findPemilikEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pemilik.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pemilik findPemilik(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pemilik.class, id);
        } finally {
            em.close();
        }
    }

    public int getPemilikCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pemilik> rt = cq.from(Pemilik.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
