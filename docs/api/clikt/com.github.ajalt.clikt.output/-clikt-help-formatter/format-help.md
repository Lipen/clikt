[clikt](../../index.md) / [com.github.ajalt.clikt.output](../index.md) / [CliktHelpFormatter](index.md) / [formatHelp](./format-help.md)

# formatHelp

`open fun formatHelp(prolog: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, epilog: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, parameters: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`HelpFormatter.ParameterHelp`](../-help-formatter/-parameter-help/index.md)`>, programName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Overrides [HelpFormatter.formatHelp](../-help-formatter/format-help.md)

Create the full help string.

### Parameters

`prolog` - Text to display before any parameter information

`epilog` - Text to display after any parameter information

`parameters` - Information about the command's parameters

`programName` - The name of the currently executing program