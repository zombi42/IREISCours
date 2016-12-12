package zombi.com.ireiscour;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by maxime on 25/11/16.
 */

public class CHttpGet {
    private Handler m_handler;
    private Context m_context;
   public CHttpGet(Handler v_handler, Context context)
   {
       m_handler=v_handler;
       m_context=context;
   }
    public boolean Connectionhttp (URL url, int idMessage) {
            if (checkConn(m_context)) {
                try {
                  BufferedReader in= new BufferedReader(new InputStreamReader(url.openStream()));
                    String str,text="";
                    while ((str = in.readLine()) != null) {
                       text=text+str;
                    }
                    in.close();
                    Message message=new Message();
                    message.obj=text;
                    message.what=idMessage;
                    m_handler.sendMessage(message);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;

                }
            }
            else
            {
                return false;
            }

    }
    public static boolean checkConn(Context v_ctx)
    {
        boolean v_ret=true;
        ConnectivityManager v_ConMgr =  (ConnectivityManager)v_ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (v_ConMgr != null) {
            NetworkInfo v_NetInfo= v_ConMgr.getActiveNetworkInfo();
            if (v_NetInfo!= null) {
                if (!v_NetInfo.isConnected())
                    v_ret= false;
                if (!v_NetInfo.isAvailable())
                    v_ret= false;
            }
            else
                v_ret= false;
        } else
            v_ret= false;
        return v_ret;
    }
}
