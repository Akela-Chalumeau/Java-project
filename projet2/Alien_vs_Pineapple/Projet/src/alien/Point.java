package alien;

/**
 * 
 * @author cdiot
 * @author mcapdordy
 *
 */
public class Point {
	public double x; //value of coordinate on x-axis
	public double y; //value of coordinate on y-axis
	
	/**
	 * Create a point
	 *  
	 * @param x the value of coordinate on x-axis
	 * @param y the value of coordinate on y-axis
	 */
	public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
	/**
	 * Create the copy of a point
	 * 
	 * @param p a point
	 */
    public Point(Point p){
        this(p.x, p.y);
    }
	
    /**
     * Compute the distance between a given point and this
     * 
     * @param p a point
     * @return the distance between the point p and this point
     */
	public double distance(Point p){
        double d1 = p.x - x;
        double d2 = p.y - y;
        return Math.sqrt(d1*d1 + d2*d2);
   }
}
