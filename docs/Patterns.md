# Patrones



## Singleton (Creacional)

Decidimos implementar el patrón de diseño creacional Singleton en la clase Bank, para poder acceder a la misma instancia de Bank dentro de todas las clases que lo necesiten. 

## Template (Comportamiento)

Decidimos implementar el patrón Template para la clase Player, ya que las subclases HumanPlayer y BotPlayer implementaran los metodos de Player, pero con distinta lógica, ya que los metodos de HumanPlayer tendrán una entrada por consola (input) para que el usuario tome decisiones, mientras que los metodos de BotPlayer se ejecutarán automáticamente y no tendrán ninguna entrada. 

## Observacion y patrón descartado

No decidimos implementar más que estos dos patrones para no complejizar el código, y por la complejidad actual del proyecto nos parece adecuado solamente utilizar dichos patrones.


### Strategy (Comportamiento)
En un momento consideraremos implementar el patrón de comportamiento Strategy para las clases HumanPlayer y BotPlayer, pero decidimos lo contrario. Al tener mas de una clase "contexto" no es necesario aplicar este patron porque cada clase que hereda de Player implementará la logica necesaria.



El codigo podría verse complejizado al implementar el patrón: 

```
public class HumanPlayer extends Player {

    public void payRent(PaymentStrategy payment){
        payment.Pay(amount)
    }


}

pubic class BotPlayer() extends Player {

    public void payRent(PaymentStrategy payment) {
        payment.Pay(amount)
    }
}


public class HumanPaymentStrategy implements IPaymentStrategy{

    public void payRent(amount) {

        //logica
        //incluye input al usuario si esta seguro
    }
    
}

public class BotPaymentStrategy implements IPaymentStrategy() {

    public void payRent(amount) {
        //logica de pago automatizada (sin input al usuario)
    }

}
```
