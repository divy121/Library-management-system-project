package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.Year;

import org.junit.Test;

public class BooksTest {

  @Test
  public void testshouldthrowexceptionWhenISBNisNull() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Books("Think and Grow Rich", "Napoleon Hill", null, Year.of(2013));
    });
    assertEquals("ISBN should not be null or empty", exception.getMessage());
  }

  @Test
  public void testshouldthrowexceptionWhenTitleisNull() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Books(null, "Napoleon Hill", "978-0-13-713526-1", Year.of(2013));
    });
    assertEquals("Title should not be null or empty", exception.getMessage());
  }

  @Test
  public void testshouldthrowexceptionWhenAuthorisEmpty() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Books("Think and Grow Rich", "", "978-0-13-713526-1", Year.of(2013));
    });
    assertEquals("Author should not be null or empty", exception.getMessage());
  }

  @Test
  public void testshouldthrowexceptionWhenPubYearisNull() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new Books("Think and Grow Rich", "Napoleon Hill", "978-0-13-713526-1", null);
    });
    assertEquals("Year should not be null or empty", exception.getMessage());
  }
}