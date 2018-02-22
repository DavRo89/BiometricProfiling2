package com.example.davide.biometricprofiling;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class ProfileSelection extends AppCompatActivity {
    public static String profilo;
    public static String profiloSet;
private static int RadiobtnCkd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_selection);

        RadioGroup profiles = new RadioGroup(this);
        profiles.setOrientation(LinearLayout.VERTICAL);
       final MainActivity leng = new MainActivity();

        final SharedPreferences sp = getApplicationContext().getSharedPreferences("profili", MODE_PRIVATE);

        for (int i = 0; i < leng.getFileProfili().length; i++) {

        //    LinearLayout parentLayout = (LinearLayout) findViewById(R.id.check_add_layout);

            RadioButton radB = new RadioButton(this);
            radB.setId(i);
            radB.setText(" " + leng.getProfiliList().get(i));
            profiles.addView(radB);
            RadiobtnCkd=i;

        }
        ((ViewGroup) findViewById(R.id.radiogroup)).addView(profiles);
profiles.check( sp.getInt("RdBtn", 0));
       profiles.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadiobtnCkd=checkedId;
                Log.d("button",""+leng.getProfiliList().get(checkedId));
                Log.d("filesssss",leng.getFileProfili()[checkedId].toString());
                profilo=leng.getFileProfili()[checkedId].toString();
                profiloSet=leng.getProfiliList().get(checkedId);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("profiliPath", profilo);
                edit.putString("profiliNome", profiloSet);
                edit.putInt("RdBtn",RadiobtnCkd);
                edit.commit();
                String ProfileValue = sp.getString("profiliPath","");
                Log.d("value",ProfileValue);
                String ProfileValue2 = sp.getString("profiliNome","");
                String ProfileValue3 = (""+( sp.getInt("RdBtn", 0)));
                Log.d("value",ProfileValue3);
            }
        });
//profilo=leng.getProfiliList().get(profiles.getCheckedRadioButtonId());
/*
        if (profiles.getCheckedRadioButtonId() != -1) {

            Log.d("profilo", "" + profiles.getCheckedRadioButtonId());
        }
*/

    }


}