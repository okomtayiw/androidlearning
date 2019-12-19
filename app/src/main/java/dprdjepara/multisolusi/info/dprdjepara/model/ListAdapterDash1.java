package dprdjepara.multisolusi.info.dprdjepara.model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import dprdjepara.multisolusi.info.dprdjepara.R;

public class ListAdapterDash1 extends BaseAdapter {
    private Context context;
    private List<Dash1> list;

    public ListAdapterDash1(Context context2, List<Dash1> list2) {
        this.context = context2;
        this.list = list2;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return this.list.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        new Date();
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.list_row_dash1, null);
        }
        Dash1 rpt = (Dash1) this.list.get(position);
        LinearLayout ll_row_dash1 = (LinearLayout) convertView.findViewById(R.id.ll_row_dash1);
        if (rpt.getUrut().intValue() == 0) {
            ll_row_dash1.setBackgroundColor(Color.parseColor("#1976D2"));
        } else if (rpt.getUrut().intValue() % 2 == 0) {
            ll_row_dash1.setBackgroundColor(Color.parseColor("#903A89CF"));
        } else {
            ll_row_dash1.setBackgroundColor(Color.parseColor("#FFD8DAF3"));
        }
        ((TextView) convertView.findViewById(R.id.tv_id)).setText(rpt.getId());
        ((TextView) convertView.findViewById(R.id.tv_tanggal)).setText(rpt.getTanggal());
        ((TextView) convertView.findViewById(R.id.tv_nama)).setText(rpt.getNama());
        ((TextView) convertView.findViewById(R.id.tv_ket)).setText(rpt.getKet());
        ((TextView) convertView.findViewById(R.id.tv_imei)).setText(rpt.getImei());
        ((TextView) convertView.findViewById(R.id.tv_glat)).setText(rpt.getGlat());
        ((TextView) convertView.findViewById(R.id.tv_glong)).setText(rpt.getGlong());
        return convertView;
    }
}
