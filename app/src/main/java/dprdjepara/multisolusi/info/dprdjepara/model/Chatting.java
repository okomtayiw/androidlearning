package dprdjepara.multisolusi.info.dprdjepara.model;

public class Chatting {
    private String flag;

    /* renamed from: id */
    private String f62id;
    private String nama;
    private String pesan;
    private String time_stamp;

    public Chatting() {
    }

    public Chatting(String id, String nama2, String pesan2, String time_stamp2, String flag2) {
        this.f62id = id;
        this.nama = nama2;
        this.pesan = pesan2;
        this.time_stamp = time_stamp2;
        this.flag = flag2;
    }

    public String getId() {
        return this.f62id;
    }

    public void setId(String id) {
        this.f62id = id;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama2) {
        this.nama = nama2;
    }

    public String getPesan() {
        return this.pesan;
    }

    public void setPesan(String pesan2) {
        this.pesan = pesan2;
    }

    public String getTime_stamp() {
        return this.time_stamp;
    }

    public void setTime_stamp(String time_stamp2) {
        this.time_stamp = time_stamp2;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag2) {
        this.flag = flag2;
    }
}
