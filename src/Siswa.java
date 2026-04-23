package src;

public class Siswa {

    private String nis;
    private String nama;
    private String alamat;

    // === KONSTRUKTOR ===
    public Siswa(String nis, String nama, String alamat) {
        this.nis    = nis;
        this.nama   = nama;
        this.alamat = alamat;
    }

    public String getNis()    { return nis; }
    public String getNama()   { return nama; }
    public String getAlamat() { return alamat; }

    public void setNama(String nama)     { this.nama   = nama; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    @Override
    public String toString() {
        return nis + "," + nama + "," + alamat;
    }

}
