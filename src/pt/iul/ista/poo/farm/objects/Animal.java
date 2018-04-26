package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public abstract class Animal extends FarmObject implements Interactable, Updatable {

	
	
	public Animal(Point2D p){
		super(p);
	}



	@Override
	public int getLayer(){
		return 3;
	}

	
	
	
	
	
	
	
	
}
