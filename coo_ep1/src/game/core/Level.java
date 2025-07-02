package game.core;

import game.entities.EnemyGeneric;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private int levelNumber;
    private long startTime;
    private long duration; // tempo da fase
    private List<EnemyGeneric> enemySpawnConfigs; // configurações de spawn específicas da fase
    private boolean isCompleted;

    public Level(int levelNumber, long duration) {
        this.levelNumber = levelNumber;
        this.duration = duration;
        this.enemySpawnConfigs = new ArrayList<>();
        this.isCompleted = false;
    }

    public void update(long currentTime, GameEngine gameEngine) {
        // Lógica para spawn de inimigos conforme cronograma da fase
        // Atualiza estado da fase, verifica se terminou
    }

    public void renderHUD() {
        // Exibe na tela o número da fase atual, pontuação, etc.
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    // Métodos para adicionar configurações de spawn, reiniciar fase, etc.
}
