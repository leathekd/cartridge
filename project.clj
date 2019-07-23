(defproject leathekd/cartridge "1.1.0-SNAPSHOT"
  :description "HTTP response playback for clj-http"
  :url "https://www.github.com/leathekd/cartridge"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.8.3"

  :dependencies [[org.clojure/core.incubator "0.1.4"]
                 [org.clojure/data.codec "0.1.1"]
                 [org.clojure/tools.reader "1.3.2"]
                 [clj-http "3.10.0"]]

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.1"]
                                  [org.slf4j/slf4j-nop "1.7.26"]
                                  [ring/ring-jetty-adapter "1.7.1"]]}}

  :test-selectors {:default  #(not (:integration %))
                   :integration :integration
                   :all (constantly true)}

  :deploy-repositories {"releases" {:url "https://repo.clojars.org"
                                    :username [:gpg :env/clojars_username]
                                    :password [:gpg :env/clojars_password]}
                        "snapshots" {:url "https://repo.clojars.org"
                                     :username [:gpg :env/clojars_username]
                                     :password [:gpg :env/clojars_password]}}
  :release-tasks
  [["clean"]
   ["vcs" "assert-committed"]
   ["test" ":all"]
   ["clean"]
   ["change" "version" "leiningen.release/bump-version" "release"]
   ["vcs" "commit"]
   ["vcs" "tag" "--no-sign"]
   ["deploy" "clojars"]
   ["change" "version" "leiningen.release/bump-version"]
   ["vcs" "commit" "[artifacts-api] dev version %s"]
   ["vcs" "push" "origin" "HEAD"]])
