package es.udc.sistemasinteligentes;

public class Nodo implements Comparable<Nodo>{

    private Estado estado;
    private Nodo padre;
    private Accion accion;
    private float coste;
    private float f;


    public Nodo(Estado e, Nodo n, Accion a){
        this.estado = e;
        this.padre  = n;
        this.accion = a;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Nodo getPadre() {
        return padre;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
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
