package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;
public class Farmer extends FarmObject {
	
	private String name;
	
	public Farmer(Point2D p) {
		super(p);
		name = "farmer";
	}

}
