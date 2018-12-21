package alien;

import java.awt.event.InputEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.shape.Line;

/**
 * 
 * @author cdiot
 * @author mcapdordy
 *
 */
public class Game extends Application {
	private final static int WIDTH = 1000; //width of the game
	private final static int HEIGHT = 1000; //height of the game
	public final static int ATTACK = 20; //number of spaceships a planet must possess to be able to attack
	public static Planet pClicked; //true if we clicked on a planet, false if not
	public static int cSquadron = -1; //-1 if we did not click on a squadron, it is the number of the squadron otherwise
	public static double percentage = 0.5; //percentage of spaceships we send

	/**
	 * 
	 * @param name the name of the resource we want to find
	 * @return the path leading to the resource
	 */
	public static String getRessourcePathByName(String name) {
		return Game.class.getResource('/' + name).toString();
	}

	/**
	 * Launch the main function
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Creation of the game, creation of all the planets and tables
	 */
	public void start(Stage stage) {

		stage.setTitle("Alien vs Pinapples");
		stage.setResizable(false);

		//creation of the scene
		Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		//main options 
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		gc.setFill(Color.BISQUE);
		gc.setStroke(Color.RED);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setLineWidth(1);

		Image space = new Image(getRessourcePathByName("images/space.jpg"), WIDTH, HEIGHT, false, false);
		
		//creation of the players
		Player players[]= new Player[3];
		players[0] = new Player("player", "/images/planet_player.png");
		players[1] = new Player("adverse", "/images/planet_rondoudou.png");
		players[2] = new Player("neutral", "/images/planet_neutral.png");
		
		//creation of the spaceships
		Spaceship light = new Spaceship(50, 50, WIDTH, HEIGHT, 1, 100);
		Spaceship dark = new Spaceship(50, 50, WIDTH, HEIGHT, 1, 100);
	
		//integration of the spaceships with the players
		players[0].setArmada(light);
		players[1].setArmada(dark);
		
		//creation and implementation of the planets
		Planet[] planets = new Planet[10];
		planets[0] = new Planet(100, WIDTH, HEIGHT, 50, 50, players[0]);
		planets[1] = new Planet(100, WIDTH, HEIGHT, WIDTH-125, HEIGHT-125, players[1]);
		
		//The number of the neutral planets vary between 1 and 8
		for(int i = 2; i<Math.random()*7+3; i++){
			
			double size = Math.random()*50+75;
			planets[i] = new Planet(size, WIDTH, HEIGHT, players[2]);
			
			//planet collision verification
			int j = 0;
			while(j<i){
				if(planets[i].intersects(planets[j])){
					planets[i].changePosition();
					j = 0;
				}else{ j++; }
			}
		}

		//table of squadrons
		Collection<Spaceship> t_ships[] = new ArrayList[100];
		
//		Line line = new Line();

		stage.setScene(scene);
		stage.show();

		/**
		 * happen when the mouse is used
		 */
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent e) {
				boolean notFound = true; //if we clicked on a planet or a squadron
				Point clic = new Point(e.getX(), e.getY());
				int i = 0;
				
				
				String txt = e.getEventType().getName();
				switch(txt) {
				//when the mouse is pressed
				case "MOUSE_PRESSED": 
					
					//if the we clicked on a planet
					while(i<10 && notFound && planets[i]!=null){
						if(planets[i].isInterior(clic) && planets[i].getSide()==players[0]) {
							notFound = false;
							pClicked = planets[i];
						}
						i++;
					}
					
					//if we clicked on a spaceship
					i=0;
					while(i<t_ships.length && notFound) {
						
						if(t_ships[i]!=null && !t_ships[i].isEmpty()) {
							
							Iterator<Spaceship> it = t_ships[i].iterator();
							while (it.hasNext() && notFound) {
								
								Spaceship ship = it.next();
								if(ship.isInterior(clic) && ship.getSide()==players[0]) {
									
									notFound = false;
									cSquadron = i;
									
								}
								
							}
							
						}
						i++;
					}
					
					break;
				//when the mouse is released, we only verify if we released on a planet
				case "MOUSE_RELEASED":
					while(i<10 && notFound && planets[i]!=null) {
						if( planets[i].isInterior(clic) && (pClicked!=null || cSquadron!=-1) ) {
								
							notFound=false;
							//if we clicked on a planet
							if(pClicked!=null) {
								pClicked.deploy(planets[i], percentage, t_ships);
								pClicked = null;
							}
							else { //if we clicked on a spaceship
								int nbPlanet = i;
								Iterator<Spaceship> it = t_ships[cSquadron].iterator();
								it.forEachRemaining(s -> {
									s.changeDestination(planets[nbPlanet]);
								});
								cSquadron = -1;
							}
						}
						i++;
					}
					break;
//					line test:
//				case "MOUSE_DRAGGED":
//					gc.strokeLine(clicked.x, clicked.y, e.getX(), e.getY());
//						gc.setStroke(Color.WHITE);
//						gc.setLineWidth(5);
//						gc.strokeLine(e.getX(),  e.getY(), e.getX(),e.getY());
//					
				}
				
			}
		};

		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMousePressed(mouseHandler);
		scene.setOnMouseReleased(mouseHandler);

		/**
		 * Happen when the keyboard is used
		 */
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				switch(e.getCode()) {
				//change the percentage
				case Z:
				case UP: if(percentage<1) {
					percentage += 0.05;
				}
				break;
				case S:
				case DOWN: if((int)(percentage*100)>0) {
					percentage -= 0.05;
				}
				break;
				//save the game (doesn't work)
				case ESCAPE:
					FileOutputStream fichier;
					try {
						fichier = new FileOutputStream("test.txt");
						ObjectOutputStream oos = new ObjectOutputStream(fichier);
						oos.writeObject(planets[3]);
						oos.flush();
						oos.close();
					} catch (IOException e1) {
//						 TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("ça a pas marché :(");
					}
				break;
				case ENTER:
//					FileInputStream fichier2;
//					ObjectInputStream oos2;
//					try {
//						fichier2 = new FileInputStream("test.txt");
//						oos2 = new ObjectInputStream(fichier2);
////					planets[3]= oos2.readObject();
//						Object t = oos2.readObject();
//					} catch (IOException | ClassNotFoundException e1) {
////						 TODO Auto-generated catch block
//						e1.printStackTrace();
//						System.out.println("ça a pas marché :(");
//					}
				break;
				default:				
				}
			}
		});

		/**
		 * Happen at every frame of the game
		 */
		new AnimationTimer() {
			public void handle(long arg0) {
				gc.drawImage(space, 0, 0);

				//for each squadron
				for(Collection<Spaceship> squadron : t_ships){
					if(squadron!=null && !squadron.isEmpty()) {
						
						//iteration of one squadron
						Iterator<Spaceship> it = squadron.iterator();
						
						//for each spaceships of the squadron
						while (it.hasNext()) {
							Spaceship ship = it.next();
							ship.updatePosition();
							if(ship.impact()) {
								it.remove();
							}else {
								ship.render(gc);
							}
						}
					}
				}
				
				//for each planets
				boolean victory=true;
				boolean defeat=true;
				int i=0;
				while(i<10 && planets[i]!=null){
					planets[i].product();
					planets[i].IA(ATTACK, planets, t_ships);
					planets[i].render(gc);
					
					if(planets[i].getSide().isAdverse()){
						victory = false;
					}
					if(planets[i].getSide().isPlayer()){
						defeat = false;
					} 
					
					i++;
				}
				
				//if the game is finished
				if(victory){
					gc.fillText("WIN", WIDTH/2, 50);
				}
				if(defeat){
					gc.fillText("LOSE", WIDTH/2, 50);
				}

				//percentage drawing
				String txt = Math.round(percentage*100)+"%";
				gc.fillText(txt, 45, 30);
			}
		}.start();
	}
}