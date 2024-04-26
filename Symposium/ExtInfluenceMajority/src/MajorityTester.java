
//Program tests majority rule opinion dynamics
public class MajorityTester
{
   public static void main(String[] args)
   {
        //create majority object
	   Majority m = new Majority(100, 1000, .9, 438576, 0, .021);
		   
	   m.run();
   }  
}  
