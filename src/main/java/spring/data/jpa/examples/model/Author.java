package spring.data.jpa.examples.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@FilterDef(name = "bookFilter", parameters = @ParamDef(name = "inStock", type = "boolean"), defaultCondition = "in_stock = :inStock")
public class Author extends BaseEntity {

    private String name;

    @Filter(name="bookFilter")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> books;

}
