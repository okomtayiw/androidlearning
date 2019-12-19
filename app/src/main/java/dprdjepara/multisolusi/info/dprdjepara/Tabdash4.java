package dprdjepara.multisolusi.info.dprdjepara;

import android.os.Bundle;
import android.util.Log;
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

import dprdjepara.multisolusi.info.dprdjepara.model.Dash4;
import dprdjepara.multisolusi.info.dprdjepara.model.ListAdapterDash4;

public class Tabdash4 extends Fragment {
    private String Kunci;
    private String ResponseUrl;
    private ListAdapterDash4 adapterDash4;
    private View convertView;
    private JSONArray jsonArrayDash;
    private JSONObject jsonObjectList;
    private String judul = "";
    private List<Dash4> listDash4;
    private Dash4 selectedList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.listDash4 = new ArrayList();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.convertView == null) {
            this.convertView = inflater.inflate(R.layout.tab_dash, container, false);
        }
        if (this.ResponseUrl == null) {
            this.ResponseUrl = "";
        }
        ListView lv_tab_dash = (ListView) this.convertView.findViewById(R.id.lv_tab_dash);
        this.listDash4 = processResponseDash4();
        this.adapterDash4 = new ListAdapterDash4(getActivity().getApplicationContext(), this.listDash4);
        lv_tab_dash.setAdapter(this.adapterDash4);

        //lv_tab_dash.notify();
        this.adapterDash4.notifyDataSetChanged();
        return this.convertView;
    }

    public void setResponseUrl(String ResponseUrl2, String Kunci2) {
        ResponseUrl = ResponseUrl2;
        Kunci = Kunci2;
    }

    private List<Dash4> processResponseDash4() {

        Dash4 dsh;
        this.listDash4.clear();
        Dash4 dsh2 = new Dash4();
        dsh2.setUrut(Integer.valueOf(0));
        dsh2.setId("Id");
        dsh2.setTanggal("Tanggal");
        dsh2.setNama("Dibaca");
        dsh2.setNamarapat("Kegiatan / Acara");
        dsh2.setTopik("Topik");
        this.listDash4.add(dsh2);
        if (this.ResponseUrl.length() != 0) {
            try {
                this.jsonArrayDash = new JSONObject(this.ResponseUrl).getJSONArray("pembaca");
                int i = 0;
                while (true) {
                    try {
                        dsh = dsh2;
                        if (i >= this.jsonArrayDash.length()) {
                            break;
                        }
                        this.jsonObjectList = this.jsonArrayDash.getJSONObject(i);
                        dsh2 = new Dash4();
                        dsh2.setUrut(Integer.valueOf(this.listDash4.size()));
                        dsh2.setId(this.jsonObjectList.getString("id"));
                        dsh2.setTanggal(this.jsonObjectList.getString("tanggal"));
                        dsh2.setNama(this.jsonObjectList.getString("nama"));
                        dsh2.setNamarapat(this.jsonObjectList.getString("nama_rapat"));
                        dsh2.setTopik(this.jsonObjectList.getString("topik"));
                        this.listDash4.add(dsh2);
                        i++;
                    } catch (JSONException e) {
                        //Dash4 dash4 = dsh;
                    }
                }
                //Dash4 dash42 = dsh;
            } catch (JSONException e2) {
            }
        }
        return this.listDash4;
    }
}
