package zombi.com.ireiscour;

/**
 * Created by maxime on 02/12/16.
 */

public enum EFormation {
    AMP("AMP",1),
    AES("AES",2),
    ASS("ASS",3),
    ES("ES",4),
    CAFERUIS("CAFERUIS",5),
    ME("ME",6);
    private String nom;
    private int ordre;
    EFormation(String nom,int ordre)
    {
        this.nom=nom;
        this.ordre=ordre;
    }
}
