package es.udc.sistemasinteligentes.ej2;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.Estado;
import es.udc.sistemasinteligentes.ProblemaBusqueda;

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

        @Override //Evalua si un estado es aplicable o no.
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

            EstadoCuadrado esCua  = (EstadoCuadrado) es;
            int[][] estadoInicial = esCua.cuadradoMagico;
            int[][] estadoFinal   = new int[esCua.n][esCua.n];
            ArrayList<Integer> auxiliar = new ArrayList<>();


            for (int x = 0; x < esCua.n; x++){
                for (int y = 0; y < esCua.n; y++){
                    estadoFinal[x][y] = estadoInicial[x][y];
                    if (!auxiliar.contains(estadoInicial[x][y])){
                        auxiliar.add(estadoInicial[x][y]);
                    }
                }
            }
            estadoFinal[i][j] = valor;

            if (auxiliar.contains(valor)){
                return null;
            }else {
                return (new EstadoCuadrado(estadoFinal));
            }

        }
    }


    private final Accion[] listaAcciones; //Acciones posibles a partir de una matriz inicial

    public ProblemaCuadradoMagico(ProblemaCuadradoMagico.EstadoCuadrado estadoInicial) {
        super(estadoInicial);
        int tam = estadoInicial.n;
        ArrayList<Integer> auxiliar = new ArrayList<>();
        int aux = 0;
        listaAcciones = new Accion[tam*tam*tam*tam];

        for(int fila=0; fila < tam ; fila++){
            for(int columna=0;columna < tam; columna++){
                if (estadoInicial.cuadradoMagico[fila][columna] != 0){
                    auxiliar.add(estadoInicial.cuadradoMagico[fila][columna]); //Estoy guardando los valores que se encuentran en el estado inicial
                }
            }
        }

        for(int fila=0; fila < tam ; fila++) {
            for (int columna = 0; columna < tam; columna++) {

                if (estadoInicial.cuadradoMagico[fila][columna] == 0){ //Comprobamos que la posición está vacía
                    for(int valor=1;valor <= tam*tam;valor++){
                        if (!auxiliar.contains(valor)){ //Comprobamos que el valor no se encuentra en la matriz
                            listaAcciones[aux] = new AccionCuadrado(valor, fila, columna);
                            aux++;
                        }
                    }
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
    } //Calcula el valor que tienen que sumar las filas, columnas y diagonales.

    //Comprueba si la fila da el valor de sumtotal
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

    //Comprueba si la columna da el valor de sumtotal
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

    //Comprueba si la diagonal da el valor de sumtotal
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

    @Override //Simplemente evalua la, fila, columna y diagonal, es meta si solo si todos son ciertos.
    public boolean esMeta(Estado es) {
        return Fila(es) && Columna(es) && Diagonal(es);
    }

}
