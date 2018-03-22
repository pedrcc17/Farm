package pt.iul.ista.poo.farm.objects;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Vegetable extends FarmObject implements Interactable {
	
	protected Point2D position;
	protected String name;
	protected int level;
	protected int ripeThreshold;
	protected int rottenThreshold;
	

	public Vegetable(Point2D position, String name, int level) {
		super(position);
		this.name = name;
		this.level = level;
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
		return level;
	}
	
	
	@Override
	public void interact(){
		
	}
	
	
}