package de.ing.simpleProducer;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import de.ing.simpleProducer.tiere.Schwein;

public class App {
	public static void main(String[] args) throws Exception{
		// Producer konfigurieren
		String topicName = "JSONDemo";
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.12.4.1:9092");

		// Set acknowledgements for producer requests.
		// acknowledge erst, wenn auf alle Broker reproduziert ist
		props.put("acks", "all");

		// If the request fails, the producer can automatically retry,
		// props.put("retries", 0);

		// Specify buffer size in config
		// props.put("batch.size", 16384);

		// Reduce the no of requests less than 0
//    		      props.put("linger.ms", 1);

		// The buffer.memory controls the total amount of memory available to the
		// producer for buffering.
//    		      props.put("buffer.memory", 33554432);

		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		props.put("value.serializer", "de.ing.simpleProducer.serializer.KafkaJsonSerializer");
		
		Producer<String, Schwein> producer = new KafkaProducer <String, Schwein>(props);
		
		for(int i = 0; i < 10; i++) {
			Schwein schwein = new Schwein("SchweinNummer " + i, i + 10);
			producer.send(new ProducerRecord<String, Schwein>(topicName,schwein.getName(),schwein));
			
		}
	         
	    System.out.println("Messages sent successfully");
	    producer.close();
	   
	}
}
