package dprdjepara.multisolusi.info.dprdjepara;

/**
 * Created by User on 03/12/2016.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dprdjepara.multisolusi.info.dprdjepara.model.AdapterCalendar;
import dprdjepara.multisolusi.info.dprdjepara.model.ListAdapterRapat;
import dprdjepara.multisolusi.info.dprdjepara.model.Rapat;
import dprdjepara.multisolusi.info.dprdjepara.model.ServerRequest;

import static android.content.ContentValues.TAG;

public class CalendarActivity  extends Activity {
    private ListView listView;
    private ProgressDialog progressDialog;
    private ServerRequest serverRequest;
    private ListAdapterRapat adapterrapat;
    private Rapat selectedList;
    private List<Rapat> list;
    //private List<HalamanRapat> listJumlahBarisRapat;
    private Integer Halaman_Rapat,Jumlah_baris_Maksimal;
    private String Imei,BarisPerHalaman,Nama,Username,Password,Hp,Role,responsejenis,Glat,Glong,Kunci;
    private String tglcari,ResponseListtgl;
    private String versi="DPRD.Jepara "+BuildConfig.VERSION_NAME;

    public GregorianCalendar month, itemmonth;// calendar instances.

    public AdapterCalendar adapter;// adapter instance
    public Handler handler;// for grabbing some event values for showing the dot
    // marker.
    public ArrayList<String> items; // container to store calendar items which
    // needs showing the event marker

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Locale.setDefault( Locale.US );
        month = (GregorianCalendar) GregorianCalendar.getInstance();
        itemmonth = (GregorianCalendar) month.clone();

        items = new ArrayList<String>();
        adapter = new AdapterCalendar(this, month);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(adapter);
        handler = new Handler();
        new ListTglAsync().execute("Baru");




        TextView title = (TextView) findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                ((AdapterCalendar) parent.getAdapter()).setSelected(v);
                String selectedGridDate = AdapterCalendar.dayString
                        .get(position);
                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*",
                        "");// taking last part of date. ie; 2 from 2012-12-02.
                int gridvalue = Integer.parseInt(gridvalueString);
                // navigate to next or previous month on clicking offdays.
                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }
                ((AdapterCalendar) parent.getAdapter()).setSelected(v);

                showToast(selectedGridDate);
                tglcari=selectedGridDate;
                new RapatActivityAsync().execute("Baru");

            }
        });

        serverRequest = new ServerRequest();
        listView = (ListView) findViewById(R.id.listview_rapat);
        Imei = getIntent().getStringExtra("imei");
        Nama = getIntent().getStringExtra("nama");
        BarisPerHalaman = getIntent().getStringExtra("barisperhalaman");
        Username = getIntent().getStringExtra("username");
        Password = getIntent().getStringExtra("password");
        Hp = getIntent().getStringExtra("hp");
        Role = getIntent().getStringExtra("role");
        Glong = getIntent().getStringExtra("glong");
        Glat = getIntent().getStringExtra("glat");
        Imei = getIntent().getStringExtra("imei");
        Kunci = getIntent().getStringExtra("kunci");
        Halaman_Rapat=0;
        list = new ArrayList<Rapat>();
        Jumlah_baris_Maksimal=100;
    }

    protected void setNextMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) + 1),
                    month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }

    }

    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }

    public void refreshCalendar() {
        TextView title = (TextView) findViewById(R.id.title);

        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items

        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }

    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            items.clear();

            // Print dates of the current week
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            String itemvalue;
            for (int i = 0; i < 7; i++) {
                itemvalue = df.format(itemmonth.getTime());
                itemmonth.add(GregorianCalendar.DATE, 1);
                try {
                    JSONObject jsonObj = new JSONObject(ResponseListtgl);
                    JSONArray jsonArray = jsonObj.getJSONArray("listtgl");
                    for(int j = 0; j < jsonArray.length(); j++){
                        JSONObject obj = jsonArray.getJSONObject(j);
                        //rpt.setTanggal(obj.getString("tanggal"));
                        items.add(obj.getString("tanggal"));
                    }
                } catch (JSONException e) {
                    Log.d(TAG, e.getMessage());
                }

            }
            items.add("2015-01-01");
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    };
    private class RapatActivityAsync extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(CalendarActivity.this);
            //progressDialog.setMessage("Ambil Data...");
            //progressDialog.setIndeterminate(false);
            //progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //Cek_Jumlah_Baris_Rapat();
            /** Mengirimkan request ke server dan memproses JSON response */
            if (params[0]=="Baru"){
                Halaman_Rapat=0;
            }
            Halaman_Rapat=Halaman_Rapat+1;
            String response;
            int mulaibaris =Integer.parseInt(BarisPerHalaman) * (Halaman_Rapat-1);
            //int jmlbaris =Integer.parseInt(BarisPerHalaman) * Halaman_Rapat;

            //response = serverRequest.sendGetRequest(ServerRequest.urlSelectRapat+"?mulaibaris="+String.valueOf(mulaibaris)+"&jmlbaris="+String.valueOf(BarisPerHalaman)+"&cari="+txtcari);
            response = serverRequest.sendGetRequestR(ServerRequest.urlListrapat2+"&mulaibaris="+String.valueOf(mulaibaris)+"&jmlbaris="+String.valueOf(BarisPerHalaman)+"&cari="+tglcari+Kunci);
            //ResponseListtgl =serverRequest.sendGetRequestR(ServerRequest.urlListtgl+"&versi="+versi+"&imei="+Imei+"&lat="+Glat + "&long="+Glong);
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
    private void populateListView(){
        //textHalamanRapat.setText("Halaman "+Halaman_Rapat+" / "+Jumlah_baris_Maksimal);
        adapterrapat = new ListAdapterRapat(getApplicationContext(), list);
        listView.setAdapter(adapterrapat);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int pos,
                                    long id) {
                selectedList = (Rapat) adapterrapat.getItem(pos);
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
                //in.putExtra("responsejenis", responsejenis);
                //in.putExtra("responsewarga", responsewarga);
                startActivity(in);
            }
        });

    }
    private List<Rapat> processResponse(String response,String statuslist){
        //List<Rapat> list = new ArrayList<Rapat>();
        if (statuslist=="Baru") {list.clear();}

        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONArray jsonArray = jsonObj.getJSONArray("listtgl");
            Log.d(TAG, "data lengt: "+jsonArray.length());
            Rapat rpt = null;
            if (jsonArray.length()>0){
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
            }}
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return list;
    }
    private class ListTglAsync extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(CalendarActivity.this);
            //progressDialog.setMessage("Ambil Data...");
            //progressDialog.setIndeterminate(false);
            //progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //Cek_Jumlah_Baris_Rapat();
            /** Mengirimkan request ke server dan memproses JSON response */
            ResponseListtgl =serverRequest.sendGetRequestR(ServerRequest.urlListtgl+Kunci);
            //list = processResponse(response,params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //populateListView();
                    handler.post(calendarUpdater);



                }
            });
        }

    }
}
