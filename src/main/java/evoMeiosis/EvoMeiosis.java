package evoMeiosis;

import processing.core.PApplet;
import processing.core.PGraphics;
import tuio.DeepSpaceTUIOHelper;

public class EvoMeiosis {

	private PApplet parent;

	private PGraphics pgTrails, pgPlayerAndParticles, pgTrees;

	private EvoMeiosisUpdater evoMeiosis;

	public EvoMeiosis(PApplet parent, int fieldWidth, int fieldHeight,
			DeepSpaceTUIOHelper deepSpaceTUIOHelper) {
		this.parent = parent;
		initCanvases(fieldWidth, fieldHeight);
		evoMeiosis = new EvoMeiosisUpdater(fieldWidth, fieldHeight,
				deepSpaceTUIOHelper);

	}

	private void initCanvases(int fieldWidth, int fieldHeight) {
		pgTrails = parent.createGraphics(fieldWidth, fieldHeight);
		pgPlayerAndParticles = parent.createGraphics(fieldWidth, fieldHeight);
		pgTrees = parent.createGraphics(fieldWidth, fieldHeight);
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
		parent.image(pgTrails, 0, 0);
		parent.image(pgTrees, 0, 0);
		parent.image(pgPlayerAndParticles, 0, 0);
	}

}
