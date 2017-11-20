package zombi.com.ireiscour.Vue;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import zombi.com.ireiscour.Model.ChoixStatic;
import zombi.com.ireiscour.R;
import zombi.com.ireiscour.Control.SharedParaChoix;

public class Login extends AppCompatActivity {
    Context context;
    protected  void gO ()
    {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_login);
        Button v_b_ok= (Button) findViewById(R.id.OK_ID);
        v_b_ok.requestFocus();
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

        v_t_pwd.setImeOptions(EditorInfo.IME_ACTION_DONE);
        v_t_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    gO();
                }
                return false;
            }
        });
        v_b_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gO();
            }
        });
    }
}
