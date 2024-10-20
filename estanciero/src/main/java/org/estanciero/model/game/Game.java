package org.estanciero.model.game;

import lombok.Getter;
import lombok.Setter;
import org.estanciero.builder.EntityHandler;
import org.estanciero.dtos.GameDto;
import org.estanciero.model.action.*;
import org.estanciero.model.action.card_action.DrawSpecialCardAction;
import org.estanciero.model.action.jail_action.GoToJailAction;
import org.estanciero.model.action.jail_action.PayBailAction;
import org.estanciero.model.action.money_action.PayRentAction;
import org.estanciero.model.action.slot_action.SellSlotAction;
import org.estanciero.model.action.slot_action.UpgradeProvinceAction;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.player.bot_profiles.AgressiveBotPlayer;
import org.estanciero.model.entities.player.bot_profiles.ConservativeBotPlayer;
import org.estanciero.model.entities.player.bot_profiles.EquilibratedBotPlayer;
import org.estanciero.model.entities.slot.*;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardActionType;
import org.estanciero.model.entities.special_cards.SpecialCardType;
import org.estanciero.model.game.enums.GameDifficulty;
import org.estanciero.model.game.enums.GameMode;
import org.estanciero.services.implementation.SpecialCardService;
import org.estanciero.services.interfaces.IGame;
import org.estanciero.services.InformationService;


import java.util.*;

import static org.estanciero.services.InformationService.*;
@Getter
@Setter
public class Game implements IGame {
    //Clase encargada de obtener todos los datos necesarios para empezar una partida. Una vez obtenidos los datos
    //se inicía un new Round();
    //Atributos

    private int moneyGoal;
    private GameDifficulty gameDifficulty;
    private GameMode gameMode;
    private InformationService infoService = new InformationService();
    private static ArrayList<Player> players;

    @Getter
    private static List<Slot> slots;
    @Getter
    @Setter
    private static ArrayList<SpecialCard> luckCardDeck;
    @Getter
    @Setter
    private static ArrayList<SpecialCard> destinyCardDeck;
    private boolean shouldRepeatPlayerTurn;
    public boolean isFinished = false;
    private EntityHandler entityHandler;
    private SpecialCardService specialCardService;
    private boolean isNewGame;

    //Constructores
    public Game(int moneyGoal,ArrayList<Player> players, List<Slot> slots,
                ArrayList<SpecialCard> luckCardDeck, ArrayList<SpecialCard> destinyCardDeck, GameMode mode,
                GameDifficulty difficulty, EntityHandler entityHandler, boolean isNewGame) {

        this.moneyGoal = moneyGoal;
        this.players = players;
        this.slots = slots;
        this.luckCardDeck = luckCardDeck;
        this.destinyCardDeck = destinyCardDeck;
        this.gameMode = mode;
        this.gameDifficulty = difficulty;
        this.shouldRepeatPlayerTurn = false;
        this.entityHandler = entityHandler;
        this.specialCardService = new SpecialCardService();
        this.isNewGame = isNewGame;
    }



    public void start(){
        infoService.printSeparator(50);
        if (isNewGame) {
            infoService.print("Starting new game...");
        }
        infoService.print("Tip: Your name is shown in " + ANSI_BLUE +"blue" + ANSI_RESET + " and opponents' in " + ANSI_PURPLE + "purple" + ANSI_RESET);
        throwDiceForPlayerTurns();
        infoService.printSeparator(50);
        infoService.print("The game has begun!");


        while (!isFinished) {
            nextTurn();
        }

    }
    public void throwDiceForPlayerTurns() {
        if (isNewGame) {
            Map<Player, Integer> playerDiceMap = new HashMap<Player, Integer>();
            ArrayList<Integer> mapValuesList = new ArrayList<>();
            //LinkedHashMap<Player,Integer> sortedPlayerDiceMap = new LinkedHashMap<>();

            infoService.printSeparator(50);
            infoService.print("Players will now throw the dice to establish the turn order");
            for (Player player : players) {

                int diceValue = Dice.throwDice();
                playerDiceMap.put(player, diceValue);

                infoService.printHighlighted(player.getPlayerNameWithColor() +
                                " throws the dice and gets " + diceValue,
                        0, InformationService.ANSI_BLUE);

            }

            for (Map.Entry<Player, Integer> entry : playerDiceMap.entrySet()) {
                mapValuesList.add(entry.getValue());
            }

            Collections.sort(mapValuesList, Collections.reverseOrder());

            //sort map matching valueList that is already sorted
            //add players to list with the correct turn order
            players.clear();
            for (int value : mapValuesList) {
                for (Map.Entry<Player, Integer> entry : playerDiceMap.entrySet()) {
                    if (entry.getValue().equals(value)) {
                        players.add(entry.getKey());
                    }

                }
            }

            Player startingPlayer = players.get(0);
            startingPlayer.setPlayerTurn(true);
            infoService.printHighlighted(startingPlayer.getPlayerNameWithColor()
                    + " rolled the highest value and will be the first one to play", 0, InformationService.ANSI_BLUE);
        }
        else {
            setNextPlayerTurn(false);
        }
    }

    private void nextTurn() {
        this.shouldRepeatPlayerTurn = false;
        infoService.printSeparator(50);
        Player playerTurn = getPlayerTurn();

        infoService.print("--------- " + InformationService.ANSI_BLUE +
                infoService.getPlayerNameWithApostrophe(playerTurn.getPlayerNameWithColor()) +
                ANSI_RESET +" turn --------- ");

        int previousPlayerPosition = playerTurn.getPosition();
        infoService.print("Money: " + playerTurn.getMoney() + " | Current position: " + previousPlayerPosition);
        int[] playerDiceArray = Dice.throwAndGetFaces();
        infoService.printPlayerDice(playerTurn,playerDiceArray);


        int diceValue = Dice.getDiceValue(playerDiceArray);

        checkForConsecutiveRolls(playerTurn, playerDiceArray);

        // LANZAR LOS DADOS
        int nextPosition;
        // checkear si el jugador pasa por el inicio
        // 41 es el ultimo slot antes del inicio

        if (!playerTurn.isInJail()) {
            if (previousPlayerPosition + diceValue > 41 ){
                infoService.print(playerTurn.getPlayerNameWithColor() +
                        " passed through the start line slot, gets $5000 from the bank.");
                playerTurn.setMoney(playerTurn.getMoney() + 5000);
                infoService.print(playerTurn.getPlayerNameWithColor() + " now has $" + playerTurn.getMoney());

                // al restar 42 se obtiene la nueva posicion, en total son 42 slots
                nextPosition = previousPlayerPosition + diceValue - 42;
            }
            //si no pasa por el inicio
            else {
                nextPosition = previousPlayerPosition + diceValue;
            }

            Slot slot = slots.get(nextPosition);
            playerTurn.setPosition(nextPosition);

            infoService.print(playerTurn.getPlayerNameWithColor() +" falls in slot "+ nextPosition);

            handleTurnActions(slot, playerTurn, diceValue);


            //turno del jugador actual ha terminado
            setNextPlayerTurn(shouldRepeatPlayerTurn);

            Player nextPlayer = getPlayerTurn();
            System.out.println("The next turn is: "+ nextPlayer.getPlayerNameWithColor());

            checkForWinner();
        }
        else {
            Slot slot = slots.get(playerTurn.getPosition());
            handleTurnActions(slot, playerTurn, diceValue);
        }





    }
    public void checkForConsecutiveRolls(Player playerTurn, int[] playerDiceArray) {
        if (Dice.areFacesEqual(playerDiceArray)) {
            playerTurn.setConsecutiveRollCount(playerTurn.getConsecutiveRollCount() +1);
        }
    }

    public Player getPlayerTurn() {

        Player playerTurn = players.get(0);
        for (Player player : players) {
            if (player.isPlayerTurn()) {
                playerTurn = player;
                break;
            }
        }
        return playerTurn;
//        Player playerToReturn = players.get(0);
//        for (Player player : players) {
//            if (player instanceof HumanPlayer humanPlayer) {
//                playerToReturn = humanPlayer;
//            }
//        }
//        return playerToReturn;
    }

    public void setNextPlayerTurn(boolean shouldPlayerRepeatTurn) {
        if(!shouldPlayerRepeatTurn) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).isPlayerTurn()) {

                    try {
                        players.get(i + 1).setPlayerTurn(true);
                        players.get(i).setPlayerTurn(false);
                        break;
                    } catch (IndexOutOfBoundsException e) {
                        players.get(i).setPlayerTurn(false);
                        players.get(0).setPlayerTurn(true);
                        break;
                    }

                }
            }
        }
    }

    public static ArrayList<Player> getPlayers() {
        return players.isEmpty() ? null : players;
    }

    public static boolean doesPlayerHaveFullProvince(Player playerToCheck, ProvinceName provinceName) {
        int provinceAmount = 0;
        int provincesOwnedByPlayer = 0;

        for (Slot slot : slots ) {
            if (slot instanceof ProvinceSlot) {
                ProvinceSlot provinceSlot = (ProvinceSlot) slot;
                if (provinceSlot.getProvince() == provinceName) {
                    provinceAmount++;
                    if (provinceSlot.getOwner() == playerToCheck) {
                        provincesOwnedByPlayer++;
                    }
                }
            }
        }

        return provinceAmount == provincesOwnedByPlayer;
    }

    public void checkPlayerIsBankrupt(Player player) {

        if (player.getMoney() < 0) {
            player.setBankrupt(true);
            infoService.printSeparator(50);
            infoService.print(player.getPlayerNameWithColor() + " has been bankrupt and is now out of the game");
        }

    }

    public ArrayList<PlayerActionBase> checkForAutomaticActions(ArrayList<PlayerActionBase> actions) {

        ArrayList<PlayerActionBase> availableActions = new ArrayList<>();

        actions.forEach(action -> {
            if (action.isAutomatic) {
                action.executeAction();
                infoService.print(action.onExecuteMessage);
            }
            else {
                availableActions.add(action);
            }
        });

        return availableActions;
    }


    /*Action handlers*/

    public void handleTurnActions(Slot slot, Player playerTurn, int diceValue) {
        //si tira doble 3 veces seguidas va a la carcel
        if (playerTurn.getConsecutiveRollCount() == 3) {
            infoService.print(playerTurn.getPlayerNameWithColor() + " rolled equal faces 3 times in a row, they must go to jail!");
            GoToJailAction goToJailAction = new GoToJailAction(playerTurn);
            goToJailAction.executeAction();
            playerTurn.setConsecutiveRollCount(0);
        }

        ArrayList<PlayerActionBase> actions = slot.validateActionSlot(playerTurn);
        if (actions.isEmpty()) {
            actions = new ArrayList<>();
        }
        actions = addUpgradeProvinceActions(actions,playerTurn);


        if (playerTurn instanceof HumanPlayer) {
            handleHumanTurnActions(slot, playerTurn, diceValue,actions);
        }
        else {
            handleBotTurnActions(slot, playerTurn, diceValue, actions);
        }
    }

    private ArrayList<PlayerActionBase> addSellSlotActions(ArrayList<PlayerActionBase> actions, Player playerTurn) {
        ArrayList<PlayerActionBase> updatedActions = actions;

        List<Slot> playerOwnedSlots = playerTurn.getOwnedSlots();
        if (playerOwnedSlots.size() > 0) {
            for (Slot slot : playerOwnedSlots) {
                PurchasableSlot purchasableSlot = (PurchasableSlot) slot;
                updatedActions.add(new SellSlotAction(purchasableSlot,playerTurn));
            }
        }

        return updatedActions;
    }

    public void handleHumanTurnActions(Slot slot, Player playerTurn, int diceValue, ArrayList<PlayerActionBase> actions) {

        if (playerTurn.isInJail()) {
            if (!handleHumanJail(playerTurn)) {
                return;
            }
        }

        //agregar opciones solo disponibles para el jugador humano
        actions = addSellSlotActions(actions,playerTurn);
        actions.add(new SkipTurnAction());
        actions.add(new SaveGameAction());
        actions.add(new ExitGameAction());


        actions = checkForAutomaticActions(actions);


        //en este punto las acciones automaticas fueron ejecutadas, solo quedan las acciones que puede
        //elejir el jugador

        //antes de darle la posibilidad de elegir una accion, verificamos que no haya quedado en bancarrota
        //si se ejecuto, por ejemplo,  una accion de pagar renta automaticamente, el jugador puede haber quedado sin dinero

        checkPlayerIsBankrupt(playerTurn);

        PlayerActionBase chosenAction = infoService.printActionsAndChoose(actions);


        //edge cases
        if (chosenAction instanceof DrawSpecialCardAction drawSpecialCardAction) {
            SpecialCard card = drawSpecialCardAction.drawSpecialCard();
            System.out.println("The card says : " + card.getCardMessage());
            specialCardService.executeCard(card,playerTurn);
        }

        else if (chosenAction instanceof RepeatTurnAction) {
            shouldRepeatPlayerTurn = true;
        }

        else if (chosenAction instanceof SaveGameAction saveGameAction) {
            GameDto gameDto = new GameDto(false,slots,luckCardDeck,destinyCardDeck,players,moneyGoal,
                    gameDifficulty,gameMode);

            saveGameAction.saveGame(gameDto, entityHandler);
            shouldRepeatPlayerTurn = true;
        }

        else if (chosenAction instanceof PayRentAction payRentAction && slot instanceof CompanySlot) {
            payRentAction.setDiceValueOptional(Optional.of(diceValue));
        }

        if (chosenAction instanceof ExitGameAction exitGameAction) {
            boolean wantExit = exitGameAction.askPlayerForExit();
            if (wantExit) {
                boolean wantSave = exitGameAction.askPlayerForSave();
                if (wantSave) {
                    SaveGameAction saveGameAction = new SaveGameAction();
                    GameDto gameDto = new GameDto(false,slots,luckCardDeck,destinyCardDeck,players,moneyGoal,
                            gameDifficulty,gameMode);

                    saveGameAction.saveGame(gameDto, entityHandler);
                    infoService.print(saveGameAction.onExecuteMessage);
                    infoService.print("Thanks for playing!");
                    infoService.print(exitGameAction.onExecuteMessage);
                    System.exit(-1);
                }
                else {
                    infoService.print("Thanks for playing!");
                    infoService.print(exitGameAction.onExecuteMessage);
                    System.exit(-1);
                }

            }
            else {
                shouldRepeatPlayerTurn = true;
            }
        }
        chosenAction.executeAction();
        infoService.print(chosenAction.onExecuteMessage);


        infoService.print(playerTurn.getPlayerNameWithColor() + " ends their turn with $"+playerTurn.getMoney());
        infoService.print(playerTurn.getPlayerNameWithColor() + " is now in position " + playerTurn.getPosition());
        checkPlayerIsBankrupt(playerTurn);
    }

    private ArrayList<PlayerActionBase> addUpgradeProvinceActions(ArrayList<PlayerActionBase> actions, Player playerTurn) {
        ArrayList<PlayerActionBase> updatedActions = actions;
        
        for (Slot slot : slots) {
            if (slot instanceof ProvinceSlot provinceSlot) {
                if (provinceSlot.getOwner() != null && provinceSlot.getOwner().equals(playerTurn)) {
                    if (provinceSlot.getRanchCount() < 5 && playerTurn.getMoney() >= provinceSlot.getRanchPrice()) {
                        updatedActions.add(new UpgradeProvinceAction(provinceSlot));
                    }
                }
            }
        }
        return updatedActions;
    }

    public boolean handleHumanJail(Player playerTurn) {
        //si el jugador se encuentra en la carcel

        infoService.printHighlighted("You are currently in jail," +
                " would you like to pay a $1000 bail to exit? (Y/N)" ,4,ANSI_CYAN);

        String answer = infoService.askForStringAndValidate(YES_NO_REGEX,"Please enter Y for Yes or N for No");

        if (answer.matches(YES_REGEX)) {
            PayBailAction payBailAction = new PayBailAction(playerTurn);
            payBailAction.executeAction();
            return true;
        }
        return false;
    }


    public void handleBotTurnActions(Slot slot, Player playerTurn, int diceValue, ArrayList<PlayerActionBase> actions) {

        if (playerTurn.isInJail()) {
            actions.add(new PayBailAction(playerTurn));
        }
        actions = checkForAutomaticActions(actions);

        if (actions.size() >0) {
            if (playerTurn instanceof EquilibratedBotPlayer equilibratedBotPlayer) {
                PlayerActionBase chosenAction = equilibratedBotPlayer.selectAction(actions);
                infoService.print(playerTurn.getPlayerNameWithColor() + " chooses " + chosenAction.displayMessage);
                //edge cases
                if (chosenAction instanceof DrawSpecialCardAction drawSpecialCardAction) {
                    SpecialCard card = drawSpecialCardAction.drawSpecialCard();
                    System.out.println("The card says : " + card.getCardMessage());
                    specialCardService.executeCard(card, playerTurn);
                } else if (chosenAction instanceof RepeatTurnAction) {
                    shouldRepeatPlayerTurn = true;
                } else if (chosenAction instanceof PayRentAction payRentAction && slot instanceof CompanySlot) {
                    payRentAction.setDiceValueOptional(Optional.of(diceValue));
                }
                chosenAction.executeAction();
                infoService.print(chosenAction.onExecuteMessage);

            }

            if (playerTurn instanceof ConservativeBotPlayer conservativeBotPlayer) {
                PlayerActionBase chosenAction = conservativeBotPlayer.selectAction(actions);
                infoService.print(playerTurn.getPlayerNameWithColor() + " chooses " + chosenAction.displayMessage);

                if (chosenAction instanceof DrawSpecialCardAction drawSpecialCardAction) {
                    SpecialCard card = drawSpecialCardAction.drawSpecialCard();
                    System.out.println("The card says : " + card.getCardMessage());
                    specialCardService.executeCard(card, playerTurn);
                } else if (chosenAction instanceof RepeatTurnAction) {
                    shouldRepeatPlayerTurn = true;
                }
                else if (chosenAction instanceof PayRentAction payRentAction && slot instanceof CompanySlot) {
                    payRentAction.setDiceValueOptional(Optional.of(diceValue));
                }
                chosenAction.executeAction();
                infoService.print(chosenAction.onExecuteMessage);

            }

            if (playerTurn instanceof AgressiveBotPlayer agressiveBotPlayer) {
                PlayerActionBase chosenAction = agressiveBotPlayer.selectAction(actions);
                infoService.print(playerTurn.getPlayerNameWithColor() + " chooses " + chosenAction.displayMessage);

                if (chosenAction instanceof DrawSpecialCardAction drawSpecialCardAction) {
                    SpecialCard card = drawSpecialCardAction.drawSpecialCard();
                    System.out.println("The card says : " + card.getCardMessage());
                    specialCardService.executeCard(card, playerTurn);
                } else if (chosenAction instanceof RepeatTurnAction) {
                    shouldRepeatPlayerTurn = true;
                }
                else if (chosenAction instanceof PayRentAction payRentAction && slot instanceof CompanySlot) {
                    payRentAction.setDiceValueOptional(Optional.of(diceValue));
                }
                chosenAction.executeAction();
                infoService.print(chosenAction.onExecuteMessage);
            }

            infoService.print(playerTurn.getPlayerNameWithColor() + " ends their turn with $"+playerTurn.getMoney());
            infoService.print(playerTurn.getPlayerNameWithColor() + " is now in position " + playerTurn.getPosition());
            checkPlayerIsBankrupt(playerTurn);
        }

    }

    /*
    Check for winner
     */
    public void checkForWinner() {
        Player winner;
        if (gameMode == GameMode.MONEY_GOAL){
            winner = checkForWinnerMoneyGoal();
        }
        else {
            winner = checkForWinnerBankrupt();
        }
        if (winner != null) {
            printEndOfGame(winner);
        }
    }

    public Player checkForWinnerMoneyGoal() {
        for (Player player : players) {
            if (player.getMoney() >= moneyGoal) {
                infoService.printSeparator(50);
                infoService.print(player.getPlayerNameWithColor() + " has won the match! - Money goal reached");
                isFinished = true;
                return player;
            }
        }
        return null;
    }

    public Player checkForWinnerBankrupt() {
        List<Player> filteredPlayers =
                players.stream().filter(Player::isBankrupt).toList();

        if (filteredPlayers.size() == 1) {
            //if only 1 player is not bankrupt => winner
            Player winner = filteredPlayers.get(0);
            infoService.printSeparator(50);
            infoService.print(winner.getPlayerNameWithColor() + " has won the match! - They're the only player " +
                    "that is not bankrupt");

            isFinished = true;
            return winner;
        }
        return null;
    }

    public void printEndOfGame(Player winner) {
        infoService.printSeparator(50);
        infoService.print("The game is over, thanks for playing!");
        infoService.printSeparator(50);
        infoService.print("Statistics: ");
        infoService.print("Winner: " + winner.getName());
        infoService.print("Money: " + winner.getMoney());
        infoService.print("Companies owned: " + winner.getCompanyCount());
        infoService.print("Railways owned: " + winner.getRailwayCount());
        infoService.print("Provinces owned: " + winner.getProvinceCount());

    }

}


