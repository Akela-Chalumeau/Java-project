package alien;


public class Player {
	private String name;
	private Spaceship armada;
	private String planet_logo;
	
	public Player(String name, String image){
		this.name=name;
		planet_logo=image;
	}
	
	public String name(){
		return name;
	}
	
	public Spaceship armada(){
		return armada;
	}
	
	public boolean isPlayer(){
		return (name=="player");
	}
	public boolean isNeutral(){
		return (name=="neutral");
	}
	public boolean isAdverse(){
		return (name=="adverse");
	}
	
	
	public String getLogo(){
		return planet_logo;
	}
	
	public void setArmada(Spaceship armada){
		armada.setSide(this);
		this.armada=armada;
		
	}
	
	public int productionTime(){
		return armada.productionTime();
	}
}
