package pt.iul.ista.poo.farm.objects;

import java.util.Random;

import org.omg.CORBA.VersionSpecHelper;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public class Land extends FarmObject implements Interactable  {
	private String name;
	private boolean plowed;


	public Land(Point2D p) {
		super(p);
//		name = "land";
		plowed = false;
	}

	@Override
	public void interact(){
//		name = "plowed";
		plowed = true;
	}


//		if(!(vegetable.isRotten() || vegetable.isRipe()))
//		vegetable.interact();
//		else removeVegetable();


//	}

//	private void plantVegetable(){
//		Random rnd = new Random();
//		if(rnd.nextBoolean() == true)
//			vegetable = new Cabage(getPosition());
//		else vegetable = new Tomato(getPosition());
//		ImageMatrixGUI.getInstance().addImage(vegetable);
//		ImageMatrixGUI.getInstance().update();
//	}



@Override
public String getName(){
	if(plowed == false) return "land";
	else return "plowed";
}



public boolean isPlowed() {
	return plowed;
}

public void setPlowed(boolean b){
	this.plowed = b;
}

@Override
public int getLayer(){
	return 1;
}




}
