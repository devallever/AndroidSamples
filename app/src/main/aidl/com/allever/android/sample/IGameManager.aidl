// IGameManager.aidl
package com.allever.android.sample;
import com.allever.android.sample.Game;
interface IGameManager {
    List<Game>getGameList();
    void addGame(in Game game);
}
