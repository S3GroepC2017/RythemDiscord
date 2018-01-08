package com.csharp.game.screens.ui.screens;

import com.csharp.sharedclasses.IAfterPosUpdateCallback;

public interface IMenuScreen extends IAfterPosUpdateCallback{
    void loadTextures();
    void createUiComponents();
}
