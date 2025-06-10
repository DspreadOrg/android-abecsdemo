/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bibliotecapinpad.conversoresEntradaSaida.GCRHelper;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGetCard;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGetCard;
/*    */ 
/*    */ 
/*    */ public class TaskGetCard
/*    */   extends StartGetCommand
/*    */ {
/*    */   EntradaComandoGetCard input;
/*    */   EntradaComandoGetCard.GetCardCallback callback;
/*    */   
/*    */   public TaskGetCard(PPCompAndroid ppCompAndroid, EntradaComandoGetCard input, EntradaComandoGetCard.GetCardCallback callback) {
/* 17 */     super(ppCompAndroid);
/* 18 */     this.input = input;
/* 19 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   public int StartGetCmd() {
/* 24 */     return getPPComp().PP_StartGetCard(GCRHelper.getCardInputHelper(this.input));
/*    */   }
/*    */ 
/*    */   
/*    */   public int GetCommand(byte[] output) {
/* 29 */     return getPPComp().PP_GetCard(output);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onProgressUpdate(Integer... progress) {}
/*    */ 
/*    */   
/*    */   public void onPostExecute(Integer iSt, String result) {
/* 38 */     SaidaComandoGetCard saidaComandoGetCard = new SaidaComandoGetCard();
/* 39 */     saidaComandoGetCard.informaResultadoOperacao(RetornoPinpad.parseCodigoRetorno(iSt));
/* 40 */     if (iSt.intValue() == 0)
/* 41 */       GCRHelper.getCardOutputHelper(result, saidaComandoGetCard); 
/* 42 */     this.callback.comandoGetCardEncerrado(saidaComandoGetCard);
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskGetCard.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */