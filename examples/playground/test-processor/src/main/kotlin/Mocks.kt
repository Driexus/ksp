class MockRef : MockQuery()
open class MockQuery {
    fun whereGreaterThan(a: String, b: Any) : MockQuery = this

    fun whereLessThan(a: String, b: Any) : MockQuery = this

    fun whereEqualTo(a: String, b: Any) : MockQuery = this

    fun get(result: (String) -> Unit) = result("")
}
