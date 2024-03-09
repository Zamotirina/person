package telran.java51.person.model;

import java.io.Serializable;

/*
 * 3.Пишем класс для Entity для нашей базы данных
 */
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id") //Пишем, чтобы в Java их сравнивать и добавлять в сеты, где их уникальность будет сравниваться как раз так
@Getter
@Entity//Первое минимальное условие
@Table(name="People") //Название таблицы в SQL

/*
 * Чтобы эту сущность система отмапила у нас для базы данных, надо выполнить два минимальных условия:
 * 1) Добавить аннотацию Entity
 * 2) Добавить аннотацию Id к полю, которое мы считаем Id
 */

/*
 * Но при этом если мы просто запустим приложение, оно не сработает, потому что система не сможет отмэппить наш Adress, это не стандартный Java-объект как LocalDate или Integer, поэтому система не понимает, что с ним делать
 * 
 * Нам надо как-то дать ей указание, что с этим адресом делать
 * 
 * Выходов есть несколько, начинаем, как всегда, с вариантов похуже:
 * 
 * 1) Сделать и класс Person, и класс Adress Serializable, то есть превратить их в поток байт. То есть мы будем хранить поле Adress класса Person как поток байт, и система сможет его сожрать. То есть Spring и Hibernate будет перегонять объект в поток байт и обратно 
 * То есть выглядеть это будет как строка, но по факту там будет храниться набор байт. Соответственно если мы подключимся к этой базе данных другим приложением, например, написанным на питоне, то питон этот поток байт уже не сожрет, он их увидит, но работать с ними не сможет
 * Но при этом лучше все равно всегда делать объекты Serializable, потому что если наше приложение разделено на несколько частей и работает на нескольких серверах, то данные могут отправлять с одного сервера другому, это будет обмен Java c Java, а он делается через Serializable
 * 
 * 2) Добавить к встроенному классу аннотацию @Embedded
 */
public class Person implements Serializable{

	private static final long serialVersionUID = -7204058271536940439L;
	@Id //Второе минимальное условие. При этом Эдуард рекомендует использовать вариант аннотации Jakarta, а не Spring, потому что так будет проще если что слезить со Spring на другую технологию, если понадобится 
	Integer id;
	@Setter
	String name;
	LocalDate birthDate;
	@Setter
	
	/*
	 *  В некоторых тьюториалах пишут, что надо ставить и там и там 
	 *  
	 * Этой аннотацией мы намекаем Spring, что это встроенный класс. 
	 * В целом можно использовать @Embedded тут, или @Embeddable над самим классом Address
	 *
	 * Есть небольшая разница. Если мы хотим, чтобы класс Address был встроенным в несколько классов, то имеет смысл использовать @Embeddable
	 * Или если мы хотим использовать составной первичный ключ из нескольких полей. Тогда одно из решений - объединить эти поля и вынести в отдельный класс, и сделать его @Embeddable
	 * 
	 * Если же где-то у меня этот класс встроенный, а где-то нет, то лучше использовать @Embedded
	 */
	@Embedded
	Address address;
}
