package dprdjepara.multisolusi.info.dprdjepara.model;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import dprdjepara.multisolusi.info.dprdjepara.R;

public class ListAdapterRapat extends BaseAdapter implements Filterable{
	private Context context;
	private List<Rapat> list, filterd;
	
	//ImageView imageFoto;
	
	public ListAdapterRapat(Context context, List<Rapat> list) {
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
			convertView = inflater.inflate(R.layout.list_row_rapat, null);
		}
		Rapat rpt = filterd.get(position);
		LinearLayout LL_row_rapat =(LinearLayout) convertView.findViewById(R.id.LL_row_rapat);
		//if (dateobj.getDate()==rpt.getTanggal()){
		//LL_row_rapat.setBackgroundColor(Color.parseColor("#ffff0000"));
		//}

		TextView textTanggal = (TextView) convertView.findViewById(R.id.text_row_rpt_tanggal);
		textTanggal.setText(rpt.getTanggal()+" "+rpt.getJam_mulai()+" s/d "+rpt.getJam_selesai());
		
		
		TextView textNama_rapat = (TextView) convertView.findViewById(R.id.text_row_rpt_nama_rapat);
		textNama_rapat.setText(rpt.getNama_rapat());
		//textNama_rapat.setTextSize(20);
		//textNama_rapat.setTextColor(Color.BLUE);
		
		//ImageView imageFoto = (ImageView) convertView.findViewById(R.id.image_row_rpt_foto);
		TextView textNama_isi = (TextView) convertView.findViewById(R.id.text_row_rpt_isi);
		textNama_isi.setText(rpt.getTopik());

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
			List<Rapat> filteredData = new ArrayList<Rapat>(); 
			FilterResults result = new FilterResults();
			String filterString = constraint.toString().toLowerCase();
			for(Rapat rpt: list){
				if(rpt.getNama_rapat().toLowerCase().contains(filterString) ){
					filteredData.add(rpt);
				}
			}
			result.count = filteredData.size();
			result.values =  filteredData;
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			filterd = (List<Rapat>) results.values;
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