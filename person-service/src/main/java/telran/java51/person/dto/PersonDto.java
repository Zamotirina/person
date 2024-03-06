package telran.java51.person.dto;

import java.time.LocalDate;

import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
public class PersonDto {

	Integer id;
	String name;
	LocalDate birthDate;
	AddressDto address;
}
