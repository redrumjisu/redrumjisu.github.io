sealed class List<out T> {
    // sealed class 에서 when 을 사용할 떄는 모든 경우의 수를 처리해야 함
    fun isEmpty() = when(this) {
        is Empty -> true
        is Node -> false
    }
    fun size(): Int = when(this) {
        is Empty -> 0
        is Node -> 1 + this.next.size()
    }
    fun tail(): List<T> = when(this) {
        is Node -> this.next
        is Empty -> this
    }
    fun head(): List<T> = when(thie) {
        is Node<T> -> this.value
        is Empty -> throw java.lang.RuntimeException("Empty list")
    }
    fun append(t: @UnsafeVariance T): List<T> = when(this) {
        is Node<T> -> Node(this.value, this.next.append(t))
        is Empty -> Node(t, Empty)
    }
    companion object {
        operator fun <T>invoke(vararg values: T): List<T> {
            var temp: List<T> = Empty
            for (value in values) {
                temp = temp.append(value)
            }
            return temp
        }
    }
}

private class Node<out T>(val value: T, val next: List<T>): List<T>()
private object Empty: List<Nothing>()