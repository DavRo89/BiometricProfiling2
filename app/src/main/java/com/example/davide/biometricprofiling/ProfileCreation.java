package com.example.davide.biometricprofiling;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import dalvik.system.DexFile;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.*;

public class ProfileCreation extends AppCompatActivity implements MainFragment.OnListItemClickListener {
    EditText textmsg;
    static final int READ_BLOCK_SIZE = 100;
    public static String nome;
    public static ArrayList<String> Lines = new ArrayList<String>();
    public static String[] biometric_names;
    public List<String> ListaBiom = new ArrayList<String>();//lista delle biometrie
    JSONObject obj = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);
        ModuleReader prova = null;
        try {
            prova = new ModuleReader(this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //  DeviceInfo.getClassesOfPackage("com.");

        textmsg = (EditText) findViewById(R.id.edit_name);
        if (savedInstanceState == null) {
            Lines.clear();
            biometric_names = (getResources().getStringArray(R.array.biometric_array));
            Lines.addAll(prova.getClassesOfPackage("com."));
            Fragment fragment2 = null;
            fragment2 = new RecyclerListFragment();

            Bundle bundle = new Bundle();
            bundle.putStringArrayList("nomi", Lines);
            Log.d("nomi", Lines.toString());
            fragment2.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment2)
                    .addToBackStack(null)
                    .commit();

        }


    }





    /*    final Button btn = (Button) findViewById(R.id.button1);*/

    public void WriteBtn(View v) throws IOException, PackageManager.NameNotFoundException {


        // add-write text into file

        try {

            textmsg = (EditText) findViewById(R.id.edit_name);
            String sControl = textmsg.getText().toString();
            if (sControl.matches("")) {
                Toast.makeText(this, "You did not enter a name!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                obj.put("Sessione", ListaBiom);

                Log.d("Json", obj.getString("Sessione"));
                //  System.out.println(obj);
//Log.d("ListaActivity", ListaBiom.subList(0,4).toString());

                boolean isFileCreated = create(ProfileCreation.this, textmsg.getText().toString(), obj.getString("Sessione"));
                if (isFileCreated) {
                    Toast.makeText(getBaseContext(), "File saved successfully!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //show error or try again.
                }

                // startActivity(new Intent(ProfileCreation.this, Biometrics_features.class));
                //display file saved message


                startActivity(new Intent(ProfileCreation.this, MainActivity.class));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void Cancel(View v) {
        //reading text from file
        startActivity(new Intent(ProfileCreation.this, MainActivity.class));
    }

    @Override
    public void onListItemClick(int position) {
        Fragment fragment = null;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }

    //Settare lista da inserire nel file
    public void getList(List<String> lista) {
        ListaBiom.clear();
        ListaBiom.addAll(lista);
    }


    private boolean create(Context context, String fileName, String jsonString) {

        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }
}
