// Assignment 5
// Cherry Alexander 
// acherry
// Bresett Matthew
// bresettm

import tester.Tester;
import javalib.funworld.*;
import javalib.worldimages.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
/*
// Represents an apple
class Apple {
    /* TEMPLATE
     * Fields:
     * ...this.p ...       -- Posn
     * Methods:
     * ...this.moveDown()... -- Apple
     * ...this.onTheGroundHelp(int)... -- boolean
     * ...this.appleImage()...         -- WorldImage
     * ...this.xPos()...               -- int
     * ...this.yPos()...               -- int
     *//*
    Posn p;//represents center of apple
    Apple(Posn p) {
        this.p = p;
    }
    // Moves the apple down in the world
    Apple moveDown() {
        return new Apple(new Posn (this.xPos(), this.yPos() + 20));
    }
    // Is the apple at this y-coordinate?
    boolean onTheGroundHelp(int height){
        return this.p.y >= height;
    }
    // Displays an apple
    public WorldImage appleImage(){
        return new FromFileImage(this.p, "small-red-apple.png");
    }
    // Returns this Apple's horizontal position
    int xPos() {
        return this.p.x;
    }
    // Returns this Apple's vertical position
    int yPos() {
        return this.p.y;
    }
} 

// Represents a basket 
class Basket {
    Posn p;//represents bottom left corner of basket
    Basket(Posn p) {
        this.p = p;
    }
    // Moves the basket horizontally if valid
    Basket moveOnKey(String ke, int bound) {
        if(ke.equals("right") && this.p.x + 40 <= bound - 10 ) {
            return new Basket(new Posn(this.p.x + 10, this.p.y));
        }
        else if(ke.equals("left") && this.p.x - 40 >= 10) {
            return new Basket(new Posn(this.p.x - 10, this.p.y));
        }
        else {
            return new Basket(this.p);
        }
    }
    // Displays the basket
    public WorldImage basketImage() {
        return new OverlayImages(new RectangleImage(this.p, 80, 55, new Black()),
                new RectangleImage(this.p, 77, 52, new Yellow()));
    }
    // Did the basket catch the apple
    public boolean basketCatch(int appleX, int appleY) {
        return appleX <= this.p.x + 40 &&
                appleX >= this.p.x - 40 &&
                appleY >= this.p.y - 30 &&
                appleY <= this.p.y;
    }
}

// Represents the game world
class AppleGame extends World {
    int height = 400;
    int width = 400;
    Posn center = new Posn(this.width / 2, this.height / 2);
    Apple a;
    Basket b;
    int score;
    AppleGame(Apple a, Basket b, int score){
        super();
        this.a = a;
        this.b = b;
        this.score = score;
    }
    // Displays the apple tree
    WorldImage treeImage(){
        return new FromFileImage(this.center, "apple-tree.png");
    }
    // Is this World's Apple at the bottom of the screen?
    boolean onTheGround() {
        return this.a.onTheGroundHelp(this.height);
    }
    // Decides whether an Apple should moveDown or reset to the top of the screen
    AppleGame fall() {
        int width = (int)((Math.random() * this.width - 40));
        int height = (int)(Math.random() * (this.height / 4));
        if(this.onTheGround() || this.caughtApple()) {
            if (width >= 40) {
                return new AppleGame(
                        new Apple(new Posn(width, height)), this.b, this.score);
            }
            else {
                return this.fall();
            }
        }
        else {
            return new AppleGame(this.a.moveDown(), this.b, this.score);
        }
    }
    // Displays an image of the AppleGame and its Apple and Basket 
    public WorldImage makeImage() {
        WorldImage appleTree = 
                new OverlayImages(new RectangleImage(
                        this.center, this.width, this.height, 
                        new White()), this.treeImage());
        return new OverlayImages(appleTree, new OverlayImages(this.a.appleImage(), 
                new OverlayImages(this.b.basketImage(), new TextImage(new Posn
                        (this.width - 30, 15), 
                        Integer.toString(this.score), Color.blue))));
    }
    // Has the basket caught the apple?
    boolean caughtApple() {
        return this.b.basketCatch(this.a.xPos(), this.a.yPos());
    }
    // Updates the score when an apple is caught
    int updateScore() {
        if (this.caughtApple()) {
            return this.score + 1;
        }
        else {
            return this.score;
        }
    }
    // Updates the Game every tick
    public AppleGame onTick() {
        return new AppleGame(this.fall().a, this.b, this.updateScore());
    }
    // Shifts the basket horizontally on key event
    public AppleGame onKeyEvent(String ke) {
        return new AppleGame(this.a, this.b.moveOnKey(ke, this.width), this.score);
    }
    // Displays the end world
    public WorldImage lastImage(String s) {
        return new OverlayImages(this.makeImage(), new TextImage(
                new Posn(100, 40), s, Color.red));
    }
    // Checks if the game is over and ends it 
    public WorldEnd worldEnds() {
        return new WorldEnd(this.score == 10, 
                this.lastImage("You caught 10 apples! You win!"));
    }
}

// Represents examples of AppleWorlds and method tests
class ExamplesAppleGame {
    // EXAMPLES
    Posn center = new Posn(200, 200);
    Posn fixedInitial = new Posn(100, 100);
    Posn low = new Posn(200,370);
    Posn miss = new Posn(400, 100);
    Apple apple1 = new Apple(center);
    Apple appleStart = new Apple(fixedInitial);
    Apple atBasket = new Apple(low);
    Apple missApple = new Apple(miss);
    Apple groundApple = new Apple(new Posn(100, 400));
    Basket basketStart = new Basket(new Posn(160, 370));
    AppleGame initialWorld = new AppleGame(appleStart, basketStart, 0);
    AppleGame caughtApple = new AppleGame(atBasket, basketStart, 0);
    AppleGame missedWorld = new AppleGame(missApple, basketStart, 0);
    AppleGame groundWorld = new AppleGame(groundApple, basketStart, 0);
    AppleGame endWorld = new AppleGame(groundApple, basketStart, 10);

    // TESTS
    // Tests moveDown for the Apple class
    boolean testMoveDown(Tester t) {
        Apple lowerApple = new Apple(new Posn(200, 220)); 
        Apple lowerApple2 = new Apple(new Posn(200, 390));
        Apple lowerApple3 = new Apple(new Posn(100, 120));
        return t.checkExpect(this.apple1.moveDown(), lowerApple)
                && t.checkExpect(this.atBasket.moveDown(), lowerApple2)
                && t.checkExpect(this.appleStart.moveDown(), lowerApple3);
    }
    // Tests onTheGroundHelp for the Apple class
    boolean testOnTheGroundHelp(Tester t) {
        return t.checkExpect(this.apple1.onTheGroundHelp(this.center.y), true)
                && t.checkExpect(this.appleStart.onTheGroundHelp(400), false)
                && t.checkExpect(this.missApple.onTheGroundHelp(this.miss.y), true);
    }
    // Tests appleImage for the Apple class
    boolean notTestAppleImage(Tester t) {
        return t.checkExpect(this.apple1.appleImage(), 
                new FromFileImage(this.center, "small-red-apple.png"))
                && t.checkExpect(this.appleStart.appleImage(), 
                        new FromFileImage(this.fixedInitial, "small-red-apple.png"));
    }
    // Tests moveOnKey for the Basket class
    boolean testBasketOnKey(Tester t) {
        Basket bs = this.basketStart;
        Basket basketR = new Basket(new Posn(170, 370));
        Basket basketL = new Basket(new Posn(150, 370));
        return t.checkExpect(this.basketStart.moveOnKey("left", 400), basketL)
                && t.checkExpect(bs.moveOnKey("right", 400), basketR)
                && t.checkExpect(bs.moveOnKey("right", 0), this.basketStart);
    }
    // Tests basketImage for the Basket class
    boolean notTestBasketImage(Tester t) {
        return t.checkExpect(this.basketStart.basketImage(), 
                new RectangleImage(basketStart.p, 80, 55, new Yellow()));
    }
    // Tests basketCatch for the Basket class
    boolean testBasketCatch(Tester t) {
        Basket bs = this.basketStart;
        return t.checkExpect(bs.basketCatch(this.center.x, this.center.y), false) &&
                t.checkExpect(bs.basketCatch(160, 370), true) &&
                t.checkExpect(bs.basketCatch(200, 370), true) &&
                t.checkExpect(bs.basketCatch(120, 370), true) &&
                t.checkExpect(bs.basketCatch(160, 340), true) &&
                t.checkExpect(bs.basketCatch(160, 339), false) &&
                t.checkExpect(bs.basketCatch(119, 340), false) &&
                t.checkExpect(bs.basketCatch(201, 340), false) &&
                t.checkExpect(bs.basketCatch(160, 371), false);
    }
    // Tests treeImage for the AppleWorld class
    boolean notTestTreeImage(Tester t) {
        return t.checkExpect(this.initialWorld.treeImage(), 
                new FromFileImage(this.center, "apple-tree.png"))
                && t.checkExpect(this.missedWorld.treeImage(), 
                        new FromFileImage(this.center, "apple-tree.png"));
    }
    // Tests onTheGround for the AppleWorld class
    boolean testOnTheGround(Tester t) {
        return t.checkExpect(this.initialWorld.onTheGround(), false)
                && t.checkExpect(this.missedWorld.onTheGround(), false)
                && t.checkExpect(this.missedWorld.onTick().onTheGround(), false)
                && t.checkExpect(this.groundWorld.onTheGround(), true);
    }
    // Tests fall for the AppleWorld class
    boolean testFall(Tester t) {
        int int1 = this.groundWorld.fall().a.p.x;
        int int2 = this.groundWorld.fall().a.p.y;
        Basket basket = this.groundWorld.fall().b;
        
        return t.checkExpect(this.initialWorld.fall(), 
                new AppleGame(this.initialWorld.a.moveDown(),
                        this.basketStart, 0)) &&
                        t.checkExpect(this.missedWorld.fall(),
                                new AppleGame(
                                        this.missedWorld.a.moveDown(),
                                        this.basketStart, 0)) 
                                        && t.checkRange(int1, 0, 400) &&
                                        t.checkRange(int2, 0, 100) &&
                                        t.checkExpect(basket, this.basketStart);
    }
    // Tests makeImage for the AppleWorld class
    boolean notTestMakeImage(Tester t) {
        return t.checkExpect(this.initialWorld.makeImage(), 
                new OverlayImages(this.initialWorld.a.appleImage(), 
                new OverlayImages(this.initialWorld.b.basketImage(), 
                        this.initialWorld.treeImage())));
    }
    // Tests caughtApple for the AppleWorld class
    boolean testCaughtApple(Tester t) {
        return t.checkExpect(this.initialWorld.caughtApple(), false) &&
                t.checkExpect(this.caughtApple.caughtApple(), true);
    }
    // Tests onTick for AppleWorld class
    boolean testOnTick(Tester t) {
        AppleGame newWorld1 = new AppleGame(
                this.initialWorld.a.moveDown(), this.basketStart, 0);
        return t.checkExpect(this.initialWorld.onTick(), newWorld1) && 
                t.checkRange(this.caughtApple.onTick().a.xPos(), 40, 360) &&
                t.checkRange(this.caughtApple.onTick().a.yPos(), 0, 100) &&
                t.checkExpect(this.caughtApple.onTick().score, 1) &&
                t.checkExpect(this.caughtApple.onTick().b, this.caughtApple.b);
    }
    // Tests onKey for AppleWorld class
    boolean testOnKey(Tester t) {
        Basket bs = this.basketStart;
        Basket basketR = new Basket(new Posn(170, 370));
        Basket basketL = new Basket(new Posn(150, 370));
        AppleGame newWorld1 = new AppleGame(
                initialWorld.a, basketR, 0);
        AppleGame newWorld2 = new AppleGame(
                initialWorld.a, basketL, 0);
        AppleGame newWorld3 = new AppleGame(
                initialWorld.a, bs, 0);
        return t.checkExpect(this.initialWorld.onKeyEvent("right"), newWorld1) && 
                t.checkExpect(this.initialWorld.onKeyEvent("left"), newWorld2) &&
                t.checkExpect(this.initialWorld.onKeyEvent("jhdf"), newWorld3);
    }
    // Tests lastImage for the AppleWorld class
    boolean notTestLastImage(Tester t) {
        return t.checkExpect(this.initialWorld.lastImage("I'm checked"), 
                new OverlayImages(this.initialWorld.makeImage(), new TextImage(
                        new Posn(100, 40), "I'm checked", Color.red)));
    }
    // Run the game
    boolean runAnimation = this.initialWorld.bigBang(400, 400, 0.3);

    
}
   */ 