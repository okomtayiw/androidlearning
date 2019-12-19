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

public class ListAdapterEdokumen extends BaseAdapter implements Filterable{
	private Context context;
	private List<Edokumen> list, filterd;

	//ImageView imageFoto;

	public ListAdapterEdokumen(Context context, List<Edokumen> list) {
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
			convertView = inflater.inflate(R.layout.list_row_edokumen, null);
		}
		Edokumen rpt = filterd.get(position);
		//LinearLayout LL_row_edokumen =(LinearLayout) convertView.findViewById(R.id.LL_row_edokumen);
		//if (dateobj.getDate()==rpt.getTanggal()){
		//LL_row_edokumen.setBackgroundColor(Color.parseColor("#ffff0000"));
		//}

		TextView textTentang = (TextView) convertView.findViewById(R.id.tv_tentang);
		textTentang.setText(rpt.getTentang());

		TextView textNomor = (TextView) convertView.findViewById(R.id.tv_nomor);
		textNomor.setText("Nomor "+rpt.getNomor()+", Tahun "+rpt.getTahun()+", "+rpt.getKategori());

		TextView textFilename = (TextView) convertView.findViewById(R.id.tv_filename);
		textFilename.setText(rpt.getFilename());

		return convertView;
	}

	
		
		 
		
	@Override
	public Filter getFilter() {
		EdokumenFilter filter = new EdokumenFilter();
		return filter;
	}
	
	
	/** Class filter untuk melakukan filter (pencarian) */
	private class EdokumenFilter extends Filter{
			
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			List<Edokumen> filteredData = new ArrayList<Edokumen>();
			FilterResults result = new FilterResults();
			String filterString = constraint.toString().toLowerCase();
			for(Edokumen rpt: list){
				if(rpt.getTentang().toLowerCase().contains(filterString) ){
					filteredData.add(rpt);
				}
			}
			result.count = filteredData.size();
			result.values =  filteredData;
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			filterd = (List<Edokumen>) results.values;
			notifyDataSetChanged();
		}

	}
	

}