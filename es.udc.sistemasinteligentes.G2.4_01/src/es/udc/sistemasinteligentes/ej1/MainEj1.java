package es.udc.sistemasinteligentes.ej1;

import es.udc.sistemasinteligentes.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.Nodo;
import es.udc.sistemasinteligentes.ProblemaBusqueda;

public class MainEj1 {

    public static void main(String[] args) throws Exception {

        ProblemaAspiradora.EstadoAspiradora estadoInicial = new ProblemaAspiradora.EstadoAspiradora(ProblemaAspiradora.EstadoAspiradora.PosicionRobot.IZQ,
                ProblemaAspiradora.EstadoAspiradora.PosicionBasura.AMBAS);
        ProblemaBusqueda aspiradora = new ProblemaAspiradora(estadoInicial);

        EstrategiaBusqueda buscador  = new Estrategia4();
        EstrategiaBusqueda buscador2 = new EstrategiaBusquedaGrafo();

        Nodo[] aux = buscador.soluciona(aspiradora);

        //Imprimimos los estados que se han recorrido, empezando desde el final del array.

        System.out.println("\nEstados recorridos desde el estado inicial hasta la meta:");

        for (int i = aux.length - 1; i >= 0; i--){
            System.out.println(aux[i].getEstado());
        }

        System.out.println("\nMETA:");
        System.out.println(aux[0].getEstado());
        System.out.println("");

        Nodo[] aux2 = buscador2.soluciona(aspiradora);

        System.out.println("\nEstados recorridos desde el estado inicial hasta la meta:");

        for (int i = aux2.length - 1; i >= 0; i--){
            System.out.println(aux2[i].getEstado());
        }

        System.out.println("\nMETA:");
        System.out.println(aux2[0].getEstado());
    }
}
