/*     */
package br.com.setis.bcw9;
/*     */
/*     */

import android.content.Intent;
/*     */ import android.media.SoundPool;
/*     */ import android.util.Log;
/*     */ //import com.dspread.bcppdemo.R;

import androidx.annotation.NonNull;
import br.com.setis.bibliotecapinpad.InterfaceUsuarioPinpad;
/*     */ import br.com.setis.bibliotecapinpad.definicoes.Menu;
/*     */ import br.com.setis.bibliotecapinpad.definicoes.NotificacaoCapturaPin;
/*     */ import br.com.setis.bibliotecapinpad.definicoes.TipoNotificacao;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;

/*     */
/*     */  class PPCompAndroidEvents
        /*     */ implements Menu.MenuCallback
        /*     */ {
    /*     */   private static String mensagem;
    /*     */   private InterfaceUsuarioPinpad interfacePinpad;
    /*     */   private int menuSelected;
    /*     */   private Boolean receveidData;

    /*     */
    /*     */   static class MyRunnable
            /*     */ implements Runnable
            /*     */ {
        /*     */     private int pin;
        /*     */     private String msg1;
        /*     */     private String msg2;

        /*     */
        /*     */     MyRunnable(int pin, String msg1, String msg2) {
            /*  35 */
            if (BuildConfig.DEBUG)
                Log.d("BCW9", String.format("MyRunnable constructor in %d, msg [%s], [%s]", new Object[]{Integer.valueOf(pin), (msg1 == null) ? "NULL" : msg1, (msg2 == null) ? "NULL" : msg2}));
            /*  36 */
            this.pin = pin;
            /*  37 */
            this.msg1 = msg1;
            /*  38 */
            this.msg2 = msg2;
            /*     */
        }

        /*     */
        /*     */
        /*     */
        public void run() {
            /*  43 */
            if (BuildConfig.DEBUG)
                Log.d("BCW9", String.format("MyRunnable Pin %d, msg [%s], [%s]", new Object[]{Integer.valueOf(this.pin), (this.msg1 == null) ? "NULL" : this.msg1, (this.msg2 == null) ? "NULL" : this.msg2}));
            /*     */
            /*  45 */
            Display.getInstance().updateText(this.pin, this.msg1, this.msg2);
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*  52 */   private static PPCompAndroidEvents instance = null;
    /*     */
    /*     */   private static SoundPool soundPool;
    /*     */   private static boolean isLoaded = false;
    /*     */   private static int soundId;
    /*  57 */   private static int count = 0;

    /*     */
    /*     */
    private PPCompAndroidEvents() {
        /*  60 */
        soundPool = new SoundPool(10, 3, 0);
        /*  61 */
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
                /*     */ {
            /*     */
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                /*  64 */
                PPCompAndroidEvents.isLoaded = true;
                /*     */
            }
            /*     */
        });
        /*  67 */
        soundId = soundPool.load(Content.getStoredContext(), R.raw.pinpad_sound, 1);
        /*     */
    }

    /*     */
    /*     */
    public void setInterfacePinpad(InterfaceUsuarioPinpad interfacePinpad) {
        /*  71 */
        this.interfacePinpad = interfacePinpad;
        /*     */
    }

    /*     */
    /*     */
    public static PPCompAndroidEvents getInstance() {
        /*  75 */
        if (instance == null)
            /*  76 */ instance = new PPCompAndroidEvents();
        /*  77 */
        return instance;
        /*     */
    }

    /*     */
    /*     */
    public static int menu(String title, String[] menuOptions, int timeout) {
        /*  81 */
        Menu menu = new Menu();
        /*     */
        /*  83 */
        List<String> itensMenu = new ArrayList<>();
        /*  84 */
        for (String s : menuOptions) {
            /*  85 */
            itensMenu.add(s);
            /*     */
        }
        /*  87 */
        menu.informaOpcoesMenu(itensMenu);
        /*  88 */
        menu.informaTituloMenu(title);
        /*  89 */
        menu.informaTimeout(timeout);
        /*  90 */
        menu.informaMenuCallback(getInstance());
        /*  91 */
        if ((getInstance()).interfacePinpad == null)
            /*  92 */ return -1;
        /*  93 */
        (getInstance()).receveidData = Boolean.valueOf(false);
        /*  94 */
        (getInstance()).interfacePinpad.menu(menu);
        /*  95 */
        while (!(getInstance()).receveidData.booleanValue()) ;

        Log.d("BCW9", String.format("Menu retornou %d", new Object[]{Integer.valueOf((getInstance()).menuSelected)}));
        /*  96 */
        return (getInstance()).menuSelected - 1;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public void informaOpcaoSelecionada(int i) {
        /* 101 */
        this.menuSelected = i;
        /* 102 */
        this.receveidData = Boolean.valueOf(true);
        /*     */
    }

    /*     */
    /*     */
    public static int pindata(byte[] message, int digits) {
        /* 106 */
        if (BuildConfig.DEBUG)
            Log.d("BCW9", String.format("Evento de pindata %d", new Object[]{Integer.valueOf(digits)}));
        /* 107 */
        if (digits > 0) {
            /* 108 */
            playSound();
            /*     */
        }
        /*     */
        /* 111 */
        NotificacaoCapturaPin notificacaoCapturaPin = new NotificacaoCapturaPin();
        /* 112 */
        notificacaoCapturaPin.informaMensagemCapturaPin((new String(message, StandardCharsets.ISO_8859_1)).replace('\r', '\n'));
        /* 113 */
        notificacaoCapturaPin.informaQuantidadeDigitosPin(digits);
        /*     */
        /* 115 */
        (getInstance()).interfacePinpad.notificacaoCapturaPin(notificacaoCapturaPin);
        /* 116 */
        return 0;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static int message(byte[] message, int type) {
        /* 121 */
        TipoNotificacao t = TipoNotificacao.values()[type];
        /* 122 */
        if (BuildConfig.DEBUG)
            Log.d("BCW9", String.format("Tipo Notificacao: %s, Mensagem: %s", new Object[]{t.name(), new String(message, StandardCharsets.ISO_8859_1)}));
        /*     */
        /*     */
        /* 125 */
        if ((getInstance()).interfacePinpad == null)
            /* 126 */ return -1;
        /* 127 */
        (getInstance()).interfacePinpad.mensagemNotificacao(new String(message, StandardCharsets.ISO_8859_1), t);
        /*     */
        /* 129 */
        return 0;
        /*     */
    }

    /*     */
    /*     */
    public static int display(int i, String message, String tags) {
        /* 133 */
        if (BuildConfig.DEBUG)
            Log.d("BCW9", String.format("Evento de display %d", new Object[]{Integer.valueOf(i)}));
        /*     */
        /* 135 */
        if (i == 0) {
            /*     */
            /*     */
            /* 138 */
            mensagem = message;
            /* 139 */
            openKeyboard(i, message, tags, false);
            /* 140 */
            return 0;
            /*     */
        }
        /* 142 */
        if (i > 0 && Display.getInstance() != null) {
            /* 143 */
            if (BuildConfig.DEBUG)
                Log.d("BCW9", String.format("Create MyRunnable Pin %d - 1, msg [%s], [%s]", new Object[]{Integer.valueOf(i), (message == null) ? "NULL" : message, (tags == null) ? "NULL" : tags}));
            /* 144 */
            Display.getInstance().runOnUiThread(new MyRunnable(i - 1, message, tags));
            /* 145 */
            return 0;
            /*     */
        }
        /* 147 */
        if (i < 0) {
            /* 148 */
            Display.getInstance().finish();
            /* 149 */
            return 0;
            /*     */
        }
        /*     */
        /* 152 */
        return -1;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    private static void openKeyboard(int numDigits, String message, String msg2, boolean isGoc) {
        /* 157 */
        Intent in = new Intent(Content.getStoredContext(), Display.class);
        /* 158 */
        in.putExtra("is_goc", isGoc);
        /* 159 */
        in.putExtra("input", formatMessage(numDigits, msg2));
        /*     */
        /* 161 */
        in.addFlags(268435456);
        /* 162 */
        Content.getStoredContext().startActivity(in);
        /*     */
    }

    /*     */
    /*     */
    @NonNull
    /*     */ private static String formatMessage(int numDigits, String amount) {
        /* 167 */
        String shownMessage = "";
        /*     */
        /* 169 */
        NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        /* 170 */
        String messageAmount = "";
        /*     */
        /*     */
        /*     */
        /*     */
        /* 175 */
        if (numDigits > 0) {
            /* 176 */
            String messagePin = "SENHA: " + String.format(Locale.US, "%" + numDigits + "s", new Object[]{""}).replace(' ', '*');
            /* 177 */
            shownMessage = String.format(Locale.US, "%s\n%s", new Object[]{messageAmount, messagePin});
            /*     */
            /*     */
        }
        /* 180 */
        else if (mensagem != null) {
            /*     */
            /*     */
            /*     */
            /* 184 */
            shownMessage = String.format(Locale.US, "%s\n%s", new Object[]{messageAmount, mensagem});
            /*     */
        }
        /*     */
        /*     */
        /* 188 */
        return shownMessage;
        /*     */
    }

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
    private static void playSound() {
        /* 200 */
        if (isLoaded)
            /*     */ {
            /* 202 */
            soundPool.play(soundId, 0.5F, 0.5F, 1, 0, 1.0F);
            /*     */
        }
        /*     */
    }
    /*     */
}


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\PPCompAndroidEvents.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */