/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bibliotecapinpad.conversoresEntradaSaida.GPNHelper;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGetPin;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGetPin;
/*    */ 
/*    */ public class TaskGetPIN
/*    */   extends StartGetCommand {
/*    */   private EntradaComandoGetPin.GetPinCallback callback;
/*    */   private EntradaComandoGetPin input;
/*    */   
/*    */   public TaskGetPIN(PPCompAndroid ppCompAndroid, EntradaComandoGetPin input, EntradaComandoGetPin.GetPinCallback callback) {
/* 15 */     super(ppCompAndroid);
/* 16 */     this.input = input;
/* 17 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int StartGetCmd() {
/* 23 */     return getPPComp().PP_StartGetPIN(GPNHelper.getPinInputHelper(this.input).toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public int GetCommand(byte[] output) {
/* 28 */     return getPPComp().PP_GetPIN(output);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onProgressUpdate(Integer... progress) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPostExecute(Integer iSt, String result) {
/* 38 */     SaidaComandoGetPin saida = new SaidaComandoGetPin();
/* 39 */     saida.informaResultadoOperacao(RetornoPinpad.parseCodigoRetorno(iSt));
/* 40 */     if (iSt.intValue() == 0)
/* 41 */       GPNHelper.getPinOutputHelper(result, saida); 
/* 42 */     this.callback.comandoGetPinEncerrado(saida);
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskGetPIN.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */