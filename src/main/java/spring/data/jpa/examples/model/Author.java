package spring.data.jpa.examples.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Author extends BaseEntity {

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> books;

}
