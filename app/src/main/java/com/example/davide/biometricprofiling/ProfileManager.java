package com.example.davide.biometricprofiling;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;

/*
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.ShapeDrawable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProfileManager extends ActionBarActivity implements MainFragment.OnListItemClickListener {
    private final List<String> mItems = new ArrayList<>();

 // public   String bio="";
  public static   ArrayList<String> scripts = new ArrayList<String>();

   private static List<String> collection = new ArrayList<String>();
    private static   ArrayList<String> ListCollection = new ArrayList<String>();


  //  private File[] files2;
//private List<File> filesNoFolder= new ArrayList<>();
    JSONObject obj = new JSONObject();
private  String nomeProfilo;
  private int indiceAssoluto;
public static String profiloSelezionato;
   private  List<String> Biom=new ArrayList<String>();//lista biometrie del profilo letto

   public static FloatingActionMenu menuMultipleActions;
    private FloatingActionButton menuMultipleActions2, saved;

    public ShapeDrawable drawable;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manager);


       }


    protected void onResume() {

        super.onResume();
        setContentView(R.layout.activity_profile_manager);
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("nomi", scripts);
        Log.d("vediamo", ListCollection.toString());
        bundle.putStringArrayList("nomiB", ListCollection);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, fragment)
                .commit();


        try {
            getProfilesName();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
      //  FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.floatingActionButton3);
       // btn.setImageIcon(R.drawable.ic_add_circle_outline_black_24dp);
        setContentView(R.layout.activity_profile_manager);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    }

    @Override
    public void onListItemClick(int position) {
        Fragment fragment = null;
indiceAssoluto=position;
        menuMultipleActions = (FloatingActionMenu) findViewById(R.id.fabbbb);
        menuMultipleActions2=(FloatingActionButton) findViewById(R.id.multiple_actions);
        menuMultipleActions.setVisibility(View.VISIBLE);

        saved=(FloatingActionButton) findViewById(R.id.saved);
        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Savez(findViewById(R.id.saved));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



     menuMultipleActions2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), AddModules.class));
            }
        });


        fragment = new AddFragment();

        try {
            Log.d("test", " " + position);
            ReadProfiles(position);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("nomi", scripts);
            fragment.setArguments(bundle);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

getFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void ReadProfiles(int indice) throws PackageManager.NameNotFoundException, FileNotFoundException {
        BufferedReader reader = null;
scripts.clear();
        String[] temp;
        PackageManager m = getPackageManager();

        String s = getPackageName();
        PackageInfo p = null;
        try {
            p = m.getPackageInfo(s, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        s = p.applicationInfo.dataDir+"/files";


        try {

         //   FileInputStream fis = new FileInputStream(files2[(indice)].toString());
            Log.d("contenuto",MainActivity.files[indice].toString());
            profiloSelezionato=MainActivity.files[indice].toString();
            MainActivity getProfiles=new MainActivity();

            nomeProfilo=getProfiles.getFileProfili()[indice].toString();

            reader = new BufferedReader(

                    new FileReader(getProfiles.getNoFolderFileProfili().get(indice)));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                temp = mLine.replace("[","").replace("]","").split(",");
             //   bio=bio+mLine.replace("[","").replace("]","");

              //  scripts.add(mLine.replace(",", " "));
               scripts.addAll(Arrays.asList(temp));
Log.d("scripts",scripts.toString());
                if (temp.length > 0) {

                }
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
     //   Log.d("myTag", bio);
    }



    public void getProfilesName() throws IOException, PackageManager.NameNotFoundException {

        collection.clear();
        ListCollection.clear();

        MainActivity getProfiles=new MainActivity();

collection.addAll(getProfiles.getProfiliList());

        ListCollection.addAll(getProfiles.getProfiliList());

//Log.d("file", collection.toString());
   //     Log.d("file2", ListCollection.toString());
    }



    public void  getList(List<String> lista){
        Biom.clear();
        Biom.addAll(lista);

    }

    public void Savez(View v) throws JSONException {
if(Biom.size()!=0){
        obj.put("Sessione", Biom);
       Log.d("biom", Biom.get(0));
       System.out.println(obj);
        Log.d("menu", scripts.get(0));
        Log.d("menu", collection.get(indiceAssoluto));

        boolean isFileCreated = create(ProfileManager.this, collection.get(indiceAssoluto), obj.getString("Sessione"));
        if(isFileCreated) {
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();
            
        } else {
            //show error or try again.
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    else{
    Toast.makeText(getBaseContext(), "No changes!",
            Toast.LENGTH_SHORT).show();

}

    }


    private boolean create(Context context, String fileName, String jsonString){

        try {
            FileOutputStream fos = openFileOutput(fileName,Context.MODE_PRIVATE);
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


