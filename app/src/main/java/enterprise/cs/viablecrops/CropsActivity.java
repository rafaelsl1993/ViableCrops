package enterprise.cs.viablecrops;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class CropsActivity extends AppCompatActivity {

    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private EditText Restult;
    boolean func = false;
    private ProgressBar bar_a; //variáveis utilizadas para barras de progresso
    private ProgressBar bar_b;
    private ProgressBar bar_c;
    private ProgressBar bar_d;
    private ProgressBar bar_e;
    private ProgressBar bar_f;

    public void refreshVars(){
        a = bar_a.getProgress(); //captura progresso atual da barra de progresso
        b = bar_b.getProgress();
        c = bar_c.getProgress();
        d = bar_d.getProgress();
        e = bar_e.getProgress();
        f = bar_f.getProgress();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops);

        Button btnHint = findViewById(R.id.btnHint);
        Button btnProb = findViewById(R.id.btnProb);
        Button btnHarv = findViewById(R.id.btnHarv);
        bar_a = findViewById(R.id.a);
        bar_b = findViewById(R.id.b);
        bar_c = findViewById(R.id.c);
        bar_d = findViewById(R.id.e);
        bar_e = findViewById(R.id.d);
        bar_f = findViewById(R.id.f);

        refreshVars();
        Restult = findViewById(R.id.txtResult);

        btnHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), HintActivity.class);
                intent.putExtra("a",a); //Os valores do vetor atual são serializados na Intent para serem recuperadas
                intent.putExtra("b",b);
                intent.putExtra("c",c);
                intent.putExtra("d",d);
                intent.putExtra("e",e);
                intent.putExtra("f",f);
                startActivity(intent);

            }
        });
        /*INICIO-------------------------------------------------------
        É feita a leitura do vetor atual e com base nestes dados é feita
        a consulta no Banco de Dados dos vetores salvos de acordo com o usuário logado.
        A query é composta pelas variáveis atuais e é utilizado um range
        de +/- 2 em cada variável.
        Após recuperar estes vetores do Banco de Dados, é calculado o coeficiênte angular
        entre o vetor atual e cada vetor recuperado.
        O vetor com o menor coeficiênte angular é utilizado como referência para o possível
        resultado do vetor atual.
         */
        btnProb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = MainActivity.provider.query(false, null, "username=?" + " and " + "umidade_solo<=? and umidade_solo>=? " +
                                "and umidade_ar<=? and umidade_ar>=? and adubagem<=? and adubagem>=? and pragas<=? and pragas>=? and temperatura_solo<=? " +
                                "and temperatura_solo>=? and temperatura_ar<=? and temperatura_ar>=?",
                        new String[] {MainActivity.User, String.valueOf(a+2),String.valueOf(a-2),String.valueOf(b+2),String.valueOf(b-2),String.valueOf(c+2),
                                String.valueOf(c-2),String.valueOf(d+2),String.valueOf(d-2),String.valueOf(e+2),String.valueOf(e-2),String.valueOf(f+2),
                                String.valueOf(f-2)});
                double normaA = Math.sqrt(a*a + b*b + c*c + d*d + e*e + f*f);
                int index;
                if(cursor.moveToFirst()){
                    double escalar = a*cursor.getInt(1) + b*cursor.getInt(2) + c*cursor.getInt(3) + d*cursor.getInt(4)
                            + e*cursor.getInt(5) + f*cursor.getInt(6);
                    double normaB = Math.sqrt(Math.pow(cursor.getInt(1),2) + Math.pow(cursor.getInt(2),2) + Math.pow(cursor.getInt(3),2) +
                            Math.pow(cursor.getInt(4),2) + Math.pow(cursor.getInt(5),2) + Math.pow(cursor.getInt(6),2));
                    double result = escalar / normaA * normaB;
                    int resultado = cursor.getInt(cursor.getColumnIndex("resultado"));
                    double pivo = result;

                    while(cursor.moveToNext()){
                        escalar = a*cursor.getInt(1) + b*cursor.getInt(2) + c*cursor.getInt(3) + d*cursor.getInt(4)
                                + e*cursor.getInt(5) + f*cursor.getInt(6);
                        normaB = Math.sqrt(Math.pow(cursor.getInt(1),2) + Math.pow(cursor.getInt(2),2) + Math.pow(cursor.getInt(3),2) +
                                Math.pow(cursor.getInt(4),2) + Math.pow(cursor.getInt(5),2) + Math.pow(cursor.getInt(6),2));
                        result = escalar / normaA * normaB;
                        if(pivo > result){
                            pivo = result;
                            resultado = cursor.getInt(cursor.getColumnIndex("resultado"));
                        }
                    }


                    Toast.makeText(CropsActivity.this, "The result tends to " + resultado, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CropsActivity.this, "Nenhum vetor encontrado", Toast.LENGTH_SHORT).show();
                }

            }
        });
        /*FIM-------------------------------------------------------------
         */



        /*INICIO----------------------------------------------------------
        Os dados salvos dos sensores são salvos no Banco de Dados de acordo
        com o usuário logado
         */
        btnHarv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!func){
                    Restult.setVisibility(View.VISIBLE);
                    Toast.makeText(CropsActivity.this, "Please, set the result.", Toast.LENGTH_SHORT).show();
                }else{
                    if(Restult.getText().toString().isEmpty() || Integer.parseInt(Restult.getText().toString())<0 ||
                            Integer.parseInt(Restult.getText().toString()) > 10)
                        return;
                    ContentValues values = new ContentValues();

                    values.put("umidade_solo", bar_a.getProgress());
                    values.put("umidade_ar", bar_b.getProgress());
                    values.put("adubagem", bar_c.getProgress());
                    values.put("pragas", bar_d.getProgress());
                    values.put("temperatura_solo", bar_e.getProgress());
                    values.put("temperatura_ar", bar_f.getProgress());
                    values.put("resultado", Integer.parseInt(Restult.getText().toString()));
                    values.put("username", MainActivity.User);

                    bar_a.setProgress((int)(Math.random() * 10));
                    bar_b.setProgress((int)(Math.random() * 10));
                    bar_c.setProgress((int)(Math.random() * 10));
                    bar_d.setProgress((int)(Math.random() * 10));
                    bar_e.setProgress((int)(Math.random() * 10));
                    bar_f.setProgress((int)(Math.random() * 10));

                    refreshVars();


                    MainActivity.provider.insert(false, values);
                    Restult.setVisibility(View.GONE);
                    Restult.setText("");
                    Toast.makeText(CropsActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }


                func = !func;
            }

        });

        /*FIM------------------------------------------------------------------------------
         */

    }
}
