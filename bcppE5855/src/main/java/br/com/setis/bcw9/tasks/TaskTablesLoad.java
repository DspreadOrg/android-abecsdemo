/*    */ package br.com.setis.bcw9.tasks;
/*    */ 
/*    */

import android.os.AsyncTask;

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bibliotecapinpad.conversoresEntradaSaida.TableHelper;
import br.com.setis.bibliotecapinpad.definicoes.TabelaAID;
import br.com.setis.bibliotecapinpad.definicoes.TabelaCAPK;
import br.com.setis.bibliotecapinpad.definicoes.TabelaCertificadosRevogados;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoTableLoad;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskTablesLoad
/*    */   extends AsyncTask<Void, Void, Integer>
/*    */ {
/*    */   private PPCompAndroid ppCompAndroid;
/*    */   private EntradaComandoTableLoad.TableLoadCallback callback;
/*    */   private EntradaComandoTableLoad entrada;
/*    */   
/*    */   public TaskTablesLoad(PPCompAndroid ppCompAndroid, EntradaComandoTableLoad input, EntradaComandoTableLoad.TableLoadCallback callback) {
/* 25 */     this.ppCompAndroid = ppCompAndroid;
/* 26 */     this.callback = callback;
/* 27 */     this.entrada = input;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Integer doInBackground(Void... v) {
/* 32 */     TableHelper helper = new TableHelper();
/*    */     
/* 34 */     String input = "";
/* 35 */     int iSt = this.ppCompAndroid.PP_TableLoadInit(helper.montaTLI(this.entrada.obtemIdentificadorRedeCredenciadora(), this.entrada.obtemVersaoTabelasEMV()));
/* 36 */     if (this.entrada.obtemTabelasAID() != null) {
/* 37 */       for (TabelaAID aid : this.entrada.obtemTabelasAID()) {
/*    */         try {
/* 39 */           String tabaid = helper.montaTabelaAid(aid);
/* 40 */           iSt = this.ppCompAndroid.PP_TableLoadRec(tabaid);
/* 41 */         } catch (NullPointerException e) {
/* 42 */           iSt = 11;
/*    */         }
/* 44 */         if (iSt != 0) {
/* 45 */           return Integer.valueOf(iSt);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 50 */     if (this.entrada.obtemTabelasCAPK() != null) {
/* 51 */       for (TabelaCAPK capk : this.entrada.obtemTabelasCAPK()) {
/* 52 */         String tabcapk = helper.montaTabelaCapk(capk);
/* 53 */         iSt = this.ppCompAndroid.PP_TableLoadRec(tabcapk);
/* 54 */         if (iSt != 0) {
/* 55 */           return Integer.valueOf(iSt);
/*    */         }
/*    */       }
/*    */     }
/* 59 */     if (this.entrada.obtemTabelasCertificadosRevogados() != null) {
/* 60 */       for (TabelaCertificadosRevogados revogados : this.entrada.obtemTabelasCertificadosRevogados()) {
/* 61 */         String certiRevog = helper.montaTabelaRevogados(revogados);
/* 62 */         iSt = this.ppCompAndroid.PP_TableLoadRec(certiRevog);
/* 63 */         if (iSt != 0) {
/* 64 */           return Integer.valueOf(iSt);
/*    */         }
/*    */       } 
/*    */     }
/* 68 */     if (iSt == 0)
/* 69 */       iSt = this.ppCompAndroid.PP_TableLoadEnd(); 
/* 70 */     return Integer.valueOf(iSt);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onPostExecute(Integer retorno) {

/* 75 */     this.callback.comandoTableLoadEncerrado(RetornoPinpad.parseCodigoRetorno(retorno));
/* 76 */     super.onPostExecute(retorno);
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskTablesLoad.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */