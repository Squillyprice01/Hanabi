# Hanabi MCTS Evolution

Hanabi is a cooperative card game with an interesting partial observability mechanic. Players cannot see the cards in their own hand. Instead, they rely on the other players to tell them information about their hand. Our agent relies on Monte Carlo Tree Search in order to play the game. We use evolutionary computation to learn a more efficient tree search rule. Feel free to evolve your own agent or modify the features!

## Table of Contents

- [Usage](#usage)
- [Support](#support)

## Usage

To run this program, maven install the main directory. From there, simply run App.java.
Some useful parameters in App.java:

  - numGames: the number of times an individual agent is evaluated
  - numGenerations: the number of generations evaluated
  - numIndividuals: the number of individuals per generation.

To create the starting generation, run Hanabi/src/main/java/util/GenerateGeneration.java. The number of individuals must match the parameter in this file.

Features for our agent are located in Hanabi/src/main/java/com/fossgalaxy/games/fireworks/ai/username/MyMCTSAgent.java.

## Support

We intend to work on this project in the future. To contact us, send a carrier pigeon somewhere and pray. If you really want to get in touch with us, message this github account and we will set up a line of communication.

