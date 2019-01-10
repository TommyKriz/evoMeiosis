package evoMeiosis.trees;

import java.util.ArrayList;

import evoMeiosis.logic.Attractor;
import evoMeiosis.logic.FADtriple;
import evoMeiosis.seeds.FreeSeed;
import processing.core.PGraphics;

public class Tree extends Attractor {
	int originX, originY;
	float aggregatedGrowthRadius;
	ArrayList<TreeParticle> treeParticles;
	PGraphics pgT;
	int seedConversionFactor = 1000;
	int seedSpawnRate = 100;
	int stuckCnt = 0;

	Tree(int originPosX, int originPosY) {
		super(originPosX, originPosY, treeRadius);
		originX = originPosX;
		originY = originPosY;
		aggregatedGrowthRadius = 200;
		// add seed at the middle
		treeParticles = new ArrayList<TreeParticle>();
		growFundament(1);
	}

	void growFundament(int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				setTreeColorField(originX + i - (size / 2), originY + j
						- (size / 2), 255, 255, 255);
			}
		}
	}

	// add seeds and convert them to TreeParticles so they can be used to
	// grow the tree
	void addSeeds(FreeSeed[] seedsToBeAdded) {
		for (int i = 0; i < seedsToBeAdded.length; i++) {
			treeParticles.add(new TreeParticle(seedsToBeAdded[i], this));
		}
	}

	void addSeed(FreeSeed seed) {
		for (int i = 0; i < seedConversionFactor; i++) {
			treeParticles.add(new TreeParticle(seed, this));
		}
	}

	void spawnSeed(int x, int y) {

		FreeSeed fs = new FreeSeed(0);
		float a = 0, f = 0, d = 0;
		for (TreeParticle t : treeParticles) {
			for (FADtriple fad : t.FADs) {
				a += fad.amplitude;
				f += fad.frequency;
				d += fad.dampingCoefficient;
			}
			a /= t.FADs.size();
			f /= t.FADs.size();
			d /= t.FADs.size();

			FADtriple fadt = new FADtriple(1, 1);
			fadt.setFAD(f, a, d);
			fs.addFAD(fadt);
		}
		fs.x = x;
		fs.y = y;
		freeSeeds.add(fs);
	}

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