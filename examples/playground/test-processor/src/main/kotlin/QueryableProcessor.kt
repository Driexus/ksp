import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate

class QueryableProcessor(
    val codeGenerator: CodeGenerator,
    val logger: KSPLogger
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation("com.example.annotation.FirestoreQueryable")
        val ret = symbols.filter { !it.validate() }.toList()
        symbols
            .filter { it is KSClassDeclaration && it.validate() }
            .forEach { it.accept(QueryableVisitor(), Unit) }
        return ret
    }

    inner class QueryableVisitor : KSVisitorVoid() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            classDeclaration.primaryConstructor!!.accept(this, data)
        }

        override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
            val parent = function.parentDeclaration as KSClassDeclaration
            val packageName = parent.containingFile!!.packageName.asString()
            val className = "${parent.simpleName.asString()}Queryable"
            val file = codeGenerator.createNewFile(Dependencies(true, function.containingFile!!), packageName , className)

            file.appendText("package $packageName\n\n")
            file.appendText("import Queryable\n")
            file.appendText("import Comparable\n")
            file.appendText("import EqualTo\n")
            file.appendText("import GreaterThan\n")
            file.appendText("import LessThan\n")
            file.appendText("import MockQuery\n")
            file.appendText("import MockRef\n\n")
            file.appendText("@Suppress(\"MemberVisibilityCanBePrivate\")\n")
            file.appendText("class $className(\n")

            function.parameters.forEach {
                val name = it.name!!.asString()
                val typeName = StringBuilder(it.type.resolve().declaration.qualifiedName?.asString() ?: "<ERROR>")
                val typeArgs = it.type.element!!.typeArguments
                if (it.type.element!!.typeArguments.isNotEmpty()) {
                    typeName.append("<")
                    typeName.append(
                        typeArgs.map {
                            val type = it.type?.resolve()
                            "${it.variance.label} ${type?.declaration?.qualifiedName?.asString() ?: "ERROR"}" +
                                if (type?.nullability == Nullability.NULLABLE) "?" else ""
                        }.joinToString(", ")
                    )
                    typeName.append(">")
                }
                file.appendText("    val $name: Comparable<$typeName>? = null,\n")
            }

            file.appendText(") : Queryable() {\n")
            file.appendText("    override fun applyQueryable(ref: MockRef) : MockQuery {\n")
            file.appendText("        var query : MockQuery = ref\n")

            function.parameters.forEach {
                val name = it.name!!.asString()
                val typeName = StringBuilder(it.type.resolve().declaration.qualifiedName?.asString() ?: "<ERROR>")
                val typeArgs = it.type.element!!.typeArguments
                if (it.type.element!!.typeArguments.isNotEmpty()) {
                    typeName.append("<")
                    typeName.append(
                        typeArgs.map {
                            val type = it.type?.resolve()
                            "${it.variance.label} ${type?.declaration?.qualifiedName?.asString() ?: "ERROR"}" +
                                if (type?.nullability == Nullability.NULLABLE) "?" else ""
                        }.joinToString(", ")
                    )
                    typeName.append(">")
                }
                file.appendText("        $name?.let {\n")
                file.appendText("            query = when (it) {\n")
                file.appendText("                is GreaterThan -> query.whereGreaterThan(\"$name\", it.value)\n")
                file.appendText("                is LessThan -> query.whereLessThan(\"$name\", it.value)\n")
                file.appendText("                is EqualTo -> query.whereEqualTo(\"$name\", it.value)\n")
                file.appendText("            }\n")
                file.appendText("        }\n\n")
            }

            file.appendText("        return query\n")
            file.appendText("    }\n")
            file.appendText("}")
            file.close()
        }
    }
}

class QueryableProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor {
        return QueryableProcessor(environment.codeGenerator, environment.logger)
    }
}
