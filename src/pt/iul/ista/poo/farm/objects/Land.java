package pt.iul.ista.poo.farm.objects;

import java.util.Random;

import org.omg.CORBA.VersionSpecHelper;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Land extends FarmObject implements Interactable  {
	private String name;
	private boolean plowed;
	private Vegetable vegetable;


	public Land(Point2D p) {
		super(p);
		name = "land";
		plowed = false;
	}

	@Override
	public void interact(){
		if(vegetable == null) {
			if(! plowed){
				name = "plowed";
				plowed = true;
				return;
			}
			plantVegetable();
			return;
		}
		if(!(vegetable.isRotten() || vegetable.isRipe()))
		vegetable.interact();
		else removeVegetable();


	}

	private void plantVegetable(){
		Random rnd = new Random();
		if(rnd.nextBoolean() == true)
			vegetable = new Cabage(getPosition());
		else vegetable = new Tomato(getPosition());
		ImageMatrixGUI.getInstance().addImage(vegetable);
		ImageMatrixGUI.getInstance().update();
	}

	private void removeVegetable(){
		//		if(vegetable.isRipe())  //add Points to farmer
		ImageMatrixGUI.getInstance().removeImage(vegetable);
		ImageMatrixGUI.getInstance().update();
		vegetable = null;
		plowed = false;
		name = "land";
	}

	@Override
	public String getName(){
		return name;
	}


	//incrementa ciclo de vegetais (plantados)
	public void incrementCycle(){
		if(vegetable == null) return;
		vegetable.incrementCycle();
	}
	//	@Override
	//	public int getLayer(){
	//		return 0;
	//	}




}
