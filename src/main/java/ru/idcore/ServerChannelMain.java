package ru.idcore;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ServerChannelMain {
    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 17117));
        System.out.println("Server is running...");
        while (true) {
            try (SocketChannel socketChannel = serverSocketChannel.accept()) {
                final ByteBuffer buffer = ByteBuffer.allocate(2 << 10);

                while (socketChannel.isConnected()) {
                    int byteCount = socketChannel.read(buffer);

                    if (byteCount == -1) {
                        break;
                    }

                    final String line = new String(buffer.array(), 0, byteCount, StandardCharsets.UTF_8);
                    buffer.clear();
                    if (!line.equals("end")) {
                        System.out.println("CLIENT: " + socketChannel.getRemoteAddress() + " - " + line);
                        socketChannel.write(ByteBuffer.wrap((Trimmer.extraTrimString(line)).getBytes(StandardCharsets.UTF_8)));
                    } else {
                        break;
                    }
                }
                System.out.println("Server is stopped...");
                break;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
