package org.estanciero;

import org.estanciero.builder.EntityHandler;
import org.estanciero.dtos.GameDto;
import org.estanciero.model.game.GameInitializer;
import org.estanciero.services.InformationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import static org.estanciero.services.InformationService.YES_REGEX;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{

    public static void main( String[] args )
    {
        ApplicationContext context = SpringApplication.run(App.class, args);
        EntityHandler handler = context.getBean(EntityHandler.class);
        InformationService infoService = new InformationService();

        if (handler.thereIsAnotherGame()) {
            infoService.print("A previous unfinished game exists, would you like to continue? (Y for Yes/ N for No)");
            String answer = infoService.askForStringAndValidate(InformationService.YES_NO_REGEX,"Please enter Y for Yes or N for no");
            if (answer.matches(YES_REGEX)) {
                GameDto gameDto = handler.getGameDto(false);
                gameDto.setNewGame(false);
                GameInitializer initializer = new GameInitializer(gameDto,handler);
                infoService.print("Loading previous game...");
                initializer.init();
            }
            else {
                GameDto gameDto = handler.getGameDto(true);
                gameDto.setNewGame(true);
                infoService.print("Starting new game...");
                GameInitializer initializer = new GameInitializer(gameDto, handler);
                initializer.init();

            }
        }
        else {
            GameDto gameDto = handler.getGameDto(true);
            gameDto.setNewGame(true);
            GameInitializer initializer = new GameInitializer(gameDto, handler);
            initializer.init();

        }

        //GameDto gameDto = handler.getGameDto(true);
        //boolean success = handler.saveGame(game);


    }
}
