; -------------------------------------------------------------
; MODULO MAIN (COMPLETAR)
;-------------------------------------------------------------
(defmodule MAIN (export deftemplate nodo))


(deftemplate MAIN::nodo
    ;;; Definición del estado.
    (slot casilla)
    (slot N_andar)
    (slot N_saltar)
    (multislot camino)
    (slot coste (default 0))
    (slot clase (default abierto))
 
)


(deffacts MAIN::estado-inicial
    ;;; DEFINE Nodo inicial
    (nodo
        (casilla 1)
        (N_andar 0)
        (N_saltar 0)
        (camino (implode$ (create$ 1)))
    )
)

(defrule MAIN::pasa-el-mejor-a-cerrado-CU
    ?nodo <- (nodo (coste ?c1) (clase abierto))
	(not (nodo (clase abierto) (coste ?c2&:(< ?c2 ?c1))))
	=>
	(modify ?nodo (clase cerrado))
	(focus OPERADORES)  
)



;-------------------------------------------------------------
; MODULO OPERADORES (COMPLETAR)
;-------------------------------------------------------------
; Acciones andar y saltar con sus restricciones
(defmodule OPERADORES
    (import MAIN deftemplate nodo))


(defrule OPERADORES::Andar
    (nodo (casilla ?num)
            (N_andar ?andadas)
            (N_saltar ?saltos)
            (camino $?movs)
            (coste ?cost)
            (clase cerrado)
    )
    (test (< ?num 8))
    =>
    (assert(nodo
            (casilla (+ ?num 1))
            (N_andar (+ ?andadas 1))
            (N_saltar ?saltos)
            (camino $?movs (+ ?num 1))
            (coste (+ ?cost 1))
            )
    )

)

(defrule OPERADORES::Saltar
    (nodo (casilla ?num)
            (N_andar ?andadas)
            (N_saltar ?saltos)
            (camino $?movs)
            (coste ?cost)
            (clase cerrado)
    )
    (test (< ?num 5))
    (test (< ?saltos ?andadas))
    =>
    (assert(nodo
            (casilla (* ?num 2))
            (N_andar ?andadas)
            (N_saltar (+ ?saltos 1))
            (camino $?movs  (* ?num 2))
            (coste (+ ?cost 2))
            )
    )
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
    (declare (auto-focus TRUE))
      ?nodo1 <- (nodo (casilla ?casilla) (coste ?cost1))
      (nodo (casilla ?casilla) 
        (coste ?cost2&:(> ?cost1 ?cost2)))
    =>
    (retract ?nodo1))
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