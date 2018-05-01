package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Cabage extends Vegetable {

	public Cabage(Point2D position) {
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
		if(isRotten()) return "bad_cabage";
		if(isRipe()) return "cabage";
		if(getCyclesToRipe()<7) return "small_cabage";
		return "planted";
	}
	
	
	
	
}
