package com.example.user.infowhirl2;

import Hose.Animation;
import Hose.Font;
import Hose.GLGame;
import Hose.Texture;
import Hose.TextureRegion;

public class Assets {
    public static TextureRegion back;
    public static Texture background;
    public static TextureRegion backgroundRegion;
    public static Texture feelButtons;
    public static Font font;
    public static Texture items;
    public static Texture birdy;
    public static Texture mat;
    public static TextureRegion next;
    public static TextureRegion nullyButton;
    public static Texture postNew;
    public static TextureRegion postnewRegion;
    public static Texture reload;
    public static TextureRegion reloadregion;
    public static TextureRegion spoti;
    public static Animation superwhirl;
    public static Animation ultimatewhirl;
    public static TextureRegion table;
    public static Texture texs;
    public static TextureRegion veryButton;
    public static Animation whirl;
    public static TextureRegion whirlS;

    public static void load(GLGame game) {
        GLGame gLGame = game;
        //background that will cover the screen
        background = new Texture(gLGame, "background2.png");
        //The background for the text display
        mat = new Texture(gLGame, "pattern.png");
        //the button that will return a new screen activity
        reload = new Texture(gLGame, "reload.png");
        feelButtons = new Texture(gLGame, "feelButtons.png");
        //This binds the reload button to the screen
        reloadregion = new TextureRegion(reload, 0.0f, 0.0f,128.0f,128.0f);
        //the button for downvoting
        nullyButton = new TextureRegion(feelButtons, 0.0f, 32.0f, 32.0f, 32.0f);
        //the button for upvoting
        veryButton = new TextureRegion(feelButtons, 0.0f, 0.0f, 32.0f, 32.0f);
        //bind the background texture to the screen
        backgroundRegion = new TextureRegion(background, 0.0f, 0.0f, InfoWhirl.getScreenWidth()*2, InfoWhirl.getScreenHeight()*2);
        table = new TextureRegion(mat, 0.0f, 0.0f, InfoWhirl.getScreenWidth(), InfoWhirl.getScreenHeight());
        //load the spritesheet
        items = new Texture(gLGame, "watlas.png");
        //load the spritesheet for bird animations
        birdy=new Texture(gLGame,"birdy.png");
        //load the spritesheet for fonts
        texs = new Texture(gLGame, "text.png");
        //load the texture for moving back from detail screen
        back = new TextureRegion(items, 192.0f, 0.0f, 64.0f, 64.0f);
        spoti = new TextureRegion(items, 95.0f, 12.0f, 64.0f, 64.0f);
        //load the texture for the active state bird
        whirlS = new TextureRegion(items, 0.0f, 84.0f, 50.0f, 50.0f);
        //load the texture for the dormant state bird
        whirl = new Animation(0.3f, new TextureRegion(birdy, 0.0f, 0.0f, 50.0f, 50.0f), new TextureRegion(birdy, 50.0f, 0.0f, 50.0f, 50.0f), new TextureRegion(birdy, 100.0f, 0.0f, 50.0f, 50.0f),new TextureRegion(birdy, 150.0f, 0.0f, 50.0f, 50.0f));
        //load the texture for the hyperactive state bird
        superwhirl = new Animation(0.3f, new TextureRegion(birdy, 0.0f, 50.0f, 50.0f, 50.0f), new TextureRegion(birdy, 50.0f, 50.0f, 50.0f, 50.0f),new TextureRegion(birdy, 150.0f, 50.0f, 50.0f, 50.0f),new TextureRegion(birdy, 50.0f, 50.0f, 50.0f, 50.0f),new TextureRegion(birdy, 0.0f, 50.0f, 50.0f, 50.0f), new TextureRegion(birdy, 100.0f, 50.0f, 50.0f, 50.0f));
        ultimatewhirl= new Animation(0.16f,new TextureRegion(birdy, 0.0f, 100.0f, 50.0f, 50.0f), new TextureRegion(birdy, 50.0f, 100.0f, 50.0f, 50.0f), new TextureRegion(birdy, 100.0f, 100.0f, 50.0f, 50.0f),new TextureRegion(birdy, 150.0f, 100.0f, 50.0f, 50.0f));
        //load the texture for button for posting a new event
        postNew = new Texture(gLGame, "postnew.png");
        //bind the postnew button to the screen
        postnewRegion = new TextureRegion(postNew, 0.0f, 0.0f, 64.0f, 64.0f);
        next = new TextureRegion(items, 0.0f, 15.0f, 64.0f, 79.0f);
        //the individual font glyphs well use as text
        font = new Font(texs, 0, 0, 16, 16, 32);
    }
}