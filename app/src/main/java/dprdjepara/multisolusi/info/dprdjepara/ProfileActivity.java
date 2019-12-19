package dprdjepara.multisolusi.info.dprdjepara;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;


public class ProfileActivity extends Activity {
	private static final String TAG = "EdokumenActivity";
	private ProgressDialog progressDialog;
	private ServerRequest serverRequest;
	private String Imei,BarisPerHalaman,Role,Kunci;
		//TelephonyManager tel;
	private String id,nama,tgl_lahir,alamat,nip,ktp,satker,partai,komisi,fraksi,alat_kelengkapan,jabatan,telp,hp,email,keterangan,barisperhalaman2;
	private EditText et_id,et_nama,et_tgl_lahir,et_alamat,et_nip,et_ktp,et_satker,et_partai,et_komisi,et_fraksi,et_alat_kelengkapan,et_jabatan,et_telp,et_hp,et_email,et_keterangan,et_barisperhalaman;
	private String response;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		serverRequest = new ServerRequest();
		et_id = (EditText) findViewById(R.id.et_id);
		et_nama = (EditText) findViewById(R.id.et_nama);
		et_tgl_lahir = (EditText) findViewById(R.id.et_tgl_lahir);
		et_alamat = (EditText) findViewById(R.id.et_alamat);
		et_nip = (EditText) findViewById(R.id.et_nip);
		et_ktp = (EditText) findViewById(R.id.et_ktp);
		et_satker = (EditText) findViewById(R.id.et_satker);
		et_partai = (EditText) findViewById(R.id.et_partai);
		et_komisi = (EditText) findViewById(R.id.et_komisi);
		et_fraksi = (EditText) findViewById(R.id.et_fraksi);
		et_alat_kelengkapan = (EditText) findViewById(R.id.et_alat_kelengkapan);
		et_jabatan = (EditText) findViewById(R.id.et_jabatan);
		et_telp = (EditText) findViewById(R.id.et_telp);
		et_hp = (EditText) findViewById(R.id.et_hp);
		et_email = (EditText) findViewById(R.id.et_email);
		et_keterangan = (EditText) findViewById(R.id.et_keterangan);
		et_barisperhalaman = (EditText) findViewById(R.id.et_barisperhalaman);

		et_id.setEnabled(false);
		et_barisperhalaman.setEnabled(false);
/*Nama = getIntent().getStringExtra("nama");
		Username = getIntent().getStringExtra("username");
		Password = getIntent().getStringExtra("password");
		Hp = getIntent().getStringExtra("hp");
		*/
		BarisPerHalaman = getIntent().getStringExtra("barisperhalaman");
		Role = getIntent().getStringExtra("role");
		Imei = getIntent().getStringExtra("imei");
		Kunci = getIntent().getStringExtra("kunci");

		new DataProfileAsync().execute("");
	}
	private class DataProfileAsync extends AsyncTask<String, Void, String>{
    	@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(ProfileActivity.this);
			//progressDialog.setMessage("Ambil Data...");
			//progressDialog.setIndeterminate(false);
			//progressDialog.setCancelable(false);
			progressDialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			response = serverRequest.sendGetRequestR(ServerRequest.urlDisplayprofile+"&id="+Imei+Kunci);
			try {
				JSONObject jsonObj = new JSONObject(response);
				JSONArray jsonArray = jsonObj.getJSONArray("profile");
				JSONObject obj = jsonArray.getJSONObject(0);

				id = obj.getString("imei");
				nama = obj.getString("nama");
				tgl_lahir = obj.getString("tgl_lahir");
				alamat = obj.getString("alamat");
				nip = obj.getString("nip");
				ktp = obj.getString("ktp");
				satker = obj.getString("satker");
				partai = obj.getString("partai");
				komisi = obj.getString("komisi");
				fraksi = obj.getString("fraksi");
				alat_kelengkapan = obj.getString("alat_kelengkapan_dewan");
				jabatan = obj.getString("jabatan");
				telp = obj.getString("telp");
				hp = obj.getString("hp");
				email = obj.getString("email");
				keterangan = obj.getString("keterangan");
				barisperhalaman2 = obj.getString("barisperhalaman");
			} catch (JSONException e) {
				Log.d(TAG, e.getMessage());
			}
			return null;
		}
		
		@Override
    	protected void onPostExecute(String result) {
    		progressDialog.dismiss();
    		runOnUiThread(new Runnable() {				
				@Override
				public void run() {
					Tampildata();
					
				}
			});
    	}
    	
    }
	private void Tampildata(){
		et_id.setText(id);
		et_nama.setText(nama);
		et_tgl_lahir.setText(tgl_lahir);
		et_alamat.setText(alamat);
		et_nip.setText(nip);
		et_ktp.setText(ktp);
		et_satker.setText(satker);
		et_partai.setText(partai);
		et_komisi.setText(komisi);
		et_fraksi.setText(fraksi);
		et_alat_kelengkapan.setText(alat_kelengkapan);
		et_jabatan.setText(jabatan);
		et_telp.setText(telp);
		et_hp.setText(hp);
		et_email.setText(email);
		et_keterangan.setText(keterangan);
		et_barisperhalaman.setText(barisperhalaman2);
	}
}