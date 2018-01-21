package com.csharp.game.screens.game.screens;

//TODO (global) helper functions from GameScreen to ScreenHelper
public class ScreenHelper {
    public static int[] calculateKeyframe(int playerIndex) {
        switch (playerIndex) {
            case 0:
                return new int[]{40, 50};
            case 1:
                return new int[]{40, 190};
            case 2:
                return new int[]{40, 330};
            case 3:
                return new int[]{40, 470};
            default:
                return null;
        }
    }

    public static int[] calculatePlayerKeysMargin(int playerIndex, int key) {
        int x = 45;
        int y;

        switch (playerIndex) {
            case 0:
                for (int i = 1; i <= key; i++) {
                    x += 100;
                }
                y = 60;
                return new int[]{x, y};
            case 1:
                for (int i = 1; i <= key; i++) {
                    x += 100;
                }
                y = 200;
                return new int[]{x, y};
            case 2:
                for (int i = 1; i <= key; i++) {
                    x += 100;
                }
                y = 340;
                return new int[]{x, y};
            case 3:
                for (int i = 1; i <= key; i++) {
                    x += 100;
                }
                y = 480;
                return new int[]{x, y};
            default:
                return null;
        }
    }
}
