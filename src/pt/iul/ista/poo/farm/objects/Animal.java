package pt.iul.ista.poo.farm.objects;

import java.util.Random;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Animal extends FarmObject implements Interactable, Updatable, Scorable {



	public Animal(Point2D p){
		super(p);
	}

	


	public void addPoints(){
		Farm.getInstance().addPoints(getPoints());
	}


	public abstract boolean canEatVegetable(Point2D pos);

	public void moveAndEat(){
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
		if (ImageMatrixGUI.getInstance().isWithinBounds(newPosition) == false) {
			return;
		}
		if(canEatVegetable(newPosition))
			Farm.getInstance().removeVegetable(newPosition);
			else 
				setPosition(newPosition);
	
		
		
		
		
		//TODO nao funciona - ovelha continua a fazer o movimento quando come vegetal
//		eatVegetable(newPosition);
//		if(eatVegetable(newPosition) == false){     //Se e so se o vegetal nao for comido, é que executa o movimento
//			setPosition(newPosition);	
			
//			System.out.println(eatVegetable(newPosition));
//		}
	}
	



	@Override
	public int getLayer(){
		return 3;
	}




}
