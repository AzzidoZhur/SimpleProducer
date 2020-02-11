package de.ing.simpleProducer.deserializer;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.ing.simpleProducer.tiere.Schwein;

public class KafkaJsonDeserializer implements Deserializer<Schwein> {

	private Class<?> type;

	public KafkaJsonDeserializer() {
	}

	public KafkaJsonDeserializer(Class<?> type) {
		this.type = type;
	}

	@Override
	public void configure(Map map, boolean b) {

	}

	@Override
	public Schwein deserialize(String s, byte[] bytes) {
		ObjectMapper mapper = new ObjectMapper();
		Schwein obj = null;
		try {
			obj = mapper.readValue(bytes, Schwein.class);
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		return (Schwein) obj;
	}

	@Override
	public void close() {

	}
}
