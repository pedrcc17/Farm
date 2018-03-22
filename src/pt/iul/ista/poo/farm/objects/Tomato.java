package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Tomato extends Vegetable {
	
	public Tomato(Point2D position) {
		super(position, "tomato", 2);
		rottenThreshold = 25;
		ripeThreshold = 15;
	}
	

}
