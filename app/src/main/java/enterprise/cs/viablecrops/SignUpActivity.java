package enterprise.cs.viablecrops;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        int a,b,c,d,e,f,g;

        ContentValues values = new ContentValues();

        for(int i = 0; i <= 100; i++){

            a = (int)(Math.random() * 10);
            b = (int)(Math.random() * 10);
            c = (int)(Math.random() * 10);
            d = (int)(Math.random() * 10);
            e = (int)(Math.random() * 10);
            f = (int)(Math.random() * 10);
            g = (int)(Math.random() * 10);

            values.put("umidade_solo", a);
            values.put("umidade_ar", b);
            values.put("adubagem", c);
            values.put("pragas", d);
            values.put("temperatura_solo", e);
            values.put("temperatura_ar", f);
            values.put("resultado", g);
            values.put("username", "a");

            System.out.println(MainActivity.provider.insert(false, values));
            values.clear();
        }

        final EditText txtName = findViewById(R.id.txtName);
        final EditText txtUser = findViewById(R.id.txtUserSgUp);
        final EditText txtPassw = findViewById(R.id.txtPassw);
        final EditText txtPasswConf = findViewById(R.id.txtPassConf);

        Button btnEndSgUp_to_Crops = findViewById(R.id.btnEndSgUp_to_Crops);

        btnEndSgUp_to_Crops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean Test = !txtUser.getText().toString().isEmpty() && !txtName.getText().toString().isEmpty();

                if(txtPassw.getText().toString().equals(txtPasswConf.getText().toString()) && Test) {

                    ContentValues values = new ContentValues();

                    values.put("username", txtUser.getText().toString());
                    values.put("fullname", txtName.getText().toString());
                    values.put("senha", txtPassw.getText().toString());

                    long returned = MainActivity.provider.insert(true, values);

                    if(returned  > 0) {

                        MainActivity.User = txtUser.getText().toString();

                        Intent intent = new Intent(getBaseContext(), CropsActivity.class);
                        startActivity(intent);
                    }

                    else{

                        Toast.makeText(SignUpActivity.this, "Something went wrong, username already exists!", Toast.LENGTH_SHORT).show();
                    }
                }

                else{

                    Toast.makeText(SignUpActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
