package com.csharp.sharedclasses;

import java.util.List;

public interface IAfterPosUpdateCallback
{
    void afterCallback(int pos, KeyPressedResult result);
    void afterEndOfSequenceCallBack(List<Player> players);
}
