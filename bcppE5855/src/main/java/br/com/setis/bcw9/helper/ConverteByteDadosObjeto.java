/*     */ package br.com.setis.bcw9.helper;
/*     */ 
/*     */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
/*     */ final class ConverteByteDadosObjeto
/*     */ {
/*     */   static int retornaTamanhoComandoFormatoN(byte[] bytes, int tamFormato) {
/*  32 */     byte[] byteArrayComBytesDeTamanho = new byte[tamFormato];
/*  33 */     System.arraycopy(bytes, 0, byteArrayComBytesDeTamanho, 0, tamFormato);
/*  34 */     return byteArrayFormatoNParaInt(byteArrayComBytesDeTamanho);
/*     */   }
/*     */   
/*     */   static int byteArrayFormatoNParaInt(byte[] bytesASeremConvertidos) {
/*  38 */     int retorno = 0;
/*  39 */     for (int i = bytesASeremConvertidos.length - 1; i >= 0; i--) {
/*  40 */       retorno = (int)(retorno + (bytesASeremConvertidos[i] - 48) * Math.pow(10.0D, (bytesASeremConvertidos.length - i - 1)));
/*     */     }
/*  42 */     return retorno;
/*     */   }
/*     */   
/*     */   static int trechoDeByteArrayFormatoNParaInt(byte[] bytesASeremConvertidos, int indexInicial, int tamanho) {
/*  46 */     byte[] aux = new byte[tamanho];
/*  47 */     System.arraycopy(bytesASeremConvertidos, indexInicial, aux, 0, tamanho);
/*  48 */     return byteArrayFormatoNParaInt(aux);
/*     */   }
/*     */   
/*     */   static long trechoDeByteArrayFormatoNParaLong(byte[] bytesASeremConvertidos, int indexInicial, int tamanho) {
/*  52 */     byte[] aux = new byte[tamanho];
/*  53 */     System.arraycopy(bytesASeremConvertidos, indexInicial, aux, 0, tamanho);
/*  54 */     return byteArrayFormatoNParaLong(aux);
/*     */   }
/*     */   
/*     */   static long byteArrayFormatoNParaLong(byte[] bytesASeremConvertidos) {
/*  58 */     long retorno = 0L;
/*  59 */     for (int i = bytesASeremConvertidos.length - 1; i >= 0; i--) {
/*  60 */       retorno = (long)(retorno + (bytesASeremConvertidos[i] - 48) * Math.pow(10.0D, (bytesASeremConvertidos.length - i - 1)));
/*     */     }
/*  62 */     return retorno;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String retornaStringDeByteArrayFormatoSEspacosFinalCortados(byte[] value) {
/*     */     int iFinal;
/*  73 */     for (iFinal = value.length - 1; iFinal > -1 && 
/*  74 */       value[iFinal] == 32; iFinal--);
/*     */ 
/*     */     
/*  77 */     byte[] aux = new byte[iFinal + 1];
/*  78 */     System.arraycopy(value, 0, aux, 0, iFinal + 1);
/*  79 */     return new String(aux, Charset.forName("ISO-8859-1"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String retornaStringDeByteArrayFormatoS(byte[] value) {
/*  89 */     int iFinal = value.length;
/*  90 */     byte[] aux = new byte[iFinal];
/*  91 */     System.arraycopy(value, 0, aux, 0, iFinal);
/*  92 */     return new String(aux, Charset.forName("ISO-8859-1"));
/*     */   }
/*     */   
/*     */   static byte[] retornaByteArrayFormatoSDeString(String str, int tamanhoCampo, boolean tamanhoVariavel) throws UnsupportedEncodingException {
/*  96 */     return retornaByteArrayFormatoQualquerDeString(str, "ISO-8859-1", tamanhoCampo, tamanhoVariavel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String retornaStringDeByteArrayFormatoA(byte[] value) {
/* 106 */     return new String(value, Charset.forName("US-ASCII"));
/*     */   }
/*     */   
/*     */   static byte[] retornaByteArrayFormatoADeString(String str, int tamanhoCampo, boolean tamanhoVariavel) throws UnsupportedEncodingException {
/* 110 */     return retornaByteArrayFormatoQualquerDeString(str, "US-ASCII", tamanhoCampo, tamanhoVariavel);
/*     */   }
/*     */   
/*     */   private static byte[] retornaByteArrayFormatoQualquerDeString(String str, String formato, int tamanhoCampo, boolean tamanhoVariavel) throws UnsupportedEncodingException {
/* 114 */     int strLen = str.length();
/* 115 */     if (strLen > tamanhoCampo)
/* 116 */       return str.substring(0, tamanhoCampo).getBytes(formato); 
/* 117 */     if (strLen == tamanhoCampo) {
/* 118 */       return str.getBytes(formato);
/*     */     }
/* 120 */     if (tamanhoVariavel)
/* 121 */       return str.getBytes(formato); 
/* 122 */     byte[] retorno = new byte[tamanhoCampo];
/* 123 */     System.arraycopy(str.getBytes(formato), 0, retorno, 0, strLen);
/* 124 */     for (int i = strLen; i < retorno.length; ) { retorno[i] = 32; i++; }
/* 125 */      return retorno;
/*     */   }
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
/*     */   static ObjetoAbecs retiraUmObjetoAbecs(byte[] dados, int index) {
/* 142 */     ObjetoAbecs retorno = new ObjetoAbecs();
/* 143 */     retorno.informaTag(dados[index] * 16 + dados[++index]);
/* 144 */     retorno.informalen(dados[++index] * 16 + dados[++index]);
/* 145 */     index++;
/* 146 */     byte[] aux = new byte[retorno.obtemLen()];
/* 147 */     for (int i = index; i < index + retorno.obtemLen(); i++) {
/* 148 */       aux[i - index] = dados[i];
/*     */     }
/* 150 */     retorno.informaValue(aux);
/* 151 */     return retorno;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ArrayList<ObjetoAbecs> byteArrayAbecsParaListaOrdenadaObjetosAbecs(byte[] bytes) {
/* 162 */     int index = 0;
/*     */     
/* 164 */     ArrayList<ObjetoAbecs> listaObjetosAbecs = new ArrayList<>();
/* 165 */     while (index < bytes.length) {
/* 166 */       int indexFinalBloco = index + retornaTamanhoComandoFormatoN(bytes, 3);
/* 167 */       index += 3;
/* 168 */       while (index < indexFinalBloco) {
/* 169 */         ObjetoAbecs objeto = retiraUmObjetoAbecs(bytes, index);
/* 170 */         listaObjetosAbecs.add(objeto);
/* 171 */         index += objeto.obtemLen() + 4;
/*     */       } 
/*     */     } 
/* 174 */     return listaObjetosAbecs;
/*     */   }
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
/*     */   private static byte[] juntaBytesCampoFormatoA(byte[] retorno, byte[] campo, int indexInicial, int indexFinal) {
/* 191 */     byte[] juntos = (byte[])retorno.clone();
/* 192 */     int j = 0; int i;
/* 193 */     for (i = indexInicial; j < campo.length && i < indexFinal; i++) {
/* 194 */       juntos[i] = campo[j];
/* 195 */       j++;
/*     */     } 
/* 197 */     for (; i < indexFinal; i++) {
/* 198 */       juntos[i] = 32;
/*     */     }
/* 200 */     return juntos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] juntaBytesCampoFormatoN(byte[] retorno, byte[] campo, int indexInicial, int indexFinal) {
/* 212 */     byte[] juntos = (byte[])retorno.clone();
/* 213 */     int j = campo.length - 1; int i;
/* 214 */     for (i = indexFinal - 1; j >= 0 && i >= 0; i--) {
/* 215 */       juntos[i] = campo[j];
/* 216 */       j--;
/*     */     } 
/* 218 */     for (; i >= indexInicial; i--) {
/* 219 */       juntos[i] = 48;
/*     */     }
/* 221 */     return juntos;
/*     */   }
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
/*     */   static byte[] juntaBytes(byte[] retorno, byte[] campo, int indexInicial, int indexFinal, char formato) {
/* 234 */     switch (formato) {
/*     */       case 'A':
/* 236 */         return juntaBytesCampoFormatoA(retorno, campo, indexInicial, indexFinal);
/*     */       case 'S':
/* 238 */         return null;
/*     */       case 'N':
/* 240 */         return juntaBytesCampoFormatoN(retorno, campo, indexInicial, indexFinal);
/*     */       case 'H':
/* 242 */         return null;
/*     */       case 'X':
/* 244 */         return null;
/*     */       case 'B':
/* 246 */         return null;
/*     */     } 
/* 248 */     return null;
/*     */   }
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
/*     */   static void insereBytesObjetoAbecs(ArrayList<ByteArrayOutputStream> bufferBlocos, ObjetoAbecs abecs) throws IOException {
/* 265 */     if (abecs == null || abecs.obtemValue() == null)
/*     */       return; 
/* 267 */     int indexBloco = bufferBlocos.size() - 1;
/*     */     
/* 269 */     int tamanhoComObjeto = ((ByteArrayOutputStream)bufferBlocos.get(indexBloco)).size() + abecs.obtemLen() + 4;
/* 270 */     if (tamanhoComObjeto > 999) {
/* 271 */       bufferBlocos.add(new ByteArrayOutputStream());
/* 272 */       indexBloco++;
/*     */     } 
/* 274 */     byte[] aux = new byte[0], dados = ((ByteArrayOutputStream)bufferBlocos.get(indexBloco)).toByteArray();
/* 275 */     if (dados.length > 4) {
/* 276 */       aux = new byte[dados.length - 3];
/* 277 */       System.arraycopy(((ByteArrayOutputStream)bufferBlocos.get(indexBloco)).toByteArray(), 3, aux, 0, aux.length);
/*     */     } 
/* 279 */     ((ByteArrayOutputStream)bufferBlocos.get(indexBloco)).reset();
/* 280 */     ((ByteArrayOutputStream)bufferBlocos.get(indexBloco)).write(inteiroParaByteArrayFormatoN(abecs.obtemLen() + 4 + aux.length, 3));
/* 281 */     ((ByteArrayOutputStream)bufferBlocos.get(indexBloco)).write(aux);
/*     */     
/* 283 */     ((ByteArrayOutputStream)bufferBlocos.get(indexBloco)).write(inteiroParaByteArrayFormatoX(abecs.obtemTag(), 2));
/* 284 */     ((ByteArrayOutputStream)bufferBlocos.get(indexBloco)).write(inteiroParaByteArrayFormatoX(abecs.obtemLen(), 2));
/* 285 */     ((ByteArrayOutputStream)bufferBlocos.get(indexBloco)).write(abecs.obtemValue());
/*     */   }
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
/*     */   static byte[] juntaBlocosERetornaByteArraySaida(ArrayList<ByteArrayOutputStream> lista) throws IOException {
/* 298 */     ByteArrayOutputStream montaRetorno = new ByteArrayOutputStream();
/* 299 */     for (ByteArrayOutputStream bloco : lista) {
/* 300 */       montaRetorno.write(bloco.toByteArray());
/*     */     }
/* 302 */     return montaRetorno.toByteArray();
/*     */   }
/*     */   
/*     */   static byte[] inteiroParaByteArrayFormatoX(int inteiro, int tam) {
/* 306 */     byte[] retorno = new byte[tam];
/*     */     
/* 308 */     for (int i = 0; i < tam; i++) {
/* 309 */       double resultado = inteiro % Math.pow(256.0D, (i + 1));
/* 310 */       resultado = (resultado - resultado % Math.pow(256.0D, i)) / Math.pow(256.0D, i);
/* 311 */       retorno[tam - i - 1] = (byte)(int)resultado;
/*     */     } 
/* 313 */     return retorno;
/*     */   }
/*     */   
/*     */   static byte[] inteiroParaByteArrayFormatoN(int inteiro, int tam) {
/* 317 */     byte[] retorno = new byte[tam];
/*     */     
/* 319 */     for (int i = 0; i < tam; i++) {
/* 320 */       double resultado = inteiro % Math.pow(10.0D, (i + 1));
/* 321 */       resultado = (resultado - resultado % Math.pow(10.0D, i)) / Math.pow(10.0D, i);
/* 322 */       retorno[tam - i - 1] = (byte)(int)(resultado + 48.0D);
/*     */     } 
/* 324 */     return retorno;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ObjetoAbecs montaObjetoPorValueAbecs(byte[] valueAbecs, int tag) {
/* 336 */     if (valueAbecs == null || valueAbecs == new byte[0])
/* 337 */       return null; 
/* 338 */     ObjetoAbecs retorno = new ObjetoAbecs();
/* 339 */     retorno.informaValue(valueAbecs);
/* 340 */     retorno.informalen(valueAbecs.length);
/* 341 */     retorno.informaTag(tag);
/* 342 */     return retorno;
/*     */   }
/*     */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\helper\ConverteByteDadosObjeto.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */