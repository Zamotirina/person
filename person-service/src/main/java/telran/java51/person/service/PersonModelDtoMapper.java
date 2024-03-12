package telran.java51.person.service;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.exception.UnknownPersonTypeException;
import telran.java51.person.model.Person;

@Configuration //Эту же аннотацию мы добавляли в нашем изначальном маппере в классе ServiceConfiguration
@RequiredArgsConstructor

public class PersonModelDtoMapper {
	
	private static final String MODEL_PACKAGE = "telran.java51.person.model.";
	private static final String DTO_SUFFIX = "Dto";
	private static final String DTO_PACKAGE = "telran.java51.person.dto.";
	
	final ModelMapper modelMapper;

	public Person mapToModel(PersonDto personDto) {
		return modelMapper.map(personDto, getModelClass(personDto));
	}

	public PersonDto mapToDto(Person person) {
		return modelMapper.map(person, getDtoClass(person));
	}


	

	private Class<? extends Person> getModelClass(PersonDto personDto) {
		
		String modelClassName = personDto.getClass().getSimpleName();
		modelClassName = modelClassName.substring(0, modelClassName.length() - 3);
		
		try {
			
			@SuppressWarnings("unchecked")
			Class<? extends Person> clazz = (Class<? extends Person>) Class.forName(MODEL_PACKAGE + modelClassName);
			return clazz;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new UnknownPersonTypeException();
		}
	}

	@SuppressWarnings("unchecked")
	private Class<? extends PersonDto> getDtoClass(Person person) {
		
		String dtoClassName = person.getClass().getSimpleName();
		dtoClassName = dtoClassName + DTO_SUFFIX;
		
		try {
			
			Class<? extends PersonDto> clazz = (Class<? extends PersonDto>) Class.forName(DTO_PACKAGE + dtoClassName);
			return clazz;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new UnknownPersonTypeException();
		}
	}

}