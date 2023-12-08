sealed class SprayQueryOperation <S: SprayData, T: Any> (private val property: Property<S, T>, private val value: T?) {
    abstract fun applyOp(query: MockQuery) : MockQuery

    override fun equals(other: Any?): Boolean =
        other is SprayQueryOperation<*, *>
        && value == other.value
        && property == other.property

    private val hashArray = arrayOf(value, property)
    override fun hashCode(): Int = hashArray.contentHashCode()
}

class GreaterThan <S: SprayData, T: Any> (private val property: Property<S, T>, private val value: T): SprayQueryOperation<S, T>(property, value) {
    override fun applyOp(query: MockQuery): MockQuery =
        query.whereGreaterThan(property.toDBString(), value)
}

class LessThan <S: SprayData, T: Any> (private val property: Property<S, T>, private val value: T): SprayQueryOperation<S, T>(property, value) {
    override fun applyOp(query: MockQuery): MockQuery =
        query.whereLessThan(property.toDBString(), value)
}

class EqualTo <S: SprayData, T: Any> (private val property: Property<S, T>, private val value: T?): SprayQueryOperation<S, T>(property, value) {
    override fun applyOp(query: MockQuery): MockQuery =
        query.whereEqualTo(property.toDBString(), value)
}
