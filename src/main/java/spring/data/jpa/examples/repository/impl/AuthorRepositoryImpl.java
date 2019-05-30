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
        final Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("bookFilter");
        filter.setParameter("inStock", inStock);
        Iterable<Author> iterable = commentRepository.findByBooksInStockCustomQuery(inStock);
        session.disableFilter("bookFilter");
        return iterable;
    }
    
}
