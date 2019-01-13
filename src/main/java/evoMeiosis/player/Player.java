package evoMeiosis.player;

import java.util.ArrayList;

import evoMeiosis.EvoMeiosisConstants;
import evoMeiosis.logic.Attractor;
import evoMeiosis.seeds.FreeSeed;

public class Player extends Attractor {

	public int id;

	public ArrayList<FreeSeed> collectedSeeds;

	public Player(int x, int y, int id) {
		super(x, y, EvoMeiosisConstants.PLAYER_CATCH_RADIUS);
		this.id = id;
		collectedSeeds = new ArrayList<FreeSeed>();
	}

	void update() {
		System.out.println(id + " : " + collectedSeeds.size());
	}

	// release all Seeds
	void release() {
		for (FreeSeed s : collectedSeeds) {
			s.free();
		}
	}

}