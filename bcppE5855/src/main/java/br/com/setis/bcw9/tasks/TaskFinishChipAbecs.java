/*     */ package br.com.setis.bcw9.tasks;
/*     */ 
/*     */

import android.util.Log;

import br.com.setis.bcw9.BuildConfig;
import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bcw9.abecs.output.AbecsFinishExecOutput;
import br.com.setis.bcw9.abecs.output.AbecsGetRespOutput;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoFinishChip;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoFinishChip;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaskFinishChipAbecs
/*     */   extends StartGetCommandAbecs
/*     */ {
/*     */   private static final String TAG = "TaskFCX";
/*     */   private EntradaComandoFinishChip entradaFCX;
/*     */   private EntradaComandoFinishChip.FinishChipCallback callback;
/*     */   private SaidaComandoFinishChip saidaFCX;
/*     */   
/*     */   public TaskFinishChipAbecs(PPCompAndroid ppCompAndroid, EntradaComandoFinishChip entradaComandoFinishChip, EntradaComandoFinishChip.FinishChipCallback callback) {
/*  28 */     super(ppCompAndroid);
/*  29 */     this.entradaFCX = entradaComandoFinishChip;
/*  30 */     this.callback = callback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Integer setFCXParams() {
/*  40 */     char[] fcxopt = "0000".toCharArray();
/*     */     
/*  42 */     fcxopt[0] = (char)(this.entradaFCX.obtemResultadoComunicacaoAdquirente().ordinal() + 48);
/*  43 */     getPPComp(); Integer ret = Integer.valueOf(PPCompAndroid.abecsSetParam(25, fcxopt.length, new String(fcxopt)));
/*  44 */     if (ret.intValue() != 0) return ret;
/*     */ 
/*     */     
/*  47 */     if (this.entradaFCX.obtemResultadoComunicacaoAdquirente() != EntradaComandoFinishChip.ResultadoComunicacaoAdquirente.ERRO_COMUNICACAO) {
/*     */ 
/*     */       
/*  50 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(28, this.entradaFCX
/*  51 */             .obtemCodigoRespostaAdquirente().length(), this.entradaFCX
/*  52 */             .obtemCodigoRespostaAdquirente()));
/*  53 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/*  57 */     if (this.entradaFCX.obtemDadosEMVRecebidos() != null && (this.entradaFCX.obtemDadosEMVRecebidos()).length > 0) {
/*     */       
/*  59 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(5, (this.entradaFCX.obtemDadosEMVRecebidos()).length, new String(this.entradaFCX
/*  60 */               .obtemDadosEMVRecebidos())));
/*  61 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/*  65 */     if (this.entradaFCX.obtemListaTagsEMV() != null && (this.entradaFCX.obtemListaTagsEMV()).length > 0) {
/*     */       
/*  67 */       String tags = new String(this.entradaFCX.obtemListaTagsEMV());
/*  68 */       if (BuildConfig.DEBUG) Log.d("TaskFCX", "Tags EMV: " + tags); 
/*  69 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(4, tags.length(), tags));
/*  70 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  76 */     return Integer.valueOf(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Integer getFCXResp() {
/*  82 */     AbecsGetRespOutput abecsGetRespOutput = new AbecsGetRespOutput();
/*     */ 
/*     */     
/*  85 */     getPPComp(); PPCompAndroid.abecsGetResp(32856, abecsGetRespOutput);
/*  86 */     if (abecsGetRespOutput.returnCode == 0) {
/*  87 */       this.saidaFCX.informaTransacaoAprovada((abecsGetRespOutput.respData[0] == 48));
/*     */     }
/*     */ 
/*     */     
/*  91 */     getPPComp(); PPCompAndroid.abecsGetResp(32852, abecsGetRespOutput);
/*  92 */     if (abecsGetRespOutput.returnCode == 0) {
/*  93 */       this.saidaFCX.informaDadosEMV(abecsGetRespOutput.respData);
/*     */     }
/*     */ 
/*     */     
/*  97 */     getPPComp(); PPCompAndroid.abecsGetResp(32857, abecsGetRespOutput);
/*  98 */     if (abecsGetRespOutput.returnCode == 0) {
/*  99 */       this.saidaFCX.informaIssuerScriptsResults(abecsGetRespOutput.respData);
/*     */     }
/*     */     
/* 102 */     return Integer.valueOf(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int StartGetCmd() {
/* 111 */     this.saidaFCX = new SaidaComandoFinishChip();
/*     */ 
/*     */     
/* 114 */     getPPComp(); Integer ret = Integer.valueOf(PPCompAndroid.abecsStartCmd("FCX"));
/* 115 */     if (ret.intValue() != 0) return ret.intValue();
/*     */ 
/*     */ 
/*     */     
/* 119 */     ret = setFCXParams();
/* 120 */     if (ret.intValue() != 0) return ret.intValue();
/*     */ 
/*     */ 
/*     */     
/* 124 */     getPPComp(); return PPCompAndroid.abecsStartExec();
/*     */   }
/*     */ 
/*     */   
/*     */   public int GetCommand(AbecsFinishExecOutput gtxFinishExec) {
/* 129 */     getPPComp(); PPCompAndroid.abecsFinishExec(gtxFinishExec);
/*     */     
/* 131 */     if (gtxFinishExec.returnCode == 2)
/*     */     {
/* 133 */       gtxFinishExec.returnCode = 1;
/*     */     }
/*     */     
/* 136 */     return gtxFinishExec.returnCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProgressUpdate(Integer... progress) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPostExecute(Integer result) {
/* 149 */     if (result.intValue() == 0) {
/* 150 */       result = getFCXResp();
/*     */     }
/*     */     
/* 153 */     this.saidaFCX.informaResultadoOperacao(RetornoPinpad.parseCodigoRetorno(result));
/*     */     
/* 155 */     this.callback.comandoFinishChipEncerrado(this.saidaFCX);
/*     */   }
/*     */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskFinishChipAbecs.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */