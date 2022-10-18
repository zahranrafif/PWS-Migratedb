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
public class KucingJpaController implements Serializable {

    public KucingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("migratedb_senamjantung_jar_0.0.1-SNAPSHOTPU");

    public KucingJpaController() {
    }
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Kucing kucing) throws PreexistingEntityException, Exception {
        if (kucing.getRekamMedisCollection() == null) {
            kucing.setRekamMedisCollection(new ArrayList<RekamMedis>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pemilik idPemilik = kucing.getIdPemilik();
            if (idPemilik != null) {
                idPemilik = em.getReference(idPemilik.getClass(), idPemilik.getIdPemilik());
                kucing.setIdPemilik(idPemilik);
            }
            Pegawai idPegawai = kucing.getIdPegawai();
            if (idPegawai != null) {
                idPegawai = em.getReference(idPegawai.getClass(), idPegawai.getIdPegawai());
                kucing.setIdPegawai(idPegawai);
            }
            Collection<RekamMedis> attachedRekamMedisCollection = new ArrayList<RekamMedis>();
            for (RekamMedis rekamMedisCollectionRekamMedisToAttach : kucing.getRekamMedisCollection()) {
                rekamMedisCollectionRekamMedisToAttach = em.getReference(rekamMedisCollectionRekamMedisToAttach.getClass(), rekamMedisCollectionRekamMedisToAttach.getIdRM());
                attachedRekamMedisCollection.add(rekamMedisCollectionRekamMedisToAttach);
            }
            kucing.setRekamMedisCollection(attachedRekamMedisCollection);
            em.persist(kucing);
            if (idPemilik != null) {
                idPemilik.getKucingCollection().add(kucing);
                idPemilik = em.merge(idPemilik);
            }
            if (idPegawai != null) {
                idPegawai.getKucingCollection().add(kucing);
                idPegawai = em.merge(idPegawai);
            }
            for (RekamMedis rekamMedisCollectionRekamMedis : kucing.getRekamMedisCollection()) {
                Kucing oldIdKucingOfRekamMedisCollectionRekamMedis = rekamMedisCollectionRekamMedis.getIdKucing();
                rekamMedisCollectionRekamMedis.setIdKucing(kucing);
                rekamMedisCollectionRekamMedis = em.merge(rekamMedisCollectionRekamMedis);
                if (oldIdKucingOfRekamMedisCollectionRekamMedis != null) {
                    oldIdKucingOfRekamMedisCollectionRekamMedis.getRekamMedisCollection().remove(rekamMedisCollectionRekamMedis);
                    oldIdKucingOfRekamMedisCollectionRekamMedis = em.merge(oldIdKucingOfRekamMedisCollectionRekamMedis);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKucing(kucing.getIdKucing()) != null) {
                throw new PreexistingEntityException("Kucing " + kucing + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kucing kucing) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kucing persistentKucing = em.find(Kucing.class, kucing.getIdKucing());
            Pemilik idPemilikOld = persistentKucing.getIdPemilik();
            Pemilik idPemilikNew = kucing.getIdPemilik();
            Pegawai idPegawaiOld = persistentKucing.getIdPegawai();
            Pegawai idPegawaiNew = kucing.getIdPegawai();
            Collection<RekamMedis> rekamMedisCollectionOld = persistentKucing.getRekamMedisCollection();
            Collection<RekamMedis> rekamMedisCollectionNew = kucing.getRekamMedisCollection();
            List<String> illegalOrphanMessages = null;
            for (RekamMedis rekamMedisCollectionOldRekamMedis : rekamMedisCollectionOld) {
                if (!rekamMedisCollectionNew.contains(rekamMedisCollectionOldRekamMedis)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RekamMedis " + rekamMedisCollectionOldRekamMedis + " since its idKucing field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPemilikNew != null) {
                idPemilikNew = em.getReference(idPemilikNew.getClass(), idPemilikNew.getIdPemilik());
                kucing.setIdPemilik(idPemilikNew);
            }
            if (idPegawaiNew != null) {
                idPegawaiNew = em.getReference(idPegawaiNew.getClass(), idPegawaiNew.getIdPegawai());
                kucing.setIdPegawai(idPegawaiNew);
            }
            Collection<RekamMedis> attachedRekamMedisCollectionNew = new ArrayList<RekamMedis>();
            for (RekamMedis rekamMedisCollectionNewRekamMedisToAttach : rekamMedisCollectionNew) {
                rekamMedisCollectionNewRekamMedisToAttach = em.getReference(rekamMedisCollectionNewRekamMedisToAttach.getClass(), rekamMedisCollectionNewRekamMedisToAttach.getIdRM());
                attachedRekamMedisCollectionNew.add(rekamMedisCollectionNewRekamMedisToAttach);
            }
            rekamMedisCollectionNew = attachedRekamMedisCollectionNew;
            kucing.setRekamMedisCollection(rekamMedisCollectionNew);
            kucing = em.merge(kucing);
            if (idPemilikOld != null && !idPemilikOld.equals(idPemilikNew)) {
                idPemilikOld.getKucingCollection().remove(kucing);
                idPemilikOld = em.merge(idPemilikOld);
            }
            if (idPemilikNew != null && !idPemilikNew.equals(idPemilikOld)) {
                idPemilikNew.getKucingCollection().add(kucing);
                idPemilikNew = em.merge(idPemilikNew);
            }
            if (idPegawaiOld != null && !idPegawaiOld.equals(idPegawaiNew)) {
                idPegawaiOld.getKucingCollection().remove(kucing);
                idPegawaiOld = em.merge(idPegawaiOld);
            }
            if (idPegawaiNew != null && !idPegawaiNew.equals(idPegawaiOld)) {
                idPegawaiNew.getKucingCollection().add(kucing);
                idPegawaiNew = em.merge(idPegawaiNew);
            }
            for (RekamMedis rekamMedisCollectionNewRekamMedis : rekamMedisCollectionNew) {
                if (!rekamMedisCollectionOld.contains(rekamMedisCollectionNewRekamMedis)) {
                    Kucing oldIdKucingOfRekamMedisCollectionNewRekamMedis = rekamMedisCollectionNewRekamMedis.getIdKucing();
                    rekamMedisCollectionNewRekamMedis.setIdKucing(kucing);
                    rekamMedisCollectionNewRekamMedis = em.merge(rekamMedisCollectionNewRekamMedis);
                    if (oldIdKucingOfRekamMedisCollectionNewRekamMedis != null && !oldIdKucingOfRekamMedisCollectionNewRekamMedis.equals(kucing)) {
                        oldIdKucingOfRekamMedisCollectionNewRekamMedis.getRekamMedisCollection().remove(rekamMedisCollectionNewRekamMedis);
                        oldIdKucingOfRekamMedisCollectionNewRekamMedis = em.merge(oldIdKucingOfRekamMedisCollectionNewRekamMedis);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = kucing.getIdKucing();
                if (findKucing(id) == null) {
                    throw new NonexistentEntityException("The kucing with id " + id + " no longer exists.");
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
            Kucing kucing;
            try {
                kucing = em.getReference(Kucing.class, id);
                kucing.getIdKucing();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kucing with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<RekamMedis> rekamMedisCollectionOrphanCheck = kucing.getRekamMedisCollection();
            for (RekamMedis rekamMedisCollectionOrphanCheckRekamMedis : rekamMedisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Kucing (" + kucing + ") cannot be destroyed since the RekamMedis " + rekamMedisCollectionOrphanCheckRekamMedis + " in its rekamMedisCollection field has a non-nullable idKucing field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pemilik idPemilik = kucing.getIdPemilik();
            if (idPemilik != null) {
                idPemilik.getKucingCollection().remove(kucing);
                idPemilik = em.merge(idPemilik);
            }
            Pegawai idPegawai = kucing.getIdPegawai();
            if (idPegawai != null) {
                idPegawai.getKucingCollection().remove(kucing);
                idPegawai = em.merge(idPegawai);
            }
            em.remove(kucing);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kucing> findKucingEntities() {
        return findKucingEntities(true, -1, -1);
    }

    public List<Kucing> findKucingEntities(int maxResults, int firstResult) {
        return findKucingEntities(false, maxResults, firstResult);
    }

    private List<Kucing> findKucingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kucing.class));
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

    public Kucing findKucing(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kucing.class, id);
        } finally {
            em.close();
        }
    }

    public int getKucingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kucing> rt = cq.from(Kucing.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
