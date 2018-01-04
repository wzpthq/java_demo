package study.wzp.data.kafka.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducerTest {

    /**
     * Producer 发送消息到broker，只需要明确broker-list和topic即可
     *
     * key/value: 当存在多个分区时，如何将数据写入到不同的分区，是通过key来hash的；
     * @param args
     */

    public static void main(String[] args) throws InterruptedException {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", "localhost:9092");
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(prop);

        for (int i = 0; i < 1000; i ++) {
            Thread.sleep(1000);
            producer.send(new ProducerRecord<String, String>("test-topic", "v" + i ));
        }

    }

}
