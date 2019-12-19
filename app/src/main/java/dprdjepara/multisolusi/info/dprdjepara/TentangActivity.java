package dprdjepara.multisolusi.info.dprdjepara;



import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;


public class TentangActivity extends Activity {
    //private String versi="RC1.1";
    //private String IsiPesan;
    private EditText txtPesan;
    //private static final String AD_UNIT_ID = "ca-app-pub-7722681318294329/7384703692";
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);
        txtPesan = (EditText) findViewById(R.id.et_pesan);
        txtPesan.setText("Aplikasi ini dibuat untuk membantu dalam pemberian informasi Rapat maupun kegiatan di dalam DPRD Jepara. Sehingga para anggota DPRD dapat terbantu dalam mengelola waktunya."+"\n" +"\n"+"Regards,");
    
        
    }

    
}