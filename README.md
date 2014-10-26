
# useful

Useful is a collection of functions that make it easier to write
Clojure applications.

## Usage With Lein-Exec

If you have `lein-exec` installed and want to play with this library
on the REPL, paste this into the REPL:

    (use '[leiningen.exec :only [deps]])
    (deps '[[asimjalis/useful "1.1.0-SNAPSHOT"]])
    (use 'asimjalis.useful)
    (println-err "hi")


## Usage in Lein Project

If you want to include this library in a project, in your
`project.clj` file, under `defproject`, modify your `:dependencies`,
so they look like this:

    :dependencies [[asimjalis/useful "1.1.0-SNAPSHOT"]]

And then in your code or on the REPL add this:

    (use 'asimjalis.useful)

Alternatively, in your code you can add this:

    (ns myproject
      (:use 
        [asimjalis.useful]))

## License

Copyright (c) 2014 Asim Jalis

For license details see LICENSE file.

# Author

Asim Jalis (asimjalis@gmail.com)

For more details see [asimjalis.com](http://asimjalis.com)
