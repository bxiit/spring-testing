package kz.spring.testing.springtesting.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
public class PersonDto {

    private UUID id;

    @NotNull(message = "Invalid name")
    private String name;

    @PastOrPresent(message = "Invalid birth date")
    private LocalDate birthDate;
}
