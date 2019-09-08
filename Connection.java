public class Connection{
	
	private Point from = new Point(0,0,-1);
	private Point to = new Point(0,0,-1);
	private double direction = 0;
	
	public Connection(Point _from, Point _to){
		from = _from;
		to = _to;
		
		direction = Math.atan2(to.y()-from.y(),to.x()-from.x());
	}
	
	public Point from(){
		return from;
	}
	
	public Point to(){
		return to;
	}
	
	public double direction(){
		return direction;
	}
	
}