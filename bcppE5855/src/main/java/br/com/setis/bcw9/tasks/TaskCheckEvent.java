/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bibliotecapinpad.conversoresEntradaSaida.CKEHelper;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoCheckEvent;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoCheckEvent;
/*    */ 
/*    */ public class TaskCheckEvent
/*    */   extends StartGetCommand {
/*    */   private EntradaComandoCheckEvent.CheckEventCallback callback;
/*    */   private EntradaComandoCheckEvent input;
/*    */   
/*    */   public TaskCheckEvent(PPCompAndroid ppCompAndroid, EntradaComandoCheckEvent input, EntradaComandoCheckEvent.CheckEventCallback callback) {
/* 15 */     super(ppCompAndroid);
/* 16 */     this.input = input;
/* 17 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   public int StartGetCmd() {
/* 22 */     return getPPComp().PP_StartCheckEvent(CKEHelper.checkEventInputHelper(this.input).toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public int GetCommand(byte[] output) {
/* 27 */     return getPPComp().PP_CheckEvent(output);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onProgressUpdate(Integer... progress) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPostExecute(Integer iSt, String result) {
/* 37 */     SaidaComandoCheckEvent saida = new SaidaComandoCheckEvent();
/* 38 */     saida.informaResultadoOperacao(RetornoPinpad.parseCodigoRetorno(iSt));
/* 39 */     if (iSt.intValue() == 0)
/* 40 */       CKEHelper.checkEventOutputHelper(result, saida); 
/* 41 */     this.callback.eventoRecebido(saida);
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskCheckEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */