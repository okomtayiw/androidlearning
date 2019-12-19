package dprdjepara.multisolusi.info.dprdjepara.model;

public class Informasi {
    private String filename;
    private String filetype;

    /* renamed from: id */
    private String f68id;
    private String keterangan;
    private String tanggal;
    private String md5;

    public Informasi() {
    }

    public Informasi(String id, String tanggal2, String keterangan2, String filename2, String filetype2) {
        this.f68id = id;
        this.tanggal = tanggal2;
        this.keterangan = keterangan2;
        this.filename = filename2;
        this.filetype = filetype2;
    }

    public String getId() {
        return this.f68id;
    }

    public void setId(String id) {
        this.f68id = id;
    }

    public String getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(String tanggal2) {
        this.tanggal = tanggal2;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan2) {
        this.keterangan = keterangan2;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename2) {
        this.filename = filename2;
    }

    public String getFiletype() {
        return this.filetype;
    }

    public void setFiletype(String filetype2) {
        this.filetype = filetype2;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
