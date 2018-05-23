package pt.iul.ista.poo.farm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

	private static final String SAVE_FNAME = "config/savedGame";

	private static final int MIN_X = 5;
	private static final int MIN_Y = 5;

	private static Farm INSTANCE = null;

	private int points;

	private Farmer farmer;

	private int max_x;
	private int max_y;

	private List<FarmObject> farmObjects;

	private Farm() {
		init();
		if (max_x < 5 || max_y < 5)
			throw new IllegalArgumentException("Invalid Dimension");

		INSTANCE = this;

		this.points = 0;

		farmObjects = new ArrayList<FarmObject>();

		ImageMatrixGUI.setSize(max_x, max_y);

		registerAll();

	}

	private void init() {
		try {
			Scanner fileScanner = new Scanner(new File("FarmConfiguration.ini"));
			if (fileScanner.hasNextInt())
				max_x = fileScanner.nextInt();
			if (fileScanner.hasNextInt())
				max_y = fileScanner.nextInt();
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("FarmConfiguration.ini not found");
			System.exit(1);
		}
	}

	private Point2D randomPosition() {
		Random rnd = new Random();
		Point2D randomPos = new Point2D(rnd.nextInt(max_x), rnd.nextInt(max_y));
		return randomPos;
	}

	private void registerAll() {
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

	private void loadScenario() {
		try {
			Scanner read = new Scanner(new File("FarmSave.txt"));
			farmObjects = new ArrayList<FarmObject>(); // remove todos os
														// objectos da lista
			ImageMatrixGUI.getInstance().clearImages(); // limpa as imagens

			// ler dimensao da farm
			String line = read.nextLine();
			String[] size = line.split(" ");
			ImageMatrixGUI.setSize(Integer.parseInt(size[0]), Integer.parseInt(size[1]));

			points = read.nextInt(); // ler numero do pontos
			read.nextLine();
			// ler objectos
			do {
				String objectLine = read.nextLine();
				String[] objectDefintion = objectLine.split(" ");
				FarmObject farmObject = createObject(objectDefintion);
				farmObjects.add(farmObject);
				ImageMatrixGUI.getInstance().addImage(farmObject);
				ImageMatrixGUI.getInstance().update();

			} while (read.hasNextLine());
			read.close();

		} catch (FileNotFoundException e) {
			System.out.println("Erro na abertura de Ficheiro de leitura");
			System.exit(1);
		}
	}

	private FarmObject createObject(String[] objectDefintion) {
		String[] position = objectDefintion[1].split(";");
		Point2D point = new Point2D(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
		String objectName = objectDefintion[0];
		FarmObject a = null;
		switch (objectName) {
		case "Land":
			a = new Land(point, new Boolean(objectDefintion[2]), new Boolean(objectDefintion[3]));
			return a;
		case "Sheep":
			a = new Sheep(point, new Integer(objectDefintion[2]), new Boolean(objectDefintion[3]));
			return a;
		case "Chicken":
			a = new Chicken(point, new Integer(objectDefintion[2]));
			return a;
		case "Egg":
			a = new Egg(point, new Integer(objectDefintion[2]));
			return a;
		case "Tomato":
			a = new Tomato(point, new Integer(objectDefintion[2]), new Integer(objectDefintion[3]),
					new Integer(objectDefintion[4]));
			return a;
		case "Cabbage":
			a = new Cabbage(point, new Integer(objectDefintion[2]), new Integer(objectDefintion[3]));
			return a;
		case "Farmer":
			farmer = new Farmer(point, new Boolean(objectDefintion[2]));
			return farmer;
		}
		return null;
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
			System.out.println("Erro de escrita no ficheiro");
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

	public FarmObject getObjectByPosition(Point2D newPosition) {
		List<FarmObject> samePositionObjects = new ArrayList<FarmObject>();
		int higherLayer = 0;
		for (FarmObject f : farmObjects) { // procura objectos na posicao
											// recebida
			if (f.getPosition().equals(newPosition))
				samePositionObjects.add(f); // adiciona os objectos na posicao
											// dada, a uma lista
		}
		for (int i = 0; i < samePositionObjects.size(); i++) { // compara os
																// layers dos
																// objectos da
																// lista
			higherLayer = Math.max(higherLayer, samePositionObjects.get(i).getLayer());
		}
		for (FarmObject fo : samePositionObjects) {
			if (fo.getLayer() == higherLayer)
				return fo; // retorna o objeto com maior layer na posicao
							// recebida
		}
		return null;
	}

	public boolean canMove(Point2D pos) {
		FarmObject i = getObjectByPosition(pos);
		if (!ImageMatrixGUI.getInstance().isWithinBounds(pos))
			return false;
		if (i instanceof Land)
			return true;
		if (i instanceof Vegetable)
			return true;
		return false;
	}

	public void addObject(FarmObject farmObj) {
		farmObjects.add(farmObj);
		ImageMatrixGUI.getInstance().addImage(farmObj);
		ImageMatrixGUI.getInstance().update();
	}

	public void addPoints(int numberOfPoints) {
		points = points + numberOfPoints;
	}

	public void removeObject(Point2D objectPosition) {
		FarmObject object = getObjectByPosition(objectPosition);
		farmObjects.remove(object);
		if (object instanceof Vegetable)
			unPlow(objectPosition);
		ImageMatrixGUI.getInstance().removeImage((ImageTile) object);
		ImageMatrixGUI.getInstance().update();
	}

	private void unPlow(Point2D position) {
		Land land = (Land) getObjectByPosition(position);
		land.setPlowed(false);
	}

	@Override
	public void update(Observable gui, Object a) {
		System.out.println("Update sent " + a);
		if (a instanceof Integer) {
			int key = (Integer) a;
			if (key == 76) // se clicada tecla "l" carrega o jogo
				loadScenario();
			if (key == 83) // se clicada tecla "s" grava o jogo
				writeScenario();

			if (Direction.isDirection(key)) {
				if (!ImageMatrixGUI.getInstance()
						.isWithinBounds(farmer.getPosition().plus(Direction.directionFor(key).asVector())))
					return;
				System.out.println("Update is a Direction " + Direction.directionFor(key));
				if (farmer.isInteract()) {
					Point2D newPosition = farmer.getPosition().plus(Direction.directionFor(key).asVector());
					Interactable i = (Interactable) getObjectByPosition(newPosition);
					i.interact();
					farmer.setInteract(false);
				} else
					farmer.moveTo(Direction.directionFor(key));

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
		Farm f = new Farm();
		f.play();
	}

}
