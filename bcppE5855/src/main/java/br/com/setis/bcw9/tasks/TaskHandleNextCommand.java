package br.com.setis.bcw9.tasks;
import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import br.com.setis.bcw9.BuildConfig;


public class TaskHandleNextCommand
  extends AsyncTask<Void, Void, Integer>
{
  private static final String TAG = "TaskDirectCommand";
  private DirectMulCommandCallback commandCallback;
  private byte[] output = null;
  private static AtomicBoolean commandInProgress = null;


  public TaskHandleNextCommand(DirectMulCommandCallback DirectMulCommandCallback) {
    this.commandCallback = DirectMulCommandCallback;
    if (commandInProgress == null) {
      commandInProgress = new AtomicBoolean(false);
    }
  }



  protected Integer doInBackground(Void... param) {
    return 0;
  }




  protected void onPostExecute(Integer result) {
    if (BuildConfig.DEBUG) Log.d("TaskDirectCommand", "onPostExecute... (result=" + result + ")" + ((this.output == null || this.output.length == 0) ? "VAZIO!" : ""));

    commandInProgress.compareAndSet(true, false);
    this.commandCallback.ComandoDiretoEncerrado(null, 0, 0);
    if (BuildConfig.DEBUG) Log.d("TaskDirectCommand", "doInBackground - FIM!");
  }

  public static interface DirectMulCommandCallback {
    void ComandoDiretoEncerrado(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2);
  }
}