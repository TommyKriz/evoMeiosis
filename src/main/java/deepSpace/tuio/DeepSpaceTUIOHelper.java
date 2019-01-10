package deepSpace.tuio;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import TUIO.TuioCursor;
import TUIO.TuioProcessing;
import deepSpace.DeepSpaceConstants;
import evoMeiosis.player.Player;

public class DeepSpaceTUIOHelper {

	private TuioProcessing tuioClient;

	private static final int INVALID = -1;

	private ArrayList<PVector> playerPositions = new ArrayList<>();

	ArrayList<Player> players = new ArrayList<>();

	public DeepSpaceTUIOHelper(final PApplet parent) {
		tuioClient = new TuioProcessing(parent);
	}

	private int GetNumTracks() {
		return tuioClient.getTuioCursorList().size();
	}

	private int GetX(int trackID) {
		if (trackID >= tuioClient.getTuioCursorList().size()) {
			return INVALID;
		}
		TuioCursor tc = tuioClient.getTuioCursorList().get(trackID);
		return tc.getScreenX(DeepSpaceConstants.WINDOW_WIDTH);
	}

	private int GetY(int trackID) {
		if (trackID >= tuioClient.getTuioCursorList().size()) {
			return INVALID;
		}
		TuioCursor tc = tuioClient.getTuioCursorList().get(trackID);
		return tc.getScreenY(DeepSpaceConstants.FLOOR_HEIGHT);
	}

	public ArrayList<PVector> getPlayerPositions() {
		playerPositions.clear();
		for (int trackID = 0; trackID < GetNumTracks(); trackID++) {
			playerPositions.add(new PVector(GetX(trackID), GetY(trackID)));
		}
		return playerPositions;
	}

	public ArrayList<Player> getPlayers() {
		players.clear();
		for (int trackID = 0; trackID < GetNumTracks(); trackID++) {
			players.add(new Player(GetX(trackID), GetY(trackID), trackID));
		}
		return players;
	}
}
