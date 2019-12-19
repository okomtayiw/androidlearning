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

import androidx.cardview.widget.CardView;
import dprdjepara.multisolusi.info.dprdjepara.R;
import java.util.List;

public class ListAdapterInformasi extends BaseAdapter {
    private Context context;
    private List<Informasi> list, filterd;

    public ListAdapterInformasi(Context context, List<Informasi> list) {
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
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.list_row_informasi, null);
        }
        Informasi rpt = filterd.get(position);
        LinearLayout LL_row_informasi =(LinearLayout) convertView.findViewById(R.id.LL_row_informasi);

//        LL_row_informasi.setBackgroundColor(Color.parseColor(String.valueOf(R.color.appGrey2)));
        //if (dateobj.getDate()==rpt.getTanggal()){
        //LL_row_rapat.setBackgroundColor(Color.parseColor("#ffff0000"));
        //}

        TextView textTanggal = (TextView) convertView.findViewById(R.id.text_row_rpt_tanggal);
        textTanggal.setText(rpt.getTanggal());


        TextView textNama_rapat = (TextView) convertView.findViewById(R.id.text_row_rpt_keterangan);
        textNama_rapat.setText(rpt.getKeterangan());

        return convertView;
    }
}
