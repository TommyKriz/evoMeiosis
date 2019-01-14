package evoMeiosis.player;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import deepSpace.tuio.DeepSpaceTUIOHelper;
import evoMeiosis.EvoMeiosisConstants;

public class PlayerSystem {

	private ArrayList<Player> activePlayers;

	private DeepSpaceTUIOHelper tuioClient;

	public PlayerSystem(DeepSpaceTUIOHelper deepSpaceTUIOHelper) {
		tuioClient = deepSpaceTUIOHelper;
		activePlayers = new ArrayList<>();
	}

	// public ArrayList<PVector> getPlayerPositions() {
	// return tuioClient.getPlayerPositions();
	// }

	public ArrayList<Player> getActivePlayers() {
		return activePlayers;
	}

	private long[] getActiveIds() {
		long[] activeIds = new long[activePlayers.size()];
		for (int i = 0; i < activePlayers.size(); i++) {
			activeIds[i] = activePlayers.get(i).id;
		}
		return activeIds;
	}

	public void update() {

		ArrayList<PVector> tuioPlayers = tuioClient.getPlayerPositions();
		long[] tuioIds = tuioClient.getPlayerIDs();

		long[] activeIds = getActiveIds();

		for (int i = 0; i < tuioIds.length; i++) {
			boolean newPlayerEnteredTheField = true;
			for (long l : activeIds) {
				if (tuioIds[i] == l) {
					newPlayerEnteredTheField = false;
					break;
				}
			}
			if (newPlayerEnteredTheField) {
				PVector p = tuioPlayers.get(i);
				activePlayers.add(new Player(p.x, p.y, tuioIds[i]));
			}
		}

		activeIds = getActiveIds();
		
		ArrayList<Integer> removePlayers = new ArrayList<>();

		if (activePlayers.size() > tuioPlayers.size()) {
			for (int i = 0; i < activeIds.length; i++) {
				boolean playerEscapedField = true;
				for (long l : tuioIds) {
					if (activeIds[i] == l) {
						playerEscapedField = false;
						break;
					}
				}
				if (playerEscapedField) {
					removePlayers.add(i);
				}
			}
		}

		for (int i : removePlayers) {
			activePlayers.get(i).release();
			activePlayers.remove(i);
		}

		//
		for (Player activePlayer : activePlayers) {
			for (int i = 0; i < tuioIds.length; i++) {
				if (activePlayer.id == tuioIds[i]) {
					activePlayer.update(tuioPlayers.get(i).x,
							tuioPlayers.get(i).y);
					break;
				}
			}
		}

		// for(int i = 0; i < activePlayers.size(); i++){
		// activePlayers.get(i).update(tuioPlayers.get(i).x,
		// tuioPlayers.get(i).y);
		// }
		//
		// for(int i = 0; i < ids.length; i++){
		// if (p.id == ids[i]){
		// break;
		// }else if()
		// }
		//
		// // p.update();
		// }
	}

	public void paintPlayers(PGraphics canvas) {
		canvas.beginDraw();
		canvas.clear();
		canvas.background(0, 0, 0, 0);
		canvas.colorMode(PApplet.HSB, 255);
		canvas.fill(150, 100, 100, 80);
		canvas.stroke(255, 0, 255, 150);

		// canvas.ellipse(200, 400, 100, 100);

		for (Player p : activePlayers) {
			canvas.ellipse(p.xOrig, p.yOrig,
					p.radius,
					p.radius);
		}

		canvas.endDraw();
	}
}
