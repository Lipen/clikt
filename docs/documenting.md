# Documenting Scripts

Clikt takes care of creating formatted help messages for commands. There
are a number of ways to customize the default behavior. You can also
implement your own [`HelpFormatter`](/api/clikt/com.github.ajalt.clikt.output/-help-formatter/) and set it on the [command's
context](commands.md#customizing-contexts).

## Help Texts

[Commands](/api/clikt/com.github.ajalt.clikt.core/-clikt-command/) and parameters accept a `help` argument. Commands also accept an
`epilog` argument, which is printed after the parameters and commands on
the help page. All text is automatically re-wrapped to the terminal
width.

```kotlin tab="Example"
class Hello : CliktCommand(help = """
    This script prints NAME COUNT times.

    COUNT must be a positive number, and defaults to 1.
    """) {

    val count by option("-c", "--count", help = "number of greetings").int().default(1)
    val name by argument()
    override fun run() = repeat(count) { echo("Hello $name!") }
}
```

```text tab="Usage"
$ ./hello --help
Usage: hello [OPTIONS] NAME

  This script prints NAME COUNT times.

  COUNT must be a positive number, and defaults to 1.

Options:
  -c, --count INT  number of greetings
  -h, --help       Show this message and exit
```

Option names and metavars will appear in help output even if no help
string is specified for them. On the other hand, arguments only appear
in the usage string. It is possible to add a help string to arguments
which will be added to the help page, but the Unix convention is to just
describe arguments in the command help.

## Subcommand Short Help

Subcommands are listed in the help page based on their
[name](commands.md#customizing-command-name). They have a short help
string which is the first line of their help.

```kotlin tab="Example"
class Tool : NoRunCliktCommand()

class Execute : NoRunCliktCommand(help = """
    Execute the command.

    The command will be executed.
    """)

class Abort : NoRunCliktCommand(help="Kill any running commands.")
```

```text tab="Usage"
$ ./tool --help
Usage: tool [OPTIONS] COMMAND [ARGS]...

Options:
  -h, --help  Show this message and exit

Commands:
  execute  Execute the command.
  abort    Kill any running commands.
```


## Help Option Customization

Clikt handles the help option is specially. It is added automatically to
every command. Any help option name that conflicts with another option is
not used for the help option. If the help option has no unique names, it
is not added.

You can change the help option's name and help message on the
[command's context](commands.md#customizing-contexts):

```kotlin tab="Example"
class Tool : NoRunCliktCommand() {
    init {
        context {
            helpOptionNames = setOf("/help")
            helpOptionMessage = "show the help"
        }
    }
}
```

```text tab="Usage"
$ ./tool /help
Usage: tool [OPTIONS]

Options:
  /help  show the help
```

If you don't want a help option to be added, you can set
`helpOptionNames = emptySet()`

## Default Values in Help

You can configure the help formatter to show default values in the help output by passing
`showRequiredTag = true` to the `CliktHelpFormatter`. By default, the string value of the
default value will be shown. You can show a different value by passing the value you want to show to
the `defaultForHelp` parameter of
[`default`](/api/clikt/com.github.ajalt.clikt.parameters.options/default/).

```kotlin tab="Example"
class Tool : NoRunCliktCommand() {
    init {
        context { helpFormatter = CliktHelpFormatter(showDefaultValues = true) }
    }

    val a by option(help = "this is optional").default("value")
    val b by option(help = "this is also optional").default("value", defaultForHelp="chosen for you")
}
```

```text tab="Usage"
$ ./tool --help
Usage: tool [OPTIONS]

Options:
  --a TEXT    this is optional (default: value)
  --b TEXT    this is also optional (default: chosen for you)
```


## Required Options in Help 

By default, [`required`](/api/clikt/com.github.ajalt.clikt.parameters.options/required/) options
are displayed the same way as other options. The help formatter includes two different ways to show
that an option is required.

### Required Option Marker

You can pass a character to the `requiredOptionMarker` argument of the `CliktHelpFormatter`. 

```kotlin tab="Example"
class Tool : NoRunCliktCommand() {
    init {
        context { helpFormatter = CliktHelpFormatter(requiredOptionMarker = "*") }
    }

    val option by option(help = "this is optional")
    val required by option(help = "this is required").required()
}
```

```text tab="Usage"
$ ./tool --help
Usage: tool [OPTIONS]

Options:
  --option TEXT    this is optional
* --required TEXT  this is required
  -h, --help       Show this message and exit
```

### Required Option Tag

You can also show a tag for required options by passing `showRequiredTag = true` to the `CliktHelpFormatter`.

```kotlin tab="Example"
class Tool : CliktCommand() {
    init {
        context { helpFormatter = CliktHelpFormatter(showRequiredTag = true) }
    }

    val option by option(help = "this is optional")
    val required by option(help = "this is required").required()
}
```

```text tab="Usage"
$ ./tool --help
Usage: tool [OPTIONS]

Options:
  --option TEXT    this is optional
  --required TEXT  this is required (required)
  -h, --help       Show this message and exit
```

## Grouping Options in Help

You can group options into separate help sections by using
[OptionGroup](/api/clikt/com.github.ajalt.clikt.parameters.groups/-option-group/).
The name of the group will be shown in the output. You can also add an extra help message to be
shown with the group. Groups can't be nested.

```kotlin tab="Example"
class UserOptions : OptionGroup(name = "User Options", help = "Options controlling the user") {
    val name by option(help = "user name")
    val age by option(help = "user age").int()
}

class Tool : NoRunCliktCommand() {
    val userOptions by UserOptions()
}
```

```text tab="Usage"
$ ./tool --help
Usage: cli [OPTIONS]

User Options:

  Options controlling the user

  --name TEXT  user name
  --age INT    user age

Options:
  -h, --help  Show this message and exit
```

### Note for IntelliJ users:

If you're using IntelliJ, there is a bug in the Kotlin plugin for versions 1.2.31 and under that prevents
[provideDelegate](/api/clikt/com.github.ajalt.clikt.parameters.groups/provide-delegate/)
from being imported automatically, so you might need to add this import manually: `import
com.github.ajalt.clikt.parameters.groups.provideDelegate`
