package telran.java51.person.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/*
 * 4. Пишем класс Adress, который у нас будет как бы вложен в класс Person
 */

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor//Нужен для ModelMapper
@Getter
@Setter
@EqualsAndHashCode
//@Data Единый аналог для аннотаций выше, но он не создает NoArgsConstructor, поэтому придется написать так
//@AllArgsConstructor
//@NoArgsConstructor

public class Address implements Serializable{

	private static final long serialVersionUID = -6371657946108821098L;

	String city;
	String street;
	Integer building;
	
}
