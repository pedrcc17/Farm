package pt.iul.ista.poo.farm;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import pt.iul.ista.poo.farm.objects.Animal;
import pt.iul.ista.poo.farm.objects.Cabage;
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

	private Farmer farmer;
	private Sheep sheep;


	private int max_x;
	private int max_y;

	private List<FarmObject> farmObjects;

	private Farm(int max_x, int max_y) {
		if (max_x < 5 || max_y < 5)
			throw new IllegalArgumentException();

		this.max_x = max_x;
		this.max_y = max_y;

		INSTANCE = this;

		farmObjects = new ArrayList<FarmObject>();


		ImageMatrixGUI.setSize(max_x, max_y);

		// Nao usar ImageMatrixGUI antes de inicializar o tamanho		
		// TODO

		loadScenario();

	}



	private Point2D randomPosition(){
		Random rnd = new Random();
		Point2D randomP = new Point2D(rnd.nextInt(max_x),rnd.nextInt(max_y));
		return randomP;
	}


	private void registerAll() {
		// TODO
		List<ImageTile> images = new ArrayList<ImageTile>();

		Point2D farmerInitialPosition = new Point2D (0,0);
		farmer = new Farmer (farmerInitialPosition);
		farmObjects.add(farmer);

		sheep = new Sheep(randomPosition());
		farmObjects.add(sheep);

		// Gravar os objectos Land na list de farmObjects
		for ( int x = 0; x < max_x; x++){
			for ( int y = 0;  y < max_y; y++){
				Point2D landPosition = new Point2D(x,y);
				Land land = new Land (landPosition);
				images.add(land);
				farmObjects.add(land);
			}
		}

		images.add(farmer);
		images.add(sheep);
		ImageMatrixGUI.getInstance().addImages(images);
		ImageMatrixGUI.getInstance().update();
	}



	private void loadScenario() {
		// TODO
		registerAll();
	}

	//incrementa os ciclos dos objectos que dependem dos ciclos de jogo
	private void incrementCycle(){
		for(FarmObject f : farmObjects){
			if(f instanceof Updatable)
				((Updatable) f).incrementCycle();
		}
	}

	//	 funcao retorna o objecto que devera ser interagido com base no layer
	//	 exemplo: caso haja Land e Vegetable numa mesma posicao, a funcao ira
	//	                    retornar o vegetable (pois tem maior layer)
	public Interactable getObjectByPosition(Point2D newPosition){
		//lista com o objetivo de guardar os objetos interactable com a mesma posicao
		List<FarmObject> samePositionObjects = new ArrayList<FarmObject>();
		int higherLayer = 0;
		for(FarmObject f : farmObjects){
			if(f instanceof Interactable){
				if(f.getPosition().equals(newPosition))   //caso o objecto tenha a mesma posicao 
					samePositionObjects.add(f);        //da que queremos, adiciona o objecto a lista
			}
		}
		for(int i = 0 ; i < samePositionObjects.size() ; i++){  //percorre a lista de objetos com a mesma position
			higherLayer = Math.max(higherLayer, samePositionObjects.get(i).getLayer()); //determina o maior layer
		}
		for(FarmObject fo : samePositionObjects){
			if(fo.getLayer() == higherLayer) return (Interactable) fo; //retorna o objeto com maior layer da lista
		}
		return null;
	}



	public void addImageToList(FarmObject farmObj){
		farmObjects.add(farmObj);
	}
	public void removeImageFromList(FarmObject farmObj){
		farmObjects.remove(farmObj);
	}


	public void removeVegetable(Point2D vegPosition){
		//		if(vegetable.isRipe())  //add Points to farmer
		Interactable veg = getObjectByPosition(vegPosition);
		farmObjects.remove(veg);
		ImageMatrixGUI.getInstance().removeImage((ImageTile) veg);
		unPlow(vegPosition);
		ImageMatrixGUI.getInstance().update();
	}

	private void unPlow(Point2D position ){
		Interactable land = getObjectByPosition(position);
		((Land) land).setPlowed(false);
	}

//	private void interact(Interactable i){
//		if(i instanceof Land){
//			if(! ((Land) i).isPlowed())
//				i.interact();
//			else 
//				plantVegetable(i);
//		}
//		if(i instanceof Vegetable){
//			if(((Vegetable) i).isRotten() || ((Vegetable)i).isRipe()){
//				removeVegetable(i);
//				unPlow(i);
//			} 
//
//			else i.interact();
//		}
//		if(i instanceof Animal){
//			i.interact();
//		}
//	}



	@Override
	public void update(Observable gui, Object a) {
 		System.out.println("Update sent " + a);
		// TODO
		if (a instanceof Integer) {
			//sempre que e clicada uma tecla lança o incrementCycle() definido acima
			incrementCycle();
			//if clicada uma tecla de direcao -> farmer move-se
			//if clicada tecla de espaço -> ativa a interacao
			//quando interacao tiver ativada, escolhe-se a direcao que se quer 
			//                   interagir(tecla direcao) -> farmer interage com land e nao se move
			int key = (Integer) a ;
			if (Direction.isDirection(key)) {
				System.out.println("Update is a Direction " + Direction.directionFor(key));
				if(farmer.isInteract()){
					Point2D newPosition = farmer.getPosition().plus(Direction.directionFor(key).asVector());
					Interactable i = getObjectByPosition(newPosition);
					i.interact();
					farmer.setInteract(false);
				}
				else farmer.moveTo(Direction.directionFor(key));
			} else if (key == 32) {
				farmer.setInteract(true);
			}
		}
		ImageMatrixGUI.getInstance().setStatusMessage("Points: ");
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
