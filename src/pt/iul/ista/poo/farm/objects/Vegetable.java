package pt.iul.ista.poo.farm.objects;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Vegetable extends FarmObject implements Interactable, Updatable {

	private Point2D position;
	private String name;
	private int layer;
	private int ripeThreshold;
	private int rottenThreshold;
	private int cycleCount;
	private int ripeCount;
	private int rottenCount;
	


	public Vegetable(Point2D position, String name, int layer, int ripeThreshold, int rottenThreshold) {
		super(position);
		this.name = name;
		this.layer = layer;
		this.ripeThreshold = ripeThreshold;
		this.ripeCount = ripeThreshold;
		this.rottenThreshold = rottenThreshold;
		this.rottenCount = rottenThreshold;
	}


	@Override
	public String getName() {
		return name;
	}

	@Override
	public Point2D getPosition() {
		return position;
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
		rottenCount--;
	}

	public boolean isRotten(){
		if(rottenCount <= 0) 
			return true;
		return false;
	}
	
	public void ripen(int n){
		ripeCount -= n;
	}
	
	public boolean isRipe(){
		if(ripeCount <= 0)
			return true;
		return false;
	}



	
	


}