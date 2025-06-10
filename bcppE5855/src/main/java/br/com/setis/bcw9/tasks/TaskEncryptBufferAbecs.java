/*     */ package br.com.setis.bcw9.tasks;
/*     */ 
/*     */

import android.os.AsyncTask;
import android.util.Log;

import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bcw9.abecs.output.AbecsGetRespOutput;
import br.com.setis.bcw9.util.Util;
import br.com.setis.bibliotecapinpad.definicoes.ModoCriptografia;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoEncryptBuffer;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoEncryptBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaskEncryptBufferAbecs
/*     */   extends AsyncTask<EntradaComandoEncryptBuffer, Void, Integer>
/*     */ {
/*     */   private static final String TAG = "TaskEBX";
/*     */   private PPCompAndroid ppCompAndroid;
/*     */   private EntradaComandoEncryptBuffer.EncryptBufferCallback callback;
/*     */   private SaidaComandoEncryptBuffer saidaEBX;
/*     */   
/*     */   public TaskEncryptBufferAbecs(PPCompAndroid ppCompAndroid, EntradaComandoEncryptBuffer.EncryptBufferCallback callback) {
/*  28 */     this.callback = callback;
/*  29 */     this.ppCompAndroid = ppCompAndroid;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPreExecute() {
/*  34 */     this.saidaEBX = new SaidaComandoEncryptBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Integer doInBackground(EntradaComandoEncryptBuffer... entradaComandoEncryptBuffers) {
/*     */     String mthddat;
/*  43 */     EntradaComandoEncryptBuffer entradaEBX = entradaComandoEncryptBuffers[0];
/*     */ 
/*     */ 
/*     */     
/*  47 */     Integer ret = Integer.valueOf(PPCompAndroid.abecsStartCmd("EBX"));
/*  48 */     if (ret.intValue() != 0) {
/*  49 */       return ret;
/*     */     }
/*     */ 
/*     */     
/*  53 */     if (entradaEBX.obtemBlocoDados() == null || (entradaEBX.obtemBlocoDados()).length <= 0) {
/*  54 */       return Integer.valueOf(11);
/*     */     }
/*     */     
/*  57 */     String dados = Util.bytesToHex(entradaEBX.obtemBlocoDados());
/*  58 */     ret = Integer.valueOf(PPCompAndroid.abecsSetParam(15, dados.length(), dados));
/*  59 */     if (ret.intValue() != 0) return ret;
/*     */ 
/*     */     
/*  62 */     switch (entradaEBX.obtemModoCriptografia()) {
/*     */       case MK_WK_3DES:
/*  64 */         mthddat = (entradaEBX.modoBlocoCBC() == true) ? "11" : "10";
/*     */         break;
/*     */ 
/*     */       
/*     */       case DUKPT_3DES:
/*  69 */         if (entradaEBX.obtemVarianteDUKPT() != null && entradaEBX
/*  70 */           .obtemVarianteDUKPT() == EntradaComandoEncryptBuffer.VarianteDUKPT.VARIANTE_DAT1) {
/*  71 */           mthddat = (entradaEBX.modoBlocoCBC() == true) ? "31" : "30";
/*     */           break;
/*     */         } 
/*  74 */         mthddat = (entradaEBX.modoBlocoCBC() == true) ? "51" : "50";
/*     */         break;
/*     */       
/*     */       default:
/*  78 */         return Integer.valueOf(11);
/*     */     } 
/*  80 */     ret = Integer.valueOf(PPCompAndroid.abecsSetParam(3, mthddat.length(), mthddat));
/*  81 */     if (ret.intValue() != 0) return ret;
/*     */ 
/*     */     
/*  84 */     if (entradaEBX.modoBlocoCBC() && entradaEBX.obtemVetorInicializacaoCBC() != null && (entradaEBX
/*  85 */       .obtemVetorInicializacaoCBC()).length > 0) {
/*     */       
/*  87 */       String vetorInic = Util.bytesToHex(entradaEBX.obtemVetorInicializacaoCBC());
/*  88 */       ret = Integer.valueOf(PPCompAndroid.abecsSetParam(29, vetorInic.length(), vetorInic));
/*  89 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/*  93 */     if (entradaEBX.obtemModoCriptografia() == ModoCriptografia.MK_WK_3DES && entradaEBX.obtemWorkingKey() != null && (entradaEBX
/*  94 */       .obtemWorkingKey()).length > 0) {
/*  96 */       String wk = Util.bytesToHex(entradaEBX.obtemWorkingKey());
                Log.e("TaskEncryptBufferAbecs", "wk: " + wk);
/*  97 */       ret = Integer.valueOf(PPCompAndroid.abecsSetParam(10, wk.length(), wk));
/*  98 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 102 */     String keyIdx = String.format("%02d", new Object[] { Integer.valueOf(entradaEBX.obtemIndiceChave()) });
/* 103 */     ret = Integer.valueOf(PPCompAndroid.abecsSetParam(9, keyIdx.length(), keyIdx));
/* 104 */     if (ret.intValue() != 0) return ret;
/*     */ 
/*     */     
/* 107 */     ret = Integer.valueOf(PPCompAndroid.abecsExecNBlk());
/* 108 */     if (ret.intValue() != 0) return ret;
/*     */ 
/*     */     
/* 111 */     AbecsGetRespOutput abecsGetRespOutput = new AbecsGetRespOutput();
/*     */ 
/*     */     
/* 114 */     PPCompAndroid.abecsGetResp(32846, abecsGetRespOutput);
/* 115 */     if (abecsGetRespOutput.returnCode == 0) {
/* 116 */       this.saidaEBX.informaDadosCriptografados(abecsGetRespOutput.respData);
/*     */     }
/*     */ 
/*     */     
/* 120 */     if (entradaEBX.obtemModoCriptografia() == ModoCriptografia.DUKPT_3DES) {
/*     */       
/* 122 */       PPCompAndroid.abecsGetResp(32844, abecsGetRespOutput);
/* 123 */       if (abecsGetRespOutput.returnCode == 0) {
/* 124 */         this.saidaEBX.informaKSN(abecsGetRespOutput.respData);
/*     */       }
/*     */     } 
/*     */     
/* 128 */     return Integer.valueOf(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onPostExecute(Integer result) {
/* 134 */     this.saidaEBX.informaResultadoOperacao(RetornoPinpad.parseCodigoRetorno(result));
/*     */     
/* 136 */     this.callback.comandoEncryptBufferEncerrado(this.saidaEBX);
/*     */   }
/*     */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskEncryptBufferAbecs.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */