/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package migratedb.senamjantung;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import migratedb.senamjantung.exceptions.NonexistentEntityException;
import migratedb.senamjantung.exceptions.PreexistingEntityException;

/**
 *
 * @author Zahran Rafif Pc
 */
public class RekamMedisJpaController implements Serializable {

    public RekamMedisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("migratedb_senamjantung_jar_0.0.1-SNAPSHOTPU");

    public RekamMedisJpaController() {
    }
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RekamMedis rekamMedis) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vaksin idVaksin = rekamMedis.getIdVaksin();
            if (idVaksin != null) {
                idVaksin = em.getReference(idVaksin.getClass(), idVaksin.getIdVaksin());
                rekamMedis.setIdVaksin(idVaksin);
            }
            Kucing idKucing = rekamMedis.getIdKucing();
            if (idKucing != null) {
                idKucing = em.getReference(idKucing.getClass(), idKucing.getIdKucing());
                rekamMedis.setIdKucing(idKucing);
            }
            em.persist(rekamMedis);
            if (idVaksin != null) {
                idVaksin.getRekamMedisCollection().add(rekamMedis);
                idVaksin = em.merge(idVaksin);
            }
            if (idKucing != null) {
                idKucing.getRekamMedisCollection().add(rekamMedis);
                idKucing = em.merge(idKucing);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRekamMedis(rekamMedis.getIdRM()) != null) {
                throw new PreexistingEntityException("RekamMedis " + rekamMedis + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RekamMedis rekamMedis) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RekamMedis persistentRekamMedis = em.find(RekamMedis.class, rekamMedis.getIdRM());
            Vaksin idVaksinOld = persistentRekamMedis.getIdVaksin();
            Vaksin idVaksinNew = rekamMedis.getIdVaksin();
            Kucing idKucingOld = persistentRekamMedis.getIdKucing();
            Kucing idKucingNew = rekamMedis.getIdKucing();
            if (idVaksinNew != null) {
                idVaksinNew = em.getReference(idVaksinNew.getClass(), idVaksinNew.getIdVaksin());
                rekamMedis.setIdVaksin(idVaksinNew);
            }
            if (idKucingNew != null) {
                idKucingNew = em.getReference(idKucingNew.getClass(), idKucingNew.getIdKucing());
                rekamMedis.setIdKucing(idKucingNew);
            }
            rekamMedis = em.merge(rekamMedis);
            if (idVaksinOld != null && !idVaksinOld.equals(idVaksinNew)) {
                idVaksinOld.getRekamMedisCollection().remove(rekamMedis);
                idVaksinOld = em.merge(idVaksinOld);
            }
            if (idVaksinNew != null && !idVaksinNew.equals(idVaksinOld)) {
                idVaksinNew.getRekamMedisCollection().add(rekamMedis);
                idVaksinNew = em.merge(idVaksinNew);
            }
            if (idKucingOld != null && !idKucingOld.equals(idKucingNew)) {
                idKucingOld.getRekamMedisCollection().remove(rekamMedis);
                idKucingOld = em.merge(idKucingOld);
            }
            if (idKucingNew != null && !idKucingNew.equals(idKucingOld)) {
                idKucingNew.getRekamMedisCollection().add(rekamMedis);
                idKucingNew = em.merge(idKucingNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = rekamMedis.getIdRM();
                if (findRekamMedis(id) == null) {
                    throw new NonexistentEntityException("The rekamMedis with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RekamMedis rekamMedis;
            try {
                rekamMedis = em.getReference(RekamMedis.class, id);
                rekamMedis.getIdRM();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rekamMedis with id " + id + " no longer exists.", enfe);
            }
            Vaksin idVaksin = rekamMedis.getIdVaksin();
            if (idVaksin != null) {
                idVaksin.getRekamMedisCollection().remove(rekamMedis);
                idVaksin = em.merge(idVaksin);
            }
            Kucing idKucing = rekamMedis.getIdKucing();
            if (idKucing != null) {
                idKucing.getRekamMedisCollection().remove(rekamMedis);
                idKucing = em.merge(idKucing);
            }
            em.remove(rekamMedis);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RekamMedis> findRekamMedisEntities() {
        return findRekamMedisEntities(true, -1, -1);
    }

    public List<RekamMedis> findRekamMedisEntities(int maxResults, int firstResult) {
        return findRekamMedisEntities(false, maxResults, firstResult);
    }

    private List<RekamMedis> findRekamMedisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RekamMedis.class));
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

    public RekamMedis findRekamMedis(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RekamMedis.class, id);
        } finally {
            em.close();
        }
    }

    public int getRekamMedisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RekamMedis> rt = cq.from(RekamMedis.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
