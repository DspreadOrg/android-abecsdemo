/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.Retornos;
import br.com.setis.bcw9.helper.EncryptHelper;
import br.com.setis.bibliotecapinpad.definicoes.ModoCriptografia;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoEncryptBuffer;
/*    */ 
/*    */ public class TaskEncryptBuffer
/*    */   extends StartGetCommand {
/*    */   EntradaComandoEncryptBuffer.EncryptBufferCallback callback;
/*    */   EntradaComandoEncryptBuffer input;
/*    */   
/*    */   public TaskEncryptBuffer(PPCompAndroid ppCompAndroid, EntradaComandoEncryptBuffer input, EntradaComandoEncryptBuffer.EncryptBufferCallback callback) {
/* 15 */     super(ppCompAndroid);
/* 16 */     this.input = input;
/* 17 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   public int StartGetCmd() {
/* 22 */     if (this.input.obtemModoCriptografia() == ModoCriptografia.DUKPT_3DES)
/* 23 */       return getPPComp().PP_StartCmdGen(EncryptHelper.inputDukpt(this.input).toString()); 
/* 24 */     if (this.input.obtemModoCriptografia() == ModoCriptografia.MK_WK_3DES || this.input
/* 25 */       .obtemModoCriptografia() == ModoCriptografia.MK_WK_DES) {
/* 26 */       return 0;
/*    */     }
/* 28 */     return Retornos.PP_INVPARM.ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public int GetCommand(byte[] output) {
/* 33 */     if (this.input.obtemModoCriptografia() == ModoCriptografia.DUKPT_3DES) {
/* 34 */       return getPPComp().PP_CmdGen(output);
/*    */     }
/* 36 */     return getPPComp().PP_EncryptBuffer(EncryptHelper.inputMK(this.input).toString(), output);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onProgressUpdate(Integer... progress) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPostExecute(Integer iSt, String result) {
/* 47 */     if (this.input.obtemModoCriptografia() == ModoCriptografia.DUKPT_3DES) {
/* 48 */       this.callback.comandoEncryptBufferEncerrado(EncryptHelper.outputDukpt(iSt.intValue(), result, this.input.obtemModoCriptografia()));
/*    */     } else {
/* 50 */       this.callback.comandoEncryptBufferEncerrado(EncryptHelper.outputMK(iSt.intValue(), result, this.input.obtemModoCriptografia()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskEncryptBuffer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */