package evoMeiosis;

import processing.core.PApplet;
import processing.core.PGraphics;
import deepSpace.DeepSpaceConstants;
import deepSpace.tuio.DeepSpaceTUIOHelper;
import evoMeiosis.audio.Agent;
import evoMeiosis.audio.AudioTest;

public class EvoMeiosis {

	private PApplet parent;

	private PGraphics pgTrails, pgPlayerAndParticles, pgTrees, pgWall;

	private EvoMeiosisEngine evoMeiosisEngine;

	private Agent agent;

	public EvoMeiosis(PApplet parent) {
		this.parent = parent;
		initCanvases();
		// TODO: remove, this is for demo purposes
		new AudioTest(parent).playSound();
		agent = new Agent();
		evoMeiosisEngine = new EvoMeiosisEngine(new DeepSpaceTUIOHelper(parent));

	}

	private void initCanvases() {
		pgTrails = parent.createGraphics(DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.FLOOR_HEIGHT);
		pgPlayerAndParticles = parent.createGraphics(
				DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.FLOOR_HEIGHT);
		pgTrees = parent.createGraphics(DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.FLOOR_HEIGHT);
		pgWall = parent.createGraphics(DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.WALL_HEIGHT);
	}

	public void update() {

		// logic

		evoMeiosisEngine.update();

		// draw

		// parent.millis();

		evoMeiosisEngine.paintPlayers(pgPlayerAndParticles);
		evoMeiosisEngine.paintTrails(pgTrails);

		/*
		 * 
		 * 
		 * collectFreeSeeds();
		 * 
		 * // ------------------------------------------- renderFreeSeeds();
		 * 
		 * // --------------------------------------------- renderTrees();
		 */

		// parent.colorMode(PApplet.HSB, 255);

		// agent.draw(pgWall);

		blendPGraphics();
	}

	private void blendPGraphics() {
		parent.image(pgTrails, 0, DeepSpaceConstants.WALL_HEIGHT);
		parent.image(pgTrees, 0, DeepSpaceConstants.WALL_HEIGHT);
		parent.image(pgPlayerAndParticles, 0, DeepSpaceConstants.WALL_HEIGHT);
		parent.image(pgWall, 0, 0);
	}

}
