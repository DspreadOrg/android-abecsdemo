/*     */ package br.com.setis.bcw9.util;
/*     */ 
/*     */ import br.com.setis.bcw9.abecs.LibToAPI;
/*     */ import br.com.setis.bibliotecapinpad.definicoes.ModoCriptografia;
/*     */ import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGetCard;
/*     */ import java.io.UnsupportedEncodingException;
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
/*     */ public class Util
/*     */ {
/*  21 */   private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
/*     */ 
/*     */   
/*     */   private static char[] getHexChars(byte[] bytes, boolean flag) {
/*  25 */     char[] hexChars = new char[bytes.length * 2];
/*  26 */     for (int j = 0, i = 0; j < bytes.length; j++, i++) {
/*  27 */       int v = bytes[j] & 0xFF;
/*  28 */       int code = Character.digit(v, 16);
/*  29 */       if (code == -1 || flag) {
/*  30 */         hexChars[i] = hexArray[v >>> 4];
/*  31 */         hexChars[++i] = hexArray[v & 0xF];
/*  32 */         if (!flag)
/*     */         {
/*  34 */           return getHexChars(bytes, true);
/*     */         }
/*     */       } else {
/*  37 */         hexChars[i] = hexArray[code];
/*     */       } 
/*     */     } 
/*  40 */     return hexChars;
/*     */   }
/*     */   
/*     */   public static String bytesToHex(byte[] bytes) {
/*  44 */     int v = bytes[0] & 0xFF;
/*  45 */     boolean flag = (Character.digit(v, 16) == -1);
/*     */     
/*  47 */     char[] hexChars = getHexChars(bytes, flag);
/*  48 */     return (new String(hexChars)).trim();
/*     */   }
/*     */   
/*     */   public static String byte2HexStr(byte[] var0) {
/*  52 */     if (var0 == null) {
/*  53 */       return "";
/*     */     }
/*     */ 
/*     */     
/*  57 */     StringBuilder var2 = new StringBuilder("");
/*     */     
/*  59 */     for (byte b : var0) {
/*  60 */       String var1 = Integer.toHexString(b & 0xFF);
/*  61 */       var2.append((var1.length() == 1) ? ("0" + var1) : var1);
/*     */     } 
/*     */     
/*  64 */     return var2.toString().toUpperCase().trim();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void extractDataFromTrack(int trackNo, String track, SaidaComandoGetCard.DadosCartao cardData) {
/*  70 */     String pan = null, cardholderName = null, dataVencimento = null, serviceCode = null;
/*     */     
/*  72 */     switch (trackNo) {
/*     */       
/*     */       case 1:
/*     */         try {
/*  76 */           track = skipNonNumericChars(track);
/*     */ 
/*     */           
/*  79 */           pan = track.substring(0, Math.min(19, track.indexOf('^')));
/*     */ 
/*     */           
/*  82 */           cardholderName = track.substring(track.indexOf('^') + 1, Math.min(track
/*  83 */                 .indexOf('^') + 26, track.lastIndexOf('^')));
/*     */ 
/*     */           
/*  86 */           dataVencimento = track.substring(track.lastIndexOf('^') + 1, track
/*  87 */               .lastIndexOf('^') + 5);
/*     */ 
/*     */           
/*  90 */           serviceCode = track.substring(track.lastIndexOf('^') + 5, track
/*  91 */               .lastIndexOf('^') + 8);
/*  92 */         } catch (Exception exception) {}
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/*     */         try {
/*  99 */           pan = track.substring(0, Math.min(19, track.indexOf('=')));
/*     */ 
/*     */           
/* 102 */           dataVencimento = track.substring(track.indexOf('=') + 1, track.indexOf('=') + 5);
/*     */ 
/*     */ 
/*     */           
/* 106 */           serviceCode = track.substring(track.indexOf('=') + 5, track.indexOf('=') + 8);
/* 107 */         } catch (Exception exception) {}
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 112 */         throw new IllegalArgumentException("Trilha não suportada!");
/*     */     } 
/*     */ 
/*     */     
/* 116 */     if (pan != null && pan.length() > 0 && (cardData
/* 117 */       .obtemPan() == null || cardData.obtemPan().length() <= 0))
/*     */     {
/* 119 */       cardData.informaPan(pan);
/*     */     }
/*     */     
/* 122 */     if (cardholderName != null && cardholderName.length() > 0 && (cardData
/* 123 */       .obtemNomePortador() == null || cardData.obtemNomePortador().length() <= 0))
/*     */     {
/* 125 */       cardData.informaNomePortador(cardholderName);
/*     */     }
/*     */     
/* 128 */     if (dataVencimento != null && dataVencimento.length() > 0 && cardData.obtemDataVencimento() == null) {
/* 129 */       cardData.informaDataVencimento(LibToAPI.parseDate(dataVencimento, "yyMM"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String BCDtoString(byte[] bcdBytes) {
/* 139 */     char[] bcdChars = new char[bcdBytes.length * 2];
/* 140 */     int index = 0;
/*     */     
/* 142 */     for (byte bcd : bcdBytes) {
/* 143 */       byte high = (byte)(bcd & 0xF0);
/* 144 */       high = (byte)(high >>> 4);
/* 145 */       high = (byte)(high & 0xF);
/* 146 */       byte low = (byte)(bcd & 0xF);
/*     */       
/* 148 */       bcdChars[index++] = (char)(high + 48);
/* 149 */       bcdChars[index++] = (char)(low + 48);
/*     */     } 
/*     */     
/* 152 */     return skipBCDPadding(bcdChars, index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String skipBCDPadding(char[] orig, int len) {
/* 158 */     while (orig[len - 1] == '?') {
/* 159 */       len--;
/*     */     }
/* 161 */     return new String(orig, 0, len);
/*     */   }
/*     */   
/*     */   public static int translateAlgo(ModoCriptografia modoCriptografia) {
/* 165 */     if (modoCriptografia == ModoCriptografia.DUKPT_3DES)
/* 166 */       return 3; 
/* 167 */     if (modoCriptografia == ModoCriptografia.DUKPT_DES)
/* 168 */       return 2; 
/* 169 */     if (modoCriptografia == ModoCriptografia.MK_WK_3DES)
/* 170 */       return 1; 
/* 171 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] hexStringToByteArray(String s) throws IllegalArgumentException {
/* 176 */     if (s == null) {
/* 177 */       throw new IllegalArgumentException("A String nao pode ser nula");
/*     */     }
/* 179 */     int len = s.length();
/* 180 */     if (len % 2 != 0) {
/* 181 */       throw new IllegalArgumentException("A String tem que conter um valor par de caracteres (2 para cada byte)");
/*     */     }
/* 183 */     byte[] data = new byte[len / 2];
/* 184 */     for (int i = 0; i < len; i += 2) {
/* 185 */       byte upperNibble = (byte)Character.digit(s.charAt(i), 16);
/* 186 */       byte lowerNibble = (byte)Character.digit(s.charAt(i + 1), 16);
/* 187 */       if (upperNibble == -1 || lowerNibble == -1) {
/* 188 */         throw new IllegalArgumentException("A String tem que estar dentro do alcance hexa decimal (0-F)");
/*     */       }
/* 190 */       data[i / 2] = (byte)((upperNibble << 4) + lowerNibble);
/*     */     } 
/* 192 */     return data;
/*     */   }
/*     */   
/*     */   public static String adjustString(String orig, int len, boolean fillWithZeros) {
/* 196 */     if (len >= orig.length()) {
/* 197 */       if (fillWithZeros) {
/* 198 */         return orig.replace(' ', '0');
/*     */       }
/* 200 */       return orig;
/*     */     } 
/* 202 */     return orig.substring(0, len);
/*     */   }
/*     */   
/*     */   public static String adjustString(String orig, int len) {
/* 206 */     return adjustString(orig, len, true);
/*     */   }
/*     */   
/*     */   public static String adjustString(byte[] orig, int len) {
/* 210 */     if (orig == null)
/* 211 */       return adjustString(" ", len, true); 
/* 212 */     return adjustString(new String(orig), len, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String parseString(byte[] asciiString) {
/* 217 */     String respData = "";
/*     */     try {
/* 219 */       respData = new String(asciiString, "ISO-8859-1");
/* 220 */     } catch (UnsupportedEncodingException e) {
/* 221 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 224 */     return respData;
/*     */   }
/*     */   
/*     */   private static String skipNonNumericChars(String orig) {
/* 228 */     char[] newString = new char[orig.length()];
/* 229 */     boolean firstNumeric = false;
/* 230 */     int index = 0; char[] arrayOfChar1; int i;
/*     */     byte b;
/* 232 */     for (arrayOfChar1 = orig.toCharArray(), i = arrayOfChar1.length, b = 0; b < i; ) { Character c = Character.valueOf(arrayOfChar1[b]);
/* 233 */       if (!Character.isAlphabetic(c.charValue()) || firstNumeric) {
/*     */ 
/*     */         
/* 236 */         newString[index++] = c.charValue();
/* 237 */         firstNumeric = true;
/*     */       }  b++; }
/*     */     
/* 240 */     return new String(newString);
/*     */   }

/*     */ }



/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw\\util\Util.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */