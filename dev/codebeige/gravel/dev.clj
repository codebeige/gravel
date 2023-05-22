(ns codebeige.gravel.dev
  (:require [nextjournal.clerk :as clerk]))

(defn- inject-cider [middleware]
  (conj middleware (requiring-resolve 'cider.nrepl/cider-middleware)))

(defn- start-nrepl! [{:keys [cider?] :or {cider? true} :as opts}]
  (let [server-opts (requiring-resolve 'nrepl.cmdline/server-opts)
        start-server (requiring-resolve 'nrepl.cmdline/start-server)
        server-started-message (requiring-resolve
                                'nrepl.cmdline/server-started-message)
        save-port-file (requiring-resolve 'nrepl.cmdline/save-port-file)
        interactive-repl (requiring-resolve 'nrepl.cmdline/interactive-repl)
        opts* (cond-> (server-opts opts)
                cider? (update :middleware inject-cider))
        server (start-server opts*)]
    (println (server-started-message server opts*))
    (save-port-file server opts*)
    (interactive-repl server opts*)))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn up! [{:keys [cider? nrepl? nrepl-opts] :or {nrepl? true} :as opts}]
  (when nrepl?
    (future (start-nrepl! (assoc nrepl-opts :cider? cider?))))
  (clerk/serve! opts))
