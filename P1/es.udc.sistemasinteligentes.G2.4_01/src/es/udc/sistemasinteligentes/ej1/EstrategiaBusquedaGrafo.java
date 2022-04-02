package es.udc.sistemasinteligentes.ej1;

import es.udc.sistemasinteligentes.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class EstrategiaBusquedaGrafo implements EstrategiaBusqueda {

    private int i;

    public EstrategiaBusquedaGrafo() {
        i = 1;
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
            if(acc != null){ //Comprobamos que la acción de la lista no sea null
                Estado sc = p.result(nodo.getEstado(), acc);
                if(sc != null){ //Comprobamos que el estado no es null
                    sucesores.add(new Nodo(sc, nodo, acc)); //Introducimos en la lista de sucesores el nodo necesario.
                }
            }
        }

        return sucesores;
    }

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception{

        Queue<Nodo> frontera       = new LinkedList<>();
        ArrayList<Nodo> explorados = new ArrayList<>();
        boolean aux1               = true;
        boolean aux2               = true;
        ArrayList<Nodo> H;
        frontera.add(new Nodo(p.getEstadoInicial(), null, null));

        Nodo nodoActual     = frontera.element();
        Estado estadoActual = nodoActual.getEstado();

        System.out.println((i++) + " - Empezando búsqueda en " + estadoActual);

        while (!frontera.isEmpty()){
            nodoActual   = frontera.remove();
            estadoActual = nodoActual.getEstado();


            if (!p.esMeta(estadoActual)){
                System.out.println((i++) + " - " + estadoActual + " no es meta");
                explorados.add(nodoActual);
                H = sucesores(p, nodoActual);

                for (Nodo n : H){

                    System.out.println((i++) + " - RESULT(" + estadoActual + "," + n.getAccion() + ")=" + n.getEstado());

                    for (Nodo n1 : frontera){ //Comprobamos que algún nodo de sucesores no se encuentre en la frontera.
                        if (n.getEstado().equals(n1.getEstado())) {
                            aux1 = false;
                            break;
                        }
                    }

                    for (Nodo n1 : explorados){ //Comprobamos que algún nodo de sucesores no se encuentre en la lista de explorados.
                        if (n.getEstado().equals(n1.getEstado())){
                            aux2 = false;
                            break;
                        }
                    }

                    if (aux1 && aux2){ //En caso de que no se encuentre en ninguna de las dos listas, lo añadimos a la frontera.
                        System.out.println((i++) + " - " + p.result(n.getPadre().getEstado(), n.getAccion()) + " NO explorado");
                        frontera.add(n);
                    } else {
                        System.out.println((i++) + " - " + p.result(n.getPadre().getEstado(), n.getAccion()) + " ya explorado");
                    }
                    aux1 = true;
                    aux2 = true;
                }
                if (!frontera.isEmpty()){ //Comprobamos que la frontera no esté vacía para informar del cambio de estado.
                    System.out.println((i++) + " - Estado actual cambiado a " + frontera.peek().getEstado());
                }
            } else {
                explorados.add(nodoActual);
                System.out.println((i++) + " - FIN - " + estadoActual);
                return reconstruye_Sol(explorados.get(explorados.size()-1));
            }

        }
        throw new Exception("No se ha podido encontrar una solución");
    }

}
