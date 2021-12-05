package player

class Player(var shuffle: Boolean, var repeat: Boolean) {
    var playing = false

    fun next() {

    }

    fun previous() {

    }

    fun play() {
        playing = true
    }

    fun pause() {
        playing = false
    }

    fun stop() {
        pause()
    }

    fun toggleShuffle() {
        shuffle = !shuffle
    }

    fun toggleRepeat() {
        repeat = !repeat
    }
}