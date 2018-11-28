package evoMeiosis;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import tuio.DeepSpaceTUIOHelper;

public class EvoMeiosis {

	DeepSpaceTUIOHelper tuioClient;

	ArrayList<PVector> players = new ArrayList<>();

	ArrayList<PVector> trees = new ArrayList<>();

	PApplet parent;

	private ITreeSpawner treeSpawner = new TreeSpawner();

	public EvoMeiosis(PApplet parent, int wallHeight) {
		this.parent = parent;
		tuioClient = new DeepSpaceTUIOHelper(parent, wallHeight);
	}

	public void update() {
		players = tuioClient.getPlayers();

		updateTrees();
		// render
		render();
	}

	private void render() {
		parent.fill(10);
		for (PVector p : players) {

			for (PVector x : players) {
				parent.line(p.x, p.y, x.x, x.y);
			}
			// parent.ellipse(p.x, p.y, 25, 25);
		}

	}

	/**
	 * see if there are n players connected together within a certain radius and
	 * get the centroid of the relevant player gathering
	 */
	private void updateTrees() {
		// TODO:

		// TODO: treeSpawner.spawnTree(parent, x, y);
		for (PVector p1 : players) {
			for (PVector p2 : players) {
				double d = p1.dist(p2);
				if (d > 0 && d < 150) {
					treeSpawner.spawnTree(parent, (p1.x + p2.x) / 2,
							(p1.y + p2.y) / 2);
				}
			}
		}
	}

	public ArrayList<PVector> getPlayers() {
		return players;
	}

}
