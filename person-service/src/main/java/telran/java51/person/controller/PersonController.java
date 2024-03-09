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
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.service.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor

public class PersonController{
	
	final PersonService personService;
	
/*
 * Эдуард также рассказал, что аннотацию @Transactionsl иногда ставят тут в контроллере, а не в ServiceImpl
 * 
 * В целом это рабатает. 
 * 
 * Но в эксклюзивных случаях, когда при микросерверной архитектуре приложение разделенно между серверами, и контроллер - на одном, а сервис - на другом. Тогда аннотации в контроллере не сработают, надо ставить их в каждом классе отдельно
 */

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
	public Iterable <PersonDto> findAllBetweenAge(@PathVariable Integer ageTo, @PathVariable Integer ageFrom) {
	
		return personService.findAllBetweenAge(ageFrom, ageTo);
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
	
	/*
	 * Этот метод у нас должен вернуть все города, которые у нас есть в базе и посчитать число Person, которые в этих городах "живут"
	 * То есть вернуть что-то формата: Ашкелон:2, Лод:3, Беер-Шева:1 
	 * 
	 * Для этого пишем новое Dto CityPopulationDto
	 */
	@GetMapping("/population/city")
	public Iterable <CityPopulationDto> getCitiesPopulation() {
	
		return personService.getCitiesPopulation();
	}

}
