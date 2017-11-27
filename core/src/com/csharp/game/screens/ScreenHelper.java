package com.csharp.game.screens;

//TODO (global) helper functions from GameScreen to ScreenHelper
public class ScreenHelper
{
    public static int calculateHMargin(int index)
    {
        int margin = 60;

        for (int i = 0; i < index; i++)
        {
            margin += 100;
        }

        return margin;
    }

    public static int calculateVMargin(int index)
    {
        int margin = 900 - 120;

        for (int i = 0; i < index; i++)
        {
            margin -= 120;
        }

        return margin;
    }
}
