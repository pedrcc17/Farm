package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Tomato extends Vegetable {

	//conta o numero de ciclos que passaram desde a ultima vez que foi cuidado
	private int cyclesAfterTakenCare;

	public Tomato(Point2D position) {
		super(position, "planted", 2, 15, 25);
		this.cyclesAfterTakenCare = 0;
	}

	//apenas altera o contador para amadurecer caso tenha sido interagido à menos de 10 ciclos
	@Override
	public void incrementCycle() {
		cyclesAfterTakenCare++;
		if(cyclesAfterTakenCare < 10){
			super.ripen(1);
		}
		super.rot();
	}



	//caso nao esteja podre, ao interagir, mete o contador da ultima vez que foi cuidado a 0
	@Override
	public void interact(){
		if(! isRotten())
			cyclesAfterTakenCare = 0;
		System.out.println("cycles to ripe : " + getCyclesToRipe());
		System.out.println(isRipe());
		System.out.println(isRotten());
	}

	@Override
	public String getName(){
		if(isRotten()) return "bad_tomato";
		if(isRipe()) return "tomato";
		if(getCyclesToRipe()<10) return "small_tomato";
		return "planted";
	}









}
