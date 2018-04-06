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
		if(! plowed){
			plowed = true;
			return;
		}
		if(vegetable == null) {
			plantVegetable();
			return;
		}
		vegetable.interact();
	}

	private void plantVegetable(){
		Random rnd = new Random();
		if(rnd.nextBoolean() == true)
			vegetable = new Cabage(getPosition());
		else vegetable = new Tomato(getPosition());
	}




}
