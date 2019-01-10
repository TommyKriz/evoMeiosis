package evoMeiosis.player;

import java.util.ArrayList;

import evoMeiosis.EvoMeiosisConstants;
import evoMeiosis.logic.Attractor;
import evoMeiosis.seeds.FreeSeed;

public class Player extends Attractor {

	public int id;

	ArrayList<FreeSeed> seeds;

	public Player(int x, int y, int id) {
		super(x, y, EvoMeiosisConstants.PLAYER_CATCH_RADIUS);
		this.id = id;
		seeds = new ArrayList<FreeSeed>();
	}

	void update() {

	}

	// release all Seeds
	void release() {
		for (FreeSeed s : seeds) {
			s.free();
		}
	}

}