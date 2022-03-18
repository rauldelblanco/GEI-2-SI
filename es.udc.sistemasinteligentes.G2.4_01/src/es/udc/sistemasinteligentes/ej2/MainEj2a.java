package es.udc.sistemasinteligentes.ej2;


import es.udc.sistemasinteligentes.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.ProblemaBusqueda;
import es.udc.sistemasinteligentes.Nodo;

public class MainEj2a {

    public static void main(String[] args) throws Exception{

        int[][] aux = {{4, 9, 2}, {3, 5, 0}, {0, 0, 0}};

        ProblemaCuadradoMagico.EstadoCuadrado estadoCuadrado = new ProblemaCuadradoMagico.EstadoCuadrado(aux);
        EstrategiaBusqueda buscador1 = new EstrategiaBusquedaAnchura();
        EstrategiaBusqueda buscador2 = new EstrategiaBusquedaProfundidad();
        ProblemaBusqueda cuadradoMagico = new ProblemaCuadradoMagico(estadoCuadrado);

        Nodo[] aux2 = buscador1.soluciona(cuadradoMagico);

        System.out.println("\nEstados recorridos desde el estado inicial hasta la meta:");

        for (int i = aux2.length - 1; i >= 0; i--){
            System.out.println(aux2[i].getEstado());
        }

        System.out.println("\nMETA:");
        System.out.println(aux2[0].getEstado());
        System.out.println(" ");

        Nodo[] aux3 = buscador2.soluciona(cuadradoMagico);

        System.out.println("\nEstados recorridos desde el estado inicial hasta la meta:");

        for (int i = aux3.length - 1; i >= 0; i--){
            System.out.println(aux3[i].getEstado());
        }

        System.out.println("\nMETA:");
        System.out.println(aux3[0].getEstado());

    }

}
