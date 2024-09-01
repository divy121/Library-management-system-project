package src;

import java.time.Year;

public class Books {
  private String title;
  private String author;
  private String isbn;
  private Year publicationYear;

  public Books(String title, String author, String isbn, Year publicationYear) {
    validateRequrements(title, author, isbn, publicationYear);
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.publicationYear = publicationYear;
  }

  private void validateRequrements(String title, String author, String isbn, Year publicationYear) {
    validateString(title, "Title should not be null or empty");
    validateString(author, "Author should not be null or empty");
    validateString(isbn, "ISBN should not be null or empty");
    if(publicationYear==null){
      throw new IllegalArgumentException("Year should not be null or empty");
    }

  
  }

  private void validateString(String string, String errorMessage) {
    if (string == null || string.isEmpty()) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getisbn() {
    return isbn;
  }

  public Year getYear() {
    return publicationYear;
  }
}