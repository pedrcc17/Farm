package pt.iul.ista.poo.farm.objects;



import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Animal extends FarmObject implements Interactable, Updatable, Scorable {


	public Animal(Point2D p){
		super(p);
	}


	
	public abstract boolean canEatVegetable(Point2D pos);

	
	public void moveAndEat(){
		Point2D newPosition = null;
		Direction dir = null;
		dir = Direction.random();
		newPosition = getPosition().plus(dir.asVector());
		if(!(Farm.getInstance().canMove(newPosition)))
			return;
		if(canEatVegetable(newPosition))
			Farm.getInstance().removeObject(newPosition);
		else 
			setPosition(newPosition);
	}
	

	
	public void addPoints(){
		Farm.getInstance().addPoints(getPoints());
	}

	

	@Override
	public int getLayer(){
		return 3;
	}




}
