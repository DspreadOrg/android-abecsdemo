/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import java.util.Map;

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bibliotecapinpad.conversoresEntradaSaida.FNCHelper;
import br.com.setis.bibliotecapinpad.conversoresEntradaSaida.Inputs;
import br.com.setis.bibliotecapinpad.definicoes.CodigosRetorno;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoFinishChip;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoFinishChip;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskFinishChip
/*    */   extends StartGetCommand
/*    */ {
/*    */   private EntradaComandoFinishChip input;
/*    */   private EntradaComandoFinishChip.FinishChipCallback callback;
/*    */   
/*    */   public TaskFinishChip(PPCompAndroid ppCompAndroid, EntradaComandoFinishChip input, EntradaComandoFinishChip.FinishChipCallback callback) {
/* 27 */     super(ppCompAndroid);
/* 28 */     this.input = input;
/* 29 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   public int StartGetCmd() {
/* 34 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int GetCommand(byte[] output) {
/* 39 */     Map<Inputs, String> map = FNCHelper.finishChipInputHelper(this.input);
/* 40 */     return getPPComp().PP_FinishChip(map.get(Inputs.INPUT), map.get(Inputs.TAGS_EMV), output);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onProgressUpdate(Integer... progress) {}
/*    */ 
/*    */   
/*    */   public void onPostExecute(Integer iSt, String result) {
/* 49 */     SaidaComandoFinishChip saidaComandoFinishChip = new SaidaComandoFinishChip();
/* 50 */     saidaComandoFinishChip.informaResultadoOperacao(RetornoPinpad.parseCodigoRetorno(iSt));
/* 51 */     if (saidaComandoFinishChip.obtemResultadoOperacao() == CodigosRetorno.OK)
/* 52 */       FNCHelper.finishChipOutputHelper(result, saidaComandoFinishChip); 
/* 53 */     this.callback.comandoFinishChipEncerrado(saidaComandoFinishChip);
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskFinishChip.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */