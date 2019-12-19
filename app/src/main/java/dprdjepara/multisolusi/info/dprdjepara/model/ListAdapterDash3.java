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

public class ListAdapterDash3 extends BaseAdapter {
    private Context context;
    private List<Dash3> list;

    public ListAdapterDash3(Context context2, List<Dash3> list2) {
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
            convertView = LayoutInflater.from(this.context).inflate(R.layout.list_row_dash3, null);
        }
        Dash3 rpt = (Dash3) this.list.get(position);
        LinearLayout ll_row_dash3 = (LinearLayout) convertView.findViewById(R.id.ll_row_dash3);
        if (rpt.getUrut().intValue() == 0) {
            ll_row_dash3.setBackgroundColor(Color.parseColor("#1976D2"));
        } else if (rpt.getUrut().intValue() % 2 == 0) {
            ll_row_dash3.setBackgroundColor(Color.parseColor("#903A89CF"));
        } else {
            ll_row_dash3.setBackgroundColor(Color.parseColor("#FFD8DAF3"));
        }
        ((TextView) convertView.findViewById(R.id.tv_id)).setText(rpt.getId());
        ((TextView) convertView.findViewById(R.id.tv_destinationnumber)).setText(rpt.getDestinationnumber());
        ((TextView) convertView.findViewById(R.id.tv_textdecoded)).setText(rpt.getTextdecoded());
        ((TextView) convertView.findViewById(R.id.tv_receivingdatetime)).setText(rpt.getSendingdatetime());
        return convertView;
    }
}
