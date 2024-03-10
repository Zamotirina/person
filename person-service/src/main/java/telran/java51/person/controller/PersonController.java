package telran.java51.person.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.ChildDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.EmployeeDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.service.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor

public class PersonController{
	
	final PersonService personService;
	


	@PostMapping
	public Boolean addPerson(@RequestBody PersonDto personDto) {

		return personService.addPerson(personDto);
	}

	@GetMapping("/{id}")
	public PersonDto findById(@PathVariable Integer id) {
	
		return personService.findById(id);
	}
	
	@GetMapping("/city/{city}")
	public Iterable <PersonDto> findAllByCity(@PathVariable String city) {
	
		return personService.findAllByCity(city);
	}
	
	@GetMapping("/ages/{ageTo}/{ageFrom}")
	public Iterable <PersonDto> findAllByAgeBetweenAgeFromAndAgeTo(@PathVariable Integer ageTo, @PathVariable Integer ageFrom) {
	
		return personService.findAllByAgeBetweenAgeFromAndAgeTo(ageFrom, ageTo);
	}
	
	@PutMapping("/{id}/name/{newName}")
	public PersonDto updatePersonName(@PathVariable Integer id, @PathVariable String newName) {
	
		return personService.updatePersonName(id, newName);
	}
	
	@GetMapping("/name/{name}")
	public Iterable <PersonDto> findAllByName(@PathVariable String name) {
	
		return personService.findAllByName(name);
	}
	
	@PutMapping("/{id}/address")
	public PersonDto updatePersonAdress(@PathVariable Integer id, @RequestBody AddressDto addressDto) {
	
		return personService.updatePersonAdress(id, addressDto);
	}
	
	
	
	@DeleteMapping("/{id}")
	public PersonDto updatePersonAdress(@PathVariable Integer id) {
	
		return personService.deleteById(id);
	}
	
	@GetMapping("/population/city")
	public Iterable <CityPopulationDto> getCitiesPopulation() {
	
		return personService.getCitiesPopulation();
	}
	
	@GetMapping("/children")
	public Iterable <ChildDto> getChildren() {
	
		return personService.getChildren();
	}
	
	@GetMapping("/salary/{min}/{max}")
	public Iterable <EmployeeDto> getEmployeesBySalaryBetween(@PathVariable int min, @PathVariable int max) {
	
		return personService.getEmployeesBySalaryBetween(min,max);
	}
	
}
