package telran.java51.person.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;

@Getter
public class EmployeeDto extends PersonDto {
	
	String company;
	int salary;
	

}
