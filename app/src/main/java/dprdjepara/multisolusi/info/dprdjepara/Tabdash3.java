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

import dprdjepara.multisolusi.info.dprdjepara.model.Dash3;
import dprdjepara.multisolusi.info.dprdjepara.model.ListAdapterDash3;

public class Tabdash3 extends Fragment {
    private String Kunci;
    private String ResponseUrl;
    private ListAdapterDash3 adapterDash3;
    private View convertView;
    private JSONArray jsonArrayDash;
    private JSONObject jsonObjectList;
    private String judul = "";
    private List<Dash3> listDash3;
    private Dash3 selectedList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.listDash3 = new ArrayList();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.convertView == null) {
            this.convertView = inflater.inflate(R.layout.tab_dash, container, false);
        }
        if (this.ResponseUrl == null) {
            this.ResponseUrl = "";
        }
        ListView lv_tab_dash = (ListView) this.convertView.findViewById(R.id.lv_tab_dash);
        this.listDash3 = processResponseDash3();
        this.adapterDash3 = new ListAdapterDash3(getActivity().getApplicationContext(), this.listDash3);
        lv_tab_dash.setAdapter(this.adapterDash3);
        this.adapterDash3.notifyDataSetChanged();
        return this.convertView;
    }

    public void setResponseUrl(String ResponseUrl2, String Kunci2) {
        this.ResponseUrl = ResponseUrl2;
        this.Kunci = Kunci2;
    }

    private List<Dash3> processResponseDash3() {
        Dash3 dsh;
        this.listDash3.clear();
        Dash3 dsh2 = new Dash3();
        dsh2.setUrut(Integer.valueOf(0));
        dsh2.setId("Id");
        dsh2.setDestinationnumber("Destination");
        dsh2.setTextdecoded("Text");
        dsh2.setSendingdatetime("Sendingdatetime");
        this.listDash3.add(dsh2);
        if (this.ResponseUrl.length() != 0) {
            try {
                this.jsonArrayDash = new JSONObject(this.ResponseUrl).getJSONArray("outbox");
                int i = 0;
                while (true) {
                    try {
                        dsh = dsh2;
                        if (i >= this.jsonArrayDash.length()) {
                            break;
                        }
                        this.jsonObjectList = this.jsonArrayDash.getJSONObject(i);
                        dsh2 = new Dash3();
                        dsh2.setUrut(Integer.valueOf(this.listDash3.size()));
                        dsh2.setId(this.jsonObjectList.getString("ID"));
                        dsh2.setDestinationnumber(this.jsonObjectList.getString("DestinationNumber"));
                        dsh2.setTextdecoded(this.jsonObjectList.getString("TextDecoded"));
                        dsh2.setSendingdatetime(this.jsonObjectList.getString("SendingDateTime"));
                        this.listDash3.add(dsh2);
                        i++;
                    } catch (JSONException e) {
                        //Dash3 dash3 = dsh;
                    }
                }
                //Dash3 dash32 = dsh;
            } catch (JSONException e2) {
            }
        }
        return this.listDash3;
    }
}
