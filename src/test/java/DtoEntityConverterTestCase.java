import by.bsac.core.beans.BasicDtoEntityConverter;
import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.core.exceptions.NoDtoClassException;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testentities.Author;
import testentities.Book;
import testentities.BookAndAuthorDto;

class DtoEntityConverterTestCase {

    private DtoEntityConverter<BookAndAuthorDto> CONVERTER;

    @BeforeEach
    void setUp() {
        try {
            CONVERTER = new BasicDtoEntityConverter<>(BookAndAuthorDto.class);
        } catch (NoDtoClassException | NoSupportedEntitiesException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toDto_bookAndAuthorEntities_shouldReturnDto() {

        Book book = new Book();
        book.setBookId(24);
        book.setBookTitle("title");

        Author author = new Author();
        author.setName("NAME");
        author.setBooks(new String[] {"BOOK1", "BOOK2", "BOOK3"});

        BookAndAuthorDto dto = CONVERTER.toDto(book, new BookAndAuthorDto());
        dto = CONVERTER.toDto(author, dto);

        Assertions.assertEquals(book.getBookId(), dto.getBookId());
        Assertions.assertEquals(book.getBookTitle(), dto.getBookTitle());

        Assertions.assertEquals(author.getName(), dto.getAuthorName());
        Assertions.assertEquals(author.getBooks(), dto.getAuthorBooks());
    }

    @Test
    void toEntity_bookAndAuthorDto_shouldReturnEntities() {

        BookAndAuthorDto dto = new BookAndAuthorDto();
        dto.setBookId(23);
        dto.setBookTitle("TITLE");
        dto.setAuthorName("NAME");
        dto.setAuthorBooks(new String[] {"BOOK24", "BOOK65", "BOOK_128"});

        Book book = CONVERTER.toEntity(dto, new Book());
        Author author = CONVERTER.toEntity(dto, new Author());

        Assertions.assertEquals(book.getBookId(), dto.getBookId());
        Assertions.assertEquals(book.getBookTitle(), dto.getBookTitle());

        Assertions.assertEquals(author.getName(), dto.getAuthorName());
        Assertions.assertEquals(author.getBooks(), dto.getAuthorBooks());
    }
}
