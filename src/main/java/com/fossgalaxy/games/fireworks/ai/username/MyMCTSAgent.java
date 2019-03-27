package com.fossgalaxy.games.fireworks.ai.username;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fossgalaxy.games.fireworks.ai.iggi.Utils;
import com.fossgalaxy.games.fireworks.ai.mcts.MCTS;
import com.fossgalaxy.games.fireworks.state.GameState;
import com.fossgalaxy.games.fireworks.state.actions.Action;

public class MyMCTSAgent extends MCTS {
	
	/**
	 * This is where our neural network should be making the decisions 
	 * Right now it is getting the first action
	 * We want to pass the game set into a neural network and then make our decision based off of this 
	 */
	@Override
	protected Action selectActionForRollout(GameState state, int playerID) {
		Collection<Action> legalActions = Utils.generateActions(playerID, state);

        List<Action> listAction = new ArrayList<>(legalActions);
        Collections.shuffle(listAction);

        return listAction.get(0);
	}
	
	
}
