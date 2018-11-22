package evoMeiosis;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import tuio.DeepSpaceTUIOHelper;

public class EvoMeiosis {

	DeepSpaceTUIOHelper tuioClient;

	ArrayList<PVector> players = new ArrayList<>();

	ArrayList<PVector> trees = new ArrayList<>();

	public EvoMeiosis(PApplet parent, int wallHeight) {
		tuioClient = new DeepSpaceTUIOHelper(parent, wallHeight);
	}

	public void update() {
		players = tuioClient.getPlayers();
		updateTrees();
	}

	/**
	 * see if there are n players connected together within a certain radius and
	 * get the centroid of the relevant player gathering
	 */
	private void updateTrees() {
		// TODO:
	}

	public ArrayList<PVector> getPlayers() {
		return players;
	}

}
