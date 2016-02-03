// Assignment 5
// Cherry Alexander 
// acherry
// Bresett Matthew
// bresettm
import tester.Tester;

/* 
Class Diagram
                
                  +---------------------+
                  |WebPage              |<------------------------+
                  +---------------------+                         |
                  |String url           |                         |
                  |String title         |                         |  
                  |ILoItem items        |                         |
                  +---------------------+                         |
                  |int totalImageSize() |                         |
                  |int textLength()     |                         |
                  |String imagesAlmost()|                         |
                  |String images()      |                         |
                  +---------------------+                         |
                          |                                       |
                          |                                       |
                          v                                       |
               +--------------------------+                       |
               | ILoItem                  |<----------------+     |
               +--------------------------+                 |     |
               +--------------------------+                 |     |
               |int listImagesSize()      |                 |     |
               |int itemTextLength()      |                 |     |
               |String listImages()       |                 |     | 
               |                          |                 |     |
               +--------------------------+                 |     |  
                          |                                 |     |
                         / \                                |     |
                         ---                                |     | 
                          |                                 |     | 
            -----------------------------                   |     |  
            |                           |                   |     |
+--------------------------+   +--------------------------+ |     |
| MtLoItem                 |   | ConsLoItem               | |     |
+--------------------------+   +--------------------------+ |     | 
+--------------------------+ +-| Item first               | |     |
|int listImagesSize()      | | | ILoItem rest             |-+     |
|int itemTextLength()      | | +--------------------------+       |
|String listImages()       | | |int listImagesSize()      |       |
|                          | | |int itemTextLength()      |       |
+--------------------------+ | |String listImages()       |       |
                             | |                          |       |
                             | +--------------------------+       |
                             v                                    |
                   +-----------------+                            |
                   | Item            |                            |
                   +-----------------+                            |
                   |int imageSize()  |                            |
                   |int textLength() |                            |
                   |String image()   |                            |
                   |                 |                            |
                   +-----------------+                            |
                          / \                                     |
                          ---                                     |
                           |                                      |
      ---------------------|----------------------                |
      |                    |                     |                |
      |                    |                     |                |
+----------------+    +-----------------+    +----------------+   |
|Text            |    |Image            |    |Link            |   |
+----------------+    +-----------------+    +----------------+   |
|String contents |    |String fileName  |    |String name     |   |
|                |    |int size         |    |WebPage page    |---+
|                |    |String fileType  |    |                | 
+----------------+    +-----------------+    +----------------+
|int imageSize() |    |int imageSize()  |    |int imageSize() |
|int textLength()|    |int textLength() |    |int textLength()|
|String image()  |    |String image()   |    |String image()  |
|                |    |                 |    |                |
+----------------+    +-----------------+    +----------------+
*/ 


// this represents a Web Page.
class WebPage {
    String url;
    String title;
    ILoItem items;
    WebPage(String url, String title, ILoItem items) {
        this.url = url;
        this.title = title;
        this.items = items;                   
    }
    /* TEMPLATE
     * Fields:
     * ...this.url...                      -- String
     * ...this.title...                    -- String         
     * Methods: 
     * ...this.totalImageSize()...         -- int
     * ...this.textLength()...             -- int
     * ...this.imagesAlmost()...           -- String
     * ...this.images()...                 -- String
     * ...this.sameWebPage(WebPage that)...-- boolean
     * Methods on Fields:
     * ...this.items.listImagesSize()...          -- int
     * ...this.items.itemTextLength()...          -- int
     * ...this.items.listImages()...              -- String
     * ...this.items.sameLoItem(ILoItem that)...  -- boolean
     * ...this.items.sameCons(ConsLoItem that)... -- boolean
     * ...this.items.sameMt(MtLoItem that)...     -- boolean
     */
    // finds the total size of all images in this and nested pages
    int totalImageSize() {
        return this.items.listImagesSize();
    }
    // computes the total number of characters in all items in this page and
    // nested pages
    int textLength() {
        int txtlngth = this.items.itemTextLength();
        return this.title.length() + txtlngth;
    }
    // returns the name and filetype of all images in this and nested pages
    // as a String with a comma and space at the end
    String imagesAlmost() {
        return this.items.listImages();
    }
    // returns the name and filetype of all images in this and nested pages
    // as a String
    String images() {
        int end = this.imagesAlmost().length() - 2;
        return this.imagesAlmost().substring(0, end);
    }
    boolean sameWebPage(WebPage that) {
        return this.url.equals(that.url) &&
            this.title.equals(that.title) &&
                this.items.sameLoItem(that.items);
    }
}

// this represents a list of Items
interface ILoItem {
    /*Methods: 
     * ...this.listImagesSize()...   -- int
     * ...this.itemTextLength()...   -- int
     * ...this.listImages()...       -- String
     */
    // computes the total image size of all images in this list of pages
    // and nested pages inside links
    int listImagesSize();
    // computes the total character length of all items in this list 
    // and nested pages inside links
    int itemTextLength();
    // Creates a string of the name of all images in this list of items
    // and in nested pages inside links
    String listImages();
    //Is this item the same as that item
    boolean sameLoItem(ILoItem that);
    //Is this item the same as that Cons of Items
    boolean sameCons(ConsLoItem that);
    //Is this item the same as that empty list of Items
    boolean sameMt(MtLoItem that);
}

// this represents a non-empty list of Items
class ConsLoItem implements ILoItem {
    IItem first;
    ILoItem rest;
    ConsLoItem(IItem first, ILoItem rest) {
        this.first = first;
        this.rest = rest;
    }
    /* TEMPLATE
     * Fields:
     * ...this.first...                   -- IItem
     * ...this.rest...                    -- ILoItem
     * Methods: 
     * ...this.listImagesSize()...          -- int
     * ...this.itemTextLength()...          -- int
     * ...this.listImages()...              -- String
     * ...this.sameLoItem(ILoItem that)...  -- boolean
     * ...this.sameCons(ConsLoItem that)... -- boolean
     * ...this.sameMt(MtLoItem that)...     -- boolean
     * Methods on Fields:
     * ...this.first.imageSize()...              -- int
     * ...this.first.textLength()...             -- int
     * ...this.first.image()...                  -- String
     * ...this.rest.sameLoItem(ILoItem that)...  -- boolean
     * ...this.rest.sameCons(ConsLoItem that)... -- boolean
     * ...this.rest.sameMt(MtLoItem that)...     -- boolean
     */
    // computes the total image size of all items in this non-empty list
    // and nested pages inside links.
    public int listImagesSize() {
        return this.first.imageSize() + this.rest.listImagesSize();
    }
    // computes the total character length of all items in this non-empty list
    // and nested pages inside links.
    public int itemTextLength() {
        return this.first.textLength() + this.rest.itemTextLength();
    }
    // Creates a string of the name of all images in this non-empty list of 
    // items and in nested pages inside links.
    public String listImages() {
        return this.first.image() + this.rest.listImages();
    }
    //Is this cons the same as that list of items
    public boolean sameLoItem(ILoItem that) {
        return that.sameCons(this);
    }
    //Is this cons of items the same as that cons of items
    public boolean sameCons(ConsLoItem that) {
        return this.first.sameItem(that.first) &&
                this.rest.sameLoItem(that.rest);
    }
    //is this cons of items the same as that empty list
    //a Cons can never equal an empty list, so always false
    public boolean sameMt(MtLoItem that) {
        return false;
    }
}

// this represents an empty list of Items
class MtLoItem implements ILoItem {
    /* TEMPLATE
     * Methods: 
     * ...this.listImagesSize()...          -- int
     * ...this.itemTextLength()...          -- int
     * ...this.listImages()...              -- String
     * ...this.sameLoItem(ILoItem that)...  -- boolean
     * ...this.sameCons(ConsLoItem that)... -- boolean
     * ...this.sameMt(MtLoItem that)...     -- boolean
     */
    MtLoItem() {/* this is an empty list */}
    public int listImagesSize() {
        return 0;
    }
    // computes the total character length of all items in this non-empty list
    // and nested pages inside links.
    public int itemTextLength() {
        return 0;
    }
    // Creates a string of the name of all images in this non-empty list of 
    // items and in nested pages inside links.
    public String listImages() {
        return "";
    }
    //Is this empty list the same as that list of Items
    public boolean sameLoItem(ILoItem that) {
        return that.sameMt(this);
    }
    //Is this empty list the same as that cons
    //A cons can never equal and empty list, so always false
    public boolean sameCons(ConsLoItem that) {
        return false; 
    }
    //is this empty list the same as that empty List
    public boolean sameMt(MtLoItem that) {
        return true;
    }
}

// this represents all possible types of Items
interface IItem {
    /* TEMPLATE
     * Methods:
     * ...imageSize()...       -- int
     * ...textLength()...      -- int
     * ...image()...           -- String
     */
    int imageSize();
    int textLength();
    String image();
    boolean sameItem(IItem that);
    Text asText();
    Image asImage();
    Link asLink();
    Paragraph asParagraph();
    Header asHeader();
    boolean sameText(IItem that);
    boolean sameImage(IItem that);
    boolean sameLink(IItem that);
    boolean sameParagraph(IItem that);
    boolean sameHeader(IItem that);
}

abstract class AItem implements IItem {
    // returns that this has no image size
    public int imageSize() {
        return 0;
    }
    //returns that this has no image name
    public String image() {
        return "";
    }
    //determines if this text is the same as that item
    public boolean sameText(IItem that) {
        return false;
    }
    //determines if this image is the same as that item
    public boolean sameImage(IItem that) {
        return false;
    }
    //determines if this link is the same as that item
    public boolean sameLink(IItem that) {
        return false;
    }
    //determines is this paragraph is the same as that item
    public boolean sameParagraph(IItem that) {
        return false;
    }
    //determines if this header is the same as that item
    public boolean sameHeader(IItem that) {
        return false;
    }
    //return this item as a text
    public Text asText() {
        throw new ClassCastException("Cannot be casted as a text");
    }
    //return this item as an image
    public Image asImage() {
        throw new ClassCastException("Cannot be casted as an image");
    }
    //return this item as a link
    public Link asLink() {
        throw new ClassCastException("Cannot be casted as a link");
    }
    //return this item as a paragraph
    public Paragraph asParagraph() {
        throw new ClassCastException("Cannot be casted as a Paragraph");
    }
    //return this item as a header
    public Header asHeader() {
        throw new ClassCastException("Cannot be casted as a Header");
    }
}

// this represents text
class Text extends AItem {
    String contents;
    Text(String contents) {
        this.contents = contents;
    }
    /* TEMPLATE
     * Fields:
     * ...this.contents...       -- String          
     * Methods: 
     * ...this.imageSize()...    -- int
     * ...this.textLength()...   -- int
     * ...this.image()...        -- String
     */

    // returns the length of this text
    public int textLength() {
        return this.contents.length();
    }
    //return this text
    public Text asText() {
        return this;
    }
    //is this text the same as that text?
    public boolean sameText(Text that) {
        return this.contents == that.contents;
    }
    //is this text the same as that item?
    public boolean sameItem(IItem that) {
        return this.sameText(that.asText());
    }
}

class Paragraph extends Text {
    Paragraph(String contents) {
        super(contents);
    }
    //return this as a paragraph
    public Paragraph asParagraph() {
        return this;
    }    
    //return this paragraph as a text
    //throws an error to make sure we cannot compare simple texts with paragraphs
    public Text asText() {
        throw new ClassCastException("Cannot be casted as a text");
    }
    //is this paragraph the same as that item
    public boolean sameItem(IItem that) {
        return this.contents == that.asParagraph().contents;
    }
    //is this paragraph the same as that paragraph
    public boolean sameParagraph(Paragraph that) {
        return this.contents == that.contents;
    }
}

class Header extends Text {
    Header(String contents) {
        super(contents);
    }
    //return this as a header
    public Header asHeader() {
        return this;
    }
    //return this header as a text
    //throws an error to make sure we cannot compare simple texts with headers
    public Text asText() {
        throw new ClassCastException("Cannot be casted as a text");
    }
    //is this header the same as that item
    public boolean sameItem(IItem that) {
        return this.sameHeader(that.asHeader());
    }
    //is this header the same as that header
    public boolean sameHeader(Header that) {
        return this.contents == that.contents;
    }
}

// this represents an Image file
class Image extends AItem {
    String fileName;
    int size;
    String fileType;
    Image(String fileName, int size, String fileType) {
        this.fileName = fileName;
        this.size = size;
        this.fileType = fileType;
    }
    /* TEMPLATE
     * Fields:
     * ...this.fileName...       -- String
     * ...this.size...           -- int
     * ...this.fileType...       -- String          
     * Methods: 
     * ...this.imageSize()...    -- int
     * ...this.textLength()...   -- int
     * ...this.image()...        -- String
     */
    // returns the size of this image
    public int imageSize() {
        return this.size;
    }
    // returns the String length of this file name and type
    public int textLength() {
        return this.fileName.length() + this.fileType.length() ;
    }
    // returns the name and type of this image as a single String
    public String image() {
        return this.fileName.concat(".").concat(this.fileType).concat(", ");
    }
    //return this image
    public Image asImage() {
        return this;
    }
    //is this Image the same as that Image?
    public boolean sameImage(Image that) {
        return this.fileName.equals(that.fileName) &&
            this.size == that.size &&
                this.fileType.equals(that.fileType);
    }
    //is this image the same as that item?
    public boolean sameItem(IItem that) {
        return this.sameImage(that.asImage());
    }
}

// this represents an Internet link
class Link extends AItem {
    String name;
    WebPage page;
    Link(String name, WebPage page) {
        this.name = name;
        this.page = page;
    }
    /* TEMPLATE
     * Fields:
     * ...this.name...           -- String
     * ...this.page...           -- WebPage         
     * Methods: 
     * ...this.imageSize()...          -- int
     * ...this.textLength()...         -- int
     * ...this.image(String acc)...    -- String
     * ...this.asLink()...             -- Link
     * ...this.sameLink(Link that)...  --boolean
     * ...this.sameItem(IItem that)... --boolean
     * Methods on Fields:
     * ...this.totalImageSize()...     -- int
     * ...this.textLength()...         -- int
     * ...this.imagesAlmost()...       -- String
     * ...this.images()...             -- String
     */
    // returns the size of the images on this page 
    public int imageSize() {
        return this.page.totalImageSize();
    }
    // returns the String length of this link name, and all the items 
    // in the linked page
    public int textLength() {
        return this.name.length() + this.page.textLength();
    }
    // returns the name and type of all images in the linked page as a String
    public String image() {
        return this.page.imagesAlmost();
    }
    //return this link
    public Link asLink() {
        return this;
    }
    //is this link the same as that link?
    public boolean sameLink(Link that) {
        return this.name.equals(that.name) && this.page == that.page;
    }
    //is this Link the same as that item?
    public boolean sameItem(IItem that) {
        return this.sameLink(that.asLink());
    }
}

/* A website with one text, two images, three pages, and four links:
 *  
 *                -----------------------------
 *               |           WebSite           |
 *               +-----------------------------+
 *               |        WebPage Page 1       |
 *               |-----------------------------|
 *               |"CatsLol"www.cats.com/catslol|
 *               |-----------------------------|   
 *               | +-------+   +------+        |
 *               | |Image  |   |Link  |        |
 *               | |"kitty"|   |"yes" |        |
 *               | |100    |   |Page 2|        | 
 *               | |jpg    |   |      |        |
 *               | +-------+   +------+        |
 *               +-----------------------------+
 *               +-----------------------------+
 *               |       WebPage  Page 2       |
 *               |-----------------------------|
 *               |"Dogs" www.cats.com/dogs     |
 *               |-----------------------------|     
 *               |+------+  +--------+ +------+|
 *               ||Link  |  |  Text  | |Link  ||
 *               ||"he"  |  |   "hi" | |"yes" ||
 *               ||Page 1|  +--------+ |Page 3||
 *               |+------+             +------+|
 *               +-----------------------------+
 *               +-----------------------------+
 *               |        WebPage Page 3       |
 *               |-----------------------------|
 *               |"Ducks" www.cats.com/ducks   |
 *               |-----------------------------|
 *               | +-------+        +------+   |
 *               | |Image  |        |Link  |   |
 *               | |"ducky"|        |"no"  |   |
 *               | |200    |        |Page 1|   |
 *               | |png    |        +------+   |
 *               | +-------+                   |
 *               |                             |
 *               |                             |
 *               +-----------------------------+
 */

// These are examples of a Web Page
class ExamplesWebPage {
    IItem HtDPTxt =     new Text("How to Design Programs");
    IItem HtDP2Txt =     new Text("How to Design Programs");
    IItem HtDPImg =     new Image("htdp", 4300, "tiff");
    IItem HtDP2Img =     new Image("htdp", 4300, "tiff");
    ILoItem MTList =    new MtLoItem();
    ILoItem MTList2 =    new MtLoItem();
    ILoItem HtDPItemsA = new ConsLoItem(HtDPImg, MTList);
    ILoItem HtDPItems = new ConsLoItem(HtDPTxt, HtDPItemsA);
    WebPage HtDPWP =   new WebPage("htdp.org", "HtDP", HtDPItems);
    IItem oodTxt =      new Text("Stay classy, Java");
    IItem oodLnk =      new Link("Back to the Future", HtDPWP);
    IItem ood2Lnk =      new Link("Back to the Future", HtDPWP);
    ILoItem oodItemsA = new ConsLoItem(oodLnk, MTList);
    ILoItem oodItems = new ConsLoItem(oodTxt, oodItemsA);
    WebPage oodWP =    new WebPage("ccs.neu.edu/OOD", "OOD", oodItems);
    IItem fundiesTxtA = new Text("Home sweet home");
    IItem fundiesTxtB = new Text("The Staff");
    IItem fundiesImgA = new Image("wvh-lab", 400, "png");
    IItem fundiesImgB = new Image("profs", 240, "jpeg");
    IItem fundiesLnkA = new Link("A Look Back", HtDPWP);
    IItem fundiesLnkB = new Link("A Look Ahead", oodWP);
    ILoItem fundiesItemsA = new ConsLoItem(fundiesLnkB, MTList);
    ILoItem fundiesItemsB = new ConsLoItem(fundiesLnkA, fundiesItemsA);
    ILoItem fundiesItemsC = new ConsLoItem(fundiesImgB, fundiesItemsB);
    ILoItem fundiesItemsD = new ConsLoItem(fundiesTxtB, fundiesItemsC);
    ILoItem fundiesItemsE = new ConsLoItem(fundiesImgA, fundiesItemsD);
    ILoItem fundiesItemsF = new ConsLoItem(fundiesImgB, fundiesItemsD);
    ILoItem fundiesItemsE2 = new ConsLoItem(fundiesImgA, fundiesItemsD);
    String funURL = new String("ccs.neu.edu/Fundies2");
    ILoItem fundiesItems = new ConsLoItem(fundiesTxtA, fundiesItemsE);
    ILoItem fundiesItems2 = new ConsLoItem(fundiesTxtA, fundiesItemsE);
    WebPage fundiesWP = new WebPage(funURL, "Fundies II", fundiesItems);  
    WebPage fundies2WP = new WebPage(funURL, "Fundies II", fundiesItems);
    WebPage fundies3WP = new WebPage("google.com", "Fundies II", fundiesItems);
    IItem para1 = new Paragraph("Hello World");
    IItem para2 = new Paragraph("Hello World, I am a Computer!");
    IItem para3 = new Paragraph("Hello World");
    IItem head1 = new Header("Hello World");
    IItem head2 = new Header("Hello World!");
    IItem head3 = new Header("Hello World");
    
    boolean testImageSize(Tester t) {
        return t.checkExpect(fundiesWP.totalImageSize(), 9240) &&
                t.checkExpect(oodWP.totalImageSize(), 4300) &&
                t.checkExpect(HtDPWP.totalImageSize(), 4300);
    }
    boolean testTextLength(Tester t) {
        return t.checkExpect(fundiesWP.textLength(), 182);
    }
    boolean testImages(Tester t) {
        return t.checkExpect(fundiesWP.images(), "wvh-lab.png, profs.jpeg, htdp.tiff, htdp.tiff")
                && t.checkExpect(oodItems.listImages().length(), 11);
    } 
    boolean testSameness(Tester t) {
        return t.checkExpect(fundiesWP.sameWebPage(fundies2WP), true) &&
            t.checkExpect(fundiesWP.sameWebPage(fundies3WP), false) &&
            t.checkExpect(fundiesItemsE.sameLoItem(fundiesItemsF), false) &&
            t.checkExpect(fundiesItemsA.sameLoItem(fundiesItemsA), true) &&
            t.checkExpect(MTList.sameLoItem(MTList2), true) &&
            t.checkExpect(HtDPTxt.sameItem(HtDP2Txt), true) &&
            t.checkExpect(oodLnk.sameItem(ood2Lnk), true) &&
            t.checkExpect(HtDP2Img.sameItem(HtDP2Img), true) &&
            t.checkExpect(HtDPTxt.sameItem(oodTxt), false) &&
            t.checkExpect(HtDPImg.sameItem(fundiesImgA), false) && 
            t.checkExpect(oodLnk.sameItem(fundiesLnkA), false) &&
            t.checkExpect(head1.sameItem(head2), false) &&
            t.checkExpect(fundiesItemsA.sameMt(new MtLoItem()), false) &&
            t.checkExpect(head1.sameItem(head3), true) && 
            t.checkExpect(MTList.sameCons(new ConsLoItem(HtDP2Img, HtDPItems)), false) &&
            t.checkExpect(para1.asParagraph().sameItem(para2), false) &&
            t.checkExpect(para1.asParagraph().sameItem(para3), true) &&
            t.checkExpect(para1.sameHeader(head1), false) &&
            t.checkExpect(para1.sameImage(head1), false) &&
            t.checkExpect(para1.sameText(head1), false) &&
            t.checkExpect(para1.sameLink(head1), false) &&
            t.checkExpect(head2.sameParagraph(head1), false);
    }
    boolean testCasting(Tester t) {
        return t.checkExpect(head1.asHeader(), new Header("Hello World")) &&
                t.checkExpect(para1.asParagraph(), para1);
    }
    boolean testBadCast(Tester t) {
        return t.checkException(new ClassCastException(
                "Cannot be casted as a text"), HtDPImg, "asText") &&
            t.checkException(new ClassCastException(
                "Cannot be casted as an image"), HtDPTxt, "asImage") &&
            t.checkException(new ClassCastException(
                "Cannot be casted as a link"), HtDPImg, "asLink") &&
            t.checkException(new ClassCastException(
                "Cannot be casted as a Header"), HtDPImg, "asHeader") &&
            t.checkException(new ClassCastException(
                "Cannot be casted as a Paragraph"), HtDPImg, "asParagraph") &&
            t.checkException(new ClassCastException(
                "Cannot be casted as a text"), para1, "asText") &&
            t.checkException(new ClassCastException(
                "Cannot be casted as a text"), head1, "asText");
    }
}
//When calculating the total size of the Images and forming the string of 
//all images in the fundiesWP, htdp.tiff appears twice because fundiesWP is 
//linked to HtDPWP and oodWP, and oodWP is also linked to HtDPWP, which means 
//the loop of the total image size  and forming the image string functions
//will connect to the HtDPWP page twice, and thus count the htdp.tiff
//image twice