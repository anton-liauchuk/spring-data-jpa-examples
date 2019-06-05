package spring.data.jpa.examples.model;

import lombok.*;
import org.hibernate.annotations.FilterDef;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntity {

    @ManyToOne
    private Author author;
    private boolean inStock;
    private String name;

}
