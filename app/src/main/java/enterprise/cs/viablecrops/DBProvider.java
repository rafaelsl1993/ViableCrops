package enterprise.cs.viablecrops;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.annotation.Nullable;

public class DBProvider{

    private SQLiteHelper DBaseHelper; //Instância do helper do banco de dados
    private SQLiteDatabase DataBase; //Instância do banco de dados SQLite

    DBProvider(Context context){
        DBaseHelper = new SQLiteHelper(context);
    }

    public Cursor query(boolean identifier , @Nullable String[] columns, @Nullable String rows, //Método que retorna a posição do cursor do banco de dados
                        @Nullable String[] selectionArgs){
        DataBase = DBaseHelper.getReadableDatabase();
        Cursor cursor;

        if(identifier) { //a variável identifier determina se o registro buscado é um usuário(true) ou um vetor de plantio(false)
            cursor = DataBase.query("Usuario", columns, rows, selectionArgs, null, null, null);
        }

        else{
            cursor = DataBase.query("Crops", columns, rows, selectionArgs, null, null, null);
        }

        return cursor;
    }

    public long insert(boolean identifier ,@Nullable ContentValues values){ //Método de inserção de registro no banco de dados
        DataBase = DBaseHelper.getWritableDatabase();

        long id;

        if(identifier) {
            id = DataBase.insert("Usuario", null, values);
        }
        else{
            id = DataBase.insert("Crops", null, values);
        }

        DataBase.close();
        return id;
    }

    public int delete(boolean identifier, @Nullable String selection, @Nullable String[] selectionArgs){ //Método de exclusão de registro do banco
        DataBase = DBaseHelper.getWritableDatabase();

        if(identifier) {
            DataBase.delete("Usuario", selection, selectionArgs);
        }
        else{
            DataBase.delete("Crops", selection, selectionArgs);
        }
        DataBase.close();
        return 1;
    }

}
