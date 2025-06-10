#指定代码的压缩级别
-optimizationpasses 5
#包名不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 #优化  不优化输入的类文件
-dontoptimize
 #混淆时是否做预校验
-dontpreverify
 #混淆时是否记录日志
-verbose
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*
 #如果引用了v4或者v7包
-dontwarn android.support.**
#-dontwarn android.*.*
#忽略警告
-ignorewarnings
#保证是独立的jar
-dontshrink


-dontskipnonpubliclibraryclassmembers
-useuniqueclassmembernames
-keeppackagenames
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,LocalVariable*Table,Synthetic,EnclosingMethod
-keepparameternames
-dontnote

-keep, allowshrinking class  br.com.setis.** {
    *;
}

-keep class com.dspread.abecs.** {
    *;
}
-dontwarn com.dspread.abecs.**

-keep class com.dspread.dsplibrary.** {
    *;
}
-dontwarn com.dspread.abecs.**