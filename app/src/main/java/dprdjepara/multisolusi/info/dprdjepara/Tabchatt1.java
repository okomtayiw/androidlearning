package dprdjepara.multisolusi.info.dprdjepara;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import dprdjepara.multisolusi.info.dprdjepara.model.Chatting;
import dprdjepara.multisolusi.info.dprdjepara.model.ListAdapterChatting;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tabchatt1 extends Fragment {
    /* access modifiers changed from: private */
    public String Kunci;
    private String ResponseUrl;
    /* access modifiers changed from: private */
    public ListAdapterChatting adapterTab1;
    private View convertView;
    private JSONArray jsonArrayChatting;
    private JSONObject jsonObjectList;
    private String judul = "";
    private List<Chatting> listTab1;
    /* access modifiers changed from: private */
    public Chatting selectedList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.listTab1 = new ArrayList();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.convertView == null) {
            this.convertView = inflater.inflate(R.layout.tab_chatt1, container, false);
        }
        if (this.ResponseUrl == null) {
            this.ResponseUrl = "";
        }
        ListView lv_tab1 = (ListView) this.convertView.findViewById(R.id.lv_tab1);
        this.listTab1 = processResponseTab1();
        this.adapterTab1 = new ListAdapterChatting(getActivity().getApplicationContext(), this.listTab1);
        lv_tab1.setAdapter(this.adapterTab1);
        this.adapterTab1.notifyDataSetChanged();
        lv_tab1.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Tabchatt1.this.selectedList = (Chatting) Tabchatt1.this.adapterTab1.getItem(position);
                Intent in = new Intent(Tabchatt1.this.getActivity(), ChattActivity.class);
                in.putExtra("id_account", Tabchatt1.this.selectedList.getId().toString());
                in.putExtra("nama", Tabchatt1.this.selectedList.getNama().toString());
                in.putExtra("kunci", Tabchatt1.this.Kunci);
                Tabchatt1.this.startActivity(in);
            }
        });
        return this.convertView;
    }

    public void setResponseUrl(String ResponseUrl2, String Kunci2) {
        this.ResponseUrl = ResponseUrl2;
        this.Kunci = Kunci2;
    }

    private List<Chatting> processResponseTab1() {
        Chatting rkp1;
        this.listTab1.clear();
        Chatting rkp12 = null;
        new Random();
        if (this.ResponseUrl.length() != 0) {
            try {
                this.jsonArrayChatting = new JSONObject(this.ResponseUrl).getJSONArray("chatt");
                int i = 0;
                while (true) {
                    try {
                        rkp1 = rkp12;
                        if (i >= this.jsonArrayChatting.length()) {
                            break;
                        }
                        this.jsonObjectList = this.jsonArrayChatting.getJSONObject(i);
                        rkp12 = new Chatting();
                        rkp12.setId(this.jsonObjectList.getString("id"));
                        rkp12.setNama(this.jsonObjectList.getString("nama"));
                        rkp12.setPesan(this.jsonObjectList.getString("pesan"));
                        rkp12.setTime_stamp(this.jsonObjectList.getString("time_stamp"));
                        rkp12.setFlag(this.jsonObjectList.getString("flag"));
                        this.listTab1.add(rkp12);
                        i++;
                    } catch (JSONException e) {
                        //Chatting chatting = rkp1;
                    }
                }
                Chatting chatting2 = rkp1;
            } catch (JSONException e2) {
            }
        }
        return this.listTab1;
    }
}
