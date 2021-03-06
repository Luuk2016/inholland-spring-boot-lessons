package nl.luukkenselaar.booksrestapi.controller;

import nl.luukkenselaar.booksrestapi.model.Book;
import nl.luukkenselaar.booksrestapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET ALL BOOKS
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.status(200).body(books);
    }

    // GET BOOKS BY YEAR
    @RequestMapping(value = "/year/{year}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBooksByYear(@PathVariable int year) {
        try {
            Iterable<Book> books = bookService.getBooksByYear(year);
            return ResponseEntity.status(HttpStatus.OK).body(books);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // GET A SPECIFIC BOOK
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        try {
            Optional<Book> book = bookService.getBookById(id);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ADD A NEW BOOK
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        try {
            Book result = bookService.addBook(book);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // DELETE A BOOK
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBookById(@PathVariable int id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}