package br.com.setis.bcw9.abecs;

public interface PPComp {
  public static final int PPCOMP_OK = 0;
  
  public static final int PPCOMP_PROCESSING = 1;
  
  public static final int PPCOMP_NOTIFY = 2;
  
  public static final int PPCOMP_NOSEC = 3;
  
  public static final int PPCOMP_F1 = 4;
  
  public static final int PPCOMP_F2 = 5;
  
  public static final int PPCOMP_F3 = 6;
  
  public static final int PPCOMP_F4 = 7;
  
  public static final int PPCOMP_BACKSP = 8;
  
  public static final int PPCOMP_ERRPKTSEC = 9;
  
  public static final int PPCOMP_INVCALL = 10;
  
  public static final int PPCOMP_INVPARM = 11;
  
  public static final int PPCOMP_TIMEOUT = 12;
  
  public static final int PPCOMP_CANCEL = 13;
  
  public static final int PPCOMP_ALREADYOPEN = 14;
  
  public static final int PPCOMP_NOTOPEN = 15;
  
  public static final int PPCOMP_EXECERR = 16;
  
  public static final int PPCOMP_INVMODEL = 17;
  
  public static final int PPCOMP_NOFUNC = 18;
  
  public static final int PPCOMP_ERRMANDAT = 19;
  
  public static final int PPCOMP_TABEXP = 20;
  
  public static final int PPCOMP_TABERR = 21;
  
  public static final int PPCOMP_NOAPPLIC = 22;
  
  public static final int PPCOMP_PORTERR = 30;
  
  public static final int PPCOMP_COMMERR = 31;
  
  public static final int PPCOMP_UNKNOWNSTAT = 32;
  
  public static final int PPCOMP_RSPERR = 33;
  
  public static final int PPCOMP_COMMTOUT = 34;
  
  public static final int PPCOMP_DATANOTFOUND = 35;
  
  public static final int PPCOMP_INTERR = 40;
  
  public static final int PPCOMP_MCDATAERR = 41;
  
  public static final int PPCOMP_ERRPIN = 42;
  
  public static final int PPCOMP_NOCARD = 43;
  
  public static final int PPCOMP_PINBUSY = 44;
  
  public static final int PPCOMP_RSPOVRFL = 45;
  
  public static final int PPCOMP_ERRCRYPT = 46;
  
  public static final int PPCOMP_SAMERR = 50;
  
  public static final int PPCOMP_NOSAM = 51;
  
  public static final int PPCOMP_SAMINV = 52;
  
  public static final int PPCOMP_DUMBCARD = 60;
  
  public static final int PPCOMP_ERRCARD = 61;
  
  public static final int PPCOMP_CARDINV = 62;
  
  public static final int PPCOMP_CARDNAUTH = 64;
  
  public static final int PPCOMP_CARDEXPIRED = 65;
  
  public static final int PPCOMP_CARDERRSTRUCT = 66;
  
  public static final int PPCOMP_CARDINVALIDAT = 67;
  
  public static final int PPCOMP_CARDPROBLEMS = 68;
  
  public static final int PPCOMP_CARDINVDATA = 69;
  
  public static final int PPCOMP_CARDAPPNAV = 70;
  
  public static final int PPCOMP_CARDAPPNAUT = 71;
  
  public static final int PPCOMP_NOBALANCE = 72;
  
  public static final int PPCOMP_LIMITEXC = 73;
  
  public static final int PPCOMP_CARDNOTEFFECT = 74;
  
  public static final int PPCOMP_VCINVCURR = 75;
  
  public static final int PPCOMP_ERRFALLBACK = 76;
  
  public static final int PPCOMP_INVAMOUNT = 77;
  
  public static final int PPCOMP_ERRMAXAID = 78;
  
  public static final int PPCOMP_CARDBLOCKED = 79;
  
  public static final int PPCOMP_CTLSSMULTIPLE = 80;
  
  public static final int PPCOMP_CTLSSCOMMERR = 81;
  
  public static final int PPCOMP_CTLSSINVALIDAT = 82;
  
  public static final int PPCOMP_CTLSSPROBLEMS = 83;
  
  public static final int PPCOMP_CTLSSAPPNAV = 84;
  
  public static final int PPCOMP_CTLSSAPPNAUT = 85;
  
  public static final int PPCOMP_CTLSEXTCVM = 86;
  
  public static final int PPCOMP_CTLSIFCHG = 87;
  
  public static final int PPCOMP_MFNFOUND = 100;
  
  public static final int PPCOMP_MFERRFMT = 101;
  
  public static final int PPCOMP_MFERR = 102;
  
  public static final int PPCOMP_ABORTED = 154;
  
  public static final int PPCOMP_ERR = 1000;
}


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\abecs\PPComp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */