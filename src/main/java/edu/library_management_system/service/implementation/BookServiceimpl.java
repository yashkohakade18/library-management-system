package edu.library_management_system.service.implementation;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.library_management_system.Exception.ResourceNotFoundException;
import edu.library_management_system.dto.Request.BookRequestDto;
import edu.library_management_system.dto.Request.UpdatePriceBookdto;
import edu.library_management_system.dto.Response.BookResponseDto;
import edu.library_management_system.entities.Book;
import edu.library_management_system.mapper.BookMapper;
import edu.library_management_system.repository.BookRepository;
import edu.library_management_system.service.BookService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceimpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookmapper;

    // 🔹 Common method (avoid code repetition)
    private Book getBookOrThrow(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    // ✅ GET BY ID
    @Override
    public BookResponseDto getBookByid(int id) {
        return bookmapper.toDto(getBookOrThrow(id));
    }

    // ✅ GET ALL
    @Override
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookmapper::toDto)
                .toList();
    }

    // ✅ SEARCH
    @Override
    public BookResponseDto getBookByTitleAndAuthor(String title, String author) {
        Book book = bookRepository.findByTitleAndAuthor(title, author);

        if (book == null) {
            throw new ResourceNotFoundException(
                    "Book not found with title: " + title + " and author: " + author
            );
        }

        return bookmapper.toDto(book);
    }

    // ✅ FILTER BY PRICE
    @Override
    public List<BookResponseDto> getBooksByPrice(double price) {
        List<Book> books = bookRepository.getBooksByPrice(price);

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("No books found with price: " + price);
        }

        return books.stream()
                .map(bookmapper::toDto)
                .toList();
    }

    // ✅ PAGINATION + SORTING
    @Override
    public Page<BookResponseDto> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookmapper::toDto);
    }

    // ✅ ADD
    @Override
    public BookResponseDto addBook(BookRequestDto dto) {
        Book book = bookmapper.toEntity(dto);
        return bookmapper.toDto(bookRepository.save(book));
    }

    // ✅ UPDATE FULL
    @Override
    public BookResponseDto updateBook(int id, BookRequestDto dto) {
        Book book = getBookOrThrow(id);

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        book.setYear(dto.getYear());

        return bookmapper.toDto(bookRepository.save(book));
    }

    // ✅ UPDATE PRICE ONLY
    @Override
    public BookResponseDto updateBookPrice(int id, UpdatePriceBookdto dto) {
        Book book = getBookOrThrow(id);

        book.setPrice(dto.getPrice());

        return bookmapper.toDto(bookRepository.save(book));
    }

    // ✅ DELETE
    @Override
    public void deleteBook(int id) {
        Book book = getBookOrThrow(id);
        bookRepository.delete(book);
    }
}