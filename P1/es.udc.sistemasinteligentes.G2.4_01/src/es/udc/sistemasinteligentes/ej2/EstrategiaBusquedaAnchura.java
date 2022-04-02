package es.udc.sistemasinteligentes.ej2;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.Estado;
import es.udc.sistemasinteligentes.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.ProblemaBusqueda;
import es.udc.sistemasinteligentes.Nodo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class EstrategiaBusquedaAnchura implements EstrategiaBusqueda {

    //Atributos para llevar a cabo un seguimiento de los pasos, de los nodos creados y de los expandidos.

    private int i;
    private int nodosCreados;
    private int nodosExpandidos;

    public EstrategiaBusquedaAnchura(){
        i = 1;
        nodosCreados = 0;
        nodosExpandidos = 0;
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
            if(acc != null){
                Estado sc = p.result(nodo.getEstado(), acc);
                if(sc != null){
                    sucesores.add(new Nodo(sc, nodo, acc));
                    nodosCreados++;
                }
            }
        }

        return sucesores;
    }

    //Implementación de la función de búsqueda en anchura vista en teoría.

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception{
        Queue<Nodo> frontera       = new LinkedList<>();
        ArrayList<Nodo> explorados = new ArrayList<>();
        boolean aux1               = true;
        boolean aux2               = true;
        ArrayList<Nodo> H;
        frontera.add(new Nodo(p.getEstadoInicial(), null, null));
        nodosCreados++;

        Nodo nodoActual     = frontera.element();
        Estado estadoActual = nodoActual.getEstado();

        if (p.esMeta(estadoActual)){
            return (reconstruye_Sol(nodoActual));
        }

        System.out.println((i++) + " - Empezando búsqueda en " + estadoActual);
        System.out.println((i++) + " - " + estadoActual + " no es meta");

        while (!frontera.isEmpty()){

            nodoActual = frontera.remove();
            estadoActual = nodoActual.getEstado();

            explorados.add(nodoActual);
            H = sucesores(p, nodoActual);
            nodosExpandidos++;

            for (Nodo n : H){ //Recorremos la lista de sucesores del nodo actual.

                System.out.println((i++) + " - RESULT(" + estadoActual + "," + n.getAccion() + ")=" + n.getEstado());

                if (p.esMeta(n.getEstado())){
                    System.out.println((i++) + " - FIN - " + n.getEstado());
                    System.out.println(" ");
                    System.out.println("Numero de nodos expandidos: " + nodosExpandidos);
                    System.out.println("Numero de nodos creados: " + nodosCreados);
                    explorados.add(n);
                    return reconstruye_Sol(explorados.get(explorados.size()-1));
                }

                for (Nodo n1 : frontera){ //Buscamos si el nodo se encuentra en la frontera.
                    if (n.getEstado().equals(n1.getEstado())) {
                        aux1 = false;
                        break;
                    }
                }

                for (Nodo n1 : explorados){ //Buscamos si el nodo se encuentra en la lista de explorados.
                    if (n.getEstado().equals(n1.getEstado())){
                        aux2 = false;
                        break;
                    }
                }

                if (aux1 && aux2){ //Si no se encuentra el nodo en ninguna de las dos listas, añadimos el nodo a la frontera.
                    System.out.println((i++) + " - " + p.result(n.getPadre().getEstado(), n.getAccion()) + " NO explorado");
                    frontera.add(n);
                } else {
                    System.out.println((i++) + " - " + p.result(n.getPadre().getEstado(), n.getAccion()) + " ya explorado");
                }

                aux1 = true;
                aux2 = true;
            }
            if (!frontera.isEmpty()){ //Comprobamos si la frontera está vacía para informar del cambio de estado.
                System.out.println((i++) + " - Estado actual cambiado a " + frontera.peek().getEstado());
                if (!p.esMeta(frontera.peek().getEstado())){
                    System.out.println((i++) + " - " + frontera.peek().getEstado() + " no es meta");
                }
            }
        }

        throw new Exception("No se ha podido encontrar una solución");
    }

}
