package spring.data.jpa.examples.repository;

import spring.data.jpa.examples.model.Author;

public interface AuthorRepositoryCustom {
    Iterable<Author> findAuthorsWithBooksInStock(boolean inStock);
}
