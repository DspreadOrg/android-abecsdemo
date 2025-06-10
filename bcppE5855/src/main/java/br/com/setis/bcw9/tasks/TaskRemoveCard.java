/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bibliotecapinpad.definicoes.CodigosRetorno;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoRemoveCard;
/*    */ 
/*    */ public class TaskRemoveCard
/*    */   extends StartGetCommand {
/*    */   EntradaComandoRemoveCard.RemoveCardCallback callback;
/*    */   
/*    */   public TaskRemoveCard(PPCompAndroid ppCompAndroid, EntradaComandoRemoveCard.RemoveCardCallback callback) {
/* 13 */     super(ppCompAndroid);
/* 14 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   public int StartGetCmd() {
/* 19 */     return getPPComp().PP_StartRemoveCard("");
/*    */   }
/*    */ 
/*    */   
/*    */   public int GetCommand(byte[] output) {
/* 24 */     return getPPComp().PP_RemoveCard(output);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onProgressUpdate(Integer... progress) {}
/*    */ 
/*    */   
/*    */   public void onPostExecute(Integer iSt, String result) {
/* 33 */     CodigosRetorno codigoRetorno = RetornoPinpad.parseCodigoRetorno(iSt);
/* 34 */     this.callback.cartaoRemovido(codigoRetorno);
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskRemoveCard.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */