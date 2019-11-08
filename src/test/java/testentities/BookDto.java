package testentities;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoProperty;

@Dto({Book.class})
public class BookDto {

    @DtoProperty(entityProperty = "book_id")
    private Integer id;

    private String book_title;

}
