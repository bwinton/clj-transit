(ns transit.core
  (:use
   compojure.core
   ring.util.response
   [ring.middleware.format-response :only [wrap-restful-response]]
   ring.adapter.jetty)
  (:require
   [compojure.handler :as handler]
   [compojure.route :as route]
   [transit.nextbus :as nextbus]))

;; Defaults

(def DEFAULT-RADIUS 0.125)
(def MAX-RADIUS 1.0)

;; Our database with stops, routes, directions

(defonce ttc (nextbus/load-agency "ttc"))

;; Helpers to parse request parameters

(defn position-from-params [params]
  {:latitude (-> params :latitude Double/parseDouble)
   :longitude (-> params :longitude Double/parseDouble)})

(defn radius-from-params [params]
  (if (contains? params :radius)
    (min (-> params :radius Double/parseDouble) MAX-RADIUS)
    DEFAULT-RADIUS))

;; The routes and app handler

(defroutes api-routes
  (GET "/api/stops" {params :params}
       (let [position (position-from-params params) radius (radius-from-params params)]
         (response {:stops (nextbus/find-stops ttc position radius)
                    :position position})))
  (GET "/api/predictions" {params :params}
       (let [position (position-from-params params) radius (radius-from-params params)]
         (response {:predictions (nextbus/predictions ttc (nextbus/find-stops ttc position radius))
                    :position position})))
  (route/resources "/")
  (route/not-found "Not found"))

(def app
  (-> (handler/api api-routes)
      (wrap-restful-response)))
