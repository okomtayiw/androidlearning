package dprdjepara.multisolusi.info.dprdjepara.model;

public class Dash2 {

    /* renamed from: id */
    private String f64id;
    private String receivingdatetime;
    private String sendernumber;
    private String textdecoded;
    private Integer urut;

    public Dash2() {
    }

    public Dash2(Integer urut2, String id, String sendernumber2, String textdecoded2, String receivingdatetime2) {
        this.urut = urut2;
        this.f64id = id;
        this.sendernumber = sendernumber2;
        this.textdecoded = textdecoded2;
        this.receivingdatetime = receivingdatetime2;
    }

    public Integer getUrut() {
        return this.urut;
    }

    public void setUrut(Integer urut2) {
        this.urut = urut2;
    }

    public String getId() {
        return this.f64id;
    }

    public void setId(String id) {
        this.f64id = id;
    }

    public String getSendernumber() {
        return this.sendernumber;
    }

    public void setSendernumber(String sendernumber2) {
        this.sendernumber = sendernumber2;
    }

    public String getTextdecoded() {
        return this.textdecoded;
    }

    public void setTextdecoded(String textdecoded2) {
        this.textdecoded = textdecoded2;
    }

    public String getReceivingdatetime() {
        return this.receivingdatetime;
    }

    public void setReceivingdatetime(String receivingdatetime2) {
        this.receivingdatetime = receivingdatetime2;
    }
}
