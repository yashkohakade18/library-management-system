package edu.library_management_system.mapper;


import org.modelmapper.ModelMapper;   // ✅ FIXED
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.library_management_system.dto.Request.BookRequestDto;
import edu.library_management_system.dto.Response.BookResponseDto;
import edu.library_management_system.entities.Book;

@Component
public class BookMapper {

    @Autowired
    private ModelMapper mapper;

    public BookResponseDto toDto(Book book) {
        return mapper.map(book, BookResponseDto.class);
    }

    public Book toEntity(BookRequestDto dto) {
        return mapper.map(dto, Book.class);
    }
}