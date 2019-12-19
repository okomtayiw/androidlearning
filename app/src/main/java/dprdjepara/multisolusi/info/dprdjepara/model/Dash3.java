package dprdjepara.multisolusi.info.dprdjepara.model;

public class Dash3 {
    private String destinationnumber;

    /* renamed from: id */
    private String f65id;
    private String sendingdatetime;
    private String textdecoded;
    private Integer urut;

    public Dash3() {
    }

    public Dash3(Integer urut2, String id, String destinationnumber2, String textdecoded2, String sendingdatetime2) {
        this.urut = urut2;
        this.f65id = id;
        this.destinationnumber = destinationnumber2;
        this.textdecoded = textdecoded2;
        this.sendingdatetime = sendingdatetime2;
    }

    public Integer getUrut() {
        return this.urut;
    }

    public void setUrut(Integer urut2) {
        this.urut = urut2;
    }

    public String getId() {
        return this.f65id;
    }

    public void setId(String id) {
        this.f65id = id;
    }

    public String getDestinationnumber() {
        return this.destinationnumber;
    }

    public void setDestinationnumber(String destinationnumber2) {
        this.destinationnumber = destinationnumber2;
    }

    public String getTextdecoded() {
        return this.textdecoded;
    }

    public void setTextdecoded(String textdecoded2) {
        this.textdecoded = textdecoded2;
    }

    public String getSendingdatetime() {
        return this.sendingdatetime;
    }

    public void setSendingdatetime(String sendingdatetime2) {
        this.sendingdatetime = sendingdatetime2;
    }
}
