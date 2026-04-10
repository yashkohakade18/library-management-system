package edu.library_management_system.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.library_management_system.dto.Request.BookRequestDto;
import edu.library_management_system.dto.Request.UpdatePriceBookdto;
import edu.library_management_system.dto.Response.BookResponseDto;

public interface BookService {

    BookResponseDto getBookByid(int id);

    List<BookResponseDto> getAllBooks();

    BookResponseDto getBookByTitleAndAuthor(String title, String author);

    List<BookResponseDto> getBooksByPrice(double price);

    Page<BookResponseDto> getBooks(Pageable pageable);

    BookResponseDto addBook(BookRequestDto dto);

    BookResponseDto updateBook(int id, BookRequestDto dto);

    BookResponseDto updateBookPrice(int id, UpdatePriceBookdto dto);

    void deleteBook(int id);
}
