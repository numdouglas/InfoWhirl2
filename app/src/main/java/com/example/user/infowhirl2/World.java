package com.example.user.infowhirl2;

import GameHose.GLGame;
import GameHose.Vector2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    public static final float WORLD_HEIGHT = 300.0f;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_RUNNING = 0;
    public static final float WORLD_WIDTH = 10.0f;
    public static GLGame game;
    public static final Vector2 gravity = new Vector2(0.0f, -12.0f);
    public float heightSoFar;
    public final WorldListener listener;
    public final Random rand;
    public int score;
    public int state;
    public List<Whirl> whirls = new ArrayList();

    public interface WorldListener {
        void coin();

        void crack();

        void highJump();

        void hit();

        void jump();
    }

    public World(WorldListener listener) {
        this.listener = listener;
        this.rand = new Random();
        generateLevel();
        this.heightSoFar = 0.0f;
        this.score = 0;
        this.state = 0;
    }

    private void generateLevel() {
        for (int i = 0; i < InfoWhirl.getXxes().size(); i++) {
            this.whirls.add(i, new Whirl(((Integer) InfoWhirl.getXxes().get(i)).floatValue(), ((Integer) InfoWhirl.getXxes().get(i)).floatValue()));
        }
    }

    public void update(float deltaTime, float accelX) {
        updateWhirls(deltaTime);
    }

    private void updateWhirls(float deltaTime) {
        if (this.whirls.size() > 0) {
            for (int i = 0; i < InfoWhirl.getXxes().size(); i++) {
                try {
                    ((Whirl) this.whirls.get(i)).update(deltaTime);
                } catch (IndexOutOfBoundsException B) {
                    B.printStackTrace();
                }
            }
        }
    }

    private void checkCollisions() {
        checkTouchCollisions();
    }

    private void checkTouchCollisions() {
    }
}