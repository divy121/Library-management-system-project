package src;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class Library{
  String name;
  private final Map<String, Books> bookInventory;
  private final Map<String, User> userCatalog;
  private final Map<String, String> borrowedBooks;
  private final Map<String, Books> borrowedBooksDetails;
  
  public Library(String name){
    validateString(name, "Name should not be empty");
    
    this.name = name;
    this.bookInventory = new HashMap<String, Books>();
    this.userCatalog = new HashMap<String, User>();
    this.borrowedBooks = new HashMap<String, String>();
    this.borrowedBooksDetails = new HashMap<String, Books>();
  }

  private void validateString(String name2, String string) {
    if (name2 == null || name2.isEmpty()) {
      throw new IllegalArgumentException(string);
    }
  }
  private void validateUser(User user, String errorMessage) {
    if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
      throw new IllegalArgumentException(errorMessage);
    }
    
  }
  //  Add a method to check if the user exists
  public void addUser(User user) {
    validateUser(user,"Username should not be empty");
    if(userCatalog.containsKey(user.getUsername())){
      throw new IllegalArgumentException("Username already exists");
    } 
    userCatalog.put(user.getUsername(), user);
   }

 //  Add a method to get a user by username
   public User getUserByName(String username){
     return userCatalog.get(username);
   }
   
  // Add a method to add a book to the library
   public void addBook(User user, Books book){
     validateUser(user, "Username should not be empty");
     validateBookNotNull(book, "Book not found");
     if(user.isPermittedToAddBook()){
      bookInventory.put(book.getisbn(), book);
     }else{
       throw new IllegalArgumentException("User not permitted to add book");
     }
    }

    private void validateBookNotNull(Books book, String errorMessage) {
      if (book == null || book.getisbn() == null || book.getisbn().isEmpty()) {
        throw new IllegalArgumentException(errorMessage);
      }
    }

    private boolean isBookBorrowedBySomeuser(String isbn){
      return borrowedBooks.containsKey(isbn);
    }

    //  Add a method to borrow a book
    public void borrowBook(User user, String isbn){
      validateUser(user, "Username should not be empty");
      Books book = bookInventory.get(isbn);
      if(isBookBorrowedBySomeuser(isbn)){
        throw new IllegalArgumentException("Book already borrowed");
      }
      validateBookNotNull(book, "Book not found");

      borrowedBooks.put(isbn, user.getUsername());
      borrowedBooksDetails.put(isbn, book);
      bookInventory.remove(isbn);
    }

    //  Add a method to return a book
    public void returnBook(User user, String isbn){
      validateUser(user, "Username should not be empty");
      if (!borrowedBooks.containsKey(isbn)) {
        throw new IllegalArgumentException("Book not borrowed by any user");
      }
      if(!user.getUsername().equals(borrowedBooks.get(isbn))){
        throw new IllegalArgumentException("book not borrowed by user");
      }
      Books book = borrowedBooksDetails.get(isbn);
      borrowedBooks.remove(isbn);
      borrowedBooksDetails.remove(isbn);
      bookInventory.put(isbn, book);
        
      }
      public String getBorrowerNameByISBN(String isbn){
        return borrowedBooks.get(isbn);
      }
      //  Add a method to view available books
      public Map<String,Books> viewAvailableBooks(){
        return Collections.unmodifiableMap(new HashMap<>(bookInventory));
      }
      public Books getBorrowedBooksDetails(String isbn){
        return borrowedBooksDetails.get(isbn);
      }
      public Books getBookByISBN(String isbn){
        return bookInventory.get(isbn);
      }
    }