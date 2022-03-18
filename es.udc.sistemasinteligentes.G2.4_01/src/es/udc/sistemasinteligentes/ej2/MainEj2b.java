package es.udc.sistemasinteligentes.ej2;

import es.udc.sistemasinteligentes.EstrategiaBusquedaInformada;
import es.udc.sistemasinteligentes.Heuristica;
import es.udc.sistemasinteligentes.Nodo;
import es.udc.sistemasinteligentes.ProblemaBusqueda;

public class MainEj2b {

    public static void main(String[] args) throws Exception {

        int[][] aux = {{2, 0, 0}, {0, 0, 0}, {0, 0, 0}};

        ProblemaCuadradoMagico.EstadoCuadrado estadoInicial = new ProblemaCuadradoMagico.EstadoCuadrado(aux);
        EstrategiaBusquedaInformada buscador = new EstrategiaBusquedaA();
        ProblemaBusqueda cuadradoMagico = new ProblemaCuadradoMagico(estadoInicial);
        Heuristica heuristica = new HeuristicaImplementada();

        Nodo[] aux2 = buscador.soluciona(cuadradoMagico, heuristica);

        System.out.println("\nEstados recorridos desde el estado inicial hasta la meta:");

        for (int i = aux2.length - 1; i >= 0; i--){
            System.out.println(aux2[i].getEstado());
        }

        System.out.println("\nMETA:");
        System.out.println(aux2[0].getEstado());

    }
}
