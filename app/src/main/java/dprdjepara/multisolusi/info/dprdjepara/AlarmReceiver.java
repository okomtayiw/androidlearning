package dprdjepara.multisolusi.info.dprdjepara;
 
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
 
public class AlarmReceiver extends BroadcastReceiver
{
      
    @Override
    public void onReceive(Context context, Intent intent)
    {
       Intent dprd = new Intent(context, DprdAlarmService.class);
       context.startService(dprd);
        
    }   
}