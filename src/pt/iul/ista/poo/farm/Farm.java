package pt.iul.ista.poo.farm;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.text.Position;

import pt.iul.ista.poo.farm.objects.Animal;
import pt.iul.ista.poo.farm.objects.Cabbage;
import pt.iul.ista.poo.farm.objects.Chicken;
import pt.iul.ista.poo.farm.objects.FarmObject;
import pt.iul.ista.poo.farm.objects.Farmer;
import pt.iul.ista.poo.farm.objects.Interactable;
import pt.iul.ista.poo.farm.objects.Land;
import pt.iul.ista.poo.farm.objects.Sheep;
import pt.iul.ista.poo.farm.objects.Tomato;
import pt.iul.ista.poo.farm.objects.Updatable;
import pt.iul.ista.poo.farm.objects.Vegetable;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Farm implements Observer {

	private Land[][] landMatrix; 

	private static final String SAVE_FNAME = "config/savedGame";

	private static final int MIN_X = 5;
	private static final int MIN_Y = 5;

	private static Farm INSTANCE  = null;
	
	private int points;

	private Farmer farmer;


	private int max_x;
	private int max_y;

	private List<FarmObject> farmObjects;

	private Farm(int max_x, int max_y) {
		if (max_x < 5 || max_y < 5)
			throw new IllegalArgumentException();

		this.max_x = max_x;
		this.max_y = max_y;

		INSTANCE = this;
		
		this.points = 0 ;

		farmObjects = new ArrayList<FarmObject>();
//		objectToRemove = new ArrayList<FarmObject>();


		ImageMatrixGUI.setSize(max_x, max_y);

		// Nao usar ImageMatrixGUI antes de inicializar o tamanho		
		// TODO

		loadScenario();

	}



	private Point2D randomPosition(){
		Random rnd = new Random();
		Point2D randomPos = new Point2D(rnd.nextInt(max_x),rnd.nextInt(max_y));
		return randomPos;
	}


	private void registerAll() {
		// TODO
		List<ImageTile> images = new ArrayList<ImageTile>();

		Point2D farmerInitialPosition = new Point2D (0,0);
		farmer = new Farmer (farmerInitialPosition);
		farmObjects.add(farmer);

//		sheep = new Sheep(randomPosition());
//		farmObjects.add(sheep);
		Sheep sheepOne = new Sheep(randomPosition());
		Sheep sheepTwo = new Sheep(randomPosition());
		farmObjects.add(sheepOne);
		farmObjects.add(sheepTwo);
		
		Chicken chickenOne = new Chicken(randomPosition());
		Chicken chickenTwo = new Chicken(randomPosition());
		farmObjects.add(chickenOne);
		farmObjects.add(chickenTwo);

		// Gravar os objectos Land na list de farmObjects
		for ( int x = 0; x < max_x; x++){
			for ( int y = 0;  y < max_y; y++){
				Point2D landPosition = new Point2D(x,y);
				Land land = new Land (landPosition);
				images.add(land);
				farmObjects.add(land);
			}
		}
		images.addAll(farmObjects);
		ImageMatrixGUI.getInstance().addImages(images);
		ImageMatrixGUI.getInstance().update();
	}



	private void loadScenario() {
		// TODO
		registerAll();
	}

	//incrementa os ciclos dos objectos que dependem dos ciclos de jogo
	private void incrementCycle(){
		// iterateFarmObjects para uma remocao segura
		List<FarmObject> iterateFarmObjects = new ArrayList<FarmObject>(farmObjects);
		for(FarmObject f : iterateFarmObjects){
			if(f instanceof Updatable)
				((Updatable) f).incrementCycle();
		}
	}
	
	//	 metodo que retorna o objecto que devera ser interagido com base no layer
	//	 exemplo: caso haja Land e Vegetable numa mesma posicao, a funcao ira
	//	                    retornar o vegetable (pois tem maior layer)
	public FarmObject getObjectByPosition(Point2D newPosition){
		//lista com o objetivo de guardar os objetos interactable com a mesma posicao
		List<FarmObject> samePositionObjects = new ArrayList<FarmObject>();
		int higherLayer = 0;
		for(FarmObject f : farmObjects){
//			if(f instanceof Interactable){
				if(f.getPosition().equals(newPosition))   //caso o objecto tenha a mesma posicao 
					samePositionObjects.add(f);        //da que queremos, adiciona o objecto a lista
			}
//		}
		for(int i = 0 ; i < samePositionObjects.size() ; i++){  //percorre a lista de objetos com a mesma position
			higherLayer = Math.max(higherLayer, samePositionObjects.get(i).getLayer()); //determina o maior layer
		}
		for(FarmObject fo : samePositionObjects){
			if(fo.getLayer() == higherLayer) return fo; //retorna o objeto com maior layer da lista
		}
		return null;
	}
	
	//TODO
	//ovelha ainda passa por cima de farmer
	public boolean canMove(Point2D pos){
		FarmObject i = getObjectByPosition(pos);
		if(! ImageMatrixGUI.getInstance().isWithinBounds(pos))
			return false;
		if( i instanceof Land )
			return true;
		if( i instanceof Vegetable )
			return true;
		return false;
			}


	public void addObject(FarmObject farmObj){
		farmObjects.add(farmObj);
		ImageMatrixGUI.getInstance().addImage(farmObj);
		ImageMatrixGUI.getInstance().update();
	}
	
	public void removeObjects(FarmObject farmObj){
		farmObjects.remove(farmObj);
		ImageMatrixGUI.getInstance().removeImage(farmObj);
		ImageMatrixGUI.getInstance().update();
	}
	

	public void addPoints(int numberOfPoints){
		points = points + numberOfPoints;
	}
	
	public void removeObject(Point2D objectPosition){
		FarmObject object = getObjectByPosition(objectPosition);
		farmObjects.remove(object);
		ImageMatrixGUI.getInstance().removeImage((ImageTile)object);
		ImageMatrixGUI.getInstance().update();			
		}
		

	public void removeVegetable(Point2D vegPosition){
		Vegetable veg = (Vegetable) getObjectByPosition(vegPosition);
		farmObjects.remove(veg);
		ImageMatrixGUI.getInstance().removeImage((ImageTile) veg);
		unPlow(vegPosition);
		ImageMatrixGUI.getInstance().update();
	}

	private void unPlow(Point2D position){
		Land land = (Land) getObjectByPosition(position);
		land.setPlowed(false);
	}



	@Override
	public void update(Observable gui, Object a) {
		System.out.println("Update sent " + a);
		// TODO
		if (a instanceof Integer) {
			//sempre que e clicada uma tecla lan�a o incrementCycle() definido acima
			//if clicada uma tecla de direcao -> farmer move-se
			//if clicada tecla de espa�o -> ativa a interacao
			//quando interacao tiver ativada, escolhe-se a direcao que se quer 
			//                   interagir(tecla direcao) -> farmer interage com land e nao se move
			int key = (Integer) a ;
			if (Direction.isDirection(key)) {
				//TODO
//				if(! ImageMatrixGUI.getInstance().isWithinBounds(Direction.isDirection(key)))
//						return;
//				incrementCycle();
				System.out.println("Update is a Direction " + Direction.directionFor(key));
				if(farmer.isInteract()){
					Point2D newPosition = farmer.getPosition().plus(Direction.directionFor(key).asVector());
					Interactable i = (Interactable) getObjectByPosition(newPosition);
					i.interact();
					farmer.setInteract(false);
				}
				else farmer.moveTo(Direction.directionFor(key));
				
				incrementCycle();
			} else if (key == 32) {
				farmer.setInteract(true);
			}
		}
		ImageMatrixGUI.getInstance().setStatusMessage("Points: " + points);
		ImageMatrixGUI.getInstance().update();
	}

	// Nao precisa de alterar nada a partir deste ponto
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
