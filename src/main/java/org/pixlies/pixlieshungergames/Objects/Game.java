package org.pixlies.pixlieshungergames.Objects;

import org.bukkit.entity.Player;
import org.pixlies.pixlieshungergames.Managers.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.pixlies.pixlieshungergames.PixliesHungergames.instance;

public class Game {
    public List<Player> playerList;

    public Game() {
        GameManager gameManager = new GameManager();
        gameManager.startCooldown();
        playerList = new ArrayList<>();
    }

}
