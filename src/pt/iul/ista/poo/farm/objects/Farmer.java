package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Farmer extends FarmObject {
	private boolean interact;

	public Farmer(Point2D p) {
		super(p);
	}

	public void moveTo(Direction b) {
		Point2D nova = null;
		switch (b) {
		case UP:
			nova = getPosition().plus((Direction.UP).asVector());
			break;
		case DOWN:
			nova = getPosition().plus((Direction.DOWN).asVector());
			break;
		case RIGHT: 
			nova = getPosition().plus((Direction.RIGHT).asVector());
			break;
		case LEFT:
			nova = getPosition().plus((Direction.LEFT).asVector());
			break;
		}

		if (ImageMatrixGUI.getInstance().isWithinBounds(nova) == false) {
			return;
		};
		setPosition(nova);
		if (interact == true) {
			
		}
	}

	public void setInteract(boolean interact) {
		this.interact = interact;
	}


}

