import java.lang.Math;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

// one class needs to have a main() method
public class Genome implements Comparable<Genome>
{
  double[] genome;
  double fitness;
  
  public Genome(double[] genome){
      this.genome = genome;
      this.fitness = -1;
  }
  
  public Genome(double[] genome, double fitness){
      this.genome = genome;
      this.fitness = fitness;
  }
  
  public double[] getGenome(){
      return this.genome;
  }
  
  public void setGenome(double[] genome){
      this.genome = genome;
  }
  
  public double getFitness(){
      return this.fitness;
  }
  
  public void setFitness(double fitness){
      this.fitness = fitness;
  }
    
  public static void testMethods()
  {
    System.out.println("Testing vectorMutate:\nPass? " + testVectorMutate() + "\n");
    System.out.println("Testing tournamentSelection:\nPass? " + testTournamentSelection() + "\n");
	System.out.println("Testing generateOffspring:\nPass? " + testGenerateOffspring() + "\n");
	System.out.println("Testing nextGeneration:\nPass? " + testNextGeneration() + "\n");
  }

  public static double[] vectorMutate(double[] genome, double mutationRate, double sigma){
  	Random r = new Random();
  	System.out.println("Original Genome: " + Arrays.toString(genome));
  	for(int i = 0; i < genome.length; i++){
  	    if(flipCoin(mutationRate)){
  	        genome[i] += sigma*r.nextGaussian();
  	    }
  	}
  	System.out.println("New Genome: " + Arrays.toString(genome));
  	return genome;
  }
  public static boolean testVectorMutate(){
  	double[] genome = {1,2,3,4,5,6,7,8,9,10};
    double[] modifiedGenome = vectorMutate(genome, 1.0, 1.0);
    return Arrays.equals(genome, modifiedGenome);
  }
  
  //TODO: DO WE NEED THIS?
  //public static genome crossover()
  
  
  
  public static Genome tournamentSelection(ArrayList<Genome> population){
	Random r = new Random();
	int firstIndex = r.nextInt(population.size());
	int secondIndex = r.nextInt(population.size());
	while(firstIndex == secondIndex){
		secondIndex = r.nextInt(population.size());
	}
	Genome organism1 = population.get(firstIndex);
	Genome organism2 = population.get(secondIndex);
	
	if(organism1.fitness >= organism2.fitness){
		return organism1;
	} else {
		return organism2;
	}
  }
  public static boolean testTournamentSelection(){
	double[] genome1 = {1};
	double[] genome2 = {0};
	double fitness1 = 100;
	double fitness2 = 0;
	Genome organism1 = new Genome(genome1, fitness1);
	Genome organism2 = new Genome(genome2, fitness2);
	ArrayList<Genome> population = new ArrayList<Genome>();
	population.add(organism1);
	population.add(organism2);
	Genome bestGenome = tournamentSelection(population);
	System.out.println("first genome's fitness: " + organism1.fitness);
	System.out.println("second genome's fitness: " + organism2.fitness);
	return bestGenome.fitness == 100;
  }
	
  public static ArrayList<Genome> nextGeneration(ArrayList<Genome> population, double mutationRate, double sigma, double elitism) { 
    ArrayList<Genome> childpop = new ArrayList<Genome>();
    //ArrayList<Genome> populationCopy = (ArrayList<Genome>) population.clone(); // copy so as to not change the original
    //population = Collections.sort(population);
    int valsToCopy = (int) Math.floor(elitism*(population.size()));
    for (int i = 0; i < valsToCopy; i++) {
      childpop.add(population.get(i));
    }
    for (int j = valsToCopy; j < population.size(); j++) {
      childpop.add(generateOffspring(population, mutationRate, sigma));
    }
    return childpop;
  }
  public static boolean testNextGeneration(){
	double[] genome1 = {100};
	double[] genome2 = {200};
	double[] genome3 = {300};
	double[] genome4 = {400};
	double[] genome5 = {500};
	double fitness1 = 100;
	double fitness2 = 200;
	double fitness3 = 300;
	double fitness4 = 400;
	double fitness5 = 500;
	Genome organism1 = new Genome(genome1, fitness1);
	Genome organism2 = new Genome(genome2, fitness2);
	Genome organism3 = new Genome(genome3, fitness3);
	Genome organism4 = new Genome(genome4, fitness4);
	Genome organism5 = new Genome(genome5, fitness5);
	ArrayList<Genome> population = new ArrayList<Genome>();
	population.add(organism1);
	population.add(organism2);
	population.add(organism3);
	population.add(organism4);
	population.add(organism5);
	ArrayList<Genome> test1 = nextGeneration(population, 1.0, 0.0, 1.0);
	ArrayList<Genome> test2 = nextGeneration(population, 1.0, 100.0, 0.0);
	
	boolean test1Pass = true;
	if(test1.get(0).genome[0] != 100){
		test1Pass = false;
	}
	if(test1.get(1).genome[0] != 200){
		test1Pass = false;
	}
	if(test1.get(2).genome[0] != 300){
		test1Pass = false;
	}
	if(test1.get(3).genome[0] != 400){
		test1Pass = false;
	}
	if(test1.get(4).genome[0] != 500){
		test1Pass = false;
	}
	
	return test1Pass;
  }
	
  public static Genome generateOffspring(ArrayList<Genome> population){
	  return generateOffspring(population, 0.2, 1.0);
  }
  public static Genome generateOffspring(ArrayList<Genome> population, double mutationRate){
	return generateOffspring(population, mutationRate, 1.0);
  }
  public static Genome generateOffspring(ArrayList<Genome> population, double mutationRate, double sigma){
	Random r = new Random();
	int firstIndex = r.nextInt(population.size());
	int secondIndex = r.nextInt(population.size());
	while(firstIndex == secondIndex){
		secondIndex = r.nextInt(population.size());
	}
	Genome organism1 = population.get(firstIndex);
	Genome organism2 = population.get(secondIndex);
    if(organism1.fitness >= organism2.fitness){
		double[] newGenome = vectorMutate(organism1.genome, mutationRate, sigma);
		Genome newG = new Genome(newGenome, organism1.fitness);
		return newG;
	} else {
	    double[] newGenome = vectorMutate(organism2.genome, mutationRate, sigma);
		Genome newG = new Genome(newGenome, organism2.fitness);
		return newG;
	}
  }
  public static boolean testGenerateOffspring(){
	double[] genome1 = {1};
	double[] genome2 = {0};
	double fitness1 = 100;
	double fitness2 = 0;
	Genome organism1 = new Genome(genome1, fitness1);
	Genome organism2 = new Genome(genome2, fitness2);
	ArrayList<Genome> population = new ArrayList<Genome>();
	population.add(organism1);
	population.add(organism2);
	Genome bestGenome = generateOffspring(population, 1.0, 10.0);
	System.out.println("first genome's fitness: " + organism1.fitness);
	System.out.println("second genome's fitness: " + organism2.fitness);
	double[] testAgainst = {1}; 
	return (bestGenome.fitness == 100 && !Arrays.equals(bestGenome.genome, testAgainst));
  }
	
  public int compareTo(Genome other){
	if(this.fitness > other.fitness){
		return 1;
	} else if(this.fitness == other.fitness) {
		return 0;
	} else {
		return -1;
	}
  }
  
  public static boolean flipCoin(double p){
      Random r = new Random();
      return p >= r.nextDouble();
  }
  
}
