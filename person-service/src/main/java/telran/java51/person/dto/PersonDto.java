package telran.java51.person.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import lombok.Getter;

/*
 * Теперь мы хотим объяснить jackson, что нужно также улавливать тип наших Person-ов и их считывать
 * 
 * То есть мы говорим ему, считывай имя класса и добавляй его как свойство (property) класса type
 */

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,include = As.PROPERTY, property = "type")

/*
 * Теперь мы добавляем пассив параметров, которые jackson должен считывать из запроса Postman и на основе этого выбирать классы
 */
@JsonSubTypes({
	
	@Type(value=ChildDto.class, name="child"),
	@Type(value=EmployeeDto.class,name="employee"),
	@Type(value=PersonDto.class,name="person")
			
})

public class PersonDto {

	Integer id;
	String name;
	LocalDate birthDate;
	AddressDto address;
}
