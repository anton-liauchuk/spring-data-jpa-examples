package spring.data.jpa.examples.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import spring.data.jpa.examples.model.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long>, JpaSpecificationExecutor {

    /**
     * Finds authors with books in stock.
     *
     * @param inStock in stock value
     * @return list of authors
     */
    List<Author> findByBooksInStock(boolean inStock);


    /**
     * Finds authors with books in stock.
     *
     * @param inStock in stock value
     * @return list of authors
     */
    @Query("select author from Author author JOIN FETCH author.books books WHERE books.inStock =:inStock")
    List<Author> findByBooksInStockCustomQuery(@Param("inStock") boolean inStock);

}
