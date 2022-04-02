package es.udc.sistemasinteligentes.ej2;

import es.udc.sistemasinteligentes.Estado;
import es.udc.sistemasinteligentes.Heuristica;

public class HeuristicaImplementada extends Heuristica {

    private int sumaTotal(int n) {
        return n*(n*n + 1)/2;
    }

    @Override
    public float evalua(Estado e) {

        int [][] cuadradoMagico = ((ProblemaCuadradoMagico.EstadoCuadrado) e).getCuadradoMagico();
        int diag1 = 0,diag2 = 0, fil = 0, col = 0, cont = 0;;
        boolean casillas1 = false, casillas2 = false;
        int aux = cuadradoMagico.length;

        //Comprobamos que ninguna diagonal se pase de N*(N^2+1)/2 o se quede corta

        for(int i = 0; i < cuadradoMagico.length; i++){
            diag1 += cuadradoMagico[i][i];
            if(cuadradoMagico[i][i]==0){ //Comprobamos si existen celdas vacías en la diagonal descendente
                casillas1=true;
            }
        }

        for (int i = 0; i < cuadradoMagico.length; i++){

            if (aux > 0){
                aux--;
            }

            diag2 += cuadradoMagico[aux][i];

            if (cuadradoMagico[aux][i] == 0){ //Comprobamos si existen celdas vacías en la diagonal ascendente
                casillas2 = true;
            }
        }

        //En caso de que la suma de alguna de las diagonales no sea la correcta y que las casillas de las diagonales sean
        //distintas de 0, devolveremos un coste muy elevado.

        if((diag1 != sumaTotal(cuadradoMagico.length) && !casillas1) || (diag2 != sumaTotal(cuadradoMagico.length) && !casillas2)){
            return 999;
        }

        casillas1 = false;
        casillas2 = false;

        //Comprobamos que ninguna fila o columna pase de N*(N^2+1)/2 o se quede corta

        for(int i = 0; i < cuadradoMagico.length; i++){

            fil = 0;
            col = 0;

            for(int j = 0; j < cuadradoMagico[i].length; j++){

                fil += cuadradoMagico[i][j];

                if(cuadradoMagico[i][j] == 0){
                    cont++; //Llevamos un recuento de las casillas vacías.
                    casillas1 = true;
                }

                col += cuadradoMagico[j][i];
                if(cuadradoMagico[j][i] == 0){
                    casillas2 = true;
                }
            }

            //En caso de que la suma de alguna de las filas o columnas no sea la correcta y que las casillas de estas sean
            //distintas de 0, devolveremos un coste muy elevado.

            if((fil != sumaTotal(cuadradoMagico.length) && !casillas1) || (col != sumaTotal(cuadradoMagico.length) && !casillas2)){
                return 999;
            }
        }

        return cont;
    }
}
