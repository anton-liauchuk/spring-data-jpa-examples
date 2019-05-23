package spring.data.jpa.examples.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Author {

    @OneToMany
    private List<Book> books;

}
