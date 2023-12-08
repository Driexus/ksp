import kotlin.reflect.KClass

class SprayQueryBuilder<S: SprayData>(private val kClass: KClass<S>) {
    private val operations = mutableListOf<SprayQueryOperation<S, *>>()

    fun build() : SprayQuery<S> = SprayQuery(operations, kClass)

    /*fun <T: Any, U: QueryableType<out T>> whereEqualTo(property: Property<S, T, U>, value: T) : SprayQueryBuilder<S> {
        operations.add {
            it.whereEqualTo(propertyToString(property), value)
        }
*//*        baseQuery = {
            baseQuery(it).whereEqualTo(propertyToString(property), value)
        }*//*
        return this
    }*/

    fun <T: Any> whereEqualTo(property: Property<S, T>, value: T) : SprayQueryBuilder<S> {
        operations.add(EqualTo(property, value))
        /*        baseQuery = {
                    baseQuery(it).whereEqualTo(propertyToString(property), value)
                }*/

        return this
    }
}

class SprayQuery<S: SprayData>(operations: List<SprayQueryOperation<*,*>>, private val kClass: KClass<S>) {
    private val baseQuery : QueryTsFun = { it }
    private val tsFun : QueryTsFun = operations.fold(baseQuery) { acc, operation ->
        return@fold {
            val currentQuery = acc(it)
            operation.applyOp(currentQuery)
            /*val currentQuery = acc(it)
            operation.toSprayQuery()
            operation(currentQuery)*/
        }
    }

    fun applyQuery(ref: MockRef) : MockQuery = tsFun(ref)

    private val sortedOps = operations.sortedBy { it.hashCode() }

    override fun hashCode(): Int = sortedOps.hashCode()

    override fun equals(other: Any?): Boolean =
        other is SprayQuery<*>
        && other.kClass == kClass
        && other.sortedOps == sortedOps
}

fun <T> List<T>.deepEquals(other: List<T>) =
    size == other.size && asSequence()
        .mapIndexed { index, element -> element == other[index] }
        .all { it }

typealias QueryTsFun = (MockQuery) -> MockQuery

interface Property<S: SprayData, T: Any> {
    fun toDBString() : String
}

abstract class SprayData

sealed interface QueryableType<Any> {
    data object StringQueryable : QueryableType<String>
    data object IntQueryable : QueryableType<Int>
}
