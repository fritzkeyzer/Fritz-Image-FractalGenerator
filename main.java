/*
FRITZ KEYZER
APRIL 25 2018

class saves a fractal png
settings found at top

use RunEm class to run this class in a batch mode

*/


import java.util.*;
import java.awt.Color;
import java.io.File;

public class main{
	
	private static int base = 7;				//(>2)			(number of splits at each node)
	private static int order = 7;				//(>2)			(order at which to end)
	
	private static double lineThickness = 0.001;//(0.001-0.02) 	(default 0.01) (line thickness)
	private static double lengthMod = 0.51;		//(0-1) 		(default =0.5) (length of line relative to order above it)
	
	private static float startHue = 0f;			//(0-1)			(default =0) (red =0 & =1) (blue =0.5)
	private static float endHue = 0.5f;			//(0-1)
	
	private static float saturation = 0.8f;		//(0-1)			(default =0.8) (WHITE =0)
	private static float luminance 	= 0.8f; 	//(0-1)			(1.0 for brighter, 0.0 for black)
	
	private static double randomAmount = 0;		//(0-1)			(add random factor to angle)
	
	private static int saveRes = 1000;			//WARNING! can take very long to save at high resolutions (use exitWhenDone to make sure thats its finished saving)
	
	private static int animatedRes = 300;		//animated resolution
	private static boolean reverse = false;		//reverse drawing order of lines
	private static boolean animate = false;		//WARNING! SLOW! watch as it grows
	private static boolean exitWhenDone = false;	//automatically exit application when done
	
	private static String folder = "auto/";
	private static File theDir = new File(folder);
	
	
	private static double thickCoeff = 0.5;
	
	
	private static ArrayList<Point> 		pointsList = 		new ArrayList<Point>();
	private static ArrayList<Connection> 	connectionsList = 	new ArrayList<Connection>();
	private static boolean finished = false;
	
	private static boolean init = false;
	
	public static void main(String[] args) {
		run(base, order, lineThickness, lengthMod, startHue, endHue, saturation, luminance, randomAmount, reverse, saveRes);
		if (exitWhenDone){
			System.exit(0);
		}
		saveFile();
		
	}
	
	public static void run(int _base, int _order, double  _lineThick, double _lenMod, float _startHue, float _endHue, float _sat, float _lum, double _random, boolean _reverse, int _saveRes){
		
		base = _base;
		order = _order;
		lineThickness = _lineThick;
		lengthMod = _lenMod;
		startHue = _startHue;
		endHue = _endHue;
		saturation = _sat;
		luminance = _lum;
		randomAmount = _random;
		reverse = _reverse;
		saveRes = _saveRes;
		
		pointsList.clear();
		connectionsList.clear();
		pointsList.add(new Point(0,0,0));
		StdDraw.clear(StdDraw.BLACK);
		
		finished = false;
		
		while(!finished){
			if (!init){
				init();
				init = true;
			}
			addLine();
		}
		superRes();
		updateScreen();
	}
	
	public static void saveFile(){
		
		System.out.println("SAVING");
		
		theDir.mkdir();
		
		String name = "";
		name +=  "base="+base;
		name += " order="+order;
		name += " thick="+lineThickness;
		name += " lengthMod="+lengthMod;
		name += " startHue="+startHue;
		name += " endHue="+endHue;
		name += " sat="+saturation;
		name += " lum="+luminance;
		name += " random="+randomAmount;
		name += " rev="+reverse;
		name += " res="+saveRes;
		
		StdDraw.save(folder+name+".png");
	}
	
	public static void init(){
		//add starting point
		pointsList.clear();
		connectionsList.clear();
		pointsList.add(new Point(0,0,0));
		
		//init gfx
		StdDraw.enableDoubleBuffering();
		if (animate){
			StdDraw.setCanvasSize(animatedRes, animatedRes);
		}
		else{
			StdDraw.setCanvasSize(saveRes, saveRes);
		}
		
		StdDraw.setScale(-5, 5);
		StdDraw.clear(StdDraw.BLACK);
	}
	
	private static void superRes(){
		
		//find max and min coordinates:
		
		double xMax = 0;
		double xMin = 0;
		double yMax = 0;
		double yMin = 0;
		
		for (Connection _theConn: connectionsList) {
			double x0 = _theConn.from().x();
			double y0 = _theConn.from().y();
			double x1 = _theConn.to().x();
			double y1 = _theConn.to().y();
			
			if (x0 > xMax){xMax = x0;}
			if (y0 > yMax){yMax = y0;}
			if (x0 < xMin){xMin = x0;}
			if (y0 < yMin){yMin = y0;}
			if (x1 > xMax){xMax = x1;}
			if (y1 > yMax){yMax = y1;}
			if (x1 < xMin){xMin = x1;}
			if (y1 < yMin){yMin = y1;}
		}
		
		double scale = 99;
		
		if (xMax-xMin > yMax-yMin){
			scale = xMax-xMin;
			yMin = ((yMax+yMin)/2)-0.5*scale;
			yMax = ((yMax+yMin)/2)+0.5*scale;
		}
		else{
			scale = yMax-yMin;
			xMin = ((xMax+xMin)/2)-0.5*scale;
			xMax = ((xMax+xMin)/2)+0.5*scale;
		}
		
		//StdDraw.enableDoubleBuffering();
		if (animate){
			StdDraw.setCanvasSize(saveRes, saveRes);
		}
		
		StdDraw.setXscale(xMin, xMax);
		StdDraw.setYscale(yMin, yMax);
		StdDraw.clear(StdDraw.BLACK);
	}
	
	private static void updateScreen(){
		StdDraw.clear(StdDraw.BLACK);
		//draw each connection
		if (reverse){
			Collections.reverse(connectionsList);
		}
		
		
		for (Connection _theConn: connectionsList) {
			float fraction = (float)(_theConn.from().order())/(order-1);
			StdDraw.setPenColor(correctColor(fraction));
			StdDraw.setPenRadius(lineThickness*Math.pow(thickCoeff,_theConn.to().order()));
			
			double x0 = _theConn.from().x();
			double y0 = _theConn.from().y();
			double x1 = _theConn.to().x();
			double y1 = _theConn.to().y();
			
			StdDraw.line(x0, y0, x1, y1);
			if (animate){
				StdDraw.show();
			}
		}
		
		StdDraw.show();
	}
	
	private static void addLine(){
		Point chosenOne = new Point(0,0,0);
		boolean found = false;
		boolean continueSearch = true;
		
		pointsList.trimToSize();
		
		int i = 0;
		while(i < pointsList.size()){
			if (pointsList.get(i).connections() < base && pointsList.get(i).order() < order){
				chosenOne = pointsList.get(i);
				found = true;
				continueSearch = false;
			}
			i ++;
			
			if (i > pointsList.size()){
				continueSearch = false;
			}
		}
		
		if (found){
			Point pointPicked = chosenOne;
			
			double equalAngle = pointPicked.connections()*(Math.PI*2/base);
			double orderOffset = (pointPicked.order()%2)*(Math.PI/base);
			
			double angle = equalAngle + orderOffset + randomAmount*Math.random()*Math.PI*2;
			double connLength = Math.pow(lengthMod, pointPicked.order());
			
			double x = pointPicked.x()+Math.cos(angle)*connLength;
			double y = pointPicked.y()+Math.sin(angle)*connLength;
			
			Point newPoint = new Point(x, y, pointPicked.order()+1);
			
			pointsList.add(newPoint);
			connectionsList.add(new Connection(pointPicked, newPoint));
			pointPicked.addConnection();
		}
		else{
			finished = true;
		}
		
	}
	
	private static Color correctColor(float _fraction){
		
		
		
		//to get rainbow, pastel colors
		Random random = new Random();
		float hue = _fraction*(endHue-startHue) + startHue;
		Color color = Color.getHSBColor(hue, saturation, luminance);
		
		return color;
	}
	
}