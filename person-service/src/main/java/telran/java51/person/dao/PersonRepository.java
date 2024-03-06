package telran.java51.person.dao;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java51.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

	/*
	 * Здесь можно экстендить и интерфейс JPARepository, но в целом CrudRepository нам вполне хватает
	 */
	@Query("SELECT p FROM Person p WHERE p.address.city =:city")
	List <Person> findAllPersonsByCity(String city);

	@Query("SELECT p FROM Person p WHERE p.birthDate BETWEEN :dateFrom and :dateTo")
	List <Person> findAllByAgeBetweenDateFromAndDateTo(LocalDate dateFrom, LocalDate dateTo);

	@Query("SELECT p FROM Person p WHERE p.name =:name")
	List <Person> findAllByName(String name);
}
