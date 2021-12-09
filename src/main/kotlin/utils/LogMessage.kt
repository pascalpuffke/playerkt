package utils

enum class MessageType {
    Error, Warning, Info
}

data class LogMessage(val type: MessageType, val message: String, val timestamp: Long)
