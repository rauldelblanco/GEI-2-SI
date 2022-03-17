package es.udc.sistemasinteligentes.ej1;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.Estado;

public class Nodo {
    //Atributos
    private Estado estado;
    private Nodo padre;
    private Accion accion;

    //Constructor
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


}
