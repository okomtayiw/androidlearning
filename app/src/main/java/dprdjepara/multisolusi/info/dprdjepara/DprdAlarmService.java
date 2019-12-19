package dprdjepara.multisolusi.info.dprdjepara;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;

import static dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest.serverUri;

public class DprdAlarmService extends Service 
{
	private NotificationManager mManager;
	private static final int NOTIFY_ME_ID=1337;
	//private TelephonyManager tel;
	private  static String TAG = "ServerRequest";
	private ServerRequest serverRequest;
	private JSONArray jsonArray;
	private JSONObject obj;

	//private static String TAG = DprdAlarmService.class.getSimpleName();
	private MyThread mythread;
	private String Imei;
	public boolean isRunning = false;
	private String Kunci;
	private int st_update;
	private String namafilenya;
	private TelephonyManager tel;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		st_update=0;
		serverRequest = new ServerRequest();
		mythread  = new MyThread();
	}
	@Override
	public synchronized void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
		if(!isRunning){
			mythread.interrupt();
			//mythread.stop();
		}
	}
	@Override
	public synchronized void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d(TAG, "onStart");
		if(!isRunning){
			mythread.start();
			isRunning = true;
		}
	}


	public void readWebPage(){
		StringBuilder stringBuilder = new StringBuilder();
		String response_str;
		response_str="";
		serverRequest = new ServerRequest();
		Imei=serverRequest.getUniqueIMEIId(getBaseContext());
		String versi="DPRD.Jepara "+BuildConfig.VERSION_NAME;
		String stringLat ="";
		String stringLong ="";

		GPSTracker gps = new GPSTracker(this);
		if(gps.canGetLocation()) { // gps enabled} // return boolean true/false
			stringLat=String.valueOf(gps.getLatitude()); // returns latitude
			stringLong=String.valueOf(gps.getLongitude()); // returns longitude
		}
		Kunci="&versi="+versi+"&imei="+Imei+"&lat="+stringLat + "&long="+stringLong;
		try {
			response_str = serverRequest.sendGetRequestR(ServerRequest.urlNoti+Kunci);

		} catch (Exception e) {
			Log.d(TAG, e.getMessage());
		}

		//response_str=stringBuilder.toString();
		try {
			if(!response_str.equalsIgnoreCase("")){
				Log.d(TAG, "Got Response");
				JSONObject jsonObj = new JSONObject(response_str);
				JSONArray jsonArray = jsonObj.getJSONArray("rapat");
				for(int i = 0; i < jsonArray.length(); i++){
					JSONObject obj = jsonArray.getJSONObject(i);
					Kirim_notification(obj.getString("id_rapat"), obj.getString("tanggal"), obj.getString("jam_mulai"), 					obj.getString("jam_selesai"), obj.getString("nama_rapat"), obj.getString("jenis_rapat"),
							obj.getString("sub_jenis_rapat"), obj.getString("sifat"), obj.getString("topik"), 					obj.getString("peserta"), obj.getString("tempat"), obj.getString("kategori"),obj.getString("pimpinan_rapat"),
							obj.getString("moderator"), obj.getString("narasumber"), obj.getString("catatan_moderator"),
							obj.getString("hasil"), obj.getString("keterangan"),Imei);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	class MyThread extends Thread{
		static final long DELAY = 100000;
		@Override
		public void run(){
			while(isRunning){
				Log.d(TAG,"Running");
				try {
					readWebPage();
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					isRunning = false;
					e.printStackTrace();
				}
			}
		}
	}



	@SuppressWarnings("deprecation")
	private void Kirim_notification(String id_rapat,String tanggal,String jam_mulai,String jam_selesai,String nama_rapat,String jenis_rapat,
									String sub_jenis_rapat,String sifat,String topik,String peserta,String tempat,String kategori,String pimpinan_rapat,
									String moderator,String narasumber,String catatan_moderator,String hasil,String keterangan,String imei){
		/*********** Create notification ***********/
		final NotificationManager mgr=
				(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);


		//Notification note=new Notification(R.drawable.ic_launcher ,"DPRD Jepara",System.currentTimeMillis());

		// This pending intent will open after notification click
		Intent in = new Intent(this, DetailRapat.class);
		in.putExtra("id_rapat", id_rapat);
		in.putExtra("tanggal", tanggal);
		in.putExtra("jam_mulai", jam_mulai);
		in.putExtra("jam_selesai", jam_selesai);
		in.putExtra("nama_rapat", nama_rapat);
		in.putExtra("jenis_rapat", jenis_rapat);
		in.putExtra("sub_jenis_rapat", sub_jenis_rapat);
		in.putExtra("sifat", sifat);
		in.putExtra("topik", topik);
		in.putExtra("peserta", peserta);
		in.putExtra("tempat", tempat);
		in.putExtra("kategori", kategori);
		in.putExtra("pimpinan_rapat", pimpinan_rapat);
		in.putExtra("moderator", moderator);
		in.putExtra("narasumber", narasumber);
		in.putExtra("catatan_moderator", catatan_moderator);
		in.putExtra("hasil", hasil);
		in.putExtra("keterangan", keterangan);
		in.putExtra("imei", imei);
		in.putExtra("kunci", Kunci);
		PendingIntent pendingi=PendingIntent.getActivity(this, 0,
				in,
				PendingIntent.FLAG_UPDATE_CURRENT);

		//note.setLatestEventInfo(this, "DPRD Jepara",tanggal+" pukul "+jam_mulai+" "+nama_rapat, pendingi);
		Notification.Builder builder = new Notification.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("DPRD Jepara")
				.setContentText(nama_rapat+", "+topik)
				.setContentIntent(pendingi);
		Notification note = builder.getNotification();
		//After uncomment this line you will see number of notification arrived
		//note.number=2;
		mgr.notify(NOTIFY_ME_ID+Integer.parseInt(id_rapat), note);
		//mgr.notify(0, note);
	}

}