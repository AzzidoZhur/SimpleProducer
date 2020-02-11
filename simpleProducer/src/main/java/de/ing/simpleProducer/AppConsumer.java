package de.ing.simpleProducer;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import de.ing.simpleProducer.tiere.Schwein;

public class AppConsumer {

	public static void main(String[] args) {

		Properties props = new Properties();
		props.put("bootstrap.servers", "10.12.4.1:9092");
		props.put("group.id", "SchweinConsumer");
		props.put("enable.auto.commit", "false");
		props.put("auto.offset.reset", "earliest");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "de.ing.simpleProducer.deserializer.KafkaJsonDeserializer");

		KafkaConsumer<String, Schwein> consumer = new KafkaConsumer<String, Schwein>(props);

		consumer.subscribe(Collections.singletonList("JSONDemo"));
		
		try {
			while (true) {
				ConsumerRecords<String, Schwein> records = consumer.poll(Duration.ofMillis(100));
				System.out.println(Thread.currentThread().getId());
				//System.out.println("...hallo? " +  records.count());
				for (ConsumerRecord<String, Schwein> record : records) {
					Schwein piggy = record.value();
					System.out.println(piggy);
				}
				consumer.commitSync();
			}
		} finally {
			consumer.close();
		}

	}
}
