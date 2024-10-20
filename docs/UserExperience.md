## **Experiencia de Usuario - Juego del estanciero**
La experiencia del juego será representada, en un principio, a través de
una consola. Considerando la evolución del proyecto y el tiempo a
nuestra disposición, evaluaremos utilizar un Front-End en HTML o
realizar una interfaz visual con el framework Spring.

### **Inicio del juego:**

Al iniciar el programa, se le presentará al jugador varias opciones
relacionadas al juego, tales como: la dificultad, condiciones para
ganar, etc. Las opciones serán ingresadas por teclado.

Una vez establecidas esas opciones, todos los jugadores (el jugador
humano y los jugadores BOT) lanzarán los dados por primera vez para
establecer el orden para jugar. El orden será determinado por el número
obtenido en los dados de forma descendente, es decir, el jugador que
obtenga el número más alto comienza a jugar.

### **Desarrollo del juego:**

Una vez iniciado el juego, todos los jugadores comenzarán con \$35.000
de dinero, ninguna propiedad adquirida y desde la posición 0 del tablero
la cual está representada como la salida. En todo momento, el jugador
tendrá a su disposición en la consola (O interfaz) los datos
relacionados al juego (como su dinero) y podrá seleccionar varias
opciones relacionadas a las decisiones que tiene disponibles en ese
turno, tales como: tirar los dados y continuar con el juego, ver sus
propiedades (con chacras, estancias), ver sus cartas, vender una
propiedad, etc. A medida que el juego avanza y el jugador obtiene
propiedades, será presentado con más opciones dependiendo de su estado
actual. El programa verificará previo al turno del jugador las opciones
que tendrá disponible.

La posición de los jugadores será representada por una matriz que se
imprimirá en la consola al inicio de cada turno. Actualmente nuestra
principal idea es representar el tablero a nivel código como un Array,
donde cada índice del Array es una posición en el tablero. Dependiendo
de la posición en el array que caiga un jugador, se dispararan los
eventos correspondientes, por ejemplo: si un jugador cae en el slot 14,
que es el de comisaría, dicho jugador irá la cárcel, donde tendrá la
opciones de perder su turno, pagar la fianza o utilizar una carta para
salir de la cárcel (en caso de contar con una).

### **Tablero:**

Ejemplo: hay jugadores situados en las posiciones 10, 25 y 38 del
tablero.

Para poder tener mayor control sobre los procesos, se decidió
transformar la forma del tablero original (hexagonal) a una forma
cuadrada.

<pre>
[ 21 ][ 22 ][ 23 ][ 24 ][ 25 X][ 26 ][ 27 ][ 28 ][ 29 ][ 30 ]
[ 20 ]                                                 [ 31 ]
[ 19 ]                                                 [ 32 ]
[ 18 ]                                                 [ 33 ]
[ 17 ]                                                 [ 34 ]
[ 16 ]                                                 [ 35 ]
[ 15 ]                                                 [ 36 ]       
[ 14 ]                                                 [ 37 ]
[ 13 ]                                                 [ 38 X]
[ 12 ]                                                 [ 39 ]
[ 11 ]                                                 [ 40 ]
[ 10 X]                                                [ 41 ]
[  9 ][  8 ][  7 ][  6 ][  5 ][  4 ][  3 ][  2 ][  1  ][  0 ]


</pre>

Turno Nº x, Jugador: (nombre del jugador)

Dinero Disponible: \$10000 \|\| Pases para salir de prisión: (0,1,2)

1\_ Tirar Dados

2\_ Ver propiedades\
3\_ Vender una propiedad

El jugador podrá tirar los dados con la primera opción, en la consola
aparecerá un resultado similar a este: Resultado: 5!
<pre>
---------
| o     |
|       |
|     o |
---------

----------
| o      |
|    o   |
|      o |
----------
</pre>
El jugador puede ver sus propiedades, aparecerá en pantalla una lista de
strings donde verá las características de sus propiedades junto al
número de chacras o si es una estancia en cada una.

Ejemplo:

<ins>Salta</ins>:

Provincia de Salta (Zona Centro) \|\| Chacras: 3 \|\| Renta:\$ 200

Provincia de Salta (Zona Sur) \|\| Estancia: Si \|\| Renta:\$ 250

Dependiendo del ritmo y del avance del proyecto con respecto a las
entregas, evaluaremos otorgarle la opción al jugador de hipotecar o
vender sus propiedades

### **Final del juego:**

Dependiendo el modo de juego elegido en el inicio, el mismo tendrá
diferentes formas de finalizar:

* 1 Donde todos los jugadores menos uno (el ganador) quedan en
bancarrota.

* 2 En el caso que se permita ganar el juego por una cantidad de dinero
definida previamente, el mismo finalizará en el momento que un jugador
llegue a ese monto.

* 3 Cuando el jugador humano elija terminar el juego. (Pierde
automáticamente)

Al finalizar la partida, se mostrará en pantalla un resumen del juego
con las estadísticas finales y otra información relevante.

Por ejemplo:

Ganador: (nombre del jugador)

---\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--

Duración de la partida: 45 min

Candida de turnos: 75

---\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--

Dinero total acumulado (por jugador): $200000

Propiedades acumuladas (por jugador): $200000
