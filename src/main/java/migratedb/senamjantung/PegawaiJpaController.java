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
import migratedb.senamjantung.exceptions.IllegalOrphanException;
import migratedb.senamjantung.exceptions.NonexistentEntityException;
import migratedb.senamjantung.exceptions.PreexistingEntityException;

/**
 *
 * @author Zahran Rafif Pc
 */
public class PegawaiJpaController implements Serializable {

    public PegawaiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pegawai pegawai) throws PreexistingEntityException, Exception {
        if (pegawai.getKucingCollection() == null) {
            pegawai.setKucingCollection(new ArrayList<Kucing>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Kucing> attachedKucingCollection = new ArrayList<Kucing>();
            for (Kucing kucingCollectionKucingToAttach : pegawai.getKucingCollection()) {
                kucingCollectionKucingToAttach = em.getReference(kucingCollectionKucingToAttach.getClass(), kucingCollectionKucingToAttach.getIdKucing());
                attachedKucingCollection.add(kucingCollectionKucingToAttach);
            }
            pegawai.setKucingCollection(attachedKucingCollection);
            em.persist(pegawai);
            for (Kucing kucingCollectionKucing : pegawai.getKucingCollection()) {
                Pegawai oldIdPegawaiOfKucingCollectionKucing = kucingCollectionKucing.getIdPegawai();
                kucingCollectionKucing.setIdPegawai(pegawai);
                kucingCollectionKucing = em.merge(kucingCollectionKucing);
                if (oldIdPegawaiOfKucingCollectionKucing != null) {
                    oldIdPegawaiOfKucingCollectionKucing.getKucingCollection().remove(kucingCollectionKucing);
                    oldIdPegawaiOfKucingCollectionKucing = em.merge(oldIdPegawaiOfKucingCollectionKucing);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPegawai(pegawai.getIdPegawai()) != null) {
                throw new PreexistingEntityException("Pegawai " + pegawai + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pegawai pegawai) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pegawai persistentPegawai = em.find(Pegawai.class, pegawai.getIdPegawai());
            Collection<Kucing> kucingCollectionOld = persistentPegawai.getKucingCollection();
            Collection<Kucing> kucingCollectionNew = pegawai.getKucingCollection();
            List<String> illegalOrphanMessages = null;
            for (Kucing kucingCollectionOldKucing : kucingCollectionOld) {
                if (!kucingCollectionNew.contains(kucingCollectionOldKucing)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Kucing " + kucingCollectionOldKucing + " since its idPegawai field is not nullable.");
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
            pegawai.setKucingCollection(kucingCollectionNew);
            pegawai = em.merge(pegawai);
            for (Kucing kucingCollectionNewKucing : kucingCollectionNew) {
                if (!kucingCollectionOld.contains(kucingCollectionNewKucing)) {
                    Pegawai oldIdPegawaiOfKucingCollectionNewKucing = kucingCollectionNewKucing.getIdPegawai();
                    kucingCollectionNewKucing.setIdPegawai(pegawai);
                    kucingCollectionNewKucing = em.merge(kucingCollectionNewKucing);
                    if (oldIdPegawaiOfKucingCollectionNewKucing != null && !oldIdPegawaiOfKucingCollectionNewKucing.equals(pegawai)) {
                        oldIdPegawaiOfKucingCollectionNewKucing.getKucingCollection().remove(kucingCollectionNewKucing);
                        oldIdPegawaiOfKucingCollectionNewKucing = em.merge(oldIdPegawaiOfKucingCollectionNewKucing);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pegawai.getIdPegawai();
                if (findPegawai(id) == null) {
                    throw new NonexistentEntityException("The pegawai with id " + id + " no longer exists.");
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
            Pegawai pegawai;
            try {
                pegawai = em.getReference(Pegawai.class, id);
                pegawai.getIdPegawai();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pegawai with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Kucing> kucingCollectionOrphanCheck = pegawai.getKucingCollection();
            for (Kucing kucingCollectionOrphanCheckKucing : kucingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pegawai (" + pegawai + ") cannot be destroyed since the Kucing " + kucingCollectionOrphanCheckKucing + " in its kucingCollection field has a non-nullable idPegawai field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pegawai);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pegawai> findPegawaiEntities() {
        return findPegawaiEntities(true, -1, -1);
    }

    public List<Pegawai> findPegawaiEntities(int maxResults, int firstResult) {
        return findPegawaiEntities(false, maxResults, firstResult);
    }

    private List<Pegawai> findPegawaiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pegawai.class));
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

    public Pegawai findPegawai(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pegawai.class, id);
        } finally {
            em.close();
        }
    }

    public int getPegawaiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pegawai> rt = cq.from(Pegawai.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
