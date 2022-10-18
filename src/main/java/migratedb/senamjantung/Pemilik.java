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
@Table(name = "pemilik")
@NamedQueries({
    @NamedQuery(name = "Pemilik.findAll", query = "SELECT p FROM Pemilik p"),
    @NamedQuery(name = "Pemilik.findByIdPemilik", query = "SELECT p FROM Pemilik p WHERE p.idPemilik = :idPemilik"),
    @NamedQuery(name = "Pemilik.findByNamaPemilik", query = "SELECT p FROM Pemilik p WHERE p.namaPemilik = :namaPemilik"),
    @NamedQuery(name = "Pemilik.findByJenisKelamin", query = "SELECT p FROM Pemilik p WHERE p.jenisKelamin = :jenisKelamin"),
    @NamedQuery(name = "Pemilik.findByNoTelepon", query = "SELECT p FROM Pemilik p WHERE p.noTelepon = :noTelepon"),
    @NamedQuery(name = "Pemilik.findByAlamat", query = "SELECT p FROM Pemilik p WHERE p.alamat = :alamat")})
public class Pemilik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Pemilik")
    private String idPemilik;
    @Basic(optional = false)
    @Column(name = "Nama_Pemilik")
    private String namaPemilik;
    @Basic(optional = false)
    @Column(name = "Jenis_Kelamin")
    private String jenisKelamin;
    @Basic(optional = false)
    @Column(name = "No_Telepon")
    private String noTelepon;
    @Basic(optional = false)
    @Column(name = "Alamat")
    private String alamat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPemilik")
    private Collection<Kucing> kucingCollection;

    public Pemilik() {
    }

    public Pemilik(String idPemilik) {
        this.idPemilik = idPemilik;
    }

    public Pemilik(String idPemilik, String namaPemilik, String jenisKelamin, String noTelepon, String alamat) {
        this.idPemilik = idPemilik;
        this.namaPemilik = namaPemilik;
        this.jenisKelamin = jenisKelamin;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
    }

    public String getIdPemilik() {
        return idPemilik;
    }

    public void setIdPemilik(String idPemilik) {
        this.idPemilik = idPemilik;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Collection<Kucing> getKucingCollection() {
        return kucingCollection;
    }

    public void setKucingCollection(Collection<Kucing> kucingCollection) {
        this.kucingCollection = kucingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPemilik != null ? idPemilik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pemilik)) {
            return false;
        }
        Pemilik other = (Pemilik) object;
        if ((this.idPemilik == null && other.idPemilik != null) || (this.idPemilik != null && !this.idPemilik.equals(other.idPemilik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "migratedb.senamjantung.Pemilik[ idPemilik=" + idPemilik + " ]";
    }
    
}
