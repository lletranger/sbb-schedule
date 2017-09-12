package org.tsys.sbb.util;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.tsys.sbb.controller.ScheduleController;
import org.tsys.sbb.controller.StationController;
import org.tsys.sbb.dto.BoardDto;
import org.tsys.sbb.dto.StationDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelegramBot extends TelegramLongPollingBot {

    private String s = "";
    private StationController stationController = new StationController();
    private ScheduleController scheduleController = new ScheduleController();

    private List<String> names = new ArrayList<>();
    private Map<String, Integer> map = new HashMap<>();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "TsysMeR_bot";
    }

    @Override
    public String getBotToken() {
        return "366837843:AAHWiSZjT6ZZX8R1LrGkdGmOx5z06U-nQOs";
    }

    @Override
    public void onUpdateReceived(Update update) {

        System.out.println("Bot got a message");

        Message message = update.getMessage();

        if (message != null && message.hasText()) {

            s = message.getText().replace("/", "");

            if (s.equals("start")) {
                sendMsg(message, "Hello from Middle-Earth Railroads. Seems like a nice day today!\nYou can get list of /stations we are servicing today");

            } else if (s.equals("stations")) {

                List<StationDto> dtos = stationController.getStations().getStations();
                names.clear();

                for (StationDto stationDto : dtos) {
                    names.add(stationDto.getName());
                    map.put(stationDto.getName(), stationDto.getId());
                }

                StringBuilder stations = new StringBuilder("");
                for (String name : names) {
                    stations.append("/".concat(name).concat("\n"));
                }

                sendMsg(message, "Stations we go to are:\n\n".concat(stations.toString()).concat("\nYou can request departing schedule for any of it by sending its name back to me"));

            } else if (map.containsKey(s)) {

                scheduleController.setId(map.get(s));
                StringBuilder schedule = new StringBuilder("");

                for (BoardDto dto : scheduleController.getScheduleDto().getFrom()) {
                    schedule.append(dto.toString().concat("\n"));
                }

                sendMsg(message, "Boards from ".concat(s)
                        .concat(" today are:\n").concat(schedule.toString())
                        .concat("\n Back to /stations"));
            } else {
                sendMsg(message, "Sorry, I don't understand !\n http://i.giphy.com/chICfOgH8ib16.gif\n");
            }
        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
