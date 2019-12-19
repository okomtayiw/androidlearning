package dprdjepara.multisolusi.info.dprdjepara.model;

public class Dash4 {

    /* renamed from: id */
    private String f66id;
    private String nama;
    private String namarapat;
    private String tanggal;
    private String topik;
    private Integer urut;

    public Dash4() {
    }

    public Dash4(Integer urut2, String id, String tanggal2, String nama2, String namarapat2, String topik2) {
        this.urut = urut2;
        this.f66id = id;
        this.tanggal = tanggal2;
        this.nama = nama2;
        this.namarapat = namarapat2;
        this.topik = topik2;
    }

    public Integer getUrut() {
        return this.urut;
    }

    public void setUrut(Integer urut2) {
        this.urut = urut2;
    }

    public String getId() {
        return this.f66id;
    }

    public void setId(String id) {
        this.f66id = id;
    }

    public String getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(String tanggal2) {
        this.tanggal = tanggal2;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama2) {
        this.nama = nama2;
    }

    public String getNamarapat() {
        return this.namarapat;
    }

    public void setNamarapat(String namarapat2) {
        this.namarapat = namarapat2;
    }

    public String getTopik() {
        return this.topik;
    }

    public void setTopik(String topik2) {
        this.topik = topik2;
    }
}
