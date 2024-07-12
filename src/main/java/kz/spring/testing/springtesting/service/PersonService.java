package kz.spring.testing.springtesting.service;

import kz.spring.testing.springtesting.dto.PersonDto;
import kz.spring.testing.springtesting.exception.NotFoundException;
import kz.spring.testing.springtesting.mappers.PersonMapper;
import kz.spring.testing.springtesting.model.Person;
import kz.spring.testing.springtesting.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public PersonDto savePerson(PersonDto personDto) {
        Person person = PersonMapper.mapToPerson(personDto);
        personRepository.save(person);
        return PersonMapper.mapToPersonDto(person);
    }

    public PersonDto findPersonById(UUID personId) {
        return personRepository.findById(personId)
                .map(PersonMapper::mapToPersonDto)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public List<PersonDto> findAllPersons() {
        return personRepository.findAll().stream()
                .map(PersonMapper::mapToPersonDto)
                .toList();
    }
}
