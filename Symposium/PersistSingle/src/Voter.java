
import java.util.Random;

/*****************************************************************************/

//Program simulates opinion dynamics based on imitation
public class Voter
{
    private final int PLUS = 1;       //opinion one
    private final int MINUS = 2;      //opinion two
    private final int PERSIST = 3;    //persisting minority opinion

    private int[][] array;            //array of opinions
    private int size;                 //size of array
    private int iterations;           //number of iterations
    private double density;           //initial density of plus opinion
    private Random random;            //random number generator
    private DrawOpinion drawer;       //drawing object
    private double persistDensity;    //density of persisting minority
    private double noise;
    int plus; int minus;

    /*************************************************************************/

    //Constructor of Voter class
    public Voter(int size, int iterations, double density, int seed, double persistDensity, double noise)
    {
        this.array = new int[size][size];    //create array
        this.size = size;                    //set size
        this.iterations = iterations;        //set iterations
        this.density = density;              //set density
        this.random = new Random(seed);      //create random number generator
        this.drawer = new DrawOpinion(array, size); //create drawing object
        this.persistDensity = persistDensity;
        this.noise = noise;
        plus = 0; minus = 0;
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

                 //change opinion of agent to a neighbor's opinion
	             if(array[i][j] != PERSIST) {
	            	 array[i][j] = imitate(i, j);
	             }
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
                  //assign plus or minus opinion
                  if (random.nextDouble() < density)
                      array[i][j] = PLUS;
                  else
                	  array[i][j] = MINUS;
              }
          initializePersist(); //fills minus with some persisting minorities
     }
     
     /************************************************************************/
     
     //method to initialize persisting minorities
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

     //Method finds the opinion of a random neighbor
     private int imitate(int i, int j)
     {
          //pick a random neighbor
          double r = random.nextDouble();

          if (r < 0.25)
        	  if(array[(i+1)%size][j] == PLUS) 		//check if south opinion is plus
        		  return array[(i+1)%size][j]; 		//return opinion of south neighbor
        	  else 							   		//else, south is minus or persist
        		  return MINUS;        		   		//return minus so persist does not spread
          else if (r < 0.5)
        	  if(array[(i-1+size)%size][j] == PLUS)	//check if north opinion is plus
        		  return array[(i-1+size)%size][j]; //return opinion of north neighbor
        	  else									//else, south is minus or persist
        		  return MINUS;						//return minus so persist does not spread
          else if (r < 0.75)
        	  if(array[i][(j+1)%size] == PLUS)		//check if east opinion is plus
        		  return array[i][(j+1)%size];		//return opinion of east neighbor
        	  else									//else, east is minus or persist
        		  return MINUS;						//return minus so persist does not spread
          else
        	  if(array[i][(j-1+size)%size] == PLUS) //check if west opinion is plus
        		  return array[i][(j-1+size)%size];	//return opinion of west neighbor
        	  else									//else, west is minus or persist
        		  return MINUS;						//return minus so persist does not spread
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

         pause(500);          //pause for some time
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