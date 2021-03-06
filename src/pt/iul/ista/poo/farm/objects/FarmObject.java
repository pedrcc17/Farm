package pt.iul.ista.poo.farm.objects;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class FarmObject implements ImageTile {

	private Point2D position;
	
	public FarmObject(Point2D p) {
		position = p;
	}

	public Point2D setPosition(Point2D a){
		return position = a ;
	}
	

	
	@Override
	public String getName() {
		return getClass().getSimpleName().toLowerCase();
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 0;
	}

	
	
}
