package es.udc.sistemasinteligentes.ej2;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.Estado;
import es.udc.sistemasinteligentes.ProblemaBusqueda;
import es.udc.sistemasinteligentes.ej1.ProblemaAspiradora;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ProblemaCuadradoMagico extends ProblemaBusqueda {


    public static class EstadoCuadrado extends Estado {

        private final int[][] cuadradoMagico;
        private final int n;

        public EstadoCuadrado(int[][] cuadradoMagico) {
            this.cuadradoMagico = cuadradoMagico;
            this.n              = cuadradoMagico.length;
        }

        @Override
        public String toString() {
            return Arrays.deepToString(cuadradoMagico);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EstadoCuadrado that = (EstadoCuadrado) o;

            return Arrays.deepEquals(cuadradoMagico, that.cuadradoMagico);
        }

        @Override
        public int hashCode() {
            int result = Arrays.deepHashCode(cuadradoMagico);
            result = 31 * result + Arrays.deepHashCode(cuadradoMagico);
            return result;
        }
    }

    public static class AccionCuadrado extends Accion{
        private final int valor;
        private final int i;
        private final int j;

        public AccionCuadrado(int valor, int i, int j) {
            this.valor = valor;
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return "" + valor;
        }

        @Override
        public boolean esAplicable(Estado es) {
            EstadoCuadrado estado = (EstadoCuadrado)es;
            int n = estado.n;
            for(int f=0; f < n ; f++) {
                for (int c = 0; c < n; c++) {
                    if (((EstadoCuadrado) es).cuadradoMagico[f][c] == valor) {
                        return false;
                    }
                }
            }
            return valor <= n * n && i < n && j < n && ((EstadoCuadrado) es).cuadradoMagico[i][j] == 0;
        }

        @Override
        public Estado aplicaA(Estado es) { //Le pasamos una matriz[N][N]

            EstadoCuadrado esCua = (EstadoCuadrado) es;
            int[][] matriz = esCua.cuadradoMagico;
            matriz[i][j] = valor;
            return new EstadoCuadrado(matriz);
        }
    }


    private final Accion[] listaAcciones;

    public ProblemaCuadradoMagico(ProblemaCuadradoMagico.EstadoCuadrado estadoInicial) {
        super(estadoInicial);
        int tam = estadoInicial.n;
        ArrayList<Integer> auxiliar = new ArrayList<>();
        int aux = 0;
        listaAcciones = new Accion[tam*tam*tam*tam];

        for(int fila=0; fila < tam ; fila++){
            for(int columna=0;columna < tam; columna++){

                if (estadoInicial.cuadradoMagico[fila][columna] == 0){ //Comprobamos que la posición está vacía

                    for(int valor=1;valor <= tam*tam;valor++){
                        if (!auxiliar.contains(estadoInicial.cuadradoMagico[fila][columna])){ //Comprobamos que el valor no se encuentra en la matriz
                            listaAcciones[aux] = new AccionCuadrado(valor, fila, columna);
                            aux++;
                        }
                    }
                } else {
                    auxiliar.add(estadoInicial.cuadradoMagico[fila][columna]); //Estoy guardando los valores que se encuentran en el estado inicial
                }
            }
        }
    }

    public Accion[] acciones(Estado es){
        //No es necesario generar las acciones dinámicamente a partir del estado porque todas las acciones se pueden
        //aplicar a todos los estados
        return listaAcciones;
    }

    private int sumaTotal(int n) {
        return n*(n*n + 1)/2;
    }

    private boolean Fila(Estado es){
        EstadoCuadrado estado = (EstadoCuadrado)es;
        int[][] cuadrado = estado.cuadradoMagico;
        int n = estado.n;
        int aux1 = sumaTotal(n);
        int sumaFila = 0;
        int f = 0;
        boolean aux2 = true;

        while (f < n && aux2) {

            for (int c = 0; c < n; c++) {
                sumaFila += cuadrado[f][c];
            }

            if (sumaFila != aux1) {
                aux2 = false;
            }
            f++;
            sumaFila = 0;
        }
        return aux2;
    }

    private boolean Columna(Estado es){
        EstadoCuadrado estado = (EstadoCuadrado)es;
        int[][] cuadrado = estado.cuadradoMagico;
        int n = estado.n;
        int sumaColumna = 0;
        int aux1 = sumaTotal(n);
        int c = 0;
        boolean aux2 = true;

        while (c < n && aux2) {

            for (int f = 0; f < n; f++) {
                sumaColumna += cuadrado[f][c];
            }

            if (sumaColumna != aux1) {
                aux2 = false;
            }

            c++;
            sumaColumna = 0;
        }
        return aux2;
    }

    private boolean Diagonal(Estado es){
        EstadoCuadrado estado = (EstadoCuadrado)es;
        int[][] cuadrado = estado.cuadradoMagico;
        int n = estado.n;
        int aux1 = sumaTotal(n);
        int sumaDiag1 = 0;
        int sumaDiag2 = 0;
        int f = 0, c = 0;
        boolean aux2 = true;

        while (f < n && c < n) {
            sumaDiag1 += cuadrado[f][c];
            f++;
            c++;
        }
        if(sumaDiag1 != aux1) {
            aux2 = false;
        } else{
            f = 0;
            c = n - 1;
            while (f < n && c >= 0) {
                sumaDiag2 += cuadrado[f][c];
                f++;
                c--;
            }
            if (sumaDiag2 != aux1) {
                aux2 = false;
            }
        }
        return aux2;
    }

    @Override
    public boolean esMeta(Estado es) {
        return Fila(es) && Columna(es) && Diagonal(es);
    }

}
