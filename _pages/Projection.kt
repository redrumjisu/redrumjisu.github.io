fun <T> Projection<T>.getOrElse(or: () -> T): T = when(this) {
    is EmptyProjection -> or()
    is ValueProjection -> this.value
}

sealed class Projection<out T> {
    abstract fun <U> map(fn: (T) -> U): Projection<U>
    abstract fun exists(fn: (T) -> Boolean): Boolean
    abstract fun filter(fn: (T) -> Boolean): Projection<T>
    abstract fun toList(): List<T>
    abstract fun orNull: T?
}

class EmptyProjection<out T>: Projection<T>() {
    override fun <U> map(fn: (T) -> U): Projection<U> = EmptyProjection<U>
    override fun exists(fn: (T) -> Boolean): Boolean = false
    override fun filter(fn: (T) -> Boolean): Projection<T> = this
    override fun toList(): List<T> = emptyList()
    override fun orNull(): T? = null
}

class ValueProjection<out T>(val value: T): Projection<T> {
    override fun <U> map(fn: (T) -> U): Projection<U> = ValueProjection(fn(value))
    override fun exists(fn: (T) -> Boolean): Boolean = fn(value)
    override fun filter(fn: (T) -> Boolean): Projection<T> = when(this) {
        true -> this
        false -> EmptyProjection()
    }
    override fun toList(): List<T> = listOf(value)
    override fun orNull(): T? = value
}