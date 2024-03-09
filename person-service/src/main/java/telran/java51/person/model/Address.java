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

//@Embeddable

/*
 * В некоторых тьюториалах пишут, что надо ставить и там и там 
 * 
 * Этой аннотацией мы намекаем Spring, что это встроенный класс. 
 * В целом можно использовать @Embedded над полем класса Person, или @Embeddable над самим классом Address
 *
 * Есть небольшая разница. Если мы хотим, чтобы класс Address был встроенным в несколько классов, то имеет смысл использовать @Embeddable
 * Или если мы хотим использовать составной первичный ключ из нескольких полей. Тогда одно из решений - объединить эти поля и вынести в отдельный класс, и сделать его @Embeddable
 * 
 * Если же где-то у меня этот класс встроенный, а где-то нет, то лучше использовать @Embedded
 */
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
