import java.util.Random;

/*****************************************************************************/

//Program simulates opinion dynamics based on imitation
public class Voter
{
    private final int PLUS = 1;       //opinion one
    private final int MINUS = 2;      //opinion two

    private int[][] array;            //array of opinions
    private int size;                 //size of array
    private int iterations;           //number of iterations
    private double density;           //initial density of plus opinion
    private Random random;            //random number generator
    private DrawOpinion drawer;       //drawing object
    private double noise;			  //amount of "noise" in each iteration
    private int[][] influenceArray;   //array for external influence broadcasting
    private double influencePlus;	  //density of influence on plus opinion
    private double influenceMinus;	  //density of influence on minus opinion
    int plus; int minus;

    /*************************************************************************/

    //Constructor of Voter class
    public Voter(int size, int iterations, double density, int seed, double noise, double influencePlus, double influenceMinus)
    {
        this.array = new int[size][size];    //create array
        this.size = size;                    //set size
        this.iterations = iterations;        //set iterations
        this.density = density;              //set density
        this.random = new Random(seed);      //create random number generator
        this.drawer = new DrawOpinion(array, size); //create drawing object
        this.noise = noise;
        this.influenceArray = new int[size][size];
        this.influencePlus = influencePlus;
        this.influenceMinus = influenceMinus;
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
             //draw();

             //update population
             for (int m = 0; m < size*size; m++)
             {
                 //pick an agent randomly
                 int i = random.nextInt(size);
	             int j = random.nextInt(size);

                 //change opinion of agent to a neighbor's opinion
                 array[i][j] = imitate(i, j);
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
          initializeInfluence();
     }
     /************************************************************************/
     //method to initialize influence array only effecting plus opinion
     private void initializeInfluence() {
    	 for(int i = 0; i < size; i++)
    		 for(int j = 0; j < size; j++) {
    			 if(random.nextDouble() < influencePlus)
        			 influenceArray[i][j] = PLUS;
        		 else if(random.nextDouble() < influenceMinus)
        			 influenceArray[i][j] = MINUS;
        		 else {
        			 influenceArray[i][j] = 0; //initialize spots where no influence is with 0
        		 }
    		 }
     }

     /************************************************************************/

     //Method finds the opinion of a random neighbor
     private int imitate(int i, int j)
     {
          //pick a random neighbor
          double r = random.nextDouble();
          if(influenceArray[i][j] != 0) {
		      if (r < 0.2)
		          return array[(i+1)%size][j];        //opinion of south neighbor
		      else if (r < 0.4)
		          return array[(i-1+size)%size][j];   //opinion of north neighbor
		      else if (r < 0.6)
		          return array[i][(j+1)%size];        //opinion of east neighbor
		      else if (r < 0.8)
		          return array[i][(j-1+size)%size];   //opinion of west neighbor
		      else
		    	  return influenceArray[i][j]; 		  //external influence opinion
          }else {
        	  if (r < 0.25)
		          return array[(i+1)%size][j];        //opinion of south neighbor
		      else if (r < 0.5)
		          return array[(i-1+size)%size][j];   //opinion of north neighbor
		      else if (r < 0.75)
		          return array[i][(j+1)%size];        //opinion of east neighbor
		      else
		          return array[i][(j-1+size)%size];   //opinion of west neighbor
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
    	 if(array[i][j] == PLUS)
    		 array[i][j] = MINUS;
    	 else 
    		 array[i][j] = PLUS;
     }
     /************************************************************************/

     //Method draws array of opinions
     private void draw()
     {
         drawer.repaint();    //repaint array

         pause(100);          //pause for some time
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