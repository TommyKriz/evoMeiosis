package evoMeiosis.player;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import deepSpace.tuio.DeepSpaceTUIOHelper;
import evoMeiosis.EvoMeiosisConstants;

public class PlayerSystem {

	private DeepSpaceTUIOHelper tuioClient;

	public PlayerSystem(DeepSpaceTUIOHelper deepSpaceTUIOHelper) {
		tuioClient = deepSpaceTUIOHelper;
	}

	public ArrayList<PVector> getPlayerPositions() {
		return tuioClient.getPlayerPositions();
	}

	public void paintPlayers(PGraphics canvas) {
		canvas.beginDraw();
		canvas.colorMode(PApplet.HSB, 255);
		canvas.fill(150, 100, 100, 80);
		canvas.stroke(255, 0, 255, 150);

		canvas.ellipse(200, 400, 100, 100);

		for (PVector p : getPlayerPositions()) {
			canvas.ellipse(p.x, p.y, EvoMeiosisConstants.PLAYER_CATCH_RADIUS,
					EvoMeiosisConstants.PLAYER_CATCH_RADIUS);
		}

		canvas.endDraw();
	}
}
