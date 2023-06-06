package practica2C;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActividadAliada implements Runnable{

	private int aliado;
	
	private IJuego juego;
	
	public ActividadAliada(int aliado, IJuego juego) {
		this.aliado = aliado;
		this.juego = juego;
	}
	
	
	@Override
	public void run() {
		//Inicia el hilo
		try {
			// Se genera un aliado si se dan las precondiciones y se dan despues las invariantes
			this.juego.eliminarEnemigo(aliado);
			TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1000);
		}catch(InterruptedException e) {
			Logger.getGlobal().log(Level.INFO, "Actividad aliada interrumpida");
			Logger.getGlobal().log(Level.INFO, e.toString());
			return;
		}
		
	}

}
