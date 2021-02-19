package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

public class PuntuacionDB extends SQLiteOpenHelper {

    private static SQLiteDatabase db;

    private static PuntuacionDB puntuacionDB;

    public static PuntuacionDB getInstance(Context context) {
        if(puntuacionDB == null) {
            puntuacionDB = new PuntuacionDB(context);
        }
        return  puntuacionDB;
    }

    private PuntuacionDB(Context context) {
        super(context, "puntuaciones.db", null, 1);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table puntuaciones(_id integer primary key autoincrement, puntos integer, equipo integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int insertarPuntuacion(Puntuacion puntuacion) {
        ContentValues cv = new ContentValues();
        cv.put("puntos", puntuacion.getPuntos());
        cv.put("equipo", puntuacion.getEquipo());
        return (int) db.insert("puntuaciones", null, cv);
    }

    public LinkedList<Puntuacion> getPuntuaciones() {
        LinkedList<Puntuacion> llPuntuaciones = new LinkedList<>();
        Cursor cursor = db.rawQuery("select _id, puntos, equipo from puntuaciones order by _id", null);
        if(cursor.moveToFirst()){
            do {
                Puntuacion puntuacion = new Puntuacion();
                puntuacion.setId(cursor.getInt(0));
                puntuacion.setPuntos(cursor.getInt(1));
                puntuacion.setEquipo(cursor.getInt(2));
                llPuntuaciones.add(puntuacion);
            }
            while (cursor.moveToNext());
        }
        return llPuntuaciones;
    }

    public void borrarPuntuacion(int id) {
        db.execSQL("delete from puntuaciones where _id = " + id);
    }

    public void reset() {
        db.execSQL("delete from puntuaciones");
    }




}
