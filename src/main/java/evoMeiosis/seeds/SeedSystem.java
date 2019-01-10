package evoMeiosis.seeds;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import evoMeiosis.EvoMeiosisConstants;

public class SeedSystem {

	ArrayList<FreeSeed> freeSeeds = new ArrayList<FreeSeed>();

	public SeedSystem() {
		// add free collectable seeds to playfield
		for (int i = 0; i < EvoMeiosisConstants.INIT_SEEDS_NUMBER; i++) {
			freeSeeds.add(new FreeSeed());
		}
	}

	public void renderFreeSeeds(PGraphics pgTrails,
			PGraphics pgPlayerAndParticles) {
		for (int f = 0; f < freeSeeds.size(); f++) {
			freeSeeds.get(f).update();
		}

		pgTrails.loadPixels();
		// draw free seedsField
		for (int s = 0; s < freeSeedFieldParticle.length; s++) {
			// println("before " + brightness(pg.pixels[s]));
			if (freeSeedFieldParticle[s]) {
				// colorMode(HSB, 255);
				// particle
				int[] c = freeSeedFieldColor[s];

				pgTrails.pixels[s] = color(c[0], c[1], c[2]);

				int x = s % fieldWidth;
				int y = (s - x) / fieldWidth;
				pgTrails.stroke(255);
				pgTrails.fill(color(c[0], c[1], c[2]));
				pgTrails.ellipse(x, y, 10, 10);
			} else if (red(pgTrails.pixels[s]) > 0
					|| green(pgTrails.pixels[s]) > 0
					|| blue(pgTrails.pixels[s]) > 0) {
				// trail
				colorMode(RGB, 255);
				pgTrails.pixels[s] = color(
						max(0, red(pgTrails.pixels[s]) - trailDecayRate),
						max(0, green(pgTrails.pixels[s]) - trailDecayRate),
						max(0, blue(pgTrails.pixels[s]) - trailDecayRate));
				colorMode(HSB, 255);
			}/*
			 * else{ pg.pixels[s] = color(0, 0, 0); }
			 */
			// println("after " + brightness(pg.pixels[s]));
		}
		pgTrails.updatePixels();

		pgPlayerAndParticles.beginDraw();
		pgPlayerAndParticles.background(0, 0, 0, 0);

		for (int s = 0; s < freeSeedFieldParticle.length; s++) {
			// println("before " + brightness(pg.pixels[s]));
			if (freeSeedFieldParticle[s]) {
				// colorMode(HSB, 255);
				// particle
				int[] c = freeSeedFieldColor[s];
				int x = s % fieldWidth;
				int y = (s - x) / fieldWidth;
				pgPlayerAndParticles.noStroke();
				pgPlayerAndParticles.fill(color(0, 0, 255, 150));
				pgPlayerAndParticles.ellipse(x, y, 2, 2);
				pgPlayerAndParticles.fill(color(c[0], c[1], c[2], 150));
				pgPlayerAndParticles.ellipse(x, y, 4, 4);
				pgPlayerAndParticles.fill(color(c[0], c[1], c[2], 80));
				pgPlayerAndParticles.ellipse(x, y, 8, 8);
				pgPlayerAndParticles.fill(color(c[0], c[1], c[2], 20));
				pgPlayerAndParticles.ellipse(x, y, 16, 16);
				pgPlayerAndParticles.fill(color(0, 0, 255, 20));
				pgPlayerAndParticles.ellipse(x, y, 18, 18);
			}
		}
		pgPlayerAndParticles.endDraw();

	}

}
