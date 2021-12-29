package models

enum class MessageType {
    Error, Warning, Info
}

data class LogMessage(
    val message: String,
    val type: MessageType = MessageType.Info,
    val timestamp: Long = System.currentTimeMillis(),
)
