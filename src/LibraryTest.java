package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Year;
import java.util.Map;

import org.junit.Test;

public class LibraryTest {
  Library library = new Library("MJ");

  @Test
  public void testLibraryNameshouldnotbeEmpty() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Library("");
    });

  }

  @Test
  public void testLibraryNameshouldnotbeNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Library(null);
    });
  }

  @Test
  public void testAddUserShouldThrowExceptionWhenUsernameisEmpty() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      library.addUser(new User("", User.Role.ADMIN, "1234"));
    });
    assertEquals("Username should not be empty", exception.getMessage());
  }

  @Test
  public void testshouldAllowAdminToAddBook() {
    User user = new User("Tushar", User.Role.ADMIN, "1234");
    Books book = new Books("Think and Grow Rich", "Napoleon Hill", "978-0-13-713526-1", Year.of(2013));
    library.addBook(user, book);
    assertEquals(book, library.getBookByISBN("978-0-13-713526-1"));
  }

  @Test
  public void testshouldNotAllowNonAdminToAddBook() {
    User user = new User("Tushar", User.Role.USER, "1234");
    Books book = new Books("Think and Grow Rich", "Napoleon Hill", "978-0-13-713526-1", Year.of(2013));
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      library.addBook(user, book);
    });
    assertEquals("User not permitted to add book", exception.getMessage());
  }

  @Test
  public void testshouldAddUsertoLibrary() {
    User admin = new User("Tushar", User.Role.ADMIN, "1234");
    library.addUser(admin);
    assertEquals(admin, library.getUserByName("Tushar"));
  }

  @Test
  public void testshouldNotAllowDuplicateUsers() {
    User primaryadmin = new User("Tushar", User.Role.ADMIN, "1234");
    User secondaryadmin = new User("Tushar", User.Role.ADMIN, "1234");
    library.addUser(primaryadmin);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      library.addUser(secondaryadmin);
    });
    assertEquals("Username already exists", exception.getMessage());
  }

  @Test
  public void testshouldFetchUserByUserName() {
    User primaryadmin = new User("Tushar", User.Role.ADMIN, "1234");
    library.addUser(primaryadmin);
    assertEquals(primaryadmin, library.getUserByName("Tushar"));
  }

  @Test
  public void testshouldRetrivelAllAvailableBooks() {
    User admin = new User("Tushar", User.Role.ADMIN, "1234");
    Books book1 = new Books("Think and Grow Rich", "Napoleon Hill", "978-0-13-713526-1", Year.of(2013));
    Books book2 = new Books("Atomic Habits", "james clear", "978-0-13-715446-1", Year.of(2012));
    library.addUser(admin);
    library.addBook(admin, book1);
    library.addBook(admin, book2);

    Map<String, Books> availablebooks = library.viewAvailableBooks();
    assertEquals(2, availablebooks.size());
    assertTrue(availablebooks.containsKey("978-0-13-713526-1"));
    assertTrue(availablebooks.containsKey("978-0-13-715446-1"));
  }

  @Test
  public void testshouldreturnUnmodifiableMapofAvailableBooks() {
    User admin = new User("Tushar", User.Role.ADMIN, "1234");
    Books book1 = new Books("Think and Grow Rich", "Napoleon Hill", "978-0-13-713526-1", Year.of(2013));
    library.addUser(admin);
    library.addBook(admin, book1);

    Map<String, Books> availablebooks = library.viewAvailableBooks();
    assertThrows(UnsupportedOperationException.class, () -> {
      availablebooks.put("978-0-13-713526-1",
          new Books("Atomic Habits", "James Clear", "978-0-13-715446-1", Year.of(2012)));
    });
  }

  @Test
  public void testshouldAllowToBorrowBookFromLibrary() {
    User admin = new User("Tushar", User.Role.ADMIN, "1234");
    User user = new User("Himesh", User.Role.USER, "1564");
    Books book = new Books("Think and Grow Rich", "Napoleon Hill", "978-0-13-713526-1", Year.of(2013));
    library.addUser(admin);
    library.addUser(user);
    library.addBook(admin, book);
    library.borrowBook(user, "978-0-13-713526-1");
    Books borrowedBook = library.getBookByISBN("978-0-13-713526-1");
    assertNull(borrowedBook, "borrowed book should be null as it has been borrowed earlier");
  }

  @Test
  public void testshouldThrowexceptionWhenBookNotFoundDuringBorrowRequest() {
    User user = new User("Divy", User.Role.USER, "4562");
    library.addUser(user);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      library.borrowBook(user, "978-0-13-713526-1");
    });
    assertEquals("Book not found", exception.getMessage());
    // Book not found
  }

  @Test
  public void testshouldNotAllowUserToBorrowBookWhenBookAlreadyBorrowed() {
    User admin = new User("Tushar", User.Role.ADMIN, "1234");
    User user1 = new User("Divy", User.Role.USER, "4562");
    User user2 = new User("Himesh", User.Role.USER, "1564");
    Books book = new Books("Think and Grow Rich", "Napoleon Hill", "978-0-13-713526-1", Year.of(2013));
    library.addUser(admin);
    library.addUser(user1);
    library.addUser(user2);
    library.addBook(admin, book);
    library.borrowBook(user1, "978-0-13-713526-1");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      library.borrowBook(user2, "978-0-13-713526-1");
    });
    assertEquals("Book already borrowed", exception.getMessage());
  }

  @Test
  public void testshouldReturnborrowerNameWhoBorrowedBook() {
    User admin = new User("Tushar", User.Role.ADMIN, "1234");
    User user1 = new User("Divy", User.Role.USER, "4562");
    User user2 = new User("Himesh", User.Role.USER, "1564");
    Books book = new Books("Think and Grow Rich", "Napoleon Hill", "978-0-13-713526-1", Year.of(2013));
    library.addUser(admin);
    library.addUser(user1);
    library.addUser(user2);
    library.addBook(admin, book);
    library.borrowBook(user1, "978-0-13-713526-1");
    assertEquals("Divy", library.getBorrowerNameByISBN("978-0-13-713526-1"));
  }

  @Test
  public void testshouldAllowusertoReturnBooktoLibrary() {
    User admin = new User("Tushar", User.Role.ADMIN, "1234");
    User user1 = new User("Divy", User.Role.USER, "4562");
    Books book = new Books("Think and Grow Rich", "Napoleon Hill", "978-0-13-713526-1", Year.of(2013));
    library.addUser(admin);
    library.addUser(user1);
    library.addBook(admin, book);
    library.borrowBook(user1, "978-0-13-713526-1");
    library.returnBook(user1, "978-0-13-713526-1");
    Books returnedbook = library.getBookByISBN("978-0-13-713526-1");
    assertNotNull(returnedbook,"Returned book have be available in the library");
  }

  @Test
  public void testshouldthrowexceptionwhenUserReturnsBookThatIsNotBorrowedByHim() {
    User admin = new User("Tushar", User.Role.ADMIN, "1234");
    User user1 = new User("Divy", User.Role.USER, "4562");
    User user2 = new User("Himesh", User.Role.USER, "1564");
    Books book = new Books("Think and Grow Rich", "Napoleon Hill", "978-0-13-713526-1", Year.of(2013));
    library.addUser(admin);
    library.addUser(user1);
    library.addUser(user2);
    library.addBook(admin, book);
    library.borrowBook(user1, "978-0-13-713526-1");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      library.returnBook(user2, "978-0-13-713526-2");
    });
    assertEquals("Book not borrowed by any user", exception.getMessage());
  }

  @Test
  public void testshouldthrowexceptionWhenNoOneBorrowedBook() {
    User admin = new User("Tushar", User.Role.ADMIN, "1234");
    User user1 = new User("Divy", User.Role.USER, "4562");
    Books book = new Books("Think and Grow Rich", "Napoleon Hill", "978-0-13-713526-1", Year.of(2013));
    library.addUser(admin);
    library.addUser(user1);
    library.addBook(admin, book);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      library.returnBook(user1, "978-0-13-713526-1");
    });
    assertEquals("Book not borrowed by any user", exception.getMessage());
  }
}