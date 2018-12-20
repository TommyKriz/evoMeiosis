
List<Player>
List<Agent>
List<TreeSpawnOrigin>

fun loop(){

	updatePlayerInputPositions();
	updateAgentPositions();
	collisionPlayerAgents(); // player collects agents
	collisionBetweenPlayers();
	updateTreeSpawnLocations();
	
	drawPlayers();
	drawAgents();
	blendCanvas();
	
}

fun collisionBetweenPlayers(){
	// if player has enough agents
	// addTreeSpawnLocation(x,y)
}

fun updateTreeSpawnLocations(){
	if(reoccurence){
		incr++;
	} 
	if(incr){
		drawTree(x,y,incr);
	}
}

fun drawTree(x,y,i){
	if (i >= TREE_GROWTH_LIMIT) spawnAgents(x,y)
	else
	//draw
}