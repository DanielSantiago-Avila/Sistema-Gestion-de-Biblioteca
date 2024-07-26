package com.api.biblioteca.service.books;

import com.api.biblioteca.dto.books.BookDto;
import com.api.biblioteca.dto.clients.ClientDto;

import java.util.List;


public interface BookService {

    BookDto saveBook(BookDto book);

    List<BookDto> getAllBooks();

    BookDto updateBook(Long id, BookDto bookDto, Long clientId);

}
