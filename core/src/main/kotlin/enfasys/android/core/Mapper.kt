package enfasys.android.core

interface Mapper<in In, out Out> {
    fun map(origin: In): Out
    fun map(origin: List<In>): List<Out> = origin.map { map(it) }
}
