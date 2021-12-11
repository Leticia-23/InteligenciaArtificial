; -------------------------------------------------------------
; MODULO MAIN
;-------------------------------------------------------------
(defmodule MAIN 
	(export deftemplate nodo casilla)
	(export deffunction heuristica)
)

; Definimos tipos de datos. Se pide b�squeda A*
; por lo que necesitamos saber que nodos est�n abiertos o cerrados y el coste

	
(deftemplate MAIN::nodo
	(multislot estado)
	(multislot camino)
	(slot heuristica)
    (slot clase (default abierto)))

(defglobal MAIN
   ?*estado-inicial* = (create$ B B B H V V V))

(deffunction MAIN::heuristica ($?estado)
   (bind ?res 0)
   (loop-for-count (?i 1 9)
    (if (neq (nth$ ?i $?estado)
             (nth$ ?i ?*estado-final*))
         then (bind ?res (+ ?res 1))
     )
    )
   ?res)
   
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;     MODULO MAIN::INICIAL        ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(deffacts nodoIncial
   (nodo   (estado ?*estado-inicial*)
           (camino)
           (heuristica (heuristica ?*estado-inicial*))
           (clase abierto)))
 


   
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; MODULO MAIN::CONTROL            ;;;
;;; A*                              ;;;
;;;;;;;;;;;;;;;;;;;;; ;;;;;;;;;;;;;;;;;;


(defrule MAIN::pasa-el-mejor-a-cerrado-A*
	?nodo <- (nodo (heuristica ?h1)
			(coste ?c1)
			(clase abierto)
		 )
	(not (nodo (clase abierto)
			(heuristica ?h2)
			(coste ?c2&:(< (+ ?c2 ?h2) (+ ?c1 ?h1)))
	     )
	)
	=>
	(modify ?nodo (clase cerrado))
	(focus OPERADORES)
)

;-------------------------------------------------------------
; MODULO OPERADORES
;-------------------------------------------------------------
; en el modulo operadores englobamos las tres acciones 
 (defmodule OPERADORES
   (import MAIN deftemplate nodo)
   (import MAIN deffunction heuristica))

(defrule OPERADORES::arriba
   (nodo (estado $?a ?b ?c ?d H $?e)
          (camino $?movimientos)
          (clase cerrado)
          (coste ?coste))
=>
   (bind $?nuevo-estado (create$  $?a H ?c ?d ?b $?e))
   (assert (nodo 
		      (estado $?nuevo-estado)
              (camino $?movimientos ^)
              (coste (+ ?coste 1))
              (heuristica (heuristica $?nuevo-estado)))))

(defrule OPERADORES::abajo
   (nodo (estado $?a H ?b ?c ?d  $?e)
          (camino $?movimientos)
          (clase cerrado)
          (coste ?coste))

=>
   (bind $?nuevo-estado (create$ $?a ?d ?b ?c H $?e))
   (assert (nodo 
              (estado $?nuevo-estado)
              (camino $?movimientos v)
              (coste (+ ?coste 1))
              (heuristica (heuristica $?nuevo-estado)))))
              
(defrule OPERADORES::izquierda
   (nodo (estado $?a&:(neq (mod (length$ $?a) 3) 2)
                   ?b H $?c)
          (camino $?movimientos)
          (clase cerrado)
          (coste ?coste))

=>
   (bind $?nuevo-estado (create$ $?a H ?b $?c))
   (assert (nodo 
		      (estado $?nuevo-estado)
              (camino $?movimientos <)
              (coste (+ ?coste 1))
              (heuristica (heuristica $?nuevo-estado)))))

(defrule OPERADORES::derecha
   (nodo (estado $?a H ?b
                 $?c&:(neq (mod (length$ $?c) 3) 2))
          (camino $?movimientos)
          (clase cerrado)
          (coste ?coste))

 =>
   (bind $?nuevo-estado (create$ $?a ?b H $?c))
   (assert (nodo 
		      (estado $?nuevo-estado)
              (camino $?movimientos >)
              (coste (+ ?coste 1))
              (heuristica (heuristica $?nuevo-estado)))))
				  
;-------------------------------------------------------------
; MODULO RESTRICCIONES
;-------------------------------------------------------------
; Nos quedamos con el nodo de menor coste
; Soluci�n v�lida si el coste de cada operador es 1.

(defmodule RESTRICCIONES
   (import MAIN deftemplate nodo))
 
; eliminamos nodos repetidos (Falta eliminar los de igual coste -INTENTALO)
(defrule RESTRICCIONES::repeticiones-de-nodo
	(declare (auto-focus TRUE))
      ?nodo1 <- (nodo (estado $?estado) (camino $?camino1))
      (nodo (estado $?estado) 
        (camino $?camino2&:(> (length$ ?camino1) (length$ ?camino2))))
 =>
   (retract ?nodo1))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;    MODULO MAIN::SOLUCION        ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmodule SOLUCION
   (import MAIN deftemplate nodo))

(defrule SOLUCION::reconoce-solucion
   (declare (auto-focus TRUE))
   ?nodo <- (nodo (estado 1 2 3 8 H 4 7 6 5)
               (camino $?movimientos))
 => 
   (retract ?nodo)
   (assert (solucion $?movimientos)))

(defrule SOLUCION::escribe-solucion
   (solucion $?movimientos)
  =>
   (printout t "Solucion:" $?movimientos crlf)
   (halt))