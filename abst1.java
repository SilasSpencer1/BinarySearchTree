import java.util.Comparator;
import tester.Tester;

class Book {
  String title;
  String author;
  int price;
  
  // general constructor
  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

// compare books by title class
class BooksByTitle implements Comparator<Book> {
  public int compare(Book first, Book after) {
    return first.title.compareTo(after.title);
  }
}

// comaring authors of Book
class BooksByAuthor implements Comparator<Book> {
  public int compare(Book first, Book after) {
    return first.author.compareTo(after.author);
  }
}

// comparing prices of Book
class BooksByPrice implements Comparator<Book> {
  public int compare(Book first, Book after) {
    return first.price - after.price;
  }
}

interface IList<T> {
  // to combine to ILists of a Book
  IList<T> add(IList<T> iLoBook);

  // to check if two IList are the same
  boolean sameList(Comparator<T> comparator, IList<T> t);
  
  // counts the length of an IList
  int count();
  
  // gets the first item in an IList
  T getFirst();

  // gets the rest of items in an Ilist
  IList<T> getRest();
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  
  // to get the length of a ConsList
  public int count() {
    return 1 + this.rest.count();
  }
  
  // to combine two IList together
  public IList<T> add(IList<T> that) {
    return new ConsList<T>(this.first, this.rest.add(that));
  }

  // to determine if two IList are the same
  public boolean sameList(Comparator<T> item, IList<T> that) {
    if (this.count() != that.count()) {
      return false;
    } else {
      return item.compare(this.first, that.getFirst()) 
          == 0 && 
          sameRestList(item, that);
    }
  }
  
  // to compare the rest of a list to see if it is the same
  public boolean sameRestList(Comparator<T> item, IList<T> that) {
    return this.rest.sameList(item, that.getRest());
  }

  // to get the first item in a ConsList
  public T getFirst() {
    return this.first;
  }

  // to get everything but the first in a ConsList
  public IList<T> getRest() {
    return this.rest;
  }
}

class MtList<T> implements IList<T> {
  
  MtList() {
  }
  
  // to get the first item of an empty list
  public T getFirst() {
    throw new RuntimeException("Error... List is empty");
  }

  // to get all but first items in an empty list
  public IList<T> getRest() {
    throw new RuntimeException("Error... List is empty");
  }

  // to add an IList and an emtpy list
  public IList<T> add(IList<T> iLoBook) {
    return iLoBook;
  }

  // to check if an empty list and another list are the same
  public boolean sameList(Comparator<T> emptyList, IList<T> that) {
    return true;
  }

  // to return the length of an empty list
  public int count() {
    return 0;
  }

}


interface IBST<T> {
  
  // takes an item and inserts it into a binary search tree
  ABST<T> insert(T item);

  // to determine wehter an item is in a bin search tree
  boolean present(T item);
  
  // help method to get the left most item in a tree
  T getLeftMostHelp(T item);

  // to get the left most item in a bin search tree
  T getLeftmost();

  // returns data field
  T getdata();

  // gets left side of bin search tree
  ABST<T> getLeftSide();

  // gets right side of bin search tree
  ABST<T> getRightSide();

  // to return only the right side of a bin search tree
  ABST<T> getRight();

  // helper method to get the right portion of BST
  ABST<T> getRightHelp(ABST<T> item, T data);

  // same helper but for items only (leaf)
  ABST<T> getRightHelp(ABST<T> item);
  
  // to determine if two trees have the same data
  boolean sameData(ABST<T> tree);

  // to determine if two trees are the same
  boolean sameTree(ABST<T> prompt);

  // builds an IList (racket method)
  IList<T> buildList();

}

abstract class ABST<T> implements IBST<T> {
  Comparator<T> order;
}

// gen constructor
class Leaf<T> extends ABST<T> {
  Leaf(Comparator<T> order) {
    this.order = order;
  }
  
  /*- TEMPLATE:
   * 
   * Fields:
   * order : Comparator
   * 
   * Methods:
   * this.getLeftMost() : T
   * this.getLeftMostHelp: t
   * this.getRight() : ABST<T>
   * this.insert(T item) : ABST<T>
   * this.present(T item) : boolean
   * this.sameTree(ABST<T> tree) : boolean 
   * this.SameTreeHelp(ABST<T> tree) : boolean
   * this.buildList() : IList<T>
   * this.sameData(ABST<T> tree) : boolean
   * this.getData() : T
   * this.getLeftSide() : ABST<T>
   * this.getRightSide() : ABST<T>
   * this.getRightHelperL(ABST<T> item, T data) : ABST<T>
   * this.getRightHelperR(ABST<T> item) : ABST<T> 
   * 
   * 
   */
   
  // gets data of a leaf (error)
  public T getdata() {
    throw new RuntimeException("no order found");
  }

  // gets left side of a leaf (error)
  public ABST<T> getLeftSide() {
    throw new RuntimeException("error... empty tree");
  }

  // gets right side of a leaf helpmethod (error)
  public ABST<T> getRightSide() {
    throw new RuntimeException("No right of an empty tree");
  }
  
  // to get the right side of a tree
  public ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  // inserts a leaf into a new node
  public ABST<T> insert(T t) {
    return new Node<T>(this.order, t, this, this);
  }

  // no leaf can be present in a tree (base case)
  public boolean present(T t) {
    return false;
  }

  // no left most in a leaf
  public T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  // to return leaf
  public T getLeftMostHelp(T item) {
    return item;
  }

  // get the right side help method returns the leaf
  public ABST<T> getRightHelp(ABST<T> order, T data) {
    return order;
  }

  // to get the right side helper but only one arg.
  public ABST<T> getRightHelp(ABST<T> order) {
    return order;
  }
  
  // to see if the order of two items is the same
  public boolean equalorder(ABST<T> item) {
    return true;
  }

  // to determine if the data of two items is the same
  public boolean sameData(ABST<T> item) {
    return true;
  }
  
  // to determine if a leaf and a tree are the same
  public boolean sameTree(ABST<T> tree) {
    return true;
  }

  // builds an empty list
  public IList<T> buildList() {
    return new MtList<T>();
  }
}

class Node<T> extends ABST<T> {
  ABST<T> left;
  ABST<T> right;
  T data;

  // gen constructor
  Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    this.data = data;
    this.order = order;
    this.left = left;
    this.right = right;
  }
  
  /*- TEMPLATE...
   * 
   * Fields...
   * order ... Comparator<T>
   * data ... T
   * left ... ABST<T>
   * right ... ABST<T>
   * 
   * 
   * Methods...
   * xthis.getLeftSide() ... ABST<T>
   * this.getRightSide() ... ABST<T>
   * this.insert(T item) ... ABST<T>
   * this.present(T item) ... boolean
   * this.getLeftMost() ... T
   * this.getLeftMostHel-(T item) ... T
   * this.getRight() ... ABST<T>
   * this.getRightHelp(ABST<T> item, T data) ... ABST<T>
   * this.getRightHelp(ABST<T> item) ... ABST<T> 
   * this.sameTree(ABST<T> Tree) ... boolean 
   * this.sameTreeHelp(ABST<T> item) ... boolean
   * this.buildList() ... IList<T> ILIst
   * this.sameData(ABST<T> item) ... boolean
   * this.getData() ... T
   * 
   * 
   * Methods:
   * 
   * 
   * this.left.getLeftMostHelp(T item) ... T 
   * this.right.getRightMostHelp(ABST<T> item, T data) ... ABST<T>
   * this.left.getRightMostHelp ABST<T> item) ... ABST<T>
   * this.sameTreeHelp(ABST<T> tree) ... boolean
   * this.buildList() ... IList<T>
   * this.right.sameTreeHelp(ABST<T> tree) ... boolean
   */
  
  // adds a new node to an existing
  public ABST<T> insert(T item) {
    if (this.order.compare(this.data, item) > 0) {
      return new Node<T>(order, this.data, this.left.insert(item), right);
    }
    else {
      return new Node<T>(this.order, this.data, this.left, this.right.insert(item));
    }
  }
  
  // gets the leftmost item of a node
  public T getLeftmost() {
    return this.left.getLeftMostHelp(this.data);
  }

  // ^
  public T getLeftMostHelp(T t) {
    return this.left.getLeftMostHelp(this.data);
  }

  // gets the right side of a tree
  public ABST<T> getRight() {
    return this.right
        .getRightHelp(this.left.getRightHelp(new Leaf<T>(this.order), this.data));
  }
  
  // checks both right and left sides as well as the item to determine
  // if the node is present in another item
  public boolean present(T item) {
    return (this.order.compare(this.data, item) == 0 || 
        this.left.present(item) || 
        this.right.present(item));
  }
  
  // gets the right side of the left field and returns the inserted item and data args
  public ABST<T> getRightHelp(ABST<T> item, T data) {
    return this.left.getRightHelp(this.right.getRightHelp(item.insert(data)), this.data);
  }

  // get Right Helper when there is no data
  public ABST<T> getRightHelp(ABST<T> item) {
    return this.left.getRightHelp(this.right.getRightHelp(item.insert(this.data)));
  }

  // to check if the data of two trees is the same
  public boolean sameData(ABST<T> tree) { 
    if (this.equals(tree)) {
      return true;
    }
    else if (this.buildList().sameList(this.order, tree.buildList())) {
      return this.sameTree(tree);
    }

    return false;
  }
  
  public boolean sameTree(ABST<T> tree) {
    if (this.order.compare(this.data, tree.getdata()) == 0) {
      return this.left.sameTree(tree.getLeftSide())
          && this.right.sameTree(tree.getRightSide());
    }
    else {
      return false;
    }
  }

  public IList<T> buildList() {
    return this.left.buildList().add(
        new ConsList<T>(this.data, new MtList<T>()))
        .add(this.right.buildList());
  }
  
  
  // returning fields...
  
  public ABST<T> getRightSide() {
    return this.right;
  }



  public ABST<T> getLeftSide() {
    return this.left;
  }
  
  public T getdata() {
    return this.data;
  }

}


class ExamplesBST {
  Comparator<Book> byTitle = new BooksByTitle();
  Comparator<Book> byAuthor = new BooksByAuthor();
  Comparator<Book> byPrice = new BooksByPrice();

  Book Silas = new Book("Silas", "Silas", 5);
  Book grandma = new Book("How to hide ur grandma", "Me", 0);
  Book noDavid = new Book("noDavid", "Me again", 13);
  Book f451 = new Book("farienheit 451", "i forget", 5);
  Book TKAM = new Book("TKAM", "Harper lee", 8);
  Book animalFarm = new Book("animal Farm", "", 2);
  Book andre = new Book("andre", "andre", 20);

  IList<Book> listBook1 = new ConsList<Book>(grandma,
      new ConsList<Book>(noDavid, new ConsList<Book>(TKAM,
          new ConsList<Book>(Silas, new ConsList<Book>(f451, new MtList<Book>())))));
  IList<Book> listBook2 = new ConsList<Book>(noDavid, new MtList<Book>());
  IList<Book> listBook3 = new ConsList<Book>(grandma,
      new ConsList<Book>(animalFarm, new ConsList<Book>(noDavid, new ConsList<Book>(TKAM,
          new ConsList<Book>(Silas, new ConsList<Book>(f451, new MtList<Book>()))))));
  ABST<Book> leaf = new Leaf<Book>(byTitle);
  ABST<Book> leafPrice = new Leaf<Book>(byPrice);

  // a tree built ordered by titles
  ABST<Book> tree1Author = new Node<Book>(byTitle, noDavid,
      new Node<Book>(byTitle, grandma, leaf, leaf),
      new Node<Book>(byTitle, Silas, new Node<Book>(byTitle, TKAM, leaf, leaf),
          new Node<Book>(byTitle, f451, leaf, leaf)));

  // tree built ordered by price
  ABST<Book> tree1Price = new Node<Book>(byPrice, grandma,
      new Node<Book>(byPrice, Silas, leafPrice, leafPrice),
      new Node<Book>(byPrice, f451, new Node<Book>(byPrice, andre, leafPrice, leafPrice),
          new Node<Book>(byPrice, noDavid, leafPrice, leafPrice)));

  ABST<Book> tree1 = new Node<Book>(byTitle, noDavid, new Node<Book>(byTitle, grandma, leaf, leaf),
      new Node<Book>(byTitle, Silas, new Node<Book>(byTitle, TKAM, leaf, leaf),
          new Node<Book>(byTitle, f451, leaf, leaf)));

  ABST<Book> tree2 = new Node<Book>(byTitle, noDavid, leaf, leaf);

  ABST<Book> tree3 = new Node<Book>(byTitle, noDavid,
      new Node<Book>(byTitle, grandma, leaf, new Node<Book>(byTitle, animalFarm, leaf, leaf)),
      new Node<Book>(byTitle, Silas, new Node<Book>(byTitle, TKAM, leaf, leaf),
          new Node<Book>(byTitle, f451, leaf, leaf)));


  //to check if getRight actually gets the left side of a BST
  boolean testGetLeftmost(Tester t) {
    return t.checkExpect(tree1Price.getLeftmost(), Silas)
        && t.checkExpect(tree2.getLeftmost(), noDavid) && t.checkExpect(
            tree3.getLeftmost(), grandma)
        && t.checkExpect(tree1Author.getLeftmost(), grandma)
        && t.checkException(new RuntimeException("No leftmost item of an empty tree"), leaf,
            "getLeftmost");
  }

  // to check if getRight actually gets the right side of a BST
  boolean testGetRight(Tester t) {
    return t.checkException(new RuntimeException("No "
        + "right of an empty tree"), leaf, "getRight");
  }

  // to check if an item is in a BST or not
  boolean testPresent(Tester t) {
    return t.checkExpect(leaf.present(animalFarm), false)
        && t.checkExpect(tree1Author.present(TKAM), true)
        && t.checkExpect(tree1Author.present(andre), false)
        && t.checkExpect(tree1Author.insert(andre).present(andre), true);
  }

  // to test if an item is accuratelt being inserted into a BST
  boolean testInsert(Tester t) {
    return t.checkExpect(tree1Author.insert(animalFarm), tree3)
        && t.checkExpect(leaf.insert(noDavid), tree2)
        && t.checkExpect(tree2.insert(grandma),
            new Node<Book>(byTitle, noDavid, new Node<Book>(byTitle, grandma, leaf, leaf), leaf));
  }
  
  // to test if the data of two items are the same or not
  boolean testsameData(Tester t) {
    return t.checkExpect(tree1Author.sameData(tree1), true)
        && t.checkExpect(tree1Author.sameData(tree3), false)
        && t.checkExpect(tree2.sameData(leaf), false) && t.checkExpect(leaf.sameData(leaf), true)
        && t.checkExpect(tree1Author.sameData(tree1Author), true)
        && t.checkExpect(tree1Author.sameData(tree1Price), false);
  }

  // to test if two trees are the same or not
  boolean testsameTree(Tester t) {
    return t.checkExpect(tree1Author.sameTree(tree1Author), true)
        && t.checkExpect(tree3.sameTree(tree1Author), false)
        && t.checkExpect(tree1Author.sameTree(tree1), true)
        && t.checkExpect(tree1Author.sameTree(tree1Price), false);
  }

  // to test if lists are accurately being built
  boolean testBuildList(Tester t) {
    return t.checkExpect(leaf.buildList(), new MtList<Book>())
        && t.checkExpect(tree2.buildList(), listBook2)
        && t.checkExpect(tree1Author.buildList(), listBook1) && 
        t.checkExpect(tree2.buildList(), listBook2);
  }
}



