package database;

public class Puntuacion {

    private int _id;
    private int puntos;
    private int equipo;

    public final static int EQUIPO_LOCAL = 1;
    public final static int EQUIPO_VISITANTE = 0;

    public Puntuacion(int _id, int puntos, int equipo) {
        this._id = _id;
        this.puntos = puntos;
        this.equipo = equipo;
    }

    public Puntuacion(int puntos, int equipo) {
        this.puntos = puntos;
        this.equipo = equipo;
    }

    public Puntuacion() {

    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getEquipo() {
        return equipo;
    }

    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }
}
