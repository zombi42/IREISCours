package zombi.com.ireiscour.Model;

/**
 * Created by maxime on 09/12/16.
 * il est trop genial <3
 * cette classe, est utilisé pour transferer les information entre les vues
 * et est le model pour les sauvegardes persistantes
 */

public class ChoixStatic {
    private ChoixStatic()
    {

    }
    public String Promotion;
    public String Formation;
    public int Lieu;
    public String Identifiant;
    public String MotdePasse;
    public short Mode; // 1 = LE MIEN , 0 = GENERIQUE
    private static ChoixStatic INSTANCE=null;
    public static ChoixStatic getInstance()
    {
        if(INSTANCE== null){
            INSTANCE=new ChoixStatic();
        }
        return INSTANCE;
    }
}
