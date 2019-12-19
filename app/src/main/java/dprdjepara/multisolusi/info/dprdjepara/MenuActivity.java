package dprdjepara.multisolusi.info.dprdjepara;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import dprdjepara.multisolusi.info.dprdjepara.model.ListAdapterRapat;
import dprdjepara.multisolusi.info.dprdjepara.model.Rapat;
import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;

import static android.R.attr.action;

public class MenuActivity extends Activity {
    private static final String TAG = "MenuActivity";
    private String BarisPerHalaman;
    private String Glat;
    private String Glong;
    private Integer Halaman_Rapat;

    /* renamed from: Hp */
    private String f55Hp;
    private String Imei;
    private Integer Jumlah_Maksimal_Halaman;
    private String Kunci;
    private String Nama;
    LayoutParams Params_home;
    LayoutParams Params_message;
    LayoutParams Params_perda;
    LayoutParams Params_profile;
    LayoutParams Params_search;
    LayoutParams Params_setting;
    private String Password;
    private String Role;
    private String Username;
    private ActionMode actionMode;
    private ListAdapterRapat adapter;
    private Callback amCallback;
    private int calendarx;
    private int calendary;
    private int chatx;
    private int chaty;
    private int dashboardx;
    private int dashboardy;
    private ImageButton fab_calendar;
    private ImageButton fab_chat;
    private ImageButton fab_dashboard;
    private ImageButton fab_home;
    private ImageButton fab_info;
    private ImageButton fab_perda;
    private ImageButton fab_profile;
    private ImageButton fab_rapat;
    private int homex;
    private int homey;
    private int infox;
    private int infoy;
    LinearLayout.LayoutParams lParams1;
    LinearLayout.LayoutParams lParams2;
    private ListView listView;
    private LinearLayout ll_calendar;
    private LinearLayout ll_chat;
    private LinearLayout ll_dashboard;
    private LinearLayout ll_home;
    private LinearLayout ll_info;
    private LinearLayout ll_perda;
    private LinearLayout ll_profile;
    private LinearLayout ll_rapat;
    private int perdax;
    private int perday;
    private int profilex;
    private int profiley;
    private ProgressDialog progressDialog;
    private int rapatx;
    private int rapaty;
    private String responsejenis;
    private String responseprofile;
    private Rapat selectedList;
    private ServerRequest serverRequest;
    TelephonyManager tel;
    private int ybagi3;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.serverRequest = new ServerRequest();
        this.ll_home = (LinearLayout) findViewById(R.id.ll_home);
        this.ll_rapat = (LinearLayout) findViewById(R.id.ll_rapat);
        this.ll_calendar = (LinearLayout) findViewById(R.id.ll_calendar);
        this.ll_perda = (LinearLayout) findViewById(R.id.ll_perda);
        this.ll_chat = (LinearLayout) findViewById(R.id.ll_chat);
        this.ll_profile = (LinearLayout) findViewById(R.id.ll_profile);
        this.ll_info = (LinearLayout) findViewById(R.id.ll_info);
        this.ll_dashboard = (LinearLayout) findViewById(R.id.ll_dashboard);
        this.fab_home = (ImageButton) findViewById(R.id.fab_home);
        this.fab_rapat = (ImageButton) findViewById(R.id.fab_rapat);
        this.fab_calendar = (ImageButton) findViewById(R.id.fab_calendar);
        this.fab_perda = (ImageButton) findViewById(R.id.fab_perda);
        this.fab_chat = (ImageButton) findViewById(R.id.fab_chat);
        this.fab_profile = (ImageButton) findViewById(R.id.fab_profile);
        this.fab_info = (ImageButton) findViewById(R.id.fab_info);
        this.fab_dashboard = (ImageButton) findViewById(R.id.fab_dashboard);
        Updateposisi();
        this.fab_home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                KlikHome(v);
            }
        });
        this.Imei = getIntent().getStringExtra("imei");
        this.Nama = getIntent().getStringExtra("nama");
        this.BarisPerHalaman = getIntent().getStringExtra("barisperhalaman");
        this.Username = getIntent().getStringExtra("username");
        this.Password = getIntent().getStringExtra("password");
        this.f55Hp = getIntent().getStringExtra("hp");
        this.Role = getIntent().getStringExtra("role");
        this.Glong = getIntent().getStringExtra("glong");
        this.Glat = getIntent().getStringExtra("glat");
        this.Kunci = getIntent().getStringExtra("kunci");
        setTitle(this.Nama);
        this.Halaman_Rapat = Integer.valueOf(1);
        if (this.Role.equals("admin")) {
            this.ll_dashboard.setVisibility(0);
            this.fab_dashboard.setVisibility(0);
            this.fab_dashboard.setClickable(true);
        } else {
            this.ll_dashboard.setVisibility(4);
            this.fab_dashboard.setVisibility(4);
            this.fab_dashboard.setClickable(false);
        }
        startService(new Intent(this, DprdAlarmService.class));
    }

    public void KlikHome(View view) {
        Intent inlanjut = new Intent(getApplicationContext(), LoginActivity.class);
        inlanjut.putExtra("imei", this.Imei);
        inlanjut.putExtra("barisperhalaman", this.BarisPerHalaman);
        inlanjut.putExtra("hp", this.f55Hp);
        inlanjut.putExtra("nama", this.Nama);
        inlanjut.putExtra("role", this.Role);
        inlanjut.putExtra("glong", this.Glong);
        inlanjut.putExtra("glat", this.Glat);
        inlanjut.putExtra("kunci", this.Kunci);
        startActivity(inlanjut);
        finish();
    }

    public void KlikRapat(View view) {
        Intent inlanjut = new Intent(getApplicationContext(), RapatActivity.class);
        inlanjut.putExtra("imei", this.Imei);
        inlanjut.putExtra("barisperhalaman", this.BarisPerHalaman);
        inlanjut.putExtra("hp", this.f55Hp);
        inlanjut.putExtra("nama", this.Nama);
        inlanjut.putExtra("role", this.Role);
        inlanjut.putExtra("glong", this.Glong);
        inlanjut.putExtra("glat", this.Glat);
        inlanjut.putExtra("tanggal", "");
        inlanjut.putExtra("kunci", this.Kunci);
        startActivity(inlanjut);
    }

    public void KlikPerda(View view) {
        Intent inlanjut = new Intent(getApplicationContext(), EdokumenActivity.class);
        inlanjut.putExtra("imei", this.Imei);
        inlanjut.putExtra("barisperhalaman", this.BarisPerHalaman);
        inlanjut.putExtra("hp", this.f55Hp);
        inlanjut.putExtra("nama", this.Nama);
        inlanjut.putExtra("role", this.Role);
        inlanjut.putExtra("glong", this.Glong);
        inlanjut.putExtra("glat", this.Glat);
        inlanjut.putExtra("kunci", this.Kunci);
        startActivity(inlanjut);
    }

    public void KlikProfile(View view) {
        Intent inlanjut = new Intent(getApplicationContext(), ProfileActivity.class);
        inlanjut.putExtra("imei", this.Imei);
        inlanjut.putExtra("barisperhalaman", this.BarisPerHalaman);
        inlanjut.putExtra("hp", this.f55Hp);
        inlanjut.putExtra("nama", this.Nama);
        inlanjut.putExtra("role", this.Role);
        inlanjut.putExtra("glong", this.Glong);
        inlanjut.putExtra("glat", this.Glat);
        inlanjut.putExtra("kunci", this.Kunci);
        startActivity(inlanjut);
    }

    public void KlikInfo(View view) {
        Intent inlanjut = new Intent(getApplicationContext(), InformasiActivity.class);
        inlanjut.putExtra("imei", this.Imei);
        inlanjut.putExtra("barisperhalaman", this.BarisPerHalaman);
        inlanjut.putExtra("hp", this.f55Hp);
        inlanjut.putExtra("nama", this.Nama);
        inlanjut.putExtra("role", this.Role);
        inlanjut.putExtra("glong", this.Glong);
        inlanjut.putExtra("glat", this.Glat);
        inlanjut.putExtra("kunci", this.Kunci);
        startActivity(inlanjut);
    }

    public void KlikChat(View view) {
        Intent inlanjut = new Intent(getApplicationContext(), ChattutamaActivity.class);
        inlanjut.putExtra("imei", this.Imei);
        inlanjut.putExtra("barisperhalaman", this.BarisPerHalaman);
        inlanjut.putExtra("hp", this.f55Hp);
        inlanjut.putExtra("nama", this.Nama);
        inlanjut.putExtra("role", this.Role);
        inlanjut.putExtra("glong", this.Glong);
        inlanjut.putExtra("glat", this.Glat);
        inlanjut.putExtra("kunci", this.Kunci);
        startActivity(inlanjut);
    }

    public void KlikCalendar(View view) {
        Intent inlanjut = new Intent(getApplicationContext(), CalendarActivity.class);
        inlanjut.putExtra("imei", this.Imei);
        inlanjut.putExtra("barisperhalaman", this.BarisPerHalaman);
        inlanjut.putExtra("hp", this.f55Hp);
        inlanjut.putExtra("nama", this.Nama);
        inlanjut.putExtra("role", this.Role);
        inlanjut.putExtra("glong", this.Glong);
        inlanjut.putExtra("glat", this.Glat);
        inlanjut.putExtra("kunci", this.Kunci);
        startActivity(inlanjut);
    }

    private void Updateposisi() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int heighty = displaymetrics.heightPixels;
        int widthx = displaymetrics.widthPixels;
        this.ybagi3 = Math.round((float) (heighty / 3));
        if (this.ybagi3 > 290) {
            this.ybagi3 = 290;
        }
        this.homex = 10;
        this.homey = 10;
        this.rapatx = Math.round((float) (widthx / 3)) + 10;
        this.rapaty = 10;
        this.calendarx = (Math.round((float) (widthx / 3)) * 2) + 10;
        this.calendary = 10;
        this.perdax = 10;
        this.perday = this.ybagi3 + 10;
        this.chatx = Math.round((float) (widthx / 3)) + 10;
        this.chaty = this.ybagi3 + 10;
        this.profilex = (Math.round((float) (widthx / 3)) * 2) + 10;
        this.profiley = this.ybagi3 + 10;
        this.infox = 10;
        this.infoy = (this.ybagi3 * 2) + 10;
        this.dashboardx = Math.round((float) (widthx / 3)) + 10;
        this.dashboardy = (this.ybagi3 * 2) + 10;
        this.ll_home.setX((float) this.homex);
        this.ll_home.setY((float) this.homey);
        this.ll_rapat.setX((float) this.rapatx);
        this.ll_rapat.setY((float) this.rapaty);
        this.ll_calendar.setX((float) this.calendarx);
        this.ll_calendar.setY((float) this.calendary);
        this.ll_perda.setX((float) this.perdax);
        this.ll_perda.setY((float) this.perday);
        this.ll_chat.setX((float) this.chatx);
        this.ll_chat.setY((float) this.chaty);
        this.ll_profile.setX((float) this.profilex);
        this.ll_profile.setY((float) this.profiley);
        this.ll_info.setX((float) this.infox);
        this.ll_info.setY((float) this.infoy);
        this.ll_dashboard.setX((float) this.dashboardx);
        this.ll_dashboard.setY((float) this.dashboardy);
    }

    public void KlikDashboard(View view) {
        Intent inlanjut = new Intent(getApplicationContext(), DashboardActivity.class);
        inlanjut.putExtra("imei", this.Imei);
        inlanjut.putExtra("barisperhalaman", this.BarisPerHalaman);
        inlanjut.putExtra("hp", this.f55Hp);
        inlanjut.putExtra("nama", this.Nama);
        inlanjut.putExtra("role", this.Role);
        inlanjut.putExtra("glong", this.Glong);
        inlanjut.putExtra("glat", this.Glat);
        inlanjut.putExtra("kunci", this.Kunci);
        startActivity(inlanjut);
    }

}