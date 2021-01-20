
public class Nbody {
	public static void main(String[] args) {		
		double simulationTime = Double.parseDouble(args[0]); //time limit of simulation
		//since computers can't really simulate constant movement, we set a timestep
		//to a low value, and the particles update after every timestep. 
		double timeStep = Double.parseDouble(args[1]); 
		String filename = args[2]; //filename with universe information
		
		System.out.println("Simulation shown is: " + filename);
		
		//initialize input stream
		In file = new In(filename); 
		boolean b = file.isEmpty();
		int planets = file.readInt(); //first number in the file is number of planets
		double radius = file.readDouble(); //scale of universe

		double[] m = new double[planets]; //mass
		double[] px = new double[planets]; //position x/y
		double[] py = new double[planets];
		double[] vx = new double[planets]; //velocity x/y
		double[] vy = new double[planets];
		String[] img = new String[planets]; //tells us file name of planets used
		
		//draw a penndraw window and set its background
		PennDraw.setXscale(-radius, radius);
		PennDraw.setYscale(-radius, radius);
		PennDraw.picture(0, 0, "starfield.jpg");
	
		
		int i = 0;
		
		//read planet mass, position and file name and store it into array
		while (i < planets) {
			m[i] = file.readDouble();
			px[i] = file.readDouble();
			py[i] = file.readDouble();
			vx[i] = file.readDouble();
			vy[i] = file.readDouble();
			img[i] = file.readString();
			i++;
			
		}
	
	/* Debugging	
		i = 0;
		while (i < planets){
			System.out.print(m[i]);
			System.out.print(px[i]);
			System.out.print(py[i]);
			System.out.print(vx[i]);
			System.out.print(vy[i]);
			System.out.print(img[i]);
			System.out.println();
			i++;
		
		}*/
		
		
		i = 0;
		final double G = 6.67e-11; //gravitational constant
		
		//place planets at position read in file
		while (i < planets){
		PennDraw.picture(px[i], py[i], img[i] );
		i++;
		}
		
		PennDraw.enableAnimation(30); //30 is the framerate
		double elapsedtime = 0; 
		
		//StdAudio.play("2001.mid"); this line may or may not play audio depending on your computer
		
		//start simulation time loop
		while (elapsedtime < simulationTime){
			PennDraw.picture(0, 0, "starfield.jpg");
			for (int v = 0; v < planets; v++){
				double netfx = 0;
				double netfy = 0;
				for (int j = 0; j < planets; j++){
					if (v == j)
					{
						continue;
					}
					
					double dx = px[j] - px[v]; 
					double dy = py[j] - py[v];
					double d = Math.sqrt(dx*dx+dy*dy); //finding distance between two planets
					double f = ((G * m[v]) / (d * d)) * m[j]; //gravitational law to find force
					double fx = f*dx/d;
					double fy = f*dy/d;
					netfx = netfx + fx; 
					netfy = netfy + fy;
					
				}
				
				double ax = netfx / m[v]; //F = ma -> a = F/m
				double ay = netfy / m[v]; 
				//calculate speed lost or gained
				vx[v] = vx[v] + timeStep*ax;
				vy[v] = vy[v] + timeStep*ay;
				
			}
			//update positions
			for (int e = 0; e < planets; e++){
				px[e] = px[e] + timeStep*vx[e]; 
				py[e] = py[e] + timeStep*vy[e];
				
				PennDraw.picture(px[e], py[e], img[e]);
			}
			//move to the next frame
			PennDraw.advance();
			elapsedtime = elapsedtime + timeStep;		
		}
		}
	}
