package ml.pevgen.test.telegrambot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class SimpleBot extends TelegramLongPollingBot {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    /* Перегружаем метод интерфейса LongPollingBot
    Теперь при получении сообщения наш бот будет отвечать сообщением Hi!
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().getText().equals("/hello")) {
            logger.info("/hello message from user id {} && user name is {}",update.getMessage().getFrom().getId(),update.getMessage().getFrom().getUserName());
            try {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Hello " + update.getMessage().getFrom().getUserName());
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    // Геттеры, которые необходимы для наследования от TelegramLongPollingBot
    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }
}
