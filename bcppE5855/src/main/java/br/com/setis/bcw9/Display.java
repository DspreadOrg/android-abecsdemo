/*    */ package br.com.setis.bcw9;
/*    */ 
/*    */ import android.app.Activity;
/*    */ import android.os.Bundle;
/*    */ import android.util.Log;
/*    */ import android.widget.TextView;
/*    */ //import com.dspread.bcppdemo.R;

import java.text.DecimalFormat;
/*    */ import java.text.DecimalFormatSymbols;
/*    */ import java.text.NumberFormat;
/*    */ import java.util.Locale;

import androidx.annotation.NonNull;

/*    */
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Display
/*    */   extends Activity
/*    */ {
/*    */   private static Display instance;
/*    */   private TextView editText;
/*    */   private boolean isGoc;
/*    */   private String input;
/*    */   private String amount;
/*    */   private String mensagem;
/*    */   
/*    */   public static Display getInstance() {
/* 28 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCreate(Bundle savedInstanceState) {
/* 33 */     super.onCreate(savedInstanceState);
/* 34 */     setContentView(R.layout.activity_display);
/*    */     
/* 36 */     this.isGoc = getIntent().getBooleanExtra("is_goc", false);
/* 37 */     this.input = getIntent().getStringExtra("input");
/*    */     
/* 39 */     this.editText = (TextView)findViewById(R.id.bcw9_pin);
/* 40 */     this.mensagem = this.input;
/* 41 */     this.editText.setText(this.input);
/* 42 */     instance = this;
/*    */   }
/*    */   
/*    */   public void updateText(int numDigits, String msg1, String amount) {
/* 46 */     if (BuildConfig.DEBUG) Log.d("BCW9", String.format("updateText Pin [%d], msg [%s], [%s]", new Object[] { Integer.valueOf(numDigits), (msg1 == null) ? "NULL" : msg1, (amount == null) ? "NULL" : amount }));
/*    */     
/* 48 */     String shownMessage = formatMessage(numDigits, amount);
/*    */     
/* 50 */     this.editText.setText(shownMessage);
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   private String formatMessage(int numDigits, String amount) {
/* 55 */     String shownMessage = "";
/* 56 */     NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
/* 57 */     String messageAmount = "";
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     if (numDigits > 0) {
/* 63 */       String messagePin = "SENHA: " + String.format(Locale.US, "%" + numDigits + "s", new Object[] { "" }).replace(' ', '*');
/* 64 */       shownMessage = String.format(Locale.US, "%s\n%s", new Object[] { messageAmount, messagePin });
/*    */     
/*    */     }
/* 67 */     else if (this.mensagem != null) {
/*    */ 
/*    */ 
/*    */       
/* 71 */       shownMessage = String.format(Locale.US, "%s\n%s", new Object[] { messageAmount, this.mensagem });
/*    */     } 
/*    */ 
/*    */     
/* 75 */     return shownMessage;
/*    */   }
/*    */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\Display.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */