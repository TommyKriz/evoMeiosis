package evoMeiosis.trees;

import java.util.ArrayList;

import processing.core.PGraphics;
import evoMeiosis.EvoMeiosisConstants;
import evoMeiosis.logic.Attractor;
import evoMeiosis.logic.FADtriple;
import evoMeiosis.seeds.Seed;

public class Tree extends Attractor {
	public int originX, originY;
	public float aggregatedGrowthRadius;
	public ArrayList<TreeParticle> treeParticles;
	public PGraphics pgT;
	public int seedConversionFactor = 1000;
	public int seedSpawnRate = 100;
	public int stuckCnt = 0;
	TreeSystem ts;

	Tree(TreeSystem ts, int mx, int my) {
		super(mx, my, EvoMeiosisConstants.TREE_RADIUS); 
		originX = mx;
		originY = my;
		aggregatedGrowthRadius = 200;
		// add seed at the middle
		treeParticles = new ArrayList<TreeParticle>();
		this.ts = ts;
	}

	// add seeds and convert them to TreeParticles so they can be used to
	// grow the tree
	public void addSeeds(Seed[] seedsToBeAdded) {
		for (int i = 0; i < seedsToBeAdded.length; i++) {
			treeParticles.add(new TreeParticle(ts, seedsToBeAdded[i], this));
		}
	}

	public void addSeed(Seed seed) {
		for (int i = 0; i < seedConversionFactor; i++) {
			treeParticles.add(new TreeParticle(ts, seed, this));
		}
	}

	//
	// private void spawnSeed(int x, int y) {
	//
	// FreeSeed fs = new FreeSeed(0);
	// float a = 0, f = 0, d = 0;
	// for (TreeParticle t : treeParticles) {
	// for (FADtriple fad : t.FADs) {
	// a += fad.amplitude;
	// f += fad.frequency;
	// d += fad.dampingCoefficient;
	// }
	// a /= t.FADs.size();
	// f /= t.FADs.size();
	// d /= t.FADs.size();
	//
	// FADtriple fadt = new FADtriple(1, 1);
	// fadt.setFAD(f, a, d);
	// fs.addFAD(fadt);
	// }
	// fs.x = x;
	// fs.y = y;
	// freeSeeds.add(fs);
	// }

	// grow a bit
	void update() {
		// println("tree @ " + originY + "/" + originY + " has " +
		// treeParticles.size() + " particles");
		for (int i = 0; i < treeParticles.size(); i++) {
			if (!treeParticles.get(i).stuck && treeParticles.get(i).update()) { // <>//
				stuckCnt++;
			}

			if (stuckCnt >= seedSpawnRate) {
				stuckCnt = 0;
				// TODO
				// spawnSeed((int) (originX + random(newTreeRadius,
				// aggregatedGrowthRadius)), (int) (originY +
				// random(newTreeRadius, aggregatedGrowthRadius)));
			}
		}
	}

}