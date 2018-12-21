package alien;

import javafx.scene.image.Image;

public class Spaceship extends Sprite{

	private int power;
	private int productionTime;
	private Planet destination;
	
	public Spaceship(double width, double height, double maxX, double maxY, int power, int productionTime) {
		super(width, height, maxX, maxY);
		this.power=power;
		this.productionTime=productionTime;
	}
	
	public Spaceship(Spaceship s) {
		super(s.width(), s.height(), s.maxX(), s.maxY());
		power=s.power;
		productionTime=s.productionTime();
		side=s.getSide();
		image=s.image;
	}
	
	public int productionTime() {
		return productionTime;
	}
	public int power(){
		return power;
	}
	
	public Planet destination() {
		return destination;
	}
	
	public void setSide(Player side){
		this.side=side;
		String name;
		switch(side.name()) {
		case "player":name="/images/TIE_fighter.png";
		break;
		case "adverse":name="/images/licorne.gif";
		break;
		default: name="/images/asteroid.png";
		}
		image=new Image(Spaceship.class.getResource(name).toString(), width(), height(), false, false);
		
	}
	
	public void setPosition(double x, double y, Planet destination) {
		this.destination=destination;
		this.x = x;
		this.y = y;
		super.validatePosition();
		Point dest = new Point(destination.x, destination.y);
		double hyp = new Point(x,y).distance(dest);
		super.setSpeed((dest.x-x)/hyp, (dest.y-y)/hyp);
	}
	
	public boolean impact() {
		boolean res=false;
		if (intersects(destination)) {
			destination.impact(this);
			res=true;
		}
		return res;
	}
	
	public void changeDestination(Planet destination) {
		setPosition(x,y,destination);
	}
}

/**
 * noeuds avec A* Node[][]
 * LinkedQueue()
 * BlockingLinkedPriorityQueue()
 * 
 */