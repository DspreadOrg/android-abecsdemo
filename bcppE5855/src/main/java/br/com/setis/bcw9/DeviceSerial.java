package br.com.setis.bcw9;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import br.com.setis.bcw9.util.Util;
import br.com.setis.bibliotecapinpad.AcessoDiretoPinpad;
import br.com.setis.bibliotecapinpad.InterfaceUsuarioPinpad;
import br.com.setis.bcw9.tasks.TaskDirectCommand;

public class DeviceSerial extends AcessoDiretoPinpad implements TaskDirectCommand.DirectCommandCallback {

    private static final String TAG = "DeviceSerial";

    private static final int ACK = 0x06;
    private static final int SYN = 0x16;
    private static final int ETB = 0x17;
    private static final int NAK = 0x15;
    private static final int CAN = 0x18;
    private static final int EOT = 0x04;

    private boolean pendingACK = false;
    private boolean pendingNAK = false;
    private boolean commandAborted = false;
    private boolean commandInProgress = false;
    private boolean isResponseReady = false;

    private Stack<byte[]> pendingCmd;

    public class RetornoDeviceSerial {
        private int returnCode;
        private byte[] answer;
        private Boolean waitingAnswer;

        public void RetornoDeviceSerial() {
            if (BuildConfig.DEBUG) Log.d(TAG, "RetornoDeviceSerial - construtor - waitingAnswer = true!!");
            waitingAnswer = true;
        }

        public void SetAnswer(byte[] answer, int len, int returnCode) {

            if (returnCode < 0) {
                if (BuildConfig.DEBUG) Log.d(TAG, "SetAnswer - returnCode < 0");
                this.returnCode = returnCode;
                this.answer = null;
                waitingAnswer = false;
                return;
            }
            if (BuildConfig.DEBUG) Log.d(TAG, "SetAnswer - returnCode >= 0");
            this.returnCode = 0;
            this.answer = new byte[len];
            System.arraycopy(answer, 0, this.answer, 0, len);
            waitingAnswer = false;
        }

        int GetReturnCode() {
            return returnCode;
        }

        byte[] GetAnswer() {
            return answer;
        }

        Boolean WaitingAnwer() {
            return waitingAnswer;
        }
    }

    private RetornoDeviceSerial retornoDeviceSerial;
    private Boolean receivedCAN = false;

    public DeviceSerial(InterfaceUsuarioPinpad interfaceUsuarioPinpad) {
        super(interfaceUsuarioPinpad);
        PPCompAndroidEvents.getInstance().setInterfacePinpad(interfaceUsuarioPinpad);
        pendingCmd = new Stack<>();
    }

    public synchronized void setResponseReady(boolean bool) {
        if (BuildConfig.DEBUG) Log.d(TAG, "setResponseReady - bool=[" + bool + "]");
        this.isResponseReady = bool;
    }

    public synchronized boolean isResponseReady() {
        return this.isResponseReady;
    }


    private byte[] splitting_data(byte[] bytes, int i) {
        int j = 0, synIndex = 0;
        boolean isFindSYN = false;
        List<byte[]> cmdList = new ArrayList<>();

        for (j = 0; j < bytes.length; j++) {
            if (bytes[j] == CAN && !isFindSYN) {
                cmdList.add(Arrays.copyOfRange(bytes, j, j + 1));
            } else if (bytes[j] == SYN && !isFindSYN) {
                isFindSYN = true;
                synIndex = j;
            } else if (bytes[j] == ETB) {
                isFindSYN = false;
                if(j+3 <= bytes.length)
                {
                    cmdList.add(Arrays.copyOfRange(bytes, synIndex, j + 3));
                    j += 2;
                }
                else{
                    cmdList.add(Arrays.copyOfRange(bytes, synIndex, bytes.length));
                    break;
                }

            }
        }

        i = 0;
        if(BuildConfig.DEBUG){

            for (byte[] cmd : cmdList) {
                Log.d(TAG, "cmd to abecs:[" + (i++) + "]," + Util.bytesToHex(cmd));
            }
        }

        byte[] currentCmdBytes = cmdList.get(0);
        cmdList.remove(0);

        if (!cmdList.isEmpty()) {
            for (i = cmdList.size() - 1; i >= 0; i--) {
                this.pendingCmd.push(cmdList.get(i));
            }
            cmdList.clear();
        }

        Log.e(TAG, "pendingCmd.size is " + pendingCmd.size());

        return currentCmdBytes;
    }
    public void continue_send_abecs_data()
    {
        Log.e(TAG, "===continue_send_abecs_data===");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "continue send abecs data");
                byte[] cmd = pendingCmd.pop();
                single_enviaComando(cmd, cmd.length);
            }
        }).start();
    }
    public int single_enviaComando(byte[] bytes, int i) {
        String input;
        if (BuildConfig.DEBUG) Log.d(TAG, "enviaComando 2....");

        if (i < 0)
            throw new IllegalArgumentException("Buffer de tamanho negativo!");

        //Se o SPE enviou um NAK, retransmite a última resposta
        if (bytes[0] == NAK) {
            if (BuildConfig.DEBUG) Log.d(TAG, "SPE envia um NAK!!");
            setResponseReady(true);
            return 0;
        }


        receivedCAN = pendingACK = pendingNAK = false;
        retornoDeviceSerial = new RetornoDeviceSerial();
        input = Util.byte2HexStr(bytes);

        if (BuildConfig.DEBUG) Log.d(TAG, "enviaComando (tamanho=" + bytes.length + ") = [" + input + "]");

        int st = PPCompAndroid.getInstance().PP_CheckSerialization(bytes);

        switch (st) {
            case NAK:
                if (BuildConfig.DEBUG) Log.d(TAG, "PP_CheckSerialization - NAK!!");
                pendingNAK = true;
                commandAborted = false;
                setResponseReady(true);
                return 0;
            case EOT:
                if (BuildConfig.DEBUG) Log.d(TAG, "PP_CheckSerialization - EOT!!");
                PPCompAndroid.getInstance().PP_AbortSerializedCmd();
                commandAborted = receivedCAN = true;
                setResponseReady(true);
                if (pendingCmd.size() > 0) {
                    Log.e(TAG, "continue process abecs data after can");
                    continue_send_abecs_data();
                }
                return 0;
            case ACK:
            default:
                if (BuildConfig.DEBUG) Log.d(TAG, "PP_CheckSerialization = " + st);
                break;
        }

//        pendingACK = true;
//        setResponseReady(true);

        //Se há comando em curso, armazena para envio posterior
        if (commandInProgress) {
            Log.e(TAG, "continue send abecs data");
            pendingCmd.push(bytes);
        } else {
            commandAborted = false;
            commandInProgress = true;
            pendingACK = true;
            setResponseReady(true);
            new TaskDirectCommand(this).execute();
        }

        return 0;
    }

    @Override
    public int enviaComando(byte[] bytes, int i) {
        String input;
        if (BuildConfig.DEBUG) Log.d(TAG, "enviaComando 1....");

        if (i < 0)
            throw new IllegalArgumentException("Buffer de tamanho negativo!");

        //Se o SPE enviou um NAK, retransmite a última resposta
        if (bytes[0] == NAK) {
            if (BuildConfig.DEBUG) Log.d(TAG, "SPE envia um NAK!!");
            setResponseReady(true);
            return 0;
        }

        byte[] bytes_first = splitting_data(bytes, i);

        Log.e(TAG, "enviaComando first (tamanho=" + bytes.length + ") = [" + Util.bytesToHex(bytes) + "]");

        return single_enviaComando(bytes_first, bytes_first.length);
    }


    @Override
    public int recebeResposta(byte[] bytes, long l) {

        long startTime = System.currentTimeMillis();
        while (!isResponseReady()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }

            if ((System.currentTimeMillis() - startTime) > l) {
                return 0;
            }
        }

        if (receivedCAN) {
            bytes[0] = (byte) EOT;
            if (BuildConfig.DEBUG) Log.d(TAG, "recebeResposta - EOT!!");

            receivedCAN = false;
            setResponseReady(false);
            return 1;
        }

        if (pendingACK) {
            bytes[0] = (byte) ACK;
            if (BuildConfig.DEBUG) Log.d(TAG, "recebeResposta - ACK!!");

            pendingACK = false;
            setResponseReady(false);
            return 1;
        }

        if (pendingNAK) {
            bytes[0] = (byte) NAK;
            if (BuildConfig.DEBUG) Log.d(TAG, "recebeResposta - NAK!!");

            pendingNAK = false;
            setResponseReady(false);
            return 1;
        }


        if (BuildConfig.DEBUG) Log.d(TAG, "recebeResposta - RESPOSTA CHEGOU");

        if (retornoDeviceSerial.GetReturnCode() < 0) {
            int st = retornoDeviceSerial.GetReturnCode();
            //retornoDeviceSerial = null;

            if (BuildConfig.DEBUG) Log.d(TAG, "recebeResposta - ERRO =[" + st + "]!!");

            setResponseReady(false);
            return st;
        }

        byte buff[] = retornoDeviceSerial.GetAnswer();

        if (buff != null && buff.length > 0) {
            if (BuildConfig.DEBUG) Log.d(TAG, "recebeResposta- tamanho=" + buff.length);

            System.arraycopy(buff, 0, bytes, 0, buff.length);

            setResponseReady(false);

            if (BuildConfig.DEBUG) Log.d(TAG, "recebeResposta - FIM tamanho=" + buff.length);
            return buff.length;
        }

        if (BuildConfig.DEBUG) Log.d(TAG, "recebeResposta - Pedido de resposta pela aplicacao ?????");
        return -1;
    }


    @Override
    public void ComandoDiretoEncerrado(byte[] out, int len, int cmdResult) {


        if (BuildConfig.DEBUG) Log.d(TAG, String.format("ComandoDiretoEncerrado (cmdResult = %d, len = %d, resp = [%s], aborted = [%b]",
                cmdResult, len, Util.byte2HexStr(out).substring(0, len * 2), commandAborted));
        commandInProgress = false;

        //Ignora resposta se comando abortado, pois isso implica em receber erro 013
        //da lib ABECS serial, o que não é desejado
        if (!commandAborted) {
            retornoDeviceSerial.SetAnswer(out, len, cmdResult);
            setResponseReady(true); }

        //Se tem comando pendente de execução, executa
        if (pendingCmd.size() > 0) {
            int waittimes = 0;
            while(true){
                if(isResponseReady() == false || waittimes++ > 200)
                    break;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
            }
            }
            Log.e(TAG, "continue process abecs data");
            byte[] cmd = pendingCmd.pop();
            single_enviaComando(cmd, cmd.length);
        }

        if (BuildConfig.DEBUG) Log.d(TAG, "ComandoDiretoEncerrado - FIM");
    }
}



