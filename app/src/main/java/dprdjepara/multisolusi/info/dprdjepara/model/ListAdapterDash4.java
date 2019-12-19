package dprdjepara.multisolusi.info.dprdjepara.model;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import dprdjepara.multisolusi.info.dprdjepara.R;


public class ListAdapterDash4 extends BaseAdapter {
    private Context context;
    private List<Dash4>  list;

    //ImageView imageFoto;

    public ListAdapterDash4(Context context, List<Dash4> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("deprecation")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.list_row_dash4, null);
        }
        Dash4 rpt = list.get(position);
        LinearLayout ll_row_dash4 =(LinearLayout) convertView.findViewById(R.id.ll_row_dash4);
        if (rpt.getUrut()==0) { // Header kasih Bold
            ll_row_dash4.setBackgroundColor(Color.parseColor("#1976D2"));
        } else if (rpt.getUrut()%2==0) {
            ll_row_dash4.setBackgroundColor(Color.parseColor("#903A89CF"));
        } else {ll_row_dash4.setBackgroundColor(Color.parseColor("#FFD8DAF3"));}


        TextView tv_id = (TextView) convertView.findViewById(R.id.tv_id);
        tv_id.setText(rpt.getId());
        TextView tv_tanggal = (TextView) convertView.findViewById(R.id.tv_tanggal);
        tv_tanggal.setText(rpt.getTanggal());
        TextView tv_nama = (TextView) convertView.findViewById(R.id.tv_nama);
        tv_nama.setText(rpt.getNama());
        TextView tv_namarapat = (TextView) convertView.findViewById(R.id.tv_namarapat);
        tv_namarapat.setText(rpt.getNamarapat());

        TextView tv_topik = (TextView) convertView.findViewById(R.id.tv_topik);
        tv_topik.setText(rpt.getTopik());

        return convertView;
    }
}
