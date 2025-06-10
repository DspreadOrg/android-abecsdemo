/*     */
package br.com.setis.bcw9;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import br.com.setis.bcw9.abecs.output.AbecsFinishExecOutput;
import br.com.setis.bcw9.abecs.output.AbecsGetRespOutput;
import br.com.setis.bcw9.abecs.output.AbecsLookRespOutput;
import com.dspread.dsplibrary.DeviceAbecs;
import com.dspread.dsplibrary.interfaces.CustomEMVEventListener;
import com.dspread.dsplibrary.interfaces.CustomPinEnterListener;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class PPCompAndroid {


    private static DeviceAbecs mDeviceAbecs;
    private String in_param;

    static {
        //System.loadLibrary("PPCompW9");
        mDeviceAbecs = DeviceAbecs.getInstance();
    }

    private static Charset stISO_8859_1 = StandardCharsets.ISO_8859_1;

    private static PPCompAndroid instance = null;

    public static PPCompAndroid getInstance() {
        if (instance == null)
            instance = new PPCompAndroid();

        return instance;
    }

    /*     */
    private PPCompAndroid() {
        /*  48 */
        String buildDate = (new SimpleDateFormat("yyMMdd", Locale.US)).format(new Date(1715624827421L));
        /*  49 */
        // ppInit("001.45", buildDate);
        /*     */
        mDeviceAbecs.ppInit("001.45", buildDate);
    }

    public  void initBindService(){
        mDeviceAbecs.deviceInit();
    }

    public void initBindService(String deviceName) {
        mDeviceAbecs.deviceInit(deviceName);
    }


    public int PP_Open() {
        /*  53 */
        byte[] in = new byte[2];
        /*  54 */
        byte[] out = new byte[1];
        // return ppcompMsg(0, new byte[100], new byte[100]);
        mDeviceAbecs.PP_Open();
        return 0;
    }


    public int PP_EncryptBuffer(String s, byte[] vbOutput) {
        /*  59 */
        return ppcompMsg(1, s.getBytes(stISO_8859_1), vbOutput);
        /*     */
    }


    public int PP_TableLoadInit(String s) {
        /*  63 */
        // return ppcompMsg(8, s.getBytes(stISO_8859_1), null);

        return mDeviceAbecs.ppcompMsg(8, s.getBytes(stISO_8859_1), null);
        /*     */
    }


    public int PP_TableLoadRec(String s) {
        /*  67 */
        //return ppcompMsg(9, s.getBytes(stISO_8859_1), null);

        return mDeviceAbecs.ppcompMsg(9, s.getBytes(stISO_8859_1), null);
        /*     */
    }


    public int PP_TableLoadEnd() {
        /*  71 */
        return mDeviceAbecs.ppcompMsg(10, null, null);
        /*     */
    }


    public int PP_StartGetCard(String s) {
        /*  75 */
        return mDeviceAbecs.ppcompMsg(6, s.getBytes(stISO_8859_1), null);
        /*     */
    }

    /*     */
    /*     */
    public int PP_GetCard(byte[] vbOutput) {
        /*  79 */
        return mDeviceAbecs.ppcompMsg(7, null, vbOutput);
        /*     */
    }

    /*     */
    /*     */
    public int PP_StartGetPIN(String s) {
        /*  83 */
        return mDeviceAbecs.ppcompMsg(2, s.getBytes(stISO_8859_1), null);
        /*     */
    }

    /*     */
    /*     */
    public int PP_GetPIN(byte[] vbOutput) {
        /*  87 */
        return mDeviceAbecs.ppcompMsg(3, null, vbOutput);
        /*     */
    }

    /*     */
    /*     */
    public int PP_StartCheckEvent(String s) {
        /*  91 */
        return mDeviceAbecs.ppcompMsg(4, s.getBytes(stISO_8859_1), null);
        /*     */
    }

    /*     */
    /*     */
    public int PP_CheckEvent(byte[] vbOutput) {
        /*  95 */
        return mDeviceAbecs.ppcompMsg(5, null, vbOutput);
        /*     */
    }

    /*     */
    /*     */
    public int PP_StartGoOnChip(String s) {
        /*  99 */
        return mDeviceAbecs.ppcompMsg(11, s.getBytes(stISO_8859_1), null);
        /*     */
    }

    /*     */
    /*     */
    public int PP_GoOnChip(byte[] vbOutput) {
        /* 103 */
        return mDeviceAbecs.ppcompMsg(12, null, vbOutput);
        /*     */
    }

    /*     */
    /*     */
    public int PP_StartCmdGen(String s) {
        /* 107 */
        return mDeviceAbecs.ppcompMsg(13, s.getBytes(stISO_8859_1), null);
        /*     */
    }

    /*     */
    /*     */
    public int PP_CmdGen(byte[] vbOutput) {
        /* 111 */
        return mDeviceAbecs.ppcompMsg(14, null, vbOutput);
        /*     */
    }

    /*     */
    /*     */
    public int PP_Close() {
        /* 115 */
        return mDeviceAbecs.ppcompMsg(15, null, null);
        /*     */
    }

    /*     */
    /*     */
    public int PP_StartChipDirect(String s) {
        /* 119 */
        return mDeviceAbecs.ppcompMsg(16, s.getBytes(stISO_8859_1), null);
        /*     */
    }

    /*     */
    /*     */
    public int PP_ChipDirect(byte[] vbOutput) {
        /* 123 */
        return mDeviceAbecs.ppcompMsg(17, null, vbOutput);
        /*     */
    }

    /*     */
    /*     */
    public int PP_StartRemoveCard(String s) {
        /* 127 */
        /*     for (int i = s.length(); i < 32; i++)
         *//* 128 *//*
            s = s + " ";*/

        in_param = s;

        if (s.length() > 32) {
            return -1;
        } else {
            for (int i = 0; i < 32 - s.length(); i++) {
                s = s + " ";
            }
        }
        Log.i("remove card param", s);
        /* 129 */
        return mDeviceAbecs.ppcompMsg(18, s.getBytes(stISO_8859_1), null);
        /*     */
    }

    /*     */
    /*     */
    public int PP_RemoveCard(byte[] vbOutput) {
        /* 133 */
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mDeviceAbecs.ppcompMsg(19, null, vbOutput);
        /*     */
    }

    /*     */
    /*     */
    public int PP_GetInfo(String s, byte[] vbOutput) {
        /* 137 */
        //return ppcompMsg(20, s.getBytes(stISO_8859_1), vbOutput);


        return mDeviceAbecs.ppcompMsg(20, s.getBytes(stISO_8859_1), vbOutput);
        /*     */
    }

    /*     */
    /*     */
    public Integer PP_GetTimestamp(String s, byte[] vbOutput) {
        /* 141 */
        return Integer.valueOf(mDeviceAbecs.ppcompMsg(21, s.getBytes(stISO_8859_1), vbOutput));
        /*     */
    }

    /*     */
    /*     */
    public Integer PP_GetDUKPT(String s, byte[] vbOutput) {
        /* 145 */
        return Integer.valueOf(mDeviceAbecs.ppcompMsg(22, s.getBytes(stISO_8859_1), vbOutput));
        /*     */
    }

    /*     */
    /*     */
    public int PP_Abort() {
        /* 149 */
        return mDeviceAbecs.ppcompMsg(23, null, null);
        /*     */
    }

    /*     */
    /*     */
    public int PP_FinishChip(String input, String tags, byte[] vbOutput) {
        /* 153 */
        return mDeviceAbecs.ppcompMsg(24, (input + tags).getBytes(stISO_8859_1), vbOutput);
        /*     */
    }

    public static int abecsStartCmd(String paramString) {

        return mDeviceAbecs.abecsStartCmd(paramString);
    }

    public static int abecsSetParam(int paramInt1, int paramInt2, String paramString) {
        return mDeviceAbecs.abecsSetParam(paramInt1, paramInt2, paramString);
    }

    /*     */
    /*     */
    public int PP_CheckSerialization(byte[] s) {
        /* 157 */
        return mDeviceAbecs.ppcompCheckSerialization(s);
        /*     */
    }

    /*     */
    public int PP_ExecSerialization(byte[] vbOutput) {
        /* 160 */
        return mDeviceAbecs.ppcompExecSerialization(vbOutput);
        /*     */
    }


    /*     */
    /*     */
    /*     */
    public void PP_AbortSerializedCmd() {
        /* 165 */
        mDeviceAbecs.ppcompAbortSerializedCmd();
        /*     */
    }

    /*     */
    //消息透传
    /*     */
    private static native int ppcompMsg(int paramInt, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2);

    /*     */
    /*     */
    private static native int ppInit(String paramString1, String paramString2);

    /*     */
    /*     */
    private static native int ppcompAbortSerializedCmd();

    /*     */
    /*     */
    private static native int ppcompCheckSerialization(byte[] paramArrayOfbyte);

    /*     */
    /*     */
    private static native int ppcompExecSerialization(byte[] paramArrayOfbyte);

    /*     */
    /*     */
    // public static native int abecsStartCmd(String paramString);

    /*     */
    /*     */
    // public static native int abecsSetParam(int paramInt1, int paramInt2, String paramString);

    /*     */
    /*     */
    public static int abecsExecNBlk() {

        return mDeviceAbecs.abecsExecNBlk();
    }


    /*     */
    /*     */
    public static int abecsStartExec() {
        return mDeviceAbecs.abecsStartExec();
    }

    /*     */
    /*     */
    public static void abecsFinishExec(AbecsFinishExecOutput paramAbecsFinishExecOutput) {
        mDeviceAbecs.abecsFinishExec(paramAbecsFinishExecOutput);
    }

    /*     */
    /*     */
    public static native void abecsLookResp(int paramInt, AbecsLookRespOutput paramAbecsLookRespOutput);

    /*     */
    /*     */
    public static void abecsGetResp(int paramInt, AbecsGetRespOutput paramAbecsGetRespOutput) {
        // br.com.setis.library.AbecsGetRespOutput MyabecsGetRespOutput = new br.com.setis.library.AbecsGetRespOutput();
        mDeviceAbecs.abecsGetResp(paramInt, paramAbecsGetRespOutput);
        // paramAbecsGetRespOutput.respData = MyabecsGetRespOutput.respData;
        // paramAbecsGetRespOutput.returnCode=  MyabecsGetRespOutput.returnCode;
    }

    /*     */
    /*     */
    public static native int abecsCheckPropCmd();
    /*     */

    public static int getMkskKeyState(int keyIndex) {
        return mDeviceAbecs.getMkskKeyStatus(keyIndex);
    }

    public static DeviceAbecs.Dukpt_Key_Info getDukptKeyState(int keyIndex) {
        return mDeviceAbecs.getDukptKeyInfo(keyIndex);
    }

    public static int setPinPadConfig(Bundle config){
        return mDeviceAbecs.setPinPadConfig(config);
    }

//    public void setEMVEventListener(CustomEMVEventListener eventListener){
//
//        mDeviceAbecs.setEMVEventListen(eventListener);
//
//    }
//
//    public void setEnterPinListener(CustomPinEnterListener onPinListener) {
//        mDeviceAbecs.setEnterPinListen(onPinListener);
//    }
}


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\PPCompAndroid.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */