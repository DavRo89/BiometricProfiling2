package biometricModules;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.davide.biometricprofiling.MainActivity;

public class DisplayMessageActivity extends AppCompatActivity {
public static int choice=0;


    @Override
  public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
Privacy();
    }



    private void Privacy(){
        AlertDialog alertDialog = new AlertDialog.Builder(DisplayMessageActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Informativa sulla privacy");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NON ACCETTO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        choice=1;
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ACCETTO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        choice=2;
                        dialog.dismiss();
                        Intent intent = new Intent(DisplayMessageActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialog.show();

    }



}
