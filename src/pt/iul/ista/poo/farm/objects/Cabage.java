package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Cabage extends Vegetable {

	public Cabage(Point2D position) {
		super(position, "cabage", 2, 10, 30);
	}
	
	@Override
	public void incrementCycle(){
		super.incrementCycle();
		super.ripen(1);
		super.rot();
		
	}
	
	@Override
	public void interact(){
		if(! super.isRotten())
		super.ripen(3);
	}

	
	
}
