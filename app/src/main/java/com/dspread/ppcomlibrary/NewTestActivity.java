package com.dspread.ppcomlibrary;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dspread.dsplibrary.DeviceAbecs;
import com.dspread.ppcomlibrary.utils.POSUtil;
import com.dspread.ppcomlibrary.utils.SecureAbecsCommand;

import java.util.Arrays;

import br.com.setis.bcw9.DeviceSerial;
import br.com.setis.bcw9.PPCompAndroid;

public class NewTestActivity extends AppCompatActivity {
    private static TextView textView;
    private final int mkskIndex = 1;
    private final int dukptIndex = 1;
    String dispText = " ";
    DeviceSerial deviceSerial;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtest);

        Button mkskStateButton = findViewById(R.id.mkskstate_button);
        Button dukptStateButton = findViewById(R.id.dukptstate_button);
        Button serializeButton = findViewById(R.id.serialtest_button);
        textView = findViewById(R.id.newtest_display_textview);

        mkskStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int keyState = PPCompAndroid.getMkskKeyState(mkskIndex);
                if(keyState == 0){
                    dispText = "key of mksk at index ["+ mkskIndex + "] has been loaded.";
                }
                else{
                    dispText = "key of mksk at index ["+ mkskIndex + "] was not loaded.";
                }
                textView.setText(dispText);
                PPCompAndroid.getMkskKeyState(0);
            }
        });
        dukptStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        serializeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serialTest();
            }
        });
    }

    public void serialTest(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                deviceSerial = new DeviceSerial(new InterfaceUsuario(NewTestActivity.this, textView));
                //OPN command test
                String opn ="164F504E17A8A9";
                deviceSerial.enviaComando(POSUtil.hexStringToBytes(opn), opn.length()/2);
                byte[] test_recebytes = new byte[1024];
                int rceLen = 0;
                rceLen = receCommand(test_recebytes,  1000);
                Log.d("NewTestActivity", "recebeResposta OPN-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = receCommand(test_recebytes,  1000);
                Log.d("NewTestActivity", "recebeResposta OPN-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));

                if(!(new String(Arrays.copyOfRange(test_recebytes, 1, 4)).equals("OPN"))
                        || !(new String(Arrays.copyOfRange(test_recebytes, 4, 7)).equals("000"))){
                    dispText += "OPN Fail\n";
                    showMessage(dispText);
                }

                //GTS command  test
                String gts ="1647545330303230341794AF";
                deviceSerial.enviaComando(POSUtil.hexStringToBytes(gts), gts.length()/2);
                rceLen = receCommand(test_recebytes,  1000);
                Log.d("NewTestActivity", "recebeResposta GTS-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = receCommand(test_recebytes,  2000);
                Log.d("NewTestActivity", "recebeResposta GTS-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));

                if(!(new String(Arrays.copyOfRange(test_recebytes, 1, 4)).equals("GTS"))
                        || !(new String(Arrays.copyOfRange(test_recebytes, 4, 7)).equals("000"))){
                    dispText += "GTS Fail\n";
                    showMessage(dispText);
                }


//                        String GCX ="16474358303632001000023034001100023031001333000C30303030303030303031303000150006323530363039001336000631373238323900133700053130303030000C00013C170429";
//                        deviceSerial.enviaComando(POSUtil.hexStringToBytes(GCX), GCX.length()/2);
//                        rceLen = receCommand(test_recebytes,  2000);
//                        Log.d("NewTestActivity", "recebeResposta GCX-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
//                        rceLen = receCommand(test_recebytes,  1000*60);
//                        Log.d("NewTestActivity", "recebeResposta GCX-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
//
//                        if(!(new String(Arrays.copyOfRange(test_recebytes, 1, 4)).equals("GCX"))
//                                || !(new String(Arrays.copyOfRange(test_recebytes, 4, 7)).equals("000"))){
//                            dispText += "GCX Fail\n";
//                            showMessage(dispText);
//                        }


                //CLX command  test
                String CLX ="16434C58303336001B00202A2A2054425645523030303031202A2A2A2A2A2A2A20563A2030202A2A2A2A2A17C338";
                deviceSerial.enviaComando(POSUtil.hexStringToBytes(CLX), CLX.length()/2);
                rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
                Log.d("NewTestActivity", "recebeResposta CLX-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
                Log.d("NewTestActivity", "recebeResposta CLX-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));

                if((!new String(Arrays.copyOfRange(test_recebytes, 1, 4)).equals("CLX"))
                        || !(new String(Arrays.copyOfRange(test_recebytes, 4, 7)).equals("000"))){
                    dispText += "CLX Fail\n";
                    showMessage(dispText);
                }

                //Secure OPN command test
                byte[] sec_opn = SecureAbecsCommand.packOPNCommand();
                deviceSerial.enviaComando(sec_opn, sec_opn.length);
                rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
                Log.d("NewTestActivity", "recebeResposta Sec OPN-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = deviceSerial.recebeResposta(test_recebytes, 6000);
                Log.d("NewTestActivity", "recebeResposta Sec OPN-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                if(rceLen>0){
                    if(SecureAbecsCommand.checkOPNCommand(Arrays.copyOfRange(test_recebytes, 0, rceLen))!=0){
                        dispText += "Secure OPN Fail\n";
                        showMessage(dispText);
                        return;
                    }
                }
                else{
                    return;
                }

                //Secure TLI command test
                byte[] sec_TLI = SecureAbecsCommand.packSecCommand("TLI01200TBVER00001".getBytes());
                deviceSerial.enviaComando(sec_TLI, sec_TLI.length);
                rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
                Log.d("NewTestActivity", "recebeResposta Sec TLI-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = 0;
                while(rceLen == 0){
                    rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
                }
                Log.d("NewTestActivity", "recebeResposta Sec TLI-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                if(Arrays.copyOfRange(test_recebytes, 0, 1)[0] != 0x16
                        || Arrays.copyOfRange(test_recebytes, 1, 2)[0] != 0x12){
                    dispText += "Secure TLI Fail\n";
                    showMessage(dispText);

                    SecureAbecsCommand.checkSecCommand(Arrays.copyOfRange(test_recebytes, 0, rceLen));
                }
                dispText += "serial test finished\n";
                showMessage(dispText);

            }
        }).start();
    }
    public static void showMessage(String message) {
        Log.e("NewActivity", message);
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(message);
            }
        });

        //textView.setText(message);
    }



    int receCommand(byte[] rcvBytes, int timeOutMs){
        int rceLen = 0;
        long startTime = System.currentTimeMillis();
        while(rceLen <= 0){
            if(System.currentTimeMillis()-startTime > timeOutMs){
                rceLen = 0;
                break;
            }
            rceLen = deviceSerial.recebeResposta(rcvBytes, 1000);
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return rceLen;
    }

}
