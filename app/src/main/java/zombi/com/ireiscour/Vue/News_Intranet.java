package zombi.com.ireiscour.Vue;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

import zombi.com.ireiscour.Control.CHttpGet;
import zombi.com.ireiscour.Model.ChoixStatic;
import zombi.com.ireiscour.R;

public class News_Intranet extends AppCompatActivity {
   static WebView webview;
   static TextView tv;
   Context context;
   Handlerweb handler;
   String urlactu="https://www.ireis.org/etudiants/actu_mobil.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__intranet);
        context=getBaseContext();
        tv= (TextView)findViewById(R.id.chargement);
        webview = (WebView) findViewById(R.id.web_intra);
        if(CHttpGet.checkConn(context)) {
            handler = new Handlerweb();
            Thread threadweb = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CHttpGet httpg = new CHttpGet(handler, context);
                        URL url=null;

                            url = new URL(urlactu);


                        httpg.ConnexionHTTPSAuth(ChoixStatic.getInstance().Identifiant,ChoixStatic.getInstance().MotdePasse,url,33);

                    } catch (IOException e) {
                        throw new RuntimeException(e);

                    }

                }
            });
            threadweb.start();
        }
        else
        {
            Toast.makeText(context,"Connnexion necessaire!",Toast.LENGTH_LONG).show();
        }
        /*  webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
                handler.proceed(ChoixStatic.getInstance().Identifiant,ChoixStatic.getInstance().MotdePasse);
            }
        });
       // webview.setHttpAuthUsernamePassword("https://www.ireis.org/etudiants/","Authentification requise",ChoixStatic.getInstance().Identifiant ,ChoixStatic.getInstance().MotdePasse);
        webview.loadUrl("https://www.ireis.org/etudiants/actu_mobil.php");
*/

    }
    public static class Handlerweb extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==33) {
                webview.loadDataWithBaseURL(null,(String)msg.obj,"text/html","utf-8",null);
                tv.setText("");

            }

        }
    }

}
