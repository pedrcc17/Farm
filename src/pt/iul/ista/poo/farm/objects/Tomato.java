package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

/**
 * @author TomásFerreira 
 * @author PedroCosta
 * 
 *    Representa a informacao associada e implementa o ciclo de vida de um tomate
 *
 *
 */


public class Tomato extends Vegetable {


	private int cyclesAfterTakenCare;    //contador de ciclos desde que foi cuidado a ultima vez

	/**
	 * 
	 * @param position - posicao do tomate 
	 */ 
	public Tomato(Point2D position) {
		super(position, 15, 25);
		this.cyclesAfterTakenCare = 0;
	}

	
	/**
	 *   Por cada ciclo de jogo, irá incrementar em 1, o numero de ciclos
	 *   desde que foi cuidado.
	 *   
	 *   Apenas ira amadurecer caso tenha sido cuidado a menos de 10 ciclos
	 *   
	 *   Ira apodrecer independentemente de ter ou nao sido cuidado 
	 *   (podendo apodrecer sem amadurecer primeiro)
	 *   
	 */
	@Override
	public void incrementCycle() {
		cyclesAfterTakenCare++;
		if(cyclesAfterTakenCare < 10)
			super.ripen(1);
		super.rot();
	}



	/**
	 *   Ao interagir, caso esteja podre ou maduro, ira remove-lo
	 *   
	 *   Caso nao seja preciso remover, cuida do tomate
	 *   (pondo a 0 o contador de ciclos desde que foi cuidado)
	 */
	@Override
	public void interact(){
		if(isRotten() || isRipe())
			removeVegetable();
		else
			cyclesAfterTakenCare = 0;
	}


/**
 *    Muda o nome do ficheiro de imagem de acordo com o estado
 *    
 *    @return - retorna o ficheiro de imagem de acordo com o estado
 */
	@Override
	public String getName(){
		if(isRotten()) return "bad_tomato";
		if(isRipe()) return "tomato";
		return "small_tomato";
	}
	
	/**
	 * @return - retorna o numero do pontos associados
	 */
	@Override
	public int getPoints(){
		return 3;
	}


	public int getCyclesAfterTakenCare() {
		return cyclesAfterTakenCare;
	}


	public void setCyclesAfterTakenCare(int cyclesAfterTakenCare) {
		this.cyclesAfterTakenCare = cyclesAfterTakenCare;
	}









}
