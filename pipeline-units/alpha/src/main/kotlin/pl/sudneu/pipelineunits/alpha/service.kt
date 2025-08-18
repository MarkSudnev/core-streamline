package pl.sudneu.pipelineunits.alpha

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Produced
import org.http4k.config.Environment
import pl.sudneu.pipelineunits.config.PipelineUnitsEnvironment.KAFKA_TOPIC_IN
import pl.sudneu.pipelineunits.config.PipelineUnitsEnvironment.KAFKA_TOPIC_OUT

fun main(args: Array<String>) {

}

fun PipelineTopology(env: Environment): Topology =
  StreamsBuilder().apply {
    stream(env[KAFKA_TOPIC_IN], Consumed.with(Serdes.String(), Serdes.String()))
      .to(env[KAFKA_TOPIC_OUT], Produced.with(Serdes.String(), Serdes.String()))
  }.build()
