package zombi.com.ireiscour;

/**
 * Created by maxime on 12/12/16.
 */

public enum ELieu {

        FIRM("Firminy",0),
        BEB("Bourg en Bresse",1),
        LR("La Ravoire",2),
        ANCY("Annecy",3);
        private String nom;
        private int ordre;
    ELieu(String nom,int ordre)
        {
            this.nom=nom;
            this.ordre=ordre;
        }

}
