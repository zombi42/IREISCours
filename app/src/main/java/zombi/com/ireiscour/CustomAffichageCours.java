package zombi.com.ireiscour;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maxime on 05/01/17.
 */

public class CustomAffichageCours extends ArrayAdapter{
    private final Activity context;
    private final String[] cours;
    private final ArrayList<HashMap<String,String>> detail;
    public CustomAffichageCours(Activity context,
                      String[] web, ArrayList<HashMap<String,String>> imageId) {
        super(context, R.layout.layout_cours, web);
        this.cours = web;
        this.context = context;
        this.detail = imageId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //
        LayoutInflater inflater=context.getLayoutInflater();
        View rowview=inflater.inflate(R.layout.layout_cours,null,true);
        //COUR
        TextView txtcours=(TextView) rowview.findViewById(R.id.Cour);
        txtcours.setText(cours[position]);
        //DATE
        TextView date=(TextView) rowview.findViewById(R.id.cadreDate);
        date.setText(detail.get(position).get("DATE"));
        //heuredebut
         date=(TextView) rowview.findViewById(R.id.cadreHeureDebut);
        date.setText(detail.get(position).get("HEUREDEB"));
        //heure fin
         date=(TextView) rowview.findViewById(R.id.cadreHeureFin);
        date.setText(detail.get(position).get("HEUREFIN"));
        //intervenant
         date=(TextView) rowview.findViewById(R.id.Intervenant);
        date.setText(detail.get(position).get("INTERVENANT"));
        //SALLE
         date=(TextView) rowview.findViewById(R.id.Salle);
        date.setText(detail.get(position).get("SALLE"));
        //PROMO
         date=(TextView) rowview.findViewById(R.id.Ppromo);
        date.setText(detail.get(position).get("PROMO"));

        return rowview;
    }
}
