package telran.java51.person.service;

import java.time.LocalDate;

import java.time.LocalDateTime;

import javax.crypto.KeyAgreement;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;


import lombok.RequiredArgsConstructor;
import telran.java51.person.dao.PersonRepository;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.exception.PersonNotFoundException;

import telran.java51.person.model.Person;

/*
 * В этом проекте мы работаем не с MongoDb, а с SQL, то в реляционных базах данных есть транзакционность, то есть мы должны согласовывать действия с базой
 * 
 *  То есть мы должны добавить в проект транзакционность.
 *  
 *  Мы по логике смотрим, где она нам нужна. Часто операции, которые что-то меняют в базе данных, должны быть транзакционными
 *  
 *  1) addPerson() НУЖНА: У нас два пользователя могут одновременно проверять, нет ли такого пользвателя, получить true, и попытаться его добавить, но добавится только один пользователь. Соответственно, если нас это не устравает, в этом методе транзакционность нужна
 *  Нам нужно, чтобы проверка существвоания пользователя и егодобавление стали атомарной операцией, которая пока не завершится, никто другой не сможет это сделать
 *  
 *  2) removePerson() - тоже нужна
 *  
 *  3) updatePersonName()  - тоже нужна
 *  
 *  4) updatePersonAdress()  - тоже нужна
 */

/*
 * Spring сильно облегчает работу с транзакционностью и позволяет добавить ее с помощью аннотаций.
 * 
 * @Transactional 
 * 
 * Важно: транзакционность при этом блокирует только те сущности, с которыми в данный момент работает наш метод. То есть person, у которого наш id, а не вообще поиск или обновление/добавление/удаление всех пользоваталей. Но также это зависит от уровня изоляции, то есть можно усилить и ослабить уровни изоляции
 * 
 * Этой аннотации есть два варианта: от jarrarta и от Spring, нам нужна именно от Spring
 * 
 * Аннотация работает так: перед запуском выдается команда start transaction, после заврешения операции система делает commit. Если происходит ошибка, то случается rollback
 * 
 * При этом при каждом запросе к базе данных создается сессия с базой данных через Hibernate, это соединение удерживается, создается persistence context, в котором содержатся все объекты, которые связаны с выполнением этой операции. Когда операция завершается, сессия закрывается.
 * 
 * Соответственно аннотация @Transactional указывает, что пока операции в методе addPerson() не завершены, сессия будет удержана и будет существовать один persistence context. И только в конце будет сделан commit, то есть объекты, которые находились в persistence context будут сохранены в базу данных.
 * 
 * То есть пока метод, над которым тсоит аннотация, полностью не завершится, коммита не будт и другой пользователь не сможет выполнить этот же метод
 * 
 * Метод полностью реализуется за одну сессию в одном контексте
 * 
 *
 */

/*
 * При этом вжано помнить, что транзакционность - дорогое удовольсвтие и абы где ее лепить не надо
 * 
 * То есть в теории можно поставить эту аннотацию над всем классом, но тогда все методы будут транзакционными, что убивает производительность
 * 
 * Например, findById() мы вполне можем оставить как есть, никаких угроз
 * 
 * Также если над методом стоит аннотация, то и методы, которые вызываются в нем становятся на это время транзакционными
 */




@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
	
	final PersonRepository personRepository;
	final ModelMapper modelMapper;
	
	
	@Override
	public PersonDto findById(Integer id) {
		
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}


	@Transactional //Добавляем, чтобы сделать транзакционность, то есть чтобы два пользователя одновременно не добавили у нас одно и то же ы
	@Override
	public Boolean addPerson(PersonDto personDto) {
		
		if(personRepository.existsById(personDto.getId())) {
			
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class)); //Вот тут эта строчка нужна, потому что такого объекта еще нет  в базе. Я НЕ обновляю объект, а добавляю впервые
			return true;
	}
	
	
	@Transactional
	@Override
	public PersonDto deleteById(Integer id) {

		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.delete(person);//Эта строчка тоже нужна, потому что тут я не обновляю объект, а удаляю
		return modelMapper.map(person, PersonDto.class);
	}
		
	@Transactional
	@Override
	public PersonDto updatePersonName(Integer id, String newName) {
	
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if(newName!=null) {
			 person.setName(newName);
	//		 personRepository.save(person);////Суперважно: когда мы добавили аннотацию, мы можем убрать эту строчку. Потому что когда метод завершится и произойдет коммит, все объекты из persistence context (то есть те, что задействовал этот метод) сохранятся (синхронизируются) в базу. Если мы не убираем эту строчку, то проверящий видит, что я не понимаю, как это работает. То есть по этой ошибке отсекают новичков
		}
		
		return modelMapper.map(person, PersonDto.class);
	}
	
	@Transactional
	@Override
	public PersonDto updatePersonAdress(Integer id, AddressDto addressDto) {
	
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

		if(addressDto!=null) {
			person.setAddress(modelMapper.map(addressDto, telran.java51.person.model.Address.class));
		//	personRepository.save(person);////Суперважно: когда мы добавили аннотацию, мы можем убрать эту строчку. Потому что когда метод завершится и произойдет коммит, все объекты из persistence context (то есть те, что задействовал этот метод) сохранятся (синхронизируются) в базу. Если мы не убираем эту строчку, то проверящий видит, что я не понимаю, как это работает. То есть по этой ошибке отсекают новичков
			
		}
		return modelMapper.map(person, PersonDto.class);
	}
	
	/*
	 * Когда в репозитории мы сделали все методы возвращающими стримы, а не листы
	 * три метода ниже у нас перестали работать, так как система требует для них аннотацию @Transactional
	 * 
	 * Проблема в том, что стрим - это не настоящие данные, это некое подобие данных, размещенное на конвейре
	 * В данные стрим превращается, когда в не выполняется термальная операция, например, toList()
	 * 
	 * Соответственно в нашем случае сессия с базой прерывается сразу после findAllPersonsByCity(), а данные мы получаем только на toList()
	 * 
	 * То есть у нас тут тоже есть угроза транзакционности, поэтому система и требует аннотацию
	 * 
	 * Но просто аннотация 	@Transactional над этими методами убивает производительность,
	 * поэтому у нее есть дополнительная настройка (readOnly=true), то есть система блокируется только на чтение
	 * 
	 * По факту это получается readlock. То есть 100500 людей смогут запрашивать поиск одновременно. Но если человек захочет удалить одновременно с ними, то он будет ждать, пока все их запросы не пройдут. Только когда они получат ответ, он сможет удалить пользователя
	 * 
	 */
	
	
	@Transactional(readOnly=true)
	@Override
	public Iterable<PersonDto> findAllByCity(String city) {
		
		return personRepository.findAllByAddressCityIgnoreCase(city).map(x->modelMapper.map(x, PersonDto.class)).toList();
	}

	@Transactional(readOnly=true)
	@Override
	public Iterable<PersonDto> findAllBetweenAge(Integer ageFrom, Integer ageTo) {
	
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

}
