/*     */ package br.com.setis.bcw9.abecs;
/*     */ 
/*     */
/*     */ import android.util.Log;
/*     */ import br.com.setis.bcw9.BuildConfig;

import androidx.annotation.Nullable;
/*     */
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGoOnChip;
/*     */ import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGetCard;
/*     */ import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGoOnChip;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LibToAPI
/*     */ {
/*  25 */   private static String TAG = "AbecsLibToAPI";
/*     */   
/*     */   public static SaidaComandoGetCard.TipoCartaoLido parseCardType(String cardType) {
/*  28 */     switch (cardType) {
/*     */       case "00":
/*  30 */         return SaidaComandoGetCard.TipoCartaoLido.MAGNETICO;
/*     */       case "03":
/*  32 */         return SaidaComandoGetCard.TipoCartaoLido.EMV_COM_CONTATO;
/*     */       case "05":
/*  34 */         return SaidaComandoGetCard.TipoCartaoLido.TARJA_SEM_CONTATO;
/*     */       case "06":
/*  36 */         return SaidaComandoGetCard.TipoCartaoLido.EMV_SEM_CONTATO;
/*     */     } 
/*  38 */     throw new IllegalArgumentException("O tipo de cartao informado nao e suportado!");
/*     */   }
/*     */ 
/*     */   
/*     */   private static String skipNonNumericChars(String orig) {
/*  43 */     char[] newString = new char[orig.length()];
/*  44 */     boolean firstNumeric = false;
/*  45 */     int index = 0; char[] arrayOfChar1; int i;
/*     */     byte b;
/*  47 */     for (arrayOfChar1 = orig.toCharArray(), i = arrayOfChar1.length, b = 0; b < i; ) { Character c = Character.valueOf(arrayOfChar1[b]);
/*  48 */       if (!Character.isAlphabetic(c.charValue()) || firstNumeric) {
/*     */ 
/*     */         
/*  51 */         newString[index++] = c.charValue();
/*  52 */         firstNumeric = true;
/*     */       }  b++; }
/*     */     
/*  55 */     return new String(newString);
/*     */   }
/*     */   
/*     */   public static Date parseDate(String data, String pattern) {
/*  59 */     Date date = null;
/*     */     
/*  61 */     DateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
/*     */     try {
/*  63 */       date = format.parse(data);
/*  64 */     } catch (ParseException e) {
/*  65 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  68 */     return date;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String adjustString(String orig, int len, char paddingChar) {
/*  73 */     return (len >= orig.length()) ? orig.replace(' ', paddingChar) : orig.substring(orig.length() - len);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String adjustHexStrField(@Nullable byte[] fieldData, int fieldLen, char paddingChar, boolean isLeftAligned) {
/*  79 */     if (fieldData == null) {
/*  80 */       fieldData = new byte[fieldLen];
/*  81 */       Arrays.fill(fieldData, (byte)paddingChar);
/*     */     } 
/*     */     
/*  84 */     String format = (isLeftAligned ? "%-" : "%") + fieldLen + "s";
/*  85 */     return adjustString(String.format(format, new Object[] { new String(fieldData) }), fieldLen, paddingChar);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String adjustHexIntField(int fieldData, int fieldLen, char paddingChar) {
/*  90 */     String format = "%0" + fieldLen + "d";
/*  91 */     return adjustString(String.format(Locale.US, format, new Object[] { Integer.valueOf(fieldData) }), fieldLen, paddingChar);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String formatTrmField(EntradaComandoGoOnChip.ParametrosTerminalRiskManagement paramTRM) {
/*  96 */     StringBuilder sb = new StringBuilder();
/*     */ 
/*     */     
/*  99 */     sb.append(adjustHexStrField(paramTRM.obtemTerminalFloorLimit(), 8, '0', false));
/*     */ 
/*     */     
/* 102 */     sb.append(adjustHexIntField(paramTRM.obtemTargetPercentage(), 2, '0'));
/*     */ 
/*     */     
/* 105 */     sb.append(adjustHexStrField(paramTRM.obtemThresholdValue(), 8, '0', false));
/*     */ 
/*     */     
/* 108 */     sb.append(adjustHexIntField(paramTRM.obtemMaxTargetPercentage(), 2, '0'));
/*     */     
/* 110 */     if (BuildConfig.DEBUG) Log.d(TAG, "TRMPAR     = " + sb.toString());
/* 111 */     if (BuildConfig.DEBUG) Log.d(TAG, "TRMPAR LEN = " + sb.length());
/*     */     
/* 113 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static SaidaComandoGoOnChip.ResultadoProcessamentoEMV getAnalysisDecision(char resp) {
/* 117 */     switch (resp) {
/*     */       case '0':
/* 119 */         return SaidaComandoGoOnChip.ResultadoProcessamentoEMV.TRANSACAO_APROVADA_OFFLINE;
/*     */       case '1':
/* 121 */         return SaidaComandoGoOnChip.ResultadoProcessamentoEMV.TRANSACAO_NEGADA_OFFLINE;
/*     */       case '2':
/* 123 */         return SaidaComandoGoOnChip.ResultadoProcessamentoEMV.TRANSACAO_REQUER_APROVACAO_ONLINE;
/*     */     } 
/* 125 */     throw new IllegalArgumentException("Valor inesperado de decis�o!");
/*     */   }
/*     */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\abecs\LibToAPI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */