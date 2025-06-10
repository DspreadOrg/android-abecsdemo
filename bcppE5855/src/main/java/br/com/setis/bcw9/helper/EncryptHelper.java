/*    */ package br.com.setis.bcw9.helper;
/*    */ 
/*    */

import br.com.setis.bcw9.RetornoPinpad;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import br.com.setis.bibliotecapinpad.definicoes.CodigosRetorno;
import br.com.setis.bibliotecapinpad.definicoes.ModoCriptografia;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoEncryptBuffer;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoEncryptBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EncryptHelper
/*    */ {
/*    */   public static StringBuilder inputDukpt(EntradaComandoEncryptBuffer entrada) {
/* 20 */     StringBuilder input = new StringBuilder();
/* 21 */     if (entrada.obtemModoCriptografia() == ModoCriptografia.DUKPT_3DES || entrada.obtemModoCriptografia() == ModoCriptografia.DUKPT_DES) {
/* 22 */       int sizeDataIn = (new String(entrada.obtemBlocoDados(), StandardCharsets.ISO_8859_1)).length();
/* 23 */       input.append("03")
/* 24 */         .append(String.format(Locale.getDefault(), "%03d", new Object[] { Integer.valueOf(sizeDataIn + 5)
/* 25 */             })).append("03")
/* 26 */         .append("3")
/* 27 */         .append(String.format(Locale.getDefault(), "%02d", new Object[] { Integer.valueOf(entrada.obtemIndiceChave())
/* 28 */             })).append(new String(entrada.obtemBlocoDados(), StandardCharsets.ISO_8859_1));
/*    */     } 
/*    */     
/* 31 */     return input;
/*    */   }
/*    */   
/*    */   public static StringBuilder inputMK(EntradaComandoEncryptBuffer entrada) {
/* 35 */     StringBuilder input = new StringBuilder();
/* 36 */     int modoCriptografia = 0;
/* 37 */     if (entrada.obtemModoCriptografia() == ModoCriptografia.MK_WK_3DES || entrada.obtemModoCriptografia() == ModoCriptografia.MK_WK_DES) {
/* 38 */       if (entrada.obtemModoCriptografia() == ModoCriptografia.MK_WK_DES)
/* 39 */         modoCriptografia = 0; 
/* 40 */       if (entrada.obtemModoCriptografia() == ModoCriptografia.MK_WK_3DES) {
/* 41 */         modoCriptografia = 1;
/*    */       }
/* 43 */       input.append(String.format("%d", new Object[] { Integer.valueOf(modoCriptografia)
/* 44 */             })).append(String.format("%02d", new Object[] { Integer.valueOf(entrada.obtemIndiceChave())
/* 45 */             })).append(String.format("%-32s", new Object[] { new String(entrada.obtemWorkingKey(), StandardCharsets.ISO_8859_1)
/* 46 */             })).append(String.format("%16s", new Object[] { new String(entrada.obtemBlocoDados(), StandardCharsets.ISO_8859_1) }));
/*    */     } 
/* 48 */     return input;
/*    */   }
/*    */   
/*    */   public static SaidaComandoEncryptBuffer outputDukpt(int iSt, String result, ModoCriptografia modoCriptografia) {
/* 52 */     SaidaComandoEncryptBuffer saida = new SaidaComandoEncryptBuffer();
/* 53 */     saida.informaResultadoOperacao(RetornoPinpad.parseCodigoRetorno(Integer.valueOf(iSt)));
/* 54 */     if (saida.obtemResultadoOperacao() == CodigosRetorno.OK) {
/* 55 */       int size = Integer.parseInt(result.substring(0, 2));
/* 56 */       saida.informaKSN(result.substring(3, 23).getBytes());
/* 57 */       saida.informaDadosCriptografados(result.substring(23, (size - 23) * 2 + 23).getBytes());
/*    */     } 
/* 59 */     return saida;
/*    */   }
/*    */   
/*    */   public static SaidaComandoEncryptBuffer outputMK(int iSt, String result, ModoCriptografia modoCriptografia) {
/* 63 */     SaidaComandoEncryptBuffer saida = new SaidaComandoEncryptBuffer();
/* 64 */     saida.informaResultadoOperacao(RetornoPinpad.parseCodigoRetorno(Integer.valueOf(iSt)));
/* 65 */     if (saida.obtemResultadoOperacao() == CodigosRetorno.OK) {
/* 66 */       saida.informaDadosCriptografados(result.substring(0, 16).getBytes());
/*    */     }
/* 68 */     return saida;
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\helper\EncryptHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */