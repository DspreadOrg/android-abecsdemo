/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.abecs.output.AbecsFinishExecOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class StartGetCommandAbecs
/*    */ {
/*    */   private PPCompAndroid ppCompAndroid;
/*    */   
/*    */   public abstract int StartGetCmd();
/*    */   
/*    */   public abstract int GetCommand(AbecsFinishExecOutput paramAbecsFinishExecOutput);
/*    */   
/*    */   public abstract void onProgressUpdate(Integer... paramVarArgs);
/*    */   
/*    */   public abstract void onPostExecute(Integer paramInteger);
/*    */   
/*    */   public StartGetCommandAbecs(PPCompAndroid ppCompAndroid) {
/* 22 */     this.ppCompAndroid = ppCompAndroid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PPCompAndroid getPPComp() {
/* 30 */     return this.ppCompAndroid;
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\StartGetCommandAbecs.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */