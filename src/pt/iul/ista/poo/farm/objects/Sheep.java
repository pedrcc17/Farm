package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;

public class Sheep extends Animal {

	private int cyclesSinceEaten;
	private boolean starving;

	public Sheep(Point2D p) {
		super(p);
		cyclesSinceEaten = 0;
	}

	public Sheep(Point2D p, int cyclesSinceEaten, boolean starving) {
		super(p);
		this.cyclesSinceEaten = cyclesSinceEaten;
		this.starving = starving;
	}

	@Override
	public void interact() {
		cyclesSinceEaten = 0;
		starving = false;
	}

	@Override
	public void incrementCycle() {
		cyclesSinceEaten++;
		if (cyclesSinceEaten < 10)
			addPoints();
		if (cyclesSinceEaten > 10 && cyclesSinceEaten < 20)
			moveAndEat();
		if (cyclesSinceEaten > 20)
			starving = true;
	}

	@Override
	public boolean canEatVegetable(Point2D newPosition) {
		if (Farm.getInstance().getObjectByPosition(newPosition) instanceof Vegetable)
			return true;
		return false;
	}

	@Override
	public int getPoints() {
		return 1;
	}

	@Override
	public String getName() {
		if (starving)
			return "famished_sheep";
		return "sheep";
	}

	@Override
	public String toString() {
		return "Sheep " + getPosition().getX() + ";" + getPosition().getY() + " " + cyclesSinceEaten + " " + starving
				+ " ";
	}

}
