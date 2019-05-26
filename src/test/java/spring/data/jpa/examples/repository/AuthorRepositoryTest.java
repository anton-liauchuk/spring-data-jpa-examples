package spring.data.jpa.examples.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.data.jpa.examples.model.Author;
import spring.data.jpa.examples.model.Book;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void findAuthorsWithBooksInStock() {
        final Author author = new Author();
        author.setName("author with books in stock");
        final Book bookInStock = new Book(author, true, "book in stock");
        final Book bookNotStock = new Book(author, false, "book not stock");
        author.setBooks(Arrays.asList(bookInStock, bookNotStock));
        final Author authorWithoutBooks = new Author();
        author.setName("author without books in stock");
        authorRepository.saveAll(Arrays.asList(author, authorWithoutBooks));

        final List<Author> authorsWithBooksInStock = authorRepository.findByBooksInStock(true);
        assertEquals(1, authorsWithBooksInStock.size());
        assertEquals(author.getName(), authorsWithBooksInStock.get(0).getName());
    }

    @Test
    public void findByBooksInStockCustomQueryTest() {
        final Author author = new Author();
        author.setName("author with books in stock");
        final Book bookInStock = new Book(author, true, "book in stock");
        final Book bookNotStock = new Book(author, false, "book not stock");
        author.setBooks(Arrays.asList(bookInStock, bookNotStock));
        final Author authorWithoutBooks = new Author();
        author.setName("author without books in stock");
        authorRepository.saveAll(Arrays.asList(author, authorWithoutBooks));

        final List<Author> authorsWithBooksInStock = authorRepository.findByBooksInStockCustomQuery(true);
        assertEquals(1, authorsWithBooksInStock.size());
        assertEquals(author.getName(), authorsWithBooksInStock.get(0).getName());
    }
}
