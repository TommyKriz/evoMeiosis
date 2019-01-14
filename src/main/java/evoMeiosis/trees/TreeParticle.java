package evoMeiosis.trees;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.apache.commons.lang3.RandomUtils;

import processing.core.PApplet;
import deepSpace.DeepSpaceConstants;
import evoMeiosis.EvoMeiosisConstants;
import evoMeiosis.logic.FADtriple;
import evoMeiosis.seeds.Seed;

public class TreeParticle {
	int x, y;
	boolean stuck = false;
	ArrayList<FADtriple> FADs = new ArrayList<FADtriple>();
	Tree assignedTree;
	float speed = 1;
	TreeSystem ts;

	TreeParticle(TreeSystem ts, Seed s, Tree t) {
		FADs = s.FADs;
		assignedTree = t;
		s.collected = false;
		s.inTree = true;
		reset();
		this.ts = ts;
	}

	void addFAD(FADtriple t) {
		FADs.add(t);
	}

	void reset() {
		// keep choosing random spots until an empty one is found
		do {
			x = PApplet.round(RandomUtils.nextFloat(assignedTree.originX
					- assignedTree.aggregatedGrowthRadius / 2,
					assignedTree.originX + assignedTree.aggregatedGrowthRadius
							/ 2));
			y = PApplet.round(RandomUtils.nextFloat(assignedTree.originY
					- assignedTree.aggregatedGrowthRadius / 2,
					assignedTree.originY + assignedTree.aggregatedGrowthRadius
							/ 2));
			if (x < 0)
				x = 1;
			else if (x > DeepSpaceConstants.WINDOW_WIDTH)
				x = DeepSpaceConstants.WINDOW_WIDTH - 2;

			if (y < 0)
				y = 1;
			else if (y > DeepSpaceConstants.WINDOW_HEIGHT)
				y = DeepSpaceConstants.WINDOW_HEIGHT - 2;

		} while (ts.isFullyGrownAt(y * DeepSpaceConstants.WINDOW_WIDTH + x));
	}

	int[] getParticleFADcolor() {
		float c = 0;
		float a = 0;

		for (FADtriple t : FADs) {
			c += t.frequency - EvoMeiosisConstants.fMin;
			a += t.amplitude - EvoMeiosisConstants.ampMin;
		}

		c = c / FADs.size();
		a = a / FADs.size();

		c = (c * 255) / (EvoMeiosisConstants.fmax - EvoMeiosisConstants.fMin);
		a = (a * 255)
				/ (EvoMeiosisConstants.ampMax - EvoMeiosisConstants.ampMin);

		return new int[] { (int) c, (int) a * 2, 255 };

		// /return new int[]{0, 255, 255};
	}

	boolean update() {
		long ms = System.currentTimeMillis();
		// move around
		if (!stuck) {

			// setTreeColorField(x, y, 0,0,0);

			// calc new pos

			for (int i = 0; i < FADs.size(); i++) {
				FADtriple t = FADs.get(i);

				float addX = (float) t.getXOffset(EvoMeiosisConstants.frequencyScale
						* speed * ms / 100);
				float addY = (float) t.getYOffset(EvoMeiosisConstants.frequencyScale
						* speed * ms / 100);

				x += PApplet.round(addX);
				y += PApplet.round(addY);
			}

			// x += round(random(-1, 1));
			// y += round(random(-1, 1));

			// println("x: " +x + "y: " + y);

			if (Point2D.distance(x, y, assignedTree.originX, assignedTree.originY) > assignedTree.aggregatedGrowthRadius
					|| x < 0
					|| y < 0
					|| x > (DeepSpaceConstants.WINDOW_WIDTH - 1)
					|| y > (DeepSpaceConstants.WINDOW_HEIGHT - 1)) {
				reset();
				return false;
			}

			// test if something is next to us
			if (!alone()) {
				// println("stuck");
				stuck = true;
				int[] c = getParticleFADcolor();
				ts.setTreeColorField(x, y, c[0], c[1], 255);
				assignedTree.aggregatedGrowthRadius += 1;
				return true;
			} else {

				// if(!isFullyGrownAt(x + y * fieldWidth)){
				// setTreeColorField(x, y, 120, 120, 120);
				// }
			}
		}
		return false;
	}

	// returns true if no neighboring pixels
	boolean alone() {

		int w = DeepSpaceConstants.WINDOW_WIDTH;
		int h = DeepSpaceConstants.WINDOW_HEIGHT;

		int cx = x;
		int cy = y;

		// get positions
		int lx = cx - 1;
		int rx = cx + 1;
		int ty = cy - 1;
		int by = cy + 1;

		if (cx <= 0 || cx >= w || lx <= 0 || lx >= w || rx <= 0 || rx >= w
				|| cy <= 0 || cy >= h || ty <= 0 || ty >= h || by <= 0
				|| by >= h)
			return true;

		// pre multiply the ys
		cy *= w;
		by *= w;
		ty *= w;

		// N, W, E, S
		if (ts.isFullyGrownAt(cx + ty) || ts.isFullyGrownAt(lx + cy)
				|| ts.isFullyGrownAt(rx + cy) || ts.isFullyGrownAt(cx + by))
			return false;

		// NW, NE, SW, SE

		if (ts.isFullyGrownAt(lx + ty) || ts.isFullyGrownAt(lx + by)
				|| ts.isFullyGrownAt(rx + ty) || ts.isFullyGrownAt(rx + by))
			return false;

		return true;
	}
}