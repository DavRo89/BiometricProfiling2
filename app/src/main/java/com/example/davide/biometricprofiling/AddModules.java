package com.example.davide.biometricprofiling;

import android.animation.Animator;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.*;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddModules extends AppCompatActivity {
    LinearLayout   destinationFrameLayout=null;
    RelativeLayout HideCheckboxes=null;
    public static ArrayList<String> Classes = new ArrayList<String>();
    private  ArrayList<String> AddClasses = new ArrayList<String>();
    private CheckBox[] checkB;
private int counter;
    JSONObject obj = new JSONObject();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modules);
        ModuleReader prova= null;
        try {
            prova = new ModuleReader(this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


      destinationFrameLayout = (LinearLayout) findViewById(R.id.destination_layout);
        if (savedInstanceState == null) {
            destinationFrameLayout.setVisibility(View.INVISIBLE);
            ViewTreeObserver viewTreeObserver = destinationFrameLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealTransition(); //Function To start the animation
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            destinationFrameLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            destinationFrameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }

        Classes.clear();
        Classes.addAll(prova.getClassesOfPackage("com."));
         checkB=new CheckBox[Classes.size()];
        Log.d("Classi", Classes.toString());
        Log.d("Classi",  ""+Classes.size());
        final LinearLayout parentLayout = (LinearLayout) findViewById(R.id.destination_layout);
        parentLayout.setVisibility(View.VISIBLE);
        final SharedPreferences sp = getApplicationContext().getSharedPreferences("ClassiAdd", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        for (int i = 0; i < Classes.size(); i++) {


            checkB[i] = new CheckBox(this);
            checkB[i].setId(i);
            checkB[i].setText(" " +Classes.get(i));
            parentLayout.addView(checkB[i]);
            edit.putString("Classi",Classes.get(i) );
            edit.commit();


        }
        String ProfileValue3 = (""+( sp.getString("Classi", "")));
        Log.d("ProfiliPerAdd",ProfileValue3);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        Button buttonView = new Button(this);
        buttonView.setText("save");
        buttonView.setOnClickListener(mThisButtonListener);
        parentLayout.addView(buttonView, p);
/*
        Button btn12 = (Button)findViewById(R.id.button12);
    //    final Button btn = (Button) findViewById(R.id.button1);

        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter=0;
for(int i = 0; i < Classes.size(); i++){

if(checkB[i].isChecked()){
    counter++;
    Log.d("checkbox",""+checkB[i].getText().toString());
    AddClasses.add(checkB[i].getText().toString());
    startActivity(new Intent(AddModules.this, ProfileManager.class));
  //  Log.d("scripts",""+ProfileManager.scripts.add(checkB[i].getText().toString()));
    FragmentManager fragmentManager = getFragmentManager();
    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
}
}
if(counter==0){

    AlertDialog alertDialog = new AlertDialog.Builder(AddModules.this).create();
    alertDialog.setTitle("Alert");
    alertDialog.setMessage("Nessun Modulo Selezionato");
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ok",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
    alertDialog.show();
}
                try {
                    obj.put("Moduli", AddClasses);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    AppendFile(obj.getString("Moduli"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
*/
    }

    private void circularRevealTransition() {
        int X = 9 * destinationFrameLayout.getWidth()/10; //The X coordinate for the initial position
        int Y = 9 * destinationFrameLayout.getHeight()/10; //The Y coordinate for the initial position
        int Duration = 500;  //Duration for the animation

        float finalRadius = Math.max(destinationFrameLayout.getWidth(), destinationFrameLayout.getHeight()); //The final radius must be the end points of the current activity

        // create the animator for this view, with the start radius as zero
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(destinationFrameLayout, X, Y, 0, finalRadius);
        circularReveal.setDuration(Duration);

        // set the view visible and start the animation
        destinationFrameLayout.setVisibility(View.VISIBLE);
        // start the animation
        circularReveal.start();
    }

public void AppendFile(String ClassiAdd) {
    File myFile = new File(ProfileManager.profiloSelezionato);

    if (myFile.exists()) {
        try {
            FileOutputStream fOut = openFileOutput(ProfileManager.profiloSelezionato.replace("/data/user/0/com.example.davide.biometricprofiling/files/",""), MODE_APPEND);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(","+ClassiAdd);
            myOutWriter.close();
            fOut.close();
        } catch (Exception e) {

        }

    }
}

    private View.OnClickListener mThisButtonListener = new View.OnClickListener() {
        public void onClick(View v) {


            counter=0;
            for(int i = 0; i < Classes.size(); i++){
                if(checkB[i].isChecked()){
                    counter++;
                    Log.d("checkbox",""+checkB[i].getText().toString());




                        AddClasses.add(checkB[i].getText().toString());

                    startActivity(new Intent(AddModules.this, ProfileManager.class));
                    //  Log.d("scripts",""+ProfileManager.scripts.add(checkB[i].getText().toString()));


                }
            }
            if(counter==0){

                AlertDialog alertDialog = new AlertDialog.Builder(AddModules.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Nessun Modulo Selezionato");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                alertDialog.show();
            }
            else{
                try {
                    obj.put("Moduli", AddClasses);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    AppendFile(obj.getString("Moduli"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    };
}
