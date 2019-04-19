import java.lang.Math;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

// one class needs to have a main() method
public class Genome
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
    
  // arguments are passed using the text field below this editor
  public static void testMethods()
  {
    System.out.println("Testing vectorMutate:\nPass? " + testVectorMutate() + "\n");
    System.out.println("Testing tournamentSelection:\nPass? " + testTournamentSelection());
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
	
  public static ArrayList<Genome> nextGeneration(ArrayList<Genome> population, double mutationRate) { // Elitism was in the paramater with default value of 0.1 (cant do this in java??)
    double elitism = 0.1;
    ArrayList<Genome> childpop;
    ArrayList<Genome> populationCopy = (ArrayList<Genome>) population.clone(); // copy so as to not change the original
    populationCopy = populationCopy.sort(); // //compareToMethod // sort an array list based off of the genomes second element
    int valsToCopy = (int) Math.floor(elitism*(population.size()));
    for (int i = 0; i < valsToCopy; i++) {
      childpop.add(populationCopy.get(i));
    }
    for (int j = valsToCopy; j < population.size(); j++) {
      childpop.add(getOffspring(population, mutationRate));
    }
    return childpop;
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
