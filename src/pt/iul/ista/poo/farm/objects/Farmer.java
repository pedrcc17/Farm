package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Farmer extends FarmObject {

	public Farmer(Point2D p) {
		super(p);
	}

	public Point2D moveTo(Direction b) {
		if (b == Direction.UP) {
			Point2D nova = getPosition().plus((Direction.UP).asVector());
			if (ImageMatrixGUI.getInstance().isWithinBounds(nova) == true) {
				return setPosition(nova);
			}
		}
		if (b == Direction.DOWN) {
			Point2D nova = getPosition().plus((Direction.DOWN).asVector());
			if (ImageMatrixGUI.getInstance().isWithinBounds(nova) == true) {
				return setPosition(nova);
			}
		}
		if (b == Direction.RIGHT) {
			Point2D nova = getPosition().plus((Direction.RIGHT).asVector());
			if (ImageMatrixGUI.getInstance().isWithinBounds(nova) == true) {
				return setPosition(nova);
			}
		}
		if (b == Direction.LEFT) {
			Point2D nova = getPosition().plus((Direction.LEFT).asVector());
			if (ImageMatrixGUI.getInstance().isWithinBounds(nova) == true) {
				return setPosition(nova);
			}
		}
		return null;

	}

}

