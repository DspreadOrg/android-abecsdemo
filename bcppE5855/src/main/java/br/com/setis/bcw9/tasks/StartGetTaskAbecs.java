/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import android.os.AsyncTask;

import br.com.setis.bcw9.abecs.output.AbecsFinishExecOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StartGetTaskAbecs
/*    */   extends AsyncTask<StartGetCommandAbecs, Integer, Integer>
/*    */ {
/*    */   public static final int TASK_BEFORE_START = 0;
/*    */   public static final int TASK_AFTER_START = 1;
/*    */   public static final int TASK_BEFORE_CMD = 2;
/*    */   public static final int TASK_AFTER_CMD = 3;
/*    */   public static final int TASK_BEFORE_FINISH = 4;
/*    */   public static final int TASK_AFTER_FINISH = 5;
/*    */   private StartGetCommandAbecs sgCmd;
/*    */   
/*    */   protected void onProgressUpdate(Integer... progress) {
/* 21 */     this.sgCmd.onProgressUpdate(progress);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Integer doInBackground(StartGetCommandAbecs... startGetCommandAbecs) {
/* 26 */     this.sgCmd = startGetCommandAbecs[0];
/*    */     
/* 28 */     AbecsFinishExecOutput gtxFinishExec = new AbecsFinishExecOutput();
/*    */     
/* 30 */     publishProgress(new Integer[] { Integer.valueOf(0) });
/* 31 */     Integer errorCode = Integer.valueOf(this.sgCmd.StartGetCmd());
/* 32 */     publishProgress(new Integer[] { Integer.valueOf(1), errorCode });
/*    */     
/* 34 */     if (errorCode.intValue() == 0) {
/*    */       do {
/* 36 */         if (isCancelled()) {
/* 37 */           publishProgress(new Integer[] { Integer.valueOf(5), errorCode });
/*    */           
/*    */           break;
/*    */         } 
/*    */         
/*    */         try {
/* 43 */           Thread.sleep(100L);
/* 44 */         } catch (InterruptedException interruptedException) {}
/*    */ 
/*    */         
/* 47 */         publishProgress(new Integer[] { Integer.valueOf(2) });
/* 48 */         errorCode = Integer.valueOf(this.sgCmd.GetCommand(gtxFinishExec));
/* 49 */         publishProgress(new Integer[] { Integer.valueOf(3), errorCode });
/*    */       }
/* 51 */       while (errorCode.intValue() == 1);
/*    */     }
/*    */     
/* 54 */     publishProgress(new Integer[] { Integer.valueOf(4), errorCode });
/*    */     
/* 56 */     return errorCode;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onPostExecute(Integer errorCode) {
/* 62 */     this.sgCmd.onPostExecute(errorCode);
/* 63 */     super.onPostExecute(null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCancelled() {
/* 68 */     super.onCancelled();
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\StartGetTaskAbecs.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */