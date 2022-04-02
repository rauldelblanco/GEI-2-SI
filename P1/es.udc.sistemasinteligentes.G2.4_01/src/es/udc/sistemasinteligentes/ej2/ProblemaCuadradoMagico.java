package es.udc.sistemasinteligentes.ej2;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.Estado;
import es.udc.sistemasinteligentes.ProblemaBusqueda;

import java.util.ArrayList;
import java.util.Arrays;

public class ProblemaCuadradoMagico extends ProblemaBusqueda {

    public static class EstadoCuadrado extends Estado {

        //Definimos los atributos de los estados.

        private final int[][] cuadradoMagico;
        private final int n;

        public EstadoCuadrado(int[][] cuadradoMagico) {
            this.cuadradoMagico = cuadradoMagico;
            this.n              = cuadradoMagico.length;
        }

        public int[][] getCuadradoMagico() {
            return cuadradoMagico;
        }

        @Override
        public String toString() {
            return Arrays.deepToString(cuadradoMagico);
        }

        @Override
        public boolean equals(Object o) {//compara una matriz con otra
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EstadoCuadrado that = (EstadoCuadrado) o;

            //Recorremos el cuadradoMágico y comprobamos que cada casilla del array sea la misma que la que le pasamos a
            //la función.

            for (int i = 0; i < cuadradoMagico.length; i++) {
                for (int j = 0; j < cuadradoMagico[i].length; j++) {
                    if (cuadradoMagico[i][j] != that.cuadradoMagico[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result = Arrays.deepHashCode(cuadradoMagico);
            result = 31 * result + Arrays.deepHashCode(cuadradoMagico);
            return result;
        }
    }

    public static class AccionCuadrado extends Accion{

        //Definimos los atributos de las acciones.

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

            //Comprobamos que la lista no contenga el valor que queremos insertar.

            for(int f=0; f < n ; f++) {
                for (int c = 0; c < n; c++) {
                    if (((EstadoCuadrado) es).cuadradoMagico[f][c] == valor) {
                        return false;
                    }
                }
            }

            //Realizamos las últimas comprobaciones

            return valor <= n * n && i < n && j < n && ((EstadoCuadrado) es).cuadradoMagico[i][j] == 0;
        }

        @Override
        public Estado aplicaA(Estado es) { //Le pasamos una matriz[N][N]

            EstadoCuadrado esCua  = (EstadoCuadrado) es;
            int[][] estadoInicial = esCua.cuadradoMagico;
            int[][] estadoFinal   = new int[esCua.n][esCua.n];
            ArrayList<Integer> auxiliar = new ArrayList<>();

            //Introducimos los valores que contiene la tabla en un array auxiliar.

            for (int x = 0; x < esCua.n; x++){
                for (int y = 0; y < esCua.n; y++){
                    estadoFinal[x][y] = estadoInicial[x][y];
                    if (!auxiliar.contains(estadoInicial[x][y])){
                        auxiliar.add(estadoInicial[x][y]);
                    }
                }
            }
            estadoFinal[i][j] = valor;

            //Comprobamos que el valor que queremos para la acción no se encuentre en el array auxiliar.

            if (auxiliar.contains(valor)){
                return null;
            }else {
                return (new EstadoCuadrado(estadoFinal));
            }

        }
    }


    public ProblemaCuadradoMagico(Estado estadoInicial) {
        super(estadoInicial);
    }

    public Accion[] acciones(Estado es){

        EstadoCuadrado esCua = (EstadoCuadrado) es;
        ArrayList<AccionCuadrado> acciones = new ArrayList<>();
        int[][] cuadradoMagico = esCua.cuadradoMagico;
        int aux1 = -1, aux2 = -1;

        for(int i = 0; i < cuadradoMagico.length; i++){ //Buscamos la primera casilla vacía
            for(int j = 0; j < cuadradoMagico[i].length; j++){
                if(cuadradoMagico[i][j] == 0){
                    aux1 = i;
                    aux2 = j;
                    break;
                }
            }
            if (aux1 != -1){
                break;
            }
        }

        if(aux1 == -1){ //En caso de que no haya casillas vacías.
            return new Accion[0];
        }else{
            for(int i = 1; i <= (cuadradoMagico.length * cuadradoMagico.length); i++){ //Introducimos todas las acciones posibles para esa casilla.
                AccionCuadrado acc = new AccionCuadrado(i, aux1, aux2);
                if(acc.esAplicable(es)) {
                    acciones.add(acc);
                }
            }
        }

        Accion[] listaAcciones= new Accion[acciones.size()];

        return acciones.toArray(listaAcciones);
    }

    private int sumaTotal(int n) {
        return n*(n*n + 1)/2;
    }

    //Comprobamos que todas las filas sumen n*(n*n + 1)/2

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

    //Comprobamos que todas las columnas sumen n*(n*n + 1)/2

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

    //Comprobamos que las dos diagonales sumen n*(n*n + 1)/2

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
