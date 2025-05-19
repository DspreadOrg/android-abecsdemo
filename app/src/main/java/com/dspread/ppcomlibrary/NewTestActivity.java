package com.dspread.ppcomlibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dspread.dsplibrary.DeviceAbecs;

import br.com.setis.bcw9.PPCompAndroid;

public class NewTestActivity extends AppCompatActivity {
    private static TextView textView;
    private final int mkskIndex = 1;
    private final int dukptIndex = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtest);

        Button mkskStateButton = findViewById(R.id.mkskstate_button);
        Button dukptStateButton = findViewById(R.id.dukptstate_button);
        textView = findViewById(R.id.newtest_display_textview);

        mkskStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dispText = " ";
                int keyState = PPCompAndroid.getMkskKeyState(mkskIndex);
                if(keyState == 0){
                    dispText = "key of mksk at index ["+ mkskIndex + "] has been loaded.";
                }
                else{
                    dispText = "key of mksk at index ["+ mkskIndex + "] was not loaded.";
                }
                textView.setText(dispText);
            }
        });

        dukptStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dispText = " ";
                DeviceAbecs.Dukpt_Key_Info dukptKeyInfo =PPCompAndroid.getDukptKeyState(dukptIndex);
                if(dukptKeyInfo.getDukptStatus() == 0){
                    dispText = "key of dukpt at index ["+ dukptIndex + "] has been loaded.";
                    dispText += "\nksn is "+dukptKeyInfo.getDukptKsn();
                }
                else{
                    dispText = "key of dukpt at index ["+ dukptIndex + "] was not loaded.";
                }
                textView.setText(dispText);
            }
        });
    }


}
