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
			veg = new Cabbage(this.getPosition());
		else veg = new Tomato(this.getPosition());
		Farm.getInstance().addImageToList(veg);
	}


	private boolean rockRandomizer(){
		Random rnd = new Random();
		if(rnd.nextInt(10) < 1)
			return true;
		return false;
	}


	public boolean isPlowed() {
		return plowed;
	}

	public void setPlowed(boolean b){
		this.plowed = b;
	}
	
	public void setRocky(boolean b){
		this.rocky = b;

	}

	public void addRock( Land a){
	
	
	
	}
	
	@Override
	public String getName(){
		if(rocky) return "rock";
		if(plowed == false) return "land";
		else return "plowed";
	}
	
	@Override
	public int getLayer(){
		return 1;
	}



}
