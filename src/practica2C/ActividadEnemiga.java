package practica2C;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActividadEnemiga implements Runnable {

	private int Enemigo;
	
	private IJuego juego;
	
	public ActividadEnemiga(int enemigo , IJuego juego) {
		this.Enemigo = enemigo;
		this.juego = juego;
	}
	@Override
	public void run() {
		// El hilo se lanza 
		try {
			// Se genera un enemigo si se dan las precondiciones y se dan despues las invariantes 
			this.juego.generarEnemigo(this.Enemigo); 
			//Una vez el hijo termina su ejecucion se duerme durante 1 a 5 segundos 
			TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1000);
		}catch(InterruptedException e) {
			// En caso de que el hilo quede interrumpido se tratara la excepcion 
			Logger.getGlobal().log(Level.INFO, "Actividad enemiga interrumpida");
			Logger.getGlobal().log(Level.INFO, e.toString());
			return;
		}

	}

}
