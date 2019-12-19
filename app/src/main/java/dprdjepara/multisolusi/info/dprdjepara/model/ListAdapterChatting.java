package dprdjepara.multisolusi.info.dprdjepara.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import dprdjepara.multisolusi.info.dprdjepara.R;
import java.util.List;

public class ListAdapterChatting extends BaseAdapter {
    private Context context;
    private List<Chatting>  filterd;
    private ServerRequest serverRequest;

    public ListAdapterChatting(Context context2, List<Chatting> list2) {
        this.context = context2;
        this.filterd = list2;
    }

    public int getCount() {
        return filterd.size();
    }

    public Object getItem(int position) {
        return this.filterd.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.list_row_chatting, null);
        }
        Chatting chatting = filterd.get(position);
        LinearLayout ll_row_chatting =(LinearLayout) convertView.findViewById(R.id.ll_row_chatting);
		/*if (chatting.getId()==0) { // Header kasih Bold
			ll_row_chatting.setBackgroundColor(Color.parseColor("#1976D2"));
		} else if (chatting.getId()%2==0) {
			ll_row_chatting.setBackgroundColor(Color.parseColor("#903A89CF"));
		} else {ll_row_chatting.setBackgroundColor(Color.parseColor("#FFD8DAF3"));}
		*/
        ImageView iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
        serverRequest = new ServerRequest();
        Glide.with(context).load(serverRequest.serverUri+ serverRequest.urlDisplayaccount+"&id="+chatting.getId())
                .thumbnail(0.5f)
                //.crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_logo);

        TextView tv_nama = (TextView) convertView.findViewById(R.id.tv_nama);
        tv_nama.setText(chatting.getNama());
        //tv_nama.setTextColor(Color.BLUE);

        TextView tv_tanggal = (TextView) convertView.findViewById(R.id.tv_tanggal);
        tv_tanggal.setText(chatting.getTime_stamp());
        //tv_tanggal.setTextColor(Color.BLUE);

        TextView tv_pesan = (TextView) convertView.findViewById(R.id.tv_pesan);
        tv_pesan.setText(chatting.getPesan());

        return convertView;

    }

}
