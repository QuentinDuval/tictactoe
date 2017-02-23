(ns tictactoe.view.svg.utils
  (:require
    [tictactoe.logic.board :as board]
    [tictactoe.view.svg.constants :as cst]
    ))


(defn square-svg-panel
  [board]
  (let [pixel-size cst/board-pixel-size
        model-size (board/get-size board)]
    [:svg.board
     {:view-box (str "0 0 " model-size " " model-size)
      :style {:max-height (str pixel-size "px")}}
     ]))

(defn- special-char
  [str-code]
  [:div {:dangerouslySetInnerHTML {:__html str-code}}])

(defn back-arrow [] (special-char "&larr;"))
(defn circle-arrow [] (special-char "&#x21bb;"))
