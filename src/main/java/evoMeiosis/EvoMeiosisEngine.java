package evoMeiosis;


import java.awt.geom.Point2D;
import java.util.ArrayList;

import deepSpace.DeepSpaceConstants;
import processing.core.PApplet;
import processing.core.PGraphics;
import evoMeiosis.audio.Audio;
import evoMeiosis.player.Player;
import evoMeiosis.player.PlayerSystem;
import evoMeiosis.seeds.Seed;
import evoMeiosis.seeds.SeedSystem;
import evoMeiosis.trees.Node;
import evoMeiosis.trees.Tree;
import evoMeiosis.trees.Tree2;
import evoMeiosis.trees.TreeSystem;
import evoMeiosis.trees.TreeSystemV2;

public class EvoMeiosisEngine {

	public PlayerSystem playerSystem;

	TreeSystemV2 treeSystem;

	SeedSystem seedSystem;
	
	ArrayList<Player> testPlayers = new ArrayList<Player>();
	
	Audio a;

	public EvoMeiosisEngine(
			deepSpace.tuio.DeepSpaceTUIOHelper deepSpaceTUIOHelper, Audio a) {
		playerSystem = new PlayerSystem(deepSpaceTUIOHelper);
		
		this.a = a;
		
		seedSystem = new SeedSystem();
		treeSystem = new TreeSystemV2(seedSystem, a);
		//
		
		//test players
		//testPlayers.add(new Player(100, 200, 123123));
		//testPlayers.add(new Player(300, 200, 123135));
	}
	

	public void update() {
		// tree update...

		playerSystem.update();
		collectFreeSeeds();
		createNewTrees();
		//createNewTreesTest();
	}

	void createNewTrees() {
		for (Player p1 : playerSystem.getActivePlayers()) {
			for (Player p2 : playerSystem.getActivePlayers()) {
				if(p1 != p2 && p1.collectedSeeds.size() > 0 && p2.collectedSeeds.size() > 0) {
					
					//2 players close enough
					if(Point2D.distance(p1.xOrig, p1.yOrig, p2.xOrig, p2.yOrig) < 
							(p1.radius +p2.radius - EvoMeiosisConstants.PLAYER_CATCH_RADIUS)) {
						//check if tree exists in range
						int mx = (int) ((p1.xOrig + p2.xOrig)/2);
						int my = (int) ((p1.yOrig + p2.yOrig)/2);
						boolean treeExists = false;
						for(Tree2 t : treeSystem.trees) {
							if(Point2D.distance(mx, my, t.x, t.y) < t.radius) {
								treeExists = true;
							}
							for(Node n : t.nodes) {
								if(Point2D.distance(mx, my, n.getX(), n.getY()) < n.getDiameter()) {
									treeExists = true;
								}
							}
						}
						if(!treeExists) {
							treeSystem.addTree(mx, my);
						}
					}
					
				}
			}
		}
	}
	

	
	void collectFreeSeeds() {

		for (int f = 0; f < seedSystem.freeSeeds.size(); f++) {
			// add to player if in range
			Seed s = seedSystem.freeSeeds.get(f);
			if (!s.collected) {
				for (Player p : playerSystem.getActivePlayers()) {
					float dist = (float) Point2D.distance(p.xOrig, p.yOrig, s.x, s.y);
					if (dist < EvoMeiosisConstants.PLAYER_CATCH_RADIUS) {
						s.attr = p;
						s.collected = true;
						s.collectedBy = p;
						p.collectedSeeds.add(s);
						p.radius += EvoMeiosisConstants.PLAYER_GROWTH_RATE;
						System.out.println("player " + p.id + " collected " + s.uniqueID);
					}
				}
				/*
				for (Player p : testPlayers) {
					float dist = (float) Point2D.distance(p.xOrig, p.yOrig, s.x, s.y);
					if (dist < EvoMeiosisConstants.PLAYER_CATCH_RADIUS) {
						s.attr = p;
						s.collected = true;
						s.collectedBy = p;
						p.collectedSeeds.add(s);
						p.radius += EvoMeiosisConstants.PLAYER_GROWTH_RATE;
						System.out.println("player " + p.id + " collected " + s.uniqueID);
					}
				}*/
			} else {
				for (Tree2 t : treeSystem.trees) {
					// add to tree if in range
					float dist = (float) Point2D.distance(t.x, t.y, s.x, s.y);
					if (dist <= t.radius) {
						if(t.addIsAllowed()) {
							s.collectedBy.radius -= EvoMeiosisConstants.PLAYER_GROWTH_RATE;
							t.addSeed(s);
							s.attr = t;
							s.collected = false;
							seedSystem.destroy(s);
						}
						
					}else {
						
						for(Node n : t.nodes) {
							dist = (float) Point2D.distance(n.getX(), n.getY(), s.x, s.y);
							if (dist <= n.getDiameter()*1.2) {
								if(t.addIsAllowed()) {
									s.collectedBy.radius -= EvoMeiosisConstants.PLAYER_GROWTH_RATE;
									t.addSeed(s);
									s.attr = t;
									s.collected = false;
									seedSystem.destroy(s);
								}
							}
						}
					}
				}
			}
		}
	}

	/*
	public void paintTestPlayers(PGraphics canvas, PApplet p) {
		
		if(p.mousePressed) {
			if(p.mouseButton == p.LEFT) {
				testPlayers.get(0).xOrig = p.mouseX;
				testPlayers.get(0).yOrig = p.mouseY-DeepSpaceConstants.WALL_HEIGHT;
			}
			if(p.mouseButton == p.RIGHT) {
				testPlayers.get(1).xOrig = p.mouseX;
				testPlayers.get(1).yOrig = p.mouseY-DeepSpaceConstants.WALL_HEIGHT;
			}
		}
		canvas.beginDraw();
		canvas.clear();
		canvas.background(0, 0, 0, 0);
		canvas.colorMode(PApplet.HSB, 255);
		canvas.fill(50, 200, 100, 80);
		canvas.stroke(255, 0, 255, 150);

		// canvas.ellipse(200, 400, 100, 100);

		for (Player pl : testPlayers) {
			canvas.ellipse(pl.xOrig, pl.yOrig,
					pl.radius,
					pl.radius);
		}

		canvas.endDraw();
	}
*/
	public void paintPlayers(PGraphics canvas) {
		playerSystem.paintPlayers(canvas);
	}

	public void paintSeeds(PGraphics canvas) {
		seedSystem.updateAndPaintSeeds(canvas);
	}
	
	public void paintTrails(PGraphics canvas) {
		seedSystem.paintTrailsV2(canvas);
		/*
		canvas.beginDraw();
		canvas.fill(250, 0, 0, 80);
		canvas.rect(0, 0, canvas.width, canvas.height);
		canvas.endDraw();*/
	}


	public void paintTrees(PGraphics canvas) {
		treeSystem.draw(canvas);
		
	}


	public void audioUpdate(Audio audio) {
		// TODO Auto-generated method stub
		
	}

}
