package kz.spring.testing.springtesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.spring.testing.springtesting.dto.PersonDto;
import kz.spring.testing.springtesting.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;

    @AfterEach
    void cleanAfterEach() {
        personRepository.deleteAll();
    }

    @Test
    void shouldSaveSuccessfully() throws Exception {
        PersonDto bexeiit = PersonDto.builder()
                .name("Bexeiit")
                .birthDate(LocalDate.of(2004, Month.NOVEMBER, 16))
                .build();

        ResultActions result = mockMvc.perform(
                post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bexeiit))
        );

        result
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        PersonDto personDtoFromResponse = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                PersonDto.class
        );

        assertThat(personDtoFromResponse)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response)
                            .hasFieldOrPropertyWithValue("name", "Bexeiit")
                            .hasFieldOrPropertyWithValue(
                                    "birthDate",
                                    LocalDate.of(2004, Month.NOVEMBER, 16)
                            );
                });
    }

    @Test
    void shouldNotSave_inValidBirthDate() throws Exception {
        PersonDto bexeiit = PersonDto.builder()
                .name("Bexeiit")
                .birthDate(LocalDate.of(2554, Month.NOVEMBER, 16))
                .build();

        ResultActions result = mockMvc.perform(
                post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bexeiit))
        );

        result
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void shouldReturnPersonById() throws Exception {
        PersonDto bexeiit = PersonDto.builder()
                .name("Bexeiit")
                .birthDate(LocalDate.of(2004, Month.NOVEMBER, 16))
                .build();

        ResultActions result = mockMvc.perform(
                post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bexeiit))
        );

        UUID savedUserId = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                PersonDto.class
        ).getId();

        mockMvc.perform(get("/persons/" + savedUserId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        PersonDto personDtoFromResponse = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                PersonDto.class
        );

        assertThat(personDtoFromResponse)
                .isNotNull()
                .satisfies(response -> {
                    assertThat(response)
                            .hasFieldOrPropertyWithValue("name", "Bexeiit")
                            .hasFieldOrPropertyWithValue(
                                    "birthDate",
                                    LocalDate.of(2004, Month.NOVEMBER, 16)
                            );
                });
    }


    @Test
    void shouldReturnAllThreePerson() throws Exception {
        PersonDto bexeiit = PersonDto.builder()
                .name("Bexeiit")
                .birthDate(LocalDate.of(2004, Month.NOVEMBER, 16))
                .build();

        mockMvc.perform(
                post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bexeiit))
        );
        PersonDto bexeiit2 = PersonDto.builder()
                .name("Bexeiit")
                .birthDate(LocalDate.of(2004, Month.NOVEMBER, 16))
                .build();

        mockMvc.perform(
                post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bexeiit2))
        );
        PersonDto bexeiit3 = PersonDto.builder()
                .name("Bexeiit")
                .birthDate(LocalDate.of(2004, Month.NOVEMBER, 16))
                .build();

        mockMvc.perform(
                post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bexeiit3))
        );

        ResultActions result = mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        List<PersonDto> personDtoFromResponse = objectMapper.readValue(
                result.andReturn().getResponse().getContentAsString(),
                List.class
        );

        assertThat(personDtoFromResponse)
                .isNotNull()
                .hasSize(3);
    }
}
