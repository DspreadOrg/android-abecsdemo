/*    */
package br.com.setis.bcw9.tasks;
/*    */
/*    */

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;

import br.com.setis.bibliotecapinpad.entradas.EntradaComandoOpen;

/*    */
/*    */ public class TaskOpen
        /*    */ extends StartGetCommand {
    /*    */   private EntradaComandoOpen.OpenCallback callback;

    /*    */
    /*    */
    public TaskOpen(PPCompAndroid ppCompAndroid, EntradaComandoOpen.OpenCallback callback) {
        /* 12 */
        super(ppCompAndroid);
        /* 13 */
        this.callback = callback;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public int StartGetCmd() {
        /* 18 */
        return 0;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public int GetCommand(byte[] output) {

        /* 23 */
        return getPPComp().PP_Open();
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
        /* 33 */
        this.callback.comandoOpenEncerrado(RetornoPinpad.parseCodigoRetorno(iSt));
        /*    */
    }
    /*    */
}


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskOpen.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */