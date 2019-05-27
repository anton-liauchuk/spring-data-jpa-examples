package spring.data.jpa.examples.repository;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import spring.data.jpa.examples.model.Author;
import spring.data.jpa.examples.model.Author_;
import spring.data.jpa.examples.model.Book;
import spring.data.jpa.examples.model.Book_;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
        authorWithoutBooks.setName("author without books in stock");
        authorRepository.saveAll(Arrays.asList(author, authorWithoutBooks));

        final List<Author> authorsWithBooksInStock = authorRepository.findByBooksInStockCustomQuery(true);
        assertEquals(1, authorsWithBooksInStock.size());
        assertEquals(author.getName(), authorsWithBooksInStock.get(0).getName());
    }

    @Test
    public void findAuthorsWithBooksInStockBySpecificationTest() {
        final Author author = new Author();
        author.setName("author with books in stock");
        final Book bookInStock = new Book(author, true, "book in stock");
        final Book bookNotStock = new Book(author, false, "book not stock");
        author.setBooks(Arrays.asList(bookInStock, bookNotStock));
        final Author authorWithoutBooks = new Author();
        authorWithoutBooks.setName("author without books in stock");
        authorRepository.saveAll(Arrays.asList(author, authorWithoutBooks));

        final List<Author> authorsWithBooksInStock = authorRepository.findAll((Specification<Author>) (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.join(Author_.books).get(Book_.IN_STOCK), true));
        assertEquals(1, authorsWithBooksInStock.size());
        assertEquals(author.getName(), authorsWithBooksInStock.get(0).getName());
    }

    @Test
    public void findAuthorByNameExampleMatcherTest() {
        final Author author = new Author();
        author.setName("author with books in stock");
        final Book bookInStock = new Book(author, true, "book in stock");
        final Book bookNotStock = new Book(author, false, "book not stock");
        author.setBooks(Arrays.asList(bookInStock, bookNotStock));
        final Author authorWithoutBooks = new Author();
        authorWithoutBooks.setName("author without books in stock");
        authorRepository.saveAll(Arrays.asList(author, authorWithoutBooks));

        final Author expectedAuthor = new Author();
        expectedAuthor.setName("author without books in stock");

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("books", "id");

        final Example<Author> example = Example.of(expectedAuthor, matcher);
        final Iterable authors = authorRepository.findAll(example);

        assertEquals(1, ((Collection<?>) authors).size());
        assertThat(authorRepository.count(example), is(1L));
        assertThat(((Author) authors.iterator().next()).getName(), CoreMatchers.equalTo(expectedAuthor.getName()));
    }
}
