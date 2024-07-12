package kz.spring.testing.springtesting.service;

import kz.spring.testing.springtesting.dto.PersonDto;
import kz.spring.testing.springtesting.mappers.PersonMapper;
import kz.spring.testing.springtesting.model.Person;
import kz.spring.testing.springtesting.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    private PersonDto personDto;

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUpEntity() {
        personDto = PersonDto.builder()
                .name("Bexeiit")
                .birthDate(LocalDate.of(2004, Month.NOVEMBER, 16))
                .build();
    }

    @AfterEach
    void cleanAfterEach() {
        personRepository.deleteAll();
    }

    @Test
    void savePerson() {
        Person mockedBexeiit = new Person(
                null,
                "Bexeiit",
                LocalDate.of(2004, Month.NOVEMBER, 16)
        );

        doReturn(mockedBexeiit)
                .when(this.personRepository)
                .save(any());

        PersonDto result = this.personService.savePerson(personDto);

        assertThat(result)
                .hasFieldOrPropertyWithValue("name", "Bexeiit")
                .hasFieldOrPropertyWithValue(
                        "birthDate",
                        LocalDate.of(2004, Month.NOVEMBER, 16)
                );

        verify(this.personRepository).save(
                mockedBexeiit
        );
        verifyNoMoreInteractions(this.personRepository);
    }

    @Test
    void findPersonById() {
        UUID id = UUID.randomUUID();
        PersonDto bexeiitovich = PersonDto.builder()
                .id(id)
                .name("Bexeiitovich")
                .birthDate(LocalDate.of(2004, Month.NOVEMBER, 16))
                .build();

        when(personRepository.findById(id)).thenReturn(Optional.of(PersonMapper.mapToPerson(bexeiitovich)));

        Person person = PersonMapper.mapToPerson(personService.findPersonById(id));
        System.out.println(person);

        PersonDto result = personService.findPersonById(id);

        assertThat(bexeiitovich)
                .isEqualTo(result);
    }

    @Test
    void findAllPersons() {
        PersonDto bexeiitovich = PersonDto.builder()
                .id(UUID.randomUUID())
                .name("Bexeiitovich")
                .birthDate(LocalDate.of(2004, Month.NOVEMBER, 16))
                .build();

        PersonDto bexeiitovich2 = PersonDto.builder()
                .id(UUID.randomUUID())
                .name("Bexeiitovich2")
                .birthDate(LocalDate.of(2004, Month.NOVEMBER, 16))
                .build();
        when(this.personRepository.findAll())
                .thenReturn(List.of(PersonMapper.mapToPerson(bexeiitovich), PersonMapper.mapToPerson(bexeiitovich2)));

        List<PersonDto> persons = personService.findAllPersons();

        assertThat(persons)
                .hasSize(2)
                .isEqualTo(List.of(bexeiitovich, bexeiitovich2));
    }
}