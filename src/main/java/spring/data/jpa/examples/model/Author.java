package spring.data.jpa.examples.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@FilterDef(name = "bookFilter", parameters = @ParamDef(name = "inStock", type = "boolean"))
@Getter
@Setter
@ToString
@Entity
public class Author extends BaseEntity {

    private String name;

    @Filter(name = "bookFilter", condition = "in_stock = :inStock")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> books;

}
