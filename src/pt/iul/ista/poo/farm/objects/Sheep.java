package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Sheep extends Animal {
	
	private int cyclesSinceEaten;
	private boolean isStarving;
	
	public Sheep(Point2D p){
		super(p);
	}
	
	
	@Override
	public void interact(){
		setCyclesSinceEaten(0);
	}
	
	@Override
	public void incrementCycle(){
		cyclesSinceEaten++;
	}
	
	private int getCyclesSinceEaten() {
		return cyclesSinceEaten;
	}

	private void setCyclesSinceEaten(int cyclesSinceEaten) {
		this.cyclesSinceEaten = cyclesSinceEaten;
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
