package alien;

import java.util.Collection;

import javafx.scene.image.Image;

/**
 * 
 * @author cdiot
 * @author mcapdordy
 *
 */
public class Spaceship extends Sprite{

	private int power; //fire power of the spaceship
	private int productionTime; //production time of the spaceship
	private Planet destination; //destination planet of the spaceship
	
	/**
	 * Create a spaceship
	 * 
	 * @param width the width of the spaceship
	 * @param height the height of the spaceship
	 * @param maxX the maximum value that the value of coordinate on x-axis can take
	 * @param maxY the maximum value that the value of coordinate on y-axis can take
	 * @param power the fire power of the spaceship
	 * @param productionTime the production time of the spaceship
	 */
	public Spaceship(double width, double height, double maxX, double maxY, int power, int productionTime) {
		super(width, height, maxX, maxY);
		this.power = power;
		this.productionTime = productionTime;
	}
	
	/**
	 * Create a copy of an existing spaceship
	 * 
	 * @param s a spaceship
	 */
	public Spaceship(Spaceship s) {
		this(s.width(), s.height(), s.maxX(), s.maxY(), s.power(), s.productionTime());
		side = s.getSide();
		image = s.image;
	}
	
	/**
	 * 
	 * @return the production time of a spaceship
	 */
	public int productionTime() {
		return productionTime;
	}
	
	/**
	 * 
	 * @return the fire power of a spaceship
	 */
	public int power(){
		return power;
	}
	
	/**
	 * 
	 * @return the destination planet of a spaceship
	 */
	public Planet destination() {
		return destination;
	}
	
	/**
	 * Assign a side to a spaceship and the corresponding image
	 */
	public void setSide(Player side){
		this.side = side;
		String name;
		switch(side.name()) {
		case "player":name = "/images/TIE_fighter.png";
		break;
		case "adverse":name = "/images/licorne.gif";
		break;
		default: name = "/images/asteroid.png";
		}
		image = new Image(Spaceship.class.getResource(name).toString(), width(), height(), false, false);		
	}
	
	/**
	 * Assign a position to a spaceship
	 * 
	 * @param x the value of coordinate on x-axis
	 * @param y the value of coordinate on y-axis
	 * @param destination the destination planet
	 */
	public void setPosition(double x, double y, Planet destination) {
		this.destination=destination;
		this.x = x;
		this.y = y;
		super.validatePosition();
		Point dest = new Point(destination.x, destination.y);
		double hyp = new Point(x,y).distance(dest);
		super.setSpeed((dest.x-x)/hyp, (dest.y-y)/hyp);
	}
	
	/**
	 * Inform if the spaceship lands on a planet
	 * 
	 * @return true if the spaceship lands on a planet, and false if not
	 */
	public boolean impact() {
		boolean res = false;
		if (intersects(destination)) {
			destination.impact(this);
			res = true;
		}
		return res;
	}
	
	/**
	 * Change the destination of the spaceship 
	 * 
	 * @param destination the destination planet
	 */
	public void changeDestination(Planet destination) {
		setPosition(x,y,destination);
	}
}