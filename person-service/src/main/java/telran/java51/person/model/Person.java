package telran.java51.person.model;

import java.io.Serializable;

/*
 * 3.Пишем класс для Entity для нашей базы данных
 */
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id") 
@Entity
@Table(name="People") 

//Добавляем эту аннотацию, чтобы выбрать стартегию мапинга наследования

@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Будет одна таблица для всех наследников
//@Inheritance(strategy = InheritanceType.JOINED)//Будут созданы таблицы под все классы, одна главная с общими полями и id, остальные таблицы - с id и уникальными полями для каждого класса 
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)//Будет создано по таблице под каждый класс. В каждой таблице - все данные этого класса (и свои и унаследованные)
public class Person implements Serializable{

	private static final long serialVersionUID = -7204058271536940439L;
	@Id 
	Integer id;
	@Setter
	String name;
	LocalDate birthDate;
	@Setter
	@Embedded
	Address address;
}
