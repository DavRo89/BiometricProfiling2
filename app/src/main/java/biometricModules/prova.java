package biometricModules;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.davide.biometricprofiling.MainActivity;
import com.example.davide.biometricprofiling.ProfileCreation;


public class  prova extends AppCompatActivity implements Recognizable {
    private  Context sContext;
    private  Activity sActivity;
private boolean isFinished;
    private static int choice=0;
    private boolean resultValue;

    public prova(Context context) throws ClassNotFoundException {
        this.sContext=context;
    }

    public prova(Activity activity) throws ClassNotFoundException {

        this.sActivity=activity;
    }


    @Override
    public void exec() {
MainActivity.Sync=false;
Privacy();
       // Log.d("ModuloProva",myDeviceModel2+" "+myDeviceModel3+" "+myDeviceModel4+" "+myDeviceModel5);
        MainActivity.Sync=true;
    }
    private void Privacy(){
        String myDeviceModel = android.os.Build.MODEL;
        String myDeviceModel2= Build.HARDWARE;
        String myDeviceModel3= Build.MANUFACTURER;
        String myDeviceModel4=Build.PRODUCT;
        int myDeviceModel5=Build.VERSION.SDK_INT;

        AlertDialog alertDialog = new AlertDialog.Builder(sActivity).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Modello: "+myDeviceModel+"\n"+"Hardware: "+myDeviceModel2+"\n"+"Produttore: "+myDeviceModel3+"\n"+"Prdotto: "+myDeviceModel4+"SDK: "+myDeviceModel5);
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


