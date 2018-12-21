package alien;

public class Point {
	public double x;
	public double y;
	
	public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Point(Point p){
        this(p.x, p.y);
    }
	
	public double distance(Point p){
        double d1 = p.x - x;
        double d2 = p.y - y;
        return Math.sqrt(d1*d1 + d2*d2);
   }
}
