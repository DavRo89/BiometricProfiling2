package com.example.davide.biometricprofiling;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static List<String> profileModules=new ArrayList<String>();
    public static List<String> profileList=new ArrayList<String>();
    public static File[] files;
   private  ArrayList<String> ModuleClass = new ArrayList<String>();
   public static List<File> filesNoFolder= new ArrayList<>();
  public static final Object lock = new Object();
public static Boolean Sync=true;


    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("profili", Activity.MODE_PRIVATE);
        String Value = sp.getString("profiliNome","");

        Log.d("StringaProfili",Value);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }



    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("profili", Activity.MODE_PRIVATE);
        String Value = sp.getString("profiliNome","");
        Log.d("StringaProfili",Value);

        try {

                getProfilesName();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    setContentView(R.layout.activity_main);

        Button btn2 = (Button)findViewById(R.id.button2);
    /*    final Button btn = (Button) findViewById(R.id.button1);*/

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, ProfileManager.class));
            }
        });


        Button btn = (Button)findViewById(R.id.button);
    /*    final Button btn = (Button) findViewById(R.id.button1);*/

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileCreation.class));
            }
        });


        Button btn7 = (Button)findViewById(R.id.button7);
        /*    final Button btn = (Button) findViewById(R.id.button1);*/

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //when play is clicked show stop button and hide play button

                startActivity(new Intent(MainActivity.this, ProfileSelection.class));
            }
        });


        String ProfileValue = sp.getString("profiliPath","");
        BufferedReader reader = null;

        String[] temp;
        try {
            ModuleClass.clear();
            reader = new BufferedReader(
                    new FileReader(ProfileValue));
            String mLine;

            while ((mLine = reader.readLine()) != null) {

                temp = mLine.replace("[","").replace("]","").replace(" ","").split(",");
                ModuleClass.addAll(Arrays.asList(temp));
            //   Log.d("provaModuli", ModuleClass.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       // Log.d("metodiProfili", reader.toString());


profileModules.clear();
        profileModules.add(ProfileValue);

        Button btn8 = (Button)findViewById(R.id.button8);
        /*    final Button btn = (Button) findViewById(R.id.button1);*/

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                for (String stringaN:ModuleClass
                     ) {
                    //PhoneInfo.class.newInstance();

if(Sync){

    try {

        ExModules(stringaN);
    } catch (NoSuchMethodException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (InvocationTargetException e) {
        e.printStackTrace();
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

    Log.d("Siamo qui","Chiamata Sync");


                }


             //   startActivity(new Intent(MainActivity.this, DisplayMessageActivity.class));
            }

            }
        });
        final TextView helloTextView = (TextView) findViewById(R.id.toolbar_title);
        helloTextView.setText(Value);
    }




    public void getProfilesName() throws IOException, PackageManager.NameNotFoundException {
        PackageManager m = getPackageManager();
        String s = getPackageName();
        PackageInfo p = m.getPackageInfo(s, 0);
        s = p.applicationInfo.dataDir+"/files";
        Log.d("profili", s);
        File directory = new File(s);
        if(directory.exists()){
          //  Log.d("files",files[0].toString());
            files = directory.listFiles();



      //  Log.d("Files", "Size: "+ files.length);
        profileList.clear();
filesNoFolder.clear();


            for(int i=0;i<files.length;i++){

                if(!files[i].isDirectory()){
                    filesNoFolder.add(files[i]);
                    Log.d("filesd3", filesNoFolder.toString());
                    profileList.add(files[i].toString().replace(s+"/",""));
                    Log.d("files", profileList.toString());
                }}


    }


    }

  public File[] getFileProfili(){
        return files;

  }

    public List<File> getNoFolderFileProfili(){
        return filesNoFolder;

    }

    public List<String> getProfiliList(){
        return profileList;

    }

    public void setFileProfili(File[] fil){
        files=fil;

    }

    //Metodo per leggere le classi nel package dei moduli e chiamare il metodo exec
    public void ExModules(String stringaN) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {

        Class<?> c=Class.forName(stringaN);
        Log.d("classesss",c.toString());
        Constructor<?> cons = c.getConstructor(Activity.class);
        Object classe=cons.newInstance(MainActivity.this);
        Method method = c.getDeclaredMethod("exec", null);

        method.invoke(classe, null);

    }


}


