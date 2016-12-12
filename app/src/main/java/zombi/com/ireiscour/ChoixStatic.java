package zombi.com.ireiscour;

/**
 * Created by maxime on 09/12/16.
 */

public class ChoixStatic {
    private ChoixStatic()
    {

    }
    public String Promotion;
    public String Formation;
    public int Lieu;
    private static ChoixStatic INSTANCE=null;
    public static ChoixStatic getInstance()
    {
        if(INSTANCE== null){
            INSTANCE=new ChoixStatic();
        }
        return INSTANCE;
    }
}
