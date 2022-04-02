package es.udc.sistemasinteligentes.ej2;

import es.udc.sistemasinteligentes.*;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class EstrategiaBusquedaA implements EstrategiaBusquedaInformada {

    //Atributos para llevar a cabo un seguimiento de los pasos, de los nodos creados y de los expandidos.

    private int i;
    private int nodosCreados;
    private int nodosExpandidos;

    public EstrategiaBusquedaA(){
        this.i = 1;
        this.nodosCreados = 0;
        this.nodosExpandidos = 0;
    }

    private Nodo[] reconstruye_Sol(Nodo n){
        ArrayList<Nodo> solucion = new ArrayList<>();
        int tam = 0;
        Nodo a  = n;

        while (a != null){
            solucion.add(a);
            a = a.getPadre();
            tam++;
        }

        return solucion.toArray(new Nodo[tam]);
    }

    private ArrayList<Nodo> sucesores(ProblemaBusqueda p, Nodo nodo) {
        ArrayList<Nodo> sucesores = new ArrayList<>();
        Accion[] accionesDisponibles = p.acciones(nodo.getEstado());

        for (Accion acc : accionesDisponibles) {
            if (acc != null){
                Estado sc = p.result(nodo.getEstado(), acc);
                if (sc != null){
                    sucesores.add(new Nodo(sc, nodo, acc));
                    nodosCreados++;
                }
            }
        }

        return sucesores;
    }

    //Implementación de la función de búsqueda A* vista en teoría.

    public Nodo[] soluciona(ProblemaBusqueda p, Heuristica h) throws Exception{

        PriorityQueue<Nodo> frontera = new PriorityQueue<>();
        ArrayList<Estado> explorados = new ArrayList<>();
        ArrayList<Nodo> H;
        frontera.add(new Nodo(p.getEstadoInicial(), null, null));
        nodosCreados++;
        Nodo nodoActual     = frontera.element();
        Estado estadoActual = nodoActual.getEstado();

        System.out.println((i++) + " - Empezando búsqueda en " + estadoActual);

        while (!frontera.isEmpty()){

            nodoActual = frontera.remove();
            estadoActual = nodoActual.getEstado();

            if (!p.esMeta(estadoActual)){

                System.out.println((i++) + " - " + estadoActual + " no es meta");
                explorados.add(estadoActual);
                H = sucesores(p, nodoActual);
                nodosExpandidos++;

                for (Nodo n : H){ //Recorremos la lista de sucesores del nodo actual.

                    System.out.println((i++) + " - RESULT(" + estadoActual + "," + n.getAccion() + ")=" + n.getEstado());
                    n.setCoste(nodoActual.getCoste() + 1);
                    n.setF(n.getCoste() + h.evalua(n.getEstado()));

                    if (!explorados.contains(n.getEstado())){
                        System.out.println((i++) + " - " + p.result(n.getPadre().getEstado(), n.getAccion()) + " NO explorado");
                        if (!frontera.contains(n)){
                            frontera.add(n);
                        } else {

                            for (Nodo nf : frontera){

                                if (n.getEstado().equals(nf.getEstado()) && (n.getF() < nf.getF())){
                                    frontera.remove(nf);
                                    frontera.add(n);
                                }
                            }
                        }

                    } else {
                        System.out.println((i++) + " - " + p.result(n.getPadre().getEstado(), n.getAccion()) + " ya explorado");
                    }

                }

            } else {
                System.out.println((i++) + " - FIN - " + estadoActual);
                System.out.println(" ");
                System.out.println("Numero de nodos expandidos: " + nodosExpandidos);
                System.out.println("Numero de nodos creados: " + nodosCreados);
                return reconstruye_Sol(nodoActual);
            }

            if (!frontera.isEmpty()){ //Comprobamos que la frontera no esté vacía para informar del cambio de estado.
                System.out.println((i++) + " - Estado actual cambiado a " + frontera.peek().getEstado());
            }

        }

        throw new Exception("No se ha podido encontrar una solución");
    }
}
