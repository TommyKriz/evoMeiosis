package deepSpace;

import processing.core.PApplet;
import evoMeiosis.EvoMeiosis;

public class DeepSpaceProcessingSketch extends PApplet {

	EvoMeiosis evoMeiosis;

	@Override
	public void settings() {
		size(DeepSpaceConstants.WINDOW_WIDTH, DeepSpaceConstants.WINDOW_HEIGHT);
	}

	@Override
	public void setup() {
		frameRate(30);
		evoMeiosis = new EvoMeiosis(this);

	}

	@Override
	public void draw() {
		evoMeiosis.update();
	}
}
