package dprdjepara.multisolusi.info.dprdjepara.viewcontroller.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import androidx.fragment.app.Fragment;
import dprdjepara.multisolusi.info.dprdjepara.CalendarActivity;
import dprdjepara.multisolusi.info.dprdjepara.EdokumenActivity;
import dprdjepara.multisolusi.info.dprdjepara.InformasiActivity;
import dprdjepara.multisolusi.info.dprdjepara.R;
import dprdjepara.multisolusi.info.dprdjepara.RapatActivity;

public class HomeFragment extends Fragment {


    String [] sampleImages = new String[]{
            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_3.jpg",
            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_1.jpg",
            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_2.jpg"
    };


    private String BarisPerHalaman;
    private String Glat;
    private String Glong;
    private String f55Hp;
    private String Imei;
    private String Kunci;
    private String Nama;
    private String Password;
    private String Role;
    private String Username;


    CarouselView carouselView;
    LinearLayout lEvent,lCalender,lInformasi,lPerda;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        carouselView = (CarouselView) view.findViewById(R.id.carouselView);

        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);


        lEvent = (LinearLayout) view.findViewById(R.id.home_icon_event_linearLayout);
        lCalender = (LinearLayout) view.findViewById(R.id.icon_calender_homeLinearLayout);
        lInformasi = (LinearLayout) view.findViewById(R.id.icon_information_homeLinearLayout);
        lPerda = (LinearLayout) view.findViewById(R.id.icon_perda_homeLinearLayout);


        this.Imei = getActivity().getIntent().getStringExtra("imei");
        this.Nama = getActivity().getIntent().getStringExtra("nama");
        this.BarisPerHalaman = getActivity().getIntent().getStringExtra("barisperhalaman");
        this.Username = getActivity().getIntent().getStringExtra("username");
        this.Password = getActivity().getIntent().getStringExtra("password");
        this.f55Hp = getActivity().getIntent().getStringExtra("hp");
        this.Role = getActivity().getIntent().getStringExtra("role");
        this.Glong = getActivity().getIntent().getStringExtra("glong");
        this.Glat = getActivity().getIntent().getStringExtra("glat");
        this.Kunci = getActivity().getIntent().getStringExtra("kunci");



        lEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Event",Toast.LENGTH_SHORT).show();
                Intent inlanjut = new Intent(getActivity(), RapatActivity.class);
                inlanjut.putExtra("imei", Imei);
                inlanjut.putExtra("barisperhalaman", BarisPerHalaman);
                inlanjut.putExtra("hp", f55Hp);
                inlanjut.putExtra("nama", Nama);
                inlanjut.putExtra("role", Role);
                inlanjut.putExtra("glong", Glong);
                inlanjut.putExtra("glat", Glat);
                inlanjut.putExtra("tanggal", "");
                inlanjut.putExtra("kunci", Kunci);
                startActivity(inlanjut);
            }
        });


        lCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inlanjut = new Intent(getActivity(), CalendarActivity.class);
                inlanjut.putExtra("imei", Imei);
                inlanjut.putExtra("barisperhalaman", BarisPerHalaman);
                inlanjut.putExtra("hp", f55Hp);
                inlanjut.putExtra("nama", Nama);
                inlanjut.putExtra("role", Role);
                inlanjut.putExtra("glong", Glong);
                inlanjut.putExtra("glat", Glat);
                inlanjut.putExtra("kunci", Kunci);
                startActivity(inlanjut);
            }
        });



        lInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inlanjut = new Intent(getActivity(), InformasiActivity.class);
                inlanjut.putExtra("imei", Imei);
                inlanjut.putExtra("barisperhalaman", BarisPerHalaman);
                inlanjut.putExtra("hp", f55Hp);
                inlanjut.putExtra("nama", Nama);
                inlanjut.putExtra("role", Role);
                inlanjut.putExtra("glong", Glong);
                inlanjut.putExtra("glat", Glat);
                inlanjut.putExtra("kunci",Kunci);
                startActivity(inlanjut);
                Toast.makeText(getActivity(),"Informasi",Toast.LENGTH_SHORT).show();
            }
        });


        lPerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inlanjut = new Intent(getActivity(), EdokumenActivity.class);
                inlanjut.putExtra("imei", Imei);
                inlanjut.putExtra("barisperhalaman", BarisPerHalaman);
                inlanjut.putExtra("hp", f55Hp);
                inlanjut.putExtra("nama", Nama);
                inlanjut.putExtra("role", Role);
                inlanjut.putExtra("glong", Glong);
                inlanjut.putExtra("glat", Glat);
                inlanjut.putExtra("kunci", Kunci);
                startActivity(inlanjut);
                Toast.makeText(getActivity(), "Perda", Toast.LENGTH_LONG).show();
            }
        });





        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            Glide.with(getActivity())
                    .load(sampleImages[position])
                    .into(imageView);

        }
    };
}