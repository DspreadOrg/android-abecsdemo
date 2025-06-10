/*     */ package br.com.setis.bcw9;
/*     */
/*     */ import androidx.annotation.NonNull;
import br.com.setis.bcw9.tasks.StartGetCommand;
/*     */ import br.com.setis.bcw9.tasks.StartGetCommandAbecs;
/*     */ import br.com.setis.bcw9.tasks.StartGetTask;
/*     */ import br.com.setis.bcw9.tasks.StartGetTaskAbecs;
/*     */ import br.com.setis.bcw9.tasks.TaskCheckEvent;
/*     */ import br.com.setis.bcw9.tasks.TaskChipDirect;
/*     */ import br.com.setis.bcw9.tasks.TaskClose;
/*     */ import br.com.setis.bcw9.tasks.TaskEncryptBufferAbecs;
/*     */ import br.com.setis.bcw9.tasks.TaskFinishChipAbecs;
/*     */ import br.com.setis.bcw9.tasks.TaskGetCardAbecs;
/*     */ import br.com.setis.bcw9.tasks.TaskGetInfo;
/*     */ import br.com.setis.bcw9.tasks.TaskGetInfoEx;
/*     */ import br.com.setis.bcw9.tasks.TaskGetPIN;
/*     */ import br.com.setis.bcw9.tasks.TaskGoOnChipAbecs;
/*     */ import br.com.setis.bcw9.tasks.TaskOpen;
/*     */ import br.com.setis.bcw9.tasks.TaskRemoveCard;
/*     */ import br.com.setis.bcw9.tasks.TaskTablesLoad;
/*     */ import br.com.setis.bibliotecapinpad.AcessoFuncoesPinpad;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoCheckEvent;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoChipDirect;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoClose;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoEncryptBuffer;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoFinishChip;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGetCard;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGetInfo;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGetInfoEx;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGetPin;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGoOnChip;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoOpen;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoRemoveCard;
/*     */ import br.com.setis.bibliotecapinpad.entradas.EntradaComandoTableLoad;
/*     */ 
/*     */ 
/*     */ public class DeviceAbecs
/*     */   extends AcessoFuncoesPinpad
/*     */ {
/*     */   public void open(@NonNull EntradaComandoOpen entradaComandoOpen, @NonNull EntradaComandoOpen.OpenCallback callback) {
/*  41 */     PPCompAndroidEvents.getInstance().setInterfacePinpad(entradaComandoOpen.obtemInterfaceUsuarioPinpad());
/*  42 */     TaskOpen to = new TaskOpen(PPCompAndroid.getInstance(), callback);
/*  43 */     (new StartGetTask()).execute(new StartGetCommand[] { (StartGetCommand)to });
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(@NonNull EntradaComandoClose entradaComandoClose) {
/*  48 */     TaskClose tc = new TaskClose(PPCompAndroid.getInstance(), entradaComandoClose);
/*  49 */     (new StartGetTask()).execute(new StartGetCommand[] { (StartGetCommand)tc });
/*     */   }
/*     */ 
/*     */   
/*     */   public void abort() {
/*  54 */     PPCompAndroid.getInstance().PP_Abort();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getInfo(EntradaComandoGetInfo entradaComandoGetInfo) {
/*  59 */     TaskGetInfo getInfo = new TaskGetInfo(PPCompAndroid.getInstance());
/*  60 */     getInfo.execute(new EntradaComandoGetInfo[] { entradaComandoGetInfo });
/*     */   }
/*     */ 
/*     */
/*     */   public void getInfoEx(EntradaComandoGetInfoEx entradaComandoGetInfoEx, EntradaComandoGetInfoEx.GetInfoExCallback getInfoExCallback) {
/*  65 */     TaskGetInfoEx getInfoEx = new TaskGetInfoEx(PPCompAndroid.getInstance(), getInfoExCallback);
/*  66 */     getInfoEx.execute(new EntradaComandoGetInfoEx[] { entradaComandoGetInfoEx });
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkEvent(EntradaComandoCheckEvent entradaComandoCheckEvent, EntradaComandoCheckEvent.CheckEventCallback checkEventCallback) {
/*  71 */     TaskCheckEvent ce = new TaskCheckEvent(PPCompAndroid.getInstance(), entradaComandoCheckEvent, checkEventCallback);
/*  72 */     (new StartGetTask()).execute(new StartGetCommand[] { (StartGetCommand)ce });
/*     */   }
/*     */ 
/*     */   
/*     */   public void chipDirect(EntradaComandoChipDirect entradaComandoChipDirect, EntradaComandoChipDirect.ChipDirectCallback chipDirectCallback) {
/*  77 */     TaskChipDirect cd = new TaskChipDirect(PPCompAndroid.getInstance(), entradaComandoChipDirect, chipDirectCallback);
/*  78 */     (new StartGetTask()).execute(new StartGetCommand[] { (StartGetCommand)cd });
/*     */   }
/*     */ 
/*     */   
/*     */   public void encryptBuffer(EntradaComandoEncryptBuffer entradaComandoEncryptBuffer, EntradaComandoEncryptBuffer.EncryptBufferCallback encryptBufferCallback) {
/*  83 */     TaskEncryptBufferAbecs ebx = new TaskEncryptBufferAbecs(PPCompAndroid.getInstance(), encryptBufferCallback);
/*  84 */     ebx.execute(new EntradaComandoEncryptBuffer[] { entradaComandoEncryptBuffer });
/*     */   }
/*     */ 
/*     */   
/*     */   public void getPin(EntradaComandoGetPin entradaComandoGetPin, EntradaComandoGetPin.GetPinCallback getPinCallback) {
/*  89 */     TaskGetPIN tgpn = new TaskGetPIN(PPCompAndroid.getInstance(), entradaComandoGetPin, getPinCallback);
/*  90 */     (new StartGetTask()).execute(new StartGetCommand[] { (StartGetCommand)tgpn });
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeCard(EntradaComandoRemoveCard entradaComandoRemoveCard, EntradaComandoRemoveCard.RemoveCardCallback removeCardCallback) {
/*  95 */     TaskRemoveCard removeCard = new TaskRemoveCard(PPCompAndroid.getInstance(), removeCardCallback);
/*  96 */     (new StartGetTask()).execute(new StartGetCommand[] { (StartGetCommand)removeCard });
/*     */   }
/*     */ 
/*     */   
/*     */   public void tableLoad(EntradaComandoTableLoad entradaComandoTableLoad, EntradaComandoTableLoad.TableLoadCallback tableLoadCallback) {
/* 101 */     TaskTablesLoad tablesLoad = new TaskTablesLoad(PPCompAndroid.getInstance(), entradaComandoTableLoad, tableLoadCallback);
/* 102 */     tablesLoad.execute(new Void[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void getCard(EntradaComandoGetCard entradaComandoGetCard, EntradaComandoGetCard.GetCardCallback getCardCallback) {
/* 107 */     TaskGetCardAbecs gc = new TaskGetCardAbecs(PPCompAndroid.getInstance(), entradaComandoGetCard, getCardCallback);
/* 108 */     (new StartGetTaskAbecs()).execute(new StartGetCommandAbecs[] { (StartGetCommandAbecs)gc });
/*     */   }
/*     */ 
/*     */   
/*     */   public void goOnChip(EntradaComandoGoOnChip entradaComandoGoOnChip, EntradaComandoGoOnChip.GoOnChipCallback goOnChipCallback) {
/* 113 */     TaskGoOnChipAbecs go = new TaskGoOnChipAbecs(PPCompAndroid.getInstance(), entradaComandoGoOnChip, goOnChipCallback);
/* 114 */     (new StartGetTaskAbecs()).execute(new StartGetCommandAbecs[] { (StartGetCommandAbecs)go });
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishChip(EntradaComandoFinishChip entradaComandoFinishChip, EntradaComandoFinishChip.FinishChipCallback finishChipCallback) {
/* 119 */     TaskFinishChipAbecs fc = new TaskFinishChipAbecs(PPCompAndroid.getInstance(), entradaComandoFinishChip, finishChipCallback);
/* 120 */     (new StartGetTaskAbecs()).execute(new StartGetCommandAbecs[] { (StartGetCommandAbecs)fc });
/*     */   }
/*     */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\DeviceAbecs.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */