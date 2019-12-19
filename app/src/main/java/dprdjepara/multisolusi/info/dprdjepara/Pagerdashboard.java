package dprdjepara.multisolusi.info.dprdjepara;


import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class Pagerdashboard extends FragmentStatePagerAdapter {
    String Kunci;
    String ResponseUrl;
    int tabCount;

    public Pagerdashboard(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Tabdash1 tab1 = new Tabdash1();
                tab1.setResponseUrl(this.ResponseUrl, this.Kunci);
                return tab1;
            case 1:
                Tabdash2 tab2 = new Tabdash2();
                tab2.setResponseUrl(this.ResponseUrl, this.Kunci);
                return tab2;
            case 2:
                Tabdash3 tab3 = new Tabdash3();
                tab3.setResponseUrl(this.ResponseUrl, this.Kunci);
                return tab3;
            case 3:
                Tabdash4 tab4 = new Tabdash4();
                tab4.setResponseUrl(ResponseUrl, Kunci);
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public void setResponseUrl(String ResponseUrl,String Kunci)
    {
        this.ResponseUrl =ResponseUrl;
        this.Kunci =Kunci;
    }
}
