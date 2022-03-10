package es.udc.sistemasinteligentes.ej1;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.Estado;
import es.udc.sistemasinteligentes.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.ProblemaBusqueda;

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
            Estado sc = p.result(nodo.getEstado(), acc);
            sucesores.add(new Nodo(sc, nodo, acc));
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

                    for (Nodo n1 : frontera){
                        if (n.getEstado().equals(n1.getEstado())) {
                            aux1 = false;
                            break;
                        }
                    }

                    for (Nodo n1 : explorados){
                        if (n.getEstado().equals(n1.getEstado())){
                            aux2 = false;
                            break;
                        }
                    }

                    if (aux1 && aux2){
                        System.out.println((i++) + " - " + p.result(n.getEstado(), n.getAccion()) + " NO explorado");
                        frontera.add(n);
                    } else {
                        System.out.println((i++) + " - " + p.result(n.getEstado(), n.getAccion()) + " ya explorado");
                    }
                    aux1 = true;
                    aux2 = true;
                }
                if (!frontera.isEmpty()){
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
