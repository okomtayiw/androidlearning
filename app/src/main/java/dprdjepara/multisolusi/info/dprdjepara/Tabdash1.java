package dprdjepara.multisolusi.info.dprdjepara;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dprdjepara.multisolusi.info.dprdjepara.model.Dash1;
import dprdjepara.multisolusi.info.dprdjepara.model.ListAdapterDash1;

public class Tabdash1 extends Fragment {
    private String Kunci;
    private String ResponseUrl;
    private ListAdapterDash1 adapterDash1;
    private View convertView;
    private JSONArray jsonArrayDash;
    private JSONObject jsonObjectList;
    private String judul = "";
    private List<Dash1> listDash1;
    private Dash1 selectedList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.listDash1 = new ArrayList();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.convertView == null) {
            this.convertView = inflater.inflate(R.layout.tab_dash, container, false);
        }
        if (this.ResponseUrl == null) {
            this.ResponseUrl = "";
        }
        ListView lv_tab_dash = (ListView) this.convertView.findViewById(R.id.lv_tab_dash);
        this.listDash1 = processResponseDash1();
        this.adapterDash1 = new ListAdapterDash1(getActivity().getApplicationContext(), this.listDash1);
        lv_tab_dash.setAdapter(this.adapterDash1);
        this.adapterDash1.notifyDataSetChanged();
        return this.convertView;
    }

    public void setResponseUrl(String ResponseUrl2, String Kunci2) {
        this.ResponseUrl = ResponseUrl2;
        this.Kunci = Kunci2;
    }

    private List<Dash1> processResponseDash1() {
        Dash1 dsh;
        this.listDash1.clear();
        Dash1 dsh2 = new Dash1();
        dsh2.setUrut(Integer.valueOf(0));
        dsh2.setId("Id");
        dsh2.setTanggal("Tanggal");
        dsh2.setNama("Nama");
        dsh2.setKet("Ket");
        dsh2.setImei("Imei");
        dsh2.setGlat("Lat");
        dsh2.setGlong("Long");
        this.listDash1.add(dsh2);
        if (this.ResponseUrl.length() != 0) {
            try {
                this.jsonArrayDash = new JSONObject(this.ResponseUrl).getJSONArray("log");
                int i = 0;
                while (true) {
                    try {
                        dsh = dsh2;
                        if (i >= this.jsonArrayDash.length()) {
                            break;
                        }
                        this.jsonObjectList = this.jsonArrayDash.getJSONObject(i);
                        dsh2 = new Dash1();
                        dsh2.setUrut(Integer.valueOf(this.listDash1.size()));
                        dsh2.setId(this.jsonObjectList.getString("id"));
                        dsh2.setTanggal(this.jsonObjectList.getString("tanggal"));
                        dsh2.setNama(this.jsonObjectList.getString("nama"));
                        dsh2.setKet(this.jsonObjectList.getString("ket"));
                        dsh2.setImei(this.jsonObjectList.getString("imei"));
                        dsh2.setGlat(this.jsonObjectList.getString("glat"));
                        dsh2.setGlong(this.jsonObjectList.getString("glong"));
                        this.listDash1.add(dsh2);
                        i++;
                    } catch (JSONException e) {
                        //Dash1 dash1 = dsh;
                    }
                }
                Dash1 dash12 = dsh;
            } catch (JSONException e2) {
            }
        }
        return this.listDash1;
    }
}
