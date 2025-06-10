/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import br.com.setis.bcw9.BuildConfig;
import br.com.setis.bcw9.PPCompAndroid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskDirectCommand
/*    */   extends AsyncTask<Void, Void, Integer>
/*    */ {
/*    */   private static final String TAG = "TaskDirectCommand";
/*    */   private DirectCommandCallback commandCallback;
/* 20 */   private byte[] output = null;
/* 21 */   private static AtomicBoolean commandInProgress = null;
/*    */ 
/*    */   
/*    */   public TaskDirectCommand(DirectCommandCallback directCommandCallback) {
/* 25 */     this.commandCallback = directCommandCallback;
/* 26 */     if (commandInProgress == null) {
/* 27 */       commandInProgress = new AtomicBoolean(false);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Integer doInBackground(Void... param) {
/* 34 */     this.output = new byte[2000];
/*    */ 
/*    */     
/* 37 */     while (commandInProgress.get()) {
/*    */       try {
/* 39 */         Thread.sleep(200L);
/* 40 */       } catch (InterruptedException e) {
/* 41 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     if (BuildConfig.DEBUG) Log.d("TaskDirectCommand", "doInBackground..."); 
/* 46 */     if (!commandInProgress.compareAndSet(false, true)) {
/* 47 */       throw new IllegalStateException("Comando já em execução!");
/*    */     }
/* 49 */     Integer result = Integer.valueOf(PPCompAndroid.getInstance().PP_ExecSerialization(this.output));
/*    */     
/* 51 */     if (BuildConfig.DEBUG) Log.d("TaskDirectCommand", "doInBackground - PP_ExecSerialization = [" + result + "] " + (
/* 52 */           (this.output.length == 0) ? " SEM RESPOSTA!" : ""));
/*    */     
/* 54 */     if (result.intValue() < 0) {
/* 55 */       if (BuildConfig.DEBUG) Log.d("TaskDirectCommand", "doInBackground - > this.output = null!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
/* 56 */       this.output = null;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 63 */       Thread.sleep(50L);
/* 64 */     } catch (InterruptedException interruptedException) {}
/*    */ 
/*    */     
/* 67 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onPostExecute(Integer result) {
/* 74 */     if (BuildConfig.DEBUG) Log.d("TaskDirectCommand", "onPostExecute... (result=" + result + ")" + ((this.output == null || this.output.length == 0) ? "VAZIO!" : ""));
/*    */     
/* 76 */     commandInProgress.compareAndSet(true, false);
/* 77 */     this.commandCallback.ComandoDiretoEncerrado(this.output, result.intValue(), (result.intValue() >= 0) ? 0 : result.intValue());
/* 78 */     if (BuildConfig.DEBUG) Log.d("TaskDirectCommand", "doInBackground - FIM!"); 
/*    */   }
/*    */   
/*    */   public static interface DirectCommandCallback {
/*    */     void ComandoDiretoEncerrado(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2);
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskDirectCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */