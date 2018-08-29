package zombi.com.ireiscour.Vue;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import zombi.com.ireiscour.Control.CHttpGet;
import zombi.com.ireiscour.Control.CustomAffichageCours;
import zombi.com.ireiscour.Control.SharedParaChoix;
import zombi.com.ireiscour.Model.ChoixStatic;
import zombi.com.ireiscour.Model.row;
import zombi.com.ireiscour.Model.table;
import zombi.com.ireiscour.R;

public class Choix_principal extends AppCompatActivity {


    static TextView m_TV_totalABS;
    static TextView m_TV_totalHO;
    static TextView m_TV_derniereABS;
    static GsonXml gsonXml;
    static Context context;
    String urlUniversalHTTPS="https://www.ireis.org/etudiants/getsrv2.php?id=";
    static String abs;
    static String dateabs;
    static String ho;
    HandlerPincipal handlermsg;


    public static class HandlerPincipal extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        { super.handleMessage(msg);
            XmlParserCreator parserCreator = new XmlParserCreator() {
                @Override
                public XmlPullParser createParser() {
                    try {
                        return XmlPullParserFactory.newInstance().newPullParser();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            gsonXml = new GsonXmlBuilder()
                    .setXmlParserCreator(parserCreator)
                    .setSameNameLists(true)
                    .create();
            if(msg.what==13)
            {
                Toast.makeText(context,"ERREUR ! ID correct ?", Toast.LENGTH_LONG).show();
                return;
            }
            if(msg.what==21)
            {
                String xml = (String) msg.obj;
                if(xml==null)
                {
                    Toast.makeText(context,"Impossible de récupérer les absences",Toast.LENGTH_SHORT).show();
                    m_TV_totalABS.setText(abs+"erreur");
                }
                else
                {
                    table tabletest = gsonXml.fromXml(xml, table.class);

                    if(tabletest.row!=null)
                    {
                        for (row tmp : tabletest.row) {

                            if(ChoixStatic.getInstance().TotalABS!= tmp.totalABS)
                                m_TV_totalABS.setTextColor(Color.RED);
                            m_TV_totalABS.setText(abs+tmp.totalABS);
                            ChoixStatic.getInstance().TotalABS=tmp.totalABS;
                            SharedParaChoix.SaveChoixStatic(context);


                            Toast.makeText(context,"Réussi",Toast.LENGTH_LONG).show();

                        }
                    }
                }
            }
            if(msg.what==22)
            {
                String xml = (String) msg.obj;
                if(xml==null)
                {
                    Toast.makeText(context,"Impossible de récupérer les Heures Optionnel",Toast.LENGTH_SHORT).show();
                    m_TV_totalHO.setText(ho+"erreur");
                }
                else
                {
                    table tabletest = gsonXml.fromXml(xml, table.class);

                    if(tabletest.row!=null)
                    {
                        for (row tmp : tabletest.row) {


                            if(ChoixStatic.getInstance().TotalHO!= tmp.totalHO)
                                m_TV_totalHO.setTextColor(Color.RED);
                            ChoixStatic.getInstance().TotalHO=tmp.totalHO;
                            SharedParaChoix.SaveChoixStatic(context);
                            m_TV_totalHO.setText(ho+tmp.totalHO);
                        }
                    }

                }
            }
            if(msg.what==23)
            {
                String xml = (String) msg.obj;
                if(xml==null)
                {
                    Toast.makeText(context,"Impossible de récupérer le detail",Toast.LENGTH_SHORT).show();
                    m_TV_derniereABS.setText("erreur");
                }
                else
                {
                    table tabletest = gsonXml.fromXml(xml, table.class);

                    if(tabletest.row!=null)
                    {



                            if(!ChoixStatic.getInstance().DerniereABS.equals(tabletest.row.get(0).DateAbsence)) {
                                m_TV_derniereABS.setTextColor(Color.RED);
                            }
                            ChoixStatic.getInstance().DerniereABS=tabletest.row.get(0).DateAbsence;
                            SharedParaChoix.SaveChoixStatic(context);
                        m_TV_derniereABS.setText(dateabs+tabletest.row.get(0).DateAbsence);

                    }

                }
            }
            if(msg.what==3)
            {
                Toast.makeText(context,(String)msg.obj,Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        m_TV_totalABS=(TextView) findViewById(R.id.textview_nbABS);
        m_TV_totalABS.setText(getString(R.string.heureAbsence)+ChoixStatic.getInstance().TotalABS);
        m_TV_totalHO=(TextView) findViewById(R.id.textView_nbHO);
        m_TV_totalHO.setText(getString(R.string.heureOp)+""+ChoixStatic.getInstance().TotalHO);
        m_TV_derniereABS=(TextView) findViewById(R.id.textView_derniereABS);
        m_TV_derniereABS.setText(getString(R.string.DerniereABS)+""+ChoixStatic.getInstance().DerniereABS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_principal);
        Button v_b_actualise;
        Button v_b_actu;
        Button v_b_planning;
        Button v_b_autre;
        context =this;
        ho=getString(R.string.heureOp);
        abs=getString(R.string.heureAbsence);
        dateabs=getString(R.string.DerniereABS);


        v_b_planning= (Button) findViewById(R.id.button_mon_planing);
        v_b_actualise= (Button) findViewById(R.id.button_actualise);
        v_b_actu= (Button) findViewById(R.id.button_actu);
        v_b_autre= (Button)findViewById(R.id.button_Autre);

        v_b_autre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent v_i = new Intent(context, Choix.class);
                v_i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(v_i);
            }
        });

        v_b_planning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoixStatic s_CS=ChoixStatic.getInstance();
                s_CS.Mode=1;
                s_CS.Lieu=4;
                Intent v_i = new Intent(context, MainActivity.class);
                v_i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//                String url = "https://www.ireis.org/etudiants/";
//                v_i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                getApplicationContext().startActivity(v_i);
            }
        });
        v_b_actu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent v_i = new Intent(context, News_Intranet.class);
                v_i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//                String url = "https://www.ireis.org/etudiants/";
//                v_i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                getApplicationContext().startActivity(v_i);
            }
        });
        v_b_actualise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHttpGet.checkConn(context)) {
                    handlermsg = new HandlerPincipal();
                    Thread threadweb = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                CHttpGet httpg = new CHttpGet(handlermsg, context);
                                URL url=null;
                                Calendar calendar= Calendar.getInstance();
                                Message message=new Message();
                                message.obj="en cours...";
                                message.what=3;
                                handlermsg.sendMessage(message);
                                ChoixStatic.getInstance().NumeroSemaine=calendar.get(Calendar.WEEK_OF_YEAR);
                                SharedParaChoix.SaveChoixStatic(context);
                                url = new URL(urlUniversalHTTPS + "5" + "&login=" + ChoixStatic.getInstance().Identifiant);
                                httpg.ConnexionHTTPSAuth(ChoixStatic.getInstance().Identifiant,ChoixStatic.getInstance().MotdePasse,url,21);
                                url = new URL(urlUniversalHTTPS + "7" + "&login=" + ChoixStatic.getInstance().Identifiant);
                                httpg.ConnexionHTTPSAuth(ChoixStatic.getInstance().Identifiant,ChoixStatic.getInstance().MotdePasse,url,22);
                                url = new URL(urlUniversalHTTPS + "6" + "&login=" + ChoixStatic.getInstance().Identifiant);
                                httpg.ConnexionHTTPSAuth(ChoixStatic.getInstance().Identifiant,ChoixStatic.getInstance().MotdePasse,url,23);


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



            }
        });

    }
}
