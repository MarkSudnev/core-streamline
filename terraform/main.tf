terraform {
  required_providers {
    kafka = {
      source  = "Mongey/kafka"
      version = "0.6.0"
    }
  }
}

locals {
  partitions         = 3
  replication_factor = 1
  topic_config = {
    "cleanup.policy" = "delete"
    "retention.ms"   = "604800000" # 7 days
  }
}

provider "kafka" {
  bootstrap_servers = ["kafka:9092"]
  tls_enabled       = false
  skip_tls_verify   = true
}

resource "kafka_topic" "first" {
  name               = "first"
  partitions         = local.partitions
  replication_factor = local.replication_factor

  config             = local.topic_config
}

resource "kafka_topic" "second" {
  name               = "second"
  partitions         = local.partitions
  replication_factor = local.replication_factor

  config             = local.topic_config
}

resource "kafka_topic" "third" {
  name               = "third"
  partitions         = local.partitions
  replication_factor = local.replication_factor

  config             = local.topic_config
}