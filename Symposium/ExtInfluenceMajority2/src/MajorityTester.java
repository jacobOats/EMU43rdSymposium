
//Program tests majority rule opinion dynamics
public class MajorityTester
{
   public static void main(String[] args)
   {
	   double noise = 0.0;
	   int seed = 21576;
      //create a majority object
	   int sumPlus = 0;
	   int sumMinus = 0;
	   double avgPlus = 0.0;
	   double avgMinus = 0.0;
      //run simulation
	   Majority[] array = new Majority[10];
	   for(int i=0; i<10; i++) {
			array[i] = new Majority(100, 1000, .5, seed, noise, .1, .5);
			array[i].run();
			sumPlus += array[i].plus;
			sumMinus += array[i].minus;
		    seed += 120;
	   }
	   avgPlus = sumPlus/10;
	   avgMinus = sumMinus/10;
	   System.out.println("Plus Average: "+avgPlus);
	   System.out.println("Minus Average: "+avgMinus);
   }  
}  
