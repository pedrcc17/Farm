package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Egg extends FarmObject implements Interactable, Updatable, Scorable {


	private int cycleCount;


	public Egg(Point2D p){
		super(p);
		cycleCount = 0;
	}

	public Egg(Point2D p, int cycleCount){
		super(p);
		this.cycleCount = cycleCount;
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
		Farm.getInstance().addObject(newChicken);
	}


	// se nao houver espaço adjacente disponivel para a galinha nascer,
	//	                            a galinha nasce na posicao do ovo 
	// Este caso nao esta especificado no enunciado
	private Point2D chickenSpawnPosition(){
		Point2D newPosition = null;
		Direction dir = null;
		dir = Direction.random();
		newPosition = getPosition().plus(dir.asVector());
		if(Farm.getInstance().canMove(newPosition))
			return newPosition;
		return getPosition();
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

	@Override
	public String toString() {
		return "Egg " + getPosition().getX() + ";" + getPosition().getY() + " " + cycleCount;
	}


}
