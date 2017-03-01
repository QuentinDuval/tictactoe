(ns tictactoe.view.svg.utils)


(defn square-svg-panel
  [{:keys [pixel-size model-size]} cells]
  (into
    [:svg.board
     {:view-box (str "0 0 " model-size " " model-size)
      :style {:max-height (str pixel-size "px")}}]
    cells))

(defn- special-char
  [str-code]
  [:div {:dangerouslySetInnerHTML {:__html str-code}}])

(def back-arrow (special-char "&larr;"))
(def circle-arrow (special-char "&#x21bb;"))
