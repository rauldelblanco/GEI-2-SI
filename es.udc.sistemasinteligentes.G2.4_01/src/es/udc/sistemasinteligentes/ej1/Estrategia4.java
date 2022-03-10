package es.udc.sistemasinteligentes.ej1;

import es.udc.sistemasinteligentes.*;

import java.util.ArrayList;

public class Estrategia4 implements EstrategiaBusqueda {

    public Estrategia4() {
    }

    @Override
    public ArrayList<Nodo> soluciona(ProblemaBusqueda p) throws Exception{
        ArrayList<Nodo> explorados = new ArrayList<>();
        Nodo nodoInicial = new Nodo(p.getEstadoInicial(), null, null);
        explorados.add(nodoInicial);

        int i = 1;
        boolean aux = true;
        Nodo nodoActual = explorados.get(0);
        Estado estadoActual = nodoActual.getEstado();

        System.out.println((i++) + " - Empezando búsqueda en " + estadoActual);

        while (!p.esMeta(estadoActual)){
            nodoActual = explorados.get(explorados.size()-1);
            estadoActual = nodoActual.getEstado();
            System.out.println((i++) + " - " + estadoActual + " no es meta");
            Accion[] accionesDisponibles = p.acciones(estadoActual);
            boolean modificado = false;
            for (Accion acc: accionesDisponibles) {
                Estado sc = p.result(estadoActual, acc);
                System.out.println((i++) + " - RESULT(" + estadoActual + ","+ acc + ")=" + sc);

                for (Nodo n : explorados){
                    if (n.getEstado().equals(sc)) {
                        aux = false;
                        break;
                    }
                }

                if (aux) {
                    estadoActual = sc;
                    System.out.println((i++) + " - " + sc + " NO explorado");
                    explorados.add(new Nodo(estadoActual, nodoActual, acc));
                    modificado = true;
                    System.out.println((i++) + " - Estado actual cambiado a " + estadoActual);
                    break;
                }
                else {
                    System.out.println((i++) + " - " + sc + " ya explorado");
                }
                aux = true;
            }
            if (!modificado) throw new Exception("No se ha podido encontrar una solución");
        }
        System.out.println((i++) + " - FIN - " + estadoActual);
        return reconstruye_Sol(explorados.get(explorados.size()-1));
    }

    private ArrayList<Nodo> reconstruye_Sol(Nodo n){
        ArrayList<Nodo> solucion = new ArrayList<>();
        Nodo a = n;

        while (a != null){
            solucion.add(a);
            a = a.getPadre();
        }

        return solucion;
    }
}
