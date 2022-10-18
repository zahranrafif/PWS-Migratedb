/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package migratedb.senamjantung;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Zahran Rafif Pc
 */
@Entity
@Table(name = "vaksin")
@NamedQueries({
    @NamedQuery(name = "Vaksin.findAll", query = "SELECT v FROM Vaksin v"),
    @NamedQuery(name = "Vaksin.findByIdVaksin", query = "SELECT v FROM Vaksin v WHERE v.idVaksin = :idVaksin"),
    @NamedQuery(name = "Vaksin.findByNamaVaksin", query = "SELECT v FROM Vaksin v WHERE v.namaVaksin = :namaVaksin"),
    @NamedQuery(name = "Vaksin.findByDosis", query = "SELECT v FROM Vaksin v WHERE v.dosis = :dosis")})
public class Vaksin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Vaksin")
    private String idVaksin;
    @Basic(optional = false)
    @Column(name = "Nama_Vaksin")
    private String namaVaksin;
    @Basic(optional = false)
    @Column(name = "Dosis")
    private int dosis;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVaksin")
    private Collection<RekamMedis> rekamMedisCollection;

    public Vaksin() {
    }

    public Vaksin(String idVaksin) {
        this.idVaksin = idVaksin;
    }

    public Vaksin(String idVaksin, String namaVaksin, int dosis) {
        this.idVaksin = idVaksin;
        this.namaVaksin = namaVaksin;
        this.dosis = dosis;
    }

    public String getIdVaksin() {
        return idVaksin;
    }

    public void setIdVaksin(String idVaksin) {
        this.idVaksin = idVaksin;
    }

    public String getNamaVaksin() {
        return namaVaksin;
    }

    public void setNamaVaksin(String namaVaksin) {
        this.namaVaksin = namaVaksin;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public Collection<RekamMedis> getRekamMedisCollection() {
        return rekamMedisCollection;
    }

    public void setRekamMedisCollection(Collection<RekamMedis> rekamMedisCollection) {
        this.rekamMedisCollection = rekamMedisCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVaksin != null ? idVaksin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vaksin)) {
            return false;
        }
        Vaksin other = (Vaksin) object;
        if ((this.idVaksin == null && other.idVaksin != null) || (this.idVaksin != null && !this.idVaksin.equals(other.idVaksin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "migratedb.senamjantung.Vaksin[ idVaksin=" + idVaksin + " ]";
    }
    
}
