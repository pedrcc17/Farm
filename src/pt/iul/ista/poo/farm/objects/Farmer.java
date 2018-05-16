package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Farmer extends FarmObject {

	private boolean interact;

	public Farmer(Point2D p) {
		super(p);
	}

	public void moveTo(Direction b) {
		Point2D newPosition = null;
		switch (b) {
		case UP:
			newPosition = getPosition().plus((Direction.UP).asVector());
			break;
		case DOWN:
			newPosition = getPosition().plus((Direction.DOWN).asVector());
			break;
		case RIGHT: 
			newPosition = getPosition().plus((Direction.RIGHT).asVector());
			break;
		case LEFT:
			newPosition = getPosition().plus((Direction.LEFT).asVector());
			break;
		}

		if (ImageMatrixGUI.getInstance().isWithinBounds(newPosition) == false) {
			return;
		};
		
		if(!(Farm.getInstance().canMove(newPosition)))
			return;
		
		setPosition(newPosition);		
	}


	public void setInteract(boolean interact) {
		this.interact = interact;
	}

	public boolean isInteract(){
		return interact;
	}

	@Override
	public int getLayer(){
		return 4;
	}
}

