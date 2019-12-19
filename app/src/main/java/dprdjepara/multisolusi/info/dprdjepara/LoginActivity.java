package dprdjepara.multisolusi.info.dprdjepara;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import android.telephony.TelephonyManager;


import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;


public class LoginActivity extends Activity {
    
    //private Button btnDaftar;
    private ServerRequest serverRequest;
    private TextView tv_versi;
    private TextView btnLogin;
	
    private String success,Imei,BarisPerHalaman,Username,Nama,Password,Hp,Role,responsejenis;
    
    private final static String TAG = "ServerRequest";
    
    private String versi="DPRD.Jepara "+BuildConfig.VERSION_NAME;
    private TelephonyManager tel;
    private String stringLat;
    private String stringLong;
    private String Kunci,fileName;
    private ProgressDialog progressDialog;
    private int st_update;
    private DownloadFile downloadTask;
    String urlnya;
    ProgressDialog pDialog;
    private EditText etImei;

    /**
     * Method yang dipanggil pada saat aplikaasi dijalankan
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_versi = (TextView) findViewById(R.id.tv_versi);
        tv_versi.setText(BuildConfig.VERSION_NAME);
        btnLogin = (TextView) findViewById(R.id.btn_login);
        st_update=0;
        //btnDaftar = (Button) findViewById(R.id.btndaftar);
        //txtPesan.setText("Aplikasi ini dibuat untuk membantu dalam pemberian informasi Rapat maupun kegiatan di dalam DPRD Jepara. Sehingga para anggota DPRD dapat terbantu dalam mengelola waktunya."+"\n" +"\n"+"Regards,");


        stringLat ="";
        stringLong ="";
        if (shouldAskPermissions()) {
            askPermissions();
        }
        GPSTracker gps = new GPSTracker(LoginActivity.this);
        if(gps.canGetLocation()) { // gps enabled} // return boolean true/false
            stringLat=String.valueOf(gps.getLatitude()); // returns latitude
            stringLong=String.valueOf(gps.getLongitude()); // returns longitude
        }
        serverRequest = new ServerRequest();
//        Imei = getUniqueIMEIId(getApplicationContext());

        Imei = "355037106718656";



        //String imei = android.os.Build.SERIAL+android.os.Build.DEVICE+android.os.Build.MODEL;//UUID.randomUUID().toString();; //tel.getDeviceId().toString();
        Kunci="&versi="+versi+"&imei="+Imei+"&lat="+stringLat + "&long="+stringLong;
        urlnya=serverRequest.serverUri+ serverRequest.urlDownloadapk +Kunci;

        //downloadTask = new DownloadFile(this);

        btnLogin.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                success="2";
                new Masuk().execute();

            }
        });


    }
    public static String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            String imei = telephonyManager.getDeviceId();
            Log.e("imei", "=" + imei);
            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return android.os.Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "not_found";
    }

    public class Masuk extends AsyncTask<String, String, String>
    {
        ProgressDialog pDialog;
 
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
 
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Mohon Menunggu ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
        	//tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            Kunci="&versi="+versi+"&imei="+Imei+"&lat="+stringLat + "&long="+stringLong;
            Log.d("ServerRequest -> ",Kunci);
            String response = serverRequest.sendGetRequestR(ServerRequest.urlLogin+Kunci);
        	try {
                Log.d("ServerRequest2 -> ",response);
    			JSONObject jsonObj = new JSONObject(response);
    			JSONArray jsonArray = jsonObj.getJSONArray("login");
    			Log.d(TAG, "data lengt: "+jsonArray.length());
    			for(int i = 0; i < jsonArray.length(); i++){
    				JSONObject obj = jsonArray.getJSONObject(i);
    				success = obj.getString("succes");
    				if (success.equals("1")) {
    				  //Imei=obj.getString("imei");
    				  BarisPerHalaman =obj.getString("barisperhalaman");
    				  //Username =obj.getString("username");
    				  //Password =obj.getString("password");
    				  Hp =obj.getString("hp");
    				  Nama =obj.getString("nama");
    				  Role = obj.getString("role");
    				  
    				}
    			}
                if (Integer.parseInt(jsonObj.getString("versi")) > BuildConfig.VERSION_CODE) st_update=1;

    		} catch (JSONException e) {
    			Log.d(TAG, e.getMessage());
    		}
        	if (success.equals("1")) {
        		//responsejenis = serverRequest.sendGetRequest(serverRequest.urlSelectJenis);
        		//if (Level.equals("1")) {
        		//	responsewarga = serverRequest.sendGetRequest(serverRequest.urlSelectWarga+"?userid=%");
        		//} else
        		//{
        		//	responsewarga = serverRequest.sendGetRequest(serverRequest.urlSelectWarga+"?userid="+UserId);
        		//}
        	
        	}
        	return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //super.onPostExecute(result);
            pDialog.dismiss();
//            runOnUiThread(new Runnable() {				
	//			@Override
	//			public void run() {
	//				//populateListView();
	//				//textHalamanJurnal.setText(textHalamanJurnal.getText() +ServerRequest.urlSelectJurnal+"?userid="+UserId+"&barisperhalaman="+BarisPerHalaman+"&awalbaris="+ (Halaman_Jurnal-1)*Integer.parseInt(BarisPerHalaman));
	//			
            if (st_update==1) {
                new DownloadFile().execute();
            } else
            if (success.equals("1")) {
            	//Intent inrapat = new Intent(getApplicationContext(), RapatActivity.class);
            	Intent inlanjut = new Intent(getApplicationContext(), MainActivity.class);
            	inlanjut.putExtra("imei", Imei);
                inlanjut.putExtra("kunci", Kunci);
            	inlanjut.putExtra("barisperhalaman", BarisPerHalaman);
            	//inrapat.putExtra("username", Username);
            	//inrapat.putExtra("password", Password);
            	inlanjut.putExtra("hp", Hp);
            	inlanjut.putExtra("nama", Nama);
            	inlanjut.putExtra("role", Role);
                inlanjut.putExtra("glong", stringLong);
                inlanjut.putExtra("glat", stringLat);
            	//inlanjut.putExtra("responsejenis", responsejenis);
            	startActivity(inlanjut);
                finish();
            }
            else if (success.equals("0")) {
            	Toast.makeText(getApplicationContext(), "Kode Ponsel belum terdaftar ...", Toast.LENGTH_LONG).show();
            	//tampilfile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara/Apk/app-debug.apk");
            	
            }
            else {
 
            	Toast.makeText(getApplicationContext(), "Tidak bisa komunikasi dengan server... ", Toast.LENGTH_LONG).show();
            }
			//	}
			//});
        }
 
    }
    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_NETWORK_STATE",
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.INSTALL_PACKAGES",
                "android.permission.READ_PHONE_STATE",
                "android.permission.WAKE_LOCK",
                "android.permission.INTERNET"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }
    public class DownloadFile extends AsyncTask<String, String, String>
    {


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Download update...");
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            //tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "DPRDJepara");if (!f.exists()) {f.mkdirs();}
            File f1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara", "Apk");if (!f1.exists()) {f1.mkdirs();}
            fileName=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara/Apk/app-debug.apk";

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                //URL url = new URL(params[0]);
                URL url = new URL(urlnya);
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
                    //if (fileLength > 0) // only if total length is known
                        //publishProgress((int) (total * 100 / fileLength));
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
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //super.onPostExecute(result);
            pDialog.dismiss();
            tampilfile(fileName);
        }

    }
    private void tampilfile(String filenya) {
        String myPackageName = getApplicationContext().getPackageName();
        File f = new File(filenya);
        Uri mediaUri = FileProvider.getUriForFile(getApplicationContext(), myPackageName + ".provider", f);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(mediaUri, "application/vnd.android.package-archive");
        startActivity(intent);



        /*Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File file = new File(filenya);

        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = file.getName().substring(file.getName().indexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);

        Uri path = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file);
        intent.setDataAndType(path, type);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

         */
    }
}