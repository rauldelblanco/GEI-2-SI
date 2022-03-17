/*package es.udc.sistemasinteligentes.ej2b;

import es.udc.sistemasinteligentes.EstrategiaBusquedaInformada;
import es.udc.sistemasinteligentes.Heuristica;
import es.udc.sistemasinteligentes.ProblemaBusqueda;
import es.udc.sistemasinteligentes.apartado2a.*;

public class MainEj2b {
    public static void main(String[] args) throws Exception {
        int[][] est = {{2, 0, 0}, {0, 0, 0},{0, 0, 0}};
        ProblemaCuadradoMagico.EstadoCuadrado estadoInicial = new ProblemaCuadradoMagico.EstadoCuadrado(est);//definimos un estado inicial
        ProblemaBusqueda cuadradoMagico = new ProblemaCuadradoMagico(estadoInicial);//creamos un nuevo problema busqueda con nuestro estado inicial

        EstrategiaBusquedaInformada buscador = new BusquedaA();//creamos el algoritmo de busqueda
        Heuristica h=new HeuristicaCuadradoMagico();//creamos la heuristica
        Nodo[] nodos = buscador.soluciona(cuadradoMagico,h);
        for (Nodo n : nodos) {
            System.out.println(n.toString());
        }
    }
}*/
