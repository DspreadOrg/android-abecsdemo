package com.dspread.ppcomlibrary;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.dspread.dsplibrary.interfaces.CustomPinEnterListener;
import com.dspread.dsplibrary.DeviceAbecsConstant;
import com.dspread.ppcomlibrary.keyboard.MyKeyBoardNumInterface;
import com.dspread.ppcomlibrary.keyboard.MyKeyboardUtil;
import com.dspread.ppcomlibrary.keyboard.MyKeyboardView;
import com.dspread.ppcomlibrary.utils.POSUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbecsPinEnterListener implements CustomPinEnterListener{
    private final String TRACE_TAG = "AbecsPinEnterListener";
    private MyKeyboardUtil keyboard;
    private boolean keyboardUIDrawFinished = false;
    Context mContext;
    byte[] keyboardMap;
    String pin = "";
    public AbecsPinEnterListener(Context context) {
        mContext = context;
    }

    private void closeKeyboard(){
        new Handler(Looper.getMainLooper()).post(() -> {
            keyboard.hide();
        });
    }
    @Override
    public void onKeyDown(int len, int keyValue) {
        Log.d(TRACE_TAG, "PP_GetPin onKeyDown:"+keyValue+","+len);
        pin = "";
        for(int i =0;i<len;i++){
            pin+="*";
        }
        new Handler(Looper.getMainLooper()).post(() -> {
            keyboard.pinpadShowPin.setText(pin);
        });

    }

    @Override
    public void onSuccess(byte[] bytes, byte[] bytes1) {
        closeKeyboard();
    }

    @Override
    public void onError(int i, String s) {
        closeKeyboard();
    }

    @Override
    public byte[] onInitKeyboardLayout(Bundle bundle) {
        keyboardUIDrawFinished = false;
        new Handler(Looper.getMainLooper()).post(() -> {
            List<String> keyList = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"));
            MyKeyboardView.setKeyBoardListener(new MyKeyBoardNumInterface() {
                @Override
                public void getKeyBoardMap(String value) {
                    Log.i(TRACE_TAG, "keyboard map: " + value);
                    keyboardMap = POSUtil.hexStringToBytes(value);
                }
            });

            View inputpin_View =  LayoutInflater.from(mContext).inflate(R.layout.my_layout_input_pin, null);
            EditText inputpin_EditText = inputpin_View.findViewById(R.id.inputpin_EditText);
            keyboard = new MyKeyboardUtil((Activity)mContext, inputpin_EditText, keyList);
            keyboard.initKeyboard(MyKeyboardView.KEYBOARDTYPE_Only_Num_Pwd, inputpin_EditText);
            String  mTitle;
            int enterPinType = bundle.getInt("enterPinType", DeviceAbecsConstant.EnterPinType.Default);
            switch (enterPinType) {
                case DeviceAbecsConstant.EnterPinType.ONLINE:
                    mTitle = "Please Enter Online PIN";
                    break;
                case DeviceAbecsConstant.EnterPinType.OFFLINE:
                    mTitle = "Please Enter Offline PIN";
                    String mMessage = "Remaining times("+bundle.getInt("remainOfflinePinTimes", 0)+")";
                    keyboard.pinpadMessage.setText(mMessage);
                    break;
                case DeviceAbecsConstant.EnterPinType.LAST_OFFLINE:
                    mTitle = "Please Enter Last Offline PIN";
                    break;
                default:
                    mTitle = "Please Input Pin";
            }
            keyboard.pinpadTitle.setText(mTitle);
            keyboardUIDrawFinished = true;;

        });

        while(!keyboardUIDrawFinished){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TRACE_TAG, "draw keyboard ui finished when enter pin");

        return keyboardMap;
    }

    @Override
    public void onUpdateKeyButton(byte[] bytes) {

    }

    @Override
    public void onUpdateMessage(String s, String s1, String s2) {

    }

    @Override
    public void onCancel() {
        closeKeyboard();
    }

    @Override
    public void onTimeout() {
        closeKeyboard();
    }
}
