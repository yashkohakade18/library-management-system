package edu.library_management_system.controller;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.library_management_system.dto.Request.BookRequestDto;
import edu.library_management_system.dto.Request.UpdatePriceBookdto;
import edu.library_management_system.dto.Response.ApiResponseDto;
import edu.library_management_system.dto.Response.BookResponseDto;
import edu.library_management_system.service.BookService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ✅ GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<BookResponseDto>> getBook(@PathVariable int id) {
        return ResponseEntity.ok(
                ApiResponseDto.success(bookService.getBookByid(id), "Book fetched successfully")
        );
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<BookResponseDto>>> getAllBooks() {
        return ResponseEntity.ok(
                ApiResponseDto.success(bookService.getAllBooks(), "All books fetched")
        );
    }

    // ✅ SEARCH
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<BookResponseDto>> getBookByTitleAndAuthor(
            @RequestParam String title,
            @RequestParam String author) {

        return ResponseEntity.ok(
                ApiResponseDto.success(
                        bookService.getBookByTitleAndAuthor(title, author),
                        "Book fetched successfully"
                )
        );
    }

    // ✅ FILTER BY PRICE
    @GetMapping("/price")
    public ResponseEntity<ApiResponseDto<List<BookResponseDto>>> getBooksByPrice(
            @RequestParam double price) {

        return ResponseEntity.ok(
                ApiResponseDto.success(
                        bookService.getBooksByPrice(price),
                        "Books fetched successfully"
                )
        );
    }

    // ✅ PAGINATION + SORTING
    @GetMapping("/filter")
    public ResponseEntity<ApiResponseDto<List<BookResponseDto>>> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BookResponseDto> books = bookService.getBooks(pageable);

        return ResponseEntity.ok(
                ApiResponseDto.success(books.getContent(), "Books fetched successfully")
        );
    }

    // ✅ ADD
    @PostMapping
    public ResponseEntity<ApiResponseDto<BookResponseDto>> addBook(
            @Valid @RequestBody BookRequestDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.created(bookService.addBook(dto), "Book added"));
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<BookResponseDto>> updateBook(
            @PathVariable int id,
            @Valid @RequestBody BookRequestDto dto) {

        return ResponseEntity.ok(
                ApiResponseDto.success(bookService.updateBook(id, dto), "Book updated")
        );
    }

    // ✅ UPDATE PRICE
    @PatchMapping("/{id}/price")
    public ResponseEntity<ApiResponseDto<BookResponseDto>> updatePrice(
            @PathVariable int id,
            @RequestBody UpdatePriceBookdto dto) {

        return ResponseEntity.ok(
                ApiResponseDto.success(
                        bookService.updateBookPrice(id, dto),
                        "Price updated"
                )
        );
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ApiResponseDto<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ApiResponseDto.success(null, "Deleted successfully");
    }
}