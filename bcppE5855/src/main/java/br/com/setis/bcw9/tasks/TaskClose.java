/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoClose;
/*    */ 
/*    */ public class TaskClose
/*    */   extends StartGetCommand {
/*    */   EntradaComandoClose callback;
/*    */   
/*    */   public TaskClose(PPCompAndroid ppCompAndroid, EntradaComandoClose callback) {
/* 11 */     super(ppCompAndroid);
/* 12 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   public int StartGetCmd() {
/* 17 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int GetCommand(byte[] output) {
/* 22 */     return getPPComp().PP_Close();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onProgressUpdate(Integer... progress) {}
/*    */ 
/*    */   
/*    */   public void onPostExecute(Integer iSt, String result) {
/* 31 */     this.callback.comandoCloseEncerrado();
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskClose.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */