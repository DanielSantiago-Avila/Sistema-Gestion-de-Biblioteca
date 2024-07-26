package com.api.biblioteca.service.books;


import com.api.biblioteca.dto.books.BookDto;
import com.api.biblioteca.entity.BookPostEntity;
import com.api.biblioteca.entity.ClientPostEntity;
import com.api.biblioteca.repository.BooksPostRepository;
import com.api.biblioteca.repository.ClientPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceMap implements BookService {

    @Autowired
    private BooksPostRepository bookRepository;

    @Autowired
    private ClientPostRepository clientRepository;

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public BookDto saveBook(BookDto bookDto) {
        validateBookDto(bookDto);

        BookPostEntity bookEntity = new BookPostEntity();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setAuthor(bookDto.getAuthor());
        bookEntity.setEditorial(bookDto.getEditorial());
        bookEntity.setIsbn(bookDto.getIsbn());
        bookEntity.setPublishedDate(bookDto.getPublishedDate());
        bookEntity.setPrice(bookDto.getPrice());
        bookEntity.setDisponibilidad(bookDto.getDisponibilidad());
        bookEntity.setClient(null); // No client assigned

        BookPostEntity savedBook = bookRepository.save(bookEntity);

        return toDto(savedBook);
    }

    @Override
    public BookDto updateBook(Long id, BookDto bookDto, Long clientId) {
        validateBookDto(bookDto);

        Optional<BookPostEntity> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new IllegalArgumentException("Book not found");
        }

        BookPostEntity bookEntity = bookOptional.get();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setAuthor(bookDto.getAuthor());
        bookEntity.setEditorial(bookDto.getEditorial());
        bookEntity.setIsbn(bookDto.getIsbn());
        bookEntity.setPublishedDate(bookDto.getPublishedDate());
        bookEntity.setPrice(bookDto.getPrice());
        bookEntity.setDisponibilidad(bookDto.getDisponibilidad());

        if (clientId != null) {
            Optional<ClientPostEntity> clientOptional = clientRepository.findById(clientId);
            if (clientOptional.isEmpty()) {
                throw new IllegalArgumentException("Client not found");
            }
            bookEntity.setClient(clientOptional.get());
        } else {
            bookEntity.setClient(null);
        }

        BookPostEntity updatedBook = bookRepository.save(bookEntity);

        return toDto(updatedBook);
    }




    private BookDto toDto(BookPostEntity entity) {
        String clientName = (entity.getClient() != null) ? entity.getClient().getName() : null;
        return new BookDto(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getEditorial(),
                entity.getIsbn(),
                entity.getPublishedDate(),
                entity.getPrice(),
                entity.getDisponibilidad(),
                clientName
        );
    }

    private void validateBookDto(BookDto bookDto) {
        if (bookDto.getTitle() == null || bookDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (bookDto.getAuthor() == null || bookDto.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }

        if (bookDto.getIsbn() == null || bookDto.getIsbn().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }

        if (bookDto.getPublishedDate() == null || bookDto.getPublishedDate().isEmpty()) {
            throw new IllegalArgumentException("Published date cannot be empty");
        }

        if (bookDto.getPrice() == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        if (bookDto.getDisponibilidad() == null || bookDto.getDisponibilidad().isEmpty()) {
            throw new IllegalArgumentException("Availability cannot be empty");
        }
    }

}
