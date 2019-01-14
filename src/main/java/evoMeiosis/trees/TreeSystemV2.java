package evoMeiosis.trees;

import java.util.ArrayList;

import evoMeiosis.audio.Audio;
import evoMeiosis.seeds.SeedSystem;
import processing.core.PGraphics;

public class TreeSystemV2 {

	
	public ArrayList<Tree2> trees;
	int idCounter = 0;
	SeedSystem s;
	Audio a;
	
	public TreeSystemV2(SeedSystem s, Audio a){
		this.s = s;
		this.a = a;
		trees = new ArrayList<Tree2>(); 
		
		//t = createTestTree();
	  //t.initializeNodeLocations();
	  
	}

	public void draw(PGraphics canvas){
		canvas.beginDraw();
		canvas.fill(0, 0, 0);
		canvas.clear();
		canvas.background(0,0,0,0);
	  
		for(Tree2 t : trees) {
			t.draw(canvas);
		}
		//t.draw(canvas);
		
	  canvas.endDraw();
	  
	  //createTestTree();
	}

	/*
	void mouseMoved(){
	  if(forceDirectedGraph.isIntersectingWith(mouseX, mouseY))
	    forceDirectedGraph.onMouseMovedAt(mouseX, mouseY);
	}
	void mousePressed(){
	  if(forceDirectedGraph.isIntersectingWith(mouseX, mouseY))
	    forceDirectedGraph.onMousePressedAt(mouseX, mouseY);
	  else if(controlPanel.isIntersectingWith(mouseX, mouseY))
	    controlPanel.onMousePressedAt(mouseX, mouseY);
	}
	void mouseDragged(){
	  if(forceDirectedGraph.isIntersectingWith(mouseX, mouseY))
	    forceDirectedGraph.onMouseDraggedTo(mouseX, mouseY);
	  else if(controlPanel.isIntersectingWith(mouseX, mouseY))
	    controlPanel.onMouseDraggedTo(mouseX, mouseY);
	}
	
	void mouseReleased(){
	  if(forceDirectedGraph.isIntersectingWith(mouseX, mouseY))
	    forceDirectedGraph.onMouseReleased();
	}*/
/*
	Tree2 createTestTree(){
		//Tree2 t = new Tree2(400, 200);
		
		t.add(new Node(0, 1));
		t.add(new Node(1, 6));
		t.add(new Node(2, 2));
		t.add(new Node(3, 1));
		t.add(new Node(4, 1));
		
		t.addEdge(0, 1, 1);
		t.addEdge(0, 2, 1);
		t.addEdge(0, 3, 1);
		t.addEdge(1, 4, 1);
		
	
	  return t;
	}*/

	public void addTree(int mx, int my) {
		a.newTreeCreated();
		trees.add(new Tree2(mx, my, idCounter, s, a));
		
		idCounter++;
	}
}
