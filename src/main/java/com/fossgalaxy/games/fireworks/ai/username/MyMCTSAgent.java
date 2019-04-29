package com.fossgalaxy.games.fireworks.ai.username;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.fossgalaxy.games.fireworks.ai.iggi.Utils;
import com.fossgalaxy.games.fireworks.ai.mcts.MCTS;
import com.fossgalaxy.games.fireworks.ai.mcts.MCTSNode;
import com.fossgalaxy.games.fireworks.state.CardColour;
import com.fossgalaxy.games.fireworks.state.GameState;
import com.fossgalaxy.games.fireworks.state.Hand;
import com.fossgalaxy.games.fireworks.state.actions.Action;
import com.fossgalaxy.games.fireworks.state.events.GameEvent;

public class MyMCTSAgent extends MCTS {
	
	private Genome genome;

	//public MyMCTSAgent(int roundLength, int rolloutDepth, int treeDepthMul, Genome genome) {
	//	super(roundLength, rolloutDepth, treeDepthMul);
	//	this.genome = genome;
    //}
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
		
		Collection<Action> legalActionsUtil = Utils.generateActions(playerID, state);
        List<Action> listAction = new ArrayList<>(legalActionsUtil);
        Collections.shuffle(listAction); // picks a random action

        for(int actionIndex = 0; actionIndex < listAction.size(); actionIndex++) {
        	GameState copy = state.getCopy();
            Action curr = listAction.get(actionIndex);
            List<GameEvent> events = curr.apply(playerID, copy);
            events.forEach(copy::addEvent);
            copy.tick();
            System.out.println("This state's sum is: " + sumArray(getFeatures(copy)));
        }

        return listAction.get(0);
    }
	
	private static double[] getFeatures(GameState state) {
		
		ArrayList<Double> featureList = new ArrayList<Double>();
		
		featureList.add((double) state.getInfomation());
		featureList.add((double) state.getLives());
		featureList.add((double) state.getScore());
		featureList.add((double) state.getTableValue(CardColour.RED));
		featureList.add((double) state.getTableValue(CardColour.BLUE));
		featureList.add((double) state.getTableValue(CardColour.GREEN));
		featureList.add((double) state.getTableValue(CardColour.ORANGE));
		featureList.add((double) state.getTableValue(CardColour.WHITE));
		
		//for every player
//		for(int player = 0; player < state.getPlayerCount(); player++) {
//			Hand curHand = state.getHand(player);
//			//for every card in the hand
//			for(int cardIndex = 0; cardIndex < state.getHandSize(); cardIndex++) {
//				//System.out.println("Player " + player +" has a card");
//				//append the color
//				if(curHand.getKnownColour(cardIndex) == null) {
//					//append zero
//					featureList.add((double) 0);
//				} else {
//					//append 1-5
//					CardColour color = curHand.getKnownColour(cardIndex);
//					switch(color) {
//						case RED:
//							featureList.add((double) 1);
//						case BLUE:
//							featureList.add((double) 2);
//						case GREEN:
//							featureList.add((double) 3);
//						case ORANGE:
//							featureList.add((double) 4);
//						case WHITE:
//							featureList.add((double) 5);
//						default:
//							featureList.add((double) -1);
//					}
//				}
//				
//				//append the value
//				if(curHand.getKnownValue(cardIndex) == null) {
//					featureList.add((double) 0);
//				} else {
//					featureList.add((double) curHand.getKnownValue(cardIndex));
//				}
//			}
//		}
	
//		int predictedLength = state.getPlayerCount() * state.getHandSize() + 8;
//		if(predictedLength == featureList.size()) {
//			System.out.println("they match");
//		} else {
//			System.out.println("they don't match");
//		}
		
//		System.out.println("The length of the feature map is: " + featureList.size());
		double[] featureArray = new double[featureList.size()];
		for(int i = 0; i < featureList.size(); i++) {
			featureArray[i] = featureList.get(i);
		}
		return featureArray;
	}
	
	private double sumArray(double[] arr) {
		double sum = 0.0;
		for(int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum;
	}
	
	private double dot(double[] arr1, double[] arr2) {
		if(arr1.length != arr2.length) {
			throw new IllegalStateException("dot product only defined when matricies have the same length");
		} else {
			double sum = 0.0;
			for(int i = 0; i < arr1.length; i++) {
				sum += arr1[i] * arr2[i];
			}
			return sum;
		}
	}
}
