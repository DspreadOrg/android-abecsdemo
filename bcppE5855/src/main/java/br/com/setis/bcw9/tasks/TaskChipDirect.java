/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bibliotecapinpad.conversoresEntradaSaida.CHPHelper;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoChipDirect;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoChipDirect;
/*    */ 
/*    */ public class TaskChipDirect
/*    */   extends StartGetCommand {
/*    */   EntradaComandoChipDirect input;
/*    */   EntradaComandoChipDirect.ChipDirectCallback callback;
/*    */   
/*    */   public TaskChipDirect(PPCompAndroid ppCompAndroid, EntradaComandoChipDirect input, EntradaComandoChipDirect.ChipDirectCallback callback) {
/* 15 */     super(ppCompAndroid);
/* 16 */     this.input = input;
/* 17 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   public int StartGetCmd() {
/* 22 */     return getPPComp().PP_StartChipDirect(CHPHelper.chipDirectInputHelper(this.input).toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public int GetCommand(byte[] output) {
/* 27 */     return getPPComp().PP_ChipDirect(output);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onProgressUpdate(Integer... progress) {}
/*    */ 
/*    */   
/*    */   public void onPostExecute(Integer iSt, String result) {
/* 36 */     SaidaComandoChipDirect saidaComandoGetCard = new SaidaComandoChipDirect();
/* 37 */     saidaComandoGetCard.informaResultadoOperacao(RetornoPinpad.parseCodigoRetorno(iSt));
/* 38 */     if (iSt.intValue() == 0)
/* 39 */       CHPHelper.chipDirectOutputHelper(result, saidaComandoGetCard); 
/* 40 */     this.callback.comandoChipDirectEncerrado(saidaComandoGetCard);
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskChipDirect.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */