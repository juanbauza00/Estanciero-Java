# Abstracciones: Interfaces y sus metodos

### Interfaz IPayment:
 Establece los metodos de pago, ya sea al banco o a otros jugadores, de esta manera, las clases BotPlayer y HumanPlayer implementan los metodos desarrollando cada una su propia logica.

**Metodos:**

- payAmount(Player player, int amount)

- payAmountToBank(int amount) 


### Interfaz IPurchasableSlot: 
Establece metodos relacionados a la obtencion de Slots comprables por los jugadores. Implementada en PurchasableSlot y heredada por los hijos (ProvinceSlot, CompanySlot, RailwaySlot)

**Metodos:**

- boolean sellSlot();

- boolean purchaseSlot();

### Interfaz IPlayer:
Descartada por la clase abstracta Player, se mantienen los siguientes metodos.

**Métodos:**

- throwDice()

- sellPropertyToBank()

- sellPropertyToPlayer()

- useGetOutOfJailCard()

