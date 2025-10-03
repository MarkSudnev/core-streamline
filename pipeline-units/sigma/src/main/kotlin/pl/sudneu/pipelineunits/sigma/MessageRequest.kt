package pl.sudneu.pipelineunits.sigma

data class MessageRequest(val message: String) {

  init {
      require(message.isNotBlank())
  }
}
