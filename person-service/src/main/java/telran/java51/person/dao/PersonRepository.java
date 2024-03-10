package telran.java51.person.dao;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.model.Child;
import telran.java51.person.model.Employee;
import telran.java51.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

	Stream <Person> findAllByNameIgnoreCase(String name);
	
	Stream <Person> findAllByAddressCityIgnoreCase(String city);
	
	Stream <Person> findAllByBirthDateBetween(LocalDate dateFrom, LocalDate dateTo);
	
	@Query("select new telran.java51.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	List <CityPopulationDto> getCitiesPopulation();
	
	@Query("SELECT c FROM Child c")
	Stream <Child> findAllChildren();
	
	
	@Query("SELECT e FROM Employee e where e.salary between :min and :max")
	Stream <Employee> findEmployeesBySalaryBetween(int min, int max);
	
}
