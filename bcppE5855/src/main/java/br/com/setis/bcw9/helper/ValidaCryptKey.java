/*     */ package br.com.setis.bcw9.helper;
/*     */ 
/*     */

import android.os.Environment;

import br.com.setis.bcw9.Content;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValidaCryptKey
/*     */ {
/*     */   private static String id;
/*     */   private static String mod;
/*     */   private static String type;
/*     */   
/*     */   public static String validCriptkey(byte bExtIdx, char cModo, char cTipo) {
/*  29 */     id = String.format(Locale.ENGLISH, "%02d", new Object[] { Byte.valueOf(bExtIdx) });
/*  30 */     mod = String.valueOf(cModo);
/*  31 */     type = String.valueOf(cTipo);
/*     */     
/*     */     try {
/*  34 */       String packgeKey = File.separator + "PPComp" + File.separator + "CryptKeyMap";
/*  35 */       String pathExternal = "", pathInternal = "";
/*     */ 
/*     */       
/*  38 */       File fileExternalStorage = getFileStorage(Environment.getExternalStorageDirectory() + packgeKey); boolean externalExist;
/*  39 */       if (externalExist = validExist(fileExternalStorage)) {
/*  40 */         pathExternal = fileExternalStorage.getCanonicalPath();
/*     */       }
/*     */       
/*  43 */       File fileInternalStorage = getFileStorage(Content.getStoredContext().getFilesDir().getPath() + packgeKey); boolean internalExist;
/*  44 */       if (internalExist = validExist(fileInternalStorage)) {
/*  45 */         pathInternal = fileInternalStorage.getCanonicalPath();
/*     */       }
/*     */       
/*  48 */       if (externalExist && internalExist) {
/*  49 */         if (validateFilesSame(pathExternal, pathInternal)) {
/*  50 */           fileExternalStorage.delete();
/*     */         } else {
/*  52 */           validParse(fileExternalStorage, fileInternalStorage);
/*     */         } 
/*  54 */       } else if (externalExist) {
/*  55 */         if (!validParse(fileExternalStorage, fileInternalStorage))
/*  56 */           return pathExternal; 
/*  57 */       } else if (!internalExist) {
/*  58 */         return "";
/*     */       } 
/*     */       
/*  61 */       return pathInternal;
/*  62 */     } catch (IOException e) {
/*  63 */       e.printStackTrace();
/*  64 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean validParse(File fileExternalStorage, File fileInternalStorage) {
/*  69 */     if (parseExternal(fileExternalStorage)) {
/*  70 */       writeInternalStorage(fileExternalStorage, fileInternalStorage);
/*  71 */       return true;
/*     */     } 
/*  73 */     return false;
/*     */   }
/*     */   
/*     */   private static File getFileStorage(String pathFile) {
/*  77 */     return new File(pathFile);
/*     */   }
/*     */   
/*     */   private static boolean validExist(File fileStorage) {
/*  81 */     return fileStorage.exists();
/*     */   }
/*     */   
/*     */   private static boolean parseExternal(File fileExternalStorage) {
/*  85 */     boolean returParse = false;
/*     */     try {
/*  87 */       StringBuilder externalContents = new StringBuilder((int)fileExternalStorage.length());
/*     */       
/*  89 */       Scanner scanner = new Scanner(fileExternalStorage); 
/*  90 */       try { while (scanner.hasNextLine()) {
/*  91 */           externalContents.append(scanner.nextLine() + System.lineSeparator());
/*     */         }
/*  93 */         scanner.close(); } catch (Throwable throwable) { try { scanner.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */          throw throwable; }
/*  95 */        Pattern pattern = Pattern.compile("\\d+;\\d+;\\d+;\\d+");
/*  96 */       Matcher matcher = pattern.matcher(externalContents);
/*  97 */       List<String> keysMatches = new ArrayList<>();
/*     */       
/*  99 */       while (matcher.find()) {
/* 100 */         keysMatches.add(matcher.group());
/*     */       }
/*     */       
/* 103 */       for (String keys : keysMatches) {
/* 104 */         List<String> valuesKey = new ArrayList<>();
/* 105 */         int cont = 0;
/* 106 */         for (int i = -1; (i = keys.indexOf(";", i + 1)) != -1; i++) {
/* 107 */           valuesKey.add(keys.substring(cont, i));
/* 108 */           cont = i + 1;
/*     */         } 
/*     */         
/* 111 */         if (!((String)valuesKey.get(0)).equals(id) || !((String)valuesKey.get(1)).equals(type) || !((String)valuesKey.get(2)).equals(mod)) {
/* 112 */           returParse = false; continue;
/*     */         } 
/* 114 */         returParse = true;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 119 */     catch (IOException e) {
/* 120 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 123 */     return returParse;
/*     */   }
/*     */   
/*     */   private static boolean validateFilesSame(String pathExternal, String pathInternal) {
/* 127 */     if (Objects.equals(fileToMD5(pathExternal), fileToMD5(pathInternal))) {
/* 128 */       return true;
/*     */     }
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writeInternalStorage(File fileExternalStorage, File fileInternalStorage) {
/*     */     try {
/* 136 */       ((File)Objects.<File>requireNonNull(fileInternalStorage.getParentFile())).mkdirs();
/* 137 */       byte[] buffer = new byte[1024];
/* 138 */       FileInputStream inputStream = new FileInputStream(fileExternalStorage);
/* 139 */       FileOutputStream outputStream = new FileOutputStream(fileInternalStorage);
/*     */       
/*     */       int size;
/* 142 */       while ((size = inputStream.read(buffer)) > 0) {
/* 143 */         outputStream.write(buffer, 0, size);
/*     */       }
/* 145 */       outputStream.flush();
/* 146 */       outputStream.close();
/* 147 */       fileExternalStorage.delete();
/* 148 */     } catch (IOException e) {
/* 149 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String fileToMD5(String filePath) {
/* 154 */     InputStream inputStream = null;
/*     */     try {
/* 156 */       inputStream = new FileInputStream(filePath);
/* 157 */       byte[] buffer = new byte[1024];
/* 158 */       MessageDigest digest = MessageDigest.getInstance("MD5");
/* 159 */       int numRead = 0;
/* 160 */       while (numRead != -1) {
/* 161 */         numRead = inputStream.read(buffer);
/* 162 */         if (numRead > 0)
/* 163 */           digest.update(buffer, 0, numRead); 
/*     */       } 
/* 165 */       byte[] md5Bytes = digest.digest();
/* 166 */       return convertHashToString(md5Bytes);
/* 167 */     } catch (Exception e) {
/* 168 */       return null;
/*     */     } finally {
/* 170 */       if (inputStream != null) {
/*     */         try {
/* 172 */           inputStream.close();
/* 173 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String convertHashToString(byte[] md5Bytes) {
/* 179 */     StringBuilder hashFile = new StringBuilder();
/* 180 */     for (byte md5Byte : md5Bytes) {
/* 181 */       hashFile.append(Integer.toString((md5Byte & 0xFF) + 256, 16).substring(1));
/*     */     }
/* 183 */     return hashFile.toString();
/*     */   }
/*     */ }


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\helper\ValidaCryptKey.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */