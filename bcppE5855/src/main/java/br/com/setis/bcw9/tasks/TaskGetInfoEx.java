/*     */ package br.com.setis.bcw9.tasks;
/*     */ 
/*     */

import android.os.AsyncTask;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.setis.bcw9.BuildConfig;
import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bibliotecapinpad.conversoresEntradaSaida.GINHelper;
import br.com.setis.bibliotecapinpad.definicoes.CodigosRetorno;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGetInfoEx;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGetInfo;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGetInfoEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaskGetInfoEx
/*     */   extends AsyncTask<EntradaComandoGetInfoEx, Void, EntradaComandoGetInfoEx>
/*     */ {
/*     */   private PPCompAndroid ppCompAndroid;
/*     */   private Integer iSt;
/*     */   private String output;
/*     */   private CodigosRetorno codigosRetorno;
/*     */   private EntradaComandoGetInfoEx.GetInfoExCallback mGetInfoExCallback;
/*     */   private EntradaComandoGetInfoEx.TipoInfo tipoInfo;
/*     */   private SaidaComandoGetInfoEx saidaComandoGetInfoEx;
/*     */   
/*     */   public TaskGetInfoEx(PPCompAndroid ppCompAndroid, EntradaComandoGetInfoEx.GetInfoExCallback getInfoExCallback) {
/*  32 */     this.mGetInfoExCallback = getInfoExCallback;
/*  33 */     this.ppCompAndroid = ppCompAndroid;
/*     */   }
/*     */   
/*     */   protected EntradaComandoGetInfoEx doInBackground(EntradaComandoGetInfoEx... entradaComandoGetInfoExes) {
/*     */     StringBuilder sb;
/*  38 */     this.tipoInfo = entradaComandoGetInfoExes[0].obtemTipoInfo();
/*  39 */     int indiceInfo = entradaComandoGetInfoExes[0].obtemIndiceInfo();
/*  40 */     if (indiceInfo < 0) {
/*  41 */       indiceInfo = 0;
/*     */     }
/*  43 */     int metodoCripto = 2;
/*  44 */     byte[] vbOutput = new byte[1000];
/*  45 */     Arrays.fill(vbOutput, (byte)32);
/*     */     
/*  47 */     switch (this.tipoInfo) {
/*     */       case INFO_GERAL:
/*  49 */         this.iSt = Integer.valueOf(this.ppCompAndroid.PP_GetInfo("00", vbOutput));
/*     */         break;
/*     */       case INFO_VERSAO_TABELAS_EMV:
/*  52 */         this.iSt = this.ppCompAndroid.PP_GetTimestamp(String.format("%02d", new Object[] { Integer.valueOf(indiceInfo) }), vbOutput);
/*     */         break;
/*     */       case INFO_KEYMAP_DUKPTDES_PIN:
/*     */       case INFO_KEYMAP_DUKPT3DES_PIN:
/*  56 */         if (this.tipoInfo == EntradaComandoGetInfoEx.TipoInfo.INFO_KEYMAP_DUKPT3DES_PIN)
/*  57 */           metodoCripto = 3; 
/*  58 */         sb = new StringBuilder();
/*  59 */         sb.append(metodoCripto).append(String.format("%02d", new Object[] { Integer.valueOf(indiceInfo) }));
/*  60 */         this.iSt = this.ppCompAndroid.PP_GetDUKPT(sb.toString(), vbOutput);
/*     */         break;
/*     */     } 
/*  63 */     this.output = new String(vbOutput, 0, 199, StandardCharsets.ISO_8859_1);
/*  64 */     return entradaComandoGetInfoExes[0];
/*     */   }
/*     */   protected void onPostExecute(EntradaComandoGetInfoEx entradaComandoGetInfoEx) {
/*     */     SaidaComandoGetInfo saidaComandoGetInfo;
/*     */     SaidaComandoGetInfoEx.InformacaoChave informacaoChave;
/*  69 */     CodigosRetorno codigosRetorno = RetornoPinpad.parseCodigoRetorno(this.iSt);
/*     */     
/*  71 */     List<Object> list = new ArrayList();
/*     */     
/*  73 */     switch (this.tipoInfo) {
/*     */       case INFO_GERAL:
/*  75 */         saidaComandoGetInfo = new SaidaComandoGetInfo();
/*  76 */         codigosRetorno = RetornoPinpad.parseCodigoRetorno(this.iSt);
/*  77 */         saidaComandoGetInfo.informaResultadoOperacao(codigosRetorno);
/*     */         
/*  79 */         list.add(GINHelper.saidaComandoGetInfo(this.output, saidaComandoGetInfo));
/*     */         break;
/*     */       
/*     */       case INFO_VERSAO_TABELAS_EMV:
/*  83 */         list.add(this.output.substring(0, 10));
/*     */         break;
/*     */       case INFO_KEYMAP_DUKPTDES_PIN:
/*     */       case INFO_KEYMAP_DUKPT3DES_PIN:
/*  87 */         if (BuildConfig.DEBUG) Log.d("KSN", this.output);
/*     */         
/*  89 */         informacaoChave = new SaidaComandoGetInfoEx.InformacaoChave();
/*  90 */         if (codigosRetorno == CodigosRetorno.OK) {
/*  91 */           informacaoChave.informaKSN(this.output.getBytes());
/*  92 */           informacaoChave.informaStatusChave((this.output != null) ? SaidaComandoGetInfoEx.InformacaoChave.StatusChave.CHAVE_PRESENTE : SaidaComandoGetInfoEx.InformacaoChave.StatusChave.CHAVE_AUSENTE);
/*     */         } else {
/*  94 */           informacaoChave.informaStatusChave(SaidaComandoGetInfoEx.InformacaoChave.StatusChave.CHAVE_AUSENTE);
/*     */         } 
/*  96 */         list.add(informacaoChave);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 101 */     this.saidaComandoGetInfoEx = new SaidaComandoGetInfoEx(this.tipoInfo, list, codigosRetorno);
/* 102 */     this.mGetInfoExCallback.comandoGetInfoExEncerrado(this.saidaComandoGetInfoEx);
/* 103 */     super.onPostExecute(entradaComandoGetInfoEx);
/*     */   }
/*     */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskGetInfoEx.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */