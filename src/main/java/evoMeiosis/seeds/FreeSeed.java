package evoMeiosis.seeds;

import java.util.ArrayList;

import org.apache.commons.lang3.RandomUtils;

import deepSpace.DeepSpaceConstants;
import evoMeiosis.logic.Attractor;
import evoMeiosis.logic.FADtriple;
import processing.core.PApplet;

public class FreeSeed {

	public int x;
	public int y;
	private float timeSinceLastUpdate = 0;
	private boolean collected = false;
	private boolean inTree = false;
	private boolean xIncr = false;
	private boolean yIncr = false;
	private float speed = 1;
	private Attractor attr;
	private float flyAwayTime = 1000;
	private boolean releaseStart = false;

	ArrayList<FADtriple> FADs = new ArrayList<FADtriple>();

	FreeSeed() {
		// println("new free seed");
		reset();
		FADs.add(new FADtriple(0.1f, 0.1f));
		FADs.add(new FADtriple(0.5f, 0.5f));
		FADs.add(new FADtriple(1f, 1f));
		speed = RandomUtils.nextFloat(0.5f, 1f);
	}

	public FreeSeed(int n) {
		// println("new free seed");
		reset();
		speed = RandomUtils.nextFloat(0.5f, 1f);
	}

	FreeSeed(FADtriple t) {
		// println("new free seed");
		reset();
		FADs.add(t);
		speed = RandomUtils.nextFloat(0.5f, 1f);
	}

	public void addFAD(FADtriple t) {
		FADs.add(t);
	}

	public void free() {
		if (attr != null) {
			attr.intensity = -attr.intensity;
			releaseStart = true;
		}
	}

	void destroy() {
		freeSeedFieldParticle[x + y * fieldWidth] = false;
		freeSeeds.remove(this);
	}

	void reset() {
		// keep choosing random spots until an empty one is found
		do {
			x = floor(random(DeepSpaceConstants.WINDOW_WIDTH));
			y = floor(random(DeepSpaceConstants.WINDOW_HEIGHT));

		} while (!isEmpty(x, y));
	}

	boolean isEmpty(int x, int y) {
		// println("new pos" + x + " " + y);
		return !freeSeedFieldParticle[y * fieldWidth + x];
		// return freeSeedFieldColor[y * fieldWidth + x] == new int[] {0, 0,
		// 0};
	}

	void idxInBounds() {
		if ((x + y * fieldWidth) < 0
				|| (x + y * fieldWidth) > (fieldWidth * fieldHeight - 1)) {
			reset();
		}
	}

	int[] getParticleFADcolor() {
		float c = 0;
		float a = 0;

		for (FADtriple t : FADs) {
			c += t.frequency - fMin;
			a += t.amplitude - ampMin;
		}

		c = c / FADs.size();
		a = a / FADs.size();

		c = (c * 255) / (fmax - fMin);
		a = (a * 255) / (ampMax - ampMin);

		// print("a: " + a + " ");
		// print("c:" + c + " ");

		colorMode(HSB, 255);

		return new int[] { (int) c, (int) a, 255 };

		// /return new int[]{0, 255, 255};
	}

	void update() {

		timeSinceLastUpdate = millis() - timeSinceLastUpdate;

		// delete from old pos
		if (releaseStart && collected) {
			flyAwayTime -= timeSinceLastUpdate; // <>//
			if (flyAwayTime <= 0) {
				collected = false;
				releaseStart = false;
				attr.intensity = -attr.intensity;
				attr = null;
				println("released");
			}
		}

		freeSeedFieldParticle[x + y * fieldWidth] = false;

		// calc new pos
		for (int i = 0; i < FADs.size(); i++) {
			FADtriple t = FADs.get(i);

			float addX = t.getXOffset(globalSpeed * speed * millis() / 10000);
			float addY = t.getYOffset(globalSpeed * speed * millis() / 10000);

			x += round(addX);
			y += round(addY);
		}

		if (attr != null) {
			x += attr.getXAttraction(x);
			y += attr.getYAttraction(y);
		}

		// check if in bounds, otherwise respawn
		idxInBounds();
		// set new pos
		freeSeedFieldParticle[x + y * fieldWidth] = true;
		freeSeedFieldColor[x + y * fieldWidth] = getParticleFADcolor();

	}
}