// Assignment 2
// Cherry Alexander 
// acherry
// Bresett Matthew
// bresettm

import java.awt.Color;
import tester.*;
import javalib.funworld.*;
import javalib.worldimages.*;
import javalib.colors.*;
import javalib.worldcanvas.*;

interface ILoApple {
    // Height of the AppleGame class
    int iHeight = 400;
    // Width of the AppleGame class
    int iWidth = 400;
    // Moves all of the Apples in the list down
    ILoApple moveAllDown();
    // Updates each Apple in the list according to their position
    ILoApple updateApples(Basket b);
    // Displays the list of Apples
    WorldImage applesImage();
    // Computes the number of Apples caught by the Basket
    int applesCaught(Basket b);
}

// Represents a non-empty list of Apples
class ConsLoApple implements ILoApple {
    Apple first;
    ILoApple rest;
    ConsLoApple(Apple first, ILoApple rest) {
        this.first = first;
        this.rest = rest;
    }
    /* TEMPLATE
     * Fields:
     * ...this.first ...                    -- Apple
     * ...this.rest...                      -- ILoApple
     * Methods:
     * ...this.moveAllDown()...             -- ILoApple
     * ...this.updateApples(Basket)...      -- ILoApple
     * ...this.applesImage()...             -- WorldImage
     * ...this.applesCaught(Basket)...      -- int
     * Methods on Fields:
     * ...this.first.moveDown()...          -- Apple
     * ...this.first.inBounds(int)...       -- boolean
     * ...this.first.appleImage()...        -- WorldImage
     * ...this.first.xPos()...              -- int
     * ...this.first.yPos()...              -- int
     * ...this.first.fixApple()...          -- Apple
     * ...this.rest.moveAllDown()...        -- ILoApple
     * ...this.rest.updateApples(Basket)... -- ILoApple
     * ...this.rest.applesImage()...        -- WorldImage
     * ...this.rest.applesCaught(Basket)... -- int
     */
    // Moves all the Apples in this list down.
    public ILoApple moveAllDown() {
        return new ConsLoApple(this.first.moveDown(), this.rest.moveAllDown());
    }
    // Resets any grounded Apples
    public ILoApple updateApples(Basket b) {
        if (this.first.inBounds(iHeight) || (b.basketCatch(this.first.xPos(), this.first.yPos()))) {
            return new ConsLoApple(new Apple(iWidth, iHeight).fixApple(iWidth, iHeight), 
                    this.rest.updateApples(b).moveAllDown());
        }
        else {
            return new ConsLoApple(this.first.moveDown(), this.rest.updateApples(b));
        }
    }
    // Displays the list of Apples
    public WorldImage applesImage() {
        return new OverlayImages(this.first.appleImage(), this.rest.applesImage());
    }
    // Computes the number of the Apples in this list caught by the Basket
    public int applesCaught(Basket b) {
        int rest = this.rest.applesCaught(b);
        if (b.basketCatch(this.first.xPos(), this.first.yPos())) {
            return 1 + rest;
        }
        else {
            return rest;
        }
    }
}

// Represents an empty list of Apples 
class MtLoApple implements ILoApple {
    MtLoApple() {
        /*this is an empty list*/        
    }
    /* TEMPLATE
     * Methods:
     * ...this.moveAllDown()...             -- ILoApple
     * ...this.updateApples(Basket)...      -- ILoApple
     * ...this.applesImage()...             -- WorldImage
     * ...this.applesCaught(Basket)...      -- int
     */
    // Moves all the Apples in this empty list down
    public ILoApple moveAllDown() {
        return this;
    }
    // Updates this empty list of Apples 
    public ILoApple updateApples(Basket b) {
        return this;
    }
    // Shows a blank image of this empty list of Apples
    public WorldImage applesImage() {
        return new RectangleImage(new Posn(-1, -1), 0, 0, new White());
    }
    // Computes that no Apples were caught in the Basket
    public int applesCaught(Basket b) {
        return 0;
    }
}

// Represents an apple
class Apple {
    Posn p; //represents center of apple
    Apple(Posn p) {
        this.p = p;
    }
    /* TEMPLATE
     * Fields:
     * ...this.p ...                   -- Posn
     * Methods:
     * ...this.moveDown()...           -- Apple
     * ...this.inBounds(int)...        -- boolean
     * ...this.appleImage()...         -- WorldImage
     * ...this.xPos()...               -- int
     * ...this.yPos()...               -- int
     * ...this.fixApple()...           -- Apple
     */
    // Constructor for a random Apple
    Apple(int width, int height) {
        int width1 = (int)((Math.random() * width));
        int height1 = (int)(Math.random() * (height / 3));
        Posn p = new Posn(width1, height1);
        this.p = p;
    }
    // Moves the apple down in the world
    Apple moveDown() {
        return new Apple(new Posn(this.xPos(), this.yPos() + 20));
    }
    // Is the apple above this y-coordinate?
    boolean inBounds(int height) {
        return this.p.y >= height;
    }
    // Displays an apple
    public WorldImage appleImage() {
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
    // Replaces improperly constructed apple with proper Apple
    Apple fixApple(int width, int height) {
        if (this.p.x < 35 || this.p.x > (width - 35)) {
            return new Apple(width, height).fixApple(width, height);
        }
        else {
            return this;
        }
    }
}
// Represents a basket 
class Basket {
    Posn p; //represents bottom left corner of basket
    Basket(Posn p) {
        this.p = p;
    }
    /* TEMPLATE
     * Fields:
     * ...this.p ...                   -- Posn
     * Methods:
     * ...this.moveOnKey()...          -- Basket
     * ...this.basketImage()...        -- WorldImage
     * ...this.basketCatch()...        -- boolean
     */
    // Moves the basket horizontally if valid
    Basket moveOnKey(String ke, int bound) {
        if (ke.equals("right") && this.p.x + 40 <= bound - 10 ) {
            return new Basket(new Posn(this.p.x + 10, this.p.y));
        }
        else if (ke.equals("left") && this.p.x - 40 >= 10) {
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
    ILoApple loa;
    Basket b;
    int score;
    AppleGame(ILoApple loa, Basket b, int score) {
        super();
        this.loa = loa;
        this.b = b;
        this.score = score;
    }
    /* TEMPLATE
     * Fields:
     * ...this.lao ...                    -- ILoApple
     * ...this.b...                       -- Basket
     * ...this.score                      -- int
     * Methods:
     * ...this.treeImage()...              -- WorldImage
     * ...this.fall()...                   -- AppleGame
     * ...this.makeImage()...              -- WorldImage
     * ...this.numCaughtApples()...        -- int
     * ...this.updateScore()...            -- int
     * ...this.onTick()...                 -- AppleGame
     * ...this.onKeyEvent(String)...       -- AppleGame
     * ...this.lastImage()...              -- WorldImage
     * ...this.worldEnds()...                   -- WorldEnd
     * Methods on Fields:
     * ...this.loa.moveAllDown()...             -- ILoApple
     * ...this.loa.updateApples(Basket)...      -- ILoApple
     * ...this.loa.applesImage()...             -- WorldImage
     * ...this.loa.applesCaught(Basket)...      -- int
     * ...this.b.moveOnKey()...          -- Basket
     * ...this.b.basketImage()...        -- WorldImage
     * ...this.b.basketCatch()...        -- boolean
     */
    // Displays the apple tree
    WorldImage treeImage() {
        return new FromFileImage(this.center, "apple-tree.png");
    }
    // Drops the list of Apples to their next position
    AppleGame fall() {
        return new AppleGame(this.loa.updateApples(this.b), this.b, this.score);
    }
    // Displays an image of the AppleGame and its Apple and Basket 
    public WorldImage makeImage() {
        WorldImage appleTree = 
                new OverlayImages(new RectangleImage(
                        this.center, this.width, this.height, 
                        new White()), this.treeImage());
        return new OverlayImages(appleTree, new OverlayImages(this.loa.applesImage(), 
                new OverlayImages(this.b.basketImage(), 
                        new TextImage(new Posn(this.width - 30, 15),
                                Integer.toString(this.score), Color.blue))));
    }
    // How many apples has the Basket caught?
    int numCaughtApples() {
        return this.loa.applesCaught(this.b);
    }
    // Updates the score when an apple is caught
    int updateScore() {
        return this.score + this.numCaughtApples();
    }    
    // Updates the Game every tick
    public AppleGame onTick() {
        return new AppleGame(this.fall().loa, this.b, this.updateScore());
    }
    // Shifts the basket horizontally on key event
    public AppleGame onKeyEvent(String ke) {
        return new AppleGame(this.loa, this.b.moveOnKey(ke, this.width), this.score);
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
    Posn low = new Posn(160, 360);
    Posn miss = new Posn(400, 100);
    Apple apple1 = new Apple(this.center);
    Apple appleStart = new Apple(this.fixedInitial);
    Apple atBasket = new Apple(this.low);
    Apple missApple = new Apple(this.miss);
    Apple groundApple = new Apple(new Posn(100, 400));
    Apple appleA = new Apple(new Posn(200, 80));
    Apple appleB = new Apple(new Posn(300, 80));
    Apple appleC = new Apple(new Posn(150, 80));
    Apple appleD = new Apple(new Posn(350, 80));
    Apple appleE = new Apple(new Posn(160, 365));
    Apple appleF = new Apple(new Posn(155, 370));
    ILoApple mT = new MtLoApple();
    ILoApple centerList = new ConsLoApple(this.apple1, this.mT);
    ILoApple startList = new ConsLoApple(this.appleStart, this.mT);
    ILoApple basketList = new ConsLoApple(this.atBasket, this.mT);
    ILoApple missList = new ConsLoApple(this.missApple, this.mT);
    ILoApple groundList = new ConsLoApple(this.groundApple, this.mT);
    ILoApple loa1 = new ConsLoApple(this.appleA, this.mT);
    ILoApple loa2 = new ConsLoApple(this.appleB, this.loa1);
    ILoApple loa3 = new ConsLoApple(this.appleC, this.loa2);
    ILoApple assortedList = new ConsLoApple(this.appleD, this.loa3);
    ILoApple threeBasketList = new ConsLoApple(this.atBasket, 
            new ConsLoApple(this.appleE, new ConsLoApple(this.appleF, this.loa1)));
    Basket basketStart = new Basket(new Posn(160, 370));
    AppleGame initialWorld = new AppleGame(this.startList, this.basketStart, 0);
    AppleGame caughtApple = new AppleGame(this.basketList, this.basketStart, 0);
    AppleGame missedWorld = new AppleGame(this.missList, this.basketStart, 0);
    AppleGame groundWorld = new AppleGame(this.groundList, this.basketStart, 0);
    AppleGame endWorld = new AppleGame(this.groundList, this.basketStart, 10);
    AppleGame randomWorld = new AppleGame(this.assortedList, this.basketStart, 0);
    AppleGame randomWorld2 = new AppleGame(this.threeBasketList, this.basketStart, 0);

    // TESTS
    // Tests moveDown for the Apple class
    boolean testMoveDown(Tester t) {
        Apple lowerApple = new Apple(new Posn(200, 220)); 
        Apple lowerApple2 = new Apple(new Posn(160, 380));
        Apple lowerApple3 = new Apple(new Posn(100, 120));
        return t.checkExpect(this.apple1.moveDown(), lowerApple)
                && t.checkExpect(this.atBasket.moveDown(), lowerApple2)
                && t.checkExpect(this.appleStart.moveDown(), lowerApple3);
    } 
    // Tests inBounds for the Apple class
    boolean testInBounds(Tester t) {
        return t.checkExpect(this.apple1.inBounds(this.center.y), true)
                && t.checkExpect(this.appleStart.inBounds(360), false)
                && t.checkExpect(this.missApple.inBounds(this.miss.y), true);
    }
    // Tests appleImage for the Apple class
    boolean notTestAppleImage(Tester t) {
        return t.checkExpect(this.apple1.appleImage(), 
                new FromFileImage(this.center, "small-red-apple.png"))
                && t.checkExpect(this.appleStart.appleImage(), 
                        new FromFileImage(this.fixedInitial, "small-red-apple.png"));
    }
    // Tests xPos for the Apple class
    boolean testXPos(Tester t) {
        return t.checkExpect(this.apple1.yPos(), 200)
                && t.checkExpect(this.appleStart.yPos(), 100)
                && t.checkExpect(this.missApple.yPos(), 100);
    }
    // Tests xPosn for the Apple class
    boolean testYPos(Tester t) {
        return t.checkExpect(this.apple1.xPos(), 200)
                && t.checkExpect(this.appleStart.xPos(), 100)
                && t.checkExpect(this.missApple.xPos(), 400);
    }
    // TODO Tests fixApple for the Apple class
   // boolean testfixApple(Tester t) {
    //    return t.checkExpect(this.new Apple(new Posn(10, 200)).yPos(), 200)
     //           && t.checkExpect(this.appleStart.yPos(), 100)
      //          && t.checkExpect(this.missApple.yPos(), 400);
    //}
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
    // Tests numCaughtApples for the AppleWorld class
    boolean testNumCaughtApples(Tester t) {
        return t.checkExpect(this.initialWorld.numCaughtApples(), 0)
                && t.checkExpect(this.missedWorld.numCaughtApples(), 0)
                && t.checkExpect(this.caughtApple.numCaughtApples(), 1)
                && t.checkExpect(this.randomWorld2.numCaughtApples(), 3);
    }
    // Tests fall for the AppleWorld class
    boolean testFall(Tester t) {
        Apple apple1 = ((ConsLoApple)this.groundWorld.fall().loa).first;
        int int1 = apple1.p.x;
        int int2 = apple1.p.y;
        Basket basket = this.groundWorld.fall().b;
        
        return t.checkExpect(this.initialWorld.fall(), 
                new AppleGame(this.initialWorld.loa.moveAllDown(),
                        this.basketStart, 0)) &&
                        t.checkExpect(this.missedWorld.fall(),
                                new AppleGame(
                                        this.missedWorld.loa.moveAllDown(),
                                        this.basketStart, 0)) 
                                        && t.checkRange(int1, 0, 400) &&
                                        t.checkRange(int2, 0, 100) &&
                                        t.checkExpect(basket, this.basketStart);
    }
    // Tests makeImage for the AppleWorld class
    boolean notTestMakeImage(Tester t) {
        return t.checkExpect(this.initialWorld.makeImage(), 
                new OverlayImages(this.initialWorld.loa.applesImage(), 
                new OverlayImages(this.initialWorld.b.basketImage(), 
                        this.initialWorld.treeImage())));
    }
    // Tests onTick for AppleWorld class
    boolean testOnTick(Tester t) {
        AppleGame newWorld1 = new AppleGame(
                this.initialWorld.loa.moveAllDown(), this.basketStart, 0);
        Apple apple1 = ((ConsLoApple)(this.caughtApple.onTick().loa)).first;
        return t.checkExpect(this.initialWorld.onTick(), newWorld1) && 
                t.checkRange(apple1.xPos(), 34, 366) &&
                t.checkRange(apple1.yPos(), 0, 134) &&
                t.checkExpect(this.caughtApple.onTick().score, 1) &&
                t.checkExpect(this.caughtApple.onTick().b, this.caughtApple.b);
    }
    // Tests onKey for AppleWorld class
    boolean testOnKey(Tester t) {
        Basket bs = this.basketStart;
        Basket basketR = new Basket(new Posn(170, 370));
        Basket basketL = new Basket(new Posn(150, 370));
        AppleGame newWorld1 = new AppleGame(
                initialWorld.loa, basketR, 0);
        AppleGame newWorld2 = new AppleGame(
                initialWorld.loa, basketL, 0);
        AppleGame newWorld3 = new AppleGame(
                initialWorld.loa, bs, 0);
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
    
    // Tests moveAllDown for the ILoApple class
    boolean testMoveAllDown(Tester t) {
        ILoApple ans1 = new ConsLoApple(new Apple(new Posn(300, 100)), 
                new ConsLoApple(new Apple(new Posn(200, 100)), 
                        new MtLoApple()));
        ILoApple ans2 = new ConsLoApple(new Apple(new Posn(200, 100)), 
                new MtLoApple());
        return t.checkExpect(this.loa2.moveAllDown(), ans1)
                && t.checkExpect(this.loa1.moveAllDown(), ans2)
                && t.checkExpect(new MtLoApple().moveAllDown(), 
                        new MtLoApple());
    }
    // Tests updateApples for the ILoApple class
    boolean testupdateApples(Tester t) {
        ILoApple ans1 = new ConsLoApple(new Apple(new Posn(300, 100)), 
                new ConsLoApple(new Apple(new Posn(200, 100)), 
                        new MtLoApple()));
        ILoApple ans2 = new ConsLoApple(new Apple(new Posn(200, 100)), 
                new MtLoApple());
        Apple apple1 = ((ConsLoApple)(this.basketList.updateApples(this.basketStart))).first;
        ILoApple rest1 = ((ConsLoApple)(this.basketList.updateApples(this.basketStart))).rest;
        Apple apple2 = ((ConsLoApple)(this.groundList.updateApples(this.basketStart))).first;
        ILoApple rest2 = ((ConsLoApple)(this.groundList.updateApples(this.basketStart))).rest;
        return t.checkExpect(this.loa2.updateApples(this.basketStart), ans1)
                && t.checkExpect(this.loa1.updateApples(this.basketStart), ans2)
                && t.checkExpect(new MtLoApple().updateApples(this.basketStart), 
                        new MtLoApple())
                        && t.checkRange(apple1.xPos(), 34, 366) 
                        && t.checkRange(apple1.yPos(), 0, 134)
                        && t.checkExpect(rest1, new MtLoApple())
                        && t.checkRange(apple2.xPos(), 34, 366) 
                        && t.checkRange(apple2.yPos(), 0, 134)
                        && t.checkExpect(rest2, new MtLoApple());
    }
    // Tests applesImage for the ILoApple class
    boolean notTestApplesImage(Tester t) {
        WorldImage loa1Img = new OverlayImages(this.appleA.appleImage(), 
                new RectangleImage(new Posn(-1, -1), 0, 0, new White()));
        WorldImage loa2Img = new OverlayImages(this.appleB.appleImage(), loa1Img);
        return t.checkExpect(this.centerList.applesImage(), 
                new OverlayImages(this.apple1.appleImage(), 
                        new RectangleImage(new Posn(-1, -1), 0, 0, new White())))
                        && t.checkExpect(this.loa1.applesImage(), loa1Img)
                        && t.checkExpect(this.loa2.applesImage(), loa2Img);
                                
    }
    // Tests applesCaught for the ILoApple class
    boolean testApplesCaught(Tester t) {
        return t.checkExpect(this.startList.applesCaught(this.basketStart), 0)
                && t.checkExpect(this.missList.applesCaught(this.basketStart), 0)
                && t.checkExpect(this.basketList.applesCaught(this.basketStart), 1)
                && t.checkExpect(this.threeBasketList.applesCaught(this.basketStart), 3);
    }
    // Run the game
    boolean runAnimation = this.randomWorld.bigBang(400, 400, 0.1);

}

    