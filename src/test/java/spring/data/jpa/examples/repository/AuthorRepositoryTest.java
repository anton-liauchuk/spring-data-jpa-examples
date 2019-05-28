package spring.data.jpa.examples.repository;

import com.querydsl.core.types.Predicate;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import spring.data.jpa.examples.model.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired AuthorRepository authorRepository;

    private Author authorWithBooks;

    @Before
    public void setUp() {
        authorWithBooks = new Author();
        authorWithBooks.setName("author with books in stock");
        final Book bookInStock = new Book(authorWithBooks, true, "book in stock");
        final Book bookNotStock = new Book(authorWithBooks, false, "book not stock");
        authorWithBooks.setBooks(Arrays.asList(bookInStock, bookNotStock));
        final Author authorWithoutBooks = new Author();
        authorWithBooks.setName("author without books in stock");
        authorRepository.saveAll(Arrays.asList(authorWithBooks, authorWithoutBooks));
    }

    @Test
    public void findAuthorsWithBooksInStock() {
        final List<Author> authorsWithBooksInStock = authorRepository.findByBooksInStock(true);
        assertEquals(1, authorsWithBooksInStock.size());
        assertEquals(authorWithBooks.getName(), authorsWithBooksInStock.get(0).getName());
    }

    @Test
    public void findByBooksInStockCustomQueryTest() {
        final List<Author> authorsWithBooksInStock = authorRepository.findByBooksInStockCustomQuery(true);
        assertEquals(1, authorsWithBooksInStock.size());
        assertEquals(authorWithBooks.getName(), authorsWithBooksInStock.get(0).getName());
    }

    @Test
    public void findAuthorsWithBooksInStockBySpecificationTest() {
        final List<Author> authorsWithBooksInStock = authorRepository.findAll((Specification<Author>) (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.join(Author_.books).get(Book_.IN_STOCK), true));
        assertEquals(1, authorsWithBooksInStock.size());
        assertEquals(authorWithBooks.getName(), authorsWithBooksInStock.get(0).getName());
    }

    @Test
    public void findAuthorByNameExampleMatcherTest() {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("books", "id");

        final Example<Author> example = Example.of(authorWithBooks, matcher);
        final Iterable authors = authorRepository.findAll(example);

        assertEquals(1, ((Collection<?>) authors).size());
        assertThat(authorRepository.count(example), is(1L));
        assertThat(((Author) authors.iterator().next()).getName(), CoreMatchers.equalTo(authorWithBooks.getName()));
    }

    @Test
    public void findAuthorWithBooksQuerydslTest() {
        final Predicate predicate = QAuthor.author.name.equalsIgnoreCase(authorWithBooks.getName());
        final Iterable<Author> authorsWithBooksInStock = authorRepository.findAll(predicate);

        assertEquals(1, ((Collection<?>) authorsWithBooksInStock).size());
        assertEquals(authorWithBooks.getName(), authorsWithBooksInStock.iterator().next().getName());
    }

    @Test
    public void findAuthorWithBooksInStockByHibernateFilterTest() {
        final Iterable<Author> authorsWithBooksInStock = authorRepository.findAuthorsWithBooksInStock(true);

        assertEquals(1, ((Collection<?>) authorsWithBooksInStock).size());
        final Author author = authorsWithBooksInStock.iterator().next();
        assertEquals(authorWithBooks.getName(), author.getName());
        assertEquals(1, author.getBooks().size());
        assertEquals("book in stock", author.getBooks().get(0).getName());
    }
}
