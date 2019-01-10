# Leihs SQL Assistant

## Start REPL

`boot repl`

This throws you into `app` namespace. From there you can first manually `(start)` and then `(reset)` the global application state whenever needed.

NOTE: Beware vim users! The reset blows away all your namespace definitions added interactively. You can disable this behaviour for a particular namespace by:

```clojure
(:require '[clojure.tools.namespace.repl :as ctnr]))
(ctnr/disable-reload!)
```

For more information see [clojure.tools.namespace](https://github.com/clojure/tools.namespace).

## Start watcher/reset task

`boot watch reset` or `boot focus` ;-)

It watches for changed files, then reloads them using `clojure.tools.namespace` and resets the global application state as defined in `boot/app.clj`. The only thing one has to do is to change a file, save it and refresh the page im browser.

## Build uberjar

`boot uberjar`

## Run uberjar

`java -jar target/leihs-sql-assistant.jar run`

## Run -main

1. `boot run` given `export ...` (or accepting the defaults)
2. Or passing command line arguments `boot [ run -- -d ... ]` (square brackets due to [this](https://github.com/boot-clj/boot/wiki/Task-Options-DSL#positional-parameters))
