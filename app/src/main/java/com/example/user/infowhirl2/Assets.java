package com.example.user.infowhirl2;

import GameHose.Animation;
import GameHose.Font;
import GameHose.GLGame;
import GameHose.Texture;
import GameHose.TextureRegion;

public class Assets {
    public static TextureRegion back;
    public static Texture background;
    public static TextureRegion backgroundRegion;
    public static Texture feelButtons;
    public static Font font;
    public static Texture items;
    public static Texture mat;
    public static TextureRegion next;
    public static TextureRegion nullyButton;
    public static Texture postNew;
    public static TextureRegion postnewRegion;
    public static Texture reload;
    public static TextureRegion reloadregion;
    public static TextureRegion spoti;
    public static Animation superwhirl;
    public static TextureRegion table;
    public static Texture texs;
    public static TextureRegion veryButton;
    public static Animation whirl;
    public static TextureRegion whirlS;

    public static void load(GLGame game) {
        GLGame gLGame = game;
        background = new Texture(gLGame, "background2.png");
        mat = new Texture(gLGame, "pattern.png");
        reload = new Texture(gLGame, "reload.png");
        feelButtons = new Texture(gLGame, "feelButtons.png");
        reloadregion = new TextureRegion(reload, 0.0f, 0.0f, 128.0f, 128.0f);
        nullyButton = new TextureRegion(feelButtons, 0.0f, 32.0f, 32.0f, 32.0f);
        veryButton = new TextureRegion(feelButtons, 0.0f, 0.0f, 32.0f, 32.0f);
        backgroundRegion = new TextureRegion(background, 0.0f, 0.0f, 640.0f, 960.0f);
        table = new TextureRegion(mat, 0.0f, 0.0f, 320.0f, 480.0f);
        items = new Texture(gLGame, "watlas.png");
        texs = new Texture(gLGame, "text.png");
        back = new TextureRegion(items, 192.0f, 0.0f, 64.0f, 64.0f);
        spoti = new TextureRegion(items, 95.0f, 12.0f, 64.0f, 64.0f);
        whirlS = new TextureRegion(items, 0.0f, 84.0f, 50.0f, 50.0f);
        superwhirl = new Animation(0.2f, new TextureRegion(items, 0.0f, 84.0f, 50.0f, 50.0f), new TextureRegion(items, 49.0f, 86.0f, 50.0f, 50.0f), new TextureRegion(items, 94.0f, 84.0f, 50.0f, 50.0f));
        whirl = new Animation(0.2f, new TextureRegion(items, 0.0f, 84.0f, 50.0f, 50.0f), new TextureRegion(items, 144.0f, 88.0f, 50.0f, 50.0f), new TextureRegion(items, 207.0f, 87.0f, 50.0f, 50.0f));
        postNew = new Texture(gLGame, "postnew.png");
        postnewRegion = new TextureRegion(postNew, 0.0f, 0.0f, 64.0f, 64.0f);
        next = new TextureRegion(items, 0.0f, 15.0f, 64.0f, 79.0f);
        font = new Font(texs, 0, 0, 16, 8, 16);
    }
}