package telran.java51.person.service;

import java.time.LocalDate;



import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import lombok.RequiredArgsConstructor;
import telran.java51.person.dao.PersonRepository;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.ChildDto;
import telran.java51.person.dto.EmployeeDto;
import telran.java51.person.dto.exception.PersonNotFoundException;
import telran.java51.person.model.Child;
import telran.java51.person.model.Employee;
import telran.java51.person.model.Person;

@Service
@RequiredArgsConstructor

/*
 * 3. Имплементируем CommandLineRunner, чтобы внизу этого класса написать несколько человек, которые предзаполнятся в нашу базу
 */
public class PersonServiceImpl implements PersonService, CommandLineRunner {
	
	final PersonRepository personRepository;
	final ModelMapper modelMapper;
	

	@Transactional //Добавляем, чтобы сделать транзакционность, то есть чтобы два пользователя одновременно не добавили у нас одно и то же ы
	@Override
	public Boolean addPerson(PersonDto personDto) {
		
		if(personRepository.existsById(personDto.getId())) {
			
			return false;
		} 
		
	if(personDto instanceof ChildDto) {
		personRepository.save(modelMapper.map(personDto, Child.class));
		return true;
	}
	
	if(personDto instanceof EmployeeDto) {
		personRepository.save(modelMapper.map(personDto, Employee.class));
		return true;
	}
	
		personRepository.save(modelMapper.map(personDto, Person.class));
			return true;
	
	}
	
	@Transactional
	@Override
	public PersonDto deleteById(Integer id) {

		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}

	
	@Transactional
	@Override
	public PersonDto updatePersonName(Integer id, String newName) {
	
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

		if(newName!=null) {
			 person.setName(newName);
			// personRepository.save(person); //строчка не нужна из-за 	@Transactional
		}
		
		return modelMapper.map(person, PersonDto.class);
	}
	
	@Transactional
	@Override
	public PersonDto updatePersonAdress(Integer id, AddressDto addressDto) {
	
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

		if(addressDto!=null) {
			
			person.setAddress(modelMapper.map(addressDto, telran.java51.person.model.Address.class));
			//personRepository.save(person);//строчка не нужна из-за 	@Transactional
			
		}
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto findById(Integer id) {
		
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		
		/*
		 * Мы добаляем тут проверку ниже, и теперь у нас в postman уже будет возвращаться нужные данные
		 */
		
		if(person instanceof Child) {
			return modelMapper.map(person, ChildDto.class);
		}
		
		if(person instanceof Employee) {
			return modelMapper.map(person, EmployeeDto.class);
		}
		
			return modelMapper.map(person, PersonDto.class);
		
	}

	
	@Transactional(readOnly=true)
	@Override
	public Iterable<PersonDto> findAllByCity(String city) {
		
		return personRepository.findAllByAddressCityIgnoreCase(city).map(x->modelMapper.map(x, PersonDto.class)).toList();
	}

	@Transactional(readOnly=true)
	@Override
	public Iterable<PersonDto> findAllByAgeBetweenAgeFromAndAgeTo(Integer ageFrom, Integer ageTo) {
	
		return personRepository.findAllByBirthDateBetween(LocalDate.now().minusYears(ageFrom),LocalDate.now().minusYears(ageTo)).map(x->modelMapper.map(x, PersonDto.class)).toList();

	}


	@Transactional(readOnly=true)
	@Override
	public Iterable<PersonDto> findAllByName(String name) {
	
		return  personRepository.findAllByNameIgnoreCase(name).map(x->modelMapper.map(x, PersonDto.class)).toList();
	}


@Override
public Iterable<CityPopulationDto> getCitiesPopulation() {

	return personRepository.getCitiesPopulation();

}

/*
 * 4. Пишем предзаполнение для нашей базы, чтобы в нее сразу кто-то заполнялся
 */

@Transactional //Добавляем аннотацию, так как сохраняем данные в базу
@Override
public void run(String... args) throws Exception {
	
	if(personRepository.count()==0) {
		
		
		Person person = new Person(1500, "Mary", LocalDate.of(1963, 2, 6), new telran.java51.person.model.Address("Tel Aviv", "Herzel", 13));
		Child child = new Child(2400, "Ann", LocalDate.of(2020, 4,13), new telran.java51.person.model.Address("Ashkelon", "Smolyansky", 67), "Pelikan");
		Employee employee = new Employee(3500, "Max", LocalDate.of(2000,12,31), new telran.java51.person.model.Address("Holon", "Street", 76), "Motorola", 15000);
		
		personRepository.save(person);
		personRepository.save(child);
		personRepository.save(employee);
	}
}

@Transactional(readOnly=true)
@Override
public Iterable<ChildDto> getChildren() {
	
	return personRepository.findAllChildren().map(x->modelMapper.map(x, ChildDto.class)).toList();
}

@Transactional(readOnly=true)
@Override
public Iterable<EmployeeDto> getEmployeesBySalaryBetween(int min, int max) {

	return personRepository.findEmployeesBySalaryBetween(min,max).map(x->modelMapper.map(x, EmployeeDto.class)).toList();
}

}
