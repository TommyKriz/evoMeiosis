package evoMeiosis.trees;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import evoMeiosis.EvoMeiosisConstants;
import evoMeiosis.audio.Audio;
import evoMeiosis.logic.Attractor;
import evoMeiosis.logic.FADtriple;
import evoMeiosis.seeds.Seed;
import evoMeiosis.seeds.SeedSystem;
import processing.core.PGraphics;

public class Tree2 extends Attractor{

	  private static final float TOTAL_KINETIC_ENERGY_DEFAULT = Float.MAX_VALUE;
	  public static final float SPRING_CONSTANT_DEFAULT       = 0.1f;
	  public static final float COULOMB_CONSTANT_DEFAULT      = 100.0f;
	  public static final float DAMPING_COEFFICIENT_DEFAULT   = 0.1f;
	  public static final float TIME_STEP_DEFAULT             = 1.0f;

  public CopyOnWriteArrayList<Node> nodes;
  private float totalKineticEnergy;
  private float springConstant;
  private float coulombConstant;
  private float dampingCoefficient;
  private float timeStep;
  

  private Node lockedNode;

  public int x;
  public int y;
  private int idCounter = 1;
  private int treeID;
private float timeSinceLastSeedAdded;
	private long lastUpdateTime = System.currentTimeMillis();
	private long timeSinceLastSeedSpawn = 0;
	SeedSystem s;
	Audio a;
  
  public Tree2(int x, int y, int id, SeedSystem s, Audio a){
	super(x, y, EvoMeiosisConstants.INIT_TREE_SIZE);
	this.x = x;
	this.y = y;
    this.nodes = new CopyOnWriteArrayList<Node>();
    this.totalKineticEnergy = TOTAL_KINETIC_ENERGY_DEFAULT;
    this.springConstant = SPRING_CONSTANT_DEFAULT;
    this.coulombConstant = COULOMB_CONSTANT_DEFAULT;
    this.dampingCoefficient = DAMPING_COEFFICIENT_DEFAULT;
    this.timeStep = TIME_STEP_DEFAULT;
    this.radius = EvoMeiosisConstants.INIT_TREE_SIZE;
    this.lockedNode = null;
    this.treeID = id;
    timeSinceLastSeedAdded = Float.MAX_VALUE;
    this.s = s;
    this.a = a;
    
    //add locked root node
    float randMass = (float)(Math.random()*3)+1;
    Node root = new Node(0, randMass);
    root.set(x, y, randMass);
    root.color = new int[] {0,0,255};
    root.s = new Seed(s);
    nodes.add(root);
    lockedNode = root;
    
  }
  
  public int getRandomID() {
	  int randomID = (int) (Math.random() * nodes.size());
	  System.out.println(randomID);
	  return nodes.get(randomID).getID();
  }
  
  public boolean addIsAllowed() {
	  //System.out.println(timeSinceLastSeedAdded);
	  //System.out.println("kinetic E " + this.totalKineticEnergy);
	  if(this.timeSinceLastSeedAdded > EvoMeiosisConstants.TREE_SEED_TIMEINTERVAL && this.totalKineticEnergy < EvoMeiosisConstants.TREE_ENERGY_THRESH) {
		  timeSinceLastSeedAdded = 0;
		  return true;
	  }else {
		  return false;
	  }
		
	}
  
  public void spawnSeed() {
	  a.agentsSpawning(s.freeSeeds.size());
	  Node n = nodes.get((int) Math.random()*nodes.size());
	  Seed se = new Seed(s);
	  se.x = (int) n.getX();
	  se.y = (int) n.getY();
	  s.freeSeeds.add(se);
  }
  
  public void addSeed(Seed s) {
	  float addE = s.getEnergy();
	  System.out.println("Added Seed with Energy: " + addE);
	  Node n = new Node(idCounter, addE/3);
	  n.s = s;
	  n.color = s.getParticleHSLcolor();
	  this.nodes.add(n);
	  
	  this.addEdge(getRandomID(), idCounter, (float) (s.getFrequency()/((Math.random()*30)+30)));
	  
	  initializeNodeLocations();
	  radius += s.getEnergy();
	  idCounter++;
	  
  }

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
    
    float minXBound = x-radius;
    float maxXBound = x+radius;
    float minYBound = y-radius;
    float maxYBound = y+radius;
    
    for(int i = 0; i < this.nodes.size(); i++){
      Node node = this.nodes.get(i);
      if(!node.isInitialized) {
    	  float x = (float) (Math.random() * (maxXBound - minXBound) + minXBound);
          float y = (float) (Math.random() * (maxYBound - minYBound) + minYBound);
          float d = node.getMass() * nodeSizeRatio;
          node.set(x, y, d);
          node.isInitialized = true;
      }
    	  
    }
  }

  public void draw(PGraphics canvas){
	coulombConstant = (float) (700 + 500 * Math.cos(System.currentTimeMillis()/1000));
    this.totalKineticEnergy = this.calculateTotalKineticEnergy();
    
    long timeSinceLastUpdate = System.currentTimeMillis() - lastUpdateTime;
    lastUpdateTime = System.currentTimeMillis();
    
    //System.out.println(timeSinceLastUpdate);
    timeSinceLastSeedAdded += timeSinceLastUpdate;
    
    timeSinceLastSeedSpawn += timeSinceLastUpdate;
    
    if(timeSinceLastSeedSpawn > 500*nodes.size()) {
    	spawnSeed();
    	timeSinceLastSeedSpawn = 0;
    }
    
    
    
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
        float blend = (float) (Math.cos(System.currentTimeMillis()/1000)+1)/2;
        canvas.stroke(blend*node1.color[0] + (1-blend)*node2.color[0], 
        		blend*node1.color[1] + (1-blend)*node2.color[1], 
        		blend*node1.color[2] + (1-blend)*node2.color[2]);
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

          if(Float.isNaN(coulombForceX)) {
        	  coulombForceX = 0;
          }
          if(Float.isNaN(coulombForceY)) {
        	  coulombForceY = 0;
          }
          
          
          //System.out.println("coulombForceX: " + coulombForceX + " coulombForceY " + coulombForceY);
          
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

        
        
        if(Float.isNaN(springForceX)) {
        	springForceX = 0;
        }
        
        if(Float.isNaN(springForceY)) {
        	springForceY = 0;
        }
        
        //System.out.println("springForceX: " + springForceX + " springForceY " + springForceY);
        
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
      
      if(Float.isNaN(velocityX)) {
    	  velocityX = 0;
        }
        if(Float.isNaN(velocityY)) {
        	velocityY = 0;
        }

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
      
      //System.out.println("Vx: " + velocityX + " Vy " + velocityY);

      totalKineticEnergy += target.getMass() * Math.sqrt(velocityX * velocityX + velocityY * velocityY) / 2.0f;
      //System.out.println("Tree " + treeID + " E: " + this.totalKineticEnergy);
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