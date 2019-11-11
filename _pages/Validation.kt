sealed class Validation {
    abstract infix operator fun plus(other: Validation): Validation
    abstract fun <T> getOrElse(t: T, or: (List<String>) -> T): T
}
object Valid: Validation() {
    override fun plus(other: Validation): Validation {
        is Invalid -> other
        is Valid -> this
    }

    override fun <T> getOrElse(t: T, or: (List<String>) -> T): T = t
}
class Invalid(var errors: List<String>): Validation() {
    companion object {
        operator fun invoke(error: String) = Invalid(listOf(error))
    }

    override fun plus(other: Validation): Validation {
        is Invalid -> Invalid(this.errors + other.errors)
        is Valid -> this
    }

    override fun <T> getOrElse(t: T, or: (List<String>) -> T): T = or(errors)
}


