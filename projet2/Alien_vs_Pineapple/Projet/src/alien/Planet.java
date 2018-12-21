package alien;

import javafx.scene.image.Image;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

/**
 * 
 * @author cdiot
 * @author mcapdordy
 *
 */
public class Planet extends Sprite{
	private int value; //number of spaceships
	private int cpt = 0; //counter which increments the spaceships production at every 100
	
	/**
	 * Create a planet assigned to the player, the IA, or neutral
	 * we can make the game easier by giving an initial value to the player, 100 for example
	 * 
	 * @param width the width of the planet
	 * @param height the height of the planet
	 * @param maxX maximum value that x can take
	 * @param maxY maximum value that y can take
	 * @param x value of coordinate on x-axis
	 * @param y value of coordinate on y-axis
	 * @param side the owner of the planet
	 * 
	 */
	public Planet(double width, double height, double maxX, double maxY, double x, double y, Player side) {
		super(width, height, maxX, maxY);
		super.setPosition(x, y);
		this.side=side;
		if(side.isNeutral()){
			value = 10;
		}
//		if(side.name()=="player"){
//			value = 100;
//		}
		image = new Image(Planet.class.getResource(side.getLogo()).toString(), width, height, false, false);
	}
	
	public Planet(double size, double maxX, double maxY, double x, double y, Player side) {
		this(size, size, maxX, maxY, x, y, side);
	}
	
	/**
	 * Create a planet at a random position with its height equals to its width
	 * 
	 * @param size size of the planet
	 * @param maxX maximum value that the value coordinate on x-axis can take
	 * @param maxY maximum value that the value coordinate on y-axis can take
	 * @param side the owner of the planet
	 */
	public Planet(double size, double maxX, double maxY, Player side){
		this(size, size, maxX, maxY, Math.random()*(maxX-200)+100, Math.random()*(maxX-200)+100, side);
	}
	
	/**
	 * Randomly change the position to avoid collision between planets
	 */
	public void changePosition(){
		x=Math.random()*(maxX()-200)+100;
		y=Math.random()*(maxY()-200)+100;
	}
	
	/**
	 * Display the graphic content of a planet
	 * 
	 * @param gc the graphic content of the game 
	 */
	public void render(GraphicsContext gc) {
		gc.drawImage(super.image, super.x, super.y);
		String txt = Integer.toString(value);
		gc.fillText(txt, x + width()/2, y + height()/2+7);
	}
	
	/**
	 * Decide what to do when a spaceship lands on a planet. 
	 * If the spaceship is an enemy, the value of the planet decreases.
	 * When the value of the planet reaches 0, it takes the same side than the invader.
	 * If the spaceship in an ally, the value of the planet increases.
	 * 
	 * @param impact the spaceship that lands on the planet
	 */
	public void impact(Spaceship impact) {
		if(impact.getSide()!=side) {
			if (value!=0) {
				value -= impact.power();
			}
			if(value<=0) {
				changeSide(impact.getSide());
			}
		}else {
			value++;
		}
	}
	
	/**
	 * Make a non-neutral planet produce a spaceship
	 */
	public void product() {
		if(!side.isNeutral()) {
			cpt ++;
			if (cpt>=side.productionTime()) {
				value++;
				cpt = 0;
			}
		}
	}
	
	/**
	 * Change the side of a planet when it is invaded
	 * 
	 * @param newSide the new side of the planet
	 */
	public void changeSide(Player newSide) {
		String name;
		side = newSide;
		switch(newSide.name()) {
		case "player":name = "/images/planet_player.png";
		break;
		case "adverse":name = "/images/planet_rondoudou.png";
		break;
		default: name = "/images/planet_neutral.png";
		}
		image = new Image(Planet.class.getResource(name).toString(), width(), height(), false, false);
	}
	
	/**
	 * Send a squadron from a planet to another
	 * 
	 * @param destination the destination planet
	 * @param percentage the percentage of spaceships in the squadron
	 * @param t_ships the spaceships that form the squadron
	 */
	public void deploy(Planet destination, double percentage, Collection<Spaceship> t_ships[]) {
		
		Collection<Spaceship> ships = new ArrayList<Spaceship>();
		double nbSpaceships=percentage*value;
		
//		Point p_planet = new Point(x,y);
		Point xy = new Point(0,0);
//		double p;
		
		int index = 0;
		while(t_ships[index] !=null && !t_ships[index].isEmpty()) {
			index++;
		}
		
		for(int i=1; i<=nbSpaceships; i++) {
			Spaceship GuiGui = new Spaceship(side.armada());
			
			xy.x = Math.random()*100-50;
			xy.y = Math.random()*100-50;
//			p=i/nbSpaceships*2*Math.PI;
//			xy.x=Math.cos(p);
//			xy.y=Math.sin(p);
//			while(p_planet.distance(xy)<=width()) {
//				if(-1<xy.x && xy.x<1) {
//					xy.x/=2;
//				}else {
//					xy.x*=2;
//				}
//				if(-1<xy.y && xy.y<1) {
//					xy.y/=2;
//				}else {
//					xy.y*=2;
//				}
//				if(i==1 && xy.x!=0 && xy.y!=0) {
//				System.out.println(i+".x:"+xy.x);
//				System.out.println(i+".y:"+xy.y);
//					
//				}
//			}
			GuiGui.setPosition(x+width()/2+xy.x, y+height()/2+xy.y, destination);
			ships.add(GuiGui);
			value--;
		}
		
		t_ships[index] = ships;
	}
	
	/**
	 * Basic IA as opponent
	 * 
	 * @param nbSpaceships number of spaceships needed to be able to send a squadron
	 * @param planets the planets controlled by the IA
	 * @param t_ships the spaceships controlled by the IA
	 */
	public void IA(int nbSpaceships, Planet[] planets, Collection<Spaceship> t_ships[]){
		if(side.isAdverse()){
			if(value>=nbSpaceships){
				int i = 0;
				while(i<10 && planets[i]!=null){
					if (planets[i].getSide().name()=="player"){
						this.deploy(planets[i], 0.5, t_ships);
					}
					i++;
				}
			}
		}
	}
}
