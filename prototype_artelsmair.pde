
PFont font;


int initSeedsNum = 200;


int[][] freeSeedFieldColor;
boolean[] freeSeedFieldParticle;
int[][] treeFieldColor;


float fmax = 2000;
float fMin = 20;
float ampMax = 3;
float ampMin = 0.2;
float globalSpeed = 0.03;

float trailDecayRate = 10;

int catchRadius = 100;
int treeRadius = 50;
int newTreeRadius = 100;

ArrayList<Tree> globalTrees;
ArrayList<FreeSeed> freeSeeds;
ArrayList<Player> players;

void setup() {
  
  //init--------------------------------------
  
  size(1000, 700);
  pgTrails = createGraphics(1000, 700);
  pgPlayerAndParticles = createGraphics(1000, 700);
  pgTrees = createGraphics(1000, 700);
  
  freeSeedFieldColor = new int[fieldWidth * fieldHeight][3];
  freeSeedFieldParticle = new boolean[fieldWidth * fieldHeight];
  treeFieldColor = new int[fieldWidth * fieldHeight][3];
   
  globalTrees = new ArrayList<Tree>();
  freeSeeds = new ArrayList<FreeSeed>();
  players = new ArrayList<Player>();
  
  players.add(new Player(0, 0, "Player1"));
  players.add(new Player(1000, 0, "Player2"));
     
  pgTrails.beginDraw();
  pgTrails.background(0);
  pgTrails.endDraw();
  
  pgTrees.beginDraw();
  pgTrees.background(0, 0, 0, 0);
  pgTrees.endDraw();
  
  frameRate(60);
  
  
  
  //------------------------------------------------------
  //add free collectable seeds to playfield
  for(int i = 0; i< initSeedsNum; i++){
   freeSeeds.add(new FreeSeed());
  }
  
}


void draw() {
  
  println(frameRate);
  
  //player handling prototype---------------------------------------
  if (keyPressed) {
    if (key == 'a') {
      players.get(0).xOrig = mouseX;
      players.get(0).yOrig = mouseY;
    }else if( key == 's'){
      players.get(1).xOrig = mouseX;
      players.get(1).yOrig = mouseY;
    }
  } else {
    
  }
  
  //check for tree spawning------------------------------------------------
  if(distance( players.get(0).xOrig,  players.get(0).yOrig,  players.get(1).xOrig,  players.get(1).yOrig) < catchRadius){
    int mx = (players.get(0).xOrig + players.get(1).xOrig)/2;
    int my = (players.get(0).yOrig + players.get(1).yOrig)/2;
    //check if tree already exists in this area!
    boolean treeAlreadyThere = false;
    for(Tree t : globalTrees){
      if(distance(mx, my, t.originX, t.originY) < newTreeRadius){
        treeAlreadyThere = true;
        break;
      }
    }
    if(!treeAlreadyThere){
      globalTrees.add(new Tree(mx, my));
    }
    
  }
 


  //update cycle-------------------------------------------------------------
    
  
  collectFreeSeeds();
  
  
  //-------------------------------------------
  renderFreeSeeds();
  image(pgTrails, 0, 0); 
  
  //---------------------------------------------
  renderTrees();
  image(pgTrees, 0, 0); 
  
  
  //---------------------------------------------
  //updatePlayers(); doesnt work for some reason
  pgPlayerAndParticles.beginDraw();
    pgPlayerAndParticles.colorMode(HSB, 255);
    pgPlayerAndParticles.fill(150, 100, 100, 80);
    pgPlayerAndParticles.stroke(255,0,255,150);
    pgPlayerAndParticles.ellipse(players.get(0).xOrig, players.get(0).yOrig, catchRadius, catchRadius);
    
    pgPlayerAndParticles.fill(0, 100, 100, 80);
    pgPlayerAndParticles.stroke(255,0,255,150);
    pgPlayerAndParticles.ellipse(players.get(1).xOrig, players.get(1).yOrig, catchRadius, catchRadius);
    pgPlayerAndParticles.endDraw();
  image(pgPlayerAndParticles, 0, 0);
  
}


/*
void mouseReleased() {
  players.get(0).release();
}*/


//-----------------------------------------------------------------------------
void collectFreeSeeds(){
  for(int f=0; f< freeSeeds.size(); f++){
    //add to player if in range
    FreeSeed s = freeSeeds.get(f);
    if(!s.collected){
      for(Player p : players){
        float dist = distance(p.xOrig, p.yOrig, s.x, s.y);
        if(dist < catchRadius){
          s.attr = p;
          s.collected = true;
          p.seeds.add(s);
        }
      }
    }else{
      for(Tree t : globalTrees){
        //add to tree if in range
        float dist = distance(t.originX, t.originY, s.x, s.y);
        if(dist <= treeRadius){
          s.attr = t;
          t.addSeed(s);
          s.destroy();
        }
      }
      
    }
  }
}


void updatePlayers(){
  
  for(Player p : players){
    p.update();
    //println(p.name + " update");
    pgPlayerAndParticles.beginDraw();
    pgPlayerAndParticles.colorMode(HSB, 255);
    pgPlayerAndParticles.fill(150, 100, 100, 80);
    pgPlayerAndParticles.stroke(255,0,255,150);
    pgPlayerAndParticles.ellipse(p.xOrig, p.yOrig, catchRadius, catchRadius);
    pgPlayerAndParticles.endDraw();
    
  }
  
}

void renderFreeSeeds(){
  for(int f=0; f< freeSeeds.size(); f++){
    freeSeeds.get(f).update();
  }
  
  pgTrails.loadPixels();
  //draw free seedsField
  for(int s = 0; s< freeSeedFieldParticle.length; s++){
    //println("before " + brightness(pg.pixels[s]));
      if(freeSeedFieldParticle[s]){
        //colorMode(HSB, 255);
        //particle
        int[] c = freeSeedFieldColor[s];
        
        pgTrails.pixels[s] = color(c[0], c[1], c[2]);
        
        int x = s% fieldWidth;
        int y = (s-x) / fieldWidth;
        pgTrails.stroke(255);
        pgTrails.fill(color(c[0], c[1], c[2]));
        pgTrails.ellipse(x, y, 10, 10);
      }else if(red(pgTrails.pixels[s]) > 0 || green(pgTrails.pixels[s]) > 0 || blue(pgTrails.pixels[s]) > 0){
        //trail
        colorMode(RGB, 255);
        pgTrails.pixels[s] = color(max(0, red(pgTrails.pixels[s]) - trailDecayRate), 
                             max(0, green(pgTrails.pixels[s]) - trailDecayRate), 
                             max(0, blue(pgTrails.pixels[s]) - trailDecayRate));
        colorMode(HSB, 255);
      }/*else{
        pg.pixels[s] = color(0, 0, 0);
      }*/
      //println("after " + brightness(pg.pixels[s]));
  }
  pgTrails.updatePixels();
  
  pgPlayerAndParticles.beginDraw();
  pgPlayerAndParticles.background(0, 0, 0, 0);
  
  for(int s = 0; s< freeSeedFieldParticle.length; s++){
    //println("before " + brightness(pg.pixels[s]));
      if(freeSeedFieldParticle[s]){
        //colorMode(HSB, 255);
        //particle
        int[] c = freeSeedFieldColor[s];     
        int x = s% fieldWidth;
        int y = (s-x) / fieldWidth;
        pgPlayerAndParticles.noStroke();
        pgPlayerAndParticles.fill(color(0, 0, 255, 150));
        pgPlayerAndParticles.ellipse(x, y, 2, 2);
        pgPlayerAndParticles.fill(color(c[0], c[1], c[2], 150));
        pgPlayerAndParticles.ellipse(x, y, 4, 4);
        pgPlayerAndParticles.fill(color(c[0], c[1], c[2], 80));
        pgPlayerAndParticles.ellipse(x, y, 8, 8);
        pgPlayerAndParticles.fill(color(c[0], c[1], c[2], 20));
        pgPlayerAndParticles.ellipse(x, y, 16, 16);
        pgPlayerAndParticles.fill(color(0, 0, 255, 20));
        pgPlayerAndParticles.ellipse(x, y, 18, 18);
      }
  }
  pgPlayerAndParticles.endDraw();
  
}



void renderTrees(){
  
  for(Tree t : globalTrees){
    t.update();
    pgPlayerAndParticles.beginDraw();
      pgPlayerAndParticles.colorMode(HSB, 255);
      pgPlayerAndParticles.fill(60, 100, 100, 40);
      pgPlayerAndParticles.noStroke();
      pgPlayerAndParticles.ellipse(t.originX, t.originY, treeRadius, treeRadius);
      pgPlayerAndParticles.fill(70, 100, 100, 30);
      pgPlayerAndParticles.noStroke();
      pgPlayerAndParticles.ellipse(t.originX, t.originY, newTreeRadius, newTreeRadius);
      pgPlayerAndParticles.endDraw();
      pgPlayerAndParticles.colorMode(RGB, 255);
  }
  
  pgTrees.beginDraw();
  pgTrees.loadPixels();
  colorMode(HSB, 255);
  for(int i=0; i<treeFieldColor.length; i++){
    int alpha = 0;
    if(treeFieldColor[i][0] != 0 || treeFieldColor[i][1] != 0 ||treeFieldColor[i][2] != 0){
      alpha = 150 + treeFieldColor[i][2];
    }
    pgTrees.pixels[i] = color(treeFieldColor[i][0], treeFieldColor[i][1], treeFieldColor[i][2], alpha);
  }
  pgTrees.updatePixels();
  
  pgTrees.endDraw();
}








//---------------------------------------------------------------------------------------------------

class FADtriple
{
  float frequency; 
  float amplitude; 
  float dampingCoefficient;
  float phase1;
  float phase2;
  
  FADtriple(float ampScale, float freqScale) {
    frequency = freqScale * random(fMin, fmax);
    amplitude = ampScale * random(ampMin, ampMax);
    dampingCoefficient = random(0.1, 0.5);
    phase1 = random(-1, 1);
    phase2 = random(-1, 1);
  }


  void setFAD(float f, float a, float d) {
    frequency = f;
    amplitude = a;
    dampingCoefficient = d;
  }
  
  float getXOffset(float i){
    return amplitude * cos(i * frequency + phase1); //* (float) ((millis()%1000)/1000) * t.dampingCoefficient;
  }
  
  float getYOffset(float i){
    return amplitude * sin(i * frequency + phase2); //* (float) ((millis()%1000)/1000) * t.dampingCoefficient;
  }
}


class Attractor{
  String type = "point";
  float intensity = 0.1;
  int radius;
  int xOrig;
  int yOrig;
  
  Attractor(int x, int y, int radius){
    this.xOrig = x;
    this.yOrig = y;
    this.radius = radius;
  }
  
  float getXAttraction(float x){
    return (xOrig - x) * intensity;
  }
  
  float getYAttraction(float y){
    return (yOrig - y) * intensity;
  }
  
}


class Player extends Attractor{
  String name;
  ArrayList<FreeSeed> seeds;
  
  Player(int x, int y, String name){
    super(x, y, catchRadius);
    this.name = name;
    seeds = new ArrayList<FreeSeed>();
  }
  
  void update(int x, int y){
    this.xOrig = x;
    this.yOrig = y;
  }
  
  void update(){
    if(name == "Mouse Player"){
      this.xOrig = mouseX;
    this.yOrig = mouseY;
    }else{
      println("Not Implemented yet!");
    }
  }
  
  //release all Seeds
  void release(){
    for(FreeSeed s : seeds){
      s.free();
    }
  }
  
  
}

class FreeSeed
{
  int x, y;
  float timeSinceLastUpdate = 0;
  ArrayList<FADtriple> FADs = new ArrayList<FADtriple>();
  boolean collected = false;
  boolean inTree = false;
  boolean xIncr = false;
  boolean yIncr = false;
  float speed = 1;
  Attractor attr;
  float flyAwayTime = 1000;
  boolean releaseStart = false;

  FreeSeed() {
    //println("new free seed");
    reset();
    FADs.add(new FADtriple(0.1, 0.1));
    FADs.add(new FADtriple(0.5, 0.5));
    FADs.add(new FADtriple(1, 1));
    speed = random(0.5, 1);
  }
  
  FreeSeed(int n) {
    //println("new free seed");
    reset();
    speed = random(0.5, 1);
  }
  
  FreeSeed(FADtriple t) {
    //println("new free seed");
    reset();
    FADs.add(t);
    speed = random(0.5, 1);
  }
  
  void addFAD(FADtriple t){
    FADs.add(t);
  }

  void free(){
    if(attr != null){
      attr.intensity = -attr.intensity;
      releaseStart = true;
    }
  }
  
  void destroy(){
    freeSeedFieldParticle[x + y * fieldWidth] = false;
    freeSeeds.remove(this);
  }

  void reset() {
    // keep choosing random spots until an empty one is found
    do {
      x = floor(random(fieldWidth));
      y = floor(random(fieldHeight));
      
    } while (!isEmpty(x, y));
  }
  
  boolean isEmpty(int x, int y){
    //println("new pos" + x + " " + y);
    return !freeSeedFieldParticle[y * fieldWidth + x];
    //return freeSeedFieldColor[y * fieldWidth + x] == new int[] {0, 0, 0};
  }

  void idxInBounds(){
    if((x + y * fieldWidth) < 0 || (x + y * fieldWidth) > (fieldWidth * fieldHeight -1)){
      reset();
    }
  }
  
  int[] getParticleFADcolor(){
    float c = 0;
    float a = 0;
    
    for(FADtriple t : FADs){
      c += t.frequency - fMin;
      a += t.amplitude - ampMin;
    }
    
    c = c/FADs.size();
    a = a/FADs.size();
    
    c = (c*255) / (fmax - fMin);
    a = (a *255) / (ampMax - ampMin);
    
    //print("a: " + a + " ");
    //print("c:" + c + " ");
    
    colorMode(HSB, 255);
    
    return new int[]{ (int) c , (int) a, 255};
    
    ///return new int[]{0, 255, 255};
  }

  void update() {
    
    timeSinceLastUpdate = millis() - timeSinceLastUpdate;
    
    //delete from old pos
    if(releaseStart && collected){
      flyAwayTime -= timeSinceLastUpdate; //<>//
      if(flyAwayTime <= 0){
        collected = false;
        releaseStart = false;
        attr.intensity = -attr.intensity;
        attr = null;
        println("released");
      }
    }
    
    freeSeedFieldParticle[x + y * fieldWidth] = false;

     //calc new pos
    for (int i=0; i<FADs.size(); i++) {
      FADtriple t = FADs.get(i);
      
      float addX = t.getXOffset(globalSpeed * speed * millis()/10000);
      float addY = t.getYOffset(globalSpeed * speed * millis()/10000);
      
      x += round(addX);
      y += round(addY);
    }
    
    if(attr != null){
      x += attr.getXAttraction(x);
      y += attr.getYAttraction(y);
    }    
    
    //check if in bounds, otherwise respawn
    idxInBounds();
    //set new pos
    freeSeedFieldParticle[x + y * fieldWidth] = true;
    freeSeedFieldColor[x + y * fieldWidth] = getParticleFADcolor();
   
  }
}





class Tree extends Attractor{
  int originX, originY;
  float aggregatedGrowthRadius;
  ArrayList<TreeParticle> treeParticles;
  PGraphics pgT;
  int seedConversionFactor = 1000;
  int seedSpawnRate = 100;
  int stuckCnt = 0;

  Tree(int originPosX, int originPosY) {
    super(originPosX, originPosY, treeRadius);
    originX = originPosX;
    originY = originPosY;
    aggregatedGrowthRadius = 200;
    //add seed at the middle
    treeParticles = new ArrayList<TreeParticle>();
    growFundament(1);
  }

  void growFundament(int size){
    for(int i=0; i<size; i++){
      for(int j=0; j<size; j++){
        setTreeColorField(originX + i-(size/2), originY + j-(size/2), 255, 255, 255);
      }
    }
  }

  //add seeds and convert them to TreeParticles so they can be used to grow the tree
  void addSeeds(FreeSeed[] seedsToBeAdded) {
    for (int i=0; i<seedsToBeAdded.length; i++) {
      treeParticles.add(new TreeParticle(seedsToBeAdded[i], this));
    }
  }
  
  void addSeed(FreeSeed seed) {
    for(int i=0; i< seedConversionFactor; i++){
      treeParticles.add(new TreeParticle(seed, this));
    }
  }
  
  void spawnSeed(int x, int y){
    
    FreeSeed fs = new FreeSeed(0);
    float a=0, f=0, d=0;
    for(TreeParticle t : treeParticles){
      for(FADtriple fad : t.FADs){
        a += fad.amplitude;
        f += fad.frequency;
        d += fad.dampingCoefficient;
      }
      a /= t.FADs.size();
      f /= t.FADs.size();
      d /= t.FADs.size();
      
      FADtriple fadt = new FADtriple(1, 1);
      fadt.setFAD(f, a, d);
      fs.addFAD(fadt);
    } 
    fs.x = x;
    fs.y = y;
    freeSeeds.add(fs);
  }

  //grow a bit
  void update() {
    //println("tree @ " + originY + "/" + originY + " has " + treeParticles.size() + " particles");
    for (int i=0; i<treeParticles.size(); i++) {
      if(!treeParticles.get(i).stuck && treeParticles.get(i).update()){ //<>//
        stuckCnt++;
      }
        
      if(stuckCnt >= seedSpawnRate){
        stuckCnt =0;
        //TODO
        //spawnSeed((int) (originX + random(newTreeRadius, aggregatedGrowthRadius)), (int) (originY + random(newTreeRadius, aggregatedGrowthRadius)));
      }
    }
  }
}




class TreeParticle {
  int x, y;
  boolean stuck = false;
  ArrayList<FADtriple> FADs = new ArrayList<FADtriple>();
  Tree assignedTree;
  float speed = 1;

  TreeParticle(FreeSeed s, Tree t) {
    FADs = s.FADs;
    assignedTree = t;
    s.collected = false;
    s.inTree = true;
    reset();
  }

  void addFAD(FADtriple t) {
    FADs.add(t);
  }

  void reset() {
    // keep choosing random spots until an empty one is found
    do {
      x = round(random(assignedTree.originX - assignedTree.aggregatedGrowthRadius/2, assignedTree.originX + assignedTree.aggregatedGrowthRadius/2));
      y = round(random(assignedTree.originY - assignedTree.aggregatedGrowthRadius/2, assignedTree.originY + assignedTree.aggregatedGrowthRadius/2));
      if(x < 0)
        x = 1;
      else if(x > fieldWidth)
        x = fieldWidth-2;

      if(y < 0)
         y = 1;
      else if(y > fieldHeight)
        y = fieldHeight-2;
      
    } while (isFullyGrownAt(y * fieldWidth + x));
  }
  
  int[] getParticleFADcolor(){
    float c = 0;
    float a = 0;
    
    for(FADtriple t : FADs){
      c += t.frequency - fMin;
      a += t.amplitude - ampMin;
    }
    
    c = c/FADs.size();
    a = a/FADs.size();
    
    c = (c*255) / (fmax - fMin);
    a = (a *255) / (ampMax - ampMin);
    
    //print("a: " + a + " ");
    //print("c:" + c + " ");
    
    colorMode(HSB, 255);
    
    return new int[]{ (int) c , (int) a*2, 255};
    
    ///return new int[]{0, 255, 255};
  }

  boolean update() {
    // move around
    if (!stuck) {
      
      //setTreeColorField(x, y, 0,0,0);

       //calc new pos
       
    for (int i=0; i<FADs.size(); i++) {
      FADtriple t = FADs.get(i);
      
      float addX = t.getXOffset(globalSpeed * speed * millis()/100);
      float addY = t.getYOffset(globalSpeed * speed * millis()/100);
      
      x += round(addX);
      y += round(addY);
    }
    
      //x += round(random(-1, 1));
      //y += round(random(-1, 1));
      
      //println("x: " +x + "y: " + y);

      if(distance(x, y, assignedTree.originX, assignedTree.originY) > assignedTree.aggregatedGrowthRadius || 
      x < 0 || y < 0 || x > (fieldWidth-1) || y > (fieldHeight-1)){
        reset();
        return false;
      }

      // test if something is next to us
      if (!alone()) {
        //println("stuck");
        stuck = true;
        int[] c = getParticleFADcolor();
        setTreeColorField(x, y, c[0], c[1], 255);
        assignedTree.aggregatedGrowthRadius += 1;
        return true;
      }else{
        
        //if(!isFullyGrownAt(x + y * fieldWidth)){
         // setTreeColorField(x, y, 120, 120, 120);
        //}
      }
    }
    return false;
  }

  // returns true if no neighboring pixels
  boolean alone() {
    
    int w = fieldWidth;
    int h = fieldHeight;

    int cx = x;
    int cy = y;

    // get positions
    int lx = cx-1;
    int rx = cx+1;
    int ty = cy-1;
    int by = cy+1;

    if (cx <= 0 || cx >= w || 
      lx <= 0 || lx >= w || 
      rx <= 0 || rx >= w || 
      cy <= 0 || cy >= h || 
      ty <= 0 || ty >= h || 
      by <= 0 || by >= h) return true;

    // pre multiply the ys
    cy *= w;
    by *= w;
    ty *= w;


    // N, W, E, S
    if (isFullyGrownAt(cx + ty) || 
      isFullyGrownAt(lx + cy)||
      isFullyGrownAt(rx + cy)||
      isFullyGrownAt(cx + by)) return false;

    // NW, NE, SW, SE

    if (isFullyGrownAt(lx + ty) || 
      isFullyGrownAt(lx + by) ||
      isFullyGrownAt(rx + ty) ||
      isFullyGrownAt(rx + by)) return false;


    return true;
  }
}





//helper methods----------------------------------------------------------
float distance(float x1, float y1, float x2, float y2){
  return sqrt(pow(x1-x2, 2) + pow(y1-y2,2));  
}

void setTreeColorField(int x, int y, int r, int g, int b){
  treeFieldColor[x + y* fieldWidth] = new int[]{r, g, b};
}

int[] getTreeFieldColor(int x, int y){
  return treeFieldColor[x + y* fieldWidth];
}

boolean isFullyGrownAt(int i){
  if(i >= fieldWidth * fieldHeight){
    return false;
  }
  int[] c = new int[]{255, 255, 255}; 
  return treeFieldColor[i][2] == c[2]; //<>//
}
