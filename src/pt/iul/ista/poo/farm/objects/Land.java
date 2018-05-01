package pt.iul.ista.poo.farm.objects;

import java.util.Random;

import org.omg.CORBA.VersionSpecHelper;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public class Land extends FarmObject implements Interactable  {
	private boolean plowed;
	private boolean rocky;


	public Land(Point2D p) {
		super(p);
		plowed = false;
		rocky = rockRandomizer();
	}

	@Override
	public void interact(){
		if(!rocky) {
		if(plowed == false) plowed = true;
		else plantVegetable();
		}
	}
	
	private void plantVegetable(){
		Random rnd = new Random();
		Vegetable veg = null;
		if(rnd.nextBoolean() == true)
			veg = new Cabage(this.getPosition());
		else veg = new Tomato(this.getPosition());
		Farm.getInstance().addImageToList(veg);
		ImageMatrixGUI.getInstance().addImage((ImageTile)veg);
		ImageMatrixGUI.getInstance().update();
	}
	

	private boolean rockRandomizer(){
		Random rnd = new Random();
		if(rnd.nextInt(10) < 1)
			return true;
		return false;
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
//		if(rocky) return "rocky";
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
