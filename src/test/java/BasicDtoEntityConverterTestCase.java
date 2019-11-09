import by.bsac.core.beans.BasicDtoEntityConverter;
import by.bsac.core.exceptions.DollyException;
import by.bsac.core.exceptions.NoDtoClassException;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testentities.AuthorDto;
import testentities.Book;
import testentities.BookDto;

class BasicDtoEntityConverterTestCase {

    @Test
    void basicDtoEntityConverter_dtoHasSupportedClasses_shouldCreateNewConverter() throws DollyException {

        BasicDtoEntityConverter<BookDto> converter = new BasicDtoEntityConverter<>(BookDto.class);

        Assertions.assertNotNull(converter);

    }

    @Test
    void basicDtoEntityConverter_dtoHasNotSupportedClasses_shouldThrowNoSupportedEntitiesException() {
        Assertions.assertThrows(NoDtoClassException.class,() -> new BasicDtoEntityConverter<>(AuthorDto.class));
    }

    @Test
    void toEntity_dtoWithValues_shouldReturnEntityWithValues() throws NoSupportedEntitiesException, NoDtoClassException {

        BasicDtoEntityConverter<BookDto> converter = new BasicDtoEntityConverter<>(BookDto.class);

        BookDto dto = new BookDto();
        dto.setBookTitle("Don Quixote");
        dto.setId(24);


        Book book = converter.toEntity(dto, new Book());

        Assertions.assertNotNull(book);
        Assertions.assertEquals(dto.getId(), book.getBookId());
        Assertions.assertEquals(dto.getBookTitle(), book.getBookTitle());
    }

    @Test
    void toDto_entityWithValues_shouldReturnDtoWithValues() throws NoSupportedEntitiesException, NoDtoClassException {

        BasicDtoEntityConverter<BookDto> converter = new BasicDtoEntityConverter<>(BookDto.class);

        Book book = new Book();
        book.setBookId(23);
        book.setBookTitle("Tom Sawyer");
        book.setNumberOfPages(468);


        BookDto dto = converter.toDto(book, new BookDto());

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(book.getBookId(), dto.getId());
        Assertions.assertEquals(book.getBookTitle(), dto.getBookTitle());
    }
}
