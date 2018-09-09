package com.example.user.infowhirl2;

import Hose.GLGame;
import Hose.Vector2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    public static final float WORLD_HEIGHT = 300.0f;
    public static final float WORLD_WIDTH = 10.0f;
    public static GLGame game;
    public static final Vector2 gravity = new Vector2(0.0f, -12.0f);
    public float heightSoFar;
    public final WorldListener listener;
    public final Random rand;
    public int score;
    public int state;
    public List<Whirl> whirls = new ArrayList();
//The interface that will deal with handling meadiaplayer events
    public interface WorldListener {

        void upvote();

        void downvote();
    }
//generate the world/field
    public World(WorldListener listener) {
        this.listener = listener;
        this.rand = new Random();
        generateField();
        this.heightSoFar = 0.0f;
        this.score = 0;
        this.state = 0;
    }
//This method will ensure the array of whirl objects is refilled with each new activity
    private void generateField() {
        whirls.clear();
        for (int i = 0; i < InfoWhirl.getXxes().size(); i++) {
            this.whirls.add(i, new Whirl(((Integer) InfoWhirl.getXxes().get(i)).floatValue(), ((Integer) InfoWhirl.getXxes().get(i)).floatValue()));
        }
    }

    public void update(float deltaTime, float accelX) {
        updateWhirls(deltaTime);
    }
//call the individual whirl's behavior(depending on its upvotes and downvotes attributes)
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