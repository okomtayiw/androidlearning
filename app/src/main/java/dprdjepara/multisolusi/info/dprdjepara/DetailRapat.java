package dprdjepara.multisolusi.info.dprdjepara;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;




import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import dprdjepara.multisolusi.info.dprdjepara.model.Dokumen;
import dprdjepara.multisolusi.info.dprdjepara.model.ListAdapterDokumen;
import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;
import dprdjepara.multisolusi.info.dprdjepara.ui.HorizontalListView;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class DetailRapat extends Activity {
	private TextView textTanggal, textJam_mulai, textJam_selesai ,textNama_rapat ,textJenis_rapat ,textTopik ,
	textTempat , textKategori , textPimpinan_rapat ,textModerator ,textNarasumber ,
	textCatatan_moderator ,textHasil ,textKeterangan ;
	
	private ImageView imageFoto;
	private Dokumen rapat;
	private ServerRequest server;
	private String Imei,Username,BarisPerHalaman,Role,responsejenis,id_dokumen,Kunci,Nama,Password,Hp,Glat,Glong;
    private TelephonyManager tel;
    private JSONObject obj;
    private LinearLayout LLDetailRapat1,LLDetailRapat2;
    private WebView WVDetailRapat,WVDetailRapatIsi1;
    android.view.ViewGroup.LayoutParams Params_LLDetailRapat1,Params_WVDetailRapatIsi1;
	private List<Dokumen> list;
	private ListAdapterDokumen adapter;
	private Dokumen selectedList;

	//private static final String AD_UNIT_ID = "ca-app-pub-7722681318294329/7384703692";

	private HorizontalListView hlv_dokumen;
	private String response;
	private ProgressDialog progressDialog;
	//private ProgressDialog mProgressDialog ;
	private String fileName,fileType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_rapat);
		Imei = getIntent().getStringExtra("imei");
		//tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		Nama = getIntent().getStringExtra("nama");
		BarisPerHalaman = getIntent().getStringExtra("barisperhalaman");
		Username = getIntent().getStringExtra("username");
		Password = getIntent().getStringExtra("password");
		Hp = getIntent().getStringExtra("hp");
		Role = getIntent().getStringExtra("role");
		Kunci = getIntent().getStringExtra("kunci");
		Glat = getIntent().getStringExtra("glat");
		Glong = getIntent().getStringExtra("glong");

		//responsejenis = getIntent().getStringExtra("responsejenis");
		//setTitle("nama_rapat");
		//rapat = new Rapat();
		server = new ServerRequest();
		list = new ArrayList<Dokumen >();
		//response ="fff";
				initView();

		/** melakukan load data melalui AsyncTask */
		new DokumenActivityAsync().execute("");

		
		
	}
	
	private void initView(){
		
		LLDetailRapat1 = (LinearLayout) findViewById(R.id.LLDetailRapat1);
		LLDetailRapat2 = (LinearLayout) findViewById(R.id.LLDetailRapat2);
		WVDetailRapat = (WebView) findViewById(R.id.WVDetailRapat);
		hlv_dokumen = (HorizontalListView) findViewById(R.id.hlv_dokumen);
		//adapter = new ListAdapterDokumen(getApplicationContext(), list);
		//hlv_dokumen.setAdapter(adapter);

		DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        Params_LLDetailRapat1 = LLDetailRapat1.getLayoutParams();

        //Params_WVDetailRapatIsi1 = hlv_dokumen.getLayoutParams();
        Params_LLDetailRapat1.height = Math.round(height*7/10);
        //Params_WVDetailRapatIsi1.width = Math.round(height*2/10);

        
        // Create WebView
        //WebView WVDetailRapatIsis2 = new WebView(this);
        //WVDetailRapatIsis2.loadUrl(server.urlUndangan+getIntent().getStringExtra("id_rapat") );

        //LLDetailRapat2.addView(WVDetailRapatIsis2);

        //Params_WVDetailRapat.height = height;
        //setTitle(Params_LLDetailRapat1.height);
        //TextView tvAddARoom = new TextView(this);
        //tvAddARoom.setText("Add a Room");
        //tvAddARoom.setLayoutParams(Params_LLDetailRapat2);

        WVDetailRapat.getSettings().setJavaScriptEnabled(true); // enable javascript
		 //WVDetailRapat.getSettings().setPluginsEnabled(true);
		 //final Activity activity = this;
		 //WVDetailRapat.setWebViewClient (new WebViewClient());
		WVDetailRapat.getSettings().setSupportZoom(true);
		WVDetailRapat.getSettings().setBuiltInZoomControls(true);
		WVDetailRapat.getSettings().setDisplayZoomControls(true);
		WVDetailRapat.loadUrl(server.serverUri+ server.urlUndangan+getIntent().getStringExtra("id_rapat")+Kunci );



		//WVDetailRapatIsi1.loadUrl(server.urlDokumenisi+getIntent().getStringExtra("id_rapat") );

		
		//WVDetailRapatIsi1.getSettings().setLoadWithOverviewMode(true);
		//WVDetailRapatIsi1.getSettings().setUseWideViewPort(true);
		//WVDetailRapat.loadUrl("http://192.168.1.3");
		 //setContentView(mWvDroidMu );

    //WebView mWebView=new WebView(MyPdfViewActivity.this);
    //mWebView.getSettings().setJavaScriptEnabled(true);
    //mWebView.getSettings().setPluginsEnabled(true);
    //mWebView.loadUrl("http://jpr01.multisolusi.info/m/dokumen.php?id_dokumen=48");
    //setContentView(mWebView);
//goToUrl ("http://jpr01.multisolusi.info/m/dokumen.php?id_dokumen=48");


	}
	private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
	private Bitmap LoadImage(String URL, BitmapFactory.Options options) {
		Bitmap bitmap = null;
		InputStream in = null;
		try {
		in = OpenHttpConnection(URL);
		bitmap = BitmapFactory.decodeStream(in, null, options);
		in.close();
		} catch (IOException e1) {
		}
		return bitmap;
		}
	private InputStream OpenHttpConnection(String strURL) throws IOException {
		InputStream inputStream = null;
		URL url = new URL(strURL);
		URLConnection conn = url.openConnection();
		 
		try {
		HttpURLConnection httpConn = (HttpURLConnection) conn;
		httpConn.setRequestMethod("GET");
		httpConn.connect();
		 
		if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
		inputStream = httpConn.getInputStream();
		}
		} catch (Exception ex) {
		}
		return inputStream;
		}
		

	
	private void goToMainActivity(){
		Intent in = new Intent(getApplicationContext(), RapatActivity.class);
		in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		in.putExtra("imei", Imei);
    		in.putExtra("barisperhalaman", BarisPerHalaman);

		startActivity(in);
	}
	


	//*** Metode Baru ***
		private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
	        @Override
	        protected Bitmap doInBackground(String... urls) {
	            Bitmap map = null;
	            for (String url : urls) {
	                map = downloadImage(url);
	            }
	            return map;
	        }
	 
	        // Sets the Bitmap returned by doInBackground
	        @Override
	        protected void onPostExecute(Bitmap result) {
	            imageFoto.setImageBitmap(result);
	        }
	 
	        // Creates Bitmap from InputStream and returns it
	        private Bitmap downloadImage(String url) {
	            Bitmap bitmap = null;
	            InputStream stream = null;
	            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	            bmOptions.inSampleSize = 1;
	 
	            try {
	                stream = getHttpConnection(url);
	                bitmap = BitmapFactory.
	                        decodeStream(stream, null, bmOptions);
	                stream.close();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	            return bitmap;
	        }
	 
	    	    // Makes HttpURLConnection and returns InputStream
	        private InputStream getHttpConnection(String urlString)
	                throws IOException {
	            InputStream stream = null;
	            URL url = new URL(urlString);
	            URLConnection connection = url.openConnection();
	 
	            try {
	                HttpURLConnection httpConnection = (HttpURLConnection) connection;
	                httpConnection.setRequestMethod("GET");
	                httpConnection.connect();
	 
	                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	                    stream = httpConnection.getInputStream();
	                }
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	            return stream;
	        }
	    }


	private class DokumenActivityAsync extends AsyncTask<String, Void, String>{

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(DetailRapat.this);
			progressDialog.setMessage("Ambil Data...");
			//progressDialog.setIndeterminate(false);
			//progressDialog.setCancelable(false);
			progressDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {

			response = server.sendGetRequestR(ServerRequest.urlListdokumen+getIntent().getStringExtra("id_rapat"));
			list = processResponse();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					populateListView();
					//TextView tv_test= (TextView) findViewById(R.id.tv_test);
					//tv_test.setText("R"+response+"R:"+ServerRequest.urlListdokumen+getIntent().getStringExtra("id_rapat"));

				}
			});
		}

	}
	private List<Dokumen> processResponse(){
		List<Dokumen> list = new ArrayList<Dokumen>();
		try {
			//JSONObject jsonObj = new JSONObject(response);
			JSONArray jsonArray = new JSONArray(response);
			Log.d(TAG, "data lengt: "+jsonArray.length());
			Dokumen rpt = null;
			//if (jsonArray.length()>0){
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject obj = jsonArray.getJSONObject(i);
				rpt = new Dokumen();
				rpt.setId_dokumen(obj.getString("id_dokumen"));
				rpt.setTanggal(obj.getString("tanggal"));
				rpt.setJudul(obj.getString("judul"));
				rpt.setSkpd(obj.getString("skpd"));
				rpt.setSumber(obj.getString("sumber"));
				rpt.setJenisDokumen(obj.getString("jenis_dokumen"));
				rpt.setFiletype(obj.getString("filetype"));
				rpt.setFilename(obj.getString("filename"));
				rpt.setMd5(obj.getString("md5"));
				list.add(rpt);
			}
		} catch (JSONException e) {
			Log.d(TAG, e.getMessage());
		}
		return list;
	}
	private void populateListView(){


		//textHalamanRapat.setText("Halaman "+Halaman_Rapat+" / "+Jumlah_baris_Maksimal);
		adapter = new ListAdapterDokumen(getApplicationContext(), list);
		hlv_dokumen.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		//TextView tv_test= (TextView) findViewById(R.id.tv_test);
		//tv_test.setText(String.valueOf(list.size()));




		hlv_dokumen.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int pos,
									long id) {
				selectedList = (Dokumen) adapter.getItem(pos);
				if (shouldAskPermissions()) {
					askPermissions();
				}

				fileType = selectedList.getFiletype().toString();
				fileType=fileType.substring(fileType.length()-4,fileType.length()-1);
				String urlnya,namafilenya;
				File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "DPRDJepara");if (!f.exists()) {f.mkdirs();}
				File f1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara", "Dokumen");if (!f1.exists()) {f1.mkdirs();}
				File f2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara", "Perda");if (!f2.exists()) {f2.mkdirs();}

				urlnya=server.serverUri+ server.urlDisplaydokumen + selectedList.getId_dokumen().toString()+Kunci;
				namafilenya=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara/Dokumen/"+
						selectedList.getId_dokumen().toString()+"_"+selectedList.getFilename().toString();
				if (new File(namafilenya).exists()) {
					String md5FromFile = server.getMD5Checksum(namafilenya);
					if (md5FromFile.equals(selectedList.getMd5())){
						tampilfile(namafilenya);
						return;
					}
				}

				progressDialog = new ProgressDialog(DetailRapat.this);
				progressDialog.setMessage("Downloading...");
				progressDialog.setIndeterminate(true);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressDialog.setCancelable(true);

				final DownloadFile downloadTask = new DownloadFile(DetailRapat.this);
				downloadTask.execute(urlnya,namafilenya);

				progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						downloadTask.cancel(true);
					}
				});

			}
		});

	}
	private void tampilfile(String filenya) {
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		File file = new File(filenya);

		MimeTypeMap mime = MimeTypeMap.getSingleton();
		String ext = file.getName().substring(file.getName().indexOf(".") + 1);
		String type = mime.getMimeTypeFromExtension(ext);

		Uri path = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file);
		intent.setDataAndType(path, type);

		startActivity(intent);
	}
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
	protected boolean shouldAskPermissions() {
		return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
	}

	@TargetApi(23)
	protected void askPermissions() {
		String[] permissions = {
				"android.permission.READ_EXTERNAL_STORAGE",
				"android.permission.WRITE_EXTERNAL_STORAGE"
		};
		int requestCode = 200;
		requestPermissions(permissions, requestCode);
	}

}
