# Entidades y Relaciones

## Clases Principales:

### Game:
#### Atributos:
* **Bank bank**: Contiene al objeto banco que será utilizando durante el juego.
* **enum gameDifficulty**: Representa el nivel de dificultad del juego (dificil || medio || facil).
* **enum gameMode**: Representa el modo de juego (evitar la bancarrota || objetivo monetario).
* **moneyGoal**: Este atributo es utilizando unicamente si se selecciona el modo de  juego por onjetivo monetario y almacena el valor objetivo.
* **Player[] players**: Almacena a los jugadores que participan en el juego.

#### Métodos:
* **start()**: Da inicio al juego.
* **nextTurn()**: Genera el cambio de turno entre los jugadores.
* **checkPlayerPosition(Player player)**: Revisa la posición de un jugador (la posición representa su lugar en el tablero) para realizar acciones, como avanzar de casilla, desde dicho punto.
* **checkForBankruptPlayers()**: Revisa si un jugador se quedó en quiebra para darle "Game Over".
* **checkForWinner()**: Dependiendo el modo de juego, chequea cuando un jugador gana y finaliza el juego.

#### Responsabilidad:
Esta clase contiene al juego completo y a todos sus objetos. Es la responsable de dirigir los turnos entre jugadores, gestionar las interacciones entre ellos junto con el banco y el tablero, y de dar tanto inicio como fin cuando un jugador resulta ganador.

---

### Bank
#### Atributos:
* **int money**: Contiene el dinero disponible del banco.

#### Métodos:
* **payAmountToPlayer(Player player, int amount)**: Paga a un jugador una cantidad definida.

#### Responsabilidad:
El banco se encarga de realizar los pagos que le corresponden a otros jugadores y funciona como una entidad que administra el dinero del juego.

---

### Player (abstract)
#### Atributos:
* **string name**: Permite establecer un nombre al jugador.
* **int idPlayer**: Permite establecer un identificador unico al jugador.
* **int playerPosition**: Permite establecer la posicion del jugador, por lo tanto definir donde se ubica el jugador en cada ronda.
* **int money**: Almacena el dinero que el jugador tiene disponible.
* **boolean isPlayerInJail**: Permite establecer si el jugador esta en prision o no. (tal vez no es necesario porque tenemos checkPlayerPosition() en la clase game).
* **int amountOfFreeJailCards**: Permite establecer la cantidad de cartas que permitan salir de la prision al jugador sin ningun costo.

#### Métodos:
* **throwDice()**: Arroja los dados y verificar cuanto debe avanzar el jugador.
* **sellPropertyToBank(Parametros a definir)**: Permite vender una propiedad al banco.
* **sellPropertyToPlayer(Parametros a definir)**: Permite vender una propiedad a otro jugador.
* **payAmount(Player player, int amount)**: Permite pagar a otros jugadores.
* **payAmount(Bank bank, int amount)**: Permite pagar al banco.

#### Responsabilidad:
Entidad abstracta que provee atributos y metodos a sus clases derivadas

---

### BotPlayer
#### Atributos:
* **enum playStyle**: Define el estilo que tendra el bot a la hora de la jugabilidad.

#### Métodos:
* Implementa métodos de la clase padre, pero adaptados para ser automáticos.

#### Responsabilidad:
Clase que implementa todos los atributos y metodos de la clase abstracta Player.

---

### HumanPlayer
#### Atributos:
* Implementa los atributos de la clase padre.

#### Métodos:
* Implementa métodos de la clase padre, pero adaptados para poder ser ejecutados por el usuario.

#### Responsabilidad:
Clase que implementa todos los atributos y metodos de la clase abstracta Player.

---

### Board
#### Atributos:
* ** BoardSlot[] slots**: Array que contiene todos los slots (posiciones de tablero) en las que se puede posicionar un jugador.
* **SpecialCard[] specialCardsDeck**: Array que contiene todas las cartas especiales (Suerte y Destino).

#### Métodos:
* **BoardSlot[] getSlotsByOwner(Player owner)**: Retorna BoardSlot[] (las propiedades de las que es dueño un jugador) toma de parametro un objeto jugador.

#### Responsabilidad:
Entidad que representa el tablero del juego. Es posible que esta clase sea eliminada en el futuro y sus implementaciones sean agregadas a la clase Game.

---

### SpecialCard
#### Atributos:
* **int moneyAmount**:
* **string description**:
* **string type**: Representa si la carta es de Suerte o Destino.

#### Métodos:
* **movePlayerToBoardSlot(int slotId)**: Mueve la posicion del jugador a un slot del tablero (Board).

#### Responsabilidad:
Entidad que representa las cartas especiales (Suerte y Destino).

---

### BoardSlot (abstract)
#### Atributos:
* **String name**: Nombre del slot, por ejemplo: Salta Zona Sur.
* **String description**: Contiene una breve descripcion del slot.
* **int position**: Numero entero que representa la posicion en el tablero de un slot.

#### Responsabilidad:
Entidad que representa cada uno de las posiciones del tablero. Es una clase abstracta porque cada uno de los slots del tablero heredaran las propiedades name, description y position.

---

### SpecialCardSlot 
#### Métodos:
* **drawSpecialCardFromDeck()**: Saca una carta del mazo, parametro cardType especifica si es de suerte o de destino.

#### Responsabilidad:
Representa un slot de Suerte o Destino.

---

### RestSlot
#### Métodos:
* **askUserForRest()**: El jugador puede optar por pasar su turno si se encuentra en este slot.

#### Responsabilidad:
Representa un slot de descanso.

---

### JailSlot
#### Métodos:
* **sendPlayerToPrision()**: Cambia la propiedad isPlayerInJail de un jugador a true. Toma de param. un jugador.
* **payBail()**: Permite a un jugador pagar la fianza y no ir a la carcel.

#### Responsabilidad:
Representa un slot de Comisaria/Marche Preso.

---

### PurchasableSlot (Abstract)
#### Atributos:
* **int price**: Al ser entidades que pueden ser compradas, estas necesitan un precio.
* **Player owner**: Objeto de clase Player que representa el jugador dueño de la propiedad, null cuando aun no ha sido comprada (y por lo tanto es propiedad del banco).
* **int sellValue**: Precio de venta al banco.
* **int rentValue**: Precio de renta que deben pagar los otros jugadores.

#### Métodos:
* **Player getSlotOwner()**: Retorna el dueño de la propiedad.
* **purchaseSlot()**: Permitira asociar un objeto Player, indicando que la propiedad fue comprada.

#### Responsabilidad:
Entidad de la que heredan las clases ProvinceSlot, CompanySlot y RailwaySlot. Representa un slot donde el jugador puede realizar una acción de compra sobre el mismo.

---

### ProvinceSlot
#### Atributos:
* **int ranchAmount**: Representa la cantidad de chacras construidas en esa provincia.
* **bool isResidence**: Representa si la provincia tiene una estancia o no.
* **string location**: Representa el nombre de la provincia.

#### Métodos:
* **bool canUpgrade()**: Verifica si es posible mejorar la provincia para colocar una residence (estancia).

#### Responsabilidad:
Entidad que representa una provincia en el tablero.

---

### RailwaySlot
Hereda los atributos y metodos de PurchasableSlot.

#### Responsabilidad:
Entidad que representa un slot de Ferrocarril.

---

### CompanySlot
Hereda los atributos y metodos de PurchasableSlot.

#### Responsabilidad:
Entidad que representa un slot de compañia (Bodega o Petrolera).
