package es.udc.sistemasinteligentes.ej2;


import es.udc.sistemasinteligentes.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.ProblemaBusqueda;
import es.udc.sistemasinteligentes.ej1.Nodo;

public class MainEj2a {

    private static final int N = 3;

    public static void main(String[] args) throws Exception{

        int[][] aux = new int[N][N];
        aux[0][0] = 4;
        aux[0][1] = 9;
        aux[0][2] = 2;
        aux[1][0] = 3;
        aux[1][1] = 5;
        aux[2][1] = 1;

        ProblemaCuadradoMagico.EstadoCuadrado estadoCuadrado = new ProblemaCuadradoMagico.EstadoCuadrado(aux);
        EstrategiaBusqueda buscador1 = new EstrategiaBusquedaAnchura();
        EstrategiaBusqueda buscador2 = new EstrategiaBusquedaProfundidad();
        ProblemaBusqueda cuadradoMagico = new ProblemaCuadradoMagico(estadoCuadrado);

        Nodo[] aux2 = buscador2.soluciona(cuadradoMagico);

        System.out.println("\nEstados recorridos desde el estado inicial hasta la meta:");

        for (int i = aux2.length - 1; i >= 0; i--){
            System.out.println(aux2[i].getEstado());
        }

        System.out.println("\nMETA:");
        System.out.println(aux2[0].getEstado());

    }

}
