package es.udc.sistemasinteligentes.ej1;

import es.udc.sistemasinteligentes.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.ProblemaBusqueda;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {

        ProblemaAspiradora.EstadoAspiradora estadoInicial = new ProblemaAspiradora.EstadoAspiradora(ProblemaAspiradora.EstadoAspiradora.PosicionRobot.IZQ,
                ProblemaAspiradora.EstadoAspiradora.PosicionBasura.AMBAS);
        ProblemaBusqueda aspiradora = new ProblemaAspiradora(estadoInicial);

        EstrategiaBusqueda buscador = new Estrategia4();

        ArrayList<Nodo> aux = buscador.soluciona(aspiradora);

        System.out.println("\nEstados recorridos desde el estado inicial hasta la meta:");

        for (int i = aux.size() - 1; i >= 0; i--){
            System.out.println(aux.get(i).getEstado());
        }

        System.out.println("\nMETA:");
        System.out.println(aux.get(0).getEstado());
    }
}
