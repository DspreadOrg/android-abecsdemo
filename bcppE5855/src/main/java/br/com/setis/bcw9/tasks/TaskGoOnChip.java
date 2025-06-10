/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import java.util.Map;

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bibliotecapinpad.conversoresEntradaSaida.GOCHelper;
import br.com.setis.bibliotecapinpad.conversoresEntradaSaida.Inputs;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGoOnChip;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGoOnChip;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskGoOnChip
/*    */   extends StartGetCommand
/*    */ {
/*    */   EntradaComandoGoOnChip input;
/*    */   EntradaComandoGoOnChip.GoOnChipCallback callback;
/*    */   
/*    */   public TaskGoOnChip(PPCompAndroid ppCompAndroid, EntradaComandoGoOnChip input, EntradaComandoGoOnChip.GoOnChipCallback callback) {
/* 21 */     super(ppCompAndroid);
/* 22 */     this.input = input;
/* 23 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   public int StartGetCmd() {
/* 28 */     Map<Inputs, String> map = GOCHelper.goOnChipInputHelper(this.input);
/* 29 */     String inputString = (String)map.get(Inputs.INPUT) + (String)map.get(Inputs.TAGS_EMV);
/* 30 */     return getPPComp().PP_StartGoOnChip(inputString);
/*    */   }
/*    */ 
/*    */   
/*    */   public int GetCommand(byte[] output) {
/* 35 */     return getPPComp().PP_GoOnChip(output);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onProgressUpdate(Integer... progress) {}
/*    */ 
/*    */   
/*    */   public void onPostExecute(Integer iSt, String result) {
/* 44 */     SaidaComandoGoOnChip saidaComandoGoOnChip = new SaidaComandoGoOnChip();
/* 45 */     saidaComandoGoOnChip.informaResultadoOperacao(RetornoPinpad.parseCodigoRetorno(iSt));
/* 46 */     if (iSt.intValue() == 0)
/* 47 */       GOCHelper.goOnChipResp(result, saidaComandoGoOnChip); 
/* 48 */     this.callback.comandoGoOnChipEncerrado(saidaComandoGoOnChip);
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskGoOnChip.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */