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
@Table(name = "pegawai")
@NamedQueries({
    @NamedQuery(name = "Pegawai.findAll", query = "SELECT p FROM Pegawai p"),
    @NamedQuery(name = "Pegawai.findByIdPegawai", query = "SELECT p FROM Pegawai p WHERE p.idPegawai = :idPegawai"),
    @NamedQuery(name = "Pegawai.findByNamaPegawai", query = "SELECT p FROM Pegawai p WHERE p.namaPegawai = :namaPegawai"),
    @NamedQuery(name = "Pegawai.findByJensiKelamin", query = "SELECT p FROM Pegawai p WHERE p.jensiKelamin = :jensiKelamin"),
    @NamedQuery(name = "Pegawai.findByNoTelepon", query = "SELECT p FROM Pegawai p WHERE p.noTelepon = :noTelepon"),
    @NamedQuery(name = "Pegawai.findByAlamat", query = "SELECT p FROM Pegawai p WHERE p.alamat = :alamat")})
public class Pegawai implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Pegawai")
    private String idPegawai;
    @Basic(optional = false)
    @Column(name = "Nama_Pegawai")
    private String namaPegawai;
    @Basic(optional = false)
    @Column(name = "Jensi_Kelamin")
    private String jensiKelamin;
    @Basic(optional = false)
    @Column(name = "No_Telepon")
    private String noTelepon;
    @Basic(optional = false)
    @Column(name = "Alamat")
    private String alamat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPegawai")
    private Collection<Kucing> kucingCollection;

    public Pegawai() {
    }

    public Pegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public Pegawai(String idPegawai, String namaPegawai, String jensiKelamin, String noTelepon, String alamat) {
        this.idPegawai = idPegawai;
        this.namaPegawai = namaPegawai;
        this.jensiKelamin = jensiKelamin;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
    }

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getJensiKelamin() {
        return jensiKelamin;
    }

    public void setJensiKelamin(String jensiKelamin) {
        this.jensiKelamin = jensiKelamin;
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
        hash += (idPegawai != null ? idPegawai.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pegawai)) {
            return false;
        }
        Pegawai other = (Pegawai) object;
        if ((this.idPegawai == null && other.idPegawai != null) || (this.idPegawai != null && !this.idPegawai.equals(other.idPegawai))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "migratedb.senamjantung.Pegawai[ idPegawai=" + idPegawai + " ]";
    }
    
}
