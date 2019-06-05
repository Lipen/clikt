# Utilities

Writing command line interfaces often involves more than just parsing
the command line. Clikt also provides functions to perform actions
commonly used in command line programs.

## Launching Editors

If you need to ask users for multi-line input, or need to have the user edit a file, you can do so
through [`TermUi.editText`][editText] and
[`TermUi.editFile`][editFile]. These
functions open the program defined in the `VISUAL` or `EDITOR` environment variables, or a sensible
default if neither are defined. The functions return the edited text if the user saved their
changes.

For example:

```kotlin
fun getCommitMessage(): String? {
    return TermUi.editText("\n" +
            "\n# Enter your message. " +
            "\n# Lines starting with # are ignored",
            requireSave = true)
            ?.replace(Regex("#[^\n]*\n"), "")
}
```

## Input Prompts

Options can [prompt for values automatically][prompting-for-input], but you can also do
so manually with [`TermUi.prompt`][prompt]. By
default, it accepts any input string, but you can also pass in a conversion function. If the
conversion raises a [`UsageError`][UsageError],
the prompt will ask the user to enter a different value.

```kotlin
val input = TermUi.prompt("Enter a number") {
    it.toIntOrNull() ?: throw UsageError("$it is not a valid integer")
}
TermUi.echo("Twice your number is ${input * 2}")
```

Which will produce interactive sessions like this:

```
Enter a number: foo
Error: foo is not a valid integer
Enter a number: 11
Twice your number is 22
```

## Confirmation Prompts

You can also ask the user for a yes or no response with
[`TermUi.confirm`][confirm]:

```kotlin
if (TermUi.confirm("Continue?") == true) {
    TermUi.echo("OK!")
}
```

If you simply want to abort the program in the user gives a negative
response, you can pass `abort=true`:

```kotlin
TermUi.confirm("Continue?", abort=true)
```


[editText]:            api/clikt/com.github.ajalt.clikt.output/-term-ui/edit-text.md
[editFile]:            api/clikt/com.github.ajalt.clikt.output/-term-ui/edit-file.md
[prompting-for-input]: options.md#prompting-for-input
[prompt]:              api/clikt/com.github.ajalt.clikt.output/-term-ui/prompt.md
[UsageError]:          api/clikt/com.github.ajalt.clikt.core/-usage-error/index.md
[confirm]:             api/clikt/com.github.ajalt.clikt.output/-term-ui/confirm.md
