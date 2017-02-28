(ns tictactoe.view.svg.utils
  (:require
    [tictactoe.view.svg.constants :as cst]
    [tictactoe.logic.constants :as game-cst]
    ))


(defn in-square-svg-panel
  [cells]
  (let [pixel-size cst/board-pixel-size
        model-size game-cst/board-size]
    (into
      [:svg.board
       {:view-box (str "0 0 " model-size " " model-size)
        :style {:max-height (str pixel-size "px")}}]
      cells)))

(defn- special-char
  [str-code]
  [:div {:dangerouslySetInnerHTML {:__html str-code}}])

(defn back-arrow [] (special-char "&larr;"))
(defn circle-arrow [] (special-char "&#x21bb;"))
