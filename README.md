# gravel
Simple graph traversal in Clojure and ClojureScript

## Development

Use the `:dev` alias for starting up the notebook server. By default, this will
also boot up nrepl cider middleware injected. You can opt out from either by
passing `false` for the `:nrepl?` or `:cider?` option respectively.

```clj
clj -X:dev
```

See `dev/user.clj` for examples on how to update the notebook from the repl.


```clj
(require '[nextjournal.clerk :as clerk])

(clerk/show! "notebooks/graph-walking.clj")
```
