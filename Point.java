public class Point{
	
	private double x = 0;
	private double y = 0;
	private int connections = 0;
	private int order = 0;
	private double distanceFromStart = 0;
	
	public Point(double _x, double _y, int _order){
		x = _x;
		y = _y;
		connections = 0;
		order = _order;
		
		distanceFromStart = Math.hypot(x-0, y-0);
	}
	
	public void addConnection(){
		connections ++;
	}
	
	public double x(){
		return x;
	}
	
	public double y(){
		return y;
	}
	
	public int connections(){
		return connections;
	}
	
	public int order(){
		return order;
	}
	
	public double dist(){
		return distanceFromStart;
	}
	
}