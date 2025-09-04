package pl.sudneu.pipelineunits.omega

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.http4k.config.Environment
import pl.sudneu.pipelineunits.omega.OmegaConfig.KAFKA_TOPIC
import pl.sudneu.pipelineunits.omega.OmegaConfig.OUTPUT_FILE

fun main(args: Array<String>) {
  val storage = mutableListOf<String>()
  val env = Environment.ENV
  val kafkaProperties = env.toConsumerProperties()
  val kafkaConsumer = KafkaConsumer(
    kafkaProperties,
    StringDeserializer(),
    StringDeserializer()
  )
  val processor = OmegaProcessor.collectAsHtmlReport(env[OUTPUT_FILE], storage)
  val handler = OmegaMessageHandler(kafkaConsumer, processor)
  handler.listen(env[KAFKA_TOPIC])
}
