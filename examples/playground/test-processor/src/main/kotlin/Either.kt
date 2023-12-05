sealed class Either<out A, out B>
class L<A>(val value: A) : Either<A, Nothing>()
class R<B>(val value: B) : Either<Nothing, B>()

fun main() {
    val x = if (true) {
        L(0)
    } else {
        R("")
    }
    use(x)
    use(L(0))
}

fun use(x: Either<Int, String>) = when (x) {
    is L -> println("It's a number: ${x.value}")
    is R -> println("It's a string: ${x.value}")
}
