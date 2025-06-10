/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import android.os.AsyncTask;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bibliotecapinpad.conversoresEntradaSaida.GINHelper;
import br.com.setis.bibliotecapinpad.definicoes.CodigosRetorno;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGetInfo;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGetInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskGetInfo
/*    */   extends AsyncTask<EntradaComandoGetInfo, Void, EntradaComandoGetInfo>
/*    */ {
/*    */   private PPCompAndroid ppCompAndroid;
/*    */   private Integer iSt;
/*    */   private String output;
/*    */   private CodigosRetorno codigosRetorno;
/*    */   
/*    */   public TaskGetInfo(PPCompAndroid ppCompAndroid) {
/* 27 */     this.ppCompAndroid = ppCompAndroid;
/*    */   }
/*    */ 
/*    */   
/*    */   protected EntradaComandoGetInfo doInBackground(EntradaComandoGetInfo... params) {
/* 32 */     byte[] vbOutput = new byte[1000];
/* 33 */     Arrays.fill(vbOutput, (byte)32);
/* 34 */     this.iSt = Integer.valueOf(this.ppCompAndroid.PP_GetInfo("00", vbOutput));
/*    */     
/* 36 */     this.output = new String(vbOutput, 0, 199, StandardCharsets.ISO_8859_1);
/*    */     
/* 38 */     return params[0];
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onPostExecute(EntradaComandoGetInfo entradaComandoGetInfo) {
/* 43 */     SaidaComandoGetInfo saidaComandoGetInfo = new SaidaComandoGetInfo();
/* 44 */     this.codigosRetorno = RetornoPinpad.parseCodigoRetorno(this.iSt);
/* 45 */     saidaComandoGetInfo.informaResultadoOperacao(this.codigosRetorno);
/*    */     
/* 47 */     GINHelper.saidaComandoGetInfo(this.output, saidaComandoGetInfo);
/*    */     
/* 49 */     entradaComandoGetInfo.comandoGetInfoEncerrado(saidaComandoGetInfo);
/* 50 */     super.onPostExecute(entradaComandoGetInfo);
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskGetInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */