package pt.iul.ista.poo.farm.objects;

import java.util.Random;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Sheep extends Animal {

	private int cyclesSinceEaten;
	private boolean starving;

	public Sheep(Point2D p){
		super(p);
		cyclesSinceEaten = 0;
	}


	@Override
	public void interact(){
		cyclesSinceEaten = 0;
		starving = false;
	}

	@Override
	public void incrementCycle(){
		cyclesSinceEaten++;
		if(cyclesSinceEaten > 10 && cyclesSinceEaten < 20){
			moveAndEat();
		}
		if(cyclesSinceEaten > 20) 
			starving = true;

		if(cyclesSinceEaten < 10)
			addPoints();
	}


	@Override   
	public boolean canEatVegetable(Point2D newPosition){
		if(Farm.getInstance().isVegetableInGivenPosition(newPosition)) 
			return true;
		return false;
	}



	public int getCyclesSinceEaten() {
		return cyclesSinceEaten;
	}

	public boolean isStarving() {
		return starving;
	}


	@Override
	public int getPoints(){
		return 1;
	}

	@Override
	public String getName(){
		if(starving) return "famished_sheep";
		return "sheep";
	}


}
