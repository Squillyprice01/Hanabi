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
        int numGames = 1;
        int numGenerations = 5;
        int numIndividuals = 2;
        String agentName = "MyMCTSAgent";

        Random random = new Random();
        ArrayList<Genome> generationList = new ArrayList<Genome>();
        for(int generation = 1; generation <= numGenerations; generation++) {
            File curGen = new File("C:\\Users\\pricew\\Hanabi\\generation" + generation + ".txt");
        	int nextGenIndex = generation + 1; 
            File nextGen = new File("C:\\Users\\pricew\\Hanabi\\generation" + nextGenIndex +".txt");
        	Scanner scan = new Scanner(curGen);
        	//PrintStream f = new PrintStream(curGen);
     		//System.setOut(f);
        	String generationInfo = "";
        	for(int individual = 0; individual < numIndividuals; individual++) {
                
        		StatsSummary statsSummary = new BasicStats();
        		String genes = scan.nextLine();
        		double fitness = Double.parseDouble(genes.substring(genes.length() - 7, genes.length()- 4));
        		genes = genes.substring(0, genes.length() - 12);
        		generationInfo += genes;
        		Genome genome = MyMCTSAgent.parse(genes);
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
        	
        	generationList = Genome.nextGeneration(generationList, 0.175, 0.2, 0);
        	String nextGenInfo = "";
        	Scanner scoreRead = new Scanner(generationInfo);
        	for(int i = 0; i < generationList.size(); i++) {
        		String temp = scoreRead.nextLine();
        		temp = temp.substring(temp.length() - 12);
        		nextGenInfo += generationList.get(i).toString() + temp + "\n";
        	}
        	scoreRead.close();
        	PrintStream f = new PrintStream(nextGen);
        	f.print(nextGenInfo);
        	f.close();
            scan.close();	
        }
        
    }
}
