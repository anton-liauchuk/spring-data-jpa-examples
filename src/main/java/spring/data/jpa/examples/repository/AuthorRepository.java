package spring.data.jpa.examples.repository;

import org.springframework.data.repository.CrudRepository;
import spring.data.jpa.examples.model.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    /**
     * Finds authors with books in stock
     *
     * @param inStock in stock value
     * @return list of authors
     */
    List<Author> findByBooksInStock(boolean inStock);

}
