package me.tahnok.wesclock;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Network {

    private static final String IP_ADDRESS = "192.168.1.239";
    private static final int PORT = 80;

    private Delegate delegate;

    public Network(Delegate delegate) {
        this.delegate = delegate;
    }

    public void sendCommand(Command command) {
        try {
            InetAddress serverAddress = InetAddress.getByName(IP_ADDRESS);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(serverAddress, PORT), 1000);
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.print(command.command);
            out.flush();
            out.close();
            socket.close();
        } catch (UnknownHostException e) {
            delegate.logError(e, "host not found");
        } catch (IOException e) {
            delegate.logError(e, "IO exception");
        }
    }

    public enum Command {
        TURN_ON("T1"),
        TURN_OFF("T0");

        String command;
        Command(String command) { this.command = command; }
    }


    public interface Delegate {
        void logError(Exception e, String message);
    }
}
