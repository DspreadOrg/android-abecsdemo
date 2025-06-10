package br.com.setis.bcw9.abecs;

public interface POSPlug {
  public static final int XPLG_OK = 0;
  
  public static final int XPLG_MAXERR = -1;
  
  public static final int XPLG_MINERR = -20;
  
  public static final int XPLG_ALREADYOPEN = -1;
  
  public static final int XPLG_INVHANDLE = -2;
  
  public static final int XPLG_NOFUNC = -3;
  
  public static final int XPLG_INVNAME = -4;
  
  public static final int XPLG_CONFIGERR = -5;
  
  public static final int XPLG_LIBNOTFOUND = -6;
  
  public static final int XPLG_INTERNALERROR = -7;
  
  public static final int XPLG_INVPARAM = -8;
  
  public static final int XPLG_INVMODEL = -9;
  
  public static final int XPLG_INVCMD = -10;
  
  public static final int XPLG_TOOMANYOPEN = -11;
  
  public static final int XPLG_BUFFER_OVFLW = -12;
  
  public static final int XPLG_WRONGVERSION = -13;
  
  public static final int XPLG_INVLICENSE = -14;
  
  public static final int POS_MAXERR = -200;
  
  public static final int POS_MINERR = -299;
  
  public static final int POS_ICCARD_OK = -200;
  
  public static final int POS_MGCARD_OK = -201;
  
  public static final int POS_BACK = -202;
  
  public static final int POS_INVDATE = -203;
  
  public static final int POS_INVTIME = -204;
  
  public static final int POS_HWERR = -205;
  
  public static final int POS_PRERR = -206;
  
  public static final int POS_PRERRVOLT = -207;
  
  public static final int POS_NOPAPER = -208;
  
  public static final int POS_TIMEOUT = -209;
  
  public static final int POS_CANCEL = -210;
  
  public static final int POS_MGCARD_ERR = -211;
  
  public static final int POS_FILE_ERR = -212;
  
  public static final int POS_MOD_ERR = -213;
  
  public static final int POS_NOKEY = -214;
  
  public static final int POS_BACKSP = -215;
  
  public static final int POS_RADIO_ERR = -216;
  
  public static final int POS_CLESS_OK = -217;
  
  public static final int POS_CLESS_ERR = -218;
  
  public static final int POS_NOCARD = -219;
  
  public static final int PIN_MAXERR = -400;
  
  public static final int PIN_MINERR = -499;
  
  public static final int PIN_TIMEOUT = -400;
  
  public static final int PIN_PROCESSING = -401;
  
  public static final int PIN_CANCEL = -402;
  
  public static final int PIN_HWERR = -403;
  
  public static final int PIN_COMM_ERR = -404;
  
  public static final int PIN_MSG_ERR = -405;
  
  public static final int PIN_NO_KEY = -406;
  
  public static final int PIN_MGCARD_ERR = -407;
  
  public static final int PIN_BACK = -408;
  
  public static final int PIN_BYPASS = -409;
  
  public static final int PIN_BUSY = -410;
  
  public static final int POS_KEYCANCEL = 200;
  
  public static final int POS_KEYBACKSP = 201;
  
  public static final int POS_KEYOK = 202;
  
  public static final int POS_KEYF1 = 311;
  
  public static final int POS_KEYF2 = 312;
  
  public static final int POS_KEYF7 = 317;
  
  public static final int POS_KEYF8 = 318;
  
  public static final int POS_KEYSYM = 400;
  
  public static final int POSBEEP_OK = 0;
  
  public static final int POSBEEP_WARN = 1;
  
  public static final int POSBEEP_ERR = 2;
  
  public static final int POSMOD_DIALASYNC = 1;
  
  public static final int POSMOD_DIALSDLC = 2;
  
  public static final int POSMOD_GSMCSD = 3;
  
  public static final int POSMOD_GPRS = 4;
  
  public static final int POSMOD_GPRS_SSL = 5;
  
  public static final int POSMOD_LAN = 6;
  
  public static final int POSMOD_LAN_SSL = 7;
  
  public static final int POSMOD_WIFI = 8;
  
  public static final int POSMOD_WIFI_SSL = 9;
  
  public static final int POSMOD_DIALPPP = 10;
  
  public static final int POSMOD_DIALPPP_SSL = 11;
  
  public static final int MODPAR_RETRY = 9;
  
  public static final int MODPAR_IPADDR = 12;
  
  public static final int MODPAR_HOSTNAME = 15;
  
  public static final int MODPAR_TCPPORT = 16;
  
  public static final int MODPAR_SSLVERSION = 20;
  
  public static final int MODPAR_SSLNAMECHECK = 21;
  
  public static final int MODSTS_SSLNCONN = -17;
  
  public static final int MODSTS_SSLCERTERR = -16;
  
  public static final int MODSTS_SOCKETNCONN = -13;
  
  public static final int MODSTS_OFF = 0;
  
  public static final int MODSTS_REGISTERING = 1;
  
  public static final int MODSTS_CONNECTING = 7;
  
  public static final int MODSTS_CONNECTED = 8;
  
  public static final int POSMAXFILENUM = 99;
  
  public static final int POSEVENT_BEFOREINIT = 0;
  
  public static final int POSEVENT_AFTERINIT = 1;
  
  public static final int POSEVENT_ERROR = 2;
  
  public static final int POSEVENT_KEY = 3;
  
  public static final int POSEVENT_MAG = 4;
  
  public static final int POSEVENT_ICCIN = 5;
  
  public static final int POSEVENT_ICCOUT = 6;
  
  public static final int POSEVENT_TIMER = 7;
  
  public static final int POSEVENT_AGAIN = 8;
  
  public static final int POSEVENT_RXSER = 9;
  
  public static final int POSEVENT_RXMOD = 10;
  
  public static final int POSEVENT_APPLICATION = 100;
  
  public static final int POSEVENT_APP_OPERATION = 100;
  
  public static final int POSEVENT_APP_CONFIRMATION = 101;
  
  public static final int HW_INVPORT = -80;
  
  public static final int HW_COMMTOUT = -81;
  
  public static final int HW_HWERR = -82;
  
  public static final int HW_TXERR = -83;
  
  public static final int HW_RADIO_ERR = -84;
  
  public static final int SSLVERSION_TLSv1 = 1;
  
  public static final int SSLVERSION_TLSv1_1 = 2;
  
  public static final int SSLVERSION_TLSv1_2 = 4;
  
  public static final int SSLVERSION_SSLv2 = 16;
  
  public static final int SSLVERSION_SSLv3 = 32;
}


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\abecs\POSPlug.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */