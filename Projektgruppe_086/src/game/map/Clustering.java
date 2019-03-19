package game.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import game.Game;
import gui.views.GameMenu;

import java.awt.*;

/**
 * Diese Klasse teilt Burgen in Königreiche auf
 */
public class Clustering {

	private Random random;
	private final List<Castle> allCastles;
	private final int kingdomCount;

	/**
	 * Ein neues Clustering-Objekt erzeugen.
	 * 
	 * @param castles      Die Liste von Burgen, die aufgeteilt werden sollen
	 * @param kingdomCount Die Anzahl von Königreichen die generiert werden sollen
	 */
	public Clustering(List<Castle> castles, int kingdomCount) {
		if (kingdomCount < 2)
			throw new IllegalArgumentException("Ungültige Anzahl an Königreichen");

		this.random = new Random();
		this.kingdomCount = kingdomCount;
		this.allCastles = Collections.unmodifiableList(castles);
	}

	/**
	 * Gibt eine Liste von Königreichen zurück. Jedes Königreich sollte dabei einen
	 * Index im Bereich 0-5 bekommen, damit die Burg richtig angezeigt werden kann.
	 * Siehe auch {@link Kingdom#getType()}
	 */
	public List<Kingdom> getPointsClusters() {
		int h, w;
		List<Kingdom> kList = new ArrayList<Kingdom>();
		h = GameMap.getMapHeight();
		w = GameMap.getMapWidth();
		int closestCastle = 0;
		double smallestDist = (int) Math.floor(Math.sqrt((w * w) + (h * h)));
		Point[] centers = new Point[kingdomCount];
		
		// generating first random centers
		for (int i = 0; i < kingdomCount; i++) {
			centers[i].x = (int) Math.floor(Math.random() * (w + 1));
			centers[i].y = (int) Math.floor(Math.random() * (h + 1));
		}
		
		//generating first empty kingdoms
		for (int i = 0; i < kingdomCount; i++) {
			kList.add(new Kingdom(i));
		}

		while (!allCastles.isEmpty()) {
			
			// adding closest castles to kingdoms 
			for (int i = 0; i < kingdomCount; i++) {
				for (int j = 0; j < allCastles.size(); i++) {
					if (allCastles.isEmpty()) {
						return kList;
					}
					if (allCastles.get(j).distance(centers[i]) < smallestDist) {
						smallestDist = allCastles.get(j).distance(centers[i]);
						closestCastle = j;
					}
				}
				kList.get(i).addCastle(allCastles.get(closestCastle));
				allCastles.remove(closestCastle);
			}
			
			//generating new centers 
			for (int i = 0; i < kingdomCount; i++) {
				double newX = 0;
				double newY = 0;
				for (int j = 0; j < kList.get(i).getCastles().size(); j++) {
					newX =+ kList.get(i).getCastles().get(j).getLocationOnMap().getX();
					newY =+ kList.get(i).getCastles().get(j).getLocationOnMap().getY();
				}
				centers[i].setLocation((int) Math.floor (newX / kList.get(i).getCastles().size()), (int) Math.floor (newY / kList.get(i).getCastles().size()));
			}
			
			
		}

		return kList;
	}
}
