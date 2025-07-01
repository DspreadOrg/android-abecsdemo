package com.dspread.ppcomlibrary;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dspread.ppcomlibrary.utils.POSUtil;
import com.dspread.ppcomlibrary.utils.AbecsCommand;
import com.dspread.dsplibrary.AbecsKeyInfo;

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
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setTextSize(18);

        mkskStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispText = "";
                AbecsKeyInfo.MKSK_Key_Info mkskKeyInfo= PPCompAndroid.getMkskKeyState(mkskIndex);
                if(mkskKeyInfo.getDataKeyState() == 0){
                    dispText += "data key of mksk at index ["+ mkskIndex + "] has been loaded.";
                }
                else{
                    dispText += "data key of mksk at index ["+ mkskIndex + "] was not loaded.";
                }
                if(mkskKeyInfo.getPinKeyState() == 0){
                    dispText += "\npin key of mksk at index ["+ mkskIndex + "] has been loaded.";
                }
                else{
                    dispText += "\npin key of mksk at index ["+ mkskIndex + "] was not loaded.";
                }
                showMessage(dispText);
            }
        });
        dukptStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispText = "";
                AbecsKeyInfo.Dukpt_Key_Info dukptKeyInfo = PPCompAndroid.getDukptKeyState(dukptIndex);
                Log.d("NewTestActivity", "dukptKeyInfo: "+dukptKeyInfo);
                if(dukptKeyInfo.getDataKeyState() == 0){
                    dispText += "data key of dukpt at index ["+ dukptIndex + "] has been loaded.";
                    dispText += "\nksn is "+dukptKeyInfo.getDataKsn();
                }
                else{
                    dispText += "data key of dukpt at index ["+ dukptIndex + "] was not loaded.";
                }
                if(dukptKeyInfo.getPinKeyState() == 0){
                    dispText += "\npin key of dukpt at index ["+ dukptIndex + "] has been loaded.";
                    dispText += "\nksn is "+dukptKeyInfo.getPinKsn();
                }
                else{
                    dispText += "\npin key of dukpt at index ["+ dukptIndex + "] was not loaded.";
                }
                showMessage(dispText);
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
                dispText = "";
                deviceSerial = new DeviceSerial(new InterfaceUsuario(NewTestActivity.this, textView));
                byte[] test_recebytes = new byte[1024];
                int rceLen = 0;
                //OPN command test
//                String opn ="164F504E17A8A9";
//                deviceSerial.enviaComando(POSUtil.hexStringToBytes(opn), opn.length()/2);
//                rceLen = receCommand(test_recebytes,  1000);
//                Log.d("NewTestActivity", "recebeResposta OPN-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
//                rceLen = receCommand(test_recebytes,  1000);
//                Log.d("NewTestActivity", "recebeResposta OPN-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
//
//                if(!(new String(Arrays.copyOfRange(test_recebytes, 1, 4)).equals("OPN"))
//                        || !(new String(Arrays.copyOfRange(test_recebytes, 4, 7)).equals("000"))){
//                    dispText += "OPN Fail\n";
//                    showMessage(dispText);
//                    return;
//                }

                //
                //OPN command test
                String CanTest ="1818164F504E17A8A9164743583032300015000632353036323700133600063039333332371702E6";
                //String CanTest ="1647435830323000150006323530363237001336000630393237343817C0D8";
                deviceSerial.enviaComando(POSUtil.hexStringToBytes(CanTest), CanTest.length()/2);
                rceLen = receCommand(test_recebytes,  1000);
                Log.d("NewTestActivity", "recebeResposta M-CAN-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = receCommand(test_recebytes,  60000);
                Log.d("NewTestActivity", "recebeResposta M-CAN-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = receCommand(test_recebytes,  60000);
                Log.d("NewTestActivity", "recebeResposta M-CAN-3: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = receCommand(test_recebytes,  60000);
                Log.d("NewTestActivity", "recebeResposta M-CAN-4: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));

                if(!(new String(Arrays.copyOfRange(test_recebytes, 1, 4)).equals("OPN"))
                        || !(new String(Arrays.copyOfRange(test_recebytes, 4, 7)).equals("000"))){
                    dispText += "Can Fail\n";
                    showMessage(dispText);
                    return;
                }

                try {
                    Thread.sleep(3000);
                }catch (Exception e){

                }

                CanTest ="1818164F504E17A8A9";
                //String CanTest ="1647435830323000150006323530363237001336000630393237343817C0D8";
                deviceSerial.enviaComando(POSUtil.hexStringToBytes(CanTest), CanTest.length()/2);
                rceLen = receCommand(test_recebytes,  1000);
                Log.d("NewTestActivity", "recebeResposta M-CAN-12: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = receCommand(test_recebytes,  1000);
                Log.d("NewTestActivity", "recebeResposta M-CAN-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = receCommand(test_recebytes,  60000);
                Log.d("NewTestActivity", "recebeResposta M-CAN-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));;
                rceLen = receCommand(test_recebytes,  60000);
                Log.d("NewTestActivity", "recebeResposta M-CAN-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));

                //Secure OPN command test
                byte[] sec_opn = AbecsCommand.packSecOPNCommand();
                deviceSerial.enviaComando(sec_opn, sec_opn.length);
                rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
                Log.d("NewTestActivity", "recebeResposta Sec OPN-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = deviceSerial.recebeResposta(test_recebytes, 6000);
                Log.d("NewTestActivity", "recebeResposta Sec OPN-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                if(rceLen>0){
                    if(AbecsCommand.checkSecOPNCommand(Arrays.copyOfRange(test_recebytes, 0, rceLen))!=0){
                        dispText += "Secure OPN Fail\n";
                        showMessage(dispText);
                        return;
                    }
                }
                else{
                    return;
                }
                //Secure TLI command test
                byte[] sec_TLI = AbecsCommand.packSecCommand("TLI01200TBVER00001".getBytes());
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

                    AbecsCommand.checkSecCommand(Arrays.copyOfRange(test_recebytes, 0, rceLen));
                }
                dispText += "serial test finished\n";
                showMessage(dispText);
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

                //String GPN ="1647504E3039333130313030313132323333343435353636373738383939414142424343444445454646303434343434202020202020202020202020202020313034313241205452414E5341C7C34F20C92044454352C94449544F2E2053454E48413F3F179800";
                String GPN ="1647504E3039333230313030313132323333343435353636373738383939414142424343444445454646303434343434202020202020202020202020202020313034313241205452414E5341C7C34F20C92044454352C94449544F2E2053454E48413F3F170032";
                //String GPN ="1647504E3039333230363030313132323333343435353636373738383939414142424343444445454646303434343434202020202020202020202020202020313034313241205452414E5341C7C34F20C92044454352C94449544F2E2053454E48413F3F17CD0D";
                deviceSerial.enviaComando(POSUtil.hexStringToBytes(GPN), GPN.length()/2);
                rceLen = receCommand(test_recebytes,  1000);
                Log.d("NewTestActivity", "recebeResposta GPN-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = receCommand(test_recebytes,  60000);
                Log.d("NewTestActivity", "recebeResposta GPN-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));

                if(!(new String(Arrays.copyOfRange(test_recebytes, 1, 4)).equals("GPN"))
                        || !(new String(Arrays.copyOfRange(test_recebytes, 4, 7)).equals("000"))){
                    dispText += "GPN Fail\n";
                    showMessage(dispText);
                }


//                //EBX command test
//                String EBX ="16454258303434000F00084C45455045415254000300023130000A0010FE4B13336446329FE6000000000000000000090002303017A28D";
//                deviceSerial.enviaComando(POSUtil.hexStringToBytes(EBX), EBX.length()/2);
//                rceLen = receCommand(test_recebytes,  1000);
//                Log.d("NewTestActivity", "recebeResposta EBX-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
//                rceLen = receCommand(test_recebytes,  1000*60);
//                Log.d("NewTestActivity", "recebeResposta EBX-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));


                //GCR Test
                //String GCR ="1647435230343030343030303030303030303031303030313331323037313032333535383736353233343536383030171824";
                //String GCX ="16474358303632001000023034001100023031001333000C30303030303030303031303000150006323530363039001336000631373238323900133700053130303030000C00013C170429";
                String GCX ="164743523034303030393930303030303030303030303332353036323330363032353454425645523030303031303017843E";
                deviceSerial.enviaComando(POSUtil.hexStringToBytes(GCX), GCX.length()/2);
                rceLen = receCommand(test_recebytes,  1000);
                Log.d("NewTestActivity", "recebeResposta GCX-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = receCommand(test_recebytes,  1000*60);
                Log.d("NewTestActivity", "recebeResposta GCX-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));

                //16474F583130380010000230340013000C3030303030303030303130300018000530303130300002000131000900023031001A000A00002710000000138820000A000C0000000000000000000000000004001B5A9F029F1A955F2A9A9C9F37829F369F109F269F275F349F039F34000C00013C171792
                String rspGCR = new String(Arrays.copyOfRange(test_recebytes, 1, 1+6));
                if(!(rspGCR.substring(0,3).equals("GCX") && rspGCR.substring(3,6).equals("000"))){
                    dispText += "GCX Fail\n";
                    showMessage(dispText);
                    return;
                }

                String GOXString = "474F583130380010000230340013000C3030303030303030303130300018000530303130300002000131000900023031001A000A00002710000000138820000A0010000000000000000000000000000000000004001B5A9F029F1A955F2A9A9C9F37829F369F109F269F275F349F039F34000C00013C";
                byte[] commonGOX = AbecsCommand.packommand(POSUtil.hexStringToBytes(GOXString));
                deviceSerial.enviaComando(commonGOX, commonGOX.length);
                rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
                Log.d("NewTestActivity", "recebeResposta GOX TLI-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = receCommand(test_recebytes,  1000*60);
                Log.d("NewTestActivity", "recebeResposta GOX-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                String rspGOX = new String(Arrays.copyOfRange(test_recebytes, 1, 1+6));
                if(!(rspGOX.substring(0,3).equals("GOX") && rspGOX.substring(3,6).equals("000"))){
                    dispText += "GOX Fail\n";
                    showMessage(dispText);
                    return;
                }

                String FCXString = "4643583036320019000430303030001C000230300005000A9108A8B7DA780080000000040019959B9F029F039F1A5F2A9A9C9F379F279F26829F369F109F34000C00013C";
                byte[] commonFCX = AbecsCommand.packommand(POSUtil.hexStringToBytes(FCXString));
                deviceSerial.enviaComando(commonFCX, commonFCX.length);
                rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
                Log.d("NewTestActivity", "recebeResposta FCX TLI-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                rceLen = receCommand(test_recebytes,  1000*60);
                Log.d("NewTestActivity", "recebeResposta FCX-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
                String rspFCX = new String(Arrays.copyOfRange(test_recebytes, 1, 1+6));
                if(!(rspFCX.substring(0,3).equals("FCX") && rspFCX.substring(3,6).equals("000"))){
                    dispText += "FCX Fail\n";
                    showMessage(dispText);
                    return;
                }
                showMessage("Finish");


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

//
//                //CLX command  test
//                String CLX ="16434C58303336001B00202A2A2054425645523030303031202A2A2A2A2A2A2A20563A2030202A2A2A2A2A17C338";
//                deviceSerial.enviaComando(POSUtil.hexStringToBytes(CLX), CLX.length()/2);
//                rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
//                Log.d("NewTestActivity", "recebeResposta CLX-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
//                rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
//                Log.d("NewTestActivity", "recebeResposta CLX-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
//
//                if((!new String(Arrays.copyOfRange(test_recebytes, 1, 4)).equals("CLX"))
//                        || !(new String(Arrays.copyOfRange(test_recebytes, 4, 7)).equals("000"))){
//                    dispText += "CLX Fail\n";
//                    showMessage(dispText);
//                }
//
//                //Secure OPN command test
//                byte[] sec_opn = AbecsCommand.packSecOPNCommand();
//                deviceSerial.enviaComando(sec_opn, sec_opn.length);
//                rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
//                Log.d("NewTestActivity", "recebeResposta Sec OPN-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
//                rceLen = deviceSerial.recebeResposta(test_recebytes, 6000);
//                Log.d("NewTestActivity", "recebeResposta Sec OPN-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
//                if(rceLen>0){
//                    if(AbecsCommand.checkSecOPNCommand(Arrays.copyOfRange(test_recebytes, 0, rceLen))!=0){
//                        dispText += "Secure OPN Fail\n";
//                        showMessage(dispText);
//                        return;
//                    }
//                }
//                else{
//                    return;
//                }
//
//                //Secure TLI command test
//                byte[] sec_TLI = AbecsCommand.packSecCommand("TLI01200TBVER00001".getBytes());
//                deviceSerial.enviaComando(sec_TLI, sec_TLI.length);
//                rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
//                Log.d("NewTestActivity", "recebeResposta Sec TLI-1: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
//                rceLen = 0;
//                while(rceLen == 0){
//                    rceLen = deviceSerial.recebeResposta(test_recebytes, 1000);
//                }
//                Log.d("NewTestActivity", "recebeResposta Sec TLI-2: "+ POSUtil.byteArray2Hex(Arrays.copyOfRange(test_recebytes, 0, rceLen)));
//                if(Arrays.copyOfRange(test_recebytes, 0, 1)[0] != 0x16
//                        || Arrays.copyOfRange(test_recebytes, 1, 2)[0] != 0x12){
//                    dispText += "Secure TLI Fail\n";
//                    showMessage(dispText);
//
//                    AbecsCommand.checkSecCommand(Arrays.copyOfRange(test_recebytes, 0, rceLen));
//                }
//                dispText += "serial test finished\n";
//                showMessage(dispText);

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
