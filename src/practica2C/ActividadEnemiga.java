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
		try {
			this.juego.generarEnemigo(this.Enemigo);
			TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1000);
		}catch(InterruptedException e) {
			Logger.getGlobal().log(Level.INFO, "Actividad enemiga interrumpida");
			Logger.getGlobal().log(Level.INFO, e.toString());
			return;
		}

	}

}
