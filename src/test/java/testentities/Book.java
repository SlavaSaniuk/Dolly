package testentities;

//Entity
public class Book {

    private Integer book_id;

    private String book_title;

    private int number_of_pages;

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

    public int getNumberOfPages() {
        return number_of_pages;
    }

    public void setNumberOfPages(int number_of_pages) {
        this.number_of_pages = number_of_pages;
    }
}
