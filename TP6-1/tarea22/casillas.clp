; -------------------------------------------------------------
; MODULO MAIN (COMPLETAR)
;-------------------------------------------------------------
(defmodule MAIN (export deftemplate nodo))


(deftemplate MAIN::nodo
    ;;; Definición del estado.
    (slot casilla)
    (multislot camino)
    (slot coste (default 0))
    (slot clase (default abierto))
 
)


(deffacts MAIN::estado-inicial
    ;;; DEFINE Nodo inicial
    (nodo
        (casilla 0)
        (camino (implode$ (create$ 1)))
        ;(coste 0)
        ;(clase abierto)
    )
)

(defrule MAIN::pasa-el-mejor-a-cerrado-CU
    ;;; IMPLEMENTA CU
    ?nodo <- (nodo (clase abierto) (coste ?c1))
    
)



;-------------------------------------------------------------
; MODULO OPERADORES (COMPLETAR)
;-------------------------------------------------------------
; Acciones andar y saltar con sus restricciones
(defmodule OPERADORES
    (import MAIN deftemplate nodo))


(defrule OPERADORES::Andar
    ;;; IMPLEMENTA
)

(defrule OPERADORES::Saltar
    ;;; IMPLEMENTA
)

;-------------------------------------------------------------
; MODULO RESTRICCIONES (COMPLETAR)
;-------------------------------------------------------------
; Nos quedamos con el nodo de menor coste
; La longitud del camino no es el coste

(defmodule RESTRICCIONES
    (import MAIN deftemplate nodo))

; eliminamos nodos repetidos
(defrule RESTRICCIONES::repeticiones-de-nodo
    ;;; IMPLEMENTA
)

;-------------------------------------------------------------
; MODULO SOLUCION
;-------------------------------------------------------------
;Definimos el modulo solución
(defmodule SOLUCION
    (import MAIN deftemplate nodo))

;Miramos si hemos encontrado la solucion
(defrule SOLUCION::encuentra-solucion
    (declare (auto-focus TRUE))
    ?nodo1 <- (nodo (casilla 8) (camino $?camino)
                (clase cerrado))
    =>
    (retract ?nodo1)
    (assert (solucion ?camino)))

;Escribimos la solución por pantalla
(defrule SOLUCION::escribe-solucion
    (solucion $?movimientos)
    =>
        (printout t "La solucion tiene " (- (length ?movimientos) 1)
                    " pasos" crlf)
    (loop-for-count (?i 1 (length ?movimientos))
        (printout t "(" (nth ?i $?movimientos) ")" " "))
    (printout t crlf)
    (halt))