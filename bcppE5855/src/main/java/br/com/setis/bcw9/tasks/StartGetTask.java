/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import android.os.AsyncTask;
import android.util.Log;

import java.nio.charset.StandardCharsets;

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.util.Util;
import br.com.setis.bibliotecapinpad.definicoes.CodigosRetorno;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StartGetTask
/*    */   extends AsyncTask<StartGetCommand, Integer, String>
/*    */ {
/*    */   public static final int TASK_BEFORE_START = 0;
/*    */   public static final int TASK_AFTER_START = 1;
/*    */   public static final int TASK_BEFORE_CMD = 2;
/*    */   public static final int TASK_AFTER_CMD = 3;
/*    */   public static final int TASK_BEFORE_FINISH = 4;
/*    */   public static final int TASK_AFTER_FINISH = 5;
/*    */   private PPCompAndroid ppcomp;
/*    */   private StartGetCommand sgCmd;
/*    */   private Integer iSt;
/*    */   private CodigosRetorno codigosRetorno;
/*    */   
/*    */   protected void onPreExecute() {
/* 26 */     this.iSt = Integer.valueOf(0);
/*    */   }
/*    */   
/*    */   protected void onProgressUpdate(Integer... progress) {
/* 30 */     this.sgCmd.onProgressUpdate(progress);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String doInBackground(StartGetCommand... startGetCommand) {
/* 35 */     this.sgCmd = startGetCommand[0];
/*    */     
/* 37 */     byte[] vbOutput = new byte[1000];
/* 38 */     publishProgress(new Integer[] { Integer.valueOf(0) });
/* 39 */     this.iSt = Integer.valueOf(this.sgCmd.StartGetCmd());
             Log.i("StartGetTask_start", "iSt="+this.iSt);
/* 40 */     publishProgress(new Integer[] { Integer.valueOf(1), this.iSt });
/*    */     
/* 42 */     if (this.iSt.intValue() != 0) {
               Log.i("StartGetTask11", "iSt="+iSt.intValue());
/* 43 */       return null;
/*    */     }
/*    */     do {
/* 46 */       if (isCancelled()) {
              Log.i("StartGetTask11", "tASK Cancelled");
/* 47 */         publishProgress(new Integer[] { Integer.valueOf(5), this.iSt });
/*    */         break;
/*    */       } 
/*    */       try {
/* 51 */         Thread.sleep(100L);
/* 52 */       } catch (InterruptedException interruptedException) {}
/*    */       
/* 54 */       publishProgress(new Integer[] { Integer.valueOf(2) });
/* 55 */       this.iSt = Integer.valueOf(this.sgCmd.GetCommand(vbOutput));
/* 56 */       publishProgress(new Integer[] { Integer.valueOf(3), this.iSt });
/*    */     }
/* 58 */     while (this.iSt.intValue() == 1);
/*    */     
/* 60 */     publishProgress(new Integer[] { Integer.valueOf(4), this.iSt });
/*    */
/* 62 */     return new String(vbOutput, StandardCharsets.ISO_8859_1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onPostExecute(String result) {
/* 68 */     this.sgCmd.onPostExecute(this.iSt, result);
/* 69 */     super.onPostExecute(result);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCancelled() {
/* 74 */     super.onCancelled();
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\StartGetTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */