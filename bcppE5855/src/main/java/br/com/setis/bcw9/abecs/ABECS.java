package br.com.setis.bcw9.abecs;

public interface ABECS {
  public static final int ABECS_SPE_IDLIST = 1;
  
  public static final int ABECS_SPE_MTHDPIN = 2;
  
  public static final int ABECS_SPE_MTHDDAT = 3;
  
  public static final int ABECS_SPE_TAGLIST = 4;
  
  public static final int ABECS_SPE_EMVDATA = 5;
  
  public static final int ABECS_SPE_CEXOPT = 6;
  
  public static final int ABECS_SPE_TRACKS = 7;
  
  public static final int ABECS_SPE_OPNDIG = 8;
  
  public static final int ABECS_SPE_KEYIDX = 9;
  
  public static final int ABECS_SPE_WKENC = 10;
  
  public static final int ABECS_SPE_MSGIDX = 11;
  
  public static final int ABECS_SPE_TIMEOUT = 12;
  
  public static final int ABECS_SPE_MINDIG = 13;
  
  public static final int ABECS_SPE_MAXDIG = 14;
  
  public static final int ABECS_SPE_DATAIN = 15;
  
  public static final int ABECS_SPE_ACQREF = 16;
  
  public static final int ABECS_SPE_APPTYPE = 17;
  
  public static final int ABECS_SPE_AIDLIST = 18;
  
  public static final int ABECS_SPE_AMOUNT = 19;
  
  public static final int ABECS_SPE_CASHBACK = 20;
  
  public static final int ABECS_SPE_TRNDATE = 21;
  
  public static final int ABECS_SPE_TRNTIME = 22;
  
  public static final int ABECS_SPE_GCXOPT = 23;
  
  public static final int ABECS_SPE_GOXOPT = 24;
  
  public static final int ABECS_SPE_FCXOPT = 25;
  
  public static final int ABECS_SPE_TRNTYPE = 33;
  
  public static final int ABECS_SPE_TRMPAR = 26;
  
  public static final int ABECS_SPE_DSPMSG = 27;
  
  public static final int ABECS_SPE_ARC = 28;
  
  public static final int ABECS_SPE_IVCBC = 29;
  
  public static final int ABECS_SPE_MFNAME = 30;
  
  public static final int ABECS_SPE_MFINFO = 31;
  
  public static final int ABECS_SPE_MNUOPT = 32;
  
  public static final int ABECS_SPE_TRNCURR = 34;
  
  public static final int ABECS_PP_SERNUM = 32769;
  
  public static final int ABECS_PP_PARTNBR = 32770;
  
  public static final int ABECS_PP_MODEL = 32771;
  
  public static final int ABECS_PP_MNNAME = 32772;
  
  public static final int ABECS_PP_CAPAB = 32773;
  
  public static final int ABECS_PP_SOVER = 32774;
  
  public static final int ABECS_PP_SPECVER = 32775;
  
  public static final int ABECS_PP_MANVERS = 32776;
  
  public static final int ABECS_PP_APPVERS = 32777;
  
  public static final int ABECS_PP_GENVERS = 32778;
  
  public static final int ABECS_PP_KRNLVER = 32784;
  
  public static final int ABECS_PP_CTLSVER = 32785;
  
  public static final int ABECS_PP_MCTLSVER = 32786;
  
  public static final int ABECS_PP_VCTLSVER = 32787;
  
  public static final int ABECS_PP_AECTLSVER = 32788;
  
  public static final int ABECS_PP_DSPTXTSZ = 32800;
  
  public static final int ABECS_PP_DSPGRSZ = 32801;
  
  public static final int ABECS_PP_MFSUP = 32802;
  
  public static final int ABECS_PP_MKDESP = 32816;
  
  public static final int ABECS_PP_MKDESD = 32817;
  
  public static final int ABECS_PP_MKTDESP = 32818;
  
  public static final int ABECS_PP_MKTDESD = 32819;
  
  public static final int ABECS_PP_DKPTDESP = 32820;
  
  public static final int ABECS_PP_DKPTTDESP = 32821;
  
  public static final int ABECS_PP_DKPTTDESD = 32822;
  
  public static final int ABECS_PP_EVENT = 32832;
  
  public static final int ABECS_PP_TRK1INC = 32833;
  
  public static final int ABECS_PP_TRK2INC = 32834;
  
  public static final int ABECS_PP_TRK3INC = 32835;
  
  public static final int ABECS_PP_TRACK1 = 32836;
  
  public static final int ABECS_PP_TRACK2 = 32837;
  
  public static final int ABECS_PP_TRACK3 = 32838;
  
  public static final int ABECS_PP_TRK1KSN = 32839;
  
  public static final int ABECS_PP_TRK2KSN = 32840;
  
  public static final int ABECS_PP_TRK3KSN = 32841;
  
  public static final int ABECS_PP_ENCPAN = 32842;
  
  public static final int ABECS_PP_ENCPANKSN = 32843;
  
  public static final int ABECS_PP_KSN = 32844;
  
  public static final int ABECS_PP_VALUE = 32845;
  
  public static final int ABECS_PP_DATAOUT = 32846;
  
  public static final int ABECS_PP_CARDTYPE = 32847;
  
  public static final int ABECS_PP_ICCSTAT = 32848;
  
  public static final int ABECS_PP_AIDTABINFO = 32849;
  
  public static final int ABECS_PP_PAN = 32850;
  
  public static final int ABECS_PP_PANSEQNO = 32851;
  
  public static final int ABECS_PP_EMVDATA = 32852;
  
  public static final int ABECS_PP_CHNAME = 32853;
  
  public static final int ABECS_PP_GOXRES = 32854;
  
  public static final int ABECS_PP_PINBLK = 32855;
  
  public static final int ABECS_PP_FCXRES = 32856;
  
  public static final int ABECS_PP_ISRESULTS = 32857;
  
  public static final int ABECS_PP_BIGRAND = 32858;
  
  public static final int ABECS_PP_LABEL = 32859;
  
  public static final int ABECS_PP_ISSCNTRY = 32860;
  
  public static final int ABECS_PP_CARDEXP = 32861;
  
  public static final int ABECS_PP_MFNAME = 32862;
  
  public static final int ABECS_PP_KSNDESP00 = 36864;
  
  public static final int ABECS_PP_KSNDESP99 = 36963;
  
  public static final int ABECS_PP_KSNTDESP00 = 37120;
  
  public static final int ABECS_PP_KSNTDESP99 = 37219;
  
  public static final int ABECS_PP_KSNTDESD00 = 37376;
  
  public static final int ABECS_PP_KSNTDESD99 = 37475;
  
  public static final int ABECS_PP_TABVER00 = 37632;
  
  public static final int ABECS_PP_TABVER99 = 37731;
}


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\abecs\ABECS.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */