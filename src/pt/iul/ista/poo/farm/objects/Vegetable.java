package pt.iul.ista.poo.farm.objects;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Vegetable implements ImageTile {
	protected Point2D position;
	protected String name;
	protected int level;

	public Vegetable(Point2D position, String name, int level) {
		super();
		this.position = position;
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
}