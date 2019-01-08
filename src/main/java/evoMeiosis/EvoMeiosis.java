package evoMeiosis;

import processing.core.PApplet;
import processing.core.PGraphics;
import deepSpace.DeepSpaceConstants;
import deepSpace.tuio.DeepSpaceTUIOHelper;
import evoMeiosis.audio.AudioTest;

public class EvoMeiosis {

	private PApplet parent;

	private PGraphics pgTrails, pgPlayerAndParticles, pgTrees;

	private EvoMeiosisUpdater evoMeiosis;

	public EvoMeiosis(PApplet parent, DeepSpaceTUIOHelper deepSpaceTUIOHelper) {
		this.parent = parent;
		initCanvases();
		// TODO: remove, this is for demo purposes
		new AudioTest(parent).playSound();
		evoMeiosis = new EvoMeiosisUpdater(deepSpaceTUIOHelper);

	}

	private void initCanvases() {
		pgTrails = parent.createGraphics(DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.FLOOR_HEIGHT);
		pgPlayerAndParticles = parent.createGraphics(
				DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.FLOOR_HEIGHT);
		pgTrees = parent.createGraphics(DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.FLOOR_HEIGHT);
	}

	public void update() {

		// logic

		evoMeiosis.update();

		// draw

		evoMeiosis.paintPlayers(pgPlayerAndParticles);
		evoMeiosis.paintTrails(pgTrails);

		blendPGraphics();
	}

	private void blendPGraphics() {
		parent.image(pgTrails, 0, DeepSpaceConstants.WALL_HEIGHT);
		parent.image(pgTrees, 0, DeepSpaceConstants.WALL_HEIGHT);
		parent.image(pgPlayerAndParticles, 0, DeepSpaceConstants.WALL_HEIGHT);
	}

}
