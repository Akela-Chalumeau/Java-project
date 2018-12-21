package alien;

import java.awt.event.InputEvent;
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

public class Game extends Application {
	private final static int SPEEDGAME=100;
	private final static int WIDTH = 600;
	private final static int HEIGHT = 600;
	public final static int ATTACK = 20;
	public static Planet p_clicked;
	public static int c_squadron=-1;
	public static double percentage=0.5;

	public static String getRessourcePathByName(String name) {
		return Game.class.getResource('/' + name).toString();
	}

	public static void main(String[] args) {
		launch(args);
	}

//	private void changeSpeed(Spaceship pinapple) {
//		int max = 5;
//		pinapple.setSpeed(max * Math.random() - max / 2, max * Math.random() - max / 2);
//	}

	public void start(Stage stage) {

		stage.setTitle("Alien vs Pinapples");
		stage.setResizable(false);

		Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		gc.setFill(Color.BISQUE);
		gc.setStroke(Color.RED);
		gc.setLineWidth(1);

		Image space = new Image(getRessourcePathByName("images/space.jpg"), WIDTH, HEIGHT, false, false);

//		Sprite spaceship = new Sprite(getRessourcePathByName("images/alien.png"), 62, 36, WIDTH, HEIGHT);
//		spaceship.setPosition(WIDTH / 2 - spaceship.width() / 2, HEIGHT / 2 - spaceship.height() / 2);
		
		Player players[]= new Player[3];
		players[0]=new Player("player", "/images/planet_player.png");
		players[1]=new Player("adverse", "/images/planet_rondoudou.png");
		players[2]=new Player("neutral", "/images/planet_neutral.png");
		
		Spaceship light = new Spaceship(50, 50, WIDTH, HEIGHT, 1, 100);
		Spaceship dark = new Spaceship(50, 50, WIDTH, HEIGHT, 1, 100);
	
		players[0].setArmada(light);
		players[1].setArmada(dark);
		
		Planet[] planets = new Planet[10];
		planets[0] = new Planet(150, 150, WIDTH, HEIGHT, 50, 50, players[0]);
		planets[1] = new Planet(75, 75, WIDTH, HEIGHT, 500, 500, players[1]);
		
		for(int i=2;i<Math.random()*7+3;i++){
			
			double size=Math.random()*150+50;
			planets[i] = new Planet(size, WIDTH, HEIGHT, players[2]);
			
			int j=0;
			while(j<i){
				if(planets[i].intersects(planets[j])){
					planets[i].changePosition();
					j=0;
				}else{ j++; }
			}
		}

		Collection<Spaceship> t_ships[] = new ArrayList[100];
		
//		Line line = new Line();

		stage.setScene(scene);
		stage.show();

		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				boolean notFound=true; //if we clicked on a planet
				Point clic=new Point(e.getX(), e.getY());
				int i=0;
				
				
				String txt = e.getEventType().getName();
				switch(txt) {
				case "MOUSE_PRESSED": 
					
					while(i<10 && notFound && planets[i]!=null){
						if(planets[i].isInterior(clic) && planets[i].getSide()==players[0]) {
							notFound=false;
							p_clicked=planets[i];
	//						gc.setStroke(Color.WHITE);
	//						gc.setLineWidth(5);
	//						gc.strokeLine(e.getX(),  e.getY(), e.getX(),e.getY());
						}
						i++;
					}
					
					i=0;
					while(i<t_ships.length && notFound) {
						
						if(t_ships[i]!=null && !t_ships[i].isEmpty()) {
							
							Iterator<Spaceship> it = t_ships[i].iterator();
							while (it.hasNext() && notFound) {
								
								Spaceship ship = it.next();
								if(ship.isInterior(clic) && ship.getSide()==players[0]) {
									
									notFound=false;
									c_squadron=i;
//									System.out.println(c_squadron);
									
								}
								
							}
							
						}
						i++;
					}
					
					break;
				case "MOUSE_RELEASED":
					while(i<10 && notFound && planets[i]!=null) {
						if( planets[i].isInterior(clic) && (p_clicked!=null || c_squadron!=-1) ) {
								
							notFound=false;
							if(p_clicked!=null) {
								p_clicked.deploy(planets[i], percentage, t_ships);
								p_clicked=null;
							}
							else {
								int nb_planet=i;
								Iterator<Spaceship> it = t_ships[c_squadron].iterator();
								it.forEachRemaining(s -> {
									s.changeDestination(planets[nb_planet]);
								});
								c_squadron=-1;
							}
	//						if(s_clicked!=null) {
	//							notFound=false;
	//							s_clicked.changeDestination(planet);
	//							s_clicked=null;
	//						}
						}
						i++;
					}
					break;
//				case "MOUSE_DRAGGED":
//					gc.strokeLine(clicked.x, clicked.y, e.getX(), e.getY());
//					
				}
				
			}
		};

		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMousePressed(mouseHandler);
		scene.setOnMouseReleased(mouseHandler);

		MediaPlayer mediaPlayer = null;
		try {
			mediaPlayer = new MediaPlayer(new Media(getRessourcePathByName("sounds/Engine.mp4")));// Only format allowed
																									// in the context of
																									// the project (mp4)

		} catch (MediaException e) {
			// in case of a platform without sound capabilities
		}
		MediaPlayer mediaPlayerPffft = null;
		try {
			mediaPlayerPffft = new MediaPlayer(new Media(getRessourcePathByName("sounds/Explosion.mp4")));

		} catch (MediaException e) {
			// in case of a platform without sound capabilities
		}

		final MediaPlayer mediaPlayerFinalCopy = mediaPlayer;// final copies are needed to transmit to inner classes
//		final MediaPlayer mediaPlayerBoomFinalCopy = mediaPlayerPffft;

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				switch(e.getCode()) {
				case Z:
				case UP: if(percentage<1) {
					percentage+=0.05;
				}
				break;
				case S:
				case DOWN: if((int)(percentage*100)>0) {
					percentage-=0.05;
				}
				break;
				default:
//					spaceship.changeSpeed(e.getCode());
					if (mediaPlayerFinalCopy != null) {
						mediaPlayerFinalCopy.stop();
						mediaPlayerFinalCopy.play();
					}					
				}
			}
		});

		new AnimationTimer() {
			public void handle(long arg0) {
				gc.drawImage(space, 0, 0);

//				spaceship.updatePosition();

				for(Collection<Spaceship> squadron : t_ships){
					if(squadron!=null && !squadron.isEmpty()) {
						Iterator<Spaceship> it = squadron.iterator();
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
				
				int i=0;
				boolean end=true;
				while(i<10 && planets[i]!=null){
					planets[i].product();
					planets[i].IA(ATTACK, planets, t_ships);
					planets[i].render(gc);
					
					if(planets[i].getSide().isAdverse()){
						end=false;
					}
					
					i++;
				}
				if(end){
					gc.fillText("WIN", 300, 50);
//					Image yo= new Image(Planet.class.getResource("/images/mario.png").toString(), 50,50, false, false);
//					gc.drawImage(yo, 350, 50);
				}

				String txt = Math.round(percentage*100)+"%";
				gc.fillText(txt, 45, 30);
//				gc.setFill(Color.);
				gc.setTextAlign(TextAlignment.CENTER);
			}
		}.start();
	}
}