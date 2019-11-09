package testentities;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoProperty;

@Dto({Book.class, Author.class})
public class BookAndAuthorDto {

    private Integer book_id;

    private String book_title;

    @DtoProperty(entityProperty = "name")
    private String author_name;

    @DtoProperty(entityProperty = "books")
    private String[] author_books;

    public Integer getBookId() {
        return book_id;
    }

    public void setBookId(Integer book_id) {
        this.book_id = book_id;
    }

    public String getBookTitle() {
        return book_title;
    }

    public void setBookTitle(String book_title) {
        this.book_title = book_title;
    }

    public String getAuthorName() {
        return author_name;
    }

    public void setAuthorName(String author_name) {
        this.author_name = author_name;
    }

    public String[] getAuthorBooks() {
        return author_books;
    }

    public void setAuthorBooks(String[] author_books) {
        this.author_books = author_books;
    }
}
