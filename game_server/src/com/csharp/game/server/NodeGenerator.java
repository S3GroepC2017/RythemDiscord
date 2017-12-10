package com.csharp.game.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RythemDiscord
 * @author Groep C#
 */
public class NodeGenerator
{
    /**
     * The accepted user inputs for the game
     */
    private List<Character> acceptedUserInputs = null;

    public NodeGenerator()
    {
        setPossibleInputKeys(4);
    }

    public NodeGenerator(int amountPossibleDifferentUserInputs)
    {
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
        Random random = new Random();

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

        ArrayList<Character> returnListAcceptedUserInputs = new ArrayList<Character>(); // new temp list to override class possible input list: "acceptedUserInputs"

        for (Integer i : chosenNumbers)
        {
            char c = potentialInputKeys.charAt(i);
            returnListAcceptedUserInputs.add(c); // get char from string and add to list
        }

        acceptedUserInputs = returnListAcceptedUserInputs; // override class list with local list
    }


    public char[] generateNode()
    {
        char[] outputUserArray = new char[5]; // set output array length

        Random random = new Random();

        for (int i = 0; i < outputUserArray.length; i++)
        {
            //loop amount of array length
            int randomSelectedChar = random.nextInt(acceptedUserInputs.size()); // selected random position from possible inputs
            outputUserArray[i] = acceptedUserInputs.get(randomSelectedChar); // add selected char to output array
        }
        return outputUserArray;
        //return new char[]{'a', 'a', 'a'};
    }
}
