package alien;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * 
 * @author cdiot
 * @author mcapdordy
 *
 */
public class Sprite {
	protected Image image; //image of the object
	protected double x; //value of coordinate on x-axis
	protected double y; //value of coordinate on y-axis
	private double xSpeed; //speed along the x-axis
	private double ySpeed; //speed along the y-axis
	private double width; //width of the object
	private double height; //height of the object
	private double maxX; //maximum value that x can take
	private double maxY; //maximum value that y can take
	protected Player side; //player, adverse, neutral

	/**
	 * 	Create a sprite
	 * 
	 * @param path the path in the file to get the image of the object
	 * @param width the width of the object
	 * @param height the height of the object
	 * @param maxX the maximum value on the x-axis
	 * @param maxY the maximum value on the y-axis
	 */
	public Sprite(String path, double width, double height, double maxX, double maxY) {
		image = new Image(path, width, height, false, false);
		this.width = width;
		this.height = height;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	/**
	 * Create the copy of an existing sprite
	 * 
	 * @param s the sprite we want to copy
	 */
	public Sprite(Sprite s) {
		image = s.image;
		width = s.width;
		height = s.height;
		maxX = s.maxX;
		maxY = s.maxY;
	}
	
	/**
	 * Create a sprite
	 * 
	 * @param width the width of the object
	 * @param height the height of the object
	 * @param maxX the maximum value on the x-axis
	 * @param maxY the maximum value on the y-axis
	 */
	public Sprite(double width, double height, double maxX, double maxY) {
		this.width = width;
		this.height = height;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	/**
	 * 
	 * @return the width of an object
	 */
	public double width() {
		return width;
	}

	/**
	 * 
	 * @return the height of an object
	 */
	public double height() {
		return height;
	}
	
	/**
	 * 
	 * @return the maximum value on the x-axis
	 */
	public double maxX() {
		return maxX;
	}
	
	/**
	 * 
	 * @return the maximum value on the y-axis
	 */
	public double maxY()
	{
		return maxY;
	}
	
	/**
	 * 
	 * @return the image of an object
	 */
	public Image image() {
		return image;
	}
	 /**
	  * 
	  * @return the player, owner of the object
	  */
	public Player getSide() {
		return side;
	}
	
	/**
	 * Give the object a side
	 * 
	 * @param side the side we want to give
	 */
	public void setSide(Player side){
		this.side = side;
	}
	
	/**
	 * Verify that x and y are valid, turn around if not
	 */
	public void validatePosition() {
		if (x + width >= maxX) {
			x = maxX - width;
			xSpeed *= -1;
		} else if (x < 0) {
			x = 0;
			xSpeed *= -1;
		}

		if (y + height >= maxY) {
			y = maxY - height;
			ySpeed *= -1;
		} else if (y < 0) {
			y = 0;
			ySpeed *= -1;
		}
	}

	/**
	 * 
	 * @param x the given value on the x-axis
	 * @param y the given value on the y-axis
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		validatePosition();
	}

	/**
	 * 
	 * @param xSpeed the given speed along the x-axis
	 * @param ySpeed the given speed along the y-axis
	 */
	public void setSpeed(double xSpeed, double ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	/**
	 * Update the position on x-axis and y-axis
	 */
	public void updatePosition() {
		x += xSpeed;
		y += ySpeed;
//		validatePosition();
	}

	/**
	 * Draw an object
	 * 
	 * @param gc the graphic content
	 */
	public void render(GraphicsContext gc) {
		gc.drawImage(image, x, y);
	}

	/**
	 * Verify if the object intersects an other
	 * 
	 * @param s the object we call
	 * @return true if the objects intersects an other, false if not
	 */
	public boolean intersects(Sprite s) {
		return ((x >= s.x && x <= s.x + s.width) || (s.x >= x && s.x <= x + width))
				&& ((y >= s.y && y <= s.y + s.height) || (s.y >= y && s.y <= y + height));
	}

	/**
	 * Write the coordinates of the sprite
	 */
	public String toString() {
		return "Sprite<" + x + ", " + y + ">";
	}
	
	/**
	 * Verify if a couple of coordinates belongs to a planet, used when we click
	 * 
	 * @param p the coordinates we want to check
	 * @return true if the point is in a planet, false if not
	 */
	public boolean isInterior(Point p) {
		Point center = new Point( x+width()/2, y+height()/2 );
		double distance = p.distance( center );
		return ( distance <= ( width()/2 ) );
	}
}
