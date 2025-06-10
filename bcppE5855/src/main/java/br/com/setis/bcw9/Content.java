/*    */
package br.com.setis.bcw9;
/*    */
/*    */

import android.app.Application;
/*    */ import android.content.ContentProvider;
/*    */ import android.content.ContentValues;
/*    */ import android.content.Context;
/*    */ import android.database.Cursor;
/*    */ import android.net.Uri;
import android.util.Log;
/*    */ import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import br.com.setis.bibliotecapinpad.RegistroBibliotecaPinpad;
import br.com.setis.files.posplug.File;
import br.com.setis.user.posplug.User;

/*    */ //import br.com.setis.files.posplug.File;
/*    */ //import br.com.setis.user.posplug.User;
/*    */
/*    */
/*    */ public class Content
        /*    */ extends ContentProvider
        /*    */ {
    /*    */   private static Application context;

    /*    */
    /*    */
    public static Context getStoredContext() {
        /* 22 */
        return (Context) context;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public boolean onCreate() {
        Log.e("Content_1", "init");
        /* 27 */
        context = (Application) getContext().getApplicationContext();
        /* 28 */
        RegistroBibliotecaPinpad.informaClassesBiblioteca(DeviceAbecs.class.getName(), DeviceSerial.class.getName());
        /* 29 */
        User.iniciaModuloUser(null, getContext());
        /* 30 */
        File.iniciaArquivos((Context) context);
        /* 31 */
        return true;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    @Nullable
    /*    */ public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        /* 37 */
        return null;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    @Nullable
    /*    */ public String getType(@NonNull Uri uri) {
        /* 43 */
        return null;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    @Nullable
    /*    */ public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        /* 49 */
        return null;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        /* 54 */
        return 0;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        /* 59 */
        return 0;
        /*    */
    }
    /*    */
}


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\Content.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */