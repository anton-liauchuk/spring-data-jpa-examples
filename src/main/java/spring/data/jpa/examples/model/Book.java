package spring.data.jpa.examples.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
public class Book extends BaseEntity {

    @ManyToOne
    private Author author;
    private boolean inStock;
    private String name;

}
