package telran.java51.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import telran.java51.person.dto.ChildDto;

/*
 * 1. Этот файл мы просто скопировали из предыдущих проектов, чтобы адекватно сработал mapper
 */
@Configuration
public class ServiceConfiguration {

	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
					.setFieldMatchingEnabled(true)
					.setFieldAccessLevel(AccessLevel.PRIVATE)
					.setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}
	


}
