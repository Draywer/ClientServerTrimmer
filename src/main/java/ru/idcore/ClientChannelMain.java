package ru.idcore;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientChannelMain {
    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("Localhost", 17117);
        final SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(socketAddress);

        try (Scanner scanner = new Scanner(System.in)) {
            final ByteBuffer buffer = ByteBuffer.allocate(2 << 10);

            String line;
            while (true) {
                System.out.println("Введите текст с большим количеством лишних пробелов:");
                line = scanner.nextLine();

                if (line.equals("end")) {
                    socketChannel.write(ByteBuffer.wrap(line.getBytes(StandardCharsets.UTF_8)));
                    break;
                }
                socketChannel.write(ByteBuffer.wrap(line.getBytes(StandardCharsets.UTF_8)));

                int byteReader = socketChannel.read(buffer);
                System.out.println("SERVER: " + new String(buffer.array(), 0, byteReader, StandardCharsets.UTF_8));
                buffer.clear();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
