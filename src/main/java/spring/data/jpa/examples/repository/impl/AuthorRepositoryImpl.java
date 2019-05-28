package spring.data.jpa.examples.repository.impl;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import spring.data.jpa.examples.model.Author;
import spring.data.jpa.examples.repository.AuthorRepository;
import spring.data.jpa.examples.repository.AuthorRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuthorRepository commentRepository;

    @Override
    public Iterable<Author> findAuthorsWithBooksInStock(boolean inStock) {
        Filter filter = entityManager.unwrap(Session.class).enableFilter("bookFilter");
        filter.setParameter("inStock", inStock);
        Iterable<Author> iterable = commentRepository.findByBooksInStock(inStock);
        entityManager.unwrap(Session.class).disableFilter("bookFilter");
        return iterable;
    }
    
}
