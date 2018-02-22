package biometricModules;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.davide.biometricprofiling.MainActivity;


public class PhoneInfo extends AppCompatActivity implements Recognizable  {
    public static int choice=0;
    private  Context sContext;
    private  Activity sActivity;
    private boolean Finished;
    private boolean resultValue;


    public PhoneInfo(Context context) throws ClassNotFoundException {
        this.sContext=context;
    }
    public PhoneInfo(Activity activity) throws ClassNotFoundException {
        this.sActivity=activity;
    }
    @Override
    public void exec()
    {
    //    PhoneInfo.class.newInstance();
        MainActivity.Sync=false;
   Privacy();
   Log.d("Dentro Modulo","Chiama");

    }




    private void Privacy(){
        AlertDialog alertDialog = new AlertDialog.Builder(sActivity).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Informativa sulla privacy");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NON ACCETTO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        choice=1;
                        resultValue = false;
                        handler.sendMessage(handler.obtainMessage());
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ACCETTO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        choice=2;
                        resultValue = true;
                        MainActivity.Sync=true;
                        handler.sendMessage(handler.obtainMessage());
                        dialog.dismiss();

                    }
                });

        alertDialog.show();
        try{ Looper.loop(); }
        catch(RuntimeException e){}

    }

    final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message mesg)
        {
            throw new RuntimeException();
        }
    };
}
