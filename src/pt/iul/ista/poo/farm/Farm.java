package pt.iul.ista.poo.farm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Scanner;

import pt.iul.ista.poo.farm.objects.Cabbage;
import pt.iul.ista.poo.farm.objects.Chicken;
import pt.iul.ista.poo.farm.objects.Egg;
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

	private static Farm INSTANCE = null;

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

		this.points = 0;

		farmObjects = new ArrayList<FarmObject>();
		// objectToRemove = new ArrayList<FarmObject>();

		ImageMatrixGUI.setSize(max_x, max_y);

		// Nao usar ImageMatrixGUI antes de inicializar o tamanho
		// TODO

		loadScenario();

	}

	private Point2D randomPosition() {
		Random rnd = new Random();
		Point2D randomPos = new Point2D(rnd.nextInt(max_x), rnd.nextInt(max_y));
		return randomPos;
	}

	public void registerAll() {
		List<ImageTile> images = new ArrayList<ImageTile>();

		Point2D farmerInitialPosition = new Point2D(0, 0);
		farmer = new Farmer(farmerInitialPosition);
		farmObjects.add(farmer);

		Sheep sheepOne = new Sheep(randomPosition());
		Sheep sheepTwo = new Sheep(randomPosition());
		farmObjects.add(sheepOne);
		farmObjects.add(sheepTwo);

		Chicken chickenOne = new Chicken(randomPosition());
		Chicken chickenTwo = new Chicken(randomPosition());
		farmObjects.add(chickenOne);
		farmObjects.add(chickenTwo);

		// Gravar os objectos Land na list de farmObjects
		for (int x = 0; x < max_x; x++) {
			for (int y = 0; y < max_y; y++) {
				Point2D landPosition = new Point2D(x, y);
				Land land = new Land(landPosition);
				images.add(land);
				farmObjects.add(land);
			}
		}
		images.addAll(farmObjects);
		ImageMatrixGUI.getInstance().addImages(images);
		ImageMatrixGUI.getInstance().update();
	}

	public void setPoints(int p) {
		points = p;
	}

	public void loadScenario() {

		List<ImageTile> farmLoad = new ArrayList<ImageTile>();
		try {
			Scanner read = new Scanner(new File("FarmSave.txt"));
			String line = read.nextLine();
			String[] size = line.split(" ");
			ImageMatrixGUI.setSize(Integer.parseInt(size[0]), Integer.parseInt(size[1]));
			setPoints(read.nextInt());
			read.nextLine();
			while (read.hasNextLine()) {
				String Objects = read.nextLine();
				String[] obj = Objects.split(" ");
				farmObjects.add(getObject(obj));
				farmLoad.addAll(farmObjects);
				ImageMatrixGUI.getInstance().addImages(farmLoad);
				ImageMatrixGUI.getInstance().update();
			}

			read.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erro na abertura de Ficheiro de leitura");
			registerAll();

		}
	}

	public FarmObject getObject(String[] obj) {
		if (obj[0].equals("Land")) {
			String[] position = obj[1].split(";");
			Point2D point = new Point2D(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
			Land a = new Land(point);
			if (obj[2].equals("true"))
				a.setPlowed(true);
			if (obj[3].equals("true"))
				a.setRocky(true);
			return a;
		}

		if (obj[0].equals("Sheep")) {
			String[] position = obj[1].split(";");
			Point2D point = new Point2D(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
			Sheep a = new Sheep(point);
			if (obj[3].equals("true"))
				a.setStarving(true);
			a.setCyclesSinceEaten(Integer.parseInt(obj[2]));
			return a;
		}

		if (obj[0].equals("Chicken")) {
			String[] position = obj[1].split(";");
			Point2D point = new Point2D(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
			Chicken a = new Chicken(point);
			a.setCycleCount(Integer.parseInt(obj[2]));
			return a;
		}

		if (obj[0].equals("Egg")) {
			String[] position = obj[1].split(";");
			Point2D point = new Point2D(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
			Egg a = new Egg(point);
			a.setCycleCount(Integer.parseInt(obj[2]));
			return a;
		}

		if (obj[0].equals("Tomato")) {
			String[] position = obj[1].split(";");
			Point2D point = new Point2D(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
			FarmObject a = new Tomato(point);
			((Tomato) a).setCyclesAfterTakenCare(Integer.parseInt(obj[2]));
			((Vegetable) a).setCyclesToRipe(Integer.parseInt(obj[3]));
			((Vegetable) a).setCyclesToRot(Integer.parseInt(obj[4]));

			return a;
		}

		if (obj[0].equals("Cabbage")) {
			String[] position = obj[1].split(";");
			Point2D point = new Point2D(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
			FarmObject a = new Cabbage(point);
			((Vegetable) a).setCyclesToRipe(Integer.parseInt(obj[3]));
			((Vegetable) a).setCyclesToRot(Integer.parseInt(obj[4]));

			return a;
		} else {
			String[] position = obj[1].split(";");
			Point2D point = new Point2D(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
			Farmer a = new Farmer(point);
			if (obj[2].equals("true"))
				a.setInteract(true);
			return a;

		}
	}

	public void writeScenario() {

		try {
			PrintWriter write = new PrintWriter(new File("FarmSave.txt"));
			write.println(max_x + " " + max_y);
			write.println(points);
			for (FarmObject a : farmObjects) {
				write.println(a.toString());

			}
			write.close();

		} catch (FileNotFoundException e) {
			System.out.println("Erro de escrita no ficheiro leitura");
		}
	}

	// incrementa os ciclos dos objectos que dependem dos ciclos de jogo
	private void incrementCycle() {
		// iterateFarmObjects para uma remocao segura
		List<FarmObject> iterateFarmObjects = new ArrayList<FarmObject>(farmObjects);
		for (FarmObject f : iterateFarmObjects) {
			if (f instanceof Updatable)
				((Updatable) f).incrementCycle();
		}
	}

	// metodo que retorna o objecto que devera ser interagido com base no layer
	// exemplo: caso haja Land e Vegetable numa mesma posicao, a funcao ira
	// retornar o vegetable (pois tem maior layer)
	public Interactable getObjectByPosition(Point2D newPosition) {
		// lista com o objetivo de guardar os objetos interactable com a mesma
		// posicao
		List<FarmObject> samePositionObjects = new ArrayList<FarmObject>();
		int higherLayer = 0;
		for (FarmObject f : farmObjects) {
			if (f instanceof Interactable) {
				if (f.getPosition().equals(newPosition)) // caso o objecto tenha
															// a mesma posicao
					samePositionObjects.add(f); // da que queremos, adiciona o
												// objecto a lista
			}
		}
		for (int i = 0; i < samePositionObjects.size(); i++) { // percorre a
																// lista de
																// objetos com a
																// mesma
																// position
			higherLayer = Math.max(higherLayer, samePositionObjects.get(i).getLayer()); // determina
																						// o
																						// maior
																						// layer
		}
		for (FarmObject fo : samePositionObjects) {
			if (fo.getLayer() == higherLayer)
				return (Interactable) fo; // retorna o objeto com maior layer da
											// lista
		}
		return null;
	}

	// TODO
	// ovelha ainda passa por cima de farmer
	public boolean canMove(Point2D pos) {
		Interactable i = getObjectByPosition(pos);
		if (!ImageMatrixGUI.getInstance().isWithinBounds(pos))
			return false;
		if (i instanceof Land)
			return true;
		if (i instanceof Vegetable)
			return true;
		return false;
	}

	public void addImageToList(FarmObject farmObj) {
		farmObjects.add(farmObj);
		ImageMatrixGUI.getInstance().addImage(farmObj);
		ImageMatrixGUI.getInstance().update();
	}

	public void removeImageFromList(FarmObject farmObj) {
		farmObjects.remove(farmObj);
		ImageMatrixGUI.getInstance().removeImage(farmObj);
		ImageMatrixGUI.getInstance().update();
	}

	public void addPoints(int numberOfPoints) {
		points = points + numberOfPoints;
	}

	public void removeObject(Point2D objectPosition) {
		Interactable object = getObjectByPosition(objectPosition);
		farmObjects.remove(object);
		ImageMatrixGUI.getInstance().removeImage((ImageTile) object);
		ImageMatrixGUI.getInstance().update();
	}

	public void removeVegetable(Point2D vegPosition) {
		Vegetable veg = (Vegetable) getObjectByPosition(vegPosition);
		farmObjects.remove(veg);
		ImageMatrixGUI.getInstance().removeImage((ImageTile) veg);
		unPlow(vegPosition);
		ImageMatrixGUI.getInstance().update();
	}

	private void unPlow(Point2D position) {
		Land land = (Land) getObjectByPosition(position);
		land.setPlowed(false);
	}

	@Override
	public void update(Observable gui, Object a) {
		System.out.println("Update sent " + a);
		// TODO
		if (a instanceof Integer) {
			// sempre que e clicada uma tecla lança o incrementCycle() definido
			// acima
			// if clicada uma tecla de direcao -> farmer move-se
			// if clicada tecla de espaço -> ativa a interacao
			// quando interacao tiver ativada, escolhe-se a direcao que se quer
			// interagir(tecla direcao) -> farmer interage com land e nao se
			// move
			int key = (Integer) a;
			if (key == 76) {
				writeScenario();
			}
			if (Direction.isDirection(key)) {
				// TODO
				// if(!
				// ImageMatrixGUI.getInstance().isWithinBounds(Direction.isDirection(key)))
				// return;
				incrementCycle();
				System.out.println("Update is a Direction " + Direction.directionFor(key));
				if (farmer.isInteract()) {
					Point2D newPosition = farmer.getPosition().plus(Direction.directionFor(key).asVector());
					Interactable i = getObjectByPosition(newPosition);
					i.interact();
					farmer.setInteract(false);
				} else
					farmer.moveTo(Direction.directionFor(key));
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
