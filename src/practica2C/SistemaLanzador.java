package practica2C;

public class SistemaLanzador {
	
	public static void main(String[] args) { 
		
		int MAX_enemigos = 10;
		int tipo_enemigos = 4;
		IJuego juego = new Juego(tipo_enemigos, MAX_enemigos);
		
		for(int e = 0; e < tipo_enemigos; e++) { // En cada iteracion, e es un tipo de enemigo 
			for (int j = 0; j < tipo_enemigos - e; j++) { // De cada tipo se activa varias veces
				ActividadEnemiga enemigos = new ActividadEnemiga(e, juego);
				new Thread(enemigos).start();
				
				ActividadAliada aliados = new ActividadAliada(e, juego);
				new Thread(aliados).start();
			}
		
		}
	}
	
}
