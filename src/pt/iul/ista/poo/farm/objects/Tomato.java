package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

/**
 * @author TomásFerreira & PedroCosta
 * 
 *    Classe que extende Vegetable
 *
 *
 */


public class Tomato extends Vegetable {

	//conta o numero de ciclos que passaram desde a ultima vez que foi cuidado
	private int cyclesAfterTakenCare;

	/**
	 * 
	 * @param position - posicao do tomate 
	 * 
	 * 
	 */
	public Tomato(Point2D position) {
		super(position, 15, 25);
		this.cyclesAfterTakenCare = 0;
	}

	//apenas altera o contador para amadurecer caso tenha sido interagido à menos de 10 ciclos
	@Override
	public void incrementCycle() {
		cyclesAfterTakenCare++;
		if(cyclesAfterTakenCare < 10)
			super.ripen(1);
		super.rot();
	}



	//caso nao esteja podre, ao interagir, mete o contador da ultima vez que foi cuidado a 0
	@Override
	public void interact(){
		if(isRotten() || isRipe())
			removeVegetable();
		else
			cyclesAfterTakenCare = 0;
	}



	@Override
	public String getName(){
		if(isRotten()) return "bad_tomato";
		if(isRipe()) return "tomato";
		return "small_tomato";
	}

	@Override
	public int getPoints(){
		return 3;
	}









}
