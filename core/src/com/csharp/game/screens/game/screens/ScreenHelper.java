package com.csharp.game.screens.game.screens;

//TODO (global) helper functions from GameScreen to ScreenHelper
public class ScreenHelper {
    public static int[] calculateKeyframe(int playerIndex) {
        switch (playerIndex) {
            case 0:
                return new int[]{50, 50};
            case 1:
                return new int[]{50, 190};
            case 2:
                return new int[]{50, 300};
            case 3:
                return new int[]{50, 410};
            default:
                return null;
        }
    }

    public static int[] calculatePlayerKeysMargin(int playerIndex, int key) {
        int x = 60;
        int y;

        switch (playerIndex) {
            case 0:
                for (int i = 0; i < key; i++) {
                    x += 100;
                }
                y = 60;
                return new int[]{x, y};
            case 1:
                for (int i = 0; i < key; i++) {
                    x += 100;
                }
                y = 200;
                return new int[]{x, y};
            case 2:
                for (int i = 0; i < key; i++) {
                    x += 100;
                }
                y = 310;
                return new int[]{x, y};
            case 3:
                for (int i = 0; i < key; i++) {
                    x += 100;
                }
                y = 420;
                return new int[]{x, y};
            default:
                return null;
        }
    }
}
