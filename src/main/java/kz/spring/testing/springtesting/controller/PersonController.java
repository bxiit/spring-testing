package kz.spring.testing.springtesting.controller;

import jakarta.validation.Valid;
import kz.spring.testing.springtesting.dto.PersonDto;
import kz.spring.testing.springtesting.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto addPerson(@Valid @RequestBody PersonDto personDto) {
        return personService.savePerson(personDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto getPerson(@PathVariable("id") UUID uuid) {
        return personService.findPersonById(uuid);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDto> getAllPerson() {
        return personService.findAllPersons();
    }
}
