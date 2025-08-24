package pl.sudneu.pipelineunits.beta

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.processor.api.ProcessorSupplier
import org.http4k.config.Environment
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_TOPIC_IN
import pl.sudneu.pipelineunits.config.PipelineConfig.KAFKA_TOPIC_OUT
import pl.sudneu.pipelineunits.config.toStreamProperties

fun main(args: Array<String>) {
  val environment = Environment.ENV overrides localEnvironment
  val kafkaStream = KafkaStreams(
    BetaTopology(environment),
    environment.toStreamProperties()
  )
  kafkaStream.start()
  Runtime
    .getRuntime()
    .addShutdownHook(Thread { kafkaStream.close() })
}

fun BetaTopology(env: Environment): Topology =
  StreamsBuilder().apply {
    stream(env[KAFKA_TOPIC_IN], Consumed.with(Serdes.String(), Serdes.String()))
      .process(ProcessorSupplier { BetaProcessor() })
      .to(env[KAFKA_TOPIC_OUT], Produced.with(Serdes.String(), Serdes.String()))
  }.build()
