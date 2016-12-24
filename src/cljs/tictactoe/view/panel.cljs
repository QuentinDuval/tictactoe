(ns tictactoe.view.panel)


(defn- special-char
  [str-code]
  [:div {:dangerouslySetInnerHTML {:__html str-code}}])

(defn- top-button
  [on-click txt]
  [:button.top-button {:on-click on-click} txt])

(defn render-top-panel
  [{:keys [on-restart on-undo]}]
  [:div.scores
   [top-button on-restart (special-char "&#x21bb;")]
   [:h1#title "Tic Tac Toe"]
   [top-button on-undo (special-char "&larr;")]
   ])
