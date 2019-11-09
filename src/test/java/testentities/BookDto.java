package testentities;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoProperty;

@Dto({Book.class})
public class BookDto {

    @DtoProperty(entityProperty = "book_id")
    private Integer id;

    private String book_title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookTitle() {
        return book_title;
    }

    public void setBookTitle(String book_title) {
        this.book_title = book_title;
    }
}
