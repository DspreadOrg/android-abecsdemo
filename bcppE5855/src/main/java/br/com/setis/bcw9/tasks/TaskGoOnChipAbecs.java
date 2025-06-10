/*     */ package br.com.setis.bcw9.tasks;
/*     */ 
/*     */

import android.util.Log;

import java.io.UnsupportedEncodingException;

import br.com.setis.bcw9.BuildConfig;
import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bcw9.RetornoPinpad;
import br.com.setis.bcw9.abecs.LibToAPI;
import br.com.setis.bcw9.abecs.output.AbecsFinishExecOutput;
import br.com.setis.bcw9.abecs.output.AbecsGetRespOutput;
import br.com.setis.bcw9.util.Util;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGoOnChip;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGoOnChip;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaskGoOnChipAbecs
/*     */   extends StartGetCommandAbecs
/*     */ {
/*     */   private static final String TAG = "TaskGOX";
/*     */   private EntradaComandoGoOnChip entradaGOX;
/*     */   private EntradaComandoGoOnChip.GoOnChipCallback callback;
/*     */   private SaidaComandoGoOnChip saidaGOX;
/*     */   
/*     */   public TaskGoOnChipAbecs(PPCompAndroid ppCompAndroid, EntradaComandoGoOnChip entradaComandoGoOnChip, EntradaComandoGoOnChip.GoOnChipCallback callback) {
/*  33 */     super(ppCompAndroid);
/*  34 */     this.entradaGOX = entradaComandoGoOnChip;
/*  35 */     this.callback = callback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Integer setGOXParams() {
/*  45 */     String acqIdx = String.format("%02d", new Object[] { Integer.valueOf(this.entradaGOX.obtemIndiceAdquirente()) });
/*  46 */     getPPComp(); Integer ret = Integer.valueOf(PPCompAndroid.abecsSetParam(16, acqIdx.length(), acqIdx));
/*  47 */     if (ret.intValue() != 0) return ret;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     if (this.entradaGOX.obtemValorTotal() != 0L) {
/*     */       
/*  58 */       String amount = String.format("%012d", new Object[] { Long.valueOf(this.entradaGOX.obtemValorTotal()) });
/*  59 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(19, amount.length(), amount));
/*  60 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/*  64 */     if (this.entradaGOX.obtemValorTroco() != 0L) {
/*     */       
/*  66 */       String cashback = String.format("%012d", new Object[] { Long.valueOf(this.entradaGOX.obtemValorTroco()) });
/*  67 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(20, cashback.length(), cashback));
/*  68 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/*  72 */     char[] goxOpt = "00000".toCharArray();
/*  73 */     if (this.entradaGOX.panNaListaExcecao()) {
/*  74 */       goxOpt[0] = '1';
/*     */     }
/*  76 */     if (this.entradaGOX.forcaTransacaoOnline()) {
/*  77 */       goxOpt[1] = '1';
/*     */     }
/*  79 */     if (!this.entradaGOX.permiteBypass()) {
/*  80 */       goxOpt[2] = '1';
/*     */     }
/*  82 */     getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(24, goxOpt.length, new String(goxOpt)));
/*  83 */     if (ret.intValue() != 0) return ret;
/*     */ 
/*     */     
/*  86 */     getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(2, 1, 
/*  87 */           Integer.toString(Util.translateAlgo(this.entradaGOX.obtemModoCriptografia()))));
/*  88 */     if (ret.intValue() != 0) return ret;
/*     */ 
/*     */     
/*  91 */     String keyIdx = String.format("%02d", new Object[] { Integer.valueOf(this.entradaGOX.obtemIndiceChave()) });
/*  92 */     getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(9, 2, keyIdx));
/*  93 */     if (ret.intValue() != 0) return ret;
/*     */ 
/*     */     
/*  96 */     if (this.entradaGOX.obtemMensagemPin() != null && this.entradaGOX.obtemMensagemPin().length() > 0) {
/*     */       
/*  98 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(27, this.entradaGOX.obtemMensagemPin().length(), this.entradaGOX
/*  99 */             .obtemMensagemPin()));
/* 100 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 104 */     if (this.entradaGOX.obtemParametrosTerminalRiskManagement() != null) {
/* 105 */       String trm = LibToAPI.formatTrmField(this.entradaGOX.obtemParametrosTerminalRiskManagement());
/*     */       
/* 107 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(26, trm.length(), trm));
/* 108 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 112 */     if (this.entradaGOX.obtemWorkingKey() != null && (this.entradaGOX.obtemWorkingKey()).length > 0) {
/* 113 */       String wk = Util.bytesToHex(this.entradaGOX.obtemWorkingKey());
/*     */       
/* 115 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(10, wk.length(), wk));
/* 116 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 120 */     if (this.entradaGOX.obtemDadosEMV() != null && (this.entradaGOX.obtemDadosEMV()).length > 0) {
/* 121 */       String emv = Util.bytesToHex(this.entradaGOX.obtemDadosEMV());
/*     */       
/* 123 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(5, emv.length(), emv));
/* 124 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 128 */     if (this.entradaGOX.obtemTimeoutInatividade() > 0) {
/* 129 */       String timeout = String.format("%02X", new Object[] { Integer.valueOf(this.entradaGOX.obtemTimeoutInatividade()) });
/* 130 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(12, timeout.length(), timeout));
/* 131 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 135 */     if (this.entradaGOX.obtemListaTagsEMV() != null && (this.entradaGOX.obtemListaTagsEMV()).length > 0) {
/* 136 */       String tags = Util.bytesToHex(this.entradaGOX.obtemListaTagsEMV());
/*     */
/* 138 */       if (BuildConfig.DEBUG) Log.d("TaskGOX", "Tags EMV: " + tags); 
/* 139 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(4, tags.length(), tags));
/* 140 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/* 143 */     return Integer.valueOf(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private Integer getGOXResp() {
/* 148 */     AbecsGetRespOutput abecsGetRespOutput = new AbecsGetRespOutput();
/*     */     
/* 150 */     getPPComp(); PPCompAndroid.abecsGetResp(32854, abecsGetRespOutput);
/* 151 */     if (abecsGetRespOutput.returnCode == 0) {
/*     */       try {
/* 153 */         char[] goxRes = (new String(abecsGetRespOutput.respData, "ISO-8859-1")).toCharArray();
/*     */         
/* 155 */         if (BuildConfig.DEBUG) Log.d("TaskGOX", "ABECS_PP_GOXRES result = " + new String(abecsGetRespOutput.respData, "ISO-8859-1"));
/*     */ 
/*     */         
/* 158 */         this.saidaGOX.informaResultadoProcessamentoEMV(LibToAPI.getAnalysisDecision(goxRes[0]));
/*     */ 
/*     */         
/* 161 */         this.saidaGOX.informaDeveColetarAssinatura((goxRes[1] == '1'));
/*     */ 
/*     */         
/* 164 */         if (goxRes[2] == '1') {
/* 165 */           this.saidaGOX.informaModoCapturaPIN(SaidaComandoGoOnChip.ModoCapturaPIN.PIN_VERIFICADO_OFFLINE);
/* 166 */         } else if (goxRes[2] == '2') {
/* 167 */           this.saidaGOX.informaModoCapturaPIN(SaidaComandoGoOnChip.ModoCapturaPIN.PIN_DEVE_SER_VERIFICADO_ONLINE);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 174 */       catch (UnsupportedEncodingException e) {
/* 175 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 180 */     getPPComp(); PPCompAndroid.abecsGetResp(32855, abecsGetRespOutput);
/* 181 */     if (abecsGetRespOutput.returnCode == 0) {
/* 182 */       this.saidaGOX.informaPinCriptografado(abecsGetRespOutput.respData);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 187 */     getPPComp(); PPCompAndroid.abecsGetResp(32844, abecsGetRespOutput);
/* 188 */     if (abecsGetRespOutput.returnCode == 0) {
/* 189 */       this.saidaGOX.informaKsn(abecsGetRespOutput.respData);
/*     */     }
/*     */ 
/*     */     
/* 193 */     getPPComp(); PPCompAndroid.abecsGetResp(32852, abecsGetRespOutput);
/* 194 */     if (abecsGetRespOutput.returnCode == 0) {
/* 195 */       this.saidaGOX.informaDadosEMV(abecsGetRespOutput.respData);
/*     */     }
/*     */     
/* 198 */     return Integer.valueOf(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int StartGetCmd() {
/* 206 */     this.saidaGOX = new SaidaComandoGoOnChip();
/*     */ 
/*     */     
/* 209 */     getPPComp(); Integer ret = Integer.valueOf(PPCompAndroid.abecsStartCmd("GOX"));
/* 210 */     if (ret.intValue() != 0) return ret.intValue();
/*     */ 
/*     */ 
/*     */     
/* 214 */     ret = setGOXParams();
/* 215 */     if (ret.intValue() != 0) return ret.intValue();
/*     */ 
/*     */ 
/*     */     
/* 219 */     getPPComp(); return PPCompAndroid.abecsStartExec();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int GetCommand(AbecsFinishExecOutput gtxFinishExec) {
/* 225 */     getPPComp(); PPCompAndroid.abecsFinishExec(gtxFinishExec);
/*     */     
/* 227 */     if (gtxFinishExec.returnCode == 2)
/*     */     {
/* 229 */       gtxFinishExec.returnCode = 1;
/*     */     }
/*     */     
/* 232 */     return gtxFinishExec.returnCode;
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
/* 245 */     if (result.intValue() == 0) {
/* 246 */       result = getGOXResp();
/*     */     }
/*     */     
/* 249 */     this.saidaGOX.informaResultadoOperacao(RetornoPinpad.parseCodigoRetorno(result));
/*     */     
/* 251 */     this.callback.comandoGoOnChipEncerrado(this.saidaGOX);
/*     */   }
/*     */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskGoOnChipAbecs.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */