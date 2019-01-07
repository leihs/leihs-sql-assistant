# Leihs SQL Assistant

## Start REPL

`boot repl`

## Start watcher/reset task

`boot watch reset`

It watches for changed files, then reloads them using `clojure.tools.namespace` and resets the application state as defined in `src/user.clj`. The only thing one has to do is to change a file, save it and refresh the page im browser.

## Built uberjar

`boot uberjar`

## Run uberjar

`java -jar target/leihs-sql-assistant.jar run`

## Run -main

1. `boot run` given `export ...` (or accepting the defaults)
2. Or passing command line arguments `boot [ run -- -d ... ]` (square brackets due to [this](https://github.com/boot-clj/boot/wiki/Task-Options-DSL#positional-parameters))
