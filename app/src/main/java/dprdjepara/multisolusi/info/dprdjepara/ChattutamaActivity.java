package dprdjepara.multisolusi.info.dprdjepara;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;


public class ChattutamaActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout_chatt;
    private ViewPager viewPager_chatt;
    private Pagerchatt adapter_chatt_tab;

    protected ProgressDialog progressDialog;
    private String ResponseUrl;
    private ServerRequest serverRequest;
    private String Imei,BarisPerHalaman,Nama,Username,Password,Hp,Role,responsejenis,Kunci,Glat,Glong;

    private ImageView iv_account;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;
    public static final String UPLOAD_KEY = "image";
    private TextView tv_judul;
    private String hslresult;

    //private Pagerchatt adapter_report_tab;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chattutama);
        serverRequest = new ServerRequest();
        Imei = getIntent().getStringExtra("imei");
        Nama = getIntent().getStringExtra("nama");
        BarisPerHalaman = getIntent().getStringExtra("barisperhalaman");
        Username = getIntent().getStringExtra("username");
        Password = getIntent().getStringExtra("password");
        Hp = getIntent().getStringExtra("hp");
        Role = getIntent().getStringExtra("role");
        Kunci = getIntent().getStringExtra("kunci");
        Glat = getIntent().getStringExtra("glat");
        Glong = getIntent().getStringExtra("glong");
        tv_judul = (TextView) findViewById(R.id.tv_judul);
        tv_judul.setText(Nama);
        iv_account = (ImageView) findViewById(R.id.iv_account);
        serverRequest = new ServerRequest();
        //Glide.clear();
        Glide.with(this).load(serverRequest.serverUri+ serverRequest.urlDisplayaccount+"&imei="+Imei)
                .thumbnail(0.5f)
                //.crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_account);
        iv_account.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pilihImage();
            }
        });

        tabLayout_chatt = (TabLayout) findViewById(R.id.tabLayout_chatt);
        tabLayout_chatt.addTab(tabLayout_chatt.newTab().setText("CHATTING"));
        tabLayout_chatt.addTab(tabLayout_chatt.newTab().setText("KONTAK"));
        tabLayout_chatt.setTabGravity(TabLayout.GRAVITY_FILL);

        ///Initializing viewPager
        viewPager_chatt = (ViewPager) findViewById(R.id.viewpager_chatt);

        //Creating our pager adapter
        Pagerchatt adapter = new Pagerchatt(getSupportFragmentManager(), tabLayout_chatt.getTabCount());

        //Adding adapter to pager
        viewPager_chatt.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        //adapter_chatt_tab = new Pagerchatt (getSupportFragmentManager(), tabLayout_chatt.getTabCount());
        //viewPager_chatt.setAdapter(adapter_chatt_tab);
        tabLayout_chatt.setOnTabSelectedListener(this);
        new ChattutamaActivityAsync().execute("");
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager_chatt.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    private class ChattutamaActivityAsync extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progressDialog = new ProgressDialog(this);
            progressDialog = new ProgressDialog(ChattutamaActivity.this);
            //progressDialog.setMessage("Ambil Data...");
            //progressDialog.setIndeterminate(false);
            //progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            /** Mengirimkan request ke server dan memproses JSON response */
            ResponseUrl= serverRequest.sendGetRequestR(ServerRequest.urlChattawal+Kunci);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    populate();
                }
            });
        }

    }

    public boolean populate() {
        adapter_chatt_tab = new Pagerchatt (getSupportFragmentManager(), tabLayout_chatt.getTabCount());
        adapter_chatt_tab.setResponseUrl(ResponseUrl,Kunci);
        viewPager_chatt.setAdapter(adapter_chatt_tab);
        adapter_chatt_tab.notifyDataSetChanged();

        try {
            //viewPager_report.setAdapter(adapter_report_tab);
            //adapter_report_tab.notifyDataSetChanged();
        } catch (Exception e) {
            //Log.d(TAG, e.getMessage());
            //stringBuilder.append("0");
        }

        return true;
    }
    private void pilihImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                iv_account.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        uploadImage();
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void uploadImage() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            ProgressDialog loading;
            ServerRequest server = new ServerRequest();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ChattutamaActivity.this, "Uploading Image", "Please wait...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                //tv_judul.setText(hslresult);
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String, String> data = new HashMap<>();
                data.put("imei",Imei);
                data.put(UPLOAD_KEY, uploadImage);

                //String result = server.sendPostRequest(server.urlUpdatelogoaccount, data);
                String result = server.sendPostRequest(server.urlUpdatelogoaccount,data);
                hslresult=result;

                return result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
}
