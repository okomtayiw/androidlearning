package dprdjepara.multisolusi.info.dprdjepara;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import dprdjepara.multisolusi.info.dprdjepara.model.Edokumen;
import dprdjepara.multisolusi.info.dprdjepara.model.ListAdapterEdokumen;
import dprdjepara.multisolusi.info.dprdjepara.model.Edokumen;
import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;


public class EdokumenActivity extends Activity {
	private static final String TAG = "EdokumenActivity";
	private ListView listView;
	private ActionMode actionMode;
	private ActionMode.Callback amCallback;
	private ProgressDialog progressDialog;
	private ServerRequest serverRequest;
	private ListAdapterEdokumen adapter;
	private Edokumen selectedList;
	private List<Edokumen> list;
	//private List<HalamanEdokumen> listJumlahBarisEdokumen;
	private Integer Halaman_Edokumen,Jumlah_baris_Maksimal;
	private String Imei,BarisPerHalaman,Nama,Username,Password,Hp,Role,responsejenis,Kunci;
		TelephonyManager tel;
	private SearchView searchView;

	private RelativeLayout BackPer;

	//private String txtcari;
	private TextView tv_judul_undangan;
	private String fileName,fileType;
	private ServerRequest server;
	private EditText et_cari;
	private Spinner sp_kategori;
	private static ArrayAdapter<String> adapterKategori;
	private static String listkategori[] ={"Undang Undang","Peraturan Pemerintah","Peraturan Daerah","Peraturan Menteri",
			"Keputusan Menteri","Keputusan Bupati",
			"Peraturan Bupati","Peraturan Lainnya" };
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_edokumen);
        serverRequest = new ServerRequest();
        listView = (ListView) findViewById(R.id.listview_edokumen);

        BackPer = (RelativeLayout) findViewById(R.id.backImage_perda);

        BackPer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		et_cari = (EditText) findViewById(R.id.et_cari);
		et_cari.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if ( (actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))){
					new EdokumenActivityAsync().execute("Baru");
					return true;
				}
				else{
					return false;
				}
			}
		});

		Nama = getIntent().getStringExtra("nama");
		BarisPerHalaman = getIntent().getStringExtra("barisperhalaman");
		Username = getIntent().getStringExtra("username");
		Password = getIntent().getStringExtra("password");
		Hp = getIntent().getStringExtra("hp");
		Role = getIntent().getStringExtra("role");
		Imei = getIntent().getStringExtra("imei");
		Kunci = getIntent().getStringExtra("kunci");
		//txtcari="";
		Halaman_Edokumen=0;

		server = new ServerRequest();

        list = new ArrayList<Edokumen>();
        /** melakukan load data melalui AsyncTask */

        //LinearLayout rootLayout = (LinearLayout) findViewById(R.id.LLEdokumen);
        listView.setOnScrollListener(new OnScrollListener() {
        	private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            //private LinearLayout lBelow;


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                this.currentScrollState = scrollState;
                this.isScrollCompleted();               
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;
                
            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE
                       //&& Jumlah_baris_Maksimal > totalItem
                       ) {
                 /** To do code here*/
                	if (Jumlah_baris_Maksimal > totalItem) {
                	new EdokumenActivityAsync().execute("Tambah");
                	}
            }
            }
        });
		sp_kategori = (Spinner) findViewById(R.id.sp_kategori);
		adapterKategori = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listkategori);
		adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_kategori.setAdapter(adapterKategori);



	}
	


    private List<Edokumen> processResponse(String response,String statuslist){
    	if (statuslist=="Baru") {list.clear();}
    	
		try {
			JSONObject jsonObj = new JSONObject(response);
			JSONArray jsonArray = jsonObj.getJSONArray("edokumen");
			Log.d(TAG, "data lengt: "+jsonArray.length());
			Edokumen rpt = null;
			//if (jsonArray.length()>0){
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject obj = jsonArray.getJSONObject(i);
				rpt = new Edokumen();
				rpt.setId(obj.getString("id"));
				rpt.setNomor(obj.getString("nomor"));
				rpt.setTahun(obj.getString("tahun"));
				rpt.setTentang(obj.getString("tentang").trim());
				rpt.setFilename(obj.getString("file_name"));
				rpt.setFiletype(obj.getString("file_type"));
				rpt.setMd5(obj.getString("md5"));
				list.add(rpt);
			}
		} catch (JSONException e) {
			Log.d(TAG, e.getMessage());
		}
		return list;
	}
    
    private void populateListView(){
    	//textHalamanEdokumen.setText("Halaman "+Halaman_Edokumen+" / "+Jumlah_baris_Maksimal);
    	adapter = new ListAdapterEdokumen(getApplicationContext(), list);
    	listView.setAdapter(adapter);
    	listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View v, int pos, long id) {
				if(actionMode != null){
					return false;
				}
				actionMode = startActionMode(amCallback);
				v.setSelected(true);
				selectedList = (Edokumen) adapter.getItem(pos);
				return true;
			}
			
		});
    	
    	 
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int pos,
					long id) {
				selectedList = (Edokumen) adapter.getItem(pos);
				/*
				Intent in = new Intent(getApplicationContext(), PreviewActivity.class);
				in.putExtra("id", selectedList.getId().toString());
				in.putExtra("filename", selectedList.getFilename().toString());
				in.putExtra("jenispreview", "edokumen");

				in.putExtra("imei", Imei);
				in.putExtra("role", Role);
		    	in.putExtra("barisperhalaman", BarisPerHalaman);
		    	//in.putExtra("responsejenis", responsejenis);
				//in.putExtra("responsewarga", responsewarga);
				startActivity(in);
				*/

				if (shouldAskPermissions()) {
					askPermissions();
				}


				fileType = selectedList.getFiletype().toString();
				fileType=fileType.substring(fileType.length()-4,fileType.length()-1);
				String urlnya,namafilenya;
				File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "DPRDJepara");if (!f.exists()) {f.mkdirs();}
				File f1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara", "Dokumen");if (!f1.exists()) {f1.mkdirs();}
				File f2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara", "Perda");if (!f2.exists()) {f2.mkdirs();}

				urlnya=server.serverUri+ server.urlDisplayedokumen+"&id=" + selectedList.getId().toString()+Kunci;
				namafilenya=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/DPRDJepara/Perda/"+
						selectedList.getId().toString()+"_"+selectedList.getFilename().toString();

				if (new File(namafilenya).exists()) {
					String md5FromFile = serverRequest.getMD5Checksum(namafilenya);
					if (md5FromFile.equals(selectedList.getMd5())){
						tampilfile(namafilenya);
						return;
					}
				}
				progressDialog = new ProgressDialog(EdokumenActivity.this);
				progressDialog.setMessage("Downloading...");
				progressDialog.setIndeterminate(true);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressDialog.setCancelable(true);

				final DownloadFile downloadTask = new DownloadFile(EdokumenActivity.this);
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
private class EdokumenActivityAsync extends AsyncTask<String, Void, String>{

    	@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(EdokumenActivity.this);
			progressDialog.setMessage("Ambil Data...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			Cek_Jumlah_Baris_Edokumen();
			/** Mengirimkan request ke server dan memproses JSON response */ 
			if (params[0]=="Baru"){
				Halaman_Edokumen=0;
			}
			Halaman_Edokumen=Halaman_Edokumen+1;
			String response;
			int mulaibaris =Integer.parseInt(BarisPerHalaman) * (Halaman_Edokumen-1);
			//int jmlbaris =Integer.parseInt(BarisPerHalaman) * Halaman_Edokumen;

			//response = serverRequest.sendGetRequest(ServerRequest.urlSelectEdokumen+"?mulaibaris="+String.valueOf(mulaibaris)+"&jmlbaris="+String.valueOf(BarisPerHalaman)+"&cari="+txtcari);
			response = serverRequest.sendGetRequestR(ServerRequest.urlListedokumen+"&mulaibaris="+String.valueOf(mulaibaris)+"&jmlbaris="+String.valueOf(BarisPerHalaman)+"&cari="+et_cari.getText().toString()+"&kategori="+sp_kategori.getSelectedItem().toString()+ Kunci);
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

	private void Cek_Jumlah_Baris_Edokumen(){


		//String response = serverRequest.sendGetRequest(ServerRequest.urlJumlahBarisEdokumen+"?cari="+txtcari);
		String response = serverRequest.sendGetRequestR(ServerRequest.urlJumlahbarisedokumen+"&cari="+et_cari.getText().toString()+Kunci);
		try {
			JSONObject jsonObj = new JSONObject(response);
			JSONArray jsonArray = jsonObj.getJSONArray("halamanedokumen");
			Log.d(TAG, "data lengt: "+jsonArray.length());
			//HalamanEdokumen hjul = null;
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject obj = jsonArray.getJSONObject(i);
				//Jumlah_baris_Maksimal =(int) Math.floor( obj.getInt("jumlahbarisedokumen")/Integer.parseInt(BarisPerHalaman))+1;
				Jumlah_baris_Maksimal =obj.getInt("jumlahbarisedokumen");
			}
		} catch (JSONException e) {
			Log.d(TAG, e.getMessage());
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
	public void cariperaturan(View view){
		new EdokumenActivityAsync().execute("Baru");
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
}