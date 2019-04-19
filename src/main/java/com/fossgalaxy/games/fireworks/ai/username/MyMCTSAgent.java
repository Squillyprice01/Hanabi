package com.fossgalaxy.games.fireworks.ai.username;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.fossgalaxy.games.fireworks.ai.iggi.Utils;
import com.fossgalaxy.games.fireworks.ai.mcts.MCTS;
import com.fossgalaxy.games.fireworks.state.CardColour;
import com.fossgalaxy.games.fireworks.state.GameState;
import com.fossgalaxy.games.fireworks.state.actions.Action;

public class MyMCTSAgent extends MCTS {
	
	private Genome genome;

	public MyMCTSAgent(int roundLength, int rolloutDepth, int treeDepthMul, Genome genome) {
		super(roundLength, rolloutDepth, treeDepthMul);
		this.genome = genome;
    }
	/**
	 * This is where our neural network should be making the decisions 
	 * Right now it is getting the first action
	 * We want to pass the game set into a neural network and then make our decision based off of this 
	 */
	@Override
	protected Action selectActionForRollout(GameState state, int playerID) {
		
		/**
		 * NOTE - WE ARE NO LONGER DOING A HAND MADE HEURISTIC -- WE WILL BE DOING THE GENOME WITH FEATURE MAP FIRST (AND THEN COMPARE WITH THIS OPTION IF WE HAVE TIME)
		 * Goal: 
		 * First check if you can play a card safely. If you can play a card - then play the card
		 * Else - If you know a card you can discard a card - then discard the card
		 * else - If you cannot play a card or discard a card then tell a player something 
		 * about their hand as to maximize the amount of information we can tell (and that has not already been told before)
		 * Else - play a random move  
		 */
		
		// Get what is currently known (about everyone's hand)
		// For each action we can have
			// we generate the next state if we take some action
			// for the set up features we weigh it against our genome
			
		Collection<Action> legalActions = Utils.generateActions(playerID, state);

        List<Action> listAction = new ArrayList<>(legalActions);
        Collections.shuffle(listAction); // picks a random action
        
        ArrayList<ArrayList<Integer>> numberList = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<CardColour>> colorList = new ArrayList<ArrayList<CardColour>>();
        
        
        for (int numPlayers = 0; numPlayers < state.getPlayerCount(); numPlayers++) {
        	ArrayList<Integer> tempInt = new ArrayList<Integer>();
        	ArrayList<CardColour> tempColor = new ArrayList<CardColour>();
	        for (int card = 0; card < state.getHand(playerID).getSize(); card++) {
	            System.out.println("getting known value for player " + playerID + " in slot " + card + ": " + state.getHand(playerID).getKnownValue(card));
	            System.out.println("getting known color for player " + playerID + " in slot " + card + ": " + state.getHand(playerID).getKnownColour(card));           
	            
	        	// goal get info
//	        	tempInt.add(state.getHand(playerID).getKnownValue(card));
//	        	tempColor.add(state.getHand(playerID).getKnownColour(card));
//	        	System.out.println("tempInt " + tempInt.get(card));
	        }
//	        numberList.add(tempInt);
//	        colorList.add(tempColor);
        }
        
        // print the numList and colorList
//        for (int j = 0; j < numberList.size(); j++) {
//        	for (int i = 0; i < numberList.get(j).size(); i++) {
//        		System.out.print("numberList: " + numberList.get(i).get(j));
//     	        System.out.print("colorList: " + colorList.get(i).get(j));
//             }
//        }
        
//        PrintStream f = null;
//		try {
//			f = new PrintStream("./out.txt");
//			System.setOut(f);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
//        System.out.println("numberList " + numberList.get(0).get(0));
       
        // if the next player does not know anything about their hand - tell them something about their hand (preferably the most/best info)
//        if ()
        
        
        // Debug statements
//        Collection<Action> suitAc = Utils.generateSuitableActions(playerID, state);
//        System.out.println("actions? " + suitAc);
//        System.out.println("player: " + playerID + " list of actions " + listAction + " info " + state.getInfomation());
        return listAction.get(0);
	}
	
	
}
