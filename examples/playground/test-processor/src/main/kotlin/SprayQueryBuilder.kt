import kotlin.reflect.KClass

abstract class SprayQueryBuilder<S: SprayData>(private val kClass: KClass<S>) {
    private val operations = mutableListOf<QueryTsFun>()

    fun build() : SprayQuery<S> = SprayQuery(operations, kClass)

    fun <T: Any, U: QueryableType<out T>> whereEqualTo(property: Property<S, T, U>, value: T) : SprayQueryBuilder<S> {
        operations.add {
            it.whereEqualTo(propertyToString(property), value)
        }
/*        baseQuery = {
            baseQuery(it).whereEqualTo(propertyToString(property), value)
        }*/
        return this
    }

    abstract fun <T: Any, U: QueryableType<out T>> propertyToString(property: Property<S, T, U>) : String
}

class SprayQuery<S: SprayData>(operations: List<QueryTsFun>, private val kClass: KClass<S>) {
    private val baseQuery : QueryTsFun = { it }
    private val tsFun : QueryTsFun = operations.fold(baseQuery) { acc, operation ->
        return@fold {
            val currentQuery = acc(it)
            operation(currentQuery)
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

typealias QueryTsFun = (MockQuery) -> MockQuery

interface Property<S: SprayData, T: Any, U: QueryableType<out T>>

abstract class SprayData

sealed interface QueryableType<Any> {
    data object StringQueryable : QueryableType<String>
    data object IntQueryable : QueryableType<Int>
}

class SprayQueryBuilderTest: SprayQueryBuilder<SprayData>(SprayData::class) {
    // TODO: Handle casting exception
    override fun <T : Any, U : QueryableType<out T>> propertyToString(property: Property<SprayData, T, U>): String {
        return when (property as SprayProperty<*,*>) {
            is SprayProperty.Name -> "name"
            is SprayProperty.Age -> "age"
        }
    }

    sealed class SprayProperty<T: Any, U: QueryableType<T>> : Property<SprayData, T, U> {
        data object Name : SprayProperty<String, QueryableType.StringQueryable>()
        data object Age : SprayProperty<Int, QueryableType.IntQueryable>()
    }
}
