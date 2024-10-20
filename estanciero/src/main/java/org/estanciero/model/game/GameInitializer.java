package org.estanciero.model.game;

import lombok.Data;
import org.estanciero.builder.EntityHandler;
import org.estanciero.dtos.GameDto;
import org.estanciero.model.entities.player.BotPlayer;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.player.bot_profiles.AgressiveBotPlayer;
import org.estanciero.model.entities.player.bot_profiles.ConservativeBotPlayer;
import org.estanciero.model.entities.player.bot_profiles.EquilibratedBotPlayer;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.estanciero.model.entities.slot.Slot;
import org.estanciero.model.game.enums.GameDifficulty;
import org.estanciero.model.game.enums.GameMode;
import org.estanciero.services.interfaces.IGameInitializer;
import org.estanciero.services.InformationService;


import java.util.ArrayList;
import java.util.List;

@Data
public class GameInitializer implements IGameInitializer {
    //clase que maneja todos los datos necesarios para la creacion de una nueva partida (new Game())


    //props
    //dificultad y modo de juego
    private GameDifficulty difficulty;
    private GameMode mode;
    private InformationService infoService = new InformationService();
    //lista de players con la que se inicia el juego
    private ArrayList<Player> players;
    //slots
    private List<Slot> slots;
    private ArrayList<SpecialCard> luckCardDeck;
    private ArrayList<SpecialCard> destinyCardDeck;
    private int moneyGoal;
    private GameDto gameDto;
    private EntityHandler entityHandler;

    public GameInitializer(GameDto gameDTO, EntityHandler entityHandler) {
        moneyGoal = 0;
        players = new ArrayList<>();
        this.entityHandler = entityHandler;
        this.gameDto = gameDTO;
    }

    public void init() {
        infoService.printWelcomeMessage();
        infoService.printSeparator(40);
        infoService.print("Initializing game...");
        infoService.print("Tip: Expanding the console interface will allow increased visibility.");

        if (gameDto.isNewGame()) {
            initNewGame();
        }
        else {
            loadPreviousGame();
        }

    }

    public void initNewGame() {
        //pedir al usuario dificultad y modo de juego
        difficulty = askPlayerForDifficulty();
        mode = askPlayerForGameMode();
        //si el modo de juego es money goal, preguntar con cuanto dinero se gana

        if (mode ==GameMode.MONEY_GOAL) {
            moneyGoal = askPlayerForMoneyGoal();
        }


        createPlayers();
        //mapear datos obtenidos de base de datos incluidos en DTO
        slots = gameDto.getSlotList();
        luckCardDeck = (ArrayList<SpecialCard>) gameDto.getLuckCards();
        destinyCardDeck = (ArrayList<SpecialCard>) gameDto.getDestinyCards();


        Game game = new Game(moneyGoal, players,slots,luckCardDeck,destinyCardDeck, mode, difficulty, entityHandler, gameDto.isNewGame());
        game.start();

    }

    public void loadPreviousGame() {
        difficulty = gameDto.getGameDifficulty();
        mode = gameDto.getGameMode();
        players = (ArrayList<Player>) gameDto.getPlayerList();
        slots = gameDto.getSlotList();
        luckCardDeck = (ArrayList<SpecialCard>) gameDto.getLuckCards();
        destinyCardDeck = (ArrayList<SpecialCard>) gameDto.getDestinyCards();
        moneyGoal = gameDto.getMoneyGoal();

        Game game = new Game(moneyGoal,players,slots,luckCardDeck,destinyCardDeck,mode, difficulty, entityHandler, gameDto.isNewGame());
        game.start();
    }

    public GameDifficulty askPlayerForDifficulty() {
        infoService.printHighlighted("Please choose the game difficulty",4, InformationService.ANSI_CYAN);
        infoService.print(InformationService.ANSI_GREEN + "1. Easy"
                + InformationService.ANSI_YELLOW +"  2. Medium"
                + InformationService.ANSI_RED +"  3. Hard"
                + InformationService.ANSI_RESET);
        int difficulty = Integer.parseInt(infoService.askForStringAndValidate("[1-3]", "Must choose a value between 1 and 3"));
        return switch (difficulty) {
            //esto es una expresion lambda
            //https://www.javacodegeeks.com/2020/05/switch-as-an-expression-in-java-with-lambda-like-syntax.html
            case 1 -> GameDifficulty.EASY;
            case 2 -> GameDifficulty.MEDIUM;
            case 3 -> GameDifficulty.HARD;
            default -> throw new IllegalArgumentException("Invalid difficulty level");
        };
    }
    public GameMode askPlayerForGameMode() {
        GameMode mode = GameMode.MONEY_GOAL;
        infoService.printSeparator(30);
        infoService.printHighlighted("Please choose the game mode",4, InformationService.ANSI_CYAN);
        infoService.print(InformationService.ANSI_GREEN + "1. Money Goal"
                + InformationService.ANSI_YELLOW +"  2. Last Player to go Bankrupt"
                + InformationService.ANSI_RESET);
        int input = Integer.parseInt(infoService.askForStringAndValidate("[1-2]", "You must choose a valid game mode"));
        return switch(input) {

            case 1 -> GameMode.MONEY_GOAL;
            case 2 -> GameMode.LASTPLAYER;
            default -> throw new IllegalArgumentException("Invalid game mode");
        };

    }

    public int askPlayerForMoneyGoal() {
        infoService.printHighlighted("Please enter a money goal", 3, InformationService.ANSI_GREEN);
        return infoService.askForNumberBetweenRange(40000,200000);
    }




    public void createPlayers() {
//
        HumanPlayer human = createNewHumanPlayer();
        human.setId(1);
        players.add(human);
        switch (difficulty) {
            case EASY -> {
                //dificultad facil
                EquilibratedBotPlayer equilibratedBotPlayer = new EquilibratedBotPlayer("Joseph",BotPlayStyle.EQUILIBRATED);
                equilibratedBotPlayer.setId(2);
                players.add(equilibratedBotPlayer);
                ConservativeBotPlayer conservativeBotPlayer = new ConservativeBotPlayer("John",BotPlayStyle.CONSERVATIVE);
                conservativeBotPlayer.setId(3);
                players.add(conservativeBotPlayer);


            }
            case MEDIUM -> {
                //dificultad media
                AgressiveBotPlayer agressiveBotPlayer = new AgressiveBotPlayer("Mike",BotPlayStyle.AGGRESSIVE);
                agressiveBotPlayer.setId(2);
                players.add(agressiveBotPlayer);

                ConservativeBotPlayer conservativeBotPlayer = new ConservativeBotPlayer("Donald",BotPlayStyle.CONSERVATIVE);
                conservativeBotPlayer.setId(3);
                players.add(conservativeBotPlayer);

                EquilibratedBotPlayer equilibratedBotPlayer = new EquilibratedBotPlayer("Allison",BotPlayStyle.EQUILIBRATED);
                equilibratedBotPlayer.setId(4);
                players.add(equilibratedBotPlayer);

            }
            case HARD -> {
                AgressiveBotPlayer agressiveBotPlayer = new AgressiveBotPlayer("John",BotPlayStyle.AGGRESSIVE);
                agressiveBotPlayer.setId(2);
                players.add(agressiveBotPlayer);

                EquilibratedBotPlayer equilibratedBotPlayer1 = new EquilibratedBotPlayer("James",BotPlayStyle.EQUILIBRATED);
                equilibratedBotPlayer1.setId(3);
                players.add(equilibratedBotPlayer1);

                EquilibratedBotPlayer equilibratedBotPlayer2 = new EquilibratedBotPlayer("William",BotPlayStyle.EQUILIBRATED);
                equilibratedBotPlayer2.setId(4);
                players.add(equilibratedBotPlayer2);

                ConservativeBotPlayer conservativeBotPlayer = new ConservativeBotPlayer("Jane",BotPlayStyle.CONSERVATIVE);
                conservativeBotPlayer.setId(5);
                players.add(conservativeBotPlayer);

            }
        }

    }
    public HumanPlayer createNewHumanPlayer() {
        infoService.printSeparator(50);
        infoService.printHighlighted("Please enter your name",3, InformationService.ANSI_BLUE);
        String name = infoService.askForStringAndValidate(InformationService.REGEX_NON_EMPTY,"Name field can't be left empty");
        return new HumanPlayer(name);

    }






}
