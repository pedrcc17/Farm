package pt.iul.ista.poo.farm.objects;

import java.util.Random;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;

public class Land extends FarmObject implements Interactable  {


	private boolean plowed;
	private boolean rocky;


	public Land(Point2D p) {
		super(p);
		plowed = false;
		rocky = rockRandomizer();
	}

	public Land(Point2D p, boolean plowed, boolean rocky) {
		super(p);
		this.plowed = plowed;
		this.rocky = rocky;
	}

	@Override
	public void interact(){
		if(!rocky) {
			if(plowed == false) plowed = true;
			else plantVegetable();
		}
	}

	private void plantVegetable(){
		Random rnd = new Random();
		Vegetable veg = null;
		if(rnd.nextBoolean() == true)
			veg = new Cabbage(this.getPosition());
		else veg = new Tomato(this.getPosition());
		Farm.getInstance().addObject(veg);
	}


	private boolean rockRandomizer(){
		Random rnd = new Random();
		if(rnd.nextInt(10) < 1)
			return true;
		return false;
	}


	public boolean isPlowed() {
		return plowed;
	}

	public void setPlowed(boolean b){
		this.plowed = b;
	}


	@Override
	public String getName(){
		if(rocky) return "rock";
		if(plowed == false) return "land";
		else return "plowed";
	}

	@Override
	public int getLayer(){
		return 1;
	}

	@Override
	public String toString() {
		return "Land "  + getPosition().getX() + ";" + getPosition().getY() + " " + plowed + " " + rocky;
	}

}
