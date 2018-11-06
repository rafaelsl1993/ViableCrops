package enterprise.cs.viablecrops;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.annotation.Nullable;

public class DBProvider{


    private SQLiteHelper DBaseHelper;
    private SQLiteDatabase DataBase;

    DBProvider(Context context){
        DBaseHelper = new SQLiteHelper(context);
    }

    public Cursor query(boolean identifier , @Nullable String[] columns, @Nullable String rows,
                        @Nullable String[] selectionArgs){
        DataBase = DBaseHelper.getReadableDatabase();
        Cursor cursor;

        if(identifier) {
            cursor = DataBase.query("Usuario", columns, rows, selectionArgs, null, null, null);
        }

        else{
            cursor = DataBase.query("Crops", columns, rows, selectionArgs, null, null, null);
        }

        return cursor;
    }

    public long insert(boolean identifier ,@Nullable ContentValues values){
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

    public int delete(boolean identifier, @Nullable String selection, @Nullable String[] selectionArgs){
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
