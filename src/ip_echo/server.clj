(ns ip-echo.server
  (:require
   [clojure.string :as str]
   [ring.adapter.jetty :refer [run-jetty]]))

(defn get-ip-from-header [req]
  (-> req
      :headers
      (get "x-forwarded-for")
      (str/split #",")
      first
      str/trim))

(defn handler [use-header? req]
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    (if use-header?
              (get-ip-from-header req)
              (:remote-addr req))})

(defn -main [& args]
  (let [use-header? (some #(= "--use-x-forwarded-for" %) args)]
    (when use-header?
      (println "Using X-FORWARDED-FOR header"))
    (run-jetty (partial handler use-header?) {:port  3030
                                              :join? true})))

