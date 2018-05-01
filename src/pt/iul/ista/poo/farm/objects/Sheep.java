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
			startMoving();
			eatVegetable();
		}
		if(cyclesSinceEaten > 20) 
			starving = true;
	}

	public int getCyclesSinceEaten() {
		return cyclesSinceEaten;
	}

	//TODO nao funciona
	private void eatVegetable(){
		if(Farm.getInstance().isVegetableInGivenPosition(this.getPosition()))
			Farm.getInstance().removeVegetable(getPosition());
	}

	private void startMoving(){
		Random rnd = new Random();
		int rand = rnd.nextInt(4);
		Point2D newPosition = null;
		switch (rand) {
		case 0:
			newPosition = getPosition().plus((Direction.UP).asVector());
			break;
		case 1:
			newPosition = getPosition().plus((Direction.DOWN).asVector());
			break;
		case 2: 
			newPosition = getPosition().plus((Direction.RIGHT).asVector());
			break;
		case 3:
			newPosition = getPosition().plus((Direction.LEFT).asVector());
			break;
		}
		if (ImageMatrixGUI.getInstance().isWithinBounds(newPosition) == false) {
			return;
		}
		setPosition(newPosition);		
	}



	public boolean isStarving() {
		return starving;
	}


	@Override
	public String getName(){
		if(starving) return "famished_sheep";
		return "sheep";
	}


}
