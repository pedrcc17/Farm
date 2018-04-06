package pt.iul.ista.poo.farm;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import pt.iul.ista.poo.farm.objects.Farmer;
import pt.iul.ista.poo.farm.objects.Land;
import pt.iul.ista.poo.farm.objects.Tomato;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Vector2D;

public class Farm implements Observer {

	private Land[][] landMatrix; 
	
	private static final String SAVE_FNAME = "config/savedGame";

	private static final int MIN_X = 5;
	private static final int MIN_Y = 5;

	private static Farm INSTANCE  = null;

	private Farmer farmer;


	private int max_x;
	private int max_y;

	private Farm(int max_x, int max_y) {
		if (max_x < 5 || max_y < 5)
			throw new IllegalArgumentException();

		this.max_x = max_x;
		this.max_y = max_y;

		INSTANCE = this;
		
		landMatrix = new Land[max_x][max_y];


		ImageMatrixGUI.setSize(max_x, max_y);

		// N�o usar ImageMatrixGUI antes de inicializar o tamanho		
		// TODO

		loadScenario();

	}

	private void registerAll() {
		// TODO
		List<ImageTile> images = new ArrayList<ImageTile>();

		Point2D pInicial = new Point2D (0,0);
		farmer = new Farmer (pInicial);
		// Gravar os objectos Land numa matriz
		for ( int x = 0; x < max_x; x++){
			for ( int y = 0;  y < max_y; y++){
				Point2D landPosition = new Point2D(x,y);
				Land land = new Land (landPosition);
				images.add(land);
				landMatrix[x][y] = land;
				}
		}

		images.add(farmer);
		ImageMatrixGUI.getInstance().addImages(images);
		ImageMatrixGUI.getInstance().update();
	}



	private void loadScenario() {
		// TODO
		registerAll();
	}

	@Override
	public void update(Observable gui, Object a) {
		System.out.println("Update sent " + a);
		// TODO
		if (a instanceof Integer) {
			int key = (Integer) a ;
			if (Direction.isDirection(key)) {
				System.out.println("Update is a Direction " + Direction.directionFor(key));
				farmer.moveTo(Direction.directionFor(key));
				if(farmer.isInteract()){
					Land land = landMatrix[farmer.getPosition().getX()][farmer.getPosition().getY()];
					land.interact();
					ImageMatrixGUI.getInstance().addImage(land);
					ImageMatrixGUI.getInstance().update();
					farmer.setInteract(false);
				}
			} else if (key == 32) {
				farmer.setInteract(true);
			}
		}
		ImageMatrixGUI.getInstance().setStatusMessage("Points: ");
		ImageMatrixGUI.getInstance().update();
	}

	// N�o precisa de alterar nada a partir deste ponto
	private void play() {
		ImageMatrixGUI.getInstance().addObserver(this);
		ImageMatrixGUI.getInstance().go();
	}

	public static Farm getInstance() {
		assert (INSTANCE != null);
		return INSTANCE;
	}
	public static void main(String[] args) {
		Farm f = new Farm(5, 7);
		f.play();
	}

}
