package spring.data.jpa.examples.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Book {

    @ManyToOne
    private Author author;
    private boolean inStock;

}
