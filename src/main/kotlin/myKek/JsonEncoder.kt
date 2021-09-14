package myKek

import org.json.JSONObject
import java.util.UUID

sealed class Value {
    class Float(val value: kotlin.Float) : Value()
    class Int(val value: kotlin.Int) : Value()
    class String(val value: kotlin.String) : Value()
    object Null : Value()
    class List(val value: kotlin.collections.List<Value>) : Value()
    class JsonObject(val value: kotlin.collections.List<Pair<kotlin.String, Value>>) : Value()
    class Boolean(val value: kotlin.Boolean) : Value()
}

class Encoder {
    companion object {
        fun encode(value: Value): String =
            when (value) {
                is Value.Float -> value.value.toString()
                is Value.Int -> value.value.toString()
                is Value.String -> JSONObject.valueToString(value.value)
                Value.Null -> "null"
                is Value.List -> "[${value.value.joinToString { encode(it) }}]"
                is Value.JsonObject -> "{${value.value.joinToString { (key, value) -> "\"$key\":${encode(value)}" }}}"
                is Value.Boolean -> value.value.toString()
            }
    }
}

class Encode {
    companion object {
        fun string(value: String): Value =
            Value.String(value)

        fun jsonObject(
            value: Pair<String, Value>,
            vararg values: Pair<String, Value>
        ): Value.JsonObject =
            Value.JsonObject((listOf(value) + values.toList()))

        fun list(values: List<Value.JsonObject>): Value =
            Value.List(values)

        fun int(value: Int): Value =
            Value.Int(value)

        fun float(value: Float): Value =
            Value.Float(value)

        fun boolean(value: Boolean): Value =
            Value.Boolean(value)

        fun <T> optional(value: T?, encoder: (T) -> Value): Value =
            when (value) {
                null -> Value.Null
                else -> encoder(value)
            }
    }
}

class UUIDEncoder private constructor() {
    companion object {
        fun encode(uuid: UUID): Value =
            Encode.string(uuid.toString())
    }
}
