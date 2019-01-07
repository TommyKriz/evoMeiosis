package deepSpace;

import processing.core.PApplet;
import deepSpace.tuio.DeepSpaceTUIOHelper;
import evoMeiosis.EvoMeiosis;

public class DeepSpaceProcessingSketch extends PApplet {

	EvoMeiosis evoMeiosis;

	@Override
	public void settings() {
		size(DeepSpaceConstants.WINDOW_WIDTH, DeepSpaceConstants.WINDOW_HEIGHT);
	}

	@Override
	public void setup() {
		stroke(255);
		// frameRate(30);
		evoMeiosis = new EvoMeiosis(this, new DeepSpaceTUIOHelper(this));

	}

	@Override
	public void draw() {

		fill(0, 0, 0, 30);
		rect(0, 0, DeepSpaceConstants.WINDOW_WIDTH,
				DeepSpaceConstants.WALL_HEIGHT);

		evoMeiosis.update();
	}
}
