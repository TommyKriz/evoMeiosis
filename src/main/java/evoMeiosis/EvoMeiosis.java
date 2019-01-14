package evoMeiosis;

import processing.core.PApplet;
import processing.core.PGraphics;
import deepSpace.DeepSpaceConstants;
import deepSpace.tuio.DeepSpaceTUIOHelper;
import evoMeiosis.audio.Agent;
import evoMeiosis.audio.Audio;
import evoMeiosis.audio.AudioTest;

public class EvoMeiosis {

	private PApplet parent;

	private PGraphics pgTrails, pgParticles, pgPlayers, pgTrees, pgWall;

	private EvoMeiosisEngine evoMeiosisEngine;

	private static Audio audio;

	public EvoMeiosis(PApplet parent) {
		this.parent = parent;
		initCanvases();
		
		// TODO: remove, this is for demo purposes
		//new AudioTest(parent).playSound();
		//agent = new Agent();
		audio = new Audio();
		audio.setup(parent);
		evoMeiosisEngine = new EvoMeiosisEngine(new DeepSpaceTUIOHelper(parent), audio);

	}

	private void initCanvases() {
		pgTrails = parent.createGraphics(DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.FLOOR_HEIGHT);
		
		pgParticles = parent.createGraphics(DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.FLOOR_HEIGHT);
		
		pgPlayers = parent.createGraphics(DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.FLOOR_HEIGHT);
		
		pgTrees = parent.createGraphics(DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.FLOOR_HEIGHT);
		
		pgWall = parent.createGraphics(DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.WALL_HEIGHT);
	}

	public void update() {

		// logic
		audio.soundUpdate();

		evoMeiosisEngine.audioUpdate(audio);
		
		//System.out.println(parent.frameRate);
		
		evoMeiosisEngine.update();
		
		evoMeiosisEngine.paintPlayers(pgPlayers);
		
		//long startTime = System.currentTimeMillis();
		evoMeiosisEngine.paintSeeds(pgParticles);
		//System.out.println("PaintSeeds " + (System.currentTimeMillis() - startTime));
		
		//startTime = System.currentTimeMillis();
		evoMeiosisEngine.paintTrails(pgTrails);
		//System.out.println("paintTrails " + (System.currentTimeMillis() - startTime));
		
		evoMeiosisEngine.paintTrees(pgTrees);
		
		//evoMeiosisEngine.paintTestPlayers(pgPlayers, parent);
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
		parent.image(pgParticles, 0, DeepSpaceConstants.WALL_HEIGHT);
		parent.image(pgTrees, 0, DeepSpaceConstants.WALL_HEIGHT);
		parent.image(pgPlayers, 0, DeepSpaceConstants.WALL_HEIGHT);
		
		//parent.image(pgWall, 0, 0);
	}

}
