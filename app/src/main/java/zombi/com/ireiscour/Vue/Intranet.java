package zombi.com.ireiscour.Vue;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import zombi.com.ireiscour.Model.ChoixStatic;
import zombi.com.ireiscour.R;

/**
 * Created by maxime on 17/11/17.
 */

public class Intranet extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_intranet);
        WebView webview = (WebView) findViewById(R.id.webview);
        webview.loadUrl("https://www.ireis.org/etudiants/actu_mobil.php");
       // webview.setHttpAuthUsernamePassword("https://www.ireis.org/etudiants/actu_mobil.php","Unauthorized",ChoixStatic.getInstance().Identifiant,ChoixStatic.getInstance().MotdePasse);
      /*  webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedHttpAuthRequest(WebView view,
                                                  HttpAuthHandler handler, String host, String realm) {
                handler.proceed(ChoixStatic.getInstance().Identifiant, ChoixStatic.getInstance().MotdePasse);
            }
        });*/
    }
}
