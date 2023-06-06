package practica2C;

import java.util.Enumeration;
import java.util.Hashtable;

public class Juego implements IJuego {

    private int contadorEnemigosTotales;

    private Hashtable<Integer, Integer> contadoresEnemigosTipo;
    private Hashtable<Integer, Integer> contadoresEliminadosTipo;

    private int MAXENEMIGOS;
    private final int MINENEMIGOS = 0;

    public Juego(int tipos_enemigo, int MAX_enemigos) {
        this.contadorEnemigosTotales = 0;
        this.MAXENEMIGOS = MAX_enemigos;
        this.contadoresEliminadosTipo = new Hashtable<Integer, Integer>();
        this.contadoresEnemigosTipo = new Hashtable<Integer, Integer>();
    }


    @Override
    public synchronized void generarEnemigo(int enemigo) {

        if (this.contadoresEnemigosTipo.get(enemigo) == null) { // Comprobamos que se ha creado una entrada en el dict
            this.contadoresEnemigosTipo.put(enemigo, 0);
            this.contadoresEliminadosTipo.put(enemigo, 0);
        }

        this.comprobarAntesDeGenerar(enemigo); // Precondicion

        this.contadorEnemigosTotales += 1; // Aumentamos el contador 

        this.contadoresEnemigosTipo.put(enemigo, this.contadoresEnemigosTipo.get(enemigo) + 1); // Aumentamos el enemigo en el diccionario 

        checkInvariante(); // Comprobamos la invarianza

        imprimirInfo(enemigo, "Generado"); // Imprimimos la generación

        this.notifyAll();
    }

    @Override
    public synchronized void eliminarEnemigo(int enemigo) {

    	this.comprobarAntesDeEliminar(enemigo); // Precondicion 

        this.contadorEnemigosTotales -= 1; // Reducimos el contador 

        // Aumentamos el enemigo en el diccionario de eliminados
        this.contadoresEnemigosTipo.put(enemigo, this.contadoresEnemigosTipo.get(enemigo) - 1);
        // Reducimos el enemigo en el diccionario 
        this.contadoresEliminadosTipo.put(enemigo, this.contadoresEliminadosTipo.get(enemigo) + 1);

        checkInvariante(); // Comprobamos la invarianza

        imprimirInfo(enemigo, "Eliminado"); // Imprimimos la eliminación

        this.notifyAll();
       
    }


    public void imprimirInfo(int enemigo, String acceso) {
        System.out.println(acceso + " enemigo tipo " + enemigo);
        System.out.println("--> Enemigos totales: " + this.sumarContadores());

        for (int e : this.contadoresEnemigosTipo.keySet()) {
            System.out.println("----> Enemigos tipo " + e + ": " + this.contadoresEnemigosTipo.get(e) + "----[Eliminados:" + this.contadoresEliminadosTipo.get(e) + "]");
        }
        System.out.println("");
    }

    public int sumarContadores() {
        int contador = 0;
        Enumeration<Integer> iter_contadores = this.contadoresEnemigosTipo.elements();
        while (iter_contadores.hasMoreElements()) { // Iteramos todos los contadores de cada tipo de enemigo 
            contador += iter_contadores.nextElement();
        }
        return contador;
    }


    public void checkInvariante() {
        assert this.sumarContadores() == this.contadorEnemigosTotales; //El contador igual a la suma de estos.
    }


    public void comprobarAntesDeGenerar(int enemigo) {
        if (enemigo != 0) {
            while (this.contadoresEnemigosTipo.get(enemigo - 1) == null) { // Espera mientras no se halla generado un enemigo de menor tipo 
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            while (this.sumarContadores() == this.MAXENEMIGOS) { // Se comprueba que no se supere el maximo de enemigos
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            
        }
    }


    public void comprobarAntesDeEliminar(int enemigo) {
        // Comprueba si existe el enemigo a eliminar, si no existe espera
        while (this.contadoresEnemigosTipo.get(enemigo) == null) { 
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Espera si noy hay enemigos de ese tipo
        while ( this.contadoresEnemigosTipo.get(enemigo) == 0 || this.sumarContadores() == this.MINENEMIGOS) { 
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
