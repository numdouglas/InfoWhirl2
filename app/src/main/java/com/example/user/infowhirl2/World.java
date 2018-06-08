package com.example.user.infowhirl2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import GameHose.GLGame;
import GameHose.Vector2;

/**
 * Created by user on 10/20/2017.
 */

public class World {
    public interface WorldListener {
        public void jump();
        public void highJump();
        public void hit();
        public void coin();
        public void crack();//keeps simulation classes clean from any direct dependencies on rendering and audio playback
    }
    public static GLGame game;
    public static final float WORLD_WIDTH = 10;
    public static final float WORLD_HEIGHT = 15 * 20;
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static final Vector2 gravity = new Vector2(0, -12);
    public  List<Whirl> whirls=new ArrayList<Whirl>();
    public final WorldListener listener;
    public final Random rand;
    public float heightSoFar;
    public int score;
    public int state;
    public World(WorldListener listener) {
        this.listener = listener;
        rand = new Random();
        generateLevel();
        this.heightSoFar = 0;
        this.score = 0;
        this.state = WORLD_STATE_RUNNING;
    }
    private void generateLevel() {

        for (int i=0;i<InfoWhirl.events.size();i++){
        whirls.add(i,new Whirl(DetailTaker.xxes.get(i),DetailTaker.yyes.get(i)));

    }}

    public void update(float deltaTime, float accelX) {
        updateWhirls(deltaTime);

    }
    private void updateWhirls(float deltaTime) {
        for(int i=0;i<InfoWhirl.events.size();i++){

        Whirl whirlo=whirls.get(i);
        whirlo.update(deltaTime);
    }}

    private void checkCollisions() {
        checkTouchCollisions();

    }
    private void checkTouchCollisions(){}

}
