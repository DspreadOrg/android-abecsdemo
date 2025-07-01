package br.com.setis.bcw9;

import android.util.Log;
import br.com.setis.bcw9.tasks.TaskDirectCommand;
import br.com.setis.bcw9.tasks.TaskHandleNextCommand;
import br.com.setis.bcw9.util.Util;
import br.com.setis.bibliotecapinpad.AcessoDiretoPinpad;
import br.com.setis.bibliotecapinpad.InterfaceUsuarioPinpad;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DeviceSerial
        extends AcessoDiretoPinpad
        implements TaskDirectCommand.DirectCommandCallback
        , TaskHandleNextCommand.DirectMulCommandCallback
{
    private static final String TAG = "DeviceSerial";
    private static final int ACK = 6;
    private static final int SYN = 22;
    private static final int ETB = 23;
    private static final int NAK = 21;
    private static final int CAN = 24;
    private static final int EOT = 4;
    private boolean pendingACK = false;
    private boolean pendingNAK = false;
    private boolean commandAborted = false;
    private boolean commandInProgress = false;
    private boolean isResponseReady = false;
    private boolean sendError = false;
    private Stack<byte[]> pendingCmd;
    private RetornoDeviceSerial retornoDeviceSerial;

    public class RetornoDeviceSerial
    {
        private int returnCode;
        private byte[] answer;
        private Boolean waitingAnswer;

        public void RetornoDeviceSerial() {
            if (BuildConfig.DEBUG) Log.d("DeviceSerial", "RetornoDeviceSerial - construtor - waitingAnswer = true!!");
            this.waitingAnswer = Boolean.valueOf(true);
        }

        public void SetAnswer(byte[] answer, int len, int returnCode) {
            if (returnCode < 0) {
                if (BuildConfig.DEBUG) Log.d("DeviceSerial", "SetAnswer - returnCode < 0");
                this.returnCode = returnCode;
                this.answer = null;
                this.waitingAnswer = Boolean.valueOf(false);
                return;
            }
            if (BuildConfig.DEBUG) Log.d("DeviceSerial", "SetAnswer - returnCode >= 0");
            this.returnCode = 0;
            this.answer = new byte[len];
            System.arraycopy(answer, 0, this.answer, 0, len);
            this.waitingAnswer = Boolean.valueOf(false);
        }

        int GetReturnCode() {
            return this.returnCode;
        }

        byte[] GetAnswer() {
            return this.answer;
        }

        Boolean WaitingAnwer() {
            return this.waitingAnswer;
        }
    }

    private Boolean receivedCAN = Boolean.valueOf(false);

    public DeviceSerial(InterfaceUsuarioPinpad interfaceUsuarioPinpad) {
        super(interfaceUsuarioPinpad);
        PPCompAndroidEvents.getInstance().setInterfacePinpad(interfaceUsuarioPinpad);
        this.pendingCmd = (Stack)new Stack<>();
    }

    public synchronized void setResponseReady(boolean bool) {
        if (BuildConfig.DEBUG) Log.d("DeviceSerial", "setResponseReady - bool=[" + bool + "]");
        this.isResponseReady = bool;
    }

    public synchronized boolean isResponseReady() {
        return this.isResponseReady;
    }

    public int singleEnViaComando(byte[] bytes, int i){

        Log.i(TAG,"execute singleEnViaComando");
        if (BuildConfig.DEBUG) Log.d("DeviceSerial", "singleEnViaComando....");

        if (i < 0) {
            throw new IllegalArgumentException("Buffer de tamanho negativo!");
        }

        if (bytes[0] == 21) {
            if (BuildConfig.DEBUG) Log.d("DeviceSerial", "SPE envia um NAK!!");
            setResponseReady(true);
            return 0;
        }

        this.sendError = false;
        this.receivedCAN = Boolean.valueOf(this.pendingACK = this.pendingNAK = false);
        this.retornoDeviceSerial = new RetornoDeviceSerial();

        Log.d(TAG, "start execute PP_CheckSerialization: cmd is "+Util.bytesToHex(bytes));
        int st = PPCompAndroid.getInstance().PP_CheckSerialization(bytes);

        switch (st) {
            case 21:
                if (BuildConfig.DEBUG) Log.d("DeviceSerial", "PP_CheckSerialization - NAK!!");
                this.pendingNAK = true;
                this.commandAborted = false;
                setResponseReady(true);
                if(!this.pendingCmd.isEmpty())
                    (new TaskHandleNextCommand(this)).execute(new Void[0]);
                return 0;
            case 4:
                Log.d(TAG, "PP_CheckSerialization - EOT!!");
                PPCompAndroid.getInstance().PP_AbortSerializedCmd();
                this.commandAborted = (this.receivedCAN = Boolean.valueOf(true)).booleanValue();
                setResponseReady(true);
                if(!this.pendingCmd.isEmpty())
                    (new TaskHandleNextCommand(this)).execute(new Void[0]);
                return 0;
        }
        if(st < 0){
            this.sendError = true;
            setResponseReady(true);
            Log.i(TAG,"an error happen when send data via serial !!!!");
            return 0;
        }

        if (BuildConfig.DEBUG) Log.d("DeviceSerial", "PP_CheckSerialization = " + st);

        if (!this.commandInProgress) {
            this.commandAborted = false;
            this.commandInProgress = true;
            this.pendingACK = true;
            setResponseReady(true);
            (new TaskDirectCommand(this)).execute(new Void[0]);
        }

        return 0;
    }

    public int enviaComando(byte[] bytes, int i) {
        Log.i(TAG,"execute enviaComando");
        if (BuildConfig.DEBUG) Log.d("DeviceSerial", "enviaComando....");

        if (i < 0) {
            throw new IllegalArgumentException("Buffer de tamanho negativo!");
        }


        if (bytes.length != 1 && bytes.length < 7) {
            Log.d(TAG, "data length is too small");
            this.sendError = true;
            setResponseReady(true);
            return 0;
        }

        if (bytes.length != 1 && bytes[bytes.length - 3] != ETB) {
            Log.d(TAG, "data format is error");
            this.sendError = true;
            setResponseReady(true);
            return 0;
        }

        int j = 0, synIndex = 0;
        boolean isFindSYN = false;
        List<byte[]> cmdList = new ArrayList<>();

        for (j = 0; j < bytes.length; j++) {
            if (bytes[j] == CAN) {
                cmdList.add(Arrays.copyOfRange(bytes, j, j + 1));
            } else if (bytes[j] == SYN && !isFindSYN) {
                isFindSYN = true;
                synIndex = j;
            } else if (bytes[j] == ETB) {
                isFindSYN = false;
                cmdList.add(Arrays.copyOfRange(bytes, synIndex, j + 3));
                j += 2;
            }
        }

        i = 0;
        for (byte[] cmd : cmdList) {
            Log.d(TAG, "cmd to abecs:[" + (i++) + "]," + Util.bytesToHex(cmd));
        }

        byte[] currentCmdBytes = cmdList.get(0);
        cmdList.remove(0);

        if (!cmdList.isEmpty()) {
            for (i = cmdList.size() - 1; i >= 0; i--) {
                this.pendingCmd.push(cmdList.get(i));
            }
            cmdList.clear();
        }

        return singleEnViaComando(currentCmdBytes, currentCmdBytes.length);
    }

    public int recebeResposta(byte[] bytes, long l) {
        long startTime = System.currentTimeMillis();
        while (!isResponseReady()) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException interruptedException) {}

            if (System.currentTimeMillis() - startTime > l) {
                return 0;
            }
        }

        if(this.sendError){
            Log.i("bcpp api","an error happen when receive via serial !!!!!!!!!!!!!!!");
            return -1;
        }

        if (this.receivedCAN.booleanValue()) {
            bytes[0] = 4;
            if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - EOT!!");

            this.receivedCAN = Boolean.valueOf(false);
            setResponseReady(false);
            return 1;
        }

        if (this.pendingACK) {
            bytes[0] = 6;
            if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - ACK!!");

            this.pendingACK = false;
            setResponseReady(false);
            return 1;
        }

        if (this.pendingNAK) {
            bytes[0] = 21;
            if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - NAK!!");

            this.pendingNAK = false;
            setResponseReady(false);
            return 1;
        }

        if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - RESPOSTA CHEGOU");

        if (this.retornoDeviceSerial.GetReturnCode() < 0) {
            int st = this.retornoDeviceSerial.GetReturnCode();

            if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - ERRO =[" + st + "]!!");

            setResponseReady(false);
            return st;
        }

        byte[] buff = this.retornoDeviceSerial.GetAnswer();

        if (buff != null && buff.length > 0) {
            if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta- tamanho=" + buff.length);

            System.arraycopy(buff, 0, bytes, 0, buff.length);

            setResponseReady(false);

            if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - FIM tamanho=" + buff.length);
            return buff.length;
        }

        if (BuildConfig.DEBUG) Log.d("DeviceSerial", "recebeResposta - Pedido de resposta pela aplicacao ?????");
        return -1;
    }

    public void ComandoDiretoEncerrado(byte[] out, int len, int cmdResult) {
        if(len<0)
            len=0;
        if (BuildConfig.DEBUG) Log.d("DeviceSerial", String.format("ComandoDiretoEncerrado (cmdResult = %d, len = %d, resp = [%s], aborted = [%b]", new Object[] {
                Integer.valueOf(cmdResult), Integer.valueOf(len), Util.byte2HexStr(out).substring(0, len * 2), Boolean.valueOf(this.commandAborted) }));
        this.commandInProgress = false;

        if (!this.commandAborted && out != null) {
            this.retornoDeviceSerial.SetAnswer(out, len, cmdResult);
            setResponseReady(true);
        }

        if (this.pendingCmd.size() > 0) {
            byte[] cmd = this.pendingCmd.pop();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            singleEnViaComando(cmd, cmd.length);
        }

        if (BuildConfig.DEBUG) Log.d("DeviceSerial", "ComandoDiretoEncerrado - FIM");
    }
}