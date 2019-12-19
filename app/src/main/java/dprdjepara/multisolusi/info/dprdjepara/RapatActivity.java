package dprdjepara.multisolusi.info.dprdjepara;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import dprdjepara.multisolusi.info.dprdjepara.model.ListAdapterRapat;
import dprdjepara.multisolusi.info.dprdjepara.model.Rapat;
import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;


public class RapatActivity extends Activity {
	private static final String TAG = "RapatActivity";
	private ListView listView;
	private ActionMode actionMode;
	private ActionMode.Callback amCallback;
	private ProgressDialog progressDialog;
	private ServerRequest serverRequest;
	private ListAdapterRapat adapter;
	private Rapat selectedList;
	private List<Rapat> list;
	//private List<HalamanRapat> listJumlahBarisRapat;
	private Integer Halaman_Rapat,Jumlah_baris_Maksimal;
	private String Imei,BarisPerHalaman,Nama,Username,Password,Hp,Role,responsejenis,Kunci,Glat,Glong;
		//TelephonyManager tel;
	private SearchView searchView;

	//private String txtcari;
	private EditText et_cari;
	private RelativeLayout BackR;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_rapat);
        serverRequest = new ServerRequest();
        listView = (ListView) findViewById(R.id.listview_rapat);
		Imei = getIntent().getStringExtra("imei");
        Nama = getIntent().getStringExtra("nama");
		BarisPerHalaman = getIntent().getStringExtra("barisperhalaman");
		Username = getIntent().getStringExtra("username");
		Password = getIntent().getStringExtra("password");
		Hp = getIntent().getStringExtra("hp");
		Role = getIntent().getStringExtra("role");
		Kunci = getIntent().getStringExtra("kunci");
		et_cari = (EditText) findViewById(R.id.et_cari);
		BackR = (RelativeLayout) findViewById(R.id.backImage_rapat);
		Glat = getIntent().getStringExtra("glat");
		Glong = getIntent().getStringExtra("glong");


		BackR.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		//txtcari=et_cari.getText().toString();
		Halaman_Rapat=0;
		list = new ArrayList<Rapat>();
		et_cari.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if ( (actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))){
					new RapatActivityAsync().execute("Baru");
					return true;
				}
				else{
					return false;
				}
			}
		});


		new RapatActivityAsync().execute("Baru");



        listView.setOnScrollListener(new OnScrollListener() {
        	private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            //private LinearLayout lBelow;


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();               
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
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
                	new RapatActivityAsync().execute("Tambah");
                	}
            }
            }
        });


    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_rapat, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.option_menu_search).getActionView();      
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        OnQueryTextListener queryTextListener = new OnQueryTextListener() {
        @Override
    	public boolean onQueryTextChange(String newText) {
    		//adapter.getFilter().filter(newText);
    		return true;
    	}
        @Override
    	public boolean onQueryTextSubmit(String query) {
        	//txtcari=query;
        	new RapatActivityAsync().execute("Baru");
            
        	return true;
    	}
        };
        searchView.setQueryHint("Cari...");
        //return true;
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	 case R.id.option_menu_search:
             // search action
             return true;
    	case R.id.option_menu_kalender:
    		Intent inkalender = new Intent(getApplicationContext(), KalenderActivity.class);
    		inkalender.putExtra("barisperhalaman", BarisPerHalaman);
    		//inkalender.putExtra("responsejenis", responsejenis);
    		inkalender.putExtra("role", Role);
    		inkalender.putExtra("nama", Nama);
    		inkalender.putExtra("imei", Imei);
    		startActivity(inkalender);
    		break;
    	case R.id.option_menu_tentang:
    		Intent intentang = new Intent(getApplicationContext(), TentangActivity.class);
    		intentang.putExtra("imei", Imei);
    		intentang.putExtra("barisperhalaman", BarisPerHalaman);
    		startActivity(intentang);
    		break;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    
    private List<Rapat> processResponse(String response,String statuslist){
    	//List<Rapat> list = new ArrayList<Rapat>();
    	if (statuslist=="Baru") {list.clear();}
    	
		try {
			JSONObject jsonObj = new JSONObject(response);
			JSONArray jsonArray = jsonObj.getJSONArray("rapat");
			Log.d(TAG, "data lengt: "+jsonArray.length());
			Rapat rpt = null;
			//if (jsonArray.length()>0){
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject obj = jsonArray.getJSONObject(i);
				rpt = new Rapat();
				rpt.setId_rapat(obj.getString("id_rapat"));
				rpt.setTanggal(obj.getString("tanggal"));
				rpt.setJam_mulai(obj.getString("jam_mulai"));
				rpt.setJam_selesai(obj.getString("jam_selesai"));
				rpt.setNama_rapat(obj.getString("nama_rapat"));
				rpt.setJenis_rapat(obj.getString("jenis_rapat"));
				rpt.setTopik(obj.getString("topik"));
				rpt.setTempat(obj.getString("tempat"));
				rpt.setKategori(obj.getString("kategori"));
				rpt.setPimpinan_rapat(obj.getString("pimpinan_rapat"));
				rpt.setModerator(obj.getString("moderator"));
				rpt.setNarasumber(obj.getString("narasumber"));
				rpt.setCatatan_moderator(obj.getString("catatan_moderator"));
				rpt.setHasil(obj.getString("hasil"));
				rpt.setKeterangan(obj.getString("keterangan"));
				//rpt.setFoto(downloadImage(serverRequest.serverUri+"/"+serverRequest.urlViewImage+  obj.getString("id")));
				list.add(rpt);
			}
		} catch (JSONException e) {
			Log.d(TAG, e.getMessage());
		}
		return list;
	}
    
    private void populateListView(){
    	//textHalamanRapat.setText("Halaman "+Halaman_Rapat+" / "+Jumlah_baris_Maksimal);
    	adapter = new ListAdapterRapat(getApplicationContext(), list);
    	listView.setAdapter(adapter);
    	listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View v, int pos, long id) {
				if(actionMode != null){
					return false;
				}
				actionMode = startActionMode(amCallback);
				v.setSelected(true);
				selectedList = (Rapat) adapter.getItem(pos);
				return true;
			}
			
		});
    	
    	 
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int pos,
					long id) {
				selectedList = (Rapat) adapter.getItem(pos);
				Intent in = new Intent(getApplicationContext(), DetailRapat.class);
				in.putExtra("id_rapat", selectedList.getId_rapat().toString());
				in.putExtra("tanggal", selectedList.getTanggal());
				in.putExtra("jam_mulai", selectedList.getJam_mulai());
				in.putExtra("jam_selesai", selectedList.getJam_selesai());
				in.putExtra("nama_rapat", selectedList.getNama_rapat());
				in.putExtra("jenis_rapat", selectedList.getJenis_rapat());
				in.putExtra("topik", selectedList.getTopik());
				in.putExtra("tempat", selectedList.getTempat());
				in.putExtra("kategori", selectedList.getKategori());
				in.putExtra("pimpinan_rapat", selectedList.getPimpinan_rapat());
				in.putExtra("moderator", selectedList.getModerator());
				in.putExtra("narasumber", selectedList.getNarasumber());
				in.putExtra("catatan_moderator", selectedList.getCatatan_moderator());
				in.putExtra("hasil", selectedList.getHasil());
				in.putExtra("keterangan", selectedList.getKeterangan());
				//in.putExtra("foto", selectedList.getFoto());
				
				in.putExtra("imei", Imei);
				in.putExtra("role", Role);
		    	in.putExtra("barisperhalaman", BarisPerHalaman);
				in.putExtra("hp", Hp);
				in.putExtra("nama", Nama);
				in.putExtra("glong", Glong);
				in.putExtra("glat", Glat);
				in.putExtra("kunci", Kunci);

				//in.putExtra("responsewarga", responsewarga);
				startActivity(in);
			}
		});
    	
       }
private class RapatActivityAsync extends AsyncTask<String, Void, String>{

    	@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(RapatActivity.this);
			progressDialog.setMessage("Ambil Data...");
			//progressDialog.setIndeterminate(false);
			//progressDialog.setCancelable(false);
			progressDialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			Cek_Jumlah_Baris_Rapat();
			/** Mengirimkan request ke server dan memproses JSON response */ 
			if (params[0]=="Baru"){
				Halaman_Rapat=0;
			}
			Halaman_Rapat=Halaman_Rapat+1;
			String response;
			int mulaibaris =Integer.parseInt(BarisPerHalaman) * (Halaman_Rapat-1);
			//int jmlbaris =Integer.parseInt(BarisPerHalaman) * Halaman_Rapat;

			//response = serverRequest.sendGetRequest(ServerRequest.urlSelectRapat+"?mulaibaris="+String.valueOf(mulaibaris)+"&jmlbaris="+String.valueOf(BarisPerHalaman)+"&cari="+txtcari);
			response = serverRequest.sendGetRequestR(ServerRequest.urlListrapat+"&mulaibaris="+String.valueOf(mulaibaris)+"&jmlbaris="+String.valueOf(BarisPerHalaman)+"&cari="+et_cari.getText().toString()+Kunci);
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
					Rapat rpt = null;
					for(int i = 0; i < list.size(); i++){
						rpt = new Rapat();
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
					//dxdxd
					//textHalamanRapat.setText(textHalamanRapat.getText() +ServerRequest.urlSelectRapat+"?userid="+UserId+"&barisperhalaman="+BarisPerHalaman+"&awalbaris="+ (Halaman_Rapat-1)*Integer.parseInt(BarisPerHalaman));
				}
			});
    	}
    	
    }
    
	private void Cek_Jumlah_Baris_Rapat(){


		//String response = serverRequest.sendGetRequest(ServerRequest.urlJumlahBarisRapat+"?cari="+txtcari);
		String response = serverRequest.sendGetRequestR(ServerRequest.urlJumlahbarisrapat+"&cari="+et_cari.getText().toString()+Kunci);
		try {
			JSONObject jsonObj = new JSONObject(response);
			JSONArray jsonArray = jsonObj.getJSONArray("halamanrapat");
			Log.d(TAG, "data lengt: "+jsonArray.length());
			//HalamanRapat hjul = null;
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject obj = jsonArray.getJSONObject(i);
				//Jumlah_baris_Maksimal =(int) Math.floor( obj.getInt("jumlahbarisrapat")/Integer.parseInt(BarisPerHalaman))+1;
				Jumlah_baris_Maksimal =obj.getInt("jumlahbarisrapat");
			}
		} catch (JSONException e) {
			Log.d(TAG, e.getMessage());
		}

    }
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
    public void carirapat(View view){
		new RapatActivityAsync().execute("Baru");
	}
    
    
}