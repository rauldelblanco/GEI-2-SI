package es.udc.sistemasinteligentes;

public class Nodo implements Comparable<Nodo>{

    private Estado estado;
    private final Nodo padre;
    private Accion accion;
    private float coste;
    private float f;


    public Nodo(Estado e, Nodo n, Accion a){ //Introducimos el método para crear un Nodo visto en teoría.
        this.estado = e;
        this.padre  = n;
        this.accion = a;
    }

    //Getters y Setters necesarios para el desarrollo del programa.

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Nodo getPadre() {
        return padre;
    }

    public Accion getAccion() {
        return accion;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public float getCoste() {
        return coste;
    }

    public void setCoste(float coste) {
        this.coste = coste;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    @Override
    public int compareTo(Nodo o) {
        return (int) (this.f - o.f);
    }
}
