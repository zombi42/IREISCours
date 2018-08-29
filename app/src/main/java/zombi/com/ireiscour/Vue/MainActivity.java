package zombi.com.ireiscour.Vue;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
//import android.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import zombi.com.ireiscour.Control.CHttpGet;
import zombi.com.ireiscour.Control.SharedParaChoix;
import zombi.com.ireiscour.Model.ChoixStatic;
import zombi.com.ireiscour.Control.CustomAffichageCours;
import zombi.com.ireiscour.R;
import zombi.com.ireiscour.Model.row;
import zombi.com.ireiscour.Model.table;


public class MainActivity extends AppCompatActivity {
    static ListView listCours;
    static Activity act;
    static GsonXml gsonXml;
    static Context context;
    //String urlFirminy="http://www.ireis.org/getsrv.php?id=2099511";
  //  String urlUniversal="http://www.ireis.org/getsrv2.php?id=";
    String urlUniversalHTTPS="https://www.ireis.org/etudiants/getsrv2.php?id=";
    public static class MonHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        { super.handleMessage(msg);
            ArrayList<String> champ = new ArrayList<>();
            ArrayList<HashMap<String,String>> champv2=new ArrayList<>();
            if(msg.what==12) {
                String xml = (String) msg.obj;
                if(xml==null)
                {
                    champ.add("ERREUR");
                    champ.add("Le serveur ne repond pas");
                }
                else
                {
                    table tabletest = gsonXml.fromXml(xml, table.class);

                    ChoixStatic CS = ChoixStatic.getInstance();
                    if(tabletest.row!=null)
                    {
                        for (row tmp : tabletest.row) {
                            if(ChoixStatic.getInstance().Mode==1)
                            {
                                champ.add(tmp.Intervention);
                                HashMap<String,String> mappy=new HashMap<>();
                                mappy.put("DATE",tmp.Date);
                                mappy.put("HEUREDEB",tmp.Debut);
                                mappy.put("HEUREFIN",tmp.Fin);
                                mappy.put("INTERVENANT",tmp.Intervenant);
                                mappy.put("SALLE",tmp.Salles);
                                champv2.add(mappy);
                            }
                            else if (tmp.Formations.contains(CS.Formation )|| ChoixStatic.getInstance().Mode==1) {
                                if (tmp.Promotions.contains(CS.Promotion)|| ChoixStatic.getInstance().Mode==1) {

                                    champ.add(tmp.Intervention);
                                    HashMap<String,String> mappy=new HashMap<>();
                                    mappy.put("DATE",tmp.Date);
                                    mappy.put("HEUREDEB",tmp.Debut);
                                    mappy.put("HEUREFIN",tmp.Fin);
                                    mappy.put("INTERVENANT",tmp.Intervenant);
                                    mappy.put("SALLE",tmp.Salles);
                                   //TEST D'AJOUT DU SICJECTELIER ! SIC JEC ATELIER
                                    if(CS.Mode==0)
                                    {
                                        if (tmp.Groupe.contains("SIC"))
                                            mappy.put("PROMO", "SIC");
                                        else if (tmp.Groupe.contains("JEC"))
                                            mappy.put("PROMO", "JEC");
                                        else if (tmp.Groupe.contains("ATELIER"))
                                            mappy.put("PROMO", "ATELIER");
                                        else
                                            mappy.put("PROMO", "");
                                    }
                                    /// / mappy.put("PROMO",tmp.Promotions);
                                    champv2.add(mappy);
                                }
                            }
                        }
                    }
                    if(champ.isEmpty())
                    {
                        champ.add("Aucun cours pour cette combinaison");
                        ArrayAdapter<String> adapterr=new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,champ);
                        listCours.setAdapter(adapterr);
                        return;
                    }
                }

            }
            if(msg.what==13)
            {
                Toast.makeText(context,"ERREUR ! ID correct ?", Toast.LENGTH_LONG).show();
                champ.add("ERREUR");
                ArrayAdapter<String> adapterr= new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, champ);
                listCours.setAdapter(adapterr);
                return;
            }
            if(msg.what==21)
            {
                String xml = (String) msg.obj;
                if(xml==null)
                {
                   Toast.makeText(context,"Impossible de récupérer les absences",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    table tabletest = gsonXml.fromXml(xml, table.class);

                    ChoixStatic CS = ChoixStatic.getInstance();
                    if(tabletest.row!=null)
                    {
                        for (row tmp : tabletest.row) {


                            ChoixStatic.getInstance().TotalABS=tmp.totalABS;
                            SharedParaChoix.SaveChoixStatic(context);
                                                   }
                    }
                    if(champ.isEmpty())
                    {
                       return;
                    }
                }
            }
            if(msg.what==22)
            {
                String xml = (String) msg.obj;
                if(xml==null)
                {
                    Toast.makeText(context,"Impossible de récupérer les Heures Optionnel",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    table tabletest = gsonXml.fromXml(xml, table.class);

                    ChoixStatic CS = ChoixStatic.getInstance();
                    if(tabletest.row!=null)
                    {
                        for (row tmp : tabletest.row) {

                            ChoixStatic.getInstance().TotalHO=tmp.totalHO;
                            SharedParaChoix.SaveChoixStatic(context);
                        }
                    }
                    if(champ.isEmpty())
                    {
                        return;
                    }
                }
            }
            String[] teststring=new String[champ.size()];
                    teststring=champ.toArray(teststring);
            CustomAffichageCours adapteur=new CustomAffichageCours(act,teststring,champv2);
            listCours.setAdapter(adapteur);
          //  ArrayAdapter<String> adapterr=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,champ);
           // listCours.setAdapter(adapterr);

        }
    }
    MonHandler handlermsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Gson gson=new Gson();
        context=this;
        act=this;
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

        listCours= (ListView) findViewById(R.id.listView);
       // ArrayList<String> adapter = new Arr
      // ArrayAdapter<row> adapterr = new ArrayAdapter<row>(this,android.R.layout.simple_list_item_1,tabletest.row);
      //  table tabletest = gsonXml.fromXml(xml,table.class);
        ArrayList<String> champ= new ArrayList<>();
      //  for (row tmp: tabletest.row   )
      //  {
         champ.add("CHARGEMENT");
            champ.add("Merci de patienter");

        ArrayAdapter<String> adapterr=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,champ);
        listCours.setAdapter(adapterr);
        if(CHttpGet.checkConn(context)) {
            handlermsg = new MonHandler();
            Thread threadweb = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CHttpGet httpg = new CHttpGet(handlermsg, context);
                        URL url=null;
                        if(ChoixStatic.getInstance().Mode==0) {
                             url = new URL(urlUniversalHTTPS + ChoixStatic.getInstance().Lieu);
                        }
                        else if(ChoixStatic.getInstance().Mode==1)
                        {
                             url = new URL(urlUniversalHTTPS + ChoixStatic.getInstance().Lieu + "&login=" + ChoixStatic.getInstance().Identifiant);
                        }

                        httpg.ConnexionHTTPSAuth(ChoixStatic.getInstance().Identifiant,ChoixStatic.getInstance().MotdePasse,url,12);
                        Calendar calendar= Calendar.getInstance();
                        if(calendar.get(Calendar.WEEK_OF_YEAR)!= ChoixStatic.getInstance().NumeroSemaine|| ChoixStatic.getInstance().TotalABS==0 || ChoixStatic.getInstance().TotalHO==0)
                        {
                            ChoixStatic.getInstance().NumeroSemaine=calendar.get(Calendar.WEEK_OF_YEAR);
                            SharedParaChoix.SaveChoixStatic(context);
                            url = new URL(urlUniversalHTTPS + "5" + "&login=" + ChoixStatic.getInstance().Identifiant);
                            httpg.ConnexionHTTPSAuth(ChoixStatic.getInstance().Identifiant,ChoixStatic.getInstance().MotdePasse,url,21);
                            url = new URL(urlUniversalHTTPS + "7" + "&login=" + ChoixStatic.getInstance().Identifiant);
                            httpg.ConnexionHTTPSAuth(ChoixStatic.getInstance().Identifiant,ChoixStatic.getInstance().MotdePasse,url,22);
                        }


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


       // Toast.makeText(this.getBaseContext(),tabletest.row.get(0).Date,Toast.LENGTH_LONG).show();
      //  test.setText(model.getName() +" lol "+model.getDescription());




    }
}
