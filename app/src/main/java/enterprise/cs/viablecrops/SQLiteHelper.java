package enterprise.cs.viablecrops;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATA_NAME = "DBASE.db";

    private static final String TABLE_USER = "Usuario";
    private static final String USERNAME = "username";
    private static final String SENHA = "senha";
    private static final String FULLNAME = "fullname";

    private static final String TABLE_VECTOR = "Crops";
    private static final String UMIDADE_SOLO = "umidade_solo";
    private static final String UMIDADE_AR = "umidade_ar";
    private static final String ADUBAGEM = "adubagem";
    private static final String PRAGAS = "pragas";
    private static final String TEMPERATURA_SOLO = "temperatura_solo";
    private static final String TEMPERATURA_AR = "temperatura_ar";
    private static final String RESULTADO = "resultado";

    private static final int VERSION = 1;

    SQLiteHelper(Context context) {
        super(context, DATA_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sqlUser = "CREATE TABLE " + TABLE_USER + "(" + USERNAME +
                        " VARCHAR(20) PRIMARY KEY, " + SENHA + " VARCHAR(20)," +
                         FULLNAME + " VARCHAR(50)" + ")";

        String sqlVector = "CREATE TABLE " + TABLE_VECTOR + "(" + UMIDADE_SOLO +
                " INT NOT NULL CHECK (umidade_solo BETWEEN 0 AND 10), " + UMIDADE_AR +
                " INT NOT NULL CHECK (umidade_ar BETWEEN 0 AND 10), " + ADUBAGEM +
                " INT NOT NULL CHECK (adubagem BETWEEN 0 AND 10), " + PRAGAS +
                " INT NOT NULL CHECK (pragas BETWEEN 0 AND 10), " + TEMPERATURA_SOLO +
                " INT NOT NULL CHECK (temperatura_solo BETWEEN 0 AND 10), " + TEMPERATURA_AR +
                " INT NOT NULL CHECK (resultado BETWEEN 0 AND 10), " + RESULTADO +
                " INT NOT NULL CHECK (temperatura_ar BETWEEN 0 AND 10), " +  USERNAME + " VARCHAR(20)" +
                ")";

        db.execSQL(sqlUser);
        db.execSQL(sqlVector);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VECTOR);
        onCreate(db);
    }
}

