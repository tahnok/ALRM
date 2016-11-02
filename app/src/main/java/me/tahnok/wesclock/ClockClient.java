package me.tahnok.wesclock;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClockClient {

    private String ipAddress;
    private int port;

    private Delegate delegate;

    public ClockClient(String ipAddress, int port, Delegate delegate) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.delegate = delegate;
    }

    public ClockClient(SettingsInterface settings, Delegate delegate) {
        this(settings.getIpdAddress(), settings.getPort(), delegate);
    }

    public void sendCommand(Command command) {
        try {
            InetAddress serverAddress = InetAddress.getByName(ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(serverAddress, port), 1000);
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
