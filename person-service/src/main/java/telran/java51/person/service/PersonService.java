package telran.java51.person.service;

import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson (PersonDto personDto);
	PersonDto findById (Integer id);
	Iterable<PersonDto> findAllByCity(String city);
	Iterable<PersonDto> findAllByAgeBetweenAgeFromAndAgeTo(Integer ageFrom, Integer ageTo);
	PersonDto updatePersonName(Integer id, String newName);
	Iterable<PersonDto> findAllByName(String name);
	PersonDto updatePersonAdress(Integer id, AddressDto addressDto);
	PersonDto deleteById(Integer id);
	Iterable<CityPopulationDto> getCitiesPopulation();
}
