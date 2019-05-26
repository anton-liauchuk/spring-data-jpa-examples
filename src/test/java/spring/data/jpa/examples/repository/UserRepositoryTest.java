package spring.data.jpa.examples.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.data.jpa.examples.model.Author;
import spring.data.jpa.examples.model.Book;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void findAuthorsWithBooksInStock() {
        final Author author = new Author();
        final Book bookInStock = new Book(author, true);
        final Book bookNotStock = new Book(author, false);
        author.setBooks(Arrays.asList(bookInStock, bookNotStock));

        authorRepository.save(author);
    }
}
