sealed class Comparable <out T: Any?> (val value: T) {
    //abstract fun apply() : (Any?) -> Pair<String, Any?>?
}

class GreaterThan <T: Any> (value: T): Comparable<T>(value)

class LessThan <T: Any> (value: T): Comparable<T>(value)

class EqualTo <T: Any?> (value: T): Comparable<T>(value)

/*class QueryableInt(value: Int) : QueryableValue<Int>(value) {
    override fun apply(): (Any?) -> Pair<String, Any?>? {
        TODO("Not yet implemented")
    }

    override fun greaterThan(compareValue: Int): (Ref) -> Ref {
        TODO("Not yet implemented")
    }
}

class test{
    fun test() {
        val a = GreaterThan(5)
        val b = a.apply()
        Unused(N, 5)
    }
}*/

class Ref
