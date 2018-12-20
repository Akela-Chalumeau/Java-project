package alien;


import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Collection;

public class Player {
	private String name;
	private Spaceship armada;
	Collection<Planet> empire;
	private Image planetLogo;
	
	public Player(String name, Spaceship armada, Image image){
		this.name = name;
		this.armada = new Spaceship(armada);
		planet_logo = image;
		empire = new ArrayList<Planet>();
	}
	
	public String name(){
		return name;
	}
	
	public void add(Player s, Planet p){
		empire.add(p);
		s.remove(p);
	}
	
	private void remove(Planet p){
		
	}
}
