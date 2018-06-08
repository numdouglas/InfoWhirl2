package com.example.user.infowhirl2;

import org.w3c.dom.Text;

import GameHose.Animation;
import GameHose.Font;
import GameHose.GLGame;
import GameHose.Texture;
import GameHose.TextureRegion;

/**
 * Created by user on 10/20/2017.
 */

public class Assets {
    public static Texture items;
    public static Texture feelButtons;
    public static Texture background;
    public static TextureRegion veryButton;
    public static TextureRegion nullyButton;
    public static Animation whirl;
    public static Animation superwhirl;
    public static TextureRegion whirlS;
    public static TextureRegion back;
    public static Texture postNew;
    public static Texture texs;
    public static TextureRegion postnewRegion;
    public static TextureRegion backgroundRegion;
    public  static TextureRegion next;
    public static TextureRegion spoti;
    public static Font font;

public static void load(GLGame game){
    background=new Texture(game,"background2.png");
    feelButtons=new Texture(game,"feelButtons.png");
    nullyButton=new TextureRegion(feelButtons,0,32,32,32);
    veryButton=new TextureRegion(feelButtons,0,0,32,32);
    backgroundRegion=new TextureRegion(background,0,0,640,960);
    items=new Texture(game,"watlas.png");
    texs=new Texture(game,"text.png");
    back=new TextureRegion(items,192,0,64,64);
    spoti=new TextureRegion(items,95,12,64,64);
    whirlS=new TextureRegion(items,0,84,50,50);
    superwhirl=new Animation(0.2f,new TextureRegion(items,0,84,50,50),new TextureRegion(items,49,86,50,50),new TextureRegion(items,94,84,50,50));
    whirl=new Animation(0.2f,new TextureRegion(items,0,84,50,50),new TextureRegion(items,144,88,50,50),new TextureRegion(items,207,87,50 ,50));
    postNew=new Texture(game,"postnew.png");
    postnewRegion=new TextureRegion(postNew,0,0,64,64);
    next=new TextureRegion(items,0,15,64,79);
    font=new Font(texs,0,0,16,8,16);
}}
