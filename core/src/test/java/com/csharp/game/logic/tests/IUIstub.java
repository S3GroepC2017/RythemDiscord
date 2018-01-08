package com.csharp.game.logic.tests;

import com.csharp.sharedclasses.DTOClientUpdate;
import com.csharp.sharedclasses.IAfterPosUpdateCallback;
import com.csharp.sharedclasses.KeyPressedResult;

public class IUIstub implements IAfterPosUpdateCallback
{
    public DTOClientUpdate lastCallbackUpdate;

    @Override
    public void callback(DTOClientUpdate callbackUpdate) {
        lastCallbackUpdate = callbackUpdate;
    }
}
