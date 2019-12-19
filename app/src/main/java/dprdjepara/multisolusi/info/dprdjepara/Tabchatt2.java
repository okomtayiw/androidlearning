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

public class Tabchatt2 extends Fragment {
    /* access modifiers changed from: private */
    public String Kunci;
    private String ResponseUrl;
    /* access modifiers changed from: private */
    public ListAdapterChatting adapterTab2;
    private View convertView;
    private JSONArray jsonArrayChatting;
    private JSONObject jsonObjectList;
    private String judul = "";
    private List<Chatting> listTab2;
    /* access modifiers changed from: private */
    public Chatting selectedList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.listTab2 = new ArrayList();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.convertView == null) {
            this.convertView = inflater.inflate(R.layout.tab_chatt2, container, false);
        }
        if (this.ResponseUrl == null) {
            this.ResponseUrl = "";
        }
        ListView lv_tab2 = (ListView) this.convertView.findViewById(R.id.lv_tab2);
        this.listTab2 = processResponseTab2();
        this.adapterTab2 = new ListAdapterChatting(getActivity().getApplicationContext(), this.listTab2);
        lv_tab2.setAdapter(this.adapterTab2);
        this.adapterTab2.notifyDataSetChanged();
        lv_tab2.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Tabchatt2.this.selectedList = (Chatting) Tabchatt2.this.adapterTab2.getItem(position);
                Intent in = new Intent(Tabchatt2.this.getActivity(), ChattActivity.class);
                in.putExtra("id_account", Tabchatt2.this.selectedList.getId().toString());
                in.putExtra("nama", Tabchatt2.this.selectedList.getNama().toString());
                in.putExtra("kunci", Tabchatt2.this.Kunci);
                Tabchatt2.this.startActivity(in);
            }
        });
        return this.convertView;
    }

    public void setResponseUrl(String ResponseUrl2, String Kunci2) {
        this.ResponseUrl = ResponseUrl2;
        this.Kunci = Kunci2;
    }

    private List<Chatting> processResponseTab2() {
        Chatting rkp2;
        this.listTab2.clear();
        Chatting rkp22 = null;
        new Random();
        if (this.ResponseUrl.length() != 0) {
            try {
                this.jsonArrayChatting = new JSONObject(this.ResponseUrl).getJSONArray("kontak");
                int i = 0;
                while (true) {
                    try {
                        rkp2 = rkp22;
                        if (i >= this.jsonArrayChatting.length()) {
                            break;
                        }
                        this.jsonObjectList = this.jsonArrayChatting.getJSONObject(i);
                        rkp22 = new Chatting();
                        rkp22.setId(this.jsonObjectList.getString("id"));
                        rkp22.setNama(this.jsonObjectList.getString("nama"));
                        rkp22.setPesan(this.jsonObjectList.getString("pesan"));
                        rkp22.setTime_stamp(this.jsonObjectList.getString("time_stamp"));
                        rkp22.setFlag(this.jsonObjectList.getString("flag"));
                        this.listTab2.add(rkp22);
                        i++;
                    } catch (JSONException e) {
                        //Chatting chatting = rkp2;
                    }
                }
                Chatting chatting2 = rkp2;
            } catch (JSONException e2) {
            }
        }
        return this.listTab2;
    }
}
