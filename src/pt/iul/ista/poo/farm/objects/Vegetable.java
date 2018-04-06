package pt.iul.ista.poo.farm.objects;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Vegetable extends FarmObject implements Interactable, Updatable {


	private String name;
	private int layer;
	private int ripeThreshold;
	private int rottenThreshold;
	private int cycleCount;
	private int cyclesToRipe;
	private int cyclesToRot;
	


	public Vegetable(Point2D position, String name, int layer, int ripeThreshold, int rottenThreshold) {
		super(position);
		this.name = name;
		this.layer = layer;
		this.ripeThreshold = ripeThreshold;
		this.cyclesToRipe = ripeThreshold;
		this.rottenThreshold = rottenThreshold;
		this.cyclesToRot = rottenThreshold;
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
	public int getLayer() {
		return layer;
	}
	
	
	
	@Override
	public void incrementCycle(){
		cycleCount++;
	}


	
	public void rot(){
		cyclesToRot--;
	}

	public boolean isRotten(){
		if(cyclesToRot <= 0) 
			return true;
		return false;
	}
	
	public void ripen(int n){
		cyclesToRipe -= n;
	}
	
	public boolean isRipe(){
		if(cyclesToRipe <= 0)
			return true;
		return false;
	}



	
	


}