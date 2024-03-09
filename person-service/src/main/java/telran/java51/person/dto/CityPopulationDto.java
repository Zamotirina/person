package telran.java51.person.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * Dto для метода getCitiesPopulation()
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CityPopulationDto {

	String city;
	Long  population;
}
