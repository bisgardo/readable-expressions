# Readable Expressions

Readable Expressions is a project that aims to provide a feature-complete fluent API that can be used to
programmatically generate any regular expression (regex) as defined by the standard `java.util.Pattern` class. The API
defines functions (and other constructs) that are named and behave in such a way that one should be able to understand
the meaning of the generated regex just by reading the source code. Additionally, using the code completion of any
modern IDE, programmers should be able to write fairly complex regexes with nothing more than a superficial knowledge on
the subject.

[TODO: The project furthermore attempts to provide a code-based reference documentation (navigable using the navigation
features of modern IDEs) of regular expressions in general.]

[TODO: Iteration of matching strings, static analysis, optimization/simplification of constructed regexes, ...]

### Status

While the currently implemented features seem to work, many require some degree of redesign in order to be fully
implementable. When a feature that is not implemented/implementable is invoked, a helpful
`java.lang.UnsupportedOperationException` is thrown.

The fluent API has been exhaustively tested for small expressions and is believed to be generally correct. Refactorings
of test code is needed in order to properly verify larger expressions.

The most notable feature that is still missing from the API is "look-around" expressions. Documentation is almost
completely absent.

In short, the project should be usable at this point, but is very incomplete and many features are still in flux.
