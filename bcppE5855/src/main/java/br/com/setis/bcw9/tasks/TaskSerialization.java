/*    */
package br.com.setis.bcw9.tasks;
/*    */
/*    */

import android.util.Log;

import br.com.setis.bcw9.BuildConfig;
import br.com.setis.bcw9.DeviceSerial;
import br.com.setis.bcw9.PPCompAndroid;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class TaskSerialization
        /*    */ extends StartGetCommand
        /*    */ {
    /*    */   private byte[] input;
    /*    */   private byte[] output;
    /*    */   private DeviceSerial.RetornoDeviceSerial callback;

    /*    */
    /*    */
    public TaskSerialization(PPCompAndroid ppCompAndroid, byte[] input, DeviceSerial.RetornoDeviceSerial callback) {
        /* 20 */
        super(ppCompAndroid);
        /* 21 */
        this.input = input;
        /* 22 */
        this.callback = callback;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public int StartGetCmd() {
        /* 27 */
        return 0;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public int GetCommand(byte[] output) {
        /* 32 */
        return getPPComp().PP_ExecSerialization(output);
        /*    */
    }

    /*    */
    /*    */
    /*    */
    /*    */
    public void onProgressUpdate(Integer... progress) {
    }

    /*    */
    /*    */
    /*    */
    /*    */
    public void onPostExecute(Integer iSt, String result) {
        /* 42 */
        if (BuildConfig.DEBUG)
            Log.d("BCW9", String.format("Resposta st=%d recebida [%s]", new Object[]{iSt, result}));
        /*    */
    }
    /*    */
}


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskSerialization.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */