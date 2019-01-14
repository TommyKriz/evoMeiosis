package evoMeiosis.player;

import java.util.ArrayList;

import evoMeiosis.EvoMeiosisConstants;
import evoMeiosis.logic.Attractor;
import evoMeiosis.seeds.Seed;

public class Player extends Attractor {

	public long id;

	public ArrayList<Seed> collectedSeeds;

	public Player(float x, float y, long id) {
		super(x, y, EvoMeiosisConstants.PLAYER_CATCH_RADIUS);
		this.id = id;
		collectedSeeds = new ArrayList<Seed>();
	}

	void update(float x, float y) {
		xOrig = x;
		yOrig = y;
		System.out.println(id + " : " + collectedSeeds.size());
	}

	// release all Seeds
	void release() {
		for (Seed s : collectedSeeds) {
			s.free();
		}
	}

	@Override
	public String toString() {
		return "Player #" + id + "  - x: " + xOrig + "  y: " + yOrig;
	}

}