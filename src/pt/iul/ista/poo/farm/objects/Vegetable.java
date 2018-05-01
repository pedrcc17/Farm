package pt.iul.ista.poo.farm.objects;
import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Vegetable extends FarmObject implements Interactable, Updatable {



	private int cyclesToRipe;
	private int cyclesToRot;


	//cada vegetal tera valores para amadurecer e apodrecer diferentes que serao incializados com ripe e rotten Threshold
	public Vegetable(Point2D position, int ripeThreshold, int rottenThreshold) {
		super(position);
		this.cyclesToRipe = ripeThreshold;
		this.cyclesToRot = rottenThreshold;
		//		this.cycleCount = 0;
	}


	
	
	public void removeVegetable(){
		//		if(vegetable.isRipe())  //add Points to farmer
		Farm.getInstance().removeVegetable(this.getPosition());
	}


	



	//ao incrementar ciclos, serao usados dois contadores, um para o amadurecimento e outro para apodrecimento (ripen e rot)

	//diminui os ciclos que faltam para apodrecer
	public void rot(){
		cyclesToRot--;
	}

	public boolean isRotten(){
		if(cyclesToRot <= 0) 
			return true;
		return false;
	}

	//diminui os ciclos que faltam para amadrecer
	public void ripen(int n){
		cyclesToRipe -= n;
	}

	public boolean isRipe(){
		if(cyclesToRipe <= 0)
			return true;
		return false;
	}


	public int getCyclesToRot() {
		return cyclesToRot;
	}


	public int getCyclesToRipe() {
		return cyclesToRipe;
	}




	@Override
	public int getLayer() {
		return 2;
	}


}