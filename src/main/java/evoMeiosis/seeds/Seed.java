package evoMeiosis.seeds;

import java.awt.Color;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;

import deepSpace.DeepSpaceConstants;
import evoMeiosis.EvoMeiosisConstants;
import evoMeiosis.logic.Attractor;
import evoMeiosis.logic.FADtriple;
import evoMeiosis.player.Player;
import processing.core.PApplet;

public class Seed {

	public int x;
	public int y;
	public String uniqueID = UUID.randomUUID().toString();
	public float timeSinceLastUpdate = 0;
	public boolean collected = false;
	public float speed = 1;
	public Attractor attr;
	public float flyAwayTime = 1000;
	public boolean releaseStart = false;
	long initMs;
	int trailLength = 20;
	
	//for trails
	int[][] trail;
	
	
	private SeedSystem s;

	public ArrayList<FADtriple> FADs = new ArrayList<FADtriple>();
	public Player collectedBy;
	
	public Seed(SeedSystem s){
		speed = RandomUtils.nextFloat(0.5f, 1f);
		this.s = s;
		initMs = System.currentTimeMillis();
		FADs.add(new FADtriple(0.1f, 0.1f));
		FADs.add(new FADtriple(0.5f, 0.5f));
		FADs.add(new FADtriple(1f, 1f));
		reset();
		initTrail();
	}

	Seed(SeedSystem s, int trailLength) {
		this(s);
		FADs.add(new FADtriple(0.1f, 0.1f));
		FADs.add(new FADtriple(0.5f, 0.5f));
		FADs.add(new FADtriple(1f, 1f));
		this.trailLength = trailLength;
		initTrail();
	}

	public Seed(SeedSystem s, FADtriple t) {
		this(s);
		FADs.add(t);
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


	void reset() {
		// keep choosing random spots until an empty one is found
		do {
			x = (int) (Math.random() * DeepSpaceConstants.WINDOW_WIDTH);
			y = (int) (Math.random() * DeepSpaceConstants.FLOOR_HEIGHT);

		} while (!s.isEmpty(x, y));
	}

	

	void idxInBounds() {
		if ((x + y * DeepSpaceConstants.WINDOW_WIDTH) < 0
				|| (x + y * DeepSpaceConstants.WINDOW_WIDTH) > (DeepSpaceConstants.WINDOW_WIDTH * DeepSpaceConstants.FLOOR_HEIGHT - 1)) {
			reset();
		}
	}
	

	public int[] getParticleHSLcolor() {
		float c = 0;
		float a = 0;

		for (FADtriple t : FADs) {
			c += t.frequency - EvoMeiosisConstants.fMin;
			a += t.amplitude - EvoMeiosisConstants.ampMin;
		}

		c = c / FADs.size();
		a = a / FADs.size();

		c = (c * 255) / (EvoMeiosisConstants.fmax - EvoMeiosisConstants.fMin);
		a = (a * 255) / (EvoMeiosisConstants.ampMax - EvoMeiosisConstants.ampMin);

		// print("a: " + a + " ");
		// print("c:" + c + " ");

		return new int[] { (int) c, (int) a, 255 };

		// /return new int[]{0, 255, 255};
	}

	void update() {

		long ms = System.currentTimeMillis() - initMs;
		timeSinceLastUpdate = ms - timeSinceLastUpdate;

		// delete from old pos
		if (releaseStart && collected) {
			flyAwayTime -= timeSinceLastUpdate; // <>//
			if (flyAwayTime <= 0) {
				collected = false;
				releaseStart = false;
				attr.intensity = -attr.intensity;
				attr = null;
				//println("released");
			}
		}

		//s.freeSeedFieldParticle[x + y * DeepSpaceConstants.WINDOW_WIDTH] = false;

		// calc new pos
		for (int i = 0; i < FADs.size(); i++) {
			FADtriple t = FADs.get(i);

			float o = EvoMeiosisConstants.frequencyScale * speed * ms / 10000;
			double addX = t.getXOffset(o);
			double addY = t.getYOffset(o);
			
			int roundedX = (int) Math.round(addX);
			int roundedY = (int) Math.round(addY);
			
			///System.out.println(addX + " " + addY);
			
			x += roundedX;
			y += roundedY;
		}

		if (attr != null) {
			x += attr.getXAttraction(x);
			y += attr.getYAttraction(y);
		}

		// check if in bounds, otherwise respawn
		idxInBounds();
		// set new pos
		//s.freeSeedFieldParticle[x + y * DeepSpaceConstants.WINDOW_WIDTH] = true;
		updateTrail(x, y);
		//
		
		//s.freeSeedFieldColor[x + y * DeepSpaceConstants.WINDOW_WIDTH] = getParticleHSLcolor();

	}
	
	private void initTrail() {
		trail = new int[trailLength][2];
		for(int i=0; i< trail.length; i++) {
			trail[i] = new int[] {-1, -1};
		}
	}
	
	private void updateTrail(int x, int y) {
		for(int i= trail.length-1; i>0 ; i--) {
			trail[i] = trail[i-1];
		}
		trail[0] = new int[] {x, y};
	}

	public float getEnergy() {
		float energy = 0;
		for(FADtriple f : FADs) {
			energy += f.amplitude;
		}
		return energy;
	}

	public float getFrequency() {
		float freq = 0;
		for(FADtriple f : FADs) {
			freq += f.frequency;
		}
		return freq/3;
	}
}