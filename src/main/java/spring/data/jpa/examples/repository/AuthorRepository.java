package spring.data.jpa.examples.repository;

import org.springframework.data.repository.CrudRepository;
import spring.data.jpa.examples.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
