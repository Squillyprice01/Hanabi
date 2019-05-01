package com.fossgalaxy.games.fireworks.ai;

import com.fossgalaxy.games.fireworks.GameRunner;
import com.fossgalaxy.games.fireworks.GameStats;
import com.fossgalaxy.games.fireworks.ai.AgentPlayer;
import com.fossgalaxy.games.fireworks.ai.mcts.MCTS;
import com.fossgalaxy.games.fireworks.ai.username.Genome;
import com.fossgalaxy.games.fireworks.ai.username.MyMCTSAgent;
import com.fossgalaxy.games.fireworks.players.Player;
import com.fossgalaxy.games.fireworks.utils.AgentUtils;
import com.fossgalaxy.stats.BasicStats;
import com.fossgalaxy.stats.StatsSummary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Game runner for testing.
 *
 * This will run a bunch of games with your agent so you can see how it does.
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException
    {
        int numPlayers = 4;
        int numGames = 10;
        int numGenerations = 50;
        int numIndividuals = 10;
        String agentName = "MyMCTSAgent";

        Random random = new Random();
        ArrayList<Genome> generationList = new ArrayList<Genome>();
        for(int generation = 1; generation <= numGenerations; generation++) {
            
        	File curGen = new File("C:\\Users\\pricew\\Hanabi\\generation" + generation + ".txt");
        	int nextGenIndex = generation + 1; 
            File nextGen = new File("C:\\Users\\pricew\\Hanabi\\generation" + nextGenIndex +".txt");
        	Scanner scan = new Scanner(curGen);
        	String generationInfo = "";
        	
        	for(int individual = 0; individual < numIndividuals; individual++) {
                
        		StatsSummary statsSummary = new BasicStats();
        		String genesString = scan.nextLine();
        		String[] genesArr = genesString.split(",");
        		double fitness = Double.parseDouble(genesArr[genesArr.length - 2]);
        		genesString = "";
        		for(int i = 0; i < genesArr.length - 3; i++) {
        			if(i == genesArr.length - 4) {
        				genesString += genesArr[i];
        			} else {
        				genesString += genesArr[i] +",";
        			}
        		}
        		generationInfo += genesString;
        		Genome genome = MyMCTSAgent.parse(genesString);
        		genome.setFitness(fitness);
        		generationList.add(genome);
        		
        		for (int game = 0; game < numGames; game++) {
		        	String agentArgs = "[" + genome.toString() + "]";	        	
		        	GameRunner runner = new GameRunner("test-game", numPlayers);
		            //add your agents to the game
		            for (int j=0; j<numPlayers; j++) {
		                // the player class keeps track of our state for us...
		                Player player = new AgentPlayer(agentName, AgentUtils.buildAgent(agentName+agentArgs));
		                runner.addPlayer(player);
		            }
		
		            GameStats stats = runner.playGame(random.nextLong());
		            statsSummary.add(stats.score);
		        }
		
		        //print out the stats
		        	System.out.println(String.format("Our agent: Avg: %f, min: %f, max: %f across %d runs",
		                statsSummary.getMean(),
		                statsSummary.getMin(),
		                statsSummary.getMax(),
		                statsSummary.getN()));
		        	generationInfo += "," + Math.floor(statsSummary.getMean()) + "," + Math.floor(statsSummary.getMin()) + "," + Math.floor(statsSummary.getMax()) + "\n";
		        	
		        }
        	
        	generationList = Genome.nextGeneration(generationList, 0.175, 0.2, 0.1);
        	String nextGenInfo = "";
        	Scanner scoreRead = new Scanner(generationInfo);
        	int current = 0;
        	while(scoreRead.hasNextLine()) {
        		String nextGenome = scoreRead.nextLine();
        		//System.out.println(nextGenome);
        		String[] genes = nextGenome.split(",");
        		int len = genes.length;
        		nextGenInfo += generationList.get(current).toString() + "," + genes[len - 3] + "," + genes[len - 2] + "," + genes[len - 1] + "\n";
        		current++;
        	}
        	scoreRead.close();
        	PrintStream f = new PrintStream(nextGen);
        	f.print(nextGenInfo);
        	f.close();
            scan.close();	
        }
        
    }
}
