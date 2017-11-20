package zombi.com.ireiscour.Control;

import android.content.Context;
import android.content.SharedPreferences;

import zombi.com.ireiscour.Model.ChoixStatic;

/**
 * Created by maxime on 09/12/16.
 */

public class SharedParaChoix {
    private SharedParaChoix()
    {

    }

    public static void RecupChoixStatic(Context context)
    {

            SharedPreferences setting=context.getSharedPreferences("IREIS",0);
            ChoixStatic.getInstance().Identifiant=setting.getString("ID",null);
        ChoixStatic.getInstance().MotdePasse=setting.getString("PWD",null);
        ChoixStatic.getInstance().Formation=setting.getString("FORM",null);
        ChoixStatic.getInstance().Lieu=setting.getInt("WHERE",99);
        ChoixStatic.getInstance().Promotion=setting.getString("PROMO",null);
        ChoixStatic.getInstance().TotalABS=setting.getFloat("TOTALABS",0);
        ChoixStatic.getInstance().TotalHO=setting.getFloat("TOTALHO",0);
        ChoixStatic.getInstance().NumeroSemaine=setting.getInt("SEMAINE",0);
        }
    public static void SaveChoixStatic(Context context)
    {
        SharedPreferences setting=context.getSharedPreferences("IREIS",0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("ID",ChoixStatic.getInstance().Identifiant);
        editor.putString("PWD",ChoixStatic.getInstance().MotdePasse);
        editor.putString("FORM",ChoixStatic.getInstance().Formation);
        editor.putInt("WHERE",ChoixStatic.getInstance().Lieu);
        editor.putString("PROMO",ChoixStatic.getInstance().Promotion);
        editor.putFloat("TOTALABS",ChoixStatic.getInstance().TotalABS);
        editor.putFloat("TOTALHO",ChoixStatic.getInstance().TotalHO);
        editor.putInt("SEMAINE",ChoixStatic.getInstance().NumeroSemaine);
        editor.commit();

    }


}
