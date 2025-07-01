package com.dspread.ppcomlibrary.utils;
import android.util.Log;

import com.dspread.dsplibrary.posUtil.Tip;

import java.util.Arrays;

public class AbecsCommand {
    private static byte[] aseKey ;
    private static RSA testRSA;

    public static byte[] getAseKey() {
        return aseKey;
    }
    public static void setAseKey(byte[] aseKey) {
        AbecsCommand.aseKey = aseKey;
    }


    private static final int CRC_MASK = 0x1021; // 根据CCITT标准的CRC多项式

    protected static int ccitt_crc16(byte[] data, int length, int crcInit) {
        int dataWord, crc = crcInit;
        if (data == null)
            return crc;
        for (int i = 0; length > 0; length--, i++) {
            dataWord = (int) (data[i] << 8);
            for (int j = 0; j < 8; j++, dataWord <<= 1) {
                if (((crc ^ dataWord) & 0x8000) != 0x0000) // 使用符号位检测 (相当于检查最高位是否为1
                {
                    crc = (crc << 1) ^ CRC_MASK;
                } else
                    crc <<= 1;
            }
        }
        return crc & 0xFFFF; // 确保返回的CRC是16位
    }

    public static byte[] packSecOPNCommand() {

        //pack sec opn command data
        testRSA = new RSA();
        testRSA.generateRSAKeyPair("65537");
        byte[] sec_opn_bytes = new byte[1024];
        int sec_opn_len = 0;
        System.arraycopy("OPN".getBytes(), 0, sec_opn_bytes,  sec_opn_len, 3);
        sec_opn_len += 3;

        System.arraycopy("523".getBytes(), 0, sec_opn_bytes,  sec_opn_len, 3);
        sec_opn_len += 3;

        sec_opn_bytes[sec_opn_len++] = '0';

        System.arraycopy("256".getBytes(), 0, sec_opn_bytes,  sec_opn_len, 3);
        sec_opn_len += 3;
        System.arraycopy(testRSA.getPublicKeyModulus().getBytes(), 0, sec_opn_bytes,  sec_opn_len, testRSA.getPublicKeyModulus().length());
        sec_opn_len +=testRSA.getPublicKeyModulus().length();

        sec_opn_bytes[sec_opn_len++] = '3';

        sec_opn_bytes[sec_opn_len++] = '0';
        System.arraycopy(testRSA.getPublicKeyExponent().getBytes(), 0, sec_opn_bytes,  sec_opn_len, testRSA.getPublicKeyExponent().length());
        sec_opn_len +=testRSA.getPublicKeyExponent().length();
        sec_opn_bytes[sec_opn_len++] = 0x17;

        byte[] sec_opn_bytes2 = new byte[1024+128];
        int sec_opn_len2 = 0;
        sec_opn_bytes2[sec_opn_len2++] = 0x16;
        for(int var_i = 0; var_i<sec_opn_len-1; var_i++){
            if(sec_opn_bytes[var_i] == 0x17 || sec_opn_bytes[var_i] == 0x16 || sec_opn_bytes[var_i] == 0x13){
                sec_opn_bytes2[sec_opn_len2++] = 0x13;
                sec_opn_bytes2[sec_opn_len2++] = (byte)(sec_opn_bytes[var_i]+0x20);
            }
            else{
                sec_opn_bytes2[sec_opn_len2++] = sec_opn_bytes[var_i];
            }
        }
        int crc = ccitt_crc16(Arrays.copyOfRange(sec_opn_bytes, 0, sec_opn_len), sec_opn_len,0);
        sec_opn_bytes2[sec_opn_len2++] = 0x17;
        sec_opn_bytes2[sec_opn_len2++] =   (byte)((crc&0xFF00) >>8);
        sec_opn_bytes2[sec_opn_len2++] =   (byte)((crc&0x00FF));
        return Arrays.copyOfRange(sec_opn_bytes2, 0, sec_opn_len2);
    }

    public static int checkSecOPNCommand(byte[] data) {

        byte[] decryptedData = Arrays.copyOfRange(data, 13, 13 + 512);
//                        byte[] decryptedData2= POSUtil.asciiHexToBytes(decryptedData);
//
//                        Log.d("NewTestActivity", "need decryptedData: "+ POSUtil.byteArray2Hex(decryptedData2));
//                        Log.d("NewTestActivity", "need decryptedData:222 "+ decryptedData2[0]+","+ decryptedData2[decryptedData2.length-1]);
        byte[] randomKey = testRSA.decryptWithPrivateKey(POSUtil.asciiHexToBytes(decryptedData));
        AbecsCommand.setAseKey(randomKey);
        Log.d("AbecsCommand", "randomKey: " + POSUtil.byteArray2Hex(randomKey));

        if (new String(Arrays.copyOfRange(data, 1, 4)).equals("OPN")
                && new String(Arrays.copyOfRange(data, 4, 7)).equals("000") && randomKey != null) {
            return 0;
        }

        Log.d("AbecsCommand", "get randomKey fail ");


        return -1;
    }

    public static byte[] packommand(byte[] data) {

        if(data == null){
            return null;
        }

        byte[] packCmdBytes = new byte[1024];
        byte[] packCmdConvertBytes = new byte[1024];
        int packCmdLen = 0;
        int packCmdConvertLen = 0;
        packCmdBytes[packCmdLen++] = 0x16;
        System.arraycopy(data, 0, packCmdBytes, packCmdLen, data.length);
        packCmdLen += data.length;
        packCmdBytes[packCmdLen++] = 0x17;

        byte[] clcrc = Arrays.copyOfRange(packCmdBytes, 1, packCmdLen);
        Tip.d("AbecsCommand", "pack crc bytes: " + POSUtil.byteArray2Hex(clcrc));
        int crc = ccitt_crc16(clcrc, clcrc.length,0);

        packCmdConvertBytes[packCmdConvertLen++] = 0x16;
        for(int i = 1; i<packCmdLen-1; i++){
            if(packCmdBytes[i] == 0x17 || packCmdBytes[i] == 0x16 || packCmdBytes[i] == 0x13){
                packCmdConvertBytes[packCmdConvertLen++] = 0x13;
                packCmdConvertBytes[packCmdConvertLen++] = (byte)(packCmdBytes[i]+0x20);
            }
            else{
                packCmdConvertBytes[packCmdConvertLen++] = packCmdBytes[i];
            }
        }

        packCmdConvertBytes[packCmdConvertLen++] = 0x17;
        packCmdConvertBytes[packCmdConvertLen++] = (byte)((crc&0xFF00) >>8);
        packCmdConvertBytes[packCmdConvertLen++] = (byte)((crc&0x00FF));


        Log.d("AbecsCommand", "final command bytes: " + POSUtil.byteArray2Hex(Arrays.copyOfRange(packCmdConvertBytes, 0, packCmdConvertLen)));

        return Arrays.copyOfRange(packCmdConvertBytes, 0, packCmdConvertLen);
    }

    public static byte[] packSecCommand(byte[] data) {

        if(data == null){
            return null;
        }

        byte[] sec_cmd_bytes = new byte[1024];
        byte[] sec_clear_bytes = new byte[1024];
        int sec_cmd_len = 0;
        int sec_clearcmd_len = 0;

        int crc = ccitt_crc16(Arrays.copyOfRange(data, 0, data.length), data.length,0);
        sec_clear_bytes[sec_clearcmd_len++] = (byte)((data.length&0xFF00)>>8);
        sec_clear_bytes[sec_clearcmd_len++] = (byte)(data.length&0xFF);
        sec_clear_bytes[sec_clearcmd_len++] = (byte)((crc&0xFF00) >>8);
        sec_clear_bytes[sec_clearcmd_len++] = (byte)((crc&0x00FF));
        System.arraycopy(data, 0, sec_clear_bytes, sec_clearcmd_len, data.length);
        sec_clearcmd_len += data.length;
        sec_clearcmd_len = (sec_clearcmd_len+15)/16*16;

        byte[] iv = new byte[16];
        Arrays.fill(iv, (byte)0x00);
        byte[]  encryptByte = AESUtils.encrypt(Arrays.copyOfRange(sec_clear_bytes, 0, sec_clearcmd_len), getAseKey(), iv);

        Log.d("AbecsCommand", "encryptByte: " + POSUtil.byteArray2Hex(encryptByte));
        sec_cmd_bytes[sec_cmd_len++] = 0x16;
        sec_cmd_bytes[sec_cmd_len++] = 0x12;
        System.arraycopy(encryptByte, 0, sec_cmd_bytes, sec_cmd_len, encryptByte.length);
        sec_cmd_len += encryptByte.length;
        sec_cmd_bytes[sec_cmd_len++] = 0x17;

        crc = ccitt_crc16(Arrays.copyOfRange(sec_cmd_bytes, 1, sec_cmd_len), sec_cmd_len-1,0);

        Log.d("AbecsCommand", "secure bytes: " + POSUtil.byteArray2Hex(Arrays.copyOfRange(sec_cmd_bytes, 0, sec_cmd_len)));

        sec_clearcmd_len = 0;
        sec_clear_bytes[sec_clearcmd_len++] = 0x16;

        for(int i = 1; i<sec_cmd_len-1; i++){
            if(sec_cmd_bytes[i] == 0x17 || sec_cmd_bytes[i] == 0x16 || sec_cmd_bytes[i] == 0x13){
                sec_clear_bytes[sec_clearcmd_len++] = 0x13;
                sec_clear_bytes[sec_clearcmd_len++] = (byte)(sec_cmd_bytes[i]+0x20);
            }
            else{
                sec_clear_bytes[sec_clearcmd_len++] = sec_cmd_bytes[i];
            }
        }
        sec_clear_bytes[sec_clearcmd_len++] = 0x17;
        sec_clear_bytes[sec_clearcmd_len++] = (byte)((crc&0xFF00) >>8);
        sec_clear_bytes[sec_clearcmd_len++] = (byte)((crc&0x00FF));

        Log.d("AbecsCommand", "final secure bytes: " + POSUtil.byteArray2Hex(Arrays.copyOfRange(sec_clear_bytes, 0, sec_clearcmd_len)));

        return Arrays.copyOfRange(sec_clear_bytes, 0, sec_clearcmd_len);
    }



    public static byte[] checkSecCommand(byte[] data) {

        if(data == null){
            return null;
        }

        if(data.length < 5){
            return null;
        }

        if(data[0] != 0x16 || data[1] != 0x12 || data[data.length-3] != 0x17){
            Log.d("AbecsCommand", "check secure command format err: ");
            return null;
        }

        byte[] changeBytes = new byte[1024];
        int changeBytesLen = 0;

        for(int i = 1;i<data.length-2;i++){
            if(data[i] == 0x13){
                i++;
                changeBytes[changeBytesLen++] = (byte)(data[i]-0x20);
            }
            else{
                changeBytes[changeBytesLen++] = data[i];
            }
        }

        int crc = ccitt_crc16(Arrays.copyOfRange(changeBytes, 0, changeBytesLen), changeBytesLen,0);
        int checkCrc = ((data[data.length-2] & 0xFF) << 8) | (data[data.length-1] & 0xFF);
        if(crc != checkCrc){
            Log.d("AbecsCommand", "check secure command crc err: "+crc+","+checkCrc);
            return null;
        }

        Log.d("AbecsCommand", "need decrypt data: "+POSUtil.byteArray2Hex(Arrays.copyOfRange(changeBytes, 1, changeBytesLen-1))) ;

        byte[] iv = new byte[16];
        byte[]  clearBytes  = AESUtils.decrypt(Arrays.copyOfRange(changeBytes, 1, changeBytesLen-1), getAseKey(), iv);
        Log.d("AbecsCommand", "clearBytes: " + POSUtil.byteArray2Hex(clearBytes));
        assert clearBytes != null;
        int len = (clearBytes[0]&0xFF)<<8 | (clearBytes[1]&0xFF);

        crc = ccitt_crc16(Arrays.copyOfRange(clearBytes, 4, 4+len), len,0);
        checkCrc = ((clearBytes[2] & 0xFF) << 8) | (clearBytes[3] & 0xFF);
        if(crc != checkCrc){
            Log.d("AbecsCommand", "2 check secure command crc err: "+crc+","+checkCrc);
            return null;
        }

        Log.d("AbecsCommand", "check secure command success: ");


        return null;

        //1612D1A32F53C7C23A8AFE14C5FCDA895A8B17FA38

    }

}
