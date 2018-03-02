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
import com.example.davide.biometricprofiling.ProfileSelection;


public class DeviceInfo implements Recognizable {
    private  Context sContext;
    private  Activity sActivity;
private boolean isFinished;
    private static int choice=0;
    private boolean resultValue;

    public DeviceInfo(Context context) throws ClassNotFoundException {
        this.sContext=context;
    }

    public DeviceInfo(Activity activity) throws ClassNotFoundException {
        this.sActivity=activity;
    }


    @Override
    public void exec() {
     //   sContext.startActivity(new Intent(sContext, ProfileSelection.class));
      DeviceInfo();

    }

    private void DeviceInfo(){
        String myDeviceModel = android.os.Build.MODEL;
        String myDeviceModel2= Build.HARDWARE;
        String myDeviceModel3= Build.MANUFACTURER;
        String myDeviceModel4=Build.PRODUCT;
        int myDeviceModel5=Build.VERSION.SDK_INT;

        AlertDialog alertDialog = new AlertDialog.Builder(sContext).create();
        alertDialog.setTitle("Device Info");
        alertDialog.setMessage("Modello: "+myDeviceModel+"\n"+"Hardware: "+myDeviceModel2+"\n"+"Manufacter: "+myDeviceModel3+"\n"+"Product: "+myDeviceModel4+"\n"+"SDK: "+myDeviceModel5);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NON ACCETTO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        choice=1;
                        resultValue = false;

                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ACCETTO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        choice=2;
                        resultValue = true;
                        MainActivity.Sync=true;
                        sContext.startActivity(new Intent(sContext, ProfileSelection.class));

                    }
                });


        alertDialog.show();


        try{ Looper.loop(); }
        catch(RuntimeException e){}




}}


