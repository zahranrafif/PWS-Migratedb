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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Zahran Rafif Pc
 */
@Entity
@Table(name = "kucing")
@NamedQueries({
    @NamedQuery(name = "Kucing.findAll", query = "SELECT k FROM Kucing k"),
    @NamedQuery(name = "Kucing.findByIdKucing", query = "SELECT k FROM Kucing k WHERE k.idKucing = :idKucing"),
    @NamedQuery(name = "Kucing.findByNamaKucing", query = "SELECT k FROM Kucing k WHERE k.namaKucing = :namaKucing"),
    @NamedQuery(name = "Kucing.findByUmur", query = "SELECT k FROM Kucing k WHERE k.umur = :umur"),
    @NamedQuery(name = "Kucing.findByJenisKelamin", query = "SELECT k FROM Kucing k WHERE k.jenisKelamin = :jenisKelamin"),
    @NamedQuery(name = "Kucing.findByRas", query = "SELECT k FROM Kucing k WHERE k.ras = :ras"),
    @NamedQuery(name = "Kucing.findByWarnaKucing", query = "SELECT k FROM Kucing k WHERE k.warnaKucing = :warnaKucing")})
public class Kucing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Kucing")
    private String idKucing;
    @Basic(optional = false)
    @Column(name = "Nama_Kucing")
    private String namaKucing;
    @Basic(optional = false)
    @Column(name = "Umur")
    private String umur;
    @Basic(optional = false)
    @Column(name = "Jenis_Kelamin")
    private String jenisKelamin;
    @Basic(optional = false)
    @Column(name = "Ras")
    private String ras;
    @Basic(optional = false)
    @Column(name = "Warna_Kucing")
    private String warnaKucing;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKucing")
    private Collection<RekamMedis> rekamMedisCollection;
    @JoinColumn(name = "Id_Pemilik", referencedColumnName = "Id_Pemilik")
    @ManyToOne(optional = false)
    private Pemilik idPemilik;
    @JoinColumn(name = "Id_Pegawai", referencedColumnName = "Id_Pegawai")
    @ManyToOne(optional = false)
    private Pegawai idPegawai;

    public Kucing() {
    }

    public Kucing(String idKucing) {
        this.idKucing = idKucing;
    }

    public Kucing(String idKucing, String namaKucing, String umur, String jenisKelamin, String ras, String warnaKucing) {
        this.idKucing = idKucing;
        this.namaKucing = namaKucing;
        this.umur = umur;
        this.jenisKelamin = jenisKelamin;
        this.ras = ras;
        this.warnaKucing = warnaKucing;
    }

    public String getIdKucing() {
        return idKucing;
    }

    public void setIdKucing(String idKucing) {
        this.idKucing = idKucing;
    }

    public String getNamaKucing() {
        return namaKucing;
    }

    public void setNamaKucing(String namaKucing) {
        this.namaKucing = namaKucing;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getRas() {
        return ras;
    }

    public void setRas(String ras) {
        this.ras = ras;
    }

    public String getWarnaKucing() {
        return warnaKucing;
    }

    public void setWarnaKucing(String warnaKucing) {
        this.warnaKucing = warnaKucing;
    }

    public Collection<RekamMedis> getRekamMedisCollection() {
        return rekamMedisCollection;
    }

    public void setRekamMedisCollection(Collection<RekamMedis> rekamMedisCollection) {
        this.rekamMedisCollection = rekamMedisCollection;
    }

    public Pemilik getIdPemilik() {
        return idPemilik;
    }

    public void setIdPemilik(Pemilik idPemilik) {
        this.idPemilik = idPemilik;
    }

    public Pegawai getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(Pegawai idPegawai) {
        this.idPegawai = idPegawai;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKucing != null ? idKucing.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kucing)) {
            return false;
        }
        Kucing other = (Kucing) object;
        if ((this.idKucing == null && other.idKucing != null) || (this.idKucing != null && !this.idKucing.equals(other.idKucing))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "migratedb.senamjantung.Kucing[ idKucing=" + idKucing + " ]";
    }
    
}
