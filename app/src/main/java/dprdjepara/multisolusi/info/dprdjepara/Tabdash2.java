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

import dprdjepara.multisolusi.info.dprdjepara.model.Dash2;
import dprdjepara.multisolusi.info.dprdjepara.model.ListAdapterDash2;

public class Tabdash2 extends Fragment {
    private String Kunci;
    private String ResponseUrl;
    private ListAdapterDash2 adapterDash2;
    private View convertView;
    private JSONArray jsonArrayDash;
    private JSONObject jsonObjectList;
    private String judul = "";
    private List<Dash2> listDash2;
    private Dash2 selectedList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.listDash2 = new ArrayList();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.convertView == null) {
            this.convertView = inflater.inflate(R.layout.tab_dash, container, false);
        }
        if (this.ResponseUrl == null) {
            this.ResponseUrl = "";
        }
        ListView lv_tab_dash = (ListView) this.convertView.findViewById(R.id.lv_tab_dash);
        this.listDash2 = processResponseDash2();
        this.adapterDash2 = new ListAdapterDash2(getActivity().getApplicationContext(), this.listDash2);
        lv_tab_dash.setAdapter(this.adapterDash2);
        this.adapterDash2.notifyDataSetChanged();
        return this.convertView;
    }

    public void setResponseUrl(String ResponseUrl2, String Kunci2) {
        this.ResponseUrl = ResponseUrl2;
        this.Kunci = Kunci2;
    }

    private List<Dash2> processResponseDash2() {
        Dash2 dsh;
        this.listDash2.clear();
        Dash2 dsh2 = new Dash2();
        dsh2.setUrut(Integer.valueOf(0));
        dsh2.setId("Id");
        dsh2.setSendernumber("Sender");
        dsh2.setTextdecoded("Text");
        dsh2.setReceivingdatetime("Receivingdatetime");
        this.listDash2.add(dsh2);
        if (this.ResponseUrl.length() != 0) {
            try {
                this.jsonArrayDash = new JSONObject(this.ResponseUrl).getJSONArray("inbox");
                int i = 0;
                while (true) {
                    try {
                        dsh = dsh2;
                        if (i >= this.jsonArrayDash.length()) {
                            break;
                        }
                        this.jsonObjectList = this.jsonArrayDash.getJSONObject(i);
                        dsh2 = new Dash2();
                        dsh2.setUrut(Integer.valueOf(this.listDash2.size()));
                        dsh2.setId(this.jsonObjectList.getString("ID"));
                        dsh2.setSendernumber(this.jsonObjectList.getString("SenderNumber"));
                        dsh2.setTextdecoded(this.jsonObjectList.getString("TextDecoded"));
                        dsh2.setReceivingdatetime(this.jsonObjectList.getString("ReceivingDateTime"));
                        this.listDash2.add(dsh2);
                        i++;
                    } catch (JSONException e) {
                        //Dash2 dash2 = dsh;
                    }
                }
                Dash2 dash22 = dsh;
            } catch (JSONException e2) {
            }
        }
        return this.listDash2;
    }
}
