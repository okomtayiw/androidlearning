package dprdjepara.multisolusi.info.dprdjepara;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dprdjepara.multisolusi.info.dprdjepara.viewcontroller.dashboard.DashboardFragment;
import dprdjepara.multisolusi.info.dprdjepara.viewcontroller.home.HomeFragment;
import dprdjepara.multisolusi.info.dprdjepara.viewcontroller.notifications.NotificationsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigation;

    private String BarisPerHalaman;
    private String Glat;
    private String Glong;




    private String f55Hp;
    private String Imei;

    private String Kunci;
    private String Nama;
    private String Password;
    private String Role;
    private String Username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        loadFragment(new HomeFragment());

        navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);



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


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_chat:
                Intent inChat = new Intent(getApplicationContext(), ChattutamaActivity.class);
                inChat.putExtra("imei", this.Imei);
                inChat.putExtra("barisperhalaman", this.BarisPerHalaman);
                inChat.putExtra("hp", this.f55Hp);
                inChat.putExtra("nama", this.Nama);
                inChat.putExtra("role", this.Role);
                inChat.putExtra("glong", this.Glong);
                inChat.putExtra("glat", this.Glat);
                inChat.putExtra("kunci", this.Kunci);
                startActivity(inChat);
                break;

            case R.id.navigation_dashboard:
                Intent iDasboard = new Intent(getApplicationContext(), DashboardActivity.class);
                iDasboard.putExtra("imei", this.Imei);
                iDasboard.putExtra("barisperhalaman", this.BarisPerHalaman);
                iDasboard.putExtra("hp", this.f55Hp);
                iDasboard.putExtra("nama", this.Nama);
                iDasboard.putExtra("role", this.Role);
                iDasboard.putExtra("glong", this.Glong);
                iDasboard.putExtra("glat", this.Glat);
                iDasboard.putExtra("kunci", this.Kunci);
                startActivity(iDasboard);
                break;

            case R.id.navigation_profile:
                Intent inlanjut = new Intent(getApplicationContext(),ProfileActivity.class);
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

        return loadFragment(fragment);


    }


    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
