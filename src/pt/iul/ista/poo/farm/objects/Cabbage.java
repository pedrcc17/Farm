package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

/**
 * @author TomásFerreira 
 * @author PedroCosta
 * 
 *    Representa a informacao associada e implementa o ciclo de vida de uma couve
 *
 *
 */
public class Cabbage extends Vegetable {

	public Cabbage(Point2D position) {
		super(position, 10, 30);
	}

/**
 *    Por cada ciclo de jogo, ira decrementar em 1 ambos
 *    os contadores de amadurecimento e apodrecimento
 */
	@Override
	public void incrementCycle(){
		ripen(1);
		rot();
	}

	
	/**
	 *   Ao interagir, caso esteja podre ou madura ira remove-la
	 *   
	 *   Caso nao seja preciso remover, ira ajudar em 1 ciclo
	 *   no amadurecimento
	 *   
	 */
	@Override
	public void interact(){
		if(isRotten() || isRipe())
			removeVegetable();
		else
			ripen(1);
	}



	/**
	 * @return - retorna o ficheiro de imagem de acordo com o estado
	 */
	@Override
	public String getName(){
		if(isRotten()) return "bad_cabbage";
		if(isRipe()) return "cabbage";
		return "small_cabbage";
	}
	
	/**
	 * @return - retorna o numero do pontos associados
	 */
	@Override
	public int getPoints(){
		return 2;
	}
	
	
	
}
