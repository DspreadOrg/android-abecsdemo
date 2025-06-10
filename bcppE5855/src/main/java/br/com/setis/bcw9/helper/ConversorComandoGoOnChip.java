/*     */ package br.com.setis.bcw9.helper;
/*     */ 
/*     */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import br.com.setis.bibliotecapinpad.definicoes.CodigosRetorno;
import br.com.setis.bibliotecapinpad.definicoes.ModoCriptografia;
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
/*     */ public class ConversorComandoGoOnChip
/*     */ {
/*     */   public static EntradaComandoGoOnChip byteArrayAbecsParaEntradaComandoGoOnChip(byte[] dadosComando) throws ConversaoException {
/*     */     try {
/*  35 */       int indiceAdq = 0, indiceChave = 0;
/*  36 */       ModoCriptografia modoCriptografia = null;
/*     */       
/*  38 */       ArrayList<ObjetoAbecs> listaObjetos = new ArrayList<>();
/*  39 */       int index = 0;
/*  40 */       while (index < dadosComando.length) {
/*  41 */         int indexFinalBloco = index + ConverteByteDadosObjeto.retornaTamanhoComandoFormatoN(dadosComando, 3);
/*  42 */         index += 3;
/*  43 */         while (index < indexFinalBloco) {
/*  44 */           ObjetoAbecs objeto = ConverteByteDadosObjeto.retiraUmObjetoAbecs(dadosComando, index);
/*     */           
/*  46 */           if (objeto.obtemTag() == 16) {
/*  47 */             indiceAdq = ConverteByteDadosObjeto.byteArrayFormatoNParaInt(objeto.obtemValue());
/*  48 */           } else if (objeto.obtemTag() == 9) {
/*  49 */             indiceChave = ConverteByteDadosObjeto.byteArrayFormatoNParaInt(objeto.obtemValue());
/*  50 */           } else if (objeto.obtemTag() == 2) {
/*  51 */             modoCriptografia = verificaModoCriptografia(objeto.obtemValue()[0]);
/*  52 */           }  listaObjetos.add(objeto);
/*  53 */           index += objeto.obtemLen() + 4;
/*     */         } 
/*     */       } 
/*  56 */       return montaBuilder(listaObjetos, indiceAdq, modoCriptografia, indiceChave).build();
/*  57 */     } catch (NullPointerException e) {
/*  58 */       throw new ConversaoException("NullPointerException", e);
/*  59 */     } catch (ArrayIndexOutOfBoundsException e) {
/*  60 */       throw new ConversaoException("ArrayIndexOutOfBounds", e);
/*  61 */     } catch (Exception e) {
/*  62 */       throw new ConversaoException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static EntradaComandoGoOnChip byteArrayObsoletoParaEntradaComandoGoOnChip(byte[] dadosComando) throws ConversaoException {
/*     */     try {
/*  68 */       int keyIdx = ConverteByteDadosObjeto.trechoDeByteArrayFormatoNParaInt(dadosComando, 31, 2);
/*  69 */       int adqIdx = 0;
/*  70 */       ModoCriptografia modo = verificaModoCriptografia(dadosComando[30]);
/*  71 */       EntradaComandoGoOnChip.Builder builder = new EntradaComandoGoOnChip.Builder(adqIdx, modo, keyIdx);
/*  72 */       return montaBuilderObsoleto(builder, dadosComando, modo).build();
/*  73 */     } catch (NullPointerException e) {
/*  74 */       throw new ConversaoException("NullPointerException", e);
/*  75 */     } catch (ArrayIndexOutOfBoundsException e) {
/*  76 */       throw new ConversaoException("ArrayIndexOutOfBounds", e);
/*  77 */     } catch (Exception e) {
/*  78 */       throw new ConversaoException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static EntradaComandoGoOnChip.Builder montaBuilderObsoleto(EntradaComandoGoOnChip.Builder builder, byte[] bytes, ModoCriptografia modo) {
/*  83 */     builder.informaValorTotal(ConverteByteDadosObjeto.trechoDeByteArrayFormatoNParaLong(bytes, 3, 12));
/*  84 */     builder.informaValorTroco(ConverteByteDadosObjeto.trechoDeByteArrayFormatoNParaLong(bytes, 15, 12));
/*  85 */     builder.informaPanNaListaExcecao(verificaTrueFalseGoOnChip(bytes[27]));
/*  86 */     builder.informaForcaTransacaoOnline(verificaTrueFalseGoOnChip(bytes[28]));
/*  87 */     builder.informaPermiteBypass(verificaTrueFalseGoOnChip(bytes[29]));
/*     */ 
/*     */ 
/*     */     
/*  91 */     if (modo == ModoCriptografia.MK_WK_DES) {
/*  92 */       byte[] wKenc = new byte[8];
/*  93 */       System.arraycopy(bytes, 57, wKenc, 0, 8);
/*  94 */       builder.informaWorkingKey(wKenc);
/*  95 */     } else if (modo == ModoCriptografia.MK_WK_3DES) {
/*  96 */       byte[] wKenc = new byte[32];
/*  97 */       System.arraycopy(bytes, 33, wKenc, 0, 32);
/*  98 */       builder.informaWorkingKey(wKenc);
/*     */     } 
/*     */ 
/*     */     
/* 102 */     if (bytes[65] == 49) {
/* 103 */       EntradaComandoGoOnChip.ParametrosTerminalRiskManagement rsManag = new EntradaComandoGoOnChip.ParametrosTerminalRiskManagement();
/* 104 */       byte[] flrLimit = new byte[8], threshold = new byte[8];
/* 105 */       int targetPer = ConverteByteDadosObjeto.trechoDeByteArrayFormatoNParaInt(bytes, 74, 2), maxPercet = ConverteByteDadosObjeto.trechoDeByteArrayFormatoNParaInt(bytes, 84, 2);
/* 106 */       System.arraycopy(bytes, 66, flrLimit, 0, flrLimit.length);
/* 107 */       System.arraycopy(bytes, 76, threshold, 0, threshold.length);
/* 108 */       rsManag.informaTerminalFloorLimit(flrLimit);
/* 109 */       rsManag.informaTargetPercentage((byte)targetPer);
/* 110 */       rsManag.informaThresholdValue(threshold);
/* 111 */       rsManag.informaMaxTargetPercentage((byte)maxPercet);
/* 112 */       builder.informaParametrosTerminalRiskManagement(rsManag);
/*     */     } 
/*     */ 
/*     */     
/* 116 */     int tagsIndex = 89;
/* 117 */     int blkTags = ConverteByteDadosObjeto.trechoDeByteArrayFormatoNParaInt(bytes, tagsIndex, 3);
/* 118 */     int tagsLen = 0;
/* 119 */     tagsIndex += 3;
/* 120 */     byte[] tags = null;
/* 121 */     if (blkTags != 3) {
/* 122 */       tagsLen = ConverteByteDadosObjeto.trechoDeByteArrayFormatoNParaInt(bytes, tagsIndex, 3) * 2;
/* 123 */       if (tagsLen > 0) {
/* 124 */         tagsIndex += 3;
/* 125 */         tags = new byte[tagsLen];
/* 126 */         System.arraycopy(bytes, tagsIndex, tags, 0, tagsLen);
/* 127 */         tagsIndex += tagsLen;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 133 */     int blkTagsOpt = ConverteByteDadosObjeto.trechoDeByteArrayFormatoNParaInt(bytes, tagsIndex, 3);
/* 134 */     int tagsOptLen = 0;
/* 135 */     tagsIndex += 3;
/* 136 */     byte[] tagsOpt = null;
/* 137 */     if (blkTagsOpt > 3) {
/* 138 */       tagsOptLen = ConverteByteDadosObjeto.trechoDeByteArrayFormatoNParaInt(bytes, tagsIndex, 3) * 2;
/* 139 */       if (tagsOptLen > 0) {
/* 140 */         tagsIndex += 3;
/* 141 */         tagsOpt = new byte[tagsOptLen];
/* 142 */         System.arraycopy(bytes, tagsIndex, tagsOpt, 0, tagsOptLen);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 147 */     byte[] tagsList = new byte[tagsLen + tagsOptLen];
/* 148 */     if (tagsLen > 0)
/* 149 */       System.arraycopy(tags, 0, tagsList, 0, tagsLen); 
/* 150 */     if (tagsOptLen > 0) {
/* 151 */       System.arraycopy(tagsOpt, 0, tagsList, tagsLen, tagsOptLen);
/*     */     }
/* 153 */     return builder.informaListaTagsEMV(tagsList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean verificaTrueFalseGoOnChip(byte byteCampo) {
/* 163 */     return (byteCampo == 49);
/*     */   }
/*     */   
/*     */   private static ModoCriptografia verificaModoCriptografia(byte value) {
/* 167 */     switch (value) {
/*     */       case 48:
/* 169 */         return ModoCriptografia.MK_WK_DES;
/*     */       case 49:
/* 171 */         return ModoCriptografia.MK_WK_3DES;
/*     */       case 50:
/* 173 */         return ModoCriptografia.DUKPT_DES;
/*     */     } 
/* 175 */     return ModoCriptografia.DUKPT_3DES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static EntradaComandoGoOnChip.Builder montaBuilder(ArrayList<ObjetoAbecs> listaObjetos, int adq, ModoCriptografia modoCriptografia, int indChave) {
/* 182 */     EntradaComandoGoOnChip.Builder builder = new EntradaComandoGoOnChip.Builder(adq, modoCriptografia, indChave);
/*     */     
/* 184 */     for (ObjetoAbecs objeto : listaObjetos) {
/* 185 */       byte[] value = objeto.obtemValue();
/* 186 */       switch (objeto.obtemTag()) {
/*     */         case 19:
/* 188 */           builder.informaValorTotal(ConverteByteDadosObjeto.byteArrayFormatoNParaInt(value));
/*     */         
/*     */         case 20:
/* 191 */           builder.informaValorTroco(ConverteByteDadosObjeto.byteArrayFormatoNParaInt(value));
/*     */         
/*     */         case 24:
/* 194 */           builder.informaPanNaListaExcecao(verificaListaExcecao(value));
/* 195 */           builder.informaForcaTransacaoOnline(verificaSeForcaTransacao(value));
/* 196 */           builder.informaPermiteBypass(verificaBypass(value));
/*     */         
/*     */         case 10:
/* 199 */           builder.informaWorkingKey(value);
/*     */         
/*     */         case 27:
/* 202 */           builder.informaMensagemPin(ConverteByteDadosObjeto.retornaStringDeByteArrayFormatoS(value));
/*     */         
/*     */         case 26:
/* 205 */           builder.informaParametrosTerminalRiskManagement(verificaRiskManagement(value));
/*     */         
/*     */         case 5:
/* 208 */           builder.informaDadosEMV(value);
/*     */         
/*     */         case 4:
/* 211 */           builder.informaListaTagsEMV(value);
/*     */         
/*     */         case 12:
/* 214 */           builder.informaTimeoutInatividade(value[0]);
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 220 */     return builder;
/*     */   }
/*     */   
/*     */   private static boolean verificaListaExcecao(byte[] valueAbecs) {
/* 224 */     return (valueAbecs[0] == 49);
/*     */   }
/*     */   private static boolean verificaSeForcaTransacao(byte[] valueAbecs) {
/* 227 */     return (valueAbecs[1] == 49);
/*     */   }
/*     */   private static boolean verificaBypass(byte[] valueAbecs) {
/* 230 */     return (valueAbecs[2] != 49);
/*     */   }
/*     */   private static EntradaComandoGoOnChip.ParametrosTerminalRiskManagement verificaRiskManagement(byte[] valueAbecs) {
/* 233 */     EntradaComandoGoOnChip.ParametrosTerminalRiskManagement risk = new EntradaComandoGoOnChip.ParametrosTerminalRiskManagement();
/* 234 */     byte[] aux = new byte[4];
/* 235 */     System.arraycopy(valueAbecs, 0, aux, 0, 4);
/* 236 */     risk.informaTerminalFloorLimit(aux);
/* 237 */     risk.informaTargetPercentage(valueAbecs[4]);
/* 238 */     aux = new byte[4];
/* 239 */     System.arraycopy(valueAbecs, 5, aux, 0, 4);
/* 240 */     risk.informaThresholdValue(aux);
/* 241 */     risk.informaMaxTargetPercentage(valueAbecs[9]);
/* 242 */     return risk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] saidaGoOnChipParaByteArrayAbecs(SaidaComandoGoOnChip saida) throws ConversaoException {
/* 250 */     ArrayList<ByteArrayOutputStream> blocosBytes = new ArrayList<>();
/* 251 */     blocosBytes.add(new ByteArrayOutputStream());
/*     */     
/* 253 */     ObjetoAbecs goxRes = montaGoxResAbecs(saida);
/* 254 */     ObjetoAbecs pinBlock = montaPinBlockAbecs(saida);
/* 255 */     ObjetoAbecs EMVData = montaEMVDataAbecs(saida);
/* 256 */     ObjetoAbecs Ksn = montaKsnAbecs(saida);
/*     */     
/*     */     try {
/* 259 */       ConverteByteDadosObjeto.insereBytesObjetoAbecs(blocosBytes, goxRes);
/* 260 */       ConverteByteDadosObjeto.insereBytesObjetoAbecs(blocosBytes, EMVData);
/* 261 */       ConverteByteDadosObjeto.insereBytesObjetoAbecs(blocosBytes, pinBlock);
/* 262 */       ConverteByteDadosObjeto.insereBytesObjetoAbecs(blocosBytes, Ksn);
/*     */       
/* 264 */       return ConverteByteDadosObjeto.juntaBlocosERetornaByteArraySaida(blocosBytes);
/* 265 */     } catch (IOException e) {
/* 266 */       throw new ConversaoException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static ObjetoAbecs montaGoxResAbecs(SaidaComandoGoOnChip saida) {
/* 272 */     byte[] valueAbecs = new byte[6];
/* 273 */     SaidaComandoGoOnChip.ResultadoProcessamentoEMV res = saida.obtemResultadoProcessamentoEMV();
/* 274 */     for (int i = 0; i < valueAbecs.length; ) { valueAbecs[i] = 48; i++; }
/* 275 */      if (res == SaidaComandoGoOnChip.ResultadoProcessamentoEMV.TRANSACAO_APROVADA_OFFLINE) {
/* 276 */       valueAbecs[0] = 48;
/* 277 */     } else if (res == SaidaComandoGoOnChip.ResultadoProcessamentoEMV.TRANSACAO_NEGADA_OFFLINE) {
/* 278 */       valueAbecs[0] = 49;
/*     */     } else {
/* 280 */       valueAbecs[0] = 50;
/* 281 */     }  if (saida.deveColetarAssinaturaPapel()) valueAbecs[1] = 49; 
/* 282 */     if (saida.obtemModoCapturaPIN() == SaidaComandoGoOnChip.ModoCapturaPIN.PIN_VERIFICADO_OFFLINE) {
/* 283 */       valueAbecs[2] = 49;
/* 284 */     } else if (saida.obtemModoCapturaPIN() == SaidaComandoGoOnChip.ModoCapturaPIN.PIN_DEVE_SER_VERIFICADO_ONLINE) {
/* 285 */       valueAbecs[2] = 50;
/* 286 */     }  return ConverteByteDadosObjeto.montaObjetoPorValueAbecs(valueAbecs, 32854);
/*     */   }
/*     */   private static ObjetoAbecs montaPinBlockAbecs(SaidaComandoGoOnChip saida) {
/* 289 */     return ConverteByteDadosObjeto.montaObjetoPorValueAbecs(saida.obtemPinCriptografado(), 32855);
/*     */   }
/*     */   private static ObjetoAbecs montaEMVDataAbecs(SaidaComandoGoOnChip saida) {
/* 292 */     return ConverteByteDadosObjeto.montaObjetoPorValueAbecs(saida.obtemDadosEMV(), 32852);
/*     */   }
/*     */   private static ObjetoAbecs montaKsnAbecs(SaidaComandoGoOnChip saida) {
/* 295 */     return ConverteByteDadosObjeto.montaObjetoPorValueAbecs(saida.obtemKsn(), 32844);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String hexByteArrayToString(byte[] array) {
/* 300 */     if (array == null || array.length == 0) {
/* 301 */       return "NULL";
/*     */     }
/* 303 */     char[] hexArray = "0123456789ABCDEF".toCharArray();
/* 304 */     char[] hexChars = new char[array.length * 2];
/* 305 */     for (int j = 0; j < array.length; j++) {
/* 306 */       int v = array[j] & 0xFF;
/* 307 */       hexChars[j * 2] = hexArray[v >>> 4];
/* 308 */       hexChars[j * 2 + 1] = hexArray[v & 0xF];
/*     */     } 
/* 310 */     return new String(hexChars);
/*     */   }
/*     */   
/*     */   public static byte[] saidaGoOnChipParaByteArrayObsoleto(SaidaComandoGoOnChip saida) throws ConversaoException {
/* 314 */     if (saida.obtemResultadoOperacao() == CodigosRetorno.OK) {
/*     */       try {
/* 316 */         ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 317 */         byte[] pinCripto = saida.obtemPinCriptografado(), ksn = saida.obtemKsn(), emvData = saida.obtemDadosEMV();
/* 318 */         int len = 48;
/* 319 */         if (emvData != null)
/* 320 */           len += emvData.length * 2; 
/* 321 */         buffer.write(ConverteByteDadosObjeto.inteiroParaByteArrayFormatoN(len, 3));
/* 322 */         montaDecision(buffer, saida.obtemResultadoProcessamentoEMV());
/* 323 */         if (saida.deveColetarAssinaturaPapel()) {
/* 324 */           buffer.write(49);
/*     */         } else {
/* 326 */           buffer.write(48);
/* 327 */         }  if (saida.obtemModoCapturaPIN() == SaidaComandoGoOnChip.ModoCapturaPIN.PIN_VERIFICADO_OFFLINE) {
/* 328 */           buffer.write(49);
/*     */         } else {
/* 330 */           buffer.write(48);
/*     */         } 
/*     */         
/* 333 */         buffer.write(new byte[] { 48, 48 });
/* 334 */         if (saida.obtemModoCapturaPIN() == SaidaComandoGoOnChip.ModoCapturaPIN.PIN_DEVE_SER_VERIFICADO_ONLINE && pinCripto != null && ksn != null) {
/*     */           
/* 336 */           buffer.write(49);
/* 337 */           buffer.write(hexByteArrayToString(pinCripto).getBytes());
/* 338 */           buffer.write(hexByteArrayToString(ksn).getBytes());
/*     */         } else {
/* 340 */           buffer.write(48);
/* 341 */           byte[] aux = new byte[36];
/* 342 */           for (int i = 0; i < aux.length; ) { aux[i] = 48; i++; }
/* 343 */            buffer.write(aux);
/*     */         } 
/* 345 */         buffer.write(ConverteByteDadosObjeto.inteiroParaByteArrayFormatoN((len - 48) / 2, 3));
/* 346 */         if (emvData != null)
/* 347 */           buffer.write(hexByteArrayToString(emvData).getBytes()); 
/* 348 */         buffer.write(new byte[] { 48, 48, 48 });
/* 349 */         return buffer.toByteArray();
/* 350 */       } catch (Exception e) {
/* 351 */         throw new ConversaoException(e);
/*     */       } 
/*     */     }
/* 354 */     return null;
/*     */   }
/*     */   
/*     */   private static void montaDecision(ByteArrayOutputStream buffer, SaidaComandoGoOnChip.ResultadoProcessamentoEMV resEmv) {
/* 358 */     switch (resEmv) {
/*     */       case TRANSACAO_APROVADA_OFFLINE:
/* 360 */         buffer.write(48);
/*     */         return;
/*     */       case TRANSACAO_NEGADA_OFFLINE:
/* 363 */         buffer.write(49);
/*     */         return;
/*     */     } 
/* 366 */     buffer.write(50);
/*     */   }
/*     */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\helper\ConversorComandoGoOnChip.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */