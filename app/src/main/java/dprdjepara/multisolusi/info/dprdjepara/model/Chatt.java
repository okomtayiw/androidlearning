package dprdjepara.multisolusi.info.dprdjepara.model;

/**
 * Created by User on 05/12/2016.
 */


import android.text.format.DateFormat;

/**
 * Created by wira on 5/19/16.
 */
public class Chatt {

    private String id;
    private String id_account_to;
    private Boolean issender;
    private String pesan;
    private String time_stamp;
    private String username_from;
    private String flag;

    public Chatt() {
    }

    public Chatt(String id, String username_from2, String id_account_to2, String pesan2, String time_stamp2, String flag2, Boolean issender2) {
        this.id = id;
        this.username_from = username_from2;
        this.id_account_to = id_account_to2;
        this.pesan = pesan2;
        this.time_stamp = time_stamp2;
        this.flag = flag2;
        this.issender = issender2;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_account_to() {
        return id_account_to;
    }

    public void setId_account_to(String id_account_to) {
        this.id_account_to = id_account_to;
    }

    public Boolean getIssender() {
        return issender;
    }

    public void setIssender(Boolean issender) {
        this.issender = issender;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getUsername_from() {
        return username_from;
    }

    public void setUsername_from(String username_from) {
        this.username_from = username_from;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}

