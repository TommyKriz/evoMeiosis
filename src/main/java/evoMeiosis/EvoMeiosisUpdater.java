package evoMeiosis;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import deepSpace.tuio.DeepSpaceTUIOHelper;

public class EvoMeiosisUpdater {

	private final DeepSpaceTUIOHelper tuioClient;

	private ArrayList<PVector> playersPositions;

	public EvoMeiosisUpdater(
			deepSpace.tuio.DeepSpaceTUIOHelper deepSpaceTUIOHelper) {
		this.tuioClient = deepSpaceTUIOHelper;
	}

	public void update() {
		playersPositions = tuioClient.getPlayerPositions();
	}

	public void paintPlayers(PGraphics canvas) {
		canvas.beginDraw();
		canvas.colorMode(PApplet.HSB, 255);
		canvas.fill(250, 100, 100, 80);
		canvas.stroke(255, 0, 255, 150);
		canvas.ellipse(200, 400, 100, 100);
		for (PVector p : playersPositions) {
			System.out.println("     x: " + p.x + " | y: " + p.y);
			canvas.ellipse(p.x, p.y, 100, 100);
		}
		canvas.endDraw();
	}

	public void paintTrails(PGraphics canvas) {
		canvas.beginDraw();
		canvas.fill(250, 0, 0, 80);
		canvas.rect(0, 0, canvas.width, canvas.height);
		canvas.endDraw();
	}

}
