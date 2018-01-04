package study.wzp.data.kafka.demo;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

public class KafkaConsumerTest {

    public static void main(String[] args) {

        Properties prop = new Properties();
        prop.put("bootstrap.servers", "localhost:9092");
        prop.put("group.id", "test");
        prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<String, String>(prop);
        consumer.subscribe(Arrays.asList("test-topic"));
        while(true) {
            ConsumerRecords<String, String> record = consumer.poll(1000);
            Iterator<ConsumerRecord<String, String>> rs = record.records("test-topic").iterator();
            while(rs.hasNext()) {
                System.out.println(rs.next());
            }
        }


    }

}
