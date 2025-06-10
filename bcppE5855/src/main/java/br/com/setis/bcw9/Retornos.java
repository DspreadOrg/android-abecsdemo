/*    */ package br.com.setis.bcw9;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Retornos
/*    */ {
/*  9 */   PP_OK(0),
/* 10 */   PP_PROCESSING(1),
/* 11 */   PP_NOTIFY(2),
/* 12 */   PP_F1(4),
/* 13 */   PP_F2(5),
/* 14 */   PP_F3(6),
/* 15 */   PP_F4(7),
/* 16 */   PP_BACKSP(8),
/*    */   
/* 18 */   PP_INVCALL(10),
/* 19 */   PP_INVPARM(11),
/* 20 */   PP_TIMEOUT(12),
/* 21 */   PP_CANCEL(13),
/* 22 */   PP_ALREADYOPEN(14),
/* 23 */   PP_NOTOPEN(15),
/* 24 */   PP_EXECERR(16),
/* 25 */   PP_INVMODEL(17),
/* 26 */   PP_NOFUNC(18),
/* 27 */   PP_TABEXP(20),
/* 28 */   PP_TABERR(21),
/* 29 */   PP_NOAPPLIC(22),
/*    */   
/* 31 */   PP_PORTERR(30),
/* 32 */   PP_COMMERR(31),
/* 33 */   PP_UNKNOWNSTAT(32),
/* 34 */   PP_RSPERR(33),
/* 35 */   PP_COMMTOUT(34),
/*    */   
/* 37 */   PP_INTERR(40),
/* 38 */   PP_MCDATAERR(41),
/* 39 */   PP_ERRPIN(42),
/* 40 */   PP_NOCARD(43),
/* 41 */   PP_PINBUSY(44),
/*    */   
/* 43 */   PP_SAMERR(50),
/* 44 */   PP_NOSAM(51),
/* 45 */   PP_SAMINV(52),
/*    */   
/* 47 */   PP_DUMBCARD(60),
/* 48 */   PP_ERRCARD(61),
/* 49 */   PP_CARDINV(62),
/* 50 */   PP_CARDBLOCKED(63),
/* 51 */   PP_CARDNAUTH(64),
/* 52 */   PP_CARDEXPIRED(65),
/* 53 */   PP_CARDERRSTRUCT(66),
/* 54 */   PP_CARDINVALIDAT(67),
/* 55 */   PP_CARDPROBLEMS(68),
/* 56 */   PP_CARDINVDATA(69),
/* 57 */   PP_CARDAPPNAV(70),
/* 58 */   PP_CARDAPPNAUT(71),
/* 59 */   PP_NOBALANCE(72),
/* 60 */   PP_LIMITEXC(73),
/* 61 */   PP_CARDNOTEFFECT(74),
/* 62 */   PP_VCINVCURR(75),
/* 63 */   PP_ERRFALLBACK(76),
/*    */   
/* 65 */   PP_CARDAPPBLOCKED(79),
/*    */   
/* 67 */   PP_CTLSSMULTIPLE(80),
/* 68 */   PP_CTLSSCOMMERR(81),
/* 69 */   PP_CTLSSINVALIDAT(82),
/* 70 */   PP_CTLSSPROBLEMS(83),
/* 71 */   PP_CTLSSAPPNAV(84),
/* 72 */   PP_CTLSSAPPNAUT(85),
/* 73 */   PP_CTLSSONDEVICE(86),
/* 74 */   PP_CTLSSIFCHG(87),
/* 75 */   UNKNOWN(999);
/*    */   
/*    */   private int codigo;
/*    */ 
/*    */   
/*    */   Retornos(int codigo) {
/* 81 */     this.codigo = codigo;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Retornos getByInt(int id) {
/* 86 */     for (Retornos r : values()) {
/* 87 */       if (r.codigo == id)
/* 88 */         return r; 
/*    */     } 
/* 90 */     return UNKNOWN;
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\Retornos.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */