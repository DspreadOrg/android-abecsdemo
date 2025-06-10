/*     */ package br.com.setis.bcw9.tasks;
/*     */ 
/*     */ import android.util.Log;
/*     */ import br.com.setis.bcw9.BuildConfig;
/*     */ import br.com.setis.bcw9.PPCompAndroid;
/*     */ import br.com.setis.bcw9.RetornoPinpad;
/*     */ import br.com.setis.bcw9.abecs.LibToAPI;
/*     */ import br.com.setis.bcw9.abecs.output.AbecsFinishExecOutput;
/*     */ import br.com.setis.bcw9.abecs.output.AbecsGetRespOutput;
/*     */ import br.com.setis.bcw9.util.Util;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGetCard;
/*     */ import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGetCard;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaskGetCardAbecs
/*     */   extends StartGetCommandAbecs
/*     */ {
/*     */   private static final String TAG = "TaskGCX";
/*     */   private EntradaComandoGetCard entradaGCX;
/*     */   private EntradaComandoGetCard.GetCardCallback callback;
/*     */   private SaidaComandoGetCard saidaGCX;
/*     */   private SaidaComandoGetCard.TipoCartaoLido cardType;
/*     */   
/*     */   public TaskGetCardAbecs(PPCompAndroid ppCompAndroid, EntradaComandoGetCard entradaComandoGetCard, EntradaComandoGetCard.GetCardCallback callback) {
/*  38 */     super(ppCompAndroid);
/*  39 */     this.entradaGCX = entradaComandoGetCard;
/*  40 */     this.callback = callback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Integer setGCXParams() {
/*  48 */     if (this.entradaGCX.obtemTipoTransacao() != 0) {
/*  49 */       String transactionType = String.format("%02X", new Object[] { Byte.valueOf(this.entradaGCX.obtemTipoTransacao()) });
/*  50 */       if (BuildConfig.DEBUG) Log.d("TaskGCX", "Transaction type:" + transactionType); 
/*  51 */       getPPComp(); Integer integer = Integer.valueOf(PPCompAndroid.abecsSetParam(33, 1, transactionType));
/*  52 */       if (integer.intValue() != 0) return integer;
/*     */     
/*     */     } 
/*     */     
/*  56 */     String acqIdx = String.format("%02d", new Object[] { Integer.valueOf(this.entradaGCX.obtemIndiceAdquirente()) });
/*  57 */     getPPComp(); Integer ret = Integer.valueOf(PPCompAndroid.abecsSetParam(16, acqIdx.length(), acqIdx));
/*  58 */     if (ret.intValue() != 0) return ret;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     if (this.entradaGCX.obtemTipoAplicacao() != null && !this.entradaGCX.obtemTipoAplicacao().isEmpty()) {
/*     */ 
/*     */       
/*  67 */       StringBuilder sb = new StringBuilder();
/*     */       
/*  69 */       for (Integer app : this.entradaGCX.obtemTipoAplicacao()) {
/*  70 */         sb.append(String.format("%02d", new Object[] { app }));
/*     */       } 
/*  72 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(17, sb.toString().length(), sb.toString()));
/*  73 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/*  77 */     if (this.entradaGCX.obtemListaRegistrosAID() != null && 
/*  78 */       this.entradaGCX.obtemListaRegistrosAID().getListaIndiceAdquirente().size() > 0 && this.entradaGCX
/*  79 */       .obtemListaRegistrosAID().getListaIndiceRegistro().size() > 0) {
/*     */       
/*  81 */       StringBuilder sb = new StringBuilder();
/*     */       
/*  83 */       for (int i = 0; i < this.entradaGCX.obtemListaRegistrosAID().getListaIndiceRegistro().size(); i++) {
/*     */         
/*  85 */         sb.append(String.format("%02d", new Object[] { this.entradaGCX.obtemListaRegistrosAID().getListaIndiceAdquirente().get(i) }));
/*  86 */         sb.append(String.format("%02d", new Object[] { this.entradaGCX.obtemListaRegistrosAID().getListaIndiceRegistro().get(i) }));
/*     */       } 
/*     */       
/*  89 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(18, sb.toString().length(), sb.toString()));
/*  90 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */ 
/*     */     
/*  95 */     if (this.entradaGCX.obtemValorTotal() > 0L) {
/*  96 */       String amount = String.format("%012d", new Object[] { Long.valueOf(this.entradaGCX.obtemValorTotal()) });
/*  97 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(19, amount.length(), amount));
/*  98 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 102 */     if (this.entradaGCX.obtemValorSaque() > 0L) {
/* 103 */       String cashback = String.format("%012lu", new Object[] { Long.valueOf(this.entradaGCX.obtemValorSaque()) });
/* 104 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(20, cashback.length(), cashback));
/* 105 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 109 */     if (this.entradaGCX.obtemCodigoMoeda() > 0) {
/* 110 */       String transactionCurrency = String.format("%d", new Object[] { Integer.valueOf(this.entradaGCX.obtemCodigoMoeda()) });
/* 111 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(34, transactionCurrency.length(), transactionCurrency));
/* 112 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 116 */     String transactionDate = (new SimpleDateFormat("yyMMdd")).format(this.entradaGCX
/* 117 */         .obtemDataHoraTransacao());
/* 118 */     String transactionTime = (new SimpleDateFormat("HHmmss")).format(this.entradaGCX
/* 119 */         .obtemDataHoraTransacao());
/*     */     
/* 121 */     getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(21, transactionDate.length(), transactionDate));
/* 122 */     if (ret.intValue() != 0) return ret;
/*     */     
/* 124 */     getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(22, transactionTime.length(), transactionTime));
/* 125 */     if (ret.intValue() != 0) return ret;
/*     */ 
/*     */     
/* 128 */     if (this.entradaGCX.permiteCtls()) {
/* 129 */       String gcxOpt = "10000";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 135 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(23, gcxOpt.length(), gcxOpt));
/* 136 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 140 */     if (this.entradaGCX.obtemDadosEMV() != null && (this.entradaGCX.obtemDadosEMV()).length > 0) {
/* 141 */       String emv = Util.bytesToHex(this.entradaGCX.obtemDadosEMV());
/* 142 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(5, emv.length(), emv));
/* 143 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 147 */     if (this.entradaGCX.obtemListaTagsEMV() != null && (this.entradaGCX.obtemListaTagsEMV()).length > 0) {
/*     */       
/* 149 */       String tags = Util.bytesToHex(this.entradaGCX.obtemListaTagsEMV());
/* 150 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(4, tags.length(), tags));
/* 151 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 155 */     if (this.entradaGCX.obtemTimeoutOperacao() > 0) {
/* 156 */       String tout = String.format("%02X", new Object[] { Integer.valueOf(this.entradaGCX.obtemTimeoutOperacao()) });
/* 157 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(12, tout.length(), tout));
/* 158 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/*     */     
/* 162 */     if (this.entradaGCX.obtemMensagemCapturaCartao() != null && this.entradaGCX
/* 163 */       .obtemMensagemCapturaCartao().length() > 0) {
/* 164 */       getPPComp(); ret = Integer.valueOf(PPCompAndroid.abecsSetParam(27, this.entradaGCX
/* 165 */             .obtemMensagemCapturaCartao().length(), this.entradaGCX
/* 166 */             .obtemMensagemCapturaCartao()));
/* 167 */       if (ret.intValue() != 0) return ret;
/*     */     
/*     */     } 
/* 170 */     return Integer.valueOf(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getGCXResp() {
/* 176 */     AbecsGetRespOutput abecsGetRespOutput = new AbecsGetRespOutput();
/*     */
/*     */ 
/*     */     
/* 180 */      getPPComp(); PPCompAndroid.abecsGetResp(32847, abecsGetRespOutput);
/* 181 */     if (abecsGetRespOutput.returnCode == 0) {


               //Log.e("taskGet",Util.byte2HexStr(abecsGetRespOutput.respData));
/*     */       
/* 183 */       String respData = Util.parseString(abecsGetRespOutput.respData);
/*     */       
/* 185 */       this.cardType = LibToAPI.parseCardType(respData);
/* 186 */       this.saidaGCX.informaTipoCartaoLido(this.cardType);
/*     */       
/* 188 */       if (this.cardType == SaidaComandoGetCard.TipoCartaoLido.EMV_COM_CONTATO || this.cardType == SaidaComandoGetCard.TipoCartaoLido.EMV_SEM_CONTATO) {
/*     */ 
/*     */ 
/*     */         
/* 192 */         getPPComp(); PPCompAndroid.abecsGetResp(32849, abecsGetRespOutput);
/* 193 */         if (abecsGetRespOutput.returnCode == 0) {
/*     */           
/* 195 */           List<SaidaComandoGetCard.InformacaoTabelaAID> aidTabInfosList = new ArrayList<>();
/*     */           
/* 197 */           String aidTabInfos = Util.parseString(abecsGetRespOutput.respData);
/*     */           
/* 199 */           for (int i = 0; i < aidTabInfos.length(); i += 6) {
/*     */             
/* 201 */             SaidaComandoGetCard.InformacaoTabelaAID aidInfo = new SaidaComandoGetCard.InformacaoTabelaAID();
/*     */             
/* 203 */             EntradaComandoGetCard.ListaRegistrosAID registroAID = new EntradaComandoGetCard.ListaRegistrosAID();
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
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 228 */             registroAID.adicionaEntrada(Integer.parseInt(aidTabInfos.substring(i, i + 2)), 
/* 229 */                 Integer.parseInt(aidTabInfos.substring(i + 2, i + 4)));
/*     */             
/* 231 */             aidInfo.informaListaRegistrosAID(registroAID);
/* 232 */             aidInfo.informaTipoAplicacao(Integer.parseInt(aidTabInfos.substring(i + 4, i + 6)));
/*     */             
/* 234 */             aidTabInfosList.add(aidInfo);
/*     */           } 
/*     */           
/* 237 */           this.saidaGCX.informaInformacaoTabelaAIDs(aidTabInfosList);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 243 */     SaidaComandoGetCard.StatusUltimaLeituraChip statusUltimaLeituraChip = null;
/*     */     
/* 245 */     getPPComp(); PPCompAndroid.abecsGetResp(32848, abecsGetRespOutput);
/* 246 */     if (BuildConfig.DEBUG) Log.d("TaskGCX", "abecsGetCard abecsGetResp(ABECS_PP_ICCSTAT): " + abecsGetRespOutput.returnCode); 
/* 247 */     if (abecsGetRespOutput.returnCode == 0) {
/* 248 */       switch (Integer.parseInt(Util.parseString(abecsGetRespOutput.respData))) {
/*     */         case 0:
/* 250 */           statusUltimaLeituraChip = SaidaComandoGetCard.StatusUltimaLeituraChip.BEM_SUCEDIDA;
/*     */           break;
/*     */         case 1:
/* 253 */           statusUltimaLeituraChip = SaidaComandoGetCard.StatusUltimaLeituraChip.ERRO_PASSIVEL_FALLBACK;
/*     */           break;
/*     */         case 2:
/* 256 */           statusUltimaLeituraChip = SaidaComandoGetCard.StatusUltimaLeituraChip.APLICACAO_NAO_SUPORTADA;
/*     */           break;
/*     */       } 
/*     */     }
/* 260 */     this.saidaGCX.informaStatusUltimaLeituraChip(statusUltimaLeituraChip);
/*     */     
/* 262 */     Objects.requireNonNull(this.saidaGCX);

             // SaidaComandoGetCard.DadosCartao dadosCartao = new SaidaComandoGetCard.DadosCartao(this.saidaGCX);

                    SaidaComandoGetCard saidaComandoGetCard = new SaidaComandoGetCard();

                      SaidaComandoGetCard.DadosCartao dadosCartao = saidaComandoGetCard.obtemDadosCartao();
/*     */ 
/*     */     
/* 265 */     getPPComp(); PPCompAndroid.abecsGetResp(32850, abecsGetRespOutput);
/* 266 */     if (BuildConfig.DEBUG) Log.d("TaskGCX", "abecsGetCard abecsGetResp(ABECS_PP_PAN): " + abecsGetRespOutput.returnCode); 
/* 267 */     if (abecsGetRespOutput.returnCode == 0) {
/* 268 */       dadosCartao.informaPan(Util.parseString(abecsGetRespOutput.respData));
/*     */     }
/*     */ 
/*     */     
/* 272 */     getPPComp(); PPCompAndroid.abecsGetResp(32851, abecsGetRespOutput);
/* 273 */     if (abecsGetRespOutput.returnCode == 0) {
/* 274 */       dadosCartao.informaPanSequenceNumber(Integer.parseInt(Util.parseString(abecsGetRespOutput.respData)));
/*     */     }
/*     */ 
/*     */     
/* 278 */     getPPComp(); PPCompAndroid.abecsGetResp(32853, abecsGetRespOutput);
/* 279 */     if (abecsGetRespOutput.returnCode == 0) {
/* 280 */       dadosCartao.informaNomePortador(Util.parseString(abecsGetRespOutput.respData));
/*     */     }
/*     */ 
/*     */     
/* 284 */     getPPComp(); PPCompAndroid.abecsGetResp(32859, abecsGetRespOutput);
/* 285 */     if (abecsGetRespOutput.returnCode == 0) {
/* 286 */       dadosCartao.informaNomeAplicacao(Util.parseString(abecsGetRespOutput.respData));
/*     */     }
/*     */ 
/*     */     
/* 290 */     getPPComp(); PPCompAndroid.abecsGetResp(32860, abecsGetRespOutput);
/* 291 */     if (abecsGetRespOutput.returnCode == 0) {
/* 292 */       dadosCartao.informaIssuerCountryCode(Integer.parseInt(Util.parseString(abecsGetRespOutput.respData)));
/*     */     }
/*     */ 
/*     */     
/* 296 */     getPPComp(); PPCompAndroid.abecsGetResp(32861, abecsGetRespOutput);
/* 297 */     if (abecsGetRespOutput.returnCode == 0) {
/* 298 */       dadosCartao.informaDataVencimento(LibToAPI.parseDate(Util.parseString(abecsGetRespOutput.respData), "yyMMdd"));
/*     */     }
/*     */     
/* 301 */     this.saidaGCX.informaDadosCartao(dadosCartao);
/*     */ 
/*     */     
/* 304 */     getPPComp(); PPCompAndroid.abecsGetResp(32852, abecsGetRespOutput);
/* 305 */     if (abecsGetRespOutput.returnCode == 0) {
/* 306 */       this.saidaGCX.informaDadosEMV(abecsGetRespOutput.respData);
/*     */     }
/*     */ 
/*     */ 
/*     */   
/* 311 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkTables(String tablePP, String tableVersion) {
/* 317 */     if (BuildConfig.DEBUG) Log.d("TaskGCX", "Versão tabelas SPE: " + tableVersion); 
/* 318 */     if (BuildConfig.DEBUG) Log.d("TaskGCX", "Versão tabelas PP : " + tablePP);
/*     */     
/* 320 */     return tableVersion.equals(tablePP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int execGTK() {
/* 330 */     getPPComp(); int retorno = PPCompAndroid.abecsStartCmd("GTK");
/*     */     
/* 332 */     if (BuildConfig.DEBUG) Log.d("TaskGCX", "abecsGetCard GTK: " + retorno); 
/* 333 */     if (retorno != 0) return retorno;
/*     */ 
/*     */     
/* 336 */     getPPComp(); retorno = PPCompAndroid.abecsExecNBlk();
/*     */     
/* 338 */     if (BuildConfig.DEBUG) Log.d("TaskGCX", "abecsGetCard execNBlk: " + retorno); 
/* 339 */     if (retorno != 0) return retorno;
/*     */ 
/*     */     
/* 342 */     AbecsGetRespOutput abecsGetRespOutput = new AbecsGetRespOutput();
/* 343 */     String track1 = null, track2 = null, track3 = null;
/*     */ 
/*     */     
/* 346 */     getPPComp(); PPCompAndroid.abecsGetResp(32836, abecsGetRespOutput);
/*     */     
/* 348 */     if (BuildConfig.DEBUG) Log.d("TaskGCX", "getGTKResp ABECS_PP_TRACK1 -> abecsGetResp: " + abecsGetRespOutput.returnCode);
/*     */     
/* 350 */     SaidaComandoGetCard.DadosCartao dadosCartao = this.saidaGCX.obtemDadosCartao();
/*     */     
/* 352 */     if (abecsGetRespOutput.returnCode == 0) {
/*     */       try {
/* 354 */         track1 = new String(abecsGetRespOutput.respData, "ISO-8859-1");
/* 355 */         if (track1.length() > 0) {
/* 356 */           dadosCartao.informaTrilha1(track1);
/*     */         }
/* 358 */       } catch (UnsupportedEncodingException e) {
/* 359 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 363 */     getPPComp(); PPCompAndroid.abecsGetResp(32837, abecsGetRespOutput);
/*     */     
/* 365 */     if (BuildConfig.DEBUG) Log.d("TaskGCX", "getGTKResp ABECS_PP_TRACK2 -> abecsGetResp: " + abecsGetRespOutput.returnCode);
/*     */     
/* 367 */     if (abecsGetRespOutput.returnCode == 0) {
/*     */       
/* 369 */       track2 = Util.BCDtoString(abecsGetRespOutput.respData);
/* 370 */       if (track2.length() > 0) {
/* 371 */         dadosCartao.informaTrilha2(track2);
/*     */       }
/*     */     } 
/*     */     
/* 375 */     getPPComp(); PPCompAndroid.abecsGetResp(32838, abecsGetRespOutput);
/*     */     
/* 377 */     if (BuildConfig.DEBUG) Log.d("TaskGCX", "getGTKResp ABECS_PP_TRACK3 -> abecsGetResp: " + abecsGetRespOutput.returnCode);
/*     */     
/* 379 */     if (abecsGetRespOutput.returnCode == 0) {
/*     */       try {
/* 381 */         track3 = Util.BCDtoString(abecsGetRespOutput.respData);
/* 382 */       } catch (Exception e) {
/*     */         
/* 384 */         track3 = null;
/*     */       } 
/* 386 */       if (track3 != null && track3.length() > 0) {
/* 387 */         dadosCartao.informaTrilha3(track3);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 392 */     if (track2 != null && track2.length() > 0) {
/* 393 */       Util.extractDataFromTrack(2, track2, dadosCartao);
/*     */     }
/* 395 */     if (track1 != null && track1.length() > 0) {
/* 396 */       Util.extractDataFromTrack(1, track1, dadosCartao);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 405 */     this.saidaGCX.informaDadosCartao(dadosCartao);
/*     */ 
/*     */     
/* 408 */     if (track1 == null && track2 == null && track3 == null && 
/* 409 */       this.cardType == SaidaComandoGetCard.TipoCartaoLido.MAGNETICO) {
/* 410 */       return 41;
/*     */     }
/*     */ 
/*     */     
/* 414 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int StartGetCmd() {
/* 421 */     this.saidaGCX = new SaidaComandoGetCard();
/*     */ 
/*     */ 
/*     */     
/* 425 */     String tableGCX = this.entradaGCX.obtemTimestamp();
/* 426 */     if (tableGCX != null && tableGCX.length() > 0) {
/*     */ 
/*     */       
/* 429 */       if (tableGCX.length() != 10) {
/* 430 */         return 11;
/*     */       }
/*     */       
/* 433 */       byte[] aux = new byte[1000];
/* 434 */       String tablePP = null;
/*     */       
/* 436 */       int acqIdx = this.entradaGCX.obtemIndiceAdquirente();
/* 437 */       if (getPPComp().PP_GetTimestamp(String.format("%02d", new Object[] { Integer.valueOf(acqIdx) }), aux).intValue() == 0) {
/* 438 */         tablePP = new String(aux, 0, 10, StandardCharsets.ISO_8859_1);
/*     */       }
/*     */       
/* 441 */       if (tablePP == null || tablePP.length() != 10) {
/* 442 */         return 20;
/*     */       }
/*     */       
/* 445 */       if (!checkTables(tablePP, tableGCX))
/*     */       {
/* 447 */         return 20;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 452 */     getPPComp(); Integer ret = Integer.valueOf(PPCompAndroid.abecsStartCmd("GCX"));
/* 453 */     if (ret.intValue() != 0) return ret.intValue();
/*     */ 
/*     */ 
/*     */     
/* 457 */     ret = setGCXParams();
/* 458 */     if (ret.intValue() != 0) return ret.intValue();
/*     */ 
/*     */ 
/*     */     
/* 462 */     getPPComp(); return PPCompAndroid.abecsStartExec();
/*     */   }
/*     */ 
/*     */   
/*     */   public int GetCommand(AbecsFinishExecOutput gtxFinishExec) {
/* 467 */     getPPComp(); PPCompAndroid.abecsFinishExec(gtxFinishExec);
/*     */     
/* 469 */     if (gtxFinishExec.returnCode == 2)
/*     */     {
/* 471 */       gtxFinishExec.returnCode = 1;
/*     */     }
/*     */     
/* 474 */     return gtxFinishExec.returnCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProgressUpdate(Integer... progress) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPostExecute(Integer result) {
/* 485 */     if (result.intValue() == 0) {
/* 486 */       result = Integer.valueOf(getGCXResp());
/* 487 */       if (result.intValue() == 0) {
/* 488 */         result = Integer.valueOf(execGTK());
/*     */       }
/*     */     } 
/*     */     
/* 492 */     this.saidaGCX.informaResultadoOperacao(RetornoPinpad.parseCodigoRetorno(result));
/*     */     
/* 494 */     this.callback.comandoGetCardEncerrado(this.saidaGCX);
/*     */   }
/*     */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\tasks\TaskGetCardAbecs.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */