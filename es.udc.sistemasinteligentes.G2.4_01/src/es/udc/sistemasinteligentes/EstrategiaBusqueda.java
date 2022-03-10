package es.udc.sistemasinteligentes;

import es.udc.sistemasinteligentes.ej1.Nodo;

import java.util.ArrayList;

public interface EstrategiaBusqueda {
    /**
     * Soluciona el problema de búsqueda, obteniendo un estado meta o arrojando una Excepcion si no encuentra una
     * @param p Problema a solucionar
     * @return Estado meta obtenido
     */
    public abstract ArrayList<Nodo> soluciona(ProblemaBusqueda p) throws Exception;
}
