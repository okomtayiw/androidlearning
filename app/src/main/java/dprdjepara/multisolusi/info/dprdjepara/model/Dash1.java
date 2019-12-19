package dprdjepara.multisolusi.info.dprdjepara.model;

public class Dash1 {
    private String glat;
    private String glong;

    /* renamed from: id */
    private String f63id;
    private String imei;
    private String ket;
    private String nama;
    private String tanggal;
    private Integer urut;

    public Dash1() {
    }

    public Dash1(Integer urut2, String id, String tanggal2, String nama2, String ket2, String imei2, String glat2, String glong2) {
        this.urut = urut2;
        this.f63id = id;
        this.tanggal = tanggal2;
        this.nama = nama2;
        this.ket = ket2;
        this.imei = imei2;
        this.glat = glong2;
        this.glong = glong2;
    }

    public Integer getUrut() {
        return this.urut;
    }

    public void setUrut(Integer urut2) {
        this.urut = urut2;
    }

    public String getId() {
        return this.f63id;
    }

    public void setId(String id) {
        this.f63id = id;
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

    public String getKet() {
        return this.ket;
    }

    public void setKet(String ket2) {
        this.ket = ket2;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei2) {
        this.imei = imei2;
    }

    public String getGlat() {
        return this.glat;
    }

    public void setGlat(String glat2) {
        this.glat = glat2;
    }

    public String getGlong() {
        return this.glong;
    }

    public void setGlong(String glong2) {
        this.glong = glong2;
    }
}
