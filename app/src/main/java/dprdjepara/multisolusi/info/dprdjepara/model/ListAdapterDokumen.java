package dprdjepara.multisolusi.info.dprdjepara.model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dprdjepara.multisolusi.info.dprdjepara.R;

public class ListAdapterDokumen extends BaseAdapter implements Filterable{
	private Context context;
	private List<Dokumen> list, filterd;

	//ImageView imageFoto;

	public ListAdapterDokumen(Context context, List<Dokumen> list) {
		this.context = context;
		this.list = list;
		this.filterd = this.list;
	}
	
	@Override
	public int getCount() {
		return filterd.size();
	}

	@Override
	public Object getItem(int position) {
		return filterd.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Date dateobj = new Date();
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(this.context);
			convertView = inflater.inflate(R.layout.list_row_dokumen, null);
		}
		Dokumen rpt = filterd.get(position);
		LinearLayout LL_row_dokumen =(LinearLayout) convertView.findViewById(R.id.LL_row_dokumen);
		//if (dateobj.getDate()==rpt.getTanggal()){
		//LL_row_dokumen.setBackgroundColor(Color.parseColor("#ffff0000"));
		//}

		TextView textJudul = (TextView) convertView.findViewById(R.id.tv_judul);
		textJudul.setText(rpt.getJudul());

		TextView textTanggal = (TextView) convertView.findViewById(R.id.tv_tanggal);
		textTanggal.setText(rpt.getJenis_dokumen()+", "+rpt.getTanggal()+", "+rpt.getSkpd()+", "+rpt.getSumber());

		//ImageView imageFoto = (ImageView) convertView.findViewById(R.id.image_row_rpt_foto);
		WebView WVDetailRapatIsi = (WebView) convertView.findViewById(R.id.WVDetailRapatIsi);
		WVDetailRapatIsi.getSettings().setJavaScriptEnabled(true); // enable javascript
		WVDetailRapatIsi.getSettings().setSupportZoom(true);
		WVDetailRapatIsi.getSettings().setBuiltInZoomControls(true);
		ServerRequest server = new ServerRequest();
		WVDetailRapatIsi.loadUrl(server.urlDisplaydokumen+rpt.getId_dokumen());

		// Create an object for subclass of AsyncTask
        //GetXMLTask task = new GetXMLTask();
        // Execute the task
        //task.execute(new String[] { rpt.getFoto() });

        //imageFoto.setImageBitmap(rpt.getFoto());
		return convertView;
	}

	
		
		 
		
	@Override
	public Filter getFilter() {
		JualFilter filter = new JualFilter();
		return filter;
	}
	
	
	/** Class filter untuk melakukan filter (pencarian) */
	private class JualFilter extends Filter{
			
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			List<Dokumen> filteredData = new ArrayList<Dokumen>();
			FilterResults result = new FilterResults();
			String filterString = constraint.toString().toLowerCase();
			for(Dokumen rpt: list){
				if(rpt.getJudul().toLowerCase().contains(filterString) ){
					filteredData.add(rpt);
				}
			}
			result.count = filteredData.size();
			result.values =  filteredData;
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			filterd = (List<Dokumen>) results.values;
			notifyDataSetChanged();
		}

	}
	
	//*** Metode Baru ***
//	private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
//        @Override
//        protected Bitmap doInBackground(String... urls) {
//            Bitmap map = null;
//            for (String url : urls) {
//                map = downloadImage(url);
//            }
//            return map;
//        }
 
        // Sets the Bitmap returned by doInBackground
//        @Override
//        protected void onPostExecute(Bitmap result) {
//            imageFoto.setImageBitmap(result);
//        }
 
//        // Creates Bitmap from InputStream and returns it
//        private Bitmap downloadImage(String url) {
//            Bitmap bitmap = null;
//            InputStream stream = null;
//            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//            bmOptions.inSampleSize = 1;
 
//            try {
//                stream = getHttpConnection(url);
//                bitmap = BitmapFactory.
//                        decodeStream(stream, null, bmOptions);
//                stream.close();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            return bitmap;
//        }
 
        // Makes HttpURLConnection and returns InputStream
//        private InputStream getHttpConnection(String urlString)
//                throws IOException {
//            InputStream stream = null;
//            URL url = new URL(urlString);
//            URLConnection connection = url.openConnection();
 //
//            try {
//                HttpURLConnection httpConnection = (HttpURLConnection) connection;
//                httpConnection.setRequestMethod("GET");
//                httpConnection.connect();
 
//                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    stream = httpConnection.getInputStream();
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            return stream;
//        }
//    }
}