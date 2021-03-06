package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public class Chicken extends Animal {

	

	private int cycleCount;



	public Chicken(Point2D p){
		super(p);
		cycleCount = 0;
	}
	
	
	public Chicken(Point2D p, int cycleCount){
		super(p);
		this.cycleCount = cycleCount;
	}
	


	@Override
	public void interact(){
		removeChicken();
	}


	@Override
	public void incrementCycle(){
		cycleCount++;
		if(cycleCount % 2 == 0)
			moveAndEat();
		if(cycleCount % 10 == 0)
			layEgg();
	}

	@Override
	public boolean canEatVegetable(Point2D newPosition){
		if(Farm.getInstance().getObjectByPosition(newPosition) instanceof Tomato)
			return true;
		return false;
	}


	private void layEgg(){
		Egg egg = new Egg(getPosition());
		Farm.getInstance().addObject(egg);
	}

	private void removeChicken(){
		Farm.getInstance().removeObject(getPosition());
		addPoints();
	}


	@Override
	public int getPoints(){
		return 2;
	}

	@Override
	public String getName(){
		return "chicken";
	}
	
	@Override
	public String toString() {
		return "Chicken " + getPosition().getX() + ";" + getPosition().getY() + " " + cycleCount;
	}


}
