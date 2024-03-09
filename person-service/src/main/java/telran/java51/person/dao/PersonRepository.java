package telran.java51.person.dao;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

	/*
	 * Здесь в этих методах мы будем возвращать именно стримы.
	 * 
	 * Я в домашке возвращала листы, а потом их превращала в стримы, но это лишнее дейстие
	 * 
	 * Такжу Эдуард показывал вариант, когда стрим создается в методах класса PersonServiceImpl, он сначала возвращает вообще всех persons через findAll() в базе, а потом их фильтрует и мапит
	 * 
	 * Но самый адекватный вариант, лишенный лишних действий, это сразу возвращать стримы из базы.
	 * 
	 * Но поскольку теперь мы работаем не с MongoDb, а с SQL, то в реляционных базах данных есть транзакционность, то есть мы должны согласовывать действия с базой
	 * И теперь нам их надо как-то добавить. 
	 */
	
	
	/*
	 * Переходим к следующей проблеме. Поиск по имени у нас создается автоматически, так как name - это поле Person
	 * 
	 * Но city - это поле не Person, а Address, но к счастью, мы можем продолжить писать последовательно и написать AddressCity, но система все это сожрет
	 * 
	 * Это лучший варинт, как это можно записать этот метод. Это самый универсальный способ, потому что он будет работать с любой базой данных
	 */
/*
 * Важно: я путаю названия методов, поэтому важно прописывать методы с автоподсказками, использовать только названия полей Entity, а также использовать операторы Between и остальные
 */	
	//@Query("select p FROM Person p WHERE p.name =?1") Это аналог написанный на языке JPQL, но лучше всегда, когда возможно использовать именнованные методы, а не @Query, потому что это более универсальный код
	Stream <Person> findAllByNameIgnoreCase(String name);
	
//	@Query("SELECT p FROM Person p WHERE p.address.city =:city") Это аналог на языке JPQL
	Stream <Person> findAllByAddressCityIgnoreCase(String city);
	
	//@Query("SELECT p FROM Person p WHERE p.birthDate BETWEEN :dateFrom and :dateTo")
	Stream <Person> findAllByBirthDateBetween(LocalDate dateFrom, LocalDate dateTo);

	@Query("select new telran.java51.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	List <CityPopulationDto> getCitiesPopulation();
	
}
