package zombi.com.ireiscour;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
//import android.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView listCours;
    GsonXml gsonXml;
    Context context;
    String urlFirminy="http://www.ireis.org/getsrv.php?id=2099511";
    String urlUniversal="http://www.ireis.org/getsrv2.php?id=";
    public class MonHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        { super.handleMessage(msg);
            if(msg.what==12) {
                String xml = (String) msg.obj;
                table tabletest = gsonXml.fromXml(xml, table.class);
                ArrayList<String> champ = new ArrayList<String>();
                ChoixStatic CS=ChoixStatic.getInstance();
                for (row tmp : tabletest.row) {
                    if (tmp.Formations.contains(CS.Formation)) { //TODO: Si choix formation
                        if(tmp.Promotions.contains(CS.Promotion)) {
                            champ.add(tmp.Date);
                            champ.add(tmp.Debut);
                            champ.add(tmp.Fin);
                            champ.add(tmp.Intervenant);
                            champ.add(tmp.Intervention);
                            champ.add(tmp.Salles);
                            champ.add(tmp.Promotions);
                            champ.add(tmp.Formations);
                            champ.add("");
                        }
                    }
                }
                ArrayAdapter<String> adapterr=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,champ);
                listCours.setAdapter(adapterr);
            }

        }
    }
    MonHandler handlermsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openning);
        Gson gson=new Gson();
        context=this;
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
        //Test Exemple
       String xml;
       // xml= "<model><name>my name</name><description>my description</description></model>";
        //Test Table + row
     /*    xml="<table>\n" +
                 "<row>\n" +
                 "<Date>2016-11-15</Date>\n" +
                 "<Debut>08:30:00</Debut>\n" +
                 "<Fin>12:30:00</Fin>\n" +
                 "<Site>Firminy</Site>\n" +
                 "<Formations>ME</Formations>\n" +
                 "<Promotions>QUINO</Promotions>\n" +
                 "<Salles>Jules Dutreuil de Rhins</Salles>\n" +
                 "<Intervenant>Georges BERGERON</Intervenant>\n" +
                 "<Intervention>Les écrits du diplôme : guidance et écrits</Intervention>\n" +
                 "<id.Intervention>1517376</id.Intervention>\n" +
                 "</row>\n" +
                 "<row>\n" +
                 "<Date>2016-11-15</Date>\n" +
                 "<Debut>08:30:00</Debut>\n" +
                 "<Fin>12:30:00</Fin>\n" +
                 "<Site>Firminy</Site>\n" +
                 "<Formations>ME</Formations>\n" +
                 "<Promotions>QUINO</Promotions>\n" +
                 "<Salles>Honoré d'Urfé</Salles>\n" +
                 "<Intervenant>Catherine ANSART</Intervenant>\n" +
                 "<Intervention>Les écrits du diplôme : guidance et écrits</Intervention>\n" +
                 "<id.Intervention>1518943</id.Intervention>\n" +
                 "</row>\n" +
                 "</table>"; */
                 //Test Row
     /*  xml ="<row>\n" +
                "<Date>2016-11-15</Date>\n" +
                "<Debut>08:30:00</Debut>\n" +
                "<Fin>12:30:00</Fin>\n" +
                "<Site>Firminy</Site>\n" +
                "<Formations>ME</Formations>\n" +
                "<Promotions>QUINO</Promotions>\n" +
                "<Salles>Jules Dutreuil de Rhins</Salles>\n" +
                "<Intervenant>Georges BERGERON</Intervenant>\n" +
                "<Intervention>Les écrits du diplôme : guidance et écrits</Intervention>\n" +
                "<id.Intervention>1517376</id.Intervention>\n" +
                "</row>";

                //MEGA TEST

*/
 /*       xml ="<table xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"#\">\n" +
                "\n" +
                "<xsd:schema\n" +
                "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "\n" +
                "<xsd:simpleType name=\"DATE\">\n" +
                "  <xsd:restriction base=\"xsd:date\">\n" +
                "    <xsd:pattern value=\"\\p{Nd}{4}-\\p{Nd}{2}-\\p{Nd}{2}\"/>\n" +
                "  </xsd:restriction>\n" +
                "</xsd:simpleType>\n" +
                "\n" +
                "<xsd:simpleType name=\"TIME\">\n" +
                "  <xsd:restriction base=\"xsd:time\">\n" +
                "    <xsd:pattern value=\"\\p{Nd}{2}:\\p{Nd}{2}:\\p{Nd}{2}(.\\p{Nd}+)?\"/>\n" +
                "  </xsd:restriction>\n" +
                "</xsd:simpleType>\n" +
                "\n" +
                "<xsd:simpleType name=\"VARCHAR\">\n" +
                "  <xsd:restriction base=\"xsd:string\">\n" +
                "  </xsd:restriction>\n" +
                "</xsd:simpleType>\n" +
                "\n" +
                "<xsd:simpleType name=\"UDT.ireis.pg_catalog.text\">\n" +
                "  <xsd:restriction base=\"xsd:string\">\n" +
                "  </xsd:restriction>\n" +
                "</xsd:simpleType>\n" +
                "\n" +
                "<xsd:simpleType name=\"INTEGER\">\n" +
                "  <xsd:restriction base=\"xsd:int\">\n" +
                "    <xsd:maxInclusive value=\"2147483647\"/>\n" +
                "    <xsd:minInclusive value=\"-2147483648\"/>\n" +
                "  </xsd:restriction>\n" +
                "</xsd:simpleType>\n" +
                "\n" +
                "<xsd:complexType name=\"RowType\">\n" +
                "  <xsd:sequence>\n" +
                "    <xsd:element name=\"Date\" type=\"DATE\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Debut\" type=\"TIME\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Fin\" type=\"TIME\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Site\" type=\"VARCHAR\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Formations\" type=\"UDT.ireis.pg_catalog.text\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Promotions\" type=\"UDT.ireis.pg_catalog.text\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Salles\" type=\"UDT.ireis.pg_catalog.text\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Intervenant\" type=\"UDT.ireis.pg_catalog.text\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Intervention\" type=\"UDT.ireis.pg_catalog.text\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"id.Intervention\" type=\"INTEGER\" nillable=\"true\"></xsd:element>\n" +
                "  </xsd:sequence>\n" +
                "</xsd:complexType>\n" +
                "\n" +
                "<xsd:complexType name=\"TableType\">\n" +
                "  <xsd:sequence>\n" +
                "    <xsd:element name=\"row\" type=\"RowType\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n" +
                "  </xsd:sequence>\n" +
                "</xsd:complexType>\n" +
                "\n" +
                "<xsd:element name=\"table\" type=\"TableType\"/>\n" +
                "\n" +
                "</xsd:schema>\n" +
                "\n" +
                "<row>\n" +
                "  <Date>2016-11-15</Date>\n" +
                "  <Debut>08:30:00</Debut>\n" +
                "  <Fin>12:30:00</Fin>\n" +
                "  <Site>Firminy</Site>\n" +
                "  <Formations>ME</Formations>\n" +
                "  <Promotions>QUINO</Promotions>\n" +
                "  <Salles>Jules Dutreuil de Rhins </Salles>\n" +
                "  <Intervenant>Georges BERGERON</Intervenant>\n" +
                "  <Intervention>Les écrits du diplôme : guidance et écrits</Intervention>\n" +
                "  <id.Intervention>1517376</id.Intervention>\n" +
                "</row>\n" +
                "\n" +
                "<row>\n" +
                "  <Date>2016-11-15</Date>\n" +
                "  <Debut>08:30:00</Debut>\n" +
                "  <Fin>12:30:00</Fin>\n" +
                "  <Site>Firminy</Site>\n" +
                "  <Formations>ME</Formations>\n" +
                "  <Promotions>QUINO</Promotions>\n" +
                "  <Salles>Honoré d'Urfé </Salles>\n" +
                "  <Intervenant>Catherine ANSART</Intervenant>\n" +
                "  <Intervention>Les écrits du diplôme : guidance et écrits</Intervention>\n" +
                "  <id.Intervention>1518943</id.Intervention>\n" +
                "</row>\n" +
                "\n" +
                "<row>\n" +
                "  <Date>2016-11-15</Date>\n" +
                "  <Debut>09:00:00</Debut>\n" +
                "  <Fin>12:30:00</Fin>\n" +
                "  <Site>Firminy</Site>\n" +
                "  <Formations>AES</Formations>\n" +
                "  <Promotions>RAVACHOL</Promotions>\n" +
                "  <Salles>Barthélémy Thimonier </Salles>\n" +
                "  <Intervenant>Magali TALEB</Intervenant>\n" +
                "  <Intervention>Développement de l'enfant</Intervention>\n" +
                "  <id.Intervention>1907308</id.Intervention>\n" +
                "</row>\n" +
                "\n" +
                "<row>\n" +
                "  <Date>2016-11-15</Date>\n" +
                "  <Debut>09:00:00</Debut>\n" +
                "  <Fin>12:30:00</Fin>\n" +
                "  <Site>Firminy</Site>\n" +
                "  <Formations>ES</Formations>\n" +
                "  <Promotions>VAE</Promotions>\n" +
                "  <Salles>Michel Rondet </Salles>\n" +
                "  <Intervenant>Eloïse GIRAULT</Intervenant>\n" +
                "  <Intervention>S.F. Protection de l'enfance</Intervention>\n" +
                "  <id.Intervention>2016587</id.Intervention>\n" +
                "</row>\n" +
                "\n" +
                "<row>\n" +
                "  <Date>2016-11-15</Date>\n" +
                "  <Debut>10:30:00</Debut>\n" +
                "  <Fin>12:30:00</Fin>\n" +
                "  <Site>Firminy</Site>\n" +
                "  <Formations>ASS / ES</Formations>\n" +
                "  <Promotions>QUINO</Promotions>\n" +
                "  <Salles>Amphi Rez de jardin</Salles>\n" +
                "  <Intervenant>Dominique BESSON</Intervenant>\n" +
                "  <Intervention>Droit pénal</Intervention>\n" +
                "  <id.Intervention>1522983</id.Intervention>\n" +
                "</row>\n" +
                "\n" +
                "</table>\n";
        xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<table xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"#\">\n" +
                "\n" +
                "<xsd:schema\n" +
                "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "\n" +
                "<xsd:simpleType name=\"DATE\">\n" +
                "  <xsd:restriction base=\"xsd:date\">\n" +
                "    <xsd:pattern value=\"\\p{Nd}{4}-\\p{Nd}{2}-\\p{Nd}{2}\"/>\n" +
                "  </xsd:restriction>\n" +
                "</xsd:simpleType>\n" +
                "\n" +
                "<xsd:simpleType name=\"TIME\">\n" +
                "  <xsd:restriction base=\"xsd:time\">\n" +
                "    <xsd:pattern value=\"\\p{Nd}{2}:\\p{Nd}{2}:\\p{Nd}{2}(.\\p{Nd}+)?\"/>\n" +
                "  </xsd:restriction>\n" +
                "</xsd:simpleType>\n" +
                "\n" +
                "<xsd:simpleType name=\"VARCHAR\">\n" +
                "  <xsd:restriction base=\"xsd:string\">\n" +
                "  </xsd:restriction>\n" +
                "</xsd:simpleType>\n" +
                "\n" +
                "<xsd:simpleType name=\"UDT.ireis.pg_catalog.text\">\n" +
                "  <xsd:restriction base=\"xsd:string\">\n" +
                "  </xsd:restriction>\n" +
                "</xsd:simpleType>\n" +
                "\n" +
                "<xsd:simpleType name=\"INTEGER\">\n" +
                "  <xsd:restriction base=\"xsd:int\">\n" +
                "    <xsd:maxInclusive value=\"2147483647\"/>\n" +
                "    <xsd:minInclusive value=\"-2147483648\"/>\n" +
                "  </xsd:restriction>\n" +
                "</xsd:simpleType>\n" +
                "\n" +
                "<xsd:complexType name=\"RowType\">\n" +
                "  <xsd:sequence>\n" +
                "    <xsd:element name=\"Date\" type=\"DATE\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Debut\" type=\"TIME\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Fin\" type=\"TIME\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Site\" type=\"VARCHAR\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Formations\" type=\"UDT.ireis.pg_catalog.text\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Promotions\" type=\"UDT.ireis.pg_catalog.text\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Salles\" type=\"UDT.ireis.pg_catalog.text\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Intervenant\" type=\"UDT.ireis.pg_catalog.text\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"Intervention\" type=\"UDT.ireis.pg_catalog.text\" nillable=\"true\"></xsd:element>\n" +
                "    <xsd:element name=\"id.Intervention\" type=\"INTEGER\" nillable=\"true\"></xsd:element>\n" +
                "  </xsd:sequence>\n" +
                "</xsd:complexType>\n" +
                "\n" +
                "<xsd:complexType name=\"TableType\">\n" +
                "  <xsd:sequence>\n" +
                "    <xsd:element name=\"row\" type=\"RowType\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n" +
                "  </xsd:sequence>\n" +
                "</xsd:complexType>\n" +
                "\n" +
                "<xsd:element name=\"table\" type=\"TableType\"/>\n" +
                "\n" +
                "</xsd:schema>\n" +
                "\n" +
                "<row>\n" +
                "  <Date>2016-11-24</Date>\n" +
                "  <Debut>13:30:00</Debut>\n" +
                "  <Fin>17:00:00</Fin>\n" +
                "  <Site>Firminy</Site>\n" +
                "  <Formations>AMP</Formations>\n" +
                "  <Promotions>QUINO</Promotions>\n" +
                "  <Salles>Jean DastÃ© </Salles>\n" +
                "  <Intervenant>Patrick Roland OBEJI</Intervenant>\n" +
                "  <Intervention>DF1 : Handicap psychomoteur</Intervention>\n" +
                "  <id.Intervention>1320944</id.Intervention>\n" +
                "</row>\n" +
                "\n" +
                "</table>";*/
       // XML model = gsonXml.fromXml(xml,XML.class);

     // row testrow= gsonXml.fromXml(xml,row.class);
        //TextView test=(TextView)findViewById(R.id.textView2);
        listCours= (ListView) findViewById(R.id.listView);
       // ArrayList<String> adapter = new Arr
      // ArrayAdapter<row> adapterr = new ArrayAdapter<row>(this,android.R.layout.simple_list_item_1,tabletest.row);
      //  table tabletest = gsonXml.fromXml(xml,table.class);
        ArrayList<String> champ=new ArrayList<String>();
      //  for (row tmp: tabletest.row   )
      //  {
         champ.add("CHARGEMENT");
            champ.add("Merci de patienter");
      //      champ.add(tmp.Intervenant);
     //       champ.add(tmp.Intervention);
       //     champ.add(tmp.Salles);
       //     champ.add(tmp.Promotions);
        //    champ.add(tmp.Formations);
            champ.add("");
       // }
        ArrayAdapter<String> adapterr=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,champ);
        listCours.setAdapter(adapterr);
        if(CHttpGet.checkConn(context)) {
            handlermsg = new MonHandler();
            Thread threadweb = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CHttpGet httpg = new CHttpGet(handlermsg, context);
                        URL url = new URL(urlUniversal+ChoixStatic.getInstance().Lieu);

                        httpg.Connectionhttp(url, 12);
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
