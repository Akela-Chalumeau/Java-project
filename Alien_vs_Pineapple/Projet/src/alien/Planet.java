package alien;

import javafx.scene.image.Image;
import java.util.Collection;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class Planet extends Sprite implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int value; //number of spaceships
	private int cpt=0;
	
	public Planet(double width, double height, double maxX, double maxY, double x, double y, Player side) {
		super(width, height, maxX, maxY);
		super.setPosition(x, y);
		this.side=side;
		if(side.isNeutral()){
			value=10;
		}
//		if(side.name()=="player"){
//			value=10000;
//		}
		image=new Image(Planet.class.getResource(side.getLogo()).toString(), width, height, false, false);
	}
	
	public Planet(double size, double maxX, double maxY, double x, double y, Player side) {
		this(size, size, maxX, maxY, x, y, side);
	}
	
	public Planet(Planet p) {
		this(p.width(),p.height(), p.maxX(), p.maxY(), p.x, p.y, p.getSide());
	}
	
	public Planet(double size,double maxX, double maxY, Player side){
		this(size, size, maxX, maxY, Math.random()*(maxX-200)+100, Math.random()*(maxY-200)+100, side);
	}
	
	public void changePosition(){
		x=Math.random()*(maxX()-200)+100;
		y=Math.random()*(maxY()-200)+100;
	}
	
	public void render(GraphicsContext gc) {
		super.render(gc);
		String txt = Integer.toString(value);
		gc.fillText(txt, x + width()/2, y + height()/2+7);
	}
	
	public void impact(Spaceship impact) {
		if(impact.getSide()!=side) {
			if (value!=0) {
				value-=impact.power();
			}
			if(value<=0) {
				changeSide(impact.getSide());
			}
		}else {
			value++;
		}
	}
	
	public void product() {
		if(!side.isNeutral()) {
			cpt+=5;
			if (cpt>=side.productionTime()) {
				value++;
				cpt=0;
			}
		}
	}
	
	public void changeSide(Player newSide) {
		String name;
		side=newSide;
		switch(newSide.name()) {
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
		
		Point xy = new Point(0,0);
//		Point p_planet = new Point(x,y);
//		double p;
		
		int index=0;
		while(t_ships[index] !=null && !t_ships[index].isEmpty()) {
			index++;
		}
		
		for(int i=1; i<=nbSpaceships;i++) {
			Spaceship GuiGui = new Spaceship(side.armada());
			
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
	
	public void IA(int v, Planet[] planets, Collection<Spaceship> t_ships[]){
		if(side.isAdverse()){
			if(value>=v){
				int i=0;
				while(i<10 && planets[i]!=null){
					if (planets[i].getSide().name()=="player"){
						this.deploy(planets[i], 0.5 , t_ships);
					}
					i++;
				}
			}
		}
	}
}
