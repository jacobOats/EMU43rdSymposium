
import java.util.Random;

/*****************************************************************************/

//Program simulates opinion dynamics based on majority rule
public class Majority
{
    private final int PLUS = 1;       //opinion one
    private final int MINUS = 2;      //opinion two
    private final int PERSIST = 3;    //persisting opinion
    
    private int[][] array;            //array of opinions
    private int size;                 //size of array
    private int iterations;           //number of iterations
    private double density;           //initial density of plus opinion
    private Random random;            //random number generator
    private DrawOpinion drawer;       //drawing object
    private double persistDensity;	  //density of persisting opinion
    private double noise;
    int plus; int minus;
    
    /*************************************************************************/

    //Constructor of Majority class
    public Majority(int size, int iterations, double density, int seed, double persistDensity, double noise)
    {
        this.array = new int[size][size];    //create array
        this.size = size;                    //set size
        this.iterations = iterations;        //set iterations
        this.density = density;              //set density
        this.random = new Random(seed);      //create random number generator
        this.drawer = new DrawOpinion(array, size); //create drawing object
        this.persistDensity = persistDensity;
        this.noise = noise;
        minus = 0; plus = 0;
    }

    /*************************************************************************/

    //Method runs simulation
    public void run()
    {
        //initialize opinions
        initialize();

        //run iterations
        for (int n = 0; n < iterations; n++)
        {
             //draw array
             draw();

             //update population
             for (int m = 0; m < size*size; m++)
             {
                 //pick an agent randomly
                 int i = random.nextInt(size);
	             int j = random.nextInt(size);

                 //change opinion of agent to majority opinion of neighborhood
	             if(array[i][j] != PERSIST)
	            	 array[i][j] = majority(i, j);
             }
             for(int k=0;k<size*size;k++) {
	        	 //randomly check if location will be changed
	        	 if(random.nextDouble() < noise) {
	        		 //pick location
		        	 int i = random.nextInt(size);
		        	 int j = random.nextInt(size);
		             //change color
		        	 flipOpinion(i, j);
	        	 }
             }
        }
        printCountedNums();
    }

    /************************************************************************/

     //Method initializes opinions
     private void initialize()
     {
          //go thru all agents
          for (int i = 0; i < size; i++)
              for (int j = 0; j < size; j++)
              {
                  //assign plus/minus opinion
                  if (random.nextDouble() < density)
                      array[i][j] = PLUS;
                  else
                      array[i][j] = MINUS;
              }
          initializePersist();
     }

     /************************************************************************/
     //method to initialize persisting opinions
     
     private void initializePersist(){
    	 //iterate through array
    	 for (int i = 0; i < size; i++)
             for (int j = 0; j < size; j++)
             {
                 //assign persisting opinion to array[i][j]
                 if (random.nextDouble() < persistDensity && array[i][j] == MINUS)
                     array[i][j] = PERSIST;
             }
     }
     /************************************************************************/

     //Method finds the majority opinion of a neighborhood
     private int majority(int i, int j)
     {
          int plus = 0;                          //initial count of opinions
          int minus = 0;

          if (array[i][j] == PLUS)               //find opinion of middle agent
              plus++;
          else
              minus++;

	      if (array[(i+1)%size][j] == PLUS)      //find opinion of south agent
		      plus += 1;
	      else
		      minus += 1;
	      
          if (array[(i-1+size)%size][j] == PLUS) //find opinion of north agent
		       plus += 1;
	      else
		       minus += 1;
	       
          if (array[i][(j+1)%size] == PLUS)      //find opinion of east agent
		      plus += 1;
	      else
		      minus += 1;

	      if (array[i][(j-1+size)%size] == PLUS) //find opinion of west agent
		      plus += 1;
	      else
		      minus += 1;   

          if (plus > minus)                      //find majority opinion by
              return PLUS;                       //comparing two opinions
          else if (plus < minus)
              return MINUS;
          else
          {
              if (random.nextDouble() < 0.5)     //tie is broken randomly
                 return PLUS;
              else
                 return MINUS;
          }
     }
     
     /************************************************************************/
   //method to print the ending numbers for plus and minus
     private void printCountedNums() {
    	 for(int i=0;i<size;i++) {
    		 for(int j=0;j<size;j++) {
    			 if(array[i][j] == PLUS)
    				 plus++;
    			 else
    				 minus++;
    		 }
    	 }
    	 System.out.println("Plus: "+ plus + " Minus: " + minus);
     }

     /************************************************************************/
     //method to flip color for noise
     private void flipOpinion(int i, int j) {
    	 if(array[i][j] != PERSIST) {
	    	 if(array[i][j] == PLUS)
	    		 array[i][j] = MINUS;
	    	 else 
	    		 array[i][j] = PLUS;
    	 }
     }
     /************************************************************************/

     //Method draws array of opinions
     private void draw()
     {
         drawer.repaint();    //repaint array

         pause(500);         //pause for some time
     }

     /************************************************************************/

     //Method pauses program for some milliseconds
     private void pause(int milliseconds)
     {
         try 
         {
             Thread.sleep(milliseconds);
         }
         catch(InterruptedException e)
         {
             System.exit(0);
         }
     }

     /*************************************************************************/
}