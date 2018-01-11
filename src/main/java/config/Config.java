package config;

import org.springframework.cloud.function.context.FunctionScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;

@FunctionScan
@Configuration
public class Config {

	private final Properties properties;

	public Config(Properties properties) {
		this.properties = properties;
	}

	@Bean
	public AmazonRekognition amazonRekognition() {
		return AmazonRekognitionClientBuilder
			.standard()
			.withRegion(this.properties.getRegion())
			.build();
	}
}
