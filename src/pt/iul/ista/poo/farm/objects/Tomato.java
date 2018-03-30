package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public class Tomato extends Vegetable {

	private int cyclesAfterTakenCare;

	public Tomato(Point2D position) {
		super(position, "tomato", 2, 15, 25);
		this.cyclesAfterTakenCare = 0;
	}

	@Override
	public void incrementCycle() {
		super.incrementCycle();
		cyclesAfterTakenCare++;
		if(cyclesAfterTakenCare < 10){
			super.ripen(1);
		}
		super.rot();
	}

	@Override
	public void interact(){
		if(! super.isRotten())
		cyclesAfterTakenCare = 0;
	}









}
