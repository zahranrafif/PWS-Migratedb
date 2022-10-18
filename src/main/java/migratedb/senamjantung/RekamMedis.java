/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package migratedb.senamjantung;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Zahran Rafif Pc
 */
@Entity
@Table(name = "rekam_medis")
@NamedQueries({
    @NamedQuery(name = "RekamMedis.findAll", query = "SELECT r FROM RekamMedis r"),
    @NamedQuery(name = "RekamMedis.findByIdRM", query = "SELECT r FROM RekamMedis r WHERE r.idRM = :idRM"),
    @NamedQuery(name = "RekamMedis.findByVaksinPertama", query = "SELECT r FROM RekamMedis r WHERE r.vaksinPertama = :vaksinPertama"),
    @NamedQuery(name = "RekamMedis.findByVaksinBerikutnya", query = "SELECT r FROM RekamMedis r WHERE r.vaksinBerikutnya = :vaksinBerikutnya")})
public class RekamMedis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_RM")
    private String idRM;
    @Basic(optional = false)
    @Column(name = "Vaksin_Pertama")
    private String vaksinPertama;
    @Basic(optional = false)
    @Column(name = "Vaksin_Berikutnya")
    private String vaksinBerikutnya;
    @JoinColumn(name = "Id_Vaksin", referencedColumnName = "Id_Vaksin")
    @ManyToOne(optional = false)
    private Vaksin idVaksin;
    @JoinColumn(name = "Id_Kucing", referencedColumnName = "Id_Kucing")
    @ManyToOne(optional = false)
    private Kucing idKucing;

    public RekamMedis() {
    }

    public RekamMedis(String idRM) {
        this.idRM = idRM;
    }

    public RekamMedis(String idRM, String vaksinPertama, String vaksinBerikutnya) {
        this.idRM = idRM;
        this.vaksinPertama = vaksinPertama;
        this.vaksinBerikutnya = vaksinBerikutnya;
    }

    public String getIdRM() {
        return idRM;
    }

    public void setIdRM(String idRM) {
        this.idRM = idRM;
    }

    public String getVaksinPertama() {
        return vaksinPertama;
    }

    public void setVaksinPertama(String vaksinPertama) {
        this.vaksinPertama = vaksinPertama;
    }

    public String getVaksinBerikutnya() {
        return vaksinBerikutnya;
    }

    public void setVaksinBerikutnya(String vaksinBerikutnya) {
        this.vaksinBerikutnya = vaksinBerikutnya;
    }

    public Vaksin getIdVaksin() {
        return idVaksin;
    }

    public void setIdVaksin(Vaksin idVaksin) {
        this.idVaksin = idVaksin;
    }

    public Kucing getIdKucing() {
        return idKucing;
    }

    public void setIdKucing(Kucing idKucing) {
        this.idKucing = idKucing;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRM != null ? idRM.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RekamMedis)) {
            return false;
        }
        RekamMedis other = (RekamMedis) object;
        if ((this.idRM == null && other.idRM != null) || (this.idRM != null && !this.idRM.equals(other.idRM))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "migratedb.senamjantung.RekamMedis[ idRM=" + idRM + " ]";
    }
    
}
