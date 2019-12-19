package dprdjepara.multisolusi.info.dprdjepara;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class Pagerchatt extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;
    String ResponseUrl,Kunci;


    //Constructor to the class
    public Pagerchatt(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs

        switch (position) {
            case 0:
                Tabchatt1 tab1 = new Tabchatt1();
                tab1.setResponseUrl(this.ResponseUrl,this.Kunci);
                return tab1;
            case 1:
                Tabchatt2 tab2 = new Tabchatt2();
                tab2.setResponseUrl(this.ResponseUrl,this.Kunci);
                return tab2;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
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
