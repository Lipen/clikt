# Exception Handling

Clikt uses exceptions internally to signal that processing has ended
early for any reason. This includes incorrect command line usage, or
printing a help page.

## Where are Exceptions Handled? {#handling}
When you call [`CliktCommand.main`][main], it will parse the command line and catch any
[`CliktError`][CliktError] and [`Abort`][Abort] exceptions.
If it catches one, it will then print out the appropriate information and exit the process.
If the caught exception is a [`PrintMessage`][PrintMessage] or [`PrintHelpMessage`][PrintHelpMessage],
the process exit status will be 0 and the message will be printed to stdout.
Otherwise it will exit with status 1 and print the message to stderr.

Any other types of exceptions indicate a programming error, and are not caught by [`main`][main].
However, [`convert`][convert] and the other parameter transformations will wrap exceptions thrown
inside them in a [`UsageError`][UsageError], so if you define a custom transformation,
you don't have to worry about an exception escaping to the user.

## Handling Exceptions Manually

[`CliktCommand.main`][main] is just a `try`/`catch` block surrounding
[`CliktCommand.parse`][parse], so if don't want exceptions to be caught,
you can call [`parse`][parse] wherever you would normally call [`main`][main].

```kotlin
fun main(args: Array<String>) = Cli().parse(args)
```

## Which Exceptions Exist?

Clikt will throw [`Abort`][Abort]
if it needs to halt execution immediately without a specific message.
All other exceptions are subclasses of [`UsageError`][UsageError].

The following subclasses exist:

* [`PrintMessage`][PrintMessage] : The exception's message should be printed.
* [`PrintHelpMessage`][PrintHelpMessage] : The help page for the exception's command should be printed.
* [`UsageError`][UsageError] : The command line was incorrect in some way. All other exceptions subclass from this. These exceptions are automatically augmented with extra information about the current parameter, if possible.
* [`BadParameterValue`][BadParameterValue] : A parameter was given the correct number of values, but of invalid format or type.
* [`MissingParameter`][MissingParameter] : A required parameter was not provided.
* [`NoSuchOption`][NoSuchOption] : An option was provided that does not exist.
* [`IncorrectOptionValueCount`][IncorrectOptionValueCount] : An option was supplied but the number of values supplied to the option was incorrect.
* [`IncorrectArgumentValueCount`][IncorrectArgumentValueCount] : An argument was supplied but the number of values supplied was incorrect.
* [`MutuallyExclusiveGroupException`][MutuallyExclusiveGroupException] : Multiple options in a mutually exclusive group were supplied when the group is restricted to a single value.


[main]:                            ../api/clikt/com.github.ajalt.clikt.core/-clikt-command/main/
[CliktError]:                      ../api/clikt/com.github.ajalt.clikt.core/-clikt-error/
[Abort]:                           ../api/clikt/com.github.ajalt.clikt.core/-abort/
[PrintMessage]:                    ../api/clikt/com.github.ajalt.clikt.core/-print-message/
[PrintHelpMessage]:                ../api/clikt/com.github.ajalt.clikt.core/-print-help-message/
[convert]:                         ../api/clikt/com.github.ajalt.clikt.parameters.options/convert/
[UsageError]:                      ../api/clikt/com.github.ajalt.clikt.core/-usage-error/
[parse]:                           ../api/clikt/com.github.ajalt.clikt.core/-clikt-command/parse/
[BadParameterValue]:               ../api/clikt/com.github.ajalt.clikt.core/-bad-parameter-value/
[MissingParameter]:                ../api/clikt/com.github.ajalt.clikt.core/-missing-parameter/
[NoSuchOption]:                    ../api/clikt/com.github.ajalt.clikt.core/-no-such-option/
[IncorrectOptionValueCount]:       ../api/clikt/com.github.ajalt.clikt.core/-incorrect-option-value-count/
[IncorrectArgumentValueCount]:     ../api/clikt/com.github.ajalt.clikt.core/-incorrect-argument-value-count/
[MutuallyExclusiveGroupException]: ../api/clikt/com.github.ajalt.clikt.core/-mutually-exclusive-group-exception/
