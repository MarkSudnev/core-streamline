package pl.sudneu.pipelineunits.sigma

import org.apache.kafka.clients.producer.KafkaProducer
import org.http4k.config.Environment
import org.http4k.server.Undertow
import org.http4k.server.asServer
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_BOOTSTRAP_SERVERS
import pl.sudneu.pipelineunits.sigma.SigmaConfig.KAFKA_TOPIC
import pl.sudneu.pipelineunits.sigma.SigmaConfig.PORT
import java.util.*

fun main(args: Array<String>) {

  val environment = Environment.ENV
  val props = Properties().also {
    it["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
    it["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
    it["bootstrap.servers"] = environment[KAFKA_BOOTSTRAP_SERVERS]
  }
  val kafkaProducer = KafkaProducer<String, String>(props)
  val handler = KafkaMessageHandler(kafkaProducer, environment[KAFKA_TOPIC])

  val api = SigmaApi(handler)
  val server = api.asServer(Undertow(environment[PORT].value)).start()

  Runtime.getRuntime().addShutdownHook(Thread {
    server.stop()
    kafkaProducer.flush()
    kafkaProducer.close()
  })
}
