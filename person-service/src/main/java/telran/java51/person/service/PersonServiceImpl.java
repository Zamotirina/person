package telran.java51.person.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.crypto.KeyAgreement;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dao.PersonRepository;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.exception.PersonNotFoundException;

import telran.java51.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
	
	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Override
	public Boolean addPerson(PersonDto personDto) {
		
		if(personRepository.existsById(personDto.getId())) {
			
			return false;
		} 
	
		
		personRepository.save(modelMapper.map(personDto, Person.class));
			return true;
	
	}

	@Override
	public PersonDto findById(Integer id) {
		
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findAllByCity(String city) {
		
		
		return personRepository.findAllPersonsByCity(city).stream().map(x->modelMapper.map(x, PersonDto.class)).toList();
	}

	@Override
	public Iterable<PersonDto> findAllByAgeBetweenAgeFromAndAgeTo(Integer ageFrom, Integer ageTo) {
	
		return personRepository.findAllByAgeBetweenDateFromAndDateTo(LocalDate.now().minusYears(ageFrom),LocalDate.now().minusYears(ageTo)).stream().map(x->modelMapper.map(x, PersonDto.class)).toList();

	}

	@Override
	public PersonDto updatePersonName(Integer id, String newName) {
	
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

		if(newName!=null) {
			 person.setName(newName);
			 personRepository.save(person);
		}
		
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findAllByName(String name) {
	
		
		return  personRepository.findAllByName(name).stream().map(x->modelMapper.map(x, PersonDto.class)).toList();
	}

	@Override
	public PersonDto updatePersonAdress(Integer id, AddressDto addressDto) {
	
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

		if(addressDto!=null) {
			
			person.setAddress(modelMapper.map(addressDto, telran.java51.person.model.Address.class));
			personRepository.save(person);
			
		}
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto deleteById(Integer id) {

		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}

}
