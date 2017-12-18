package com.csharp.game.server;

import com.csharp.sharedclasses.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RythemDiscord
 *
 * @author Groep C#
 */
public class NodeGenerator
{
    /**
     * The Random object to generate pseudo-random numbers for assigning the nodes to the players
     */
    private final Random random = new Random();

    /**
     * The accepted user inputs for the game
     */
    private List<Character> acceptedUserInputs = null;

    public NodeGenerator()
    {
        this(5);
    }

    public NodeGenerator(int amountPossibleDifferentUserInputs)
    {
        if (amountPossibleDifferentUserInputs <= 0)
        {
            throw new IllegalArgumentException("There was an illegal argument in the constructor");
        }
        setPossibleInputKeys(amountPossibleDifferentUserInputs);
    }

    /**
     * Generates and Sets the value for the accepted user inputs
     *
     * @param amountPossibleDifferentUserInputs must be a positive number
     * @output
     */
    private void setPossibleInputKeys(int amountPossibleDifferentUserInputs)
    {
        String potentialInputKeys = "qwertyuiopasdfghjklzxcvbnm"; // all values from which can be chosen

        List<Integer> chosenNumbers = new ArrayList<>(); //temp arraylist to keep track of the chosen string possitions

        for (int i = 0; i < amountPossibleDifferentUserInputs; i++)
        {
            //loop for the amount of chars what can be used in the generation
            int tempChosen = random.nextInt(potentialInputKeys.length());
            if (chosenNumbers.contains(tempChosen))
            {
                // if number has already been chosen, subtract and don't add
                i--;
            }

            else
            {
                chosenNumbers.add(tempChosen); // if not already chosen, add to list
            }
        }

        ArrayList<Character> returnListAcceptedUserInputs = new ArrayList<>(); // new temp list to override class possible input list: "acceptedUserInputs"

        for (Integer i : chosenNumbers)
        {
            char c = potentialInputKeys.charAt(i);
            returnListAcceptedUserInputs.add(c); // get char from string and add to list
        }

        acceptedUserInputs = returnListAcceptedUserInputs; // override class list with local list
    }


    public List<Player> generateNode(List<Player> players)
    {
        // Change this number to the amount of keys that have to be pressed
        final int amountOfNodesForSequence = 5;

        // Get a new set of possible keys
        setPossibleInputKeys(amountOfNodesForSequence);

        // Create arrays for the players
        char[][] listOfPlayerLists = new char[players.size()][];

        for (int i = 0; i < players.size(); i++)
        {
            listOfPlayerLists[i] = new char[amountOfNodesForSequence];
        }

        /*
            Add nodes to the lists
            Now the lists are hardcoded to have 'amountOfNodesForSequence' nodes each
        */
        for (int i = 0; i < amountOfNodesForSequence; i++)
        {
            // Add to one player the node, and give the other players ' '
            int playerToReceiveNode = random.nextInt(players.size());
            listOfPlayerLists[playerToReceiveNode][i] = acceptedUserInputs.get(i);

            for (int j = 0; j < listOfPlayerLists.length; j++)
            {
                if (playerToReceiveNode != j)
                {
                    listOfPlayerLists[j][i] = ' ';
                }
            }
        }

        // Add the created Arrays to the players
        for (int i = 0; i < players.size(); i++)
        {
            players.get(i).setNodeList(listOfPlayerLists[i]);
        }

        return players;
    }
}
