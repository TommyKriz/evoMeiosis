package demos;

import processing.core.PApplet;
import processing.core.PFont;

public class VerySimpleTuioSample extends PApplet {

	TUIOHelper tuio;

	float cursor_size = 25;
	PFont font;
	int scaleFactor = 4;
	int windowWidth = 3030 / scaleFactor; // for real Deep Space this should be
											// 3030
	int windowHeight = 3712 / scaleFactor; // for real Deep Space this should be
											// 3712
	int wallHeight = 1914 / scaleFactor; // for real Deep Space this should be
											// 1914 (Floor is 1798)
	int show = 0xffff;

	public void settings() {
		size(windowWidth, windowHeight);
	}

	public void setup() {
		noStroke();
		fill(0);

		font = createFont("Arial", 18);
		textFont(font, 18);
		textAlign(CENTER, CENTER);

		tuio = new TUIOHelper(this);
		tuio.initTracking(false, wallHeight);
	}

	public void draw() {
		// clear background with white
		background(255);

		// set upper half of window (=wall projection) bluish
		noStroke();
		fill(70, 100, 150);
		rect(0, 0, windowWidth, wallHeight);
		fill(150);
		text((int) frameRate + " FPS", width / 2, 10);

		// show each track with the corresponding id number
		for (int trackID = 0; trackID < tuio.GetNumTracks(); trackID++) {
			noStroke();
			fill(192, 192, 192);
			ellipse(tuio.GetX(trackID), tuio.GetY(trackID), cursor_size,
					cursor_size);
			fill(0);
			text(tuio.GetCursorID(trackID), tuio.GetX(trackID),
					tuio.GetY(trackID));
			println(" #" + tuio.GetCursorID(trackID) + "->   x:"
					+ tuio.GetX(trackID) + "  y:" + tuio.GetY(trackID));
		}

	}

	public static void main(String[] args) {
		PApplet.main("demos.VerySimpleTuioSample");
	}

}
