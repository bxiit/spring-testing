package kz.spring.testing.springtesting.mappers;

import kz.spring.testing.springtesting.dto.PersonDto;
import kz.spring.testing.springtesting.model.Person;

public final class PersonMapper {

    public static PersonDto mapToPersonDto(Person person) {
        return PersonDto.builder()
                .id(person.getId())
                .name(person.getName())
                .birthDate(person.getBirthDate())
                .build();
    }

    public static Person mapToPerson(PersonDto personDto) {
        return Person.builder()
                .id(personDto.getId())
                .name(personDto.getName())
                .birthDate(personDto.getBirthDate())
                .build();
    }
}
