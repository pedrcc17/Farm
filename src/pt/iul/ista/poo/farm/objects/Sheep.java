package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Sheep extends Animal {
	
	private int cyclesSinceEaten;
	private boolean isStarving;
	
	public Sheep(Point2D p){
		super(p);
		cyclesSinceEaten = 0;
	}
	
	
	@Override
	public void interact(){
		cyclesSinceEaten = 0;
	}
	
	@Override
	public void incrementCycle(){
		cyclesSinceEaten++;
		if(cyclesSinceEaten > 10)
			startMoving();
		if(cyclesSinceEaten > 20) 
			isStarving = true;
	}
	
	public int getCyclesSinceEaten() {
		return cyclesSinceEaten;
	}
	
	
	//TODO 
	private void startMoving(){
	}



	public boolean isStarving() {
		if(cyclesSinceEaten > 10) return true;
		return false;
	}
	
	
	@Override
	public String getName(){
		if(! isStarving()) return "sheep";
		else return "famished_sheep";
	}
	

}
