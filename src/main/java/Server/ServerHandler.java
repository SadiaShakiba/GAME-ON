package Server;

import utility.CreateSessionData;
import utility.Operation;
import utility.Person;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerHandler {
    static final HashMap<String, ServerSession> serverSessions = new HashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                Socket socket = serverSocket.accept();

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    Operation operation = (Operation) objectInputStream.readObject(); // What client wants to do
                    if (operation == Operation.CREATE_SESSION) {
                        CreateSessionData createSessionData = (CreateSessionData) objectInputStream.readObject();
                        ServerSession serverSession = new ServerSession(objectInputStream, objectOutputStream, createSessionData);
                        serverSessions.put(createSessionData.getPerson().getUuid(), serverSession);
                        new Thread((serverSession)).start();
                        break;
                    } else if (operation == Operation.JOIN_SESSION) {
                        String string = (String) objectInputStream.readObject();
                        Person person = (Person) objectInputStream.readObject();
                        if (serverSessions.containsKey(string)) {
                            serverSessions.get(string).addStreams(objectInputStream, objectOutputStream, person);
                            break;
                        } else {
                            objectOutputStream.writeObject(Operation.JOIN_SESSION_FAILED);
                        }
                    } else {
                        socket.close();
                    }
                }
            }
        } catch (IOException | ClassCastException | ClassNotFoundException | NullPointerException ex) {
            System.out.println("Error occurred in main in ServerHandler: " + ex.toString());
            ex.printStackTrace();
        }
    }
}