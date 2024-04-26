
//Program tests group rule opinion dynamics
public class GroupTester
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
	   Group[] array = new Group[10];
	   for(int i=0; i<10; i++) {
			array[i] = new Group(100, 1000, .5, seed, .1, .3, noise);
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