package dprdjepara.multisolusi.info.dprdjepara;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import dprdjepara.multisolusi.info.dprdjepara.model.Informasi;
import dprdjepara.multisolusi.info.dprdjepara.model.ListAdapterInformasi;
import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InformasiActivity extends Activity {
    private static final String TAG = "InformasiActivity";
    /* access modifiers changed from: private */
    private String BarisPerHalaman, textCari;
    private String Glat;
    private String Glong;
    /* access modifiers changed from: private */
    private Integer Halaman_Rapat;

    /* renamed from: Hp */
    private String f53Hp;
    private String Imei;
    /* access modifiers changed from: private */
    private Integer Jumlah_baris_Maksimal;
    /* access modifiers changed from: private */
    private String Kunci;
    private String Nama;
    private String Password;
    private String Role;
    private String Username;
    private ActionMode actionMode;
    /* access modifiers changed from: private */
    private ListAdapterInformasi adapter;
    private Callback amCallback;
    /* access modifiers changed from: private */
    private EditText et_cari;
    /* access modifiers changed from: private */
    private String fileName;
    /* access modifiers changed from: private */
    private String fileType;
    /* access modifiers changed from: private */
    private List<Informasi> list;
    private ListView listView;
    /* access modifiers changed from: private */
    private ProgressDialog progressDialog;
    private String responsejenis;
    private SearchView searchView;
    /* access modifiers changed from: private */
    private Informasi selectedList;
    /* access modifiers changed from: private */
    private ServerRequest server;
    /* access modifiers changed from: private */
    private ServerRequest serverRequest;
    /* back */
    private RelativeLayout BackIn;




    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private class DownloadFile extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadFile(Context context) {
            this.context = context;
        }
        @Override
        protected String doInBackground(String... strings) {
            fileName = strings[1];  // -> maven.pdf
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }
                int fileLength = connection.getContentLength();
                input = connection.getInputStream();

                output = new FileOutputStream(fileName);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            progressDialog.dismiss();
            if (result != null)
                Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(context, fileName + " downloaded", Toast.LENGTH_SHORT).show();
                tampilfile(fileName);
            }
        }

    }

    private class InformasiActivityAsync extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(InformasiActivity.this);
            progressDialog.setMessage("Ambil Data...");
            //progressDialog.setIndeterminate(false);
            //progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Cek_Jumlah_Baris_Informasi();
            /** Mengirimkan request ke server dan memproses JSON response */
            if (params[0]=="Baru"){
                Halaman_Rapat=0;
            }
            Halaman_Rapat=Halaman_Rapat+1;
            String response;
            int mulaibaris =Integer.parseInt(BarisPerHalaman) * (Halaman_Rapat-1);
            //int jmlbaris =Integer.parseInt(BarisPerHalaman) * Halaman_Rapat;

            //response = serverRequest.sendGetRequest(ServerRequest.urlSelectRapat+"?mulaibaris="+String.valueOf(mulaibaris)+"&jmlbaris="+String.valueOf(BarisPerHalaman)+"&cari="+txtcari);
            response = serverRequest.sendGetRequestR(ServerRequest.urlListinformasi+"&mulaibaris="+String.valueOf(mulaibaris)+"&jmlbaris="+String.valueOf(BarisPerHalaman)+"&cari="+textCari+Kunci);
            list = processResponse(response,params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    populateListView();

                }
            });
        }

    }

    private class RapatFotoActivityAsync extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            if(params[0] == "load"){
                try {
                    Informasi rpt = null;
                    for(int i = 0; i < list.size(); i++){
                        rpt = new Informasi();
                        rpt =list.get(i);
                        //rpt.setFoto(downloadImage(serverRequest.serverUri+"/"+serverRequest.urlViewImage+  rpt.getId_rapat()));
                        list.set(i,rpt);
                    }
                } finally {
                    //Log.d(TAG, "");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    populateListView();
                }
            });
        }

    }

    /* access modifiers changed from: protected */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi);
        this.serverRequest = new ServerRequest();
        this.listView = (ListView) findViewById(R.id.listview_informasi);
        this.Imei = getIntent().getStringExtra("imei");
        this.Nama = getIntent().getStringExtra("nama");
        this.BarisPerHalaman = getIntent().getStringExtra("barisperhalaman");
        this.Username = getIntent().getStringExtra("username");
        this.Password = getIntent().getStringExtra("password");
        this.f53Hp = getIntent().getStringExtra("hp");
        this.Role = getIntent().getStringExtra("role");
        this.Kunci = getIntent().getStringExtra("kunci");
        this.et_cari = (EditText) findViewById(R.id.et_cari);
        this.BackIn  = (RelativeLayout) findViewById(R.id.backImage_informasi);
        this.Glat = getIntent().getStringExtra("glat");
        this.Glong = getIntent().getStringExtra("glong");
        this.Halaman_Rapat = Integer.valueOf(0);
        this.list = new ArrayList();
        textCari = et_cari.getText().toString();

        this.BackIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.et_cari.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId != 6 && (event.getKeyCode() != 66 || event.getAction() != 0)) {
                    return false;
                }
                textCari = et_cari.getText().toString();
                new InformasiActivityAsync().execute(new String[]{"Baru"});
                return true;
            }
        });
        new InformasiActivityAsync().execute(new String[]{"Baru"});
        this.listView.setOnScrollListener(new OnScrollListener() {
            private int currentFirstVisibleItem;
            private int currentScrollState;
            private int currentVisibleItemCount;
            private int totalItem;

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                isScrollCompleted();
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;
            }

            public void isScrollCompleted() {
                if (this.totalItem - this.currentFirstVisibleItem == this.currentVisibleItemCount && this.currentScrollState == 0 && Jumlah_baris_Maksimal.intValue() > this.totalItem) {
                    textCari = et_cari.getText().toString();
                    new InformasiActivityAsync().execute(new String[]{"Tambah"});
                }
            }
        });
    }

    /* access modifiers changed from: private */
    private List<Informasi> processResponse(String response, String statuslist) {
        if (statuslist == "Baru") {
            this.list.clear();
        }
        try {
            JSONArray jsonArray = new JSONObject(response).getJSONArray("informasi");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Informasi rpt = new Informasi();
                rpt.setId(obj.getString("id"));
                rpt.setTanggal(obj.getString("tanggal"));
                rpt.setKeterangan(obj.getString("keterangan"));
                rpt.setFilename(obj.getString("filename"));
                rpt.setFiletype(obj.getString("filetype"));
                rpt.setMd5(obj.getString("md5"));
                this.list.add(rpt);
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return this.list;
    }

    /* access modifiers changed from: private */
    private void populateListView() {
        adapter = new ListAdapterInformasi(getApplicationContext(), this.list);
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
                selectedList = (Informasi) adapter.getItem(pos);
                if (shouldAskPermissions()) {
                    askPermissions();
                }
                fileType = selectedList.getFiletype().toString();
                fileType = fileType.substring(fileType.length() - 4, fileType.length() - 1);
                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "DPRDJepara");
                if (!f.exists()) {
                    f.mkdirs();
                }
                File f1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/DPRDJepara", "Informasi");
                if (!f1.exists()) {
                    f1.mkdirs();
                }
                File f2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/DPRDJepara", "Perda");
                if (!f2.exists()) {
                    f2.mkdirs();
                }


                StringBuilder sb = new StringBuilder();
                StringBuilder append = sb.append(server.serverUri);
                String urlnya = append.append(server.urlDownloadinformasi).append(selectedList.getId().toString()).append(Kunci).toString();
                String namafilenya = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)  + "/DPRDJepara/Informasi/" + selectedList.getId().toString() + "_" + selectedList.getFilename().toString();
                if (new File(namafilenya).exists()) {
                    String md5FromFile = serverRequest.getMD5Checksum(namafilenya);
                    if (md5FromFile.equals(selectedList.getMd5())){
                        tampilfile(namafilenya);
                        return;
                    }
                }
                progressDialog = new ProgressDialog(InformasiActivity.this);
                progressDialog.setMessage("Downloading...");
                progressDialog.setIndeterminate(true);
                progressDialog.setProgressStyle(1);
                progressDialog.setCancelable(true);
                final DownloadFile downloadTask = new DownloadFile(InformasiActivity.this);
                downloadTask.execute(new String[]{urlnya, namafilenya });
                progressDialog.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        downloadTask.cancel(true);
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    private void Cek_Jumlah_Baris_Informasi() {
        try {
            JSONArray jsonArray = new JSONObject(ServerRequest.sendGetRequestR("jumlahbarisinformasi&cari=" + this.et_cari.getText().toString() + this.Kunci)).getJSONArray("halamaninformasi");
            for (int i = 0; i < jsonArray.length(); i++) {
                this.Jumlah_baris_Maksimal = Integer.valueOf(jsonArray.getJSONObject(i).getInt(ServerRequest.urlJumlahbarisinformasi));
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        Options bmOptions = new Options();
        bmOptions.inSampleSize = 1;
        try {
            InputStream stream = getHttpConnection(url);
            bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
            stream.close();
            return bitmap;
        } catch (IOException e1) {
            e1.printStackTrace();
            return bitmap;
        }
    }

    private InputStream getHttpConnection(String urlString) throws IOException {
        try {
            HttpURLConnection httpConnection = (HttpURLConnection) new URL(urlString).openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
            if (httpConnection.getResponseCode() == 200) {
                return httpConnection.getInputStream();
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void cariinformasi(View view) {
        textCari = et_cari.getText().toString();
        new InformasiActivityAsync().execute(new String[]{"Baru"});
    }

    /* access modifiers changed from: protected */
    private boolean shouldAskPermissions() {
        Log.d(TAG," shouldAskPermissions");
        return VERSION.SDK_INT > 22;
    }

    @TargetApi(23)
    protected void askPermissions() {
        Log.d(TAG," askPermissions");
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    /* access modifiers changed from: private */
    private void tampilfile(String filenya) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setAction("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        File pdfFile = new File(filenya);
        Uri path = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", pdfFile);
        //Uri path = Uri.fromFile(pdfFile);
        intent.setDataAndType(path , MimeTypeMap.getSingleton().getMimeTypeFromExtension(pdfFile.getName().substring(pdfFile.getName().indexOf(".") + 1)));

        try{
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(InformasiActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }

    }

}