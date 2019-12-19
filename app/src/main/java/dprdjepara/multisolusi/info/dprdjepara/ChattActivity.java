package dprdjepara.multisolusi.info.dprdjepara;

/**
 * Created by User on 03/12/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dprdjepara.multisolusi.info.dprdjepara.model.Chatt;
import dprdjepara.multisolusi.info.dprdjepara.model.ListAdapterChatt;
import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;

import static android.R.id.list;

public class ChattActivity extends Activity {

    private static final String TAG = "ChattActivity";
    private int Adachatmasuk;
    private String BarisPerHalaman;
    private String Glat;
    private String Glong;

    /* renamed from: Hp */
    private String f47Hp;
    private String Imei;
    /* access modifiers changed from: private */
    public String Kunci;
    private String Nama;
    private String Password;
    /* access modifiers changed from: private */
    public String ResponseBaca;
    private String Role;
    private String Username;
    private ListAdapterChatt adapter;
    /* access modifiers changed from: private */
    public String id_account;
    /* access modifiers changed from: private */
    public String idlast;
    public boolean isRunning = false;
    private ImageView iv_chatt;
    private List<Chatt> list;
    private ListView listView;
    /* access modifiers changed from: private */
    public EditText mMessageEdit;
    private Button mSendButton;
    private String mTime;
    private String pesan;
    /* access modifiers changed from: private */
    public ServerRequest serverRequest;
    private TextView tv_judul;
    private UpdateChatt updateChatt;

    private class ChattActivityAsync extends AsyncTask<String, Void, String> {
        private ChattActivityAsync() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            ResponseBaca = serverRequest.sendGetRequestR(sb.append(ServerRequest.urlReadchat2).append("&id_account_from=").append(ChattActivity.this.id_account).append("&idlast=").append(ChattActivity.this.idlast).append(ChattActivity.this.Kunci).toString());
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String result) {
            ChattActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    ChattActivity.this.populateListView();
                }
            });
        }
    }

    private class SendChattActivityAsync extends AsyncTask<String, Void, String> {
        private SendChattActivityAsync() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... params) {
            ResponseBaca = serverRequest.sendGetRequestR("sendchatt2&idlast=" + ChattActivity.this.idlast + "&id_account_to=" + ChattActivity.this.id_account + "&pesan=" + ChattActivity.this.mMessageEdit.getText() + ChattActivity.this.Kunci);
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String result) {
            ChattActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    ChattActivity.this.populateListView();
                    ChattActivity.this.mMessageEdit.setText("");
                }
            });
        }
    }

    class UpdateChatt extends Thread {
        static final long DELAY = 5000;

        UpdateChatt() {
        }

        public void run() {
            while (ChattActivity.this.isRunning) {
                try {
                    new ChattActivityAsync().execute(new String[]{"Baru"});
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    ChattActivity.this.isRunning = false;
                    e.printStackTrace();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatt);
        this.Kunci = getIntent().getStringExtra("kunci");
        this.id_account = getIntent().getStringExtra("id_account");
        this.mSendButton = (Button) findViewById(R.id.sendButton);
        this.mMessageEdit = (EditText) findViewById(R.id.messageEdit);
        this.listView = (ListView) findViewById(R.id.listview_chatt);
        this.list = new ArrayList();
        this.list.clear();
        this.idlast = "0";
        this.pesan = "";
        this.serverRequest = new ServerRequest();
        this.iv_chatt = (ImageView) findViewById(R.id.iv_chatt);
        this.tv_judul = (TextView) findViewById(R.id.tv_judul);
        this.tv_judul.setText(getIntent().getStringExtra("nama"));
        RequestManager with = Glide.with((Activity) this);
        StringBuilder sb = new StringBuilder();
        ServerRequest serverRequest2 = this.serverRequest;
        StringBuilder append = sb.append(ServerRequest.serverUri);
        ServerRequest serverRequest3 = this.serverRequest;
        with.load(append.append(ServerRequest.urlDisplayaccount).append("&id=").append(this.id_account).toString()).thumbnail(0.5f)
                //.crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(this.iv_chatt);
        this.Adachatmasuk = 0;
        new ChattActivityAsync().execute(new String[]{"Baru"});
        this.mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!ChattActivity.this.mMessageEdit.getText().toString().equals("")) {
                    new SendChattActivityAsync().execute(new String[]{""});
                }
            }
        });
        this.updateChatt = new UpdateChatt();
        if (!this.isRunning) {
            this.updateChatt.start();
            this.isRunning = true;
        }
    }

    public synchronized void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (!this.isRunning) {
            this.updateChatt.interrupt();
            this.updateChatt.stop();
        }
    }

    /* access modifiers changed from: private */
    public void populateListView() {
        try {
            JSONArray jsonArray = new JSONObject(this.ResponseBaca).getJSONArray(ServerRequest.urlReadchat);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Chatt rpt = new Chatt();
                rpt.setId(obj.getString("id"));
                rpt.setUsername_from(obj.getString("username_from"));
                rpt.setId_account_to(obj.getString("id_account_to"));
                rpt.setPesan(obj.getString("pesan"));
                rpt.setTime_stamp(obj.getString("time_stamp"));
                rpt.setFlag(obj.getString("flag"));
                if (obj.getString("issender").equals("1")) {
                    rpt.setIssender(Boolean.TRUE);
                } else {
                    rpt.setIssender(Boolean.FALSE);
                }
                this.idlast = obj.getString("id");
                this.list.add(rpt);
                this.Adachatmasuk = 1;
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        if (this.Adachatmasuk == 1) {
            this.adapter = new ListAdapterChatt(getApplicationContext(), this.list);
            this.listView.setAdapter(this.adapter);
            this.adapter.notifyDataSetChanged();
            this.Adachatmasuk = 0;
        }
    }
}
