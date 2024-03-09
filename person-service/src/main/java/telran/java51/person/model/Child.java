package telran.java51.person.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 1.Мы разбираем тему наследования и поэтому создаем Child - класс-наследник от Person
 */
@NoArgsConstructor
@Getter
@Setter
@Entity //Несмотря на наследование, добавляем аннотацию Entity
public class Child extends Person {
	
	private static final long serialVersionUID = -8142521765096134280L;
	
	String kindergarten;
	
	/*
	 * Пишем этот конструктор, потому что аннотация @AllArgsConstructor даст нам конструктор с одним аргументом - детским садом
	 */

	public Child(Integer id, String name, LocalDate birthDate, Address address, String kindergarten) {
		super(id, name, birthDate, address);
		this.kindergarten=kindergarten;
		
	}
	
	
	

}
