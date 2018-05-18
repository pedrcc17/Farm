package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Cabbage extends Vegetable {

	@Override
	public String toString() {
		return "Cabbage" + " " + getPosition().getX() + ";" + getPosition().getY() + " " + getCyclesToRipe() + " " + getCyclesToRot();
	}
	public int Cycle;
	
	public void setCycle(int cycle) {
		Cycle = cycle;
	}

	public Cabbage(Point2D position) {
		super(position, 10, 30);
	}

	@Override
	public void incrementCycle(){
		ripen(1);
		rot();
	}

	
	//caso nao esteja podre, ao interagir, decrementa em 2 os ciclos restantes para amadurecer
	@Override
	public void interact(){
		if(isRotten() || isRipe())
			removeVegetable();
		else
			ripen(1);
	}



	
	
	@Override
	public String getName(){
		if(isRotten()) return "bad_cabbage";
		if(isRipe()) return "cabbage";
		return "small_cabbage";
	}
	
	@Override
	public int getPoints(){
		return 2;
	}
	
	
	
}
