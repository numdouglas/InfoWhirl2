package com.example.user.infowhirl2;

import GameHose.DynamicObject;
import GameHose.GameObject;

/**
 * Created by user on 10/20/2017.
 */

public class Whirl extends GameObject {
    int state;
    public static final float BOB_WIDTH = 0.8f;
    public static final float BOB_HEIGHT = 0.8f;
    public  int lives;
    float stateTime;
    public Whirl(float x, float y) {
        super(x, y, BOB_WIDTH, BOB_HEIGHT);
        stateTime = 0;
        lives=0;//based on the number of nulls or verys
    }
    public void update(float deltaTime) {
        stateTime+=deltaTime;
    }
}
