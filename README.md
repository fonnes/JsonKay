# JsonKay
Elm inspired json encoding and decoding in Kotlin

# Usage

```kotlin
val encoded = Encoder.encode(
  Encode.jsonObject(
      Pair("id", Encode.string(id)),
      Pair("title", Encode.string(title),
      Pair("description", Encode.optional(description, Encode::string))
  )
)

println(encoded) // {"id":"1234","title":"hello","description":null}
```
