package com.example.user.infowhirl2;

import GameHose.GameObject;

public class Whirl extends GameObject {
    public static final float BOB_HEIGHT = 0.8f;
    public static final float BOB_WIDTH = 0.8f;
    public int lives = 0;
    int state;
    float stateTime = 0.0f;

    public Whirl(float x, float y) {
        super(x, y, 0.8f, 0.8f);
    }

    public void update(float deltaTime) {
        this.stateTime += deltaTime;
    }
}