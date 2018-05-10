package pt.iul.ista.poo.farm.objects;

import java.util.Random;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Egg extends FarmObject implements Updatable, Interactable, Scorable {

	private int cycleCount;

	public Egg(Point2D p){
		super(p);
		cycleCount = 0;
	}

	@Override
	public void interact(){
		addPoints();
		removeEgg();
	}

	//galinha nasce mas ovo fica
	@Override
	public void incrementCycle(){
		cycleCount++;
		if(cycleCount == 20){
			hatchChicken();
			removeEgg();
		}
	}


	private void addPoints(){
		Farm.getInstance().addPoints(getPoints());
	}

	private void removeEgg(){
		Farm.getInstance().removeObject(getPosition());
	}

	private void hatchChicken(){
		Chicken newChicken = new Chicken(chickenSpawnPosition());
		Farm.getInstance().addImageToList(newChicken);
	}

	//TODO usar esta funcao ?  pode-se fazer o egg extender o animal ?
	private Point2D chickenSpawnPosition(){
		Random rnd = new Random();
		int rand = rnd.nextInt(4);
		Point2D newPosition = null;
		switch (rand) {
		case 0:
			newPosition = getPosition().plus((Direction.UP).asVector());
			break;
		case 1:
			newPosition = getPosition().plus((Direction.DOWN).asVector());
			break;
		case 2: 
			newPosition = getPosition().plus((Direction.RIGHT).asVector());
			break;
		case 3:
			newPosition = getPosition().plus((Direction.LEFT).asVector());
			break;
		}
		if (ImageMatrixGUI.getInstance().isWithinBounds(newPosition) == true)
			return newPosition;
		else return getPosition();
	}

	@Override
	public int getPoints(){
		return 1;
	}

	@Override
	public String getName(){
		return "egg";
	}

	@Override
	public int getLayer(){
		return 3;
	}
}
