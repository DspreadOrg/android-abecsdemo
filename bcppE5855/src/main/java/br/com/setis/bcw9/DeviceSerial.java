/*     */ package br.com.setis.bcw9;
/*     */ 
/*     */ import android.util.Log;
/*     */

import br.com.setis.bcw9.tasks.TaskDirectCommand;
/*     */
/*     */ import br.com.setis.bcw9.util.Util;
import br.com.setis.bibliotecapinpad.AcessoDiretoPinpad;
/*     */ import br.com.setis.bibliotecapinpad.InterfaceUsuarioPinpad;
/*     */
import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeviceSerial
/*     */   extends AcessoDiretoPinpad
/*     */   implements TaskDirectCommand.DirectCommandCallback
/*     */ {
/*     */   private static final String TAG = "DeviceSerial";
/*     */   private static final int ACK = 6;
/*     */   private static final int SYN = 22;
/*     */   private static final int ETB = 23;
/*     */   private static final int NAK = 21;
/*     */   private static final int CAN = 24;
/*     */   private static final int EOT = 4;
/*     */   private boolean pendingACK = false;
/*     */   private boolean pendingNAK = false;
/*     */   private boolean commandAborted = false;
/*     */   private boolean commandInProgress = false;
/*     */   private boolean isResponseReady = false;
            private boolean sendError = false;
/*     */   private Stack<byte[]> pendingCmd;
/*     */   private RetornoDeviceSerial retornoDeviceSerial;
/*     */   
/*     */   public class RetornoDeviceSerial
/*     */   {
/*     */     private int returnCode;
/*     */     private byte[] answer;
/*     */     private Boolean waitingAnswer;
/*     */     
/*     */     public void RetornoDeviceSerial() {
/*  39 */       if (BuildConfig.DEBUG) Log.d("DeviceSerial", "RetornoDeviceSerial - construtor - waitingAnswer = true!!"); 
/*  40 */       this.waitingAnswer = Boolean.valueOf(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void SetAnswer(byte[] answer, int len, int returnCode) {
/*  45 */       if (returnCode < 0) {
/*  46 */         if (BuildConfig.DEBUG) Log.d("DeviceSerial", "SetAnswer - returnCode < 0"); 
/*  47 */         this.returnCode = returnCode;
/*  48 */         this.answer = null;
/*  49 */         this.waitingAnswer = Boolean.valueOf(false);
/*     */         return;
/*     */       } 
/*  52 */       if (BuildConfig.DEBUG) Log.d("DeviceSerial", "SetAnswer - returnCode >= 0"); 
/*  53 */       this.returnCode = 0;
/*  54 */       this.answer = new byte[len];
/*  55 */       System.arraycopy(answer, 0, this.answer, 0, len);
/*  56 */       this.waitingAnswer = Boolean.valueOf(false);
/*     */     }
/*     */     
/*     */     int GetReturnCode() {
/*  60 */       return this.returnCode;
/*     */     }
/*     */     
/*     */     byte[] GetAnswer() {
/*  64 */       return this.answer;
/*     */     }
/*     */     
/*     */     Boolean WaitingAnwer() {
/*  68 */       return this.waitingAnswer;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  73 */   private Boolean receivedCAN = Boolean.valueOf(false);
/*     */   
/*     */   public DeviceSerial(InterfaceUsuarioPinpad interfaceUsuarioPinpad) {
/*  76 */     super(interfaceUsuarioPinpad);
/*  77 */     PPCompAndroidEvents.getInstance().setInterfacePinpad(interfaceUsuarioPinpad);
/*  78 */     this.pendingCmd = (Stack)new Stack<>();
/*     */   }
/*     */   
/*     */   public synchronized void setResponseReady(boolean bool) {
/*  82 */     if (BuildConfig.DEBUG) Log.d("DeviceSerial", "setResponseReady - bool=[" + bool + "]"); 
/*  83 */     this.isResponseReady = bool;
/*     */   }
/*     */   
/*     */   public synchronized boolean isResponseReady() {
/*  87 */     return this.isResponseReady;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int enviaComando(byte[] bytes, int i) {

    Log.i("WecomeActivity11","enviaComando gogo");
/*  94 */     if (BuildConfig.DEBUG) Log.d("DeviceSerial", "enviaComando....");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     if (i < 0) {
/* 103 */       throw new IllegalArgumentException("Buffer de tamanho negativo!");
/*     */     }
/*     */     
/* 106 */     if (bytes[0] == 21) {
/* 107 */       if (BuildConfig.DEBUG) Log.d("DeviceSerial", "SPE envia um NAK!!"); 
/* 108 */       setResponseReady(true);
/* 109 */       return 0;
/*     */     } 
/*     */ 
/*     */     this.sendError = false;
/* 113 */     this.receivedCAN = Boolean.valueOf(this.pendingACK = this.pendingNAK = false);
/* 114 */     this.retornoDeviceSerial = new RetornoDeviceSerial();
/* 115 */     //String input = br.com.setis.bcw9.Util.byte2HexStr(bytes);
               String input = Util.byte2HexStr(bytes);


/*     */     
/* 117 */     if (BuildConfig.DEBUG) Log.d("DeviceSerial", "enviaComando (tamanho=" + bytes.length + ") = [" + input + "]");
/*     */     
/* 119 */     int st = PPCompAndroid.getInstance().PP_CheckSerialization(bytes);
/*     */     
/* 121 */     switch (st) {
/*     */       case 21:
/* 123 */         if (BuildConfig.DEBUG) Log.d("DeviceSerial", "PP_CheckSerialization - NAK!!"); 
/* 124 */         this.pendingNAK = true;
/* 125 */         this.commandAborted = false;
/* 126 */         setResponseReady(true);
/* 127 */         return 0;
/*     */       case 4:
/* 129 */         if (BuildConfig.DEBUG) Log.d("DeviceSerial", "PP_CheckSerialization - EOT!!"); 
/* 130 */         PPCompAndroid.getInstance().PP_AbortSerializedCmd();
/* 131 */         this.commandAborted = (this.receivedCAN = Boolean.valueOf(true)).booleanValue();
/* 132 */         setResponseReady(true);
/* 133 */         return 0;
/*     */     }
              if(st < 0){
                  this.sendError = true;
                  setResponseReady(true);
                  Log.i("bcpp api","an error happen when send data via serial");
                  return 0;
              }
/*     */     
/* 136 */     if (BuildConfig.DEBUG) Log.d("DeviceSerial", "PP_CheckSerialization = " + st);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     if (this.commandInProgress) {
/* 145 */       this.pendingCmd.push(bytes);
/*     */     } else {
/* 147 */       this.commandAborted = false;
/* 148 */       this.commandInProgress = true;
/* 149 */       this.pendingACK = true;
/* 150 */       setResponseReady(true);
/* 151 */       (new TaskDirectCommand(this)).execute(new Void[0]);
/*     */     } 
/*     */     
/* 154 */     return 0;
/*     */   }
/*     */
/*     */ 
/*     */ 
/*     */   
/*     */   public int recebeResposta(byte[] bytes, long l) {
              //Log.i("bcpp api","recebeResposta check ready");
/* 161 */     long startTime = System.currentTimeMillis();
/* 162 */     while (!isResponseReady()) {
/*     */       try {
/* 164 */         Thread.sleep(50L);
/* 165 */       } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */       
/* 168 */       if (System.currentTimeMillis() - startTime > l) {
/* 169 */         return 0;
/*     */       }
/*     */     }

              //Log.i("bcpp api","recebeResposta running");

              if(this.sendError){
                  Log.i("bcpp api","an error happen when receive via serial");
                  return -1;
              }
/*     */     
/* 173 */     if (this.receivedCAN.booleanValue()) {
/* 174 */       bytes[0] = 4;
/* 175 */       if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - EOT!!");
/*     */       
/* 177 */       this.receivedCAN = Boolean.valueOf(false);
/* 178 */       setResponseReady(false);
/* 179 */       return 1;
/*     */     } 
/*     */     
/* 182 */     if (this.pendingACK) {
/* 183 */       bytes[0] = 6;
/* 184 */       if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - ACK!!");
/*     */       
/* 186 */       this.pendingACK = false;
/* 187 */       setResponseReady(false);
/* 188 */       return 1;
/*     */     } 
/*     */     
/* 191 */     if (this.pendingNAK) {
/* 192 */       bytes[0] = 21;
/* 193 */       if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - NAK!!");
/*     */       
/* 195 */       this.pendingNAK = false;
/* 196 */       setResponseReady(false);
/* 197 */       return 1;
/*     */     } 
/*     */ 
/*     */     
/* 201 */     if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - RESPOSTA CHEGOU");
/*     */     
/* 203 */     if (this.retornoDeviceSerial.GetReturnCode() < 0) {
/* 204 */       int st = this.retornoDeviceSerial.GetReturnCode();
/*     */ 
/*     */       
/* 207 */       if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - ERRO =[" + st + "]!!");
/*     */       
/* 209 */       setResponseReady(false);
/* 210 */       return st;
/*     */     } 
/*     */     
/* 213 */     byte[] buff = this.retornoDeviceSerial.GetAnswer();
/*     */     
/* 215 */     if (buff != null && buff.length > 0) {
/* 216 */       if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta- tamanho=" + buff.length);
/*     */       
/* 218 */       System.arraycopy(buff, 0, bytes, 0, buff.length);
/*     */       
/* 220 */       setResponseReady(false);
/*     */       
/* 222 */       if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - FIM tamanho=" + buff.length);
/* 223 */       return buff.length;
/*     */     } 
/*     */     
/* 226 */     if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - Pedido de resposta pela aplicacao ?????");
/* 227 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ComandoDiretoEncerrado(byte[] out, int len, int cmdResult) {
/* 235 */     if (BuildConfig.DEBUG) Log.d("DeviceSerial", String.format("ComandoDiretoEncerrado (cmdResult = %d, len = %d, resp = [%s], aborted = [%b]", new Object[] {
/* 236 */               Integer.valueOf(cmdResult), Integer.valueOf(len), Util.byte2HexStr(out).substring(0, len * 2), Boolean.valueOf(this.commandAborted) }));
/* 237 */     this.commandInProgress = false;
/*     */ 
/*     */ 
/*     */     
/* 241 */     if (!this.commandAborted) {
/* 242 */       this.retornoDeviceSerial.SetAnswer(out, len, cmdResult);
/* 243 */       setResponseReady(true);
/*     */     } 
/*     */     
/* 246 */     if (this.pendingCmd.size() > 0) {
/* 247 */       byte[] cmd = this.pendingCmd.pop();
/* 248 */       enviaComando(cmd, cmd.length);
/*     */     } 
/*     */     
/* 251 */     if (BuildConfig.DEBUG) Log.d("DeviceSerial", "ComandoDiretoEncerrado - FIM"); 
/*     */   }
/*     */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\DeviceSerial.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */