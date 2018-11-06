package enterprise.cs.viablecrops;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class HintActivity extends AppCompatActivity {

    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    CheckBox chkUmdSolo;
    CheckBox chkUmdAr;
    CheckBox chkTempSolo;
    CheckBox chkTempAr;
    CheckBox chkAdub;
    CheckBox chkPraga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        final SeekBar txtUmdSolo = findViewById(R.id.txtUmdSolo);
        final SeekBar txtUmdAr = findViewById(R.id.txtUmdAr);
        final SeekBar txtTempSolo = findViewById(R.id.txtTempSolo);
        final SeekBar txtTempAr = findViewById(R.id.txtTempAr);
        final SeekBar txtAdub = findViewById(R.id.txtAdub);
        final SeekBar txtPraga = findViewById(R.id.txtPraga);

        txtUmdSolo.setProgress(Integer.parseInt(getIntent().getSerializableExtra("a").toString()));
        txtUmdAr.setProgress(Integer.parseInt(getIntent().getSerializableExtra("b").toString()));
        txtTempSolo.setProgress(Integer.parseInt(getIntent().getSerializableExtra("c").toString()));
        txtTempAr.setProgress(Integer.parseInt(getIntent().getSerializableExtra("d").toString()));
        txtAdub.setProgress(Integer.parseInt(getIntent().getSerializableExtra("e").toString()));
        txtPraga.setProgress(Integer.parseInt(getIntent().getSerializableExtra("f").toString()));



        chkUmdSolo = findViewById(R.id.chkUmdSolo);
        chkUmdAr = findViewById(R.id.chkUmdAr);
        chkTempSolo = findViewById(R.id.chkTempSolo);
        chkTempAr = findViewById(R.id.chkTempAr);
        chkAdub = findViewById(R.id.chkAdub);
        chkPraga = findViewById(R.id.chkPraga);

        txtUmdSolo.setEnabled(chkUmdSolo.isChecked());
        txtUmdAr.setEnabled(chkUmdAr.isChecked());
        txtTempSolo.setEnabled(chkTempSolo.isChecked());
        txtTempAr.setEnabled(chkTempAr.isChecked());
        txtAdub.setEnabled(chkAdub.isChecked());
        txtPraga.setEnabled(chkPraga.isChecked());

        Button btnCalc = findViewById(R.id.btnCalc);

        chkUmdSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUmdSolo.setEnabled(chkUmdSolo.isChecked());

            }
        });

        chkUmdAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUmdAr.setEnabled(chkUmdAr.isChecked());


            }
        });

        chkTempSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTempSolo.setEnabled(chkTempSolo.isChecked());

            }
        });

        chkTempAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTempAr.setEnabled(chkTempAr.isChecked());

            }
        });

        chkAdub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAdub.setEnabled(chkAdub.isChecked());

            }
        });

        chkPraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPraga.setEnabled(chkPraga.isChecked());

            }
        });


        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chkUmdSolo.isChecked())
                    a = txtUmdSolo.getProgress();
                if(chkUmdSolo.isChecked())
                    b = txtUmdAr.getProgress();
                if(chkUmdSolo.isChecked())
                    c = txtTempSolo.getProgress();
                if(chkUmdSolo.isChecked())
                    d = txtTempAr.getProgress();
                if(chkUmdSolo.isChecked())
                    e = txtAdub.getProgress();
                if(chkUmdSolo.isChecked())
                    f = txtPraga.getProgress();

                System.out.println("PASSOU1 ------------------------------------------------------");

                Cursor cursor = MainActivity.provider.query(false, null, "username=?" + " and " + "umidade_solo<=? and umidade_solo>=? " +
                                "and umidade_ar<=? and umidade_ar>=? and adubagem<=? and adubagem>=? and pragas<=? and pragas>=? and temperatura_solo<=? " +
                                "and temperatura_solo>=? and temperatura_ar<=? and temperatura_ar>=?",
                        new String[] {MainActivity.User, String.valueOf(a+2),String.valueOf(a-2),String.valueOf(b+2),String.valueOf(b-2),String.valueOf(c+2),
                                String.valueOf(c-2),String.valueOf(d+2),String.valueOf(d-2),String.valueOf(e+2),String.valueOf(e-2),String.valueOf(f+2),
                                String.valueOf(f-2)});
                System.out.println("PASSOU2 ------------------------------------------------------");
                if(cursor.moveToFirst()) {
                    int index = cursor.getPosition();
                    int pivo = cursor.getInt(cursor.getColumnIndex("resultado"));
                    while(cursor.moveToNext()) {
                        if (pivo < cursor.getInt(cursor.getColumnIndex("resultado"))) {
                            System.out.print("IFAO-----------------------");
                            pivo = cursor.getInt(cursor.getColumnIndex("resultado"));
                            index = cursor.getPosition();
                        }
                    }


                    Toast.makeText(HintActivity.this, "The result is Harvest set on this screen", Toast.LENGTH_SHORT).show();

                    cursor.moveToPosition(index);
                    txtUmdSolo.setProgress(cursor.getInt(1));
                    txtUmdAr.setProgress(cursor.getInt(2));
                    txtTempSolo.setProgress(cursor.getInt(3));
                    txtTempAr.setProgress(cursor.getInt(4));
                    txtAdub.setProgress(cursor.getInt(5));
                    txtPraga.setProgress(cursor.getInt(6));
                }else{
                    Toast.makeText(HintActivity.this, "No vector found..... :(", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}
