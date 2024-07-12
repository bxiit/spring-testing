package kz.spring.testing.springtesting.repository;

import kz.spring.testing.springtesting.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
