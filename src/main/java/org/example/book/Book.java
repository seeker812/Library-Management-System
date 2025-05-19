package org.example.book;

public abstract class Book {
    private final String title;
    private final String author;
    private final String genre;
    private final String format;
    private final int publicationYear;
    private final String ISBN;
    public Book(String title, String author, String genre, String format, int publicationYear, String ISBN) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.format = format;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }
    public String getFormat() {
        return format;
    }
    public int getPublicationYear() {
        return publicationYear;
    }
    public String getISBN() {
        return ISBN;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", format='" + format + '\'' +
                ", publicationYear=" + publicationYear +
                ", ISBN='" + ISBN + '\'' +
                '}';
    }
}
