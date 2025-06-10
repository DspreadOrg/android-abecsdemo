package com.dspread.ppcomlibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import br.com.setis.bibliotecapinpad.InterfaceUsuarioPinpad;
import br.com.setis.bibliotecapinpad.definicoes.LedsContactless;
import br.com.setis.bibliotecapinpad.definicoes.Menu;
import br.com.setis.bibliotecapinpad.definicoes.NotificacaoCapturaPin;
import br.com.setis.bibliotecapinpad.definicoes.TipoNotificacao;


class InterfaceUsuario implements InterfaceUsuarioPinpad {

    private Context context;
    private TextView mTextView;

    public InterfaceUsuario(Context context, TextView textView) {
        this.context = context;
        mTextView = textView;
    }

    @Override
    public void mensagemNotificacao(String s, TipoNotificacao tipoNotificacao) {
        String displayMsg;
        if(tipoNotificacao == TipoNotificacao.DSP_2X16){
            displayMsg = s.substring(0,16)+"\n"+s.substring(16,s.length());
        }
        else{
            displayMsg = s;
        }
        showMessage(displayMsg);

    }

    @Override
    public void notificacaoCapturaPin(NotificacaoCapturaPin notificacaoCapturaPin) {
        String displayMsg = notificacaoCapturaPin.obtemMensagemCapturaPin();
        if (!displayMsg.contains("*")) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < notificacaoCapturaPin.obtemQuantidadeDigitosPin(); i++) {
                sb.append("*");
            }
            displayMsg += sb.toString();
        }
        showMessage(displayMsg);
    }

    private void showMessage(final String message) {
        Log.e("showMessage:", message);
        Activity activity = (Activity) context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(message);
            }
        });
    }

    @Override
    public void menu(final Menu menu) {
        final String[] items = new String[menu.obtemOpcoesMenu().size()];

        for (int x = 0; x < menu.obtemOpcoesMenu().size(); x++) {
            items[x] = menu.obtemOpcoesMenu().get(x);
        }

        Activity activity = (Activity) context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*
                String options[] = new String[items.length];
                int i = 0;
                try {
                    for (String str : items) {
                        options[i++] = new String(str.getBytes("ISO-8859-1"), "UTF-8");
                    }
                } catch (UnsupportedEncodingException e) {
                }
                */

                final AlertDialog dialog;
                View convertView = new ListView(context);


                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
                builder.setView(convertView);
                builder.setTitle("SELECIONE");
                dialog = builder.create();

                ListView lv = (ListView) convertView;

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        context,
                        android.R.layout.simple_list_item_1, items);

                lv.setAdapter(adapter);
                lv.setClickable(true);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        Log.d("Menu", pos + " : " + id);
                        menu.obtemMenuCallback().informaOpcaoSelecionada(pos + 1);
                        dialog.dismiss();
                    }
                });


                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION);
                //dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        menu.obtemMenuCallback().informaOpcaoSelecionada(0);
                    }
                });
                dialog.show();
                //menu.obtemMenuCallback().informaOpcaoSelecionada(1);

            }
        });
    }

    @Override
    public void ledsProcessamentoContactless(LedsContactless ledsContactless) {

    }


}
