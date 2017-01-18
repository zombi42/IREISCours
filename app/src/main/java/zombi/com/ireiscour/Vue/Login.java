package zombi.com.ireiscour.Vue;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import zombi.com.ireiscour.Model.ChoixStatic;
import zombi.com.ireiscour.R;
import zombi.com.ireiscour.Control.SharedParaChoix;

public class Login extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_login);
        Button v_b_ok= (Button) findViewById(R.id.OK_ID);
        SharedParaChoix.RecupChoixStatic(context);
        EditText v_t_id= (EditText) findViewById(R.id.identifiant);
        EditText v_t_pwd=(EditText) findViewById(R.id.motdepasse);
        if(ChoixStatic.getInstance().Identifiant!=null)
        {
            v_t_id.setText(ChoixStatic.getInstance().Identifiant);

        }
        if(ChoixStatic.getInstance().MotdePasse!=null)
        {
            v_t_pwd.setText(ChoixStatic.getInstance().MotdePasse);

        }

        v_b_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText v_t_id= (EditText) findViewById(R.id.identifiant);
                EditText v_t_pwd=(EditText) findViewById(R.id.motdepasse);

                ChoixStatic CS=ChoixStatic.getInstance();
                CS.Identifiant=v_t_id.getText().toString();
                CS.MotdePasse=v_t_pwd.getText().toString();
                Intent v_i = new Intent(context, Choix.class);
                SharedParaChoix.SaveChoixStatic(context);
                v_i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(v_i);
            }
        });
    }
}
