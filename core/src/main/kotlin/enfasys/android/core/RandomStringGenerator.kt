package enfasys.android.core

import kotlin.random.Random

class RandomStringGenerator {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun generate(length: Int = DEFAULT_LENGTH): String {
        return (INITIAL_POSITION..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    companion object {
        const val DEFAULT_LENGTH = 8
        const val INITIAL_POSITION = 1
    }
}
