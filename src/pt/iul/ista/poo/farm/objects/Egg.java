package pt.iul.ista.poo.farm.objects;

import java.util.Random;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Egg extends FarmObject implements Interactable, Updatable, Scorable {

	@Override
	public String toString() {
		return "Egg " + getPosition().getX() + ";" + getPosition().getY() + " " + cycleCount;
	}

	private int cycleCount;


	public Egg(Point2D p){
		super(p);
		cycleCount = 0;
	}
	
	public void setCycleCount(int cycleCount) {
		this.cycleCount = cycleCount;
	}

	@Override
	public void interact(){
		removeEgg();
	}

	@Override
	public void incrementCycle(){
		cycleCount++;
		if(cycleCount == 20){
			hatchChicken();
		}
	}



	private void removeEgg(){
		Farm.getInstance().removeObject(getPosition());
		addPoints();
	}

	private void addPoints(){
		Farm.getInstance().addPoints(getPoints());
	}


	private void hatchChicken(){
		Farm.getInstance().removeObject(getPosition());
		Chicken newChicken = new Chicken(chickenSpawnPosition());
		Farm.getInstance().addImageToList(newChicken);
}

//TODO igual no animal ( moveandeat() ) se a posicao nao for uma que se possa mexer, ele precisa encontrar outra - fazer um while ? abaixo
	//o que e suposto acontecer se o sitio onde ira nascer a galinha estiver com objectos incompativeis ?
	
private Point2D chickenSpawnPosition(){
	Point2D newPosition = null;
	Direction dir = null;
	dir = Direction.random();
	newPosition = getPosition().plus(dir.asVector());
	if(Farm.getInstance().canMove(newPosition))
		return newPosition;
	//		while(! Farm.getInstance().canMove(newPosition)){
	//			dir = Direction.random();
	//			newPosition = getPosition().plus(dir.asVector());
	//		}
	//		if (Farm.getInstance().canMove(newPosition))
	//			return newPosition;
	return getPosition();
}
//			Random rnd = new Random();
//			int rand = rnd.nextInt(4);
//			Point2D newPosition = null;
//			switch (rand) {
//			case 0:
//				newPosition = getPosition().plus((Direction.UP).asVector());
//				break;
//			case 1:
//				newPosition = getPosition().plus((Direction.DOWN).asVector());
//				break;
//			case 2: 
//				newPosition = getPosition().plus((Direction.RIGHT).asVector());
//				break;
//			case 3:
//				newPosition = getPosition().plus((Direction.LEFT).asVector());
//				break;
//			}
//			if (ImageMatrixGUI.getInstance().isWithinBounds(newPosition) == true)
//				return newPosition;
//			if(! Farm.getInstance().canMove(newPosition))
//				return null;
//			else return getPosition();
//		}

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
