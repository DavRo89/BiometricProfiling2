package biometricModules;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.davide.biometricprofiling.MainActivity;
import com.example.davide.biometricprofiling.ProfileCreation;


public class  prova extends AppCompatActivity implements Recognizable {
    private  Context sContext;
    private  Activity sActivity;
private boolean isFinished;

    public prova(Context context) throws ClassNotFoundException {
        this.sContext=context;
    }

    public prova(Activity activity) throws ClassNotFoundException {

        this.sActivity=activity;
    }


    @Override
    public void exec() {
MainActivity.Sync=false;
        Log.d("ModuloProva","funziona");
        MainActivity.Sync=true;
    }


}
