package pt.iul.ista.poo.farm.objects;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Vegetable extends FarmObject {
	
	protected Point2D position;
	protected String name;
	protected int layer;
	protected int ripeThreshold;
	protected int rottenThreshold;
	protected int cicle;
	

	public Vegetable(Point2D position, String name, int layer) {
		super(position);
		this.name = name;
		this.layer = layer;
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
	
	
}