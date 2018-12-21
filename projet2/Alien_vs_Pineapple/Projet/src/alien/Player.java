package alien;


import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author cdiot
 * @author mcapdordy
 *
 */
public class Player {
	private String name; //name of the side
	private Spaceship armada; //type of the spaceship owned by the player
	private String planetLogo; //kind of spaceships owned by the player
	
	/**
	 * Create a player, basically neutral, player, or adverse
	 * 
	 * @param name name of the player
	 * @param image name of the kind of spaceships owned by the player
	 */
	public Player(String name, String image){
		this.name = name;
		planetLogo = image;
	}
	
	/**
	 * Give the name of a kind of spaceships
	 * 
	 * @return the name of the kind of the spaceships
	 */
	public String name(){
		return name;
	}
	
	/**
	 * Give the kind of spaceships
	 * 
	 * @return the kind of spaceships
	 */
	public Spaceship armada(){
		return armada;
	}
	
	/**
	 * Inform if the player is the main player
	 * 
	 * @return true if the player is player, and false if not
	 */
	public boolean isPlayer(){
		return (name=="player");
	}
	
	/**
	 * Inform if the player is neutral
	 * 
	 * @return true if the player is neutral, and false if not
	 */
	public boolean isNeutral(){
		return (name=="neutral");
	}
	
	/**
	 * Inform if the player is an adverse
	 * 
	 * @return true if the player is an adverse, and false if not
	 */
	public boolean isAdverse(){
		return (name=="adverse");
	}
	
	/**
	 * Get the name of the kind of spaceships owned by a player
	 * 
	 * @return the name of the kind of spaceships owned by a player
	 */
	public String getLogo(){
		return planetLogo;
	}
	
	/**
	 * Give a kind of spaceships to a player
	 * 
	 * @param armada the kind of spaceships
	 */
	public void setArmada(Spaceship armada){
		armada.setSide(this);
		this.armada = armada;		
	}
	
	/**
	 * Get the production time of the spaceship
	 * 
	 * @return the production time of the spaceship
	 */
	public int productionTime(){
		return armada.productionTime();
	}
}
