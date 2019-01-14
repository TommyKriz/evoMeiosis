package evoMeiosis.trees;

import java.util.ArrayList;

import evoMeiosis.EvoMeiosisConstants;
import evoMeiosis.logic.Attractor;
import evoMeiosis.seeds.Seed;
import processing.core.PGraphics;

public class Tree2 extends Attractor{

  private static final float TOTAL_KINETIC_ENERGY_DEFAULT = Float.MAX_VALUE;
  public static final float SPRING_CONSTANT_DEFAULT       = 0.3f;
  public static final float COULOMB_CONSTANT_DEFAULT      = 500.0f;
  public static final float DAMPING_COEFFICIENT_DEFAULT   = 0.2f;
  public static final float TIME_STEP_DEFAULT             = 1.5f;

  private ArrayList<Node> nodes;
  private float totalKineticEnergy;
  private float springConstant;
  private float coulombConstant;
  private float dampingCoefficient;
  private float timeStep;

  private Node lockedNode;

  public int x;
  public int y;
  private int idCounter = 0;
  
  public Tree2(int x, int y){
	super(x, y, EvoMeiosisConstants.INIT_TREE_SIZE);
	this.x = x;
	this.y = y;
    this.nodes = new ArrayList<Node>();
    this.totalKineticEnergy = TOTAL_KINETIC_ENERGY_DEFAULT;
    this.springConstant = SPRING_CONSTANT_DEFAULT;
    this.coulombConstant = COULOMB_CONSTANT_DEFAULT;
    this.dampingCoefficient = DAMPING_COEFFICIENT_DEFAULT;
    this.timeStep = TIME_STEP_DEFAULT;

    this.lockedNode = null;
  }
  
  public int getRandomID() {
	  return nodes.get((int)Math.random() * nodes.size()).getID();
  }
  /*
  public void addSeed(Seed s) {
	  Node n = new Node(idCounter, );
	  getRandomID()
	  this.nodes.add(node);
	  idCounter++;
  }*/

  public void add(Node node){
    this.nodes.add(node);
  }
  
  public void addEdge(int id1, int id2, float naturalSpringLength){
    Node node1 = this.getNodeWith(id1);
    Node node2 = this.getNodeWith(id2);
    node1.add(node2, naturalSpringLength);
    node2.add(node1, naturalSpringLength);
  }
  
  private Node getNodeWith(int id){
    Node node = null;
    for(int i = 0; i < this.nodes.size(); i++){
      Node target = this.nodes.get(i);
      if(target.getID() == id){
        node = target;
        break;
      }
    }
    return node;
  }

  public void initializeNodeLocations(){
    float maxMass = 0.0f;
    
    for(int i = 0; i < this.nodes.size(); i++){
      float mass = this.nodes.get(i).getMass();
      if(mass > maxMass)
        maxMass = mass;
    }
    
    float nodeSizeRatio = 5;
    
    float minXBound = x-EvoMeiosisConstants.INIT_TREE_SIZE;
    float maxXBound = x+EvoMeiosisConstants.INIT_TREE_SIZE;
    float minYBound = y-EvoMeiosisConstants.INIT_TREE_SIZE;
    float maxYBound = y+EvoMeiosisConstants.INIT_TREE_SIZE;
    
    for(int i = 0; i < this.nodes.size(); i++){
      Node node = this.nodes.get(i);
      float x = (float) (Math.random() * (maxXBound - minXBound) + minXBound);
      float y = (float) (Math.random() * (maxYBound - minYBound) + minYBound);
      float d = node.getMass() * nodeSizeRatio;
      node.set(x, y, d);
    }
  }

  public void draw(PGraphics canvas){
    this.totalKineticEnergy = this.calculateTotalKineticEnergy();

    canvas.fill(255, 0 ,0, 255);
    canvas.stroke(255);
    
    this.drawEdges(canvas);
    
    for(int i = 0; i < this.nodes.size(); i++)
      this.nodes.get(i).draw(canvas);

    //canvas.fill(0);

  }

  private void drawEdges(PGraphics canvas){
    //stroke(51, 51, 255);
    for(int i = 0; i < this.nodes.size(); i++){
      Node node1 = this.nodes.get(i);
      for(int j = 0; j < node1.getSizeOfAdjacents(); j++){
        Node node2 = node1.getAdjacentAt(j);
        canvas.line(node1.getX(), node1.getY(), node2.getX(), node2.getY());
      }
    }
  }

  private float calculateTotalKineticEnergy(){ //ToDo:check the calculation in terms of Math...
    for(int i = 0; i < this.nodes.size(); i++){
      Node target = this.nodes.get(i);
      if(target == this.lockedNode)
        continue;

      float forceX = 0.0f;
      float forceY = 0.0f;
      for(int j = 0; j < this.nodes.size(); j++){ //Coulomb's law
        Node node = this.nodes.get(j);
        if(node != target){
          float dx = target.getX() - node.getX();
          float dy = target.getY() - node.getY();
          float distance = (float) Math.sqrt(dx * dx + dy * dy);
          float xUnit = dx / distance;
          float yUnit = dy / distance;

          float coulombForceX = (float) (this.coulombConstant * (target.getMass() * node.getMass()) / Math.pow(distance, 2.0f) * xUnit);
          float coulombForceY = (float) (this.coulombConstant * (target.getMass() * node.getMass()) / Math.pow(distance, 2.0f) * yUnit);

          forceX += coulombForceX;
          forceY += coulombForceY;
        }
      }

      for(int j = 0; j < target.getSizeOfAdjacents(); j++){ //Hooke's law
        Node node = target.getAdjacentAt(j);
        float springLength = target.getNaturalSpringLengthAt(j);
        float dx = target.getX() - node.getX();
        float dy = target.getY() - node.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        float xUnit = dx / distance;
        float yUnit = dy / distance;

        float d = distance - springLength;

        float springForceX = -1 * this.springConstant * d * xUnit;
        float springForceY = -1 * this.springConstant * d * yUnit;

        forceX += springForceX;
        forceY += springForceY;
      }

      target.setForceToApply(forceX, forceY);
    }

    float totalKineticEnergy = 0.0f;
    for(int i = 0; i < this.nodes.size(); i++){
      Node target = this.nodes.get(i);
      if(target == this.lockedNode)
        continue;

      float forceX = target.getForceX();
      float forceY = target.getForceY();

      float accelerationX = forceX / target.getMass();
      float accelerationY = forceY / target.getMass();

      float velocityX = (target.getVelocityX() + this.timeStep * accelerationX) * this.dampingCoefficient;
      float velocityY = (target.getVelocityY() + this.timeStep * accelerationY) * this.dampingCoefficient;

      float x = (float) (target.getX() + this.timeStep * target.getVelocityX() + accelerationX * Math.pow(this.timeStep, 2.0f) / 2.0f);
      float y = (float) (target.getY() + this.timeStep * target.getVelocityY() + accelerationY * Math.pow(this.timeStep, 2.0f) / 2.0f);

      float radius = target.getDiameter() / 2.0f; //for boundary check
      
      /*
      if(x < this.getX() + radius)
        x = this.getX() + radius;
      else if(x > this.getX() + this.getWidth() - radius)
        x =  this.getX() + this.getWidth() - radius;
      if(y < this.getY() + radius)
        y = this.getY() + radius;
      else if(y > this.getY() + this.getHeight() - radius)
        y =  this.getX() + this.getHeight() - radius;
      
      */
      
      target.set(x, y);
      target.setVelocities(velocityX, velocityY);
      target.setForceToApply(0.0f, 0.0f);

      totalKineticEnergy += target.getMass() * Math.sqrt(velocityX * velocityX + velocityY * velocityY) / 2.0f;
    }
    return totalKineticEnergy;
  }

  /*
  public void onMouseMovedAt(int x, int y){
    for(int i = 0; i < this.nodes.size(); i++){
      Node node = this.nodes.get(i);
      if(node.isIntersectingWith(x, y))
        node.highlight();
      else
        node.dehighlight();
    }
  }
  */
  
  public void onMousePressedAt(int x, int y){
    for(int i = 0; i < this.nodes.size(); i++){
      Node node = this.nodes.get(i);
      if(node.isIntersectingWith(x, y)){
        this.lockedNode = node;
        this.lockedNode.setVelocities(0.0f, 0.0f);
        break;
      }
    }
  }
  /*
  public void onMouseDraggedTo(int x, int y){
    if(this.lockedNode != null){
      float radius = this.lockedNode.getDiameter() / 2.0f; //for boundary check
      if(x < this.getX() + radius)
        x = (int)(this.getX() + radius);
      else if(x > this.getX() + this.getWidth() - radius)
        x =  (int)(this.getX() + this.getWidth() - radius);
      if(y < this.getY() + radius)
        y = (int)(this.getY() + radius);
      else if(y > this.getY() + this.getHeight() - radius)
        y =  (int)(this.getX() + this.getHeight() - radius);

      this.lockedNode.set(x, y);
      this.lockedNode.setVelocities(0.0f, 0.0f);
    }
  }*/
  
  public void onMouseReleased(){
    this.lockedNode = null;
  }

  //@Override
  public void onSpringConstantChangedTo(float value){
    this.springConstant = value;
  }
  //@Override
  public void onCoulombConstantChangedTo(float value){
    this.coulombConstant = value;
  }
  //@Override
  public void onDampingCoefficientChangedTo(float value){
    this.dampingCoefficient = value;
  }
  //@Override
  public void onTimeStepChangedTo(float value){
    this.timeStep = value;
  }



}