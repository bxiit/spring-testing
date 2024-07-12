package kz.spring.testing.springtesting.repository;

import kz.spring.testing.springtesting.model.Person;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    private Person person;

    @BeforeEach
    void setUpEntity() {
        person = Person.builder()
                .id(UUID.randomUUID())
                .name("Atabek")
                .birthDate(LocalDate.of(2004, Month.NOVEMBER, 16))
                .build();
    }


    @AfterEach
    void cleanAfterEach() {
        personRepository.deleteAll();
    }

    @Test
    void shouldReturnPerson_whenPersonExists() {
        Person savedPerson = personRepository.save(person);

        Optional<Person> personOptional = personRepository.findById(savedPerson.getId());
        assertThat(personOptional)
                .isPresent();
    }

    @Test
    void shouldReturnEmptyOptional_whenPersonDoesNotExists() {
        Optional<Person> personOptional = personRepository.findById(UUID.randomUUID());
        assertThat(personOptional)
                .isEmpty();
    }

    @Test
    void shouldReturnAllThreePerson() {
        personRepository.save(person);
        Person bxiit2 = Person.builder()
                .id(UUID.randomUUID())
                .name("bxiit2")
                .birthDate(LocalDate.of(2004, Month.NOVEMBER, 16))
                .build();
        personRepository.save(bxiit2);
        Person bxiit3 = Person.builder()
                .id(UUID.randomUUID())
                .name("bxiit3")
                .birthDate(LocalDate.of(2004, Month.NOVEMBER, 16))
                .build();
        personRepository.save(bxiit3);

        List<Person> persons = personRepository.findAll();

        assertThat(persons)
                .isNotNull()
                .hasSize(3);
    }
}
