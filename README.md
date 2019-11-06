# Dolly
Dolly library suggest easy conversion between entity and DTO classes

How to convert DTO object to entity objrct and otherwise.
1) Annotate your DTO java class with @Dto annotation and set supported* entity classes as annotation value.

*Supported entities are those entities of which the DTO class consists.

2) Use DEConverter class to convert entities to DTO and otherwise:
1. DEConverter.toDto(EntityClass entity_object, DTOClass dto_object);
2. DEConverter.toEntity(DTOClass dto_object, EntityClass entity_object);
    
Examples:

   Suppose, we have Book and Author entity and BookAndAuthor DTO classes.
   
   1) Convert to DTO
   1. Annotate BookAndAuthor dto class with @Dto annotation.
   
  **@Dto({Book.class, Author.class})**  
  `public class BookAndAuthor { ... }`
   
   2. Use DEConverter to convert entities to dto:
  
  `Book book = new Book();`  
   //Set properties of book object
   ...
  `Author author = new Author();`  
    //Set properties of author object
    ...
 ` BookAndAuthor dto = DEConverter.toDto(book, new BookAndAuthor());  
  dto = DEConverte.toDto(author, dto);`  
  
   3. Now dto object contains properties from book and author objects.

## @DtoProperty annotation 

   In case when you DTO object has field with name not same as related entity field, yoc can annotate this field with @DtoProperty annotation and set entityProperty annotation property with value of related entity field name.
   
`public class Author {
    
   private String author_name;
}`

`@Dto({Book.class, Author.class})  
public class BookAndAuthor { 
  
   **@DtoProperty(entityProperty = "author_name")**  
   private String author;
}`


