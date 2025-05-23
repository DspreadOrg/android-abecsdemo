package com.dspread.ppcomlibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dspread.dsplibrary.interfaces.CustomEMVEventListener;
import com.dspread.dsplibrary.DeviceAbecsConstant;
import com.dspread.dsplibrary.posUtil.TonePlayer;
import java.util.ArrayList;

public class AbecsEventListener implements CustomEMVEventListener {

    private Context mContext;
    private TextView displayTextView;
    private String show_str = "";

    public AbecsEventListener(Context context, TextView textView) {

        this.mContext = context;
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.activity_welcome, null);
        //displayTextView = rootView.findViewById(R.id.display_textview2);
        displayTextView = textView;
    }
    @Override
    public void onRequestShowMessage(DeviceAbecsConstant.EmvMessageId msgId) {

        Log.d("onRequestShowMessage", "msgId msgId msgId " + msgId);
        switch (msgId) {
            case MPEMV_MSG_INSERT_CARD:
            case MPEMV_MSG_TAP_CARD:
            case MPEMV_MSG_INSERT_TAP_CARD:
            case MPEMV_MSG_SWIPE_CARD:
            case MPEMV_MSG_TAP_SWIPE_CARD:
            case MPEMV_MSG_INSERT_SWIPE_CARD:
            case MPEMV_MSG_INSERT_TAP_SWIPE_CARD:
                TonePlayer.playTone(1, 300, 120);
                break;
        }
        switch (msgId) {
            case MPEMV_MSG_NONE:
                break;
            case MPEMV_MSG_REMOVE_CARD:
                show_str = "RETIRE O CARTÃO";
                break;
            case MPEMV_MSG_INSERT_TAP_SWIPE_CARD:
                show_str = "INSIRA OU \nPASSE O CARTÃO\nVALOR:1,00";
                break;
            case MPEMV_MSG_PROCESSING:
                show_str = "Processing";
                break;
            default:
                break;
        }
        new Handler(Looper.getMainLooper()).post(() -> {
            displayTextView.setVisibility(View.VISIBLE);
            displayTextView.setText(show_str);
        });

    }

    @Override
    public void onRequestSelectApp(ArrayList<String> appInfo) {

    }
}
