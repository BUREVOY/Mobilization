package ru.mirea.yudakovam.timeservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketUtils {
    //2 функции

    /**
     * BufferedReader для получения входящих данных
     * принимает объект сокет
     * Считываем поток
     * InputStreamReader преобразует приходящие байты в символы
     * Буферизируем(сохраняем) данные
     */
    public static BufferedReader getReader(Socket s) throws IOException {
        return (new BufferedReader(new InputStreamReader(s.getInputStream())));
    }
    /**
     * Makes a PrintWriter to send outgoing data. This PrintWriter will
     * automatically flush stream when println is called.
     * В примере не используется
     * извлекает выходной поток,
     * создаем PrintWriter, тру - это авто вызов flush,
     * гарантия что все будет отправлено
     */
    public static PrintWriter getWriter(Socket s) throws IOException {
        // Second argument of true means autoflush.
        return (new PrintWriter(s.getOutputStream(), true));
    }
}
