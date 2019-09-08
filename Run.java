public class Run{
	
	private static boolean infinite = true;	//run for infinity?
	private static int numberOfRandom = 5;		//or for how many?
	private static boolean saveEach = false;	//save each one?
	
	private static int minBase = 5;
	private static int maxBase = 5;
	private static int minLines = 1024;
	private static int maxLines = 16384;
	private static int maximumOrder = 9;
	private static double minThickness = 0.001;
	private static double maxThickness = 0.1;
	private static double minLenMod = 1;
	private static double maxLenMod = 2;
	
	
	private static float minHue = 0f;
	private static float maxHue = 1f;
	private static float minSat = 0.7f;
	private static float maxSat = 0.8f;
	private static float minLum = 0.8f;
	private static float maxLum = 0.9f;
	
	
	private static double random = 0;
	private static int res = 1000;
	private static boolean allowReverseRandomly = true;
	private static boolean reverse = false;
	
	
	public static void main(String[] args){
		int i = 0;
		while (i >= 0){
			makeANewOne();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (saveEach){
				main.saveFile();
			}
			
			i ++;
			if (!infinite && i >= numberOfRandom){
				i = -1;
			}
		}
		
		System.exit(0);
	}
	
	
	private static void makeANewOne(){
		int base = (int)((maxBase-minBase)*Math.pow(Math.random(), 2)+minBase);
		
		int maxOrder = (int)(Math.log10(maxLines)/Math.log10(base));
		int minOrder = (int)Math.ceil((Math.log10(minLines)/Math.log10(base)));
		
		int order = (int)((maxOrder-minOrder)*Math.pow(Math.random(), 1)+minOrder);
		if (order > maximumOrder){order = maximumOrder;}
		
		double lineThickness = ((maxThickness-minThickness)*Math.pow(Math.random(), base) + minThickness);
		double lengthMod = ((maxLenMod-minLenMod)*Math.random()+minLenMod);
		float startHue = (float)((maxHue-minHue)*Math.random()+minHue);
		float endHue = (float)((maxHue-minHue)*Math.random()+minHue);
		float saturation = (float)((maxSat-minSat)*Math.random()+minSat);
		float luminance = (float)((maxLum-minLum)*Math.random()+minLum);
		
		if (allowReverseRandomly){
			if (Math.random()>0.5){
				reverse = true;
			}
			else{
				reverse = false;
			}
		}
		
		String args = "";
		args +=  "base="+base;
		args += " order="+order;
		args += " thick="+lineThickness;
		args += " lengthMod="+lengthMod;
		args += " startHue="+startHue;
		args += " endHue="+endHue;
		args += " sat="+saturation;
		args += " lum="+luminance;
		args += " random="+random;
		args += " rev="+reverse;
		args += " res="+res;
		
		System.out.println(args);
		
		main.run(base, order, lineThickness, lengthMod, startHue, endHue, saturation, luminance, random, reverse, res);
	}
	
}