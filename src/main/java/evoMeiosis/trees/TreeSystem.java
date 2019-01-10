package evoMeiosis.trees;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import deepSpace.DeepSpaceConstants;
import evoMeiosis.EvoMeiosisConstants;

public class TreeSystem {

	private int[][] treeFieldColor;

	private ArrayList<Tree> trees = new ArrayList<Tree>();

	public TreeSystem() {
		treeFieldColor = new int[DeepSpaceConstants.WINDOW_WIDTH
				* DeepSpaceConstants.WINDOW_HEIGHT][3];
	}

	void renderTrees(PGraphics pgPlayerAndParticles, PGraphics pgTrees) {
		for (Tree t : trees) {
			t.update();
			pgPlayerAndParticles.beginDraw();
			pgPlayerAndParticles.colorMode(PApplet.HSB, 255);
			pgPlayerAndParticles.fill(60, 100, 100, 40);
			pgPlayerAndParticles.noStroke();
			pgPlayerAndParticles.ellipse(t.originX, t.originY,
					EvoMeiosisConstants.TREE_RADIUS,
					EvoMeiosisConstants.TREE_RADIUS);
			pgPlayerAndParticles.fill(70, 100, 100, 30);
			pgPlayerAndParticles.noStroke();
			pgPlayerAndParticles.ellipse(t.originX, t.originY,
					EvoMeiosisConstants.NEW_TREE_RADIUS,
					EvoMeiosisConstants.NEW_TREE_RADIUS);
			pgPlayerAndParticles.endDraw();
		}

		pgPlayerAndParticles.colorMode(PApplet.RGB, 255);
		pgTrees.beginDraw();
		pgTrees.loadPixels();
		pgTrees.colorMode(PApplet.HSB, 255);

		for (int i = 0; i < treeFieldColor.length; i++) {
			int alpha = 0;
			if (treeFieldColor[i][0] != 0 || treeFieldColor[i][1] != 0
					|| treeFieldColor[i][2] != 0) {
				alpha = 150 + treeFieldColor[i][2];
			}
			// TODO: oder parent.color ?
			pgTrees.pixels[i] = pgTrees.color(treeFieldColor[i][0],
					treeFieldColor[i][1], treeFieldColor[i][2], alpha);
		}

		pgTrees.updatePixels();

		pgTrees.endDraw();
	}

	void setTreeColorField(int x, int y, int r, int g, int b) {
		treeFieldColor[x + y * DeepSpaceConstants.WINDOW_WIDTH] = new int[] {
				r, g, b };
	}

	int[] getTreeFieldColor(int x, int y) {
		return treeFieldColor[x + y * DeepSpaceConstants.WINDOW_WIDTH];
	}

	boolean isFullyGrownAt(int i) {
		if (i >= DeepSpaceConstants.WINDOW_WIDTH
				* DeepSpaceConstants.WINDOW_HEIGHT) {
			return false;
		}
		int[] c = new int[] { 255, 255, 255 };
		return treeFieldColor[i][2] == c[2]; // <>//
	}

	public void checkForNewTree(ArrayList<PVector> playerPositions) {

		for (PVector p1 : playerPositions) {
			for (PVector p2 : playerPositions) {

				// if (p1.id == p2.id) {
				// continue;
				// }

				if (distance(p1.x, p1.y, p2.x, p2.y) < EvoMeiosisConstants.PLAYER_CATCH_RADIUS) {
					int mx = (int) ((p1.x + p2.x) / 2);
					int my = (int) ((p1.y + p2.y) / 2);
					// check if tree already exists in this area!
					boolean treeAlreadyThere = false;
					for (Tree t : trees) {
						if (distance(mx, my, t.originX, t.originY) < EvoMeiosisConstants.NEW_TREE_RADIUS) {
							treeAlreadyThere = true;
							break;
						}
					}
					if (!treeAlreadyThere) {
						trees.add(new Tree(mx, my));
						growFundament(mx, my, 1);
					}

				}

			}
		}

	}

	// ---------------------------------------------------------------------------------------------------

	private void growFundament(int originX, int originY, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				setTreeColorField(originX + i - (size / 2), originY + j
						- (size / 2), 255, 255, 255);
			}
		}
	}

	// helper methods----------------------------------------------------------
	private double distance(float x1, float y1, float x2, float y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

}
