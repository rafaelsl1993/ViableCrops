package enterprise.cs.viablecrops;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static DBProvider provider;
    static String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        provider = new DBProvider(getApplicationContext());

        Button btnSgUp = findViewById(R.id.btnSgUp);
        Button btnSgIn = findViewById(R.id.btnSgIn);

        final EditText usuario = findViewById(R.id.txtUser);
        final EditText senha = findViewById(R.id.txtPass);

        btnSgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);

            }
        });

        btnSgIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = provider.query(true, null, "username=?" +
                        " AND " + "senha=?" , new String[] {usuario.getText().toString(), senha.getText().toString()});
                if(cursor.moveToFirst()){
                    User = usuario.getText().toString();
                    Intent intent = new Intent(getBaseContext(), CropsActivity.class);
                    startActivity(intent);
                }
                else{

                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
