package dprdjepara.multisolusi.info.dprdjepara;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;

import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;

public class DashboardActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    public static final String UPLOAD_KEY = "image";
    private String BarisPerHalaman;
    private String Glat;
    private String Glong;

    /* renamed from: Hp */
    private String f49Hp;
    private String Imei;
    /* access modifiers changed from: private */
    public String Kunci;
    private String Nama;
    private int PICK_IMAGE_REQUEST = 1;
    private String Password;
    /* access modifiers changed from: private */
    public String ResponseUrl;
    private String Role;
    private String Username;
    private Pagerdashboard adapter_dashboard_tab;
    private Bitmap bitmap;
    private Uri filePath;
    private String hslresult;
    private ImageView iv_account;
    protected ProgressDialog progressDialog;
    private String responsejenis;
    /* access modifiers changed from: private */
    public ServerRequest serverRequest;
    private TabLayout tabLayout_dashboard;
    private TextView tv_judul;
    private ViewPager viewPager_dashboard;
    private RelativeLayout BackDas;

    private class DashboardActivityAsync extends AsyncTask<String, Void, String> {
        private DashboardActivityAsync() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            progressDialog = new ProgressDialog(DashboardActivity.this);
            progressDialog.setMessage("Ambil Data...");
            progressDialog.show();
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... params) {
            ResponseUrl = serverRequest.sendGetRequestR(ServerRequest.urlAdminlog + Kunci);
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String result) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    populate();
                }
            });
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_dashboard);
        this.serverRequest = new ServerRequest();
        this.Imei = getIntent().getStringExtra("imei");
        this.Nama = getIntent().getStringExtra("nama");
        this.BarisPerHalaman = getIntent().getStringExtra("barisperhalaman");
        this.Username = getIntent().getStringExtra("username");
        this.Password = getIntent().getStringExtra("password");
        this.f49Hp = getIntent().getStringExtra("hp");
        this.Role = getIntent().getStringExtra("role");
        this.Kunci = getIntent().getStringExtra("kunci");
        this.Glat = getIntent().getStringExtra("glat");
        this.Glong = getIntent().getStringExtra("glong");
        this.tabLayout_dashboard = (TabLayout) findViewById(R.id.tabLayout_dashboard);
        this.tabLayout_dashboard.addTab(this.tabLayout_dashboard.newTab().setText((CharSequence) "Log"));
        this.tabLayout_dashboard.addTab(this.tabLayout_dashboard.newTab().setText((CharSequence) "Inbox"));
        this.tabLayout_dashboard.addTab(this.tabLayout_dashboard.newTab().setText((CharSequence) "Outbox"));
        this.tabLayout_dashboard.addTab(this.tabLayout_dashboard.newTab().setText((CharSequence) "Pembaca"));
        this.tabLayout_dashboard.setTabGravity(0);
        this.viewPager_dashboard = (ViewPager) findViewById(R.id.viewpager_dashboard);
        this.viewPager_dashboard.setAdapter(new Pagerdashboard(getSupportFragmentManager(), this.tabLayout_dashboard.getTabCount()));
        this.tabLayout_dashboard.setOnTabSelectedListener(this);
        new DashboardActivityAsync().execute(new String[]{""});


        BackDas = (RelativeLayout) findViewById(R.id.backImage_dasboard);

        BackDas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void onTabSelected(Tab tab) {
        this.viewPager_dashboard.setCurrentItem(tab.getPosition());
    }

    public void onTabUnselected(Tab tab) {
    }

    public void onTabReselected(Tab tab) {
    }

    public boolean populate() {
        adapter_dashboard_tab = new Pagerdashboard(getSupportFragmentManager(), tabLayout_dashboard.getTabCount());
        adapter_dashboard_tab.setResponseUrl(ResponseUrl, Kunci);
        viewPager_dashboard.setAdapter(adapter_dashboard_tab);
        adapter_dashboard_tab.notifyDataSetChanged();
        return true;
    }

    public void KlikRefresh(View view) {
        new DashboardActivityAsync().execute(new String[]{""});
    }
}
