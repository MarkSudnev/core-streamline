package pl.sudneu.pipelineunits.sigma

import dev.forkhandles.result4k.asSuccess
import io.kotest.matchers.collections.shouldHaveSize
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.Cluster
import org.apache.kafka.common.serialization.StringSerializer
import org.http4k.client.OkHttp
import org.http4k.config.Port
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.kotest.shouldHaveStatus
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AcceptanceTest {

  private val producer = MockProducer(
    Cluster.empty(),
    true,
    null,
    StringSerializer(),
    StringSerializer()
  )
  private val handler = KafkaMessageHandler(producer, "test-topic")
  private val service = SigmaApi(handler).asServer(SunHttp(Port.RANDOM.value))
  private val client = ClientFilters
    .SetBaseUriFrom(Uri.of("http://localhost:${service.port()}"))
    .then(OkHttp())

  @BeforeEach
  fun setup() {
    service.start()
  }

  @AfterEach
  fun teardown() {
    service.stop()
  }

  @Test
  fun `accept message and send it to kafka topic`() {
    client(Request(POST, "/api/v1/messages").body(exampleBody)) shouldHaveStatus CREATED
    producer.history() shouldHaveSize 1
  }
}

private const val exampleBody = """{"message": "Arcu tristique conubia porttitor praesent dictumst praesent faucibus."}"""
