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
}

resource "kafka_topic" "first-topic" {
  name               = "first"
  partitions         = local.partitions
  replication_factor = local.replication_factor

  config             = local.topic_config
}

resource "kafka_topic" "second-topic" {
  name               = "second"
  partitions         = local.partitions
  replication_factor = local.replication_factor

  config             = local.topic_config
}

resource "kafka_topic" "third-topic" {
  name               = "third"
  partitions         = local.partitions
  replication_factor = local.replication_factor

  config             = local.topic_config
}