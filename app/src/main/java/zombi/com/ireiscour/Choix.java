package zombi.com.ireiscour;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Choix extends AppCompatActivity {

    RadioGroup m_RB_forma;
    RadioGroup m_RB_promo;
    RadioGroup m_RB_lieu;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);
        Button v_b_ok;
        context =this;
        v_b_ok= (Button) findViewById(R.id.OK);
        m_RB_forma=(RadioGroup) findViewById(R.id.radioGroupF);
        m_RB_promo=(RadioGroup) findViewById(R.id.radioGroupP);
        m_RB_lieu=(RadioGroup) findViewById(R.id.radioGroupL);
        v_b_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton v_rb_f=(RadioButton) findViewById(m_RB_forma.getCheckedRadioButtonId());
                RadioButton v_rb_p=(RadioButton) findViewById(m_RB_promo.getCheckedRadioButtonId());
                RadioButton v_rb_l=(RadioButton) findViewById(m_RB_lieu.getCheckedRadioButtonId());
                ChoixStatic s_CS=ChoixStatic.getInstance();
                if(v_rb_f!=null && v_rb_p!=null && v_rb_l!=null) {
                    s_CS.Formation = v_rb_f.getText().toString();
                    s_CS.Promotion = v_rb_p.getText().toString();

                        if(v_rb_l.getText().toString().contains(getString(R.string.Firm))) {
                            s_CS.Lieu = 0;
                        }
                         else if(v_rb_l.getText().toString().contains(getString(R.string.BeB))) {
                            s_CS.Lieu = 1;
                        }
                        else if(v_rb_l.getText().toString().contains(getString(R.string.Ancy))) {
                            s_CS.Lieu = 2;
                        }
                        else if(v_rb_l.getText().toString().contains(getString(R.string.LR))) {
                            s_CS.Lieu = 3;
                        }

                    //TEST
                  //  s_CS = ChoixStatic.getInstance();
                    Intent v_i = new Intent(context, MainActivity.class);
                    v_i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(v_i);
                }
                else
                {
                    Toast.makeText(context,"Merci de choisir une FORMATION, une PROMOTION et un LIEU",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
