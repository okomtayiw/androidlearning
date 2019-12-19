package dprdjepara.multisolusi.info.dprdjepara.model;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dprdjepara.multisolusi.info.dprdjepara.R;

public class ListAdapterChatt extends BaseAdapter implements Filterable{
	private Context context;
	private List<Chatt> list, filterd;

	//ImageView imageFoto;

	public ListAdapterChatt(Context context, List<Chatt> list) {
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
			convertView = inflater.inflate(R.layout.list_row_chatt, null);
		}
		Chatt rpt = filterd.get(position);
		LinearLayout LL_row_dokumen =(LinearLayout) convertView.findViewById(R.id.LL_row_chatt);
		//if (dateobj.getDate()==rpt.getTanggal()){
		//LL_row_dokumen.setBackgroundColor(Color.parseColor("#ffff0000"));
		//}

		TextView textFrom = (TextView) convertView.findViewById(R.id.tv_from);
		textFrom.setText(rpt.getUsername_from()+
					" ("+rpt.getTime_stamp()+")");

		TextView textPesan = (TextView) convertView.findViewById(R.id.tv_pesan);
		textPesan.setText(rpt.getPesan());

		FrameLayout right_arrow = (FrameLayout) convertView.findViewById(R.id.right_arrow);
		FrameLayout left_arrow= (FrameLayout) convertView.findViewById(R.id.left_arrow);
		if (rpt.getIssender().equals(Boolean.TRUE)) {
			right_arrow.setVisibility(View.VISIBLE);
			left_arrow.setVisibility(View.GONE);
			LL_row_dokumen.setGravity(Gravity.RIGHT);
		} else {
			right_arrow.setVisibility(View.GONE);
			left_arrow.setVisibility(View.VISIBLE);
			LL_row_dokumen.setGravity(Gravity.LEFT);

		}
		return convertView;
	}

	@Override
	public Filter getFilter() {
		ChattFilter filter = new ChattFilter();
		return filter;
	}
	
	
	/** Class filter untuk melakukan filter (pencarian) */
	private class ChattFilter extends Filter{
			
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			List<Chatt> filteredData = new ArrayList<Chatt>();
			FilterResults result = new FilterResults();
			String filterString = constraint.toString().toLowerCase();
			for(Chatt rpt: list){
				if(rpt.getPesan().toLowerCase().contains(filterString) ){
					filteredData.add(rpt);
				}
			}
			result.count = filteredData.size();
			result.values =  filteredData;
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			filterd = (List<Chatt>) results.values;
			notifyDataSetChanged();
		}
	}
}