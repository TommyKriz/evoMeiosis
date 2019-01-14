package evoMeiosis.seeds;

import java.util.ArrayList;

import deepSpace.DeepSpaceConstants;
import processing.core.PApplet;
import processing.core.PGraphics;
import evoMeiosis.EvoMeiosisConstants;

public class SeedSystem {

	//arrays for drawing pixels
	public int[][] freeSeedFieldColor;
	public boolean[] freeSeedFieldParticle;

	//data array
	public ArrayList<Seed> freeSeeds = new ArrayList<Seed>();

	public SeedSystem() {

		freeSeedFieldColor = new int[DeepSpaceConstants.WINDOW_WIDTH
				* DeepSpaceConstants.FLOOR_HEIGHT][3];
		freeSeedFieldParticle = new boolean[DeepSpaceConstants.WINDOW_WIDTH
				* DeepSpaceConstants.FLOOR_HEIGHT];

		// add free collectable seeds to playfield
		for (int i = 0; i < EvoMeiosisConstants.INIT_SEEDS_NUMBER; i++) {
			freeSeeds.add(new Seed(this));
		}
	}
	
	public void destroy(Seed s) {
		freeSeedFieldParticle[s.x + s.y * DeepSpaceConstants.WINDOW_WIDTH] = false;
		freeSeeds.remove(s);
	}
	
	boolean isEmpty(int x, int y) {
		// println("new pos" + x + " " + y);
		return !freeSeedFieldParticle[y * DeepSpaceConstants.WINDOW_WIDTH + x];
		// return freeSeedFieldColor[y * fieldWidth + x] == new int[] {0, 0,
		// 0};
	}
	
	public void paintTrailsV2(PGraphics pgTrails) {
		pgTrails.beginDraw();
		//pgTrails.loadPixels();
		
		pgTrails.colorMode(pgTrails.HSB, 255);
		//pgTrails.clear();
		pgTrails.background(0,0,0,255);
		//pgTrails.background(0,0,0,0);
		pgTrails.noStroke();
		
		for(Seed s : freeSeeds) {
			int[] c = s.getParticleHSLcolor();
			
			for(int i=0; i<s.trail.length;i++) {
				float decay = (float)i / (float) s.trail.length;
				pgTrails.fill(pgTrails.color(c[0], c[1], c[2], 200- decay*255));
				int elSize = (int) (5f - 5f*decay);
				pgTrails.ellipse(s.trail[i][0], s.trail[i][1], elSize, elSize);
			}
			
		}
		pgTrails.endDraw();
		//pgTrails.updatePixels();
	}
	
	

	public void paintTrails(PGraphics pgTrails) {
		
		pgTrails.beginDraw();
		pgTrails.loadPixels();
		
		// draw free seedsField
		for (int s = 0; s < freeSeedFieldParticle.length; s++) {
			// println("before " + brightness(pg.pixels[s]));
			if (freeSeedFieldParticle[s]) {
				// colorMode(HSB, 255);
				// particle
				int[] c = freeSeedFieldColor[s];

				pgTrails.pixels[s] = pgTrails.color(c[0], c[1], c[2]);

				int x = s % DeepSpaceConstants.WINDOW_WIDTH;
				int y = (s - x) / DeepSpaceConstants.WINDOW_WIDTH;
				pgTrails.stroke(255);
				pgTrails.fill(pgTrails.color(c[0], c[1], c[2]));
				pgTrails.ellipse(x, y, 10, 10);
			} else if
			(pgTrails.red(pgTrails.pixels[s]) > 0 || pgTrails.green(pgTrails.pixels[s]) > 0
					|| pgTrails.blue(pgTrails.pixels[s]) > 0) {
				
				// trail
				pgTrails.colorMode(PApplet.RGB, 255);
				pgTrails.pixels[s] = pgTrails.color(
						Math.max(0, pgTrails.red(pgTrails.pixels[s]) - EvoMeiosisConstants.trailDecayRate),
						Math.max(0, pgTrails.green(pgTrails.pixels[s]) - EvoMeiosisConstants.trailDecayRate),
						Math.max(0, pgTrails.blue(pgTrails.pixels[s]) - EvoMeiosisConstants.trailDecayRate));
				pgTrails.colorMode(PApplet.HSB, 255);
			}/*
			 * else{ pg.pixels[s] = color(0, 0, 0); }
			 */
			// println("after " + brightness(pg.pixels[s]));
		}
		pgTrails.updatePixels();
	}
	
	
	public void updateAndPaintSeeds(PGraphics pgPlayerAndParticles) {
		
		pgPlayerAndParticles.beginDraw();
		pgPlayerAndParticles.clear();
		
		//pgPlayerAndParticles.background(0, 0, 0, 255);
		pgPlayerAndParticles.background(0, 0, 0, 0);
		
		for(Seed s : freeSeeds) {
			s.update();
			int[] c = s.getParticleHSLcolor();
			pgPlayerAndParticles.stroke(c[0], c[1], c[2], 100);
			//pgPlayerAndParticles.stroke(c[0], c[1], c[2], 150);
			
			//pgPlayerAndParticles.fill(pgPlayerAndParticles.color(c[0], c[1], c[2], 150));
			//pgPlayerAndParticles.ellipse(s.x, s.y, 2, 2);
			
			pgPlayerAndParticles.fill(pgPlayerAndParticles.color(c[0], c[1], c[2], 100));
			pgPlayerAndParticles.ellipse(s.x, s.y, 8, 8);
			//pgPlayerAndParticles.fill(pgPlayerAndParticles.color(c[0], c[1], c[2], 80));
			//pgPlayerAndParticles.ellipse(s.x, s.y, 8, 8);
			
			//pgPlayerAndParticles.fill(pgPlayerAndParticles.color(c[0], c[1], c[2], 20));
			//pgPlayerAndParticles.ellipse(s.x, s.y, 16, 16);
			//pgPlayerAndParticles.fill(pgPlayerAndParticles.color(c[0], c[1], c[2], 20));
			////pgPlayerAndParticles.ellipse(s.x, s.y, 18, 18);
			//pgPlayerAndParticles.fill(pgPlayerAndParticles.color(0, 0, 255, 15));
			//pgPlayerAndParticles.ellipse(s.x, s.y, 22, 22);
			//pgPlayerAndParticles.fill(pgPlayerAndParticles.color(0, 0, 255, 10));
			//pgPlayerAndParticles.ellipse(s.x, s.y, 30, 30);
			//pgPlayerAndParticles.fill(pgPlayerAndParticles.color(0, 0, 255, 5));
			//pgPlayerAndParticles.ellipse(s.x, s.y, 40, 40);
			
			//pgPlayerAndParticles.fill(pgPlayerAndParticles.color(c[0], c[1], c[2], 20));
			//pgPlayerAndParticles.ellipse(s.x, s.y, 10, 10);
		}

		
		/*
		for (int s = 0; s < freeSeedFieldParticle.length; s++) {
			// println("before " + brightness(pg.pixels[s]));
			if (freeSeedFieldParticle[s]) {
				// colorMode(HSB, 255);
				// particle
				int[] c = freeSeedFieldColor[s];
				int x = s % DeepSpaceConstants.WINDOW_WIDTH;
				int y = (s - x) / DeepSpaceConstants.WINDOW_WIDTH;
				
			}
		}*/
		pgPlayerAndParticles.endDraw();

	}

}
