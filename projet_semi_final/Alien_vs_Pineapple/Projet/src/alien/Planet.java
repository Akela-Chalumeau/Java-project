package alien;

import javafx.scene.image.Image;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class Planet extends Sprite{
	private int value; //number of spaceships
	private Spaceship armada; //ship type
	private int cpt=0;
	
	public Planet(double width, double height, double maxX, double maxY, double x, double y, String side, Spaceship armada) {
		super(width, height, maxX, maxY);
		super.setPosition(x, y);
		String name;
		switch(side) {
		case "player":name="/images/planet_player.png";
			this.value=100;
		break;
		case "adverse":name="/images/planet_rondoudou.png";
			this.value=50;
		break;
		default: name="/images/planet_neutral.png";
			this.value=10;
		}
		image=new Image(Planet.class.getResource(name).toString(), width, height, false, false);
		this.side=side;
		this.armada=armada;
	}
	
	public Planet(double width, double height, double maxX, double maxY, Spaceship armada) {
		this(width, height, maxX, maxY, Math.random()*600, Math.random()*600, "neutral", armada);
	}
	
	public Planet(Planet p) {
		this(p.width(),p.height(), p.maxX(), p.maxY(), p.x, p.y, p.getSide(), p.armada);
	}
	
	public String getSide() {
		return side;
	}
	
	public void render(GraphicsContext gc) {
		gc.drawImage(super.image, super.x, super.y);
		String txt = Integer.toString(value);
		gc.fillText(txt, x + width()/2, y + height()/2+7);
	}
	
	public void impact(Spaceship impact) {
		if(impact.getSide()!=side) {
			if (value!=0) {
				value--;
			}
			if(value<=0) {
				changeSide(impact.getSide());
			}
		}else {
			value++;
		}
	}
	
	public void product() {
		if(side!="neutral") {
			cpt++;
//			if (cpt>=1) {
			if (cpt>=armada.productionTime()) {
				value++;
				cpt=0;
			}
		}
	}
	
	public void changeSide(String newSide) {
		String name;
		side=newSide;
		switch(newSide) {
		case "player":name="/images/planet_player.png";
		break;
		case "adverse":name="/images/planet_rondoudou.png";
		break;
		default: name="/images/planet_neutral.png";
		}
		image=new Image(Planet.class.getResource(name).toString(), width(), height(), false, false);
	}
	
	public void deploy(Planet destination, double percentage, Collection<Spaceship> t_ships[]) {
		
		Collection<Spaceship> ships = new ArrayList<Spaceship>();
		double nbSpaceships=percentage*value;
		
		Point p_planet = new Point(x,y);
		Point xy = new Point(0,0);
		double p;
		
		int index=0;
		while(t_ships[index] !=null && !t_ships[index].isEmpty()) {
			index++;
		}
		
		for(int i=1; i<=nbSpaceships;i++) {
			Spaceship GuiGui = new Spaceship(armada);
			
			xy.x= Math.random()*100-50;
			xy.y= Math.random()*100-50;
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
	
	public void IA(int v, Collection<Planet> planets, Collection<Spaceship> t_ships[]){
		if(side=="adverse"){
			if(value>=v){
				Iterator<Planet> p = planets.iterator();
				while(p.hasNext()) {
					Planet planet = p.next();
					this.deploy(planet, 0.5 , t_ships);
				}
			}
		}
	}
}
