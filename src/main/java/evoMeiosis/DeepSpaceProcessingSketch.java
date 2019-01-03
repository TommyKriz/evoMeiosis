package evoMeiosis;

import processing.core.PApplet;
import tuio.DeepSpaceTUIOHelper;

public class DeepSpaceProcessingSketch extends PApplet {

	private static final int DEEP_SPACE_WIDTH = 3030;

	private static final int DEEP_SPACE_WALL_HEIGHT = 1914;

	private static final int DEEP_SPACE_HEIGHT = 3712;

	private static final int scaleFactor = 4;

	final int windowWidth = DEEP_SPACE_WIDTH / scaleFactor;
	final int windowHeight = DEEP_SPACE_HEIGHT / scaleFactor;
	final int wallHeight = DEEP_SPACE_WALL_HEIGHT / scaleFactor;

	EvoMeiosis evoMeiosis;

	@Override
	public void settings() {
		size(windowWidth, windowHeight);
	}

	@Override
	public void setup() {
		stroke(255);
		// frameRate(30);
		evoMeiosis = new EvoMeiosis(this, windowWidth, windowHeight
				- wallHeight, new DeepSpaceTUIOHelper(this, wallHeight));

	}

	@Override
	public void draw() {
		// float alpha = 30;
		// // background(10, alpha);
		//
		// // set upper half of window (=wall projection) bluish
		// fill(70, 100, 150, alpha);
		// rect(0, 0, windowWidth, windowHeight);
		//
		// // set lower half of window (=floor projection) reddish
		// fill(250, 100, 50, alpha);
		// rect(0, wallHeight, windowWidth, wallHeight);

		evoMeiosis.update();
	}

	public static void main(String[] args) {
		PApplet.main("evoMeiosis.DeepSpaceProcessingSketch");
	}

}
