package com.example.onlinegamingplatformaoop;
import utility.CreateSessionData;
import utility.Operation;
import utility.Person;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import static utility.UtilityMethods.showConfirmation;
import static utility.UtilityMethods.showError;

class ClientHandler {
    private String username;
    private String topField;
    private int selectedValue;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public ClientHandler(Socket socket) {
        if (socket != null) {
            try {
                this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                showError("Exception Occurred in ClientThread Constructor: " + ex.toString());
            }
            return;
        }
        showError("");
    }

    public String initiateSession() throws NumberFormatException {
        this.sendObject(Operation.CREATE_SESSION);
        CreateSessionData data = new CreateSessionData(this.username, Operation.CREATE_SESSION, this.selectedValue, Integer.parseInt(this.topField));
        this.sendObject(data);
        System.out.println(data.getPerson().getUuid());
        return data.getPerson().getUuid();
    }

    public Object[] joinSession(String id) throws ClassCastException {
        this.sendObject(Operation.JOIN_SESSION);
        this.sendObject(this.topField);
        Person person = new Person(this.username);
        this.sendObject(person);
        return (Object[]) this.readObject();
    }

    private void sendObject(Object object) {
        try {
            this.objectOutputStream.writeObject(object);
        } catch (IOException ex) {
            showError("Error Occurred in sendObject in ClientHandler: " + ex.toString());
        }
    }

    private Object readObject() {
        try {
            return this.objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            showError("Error Occurred in readObject in ClientHandler: " + ex.toString());
        }
        return null;
    }

    public boolean setValues(String username, String topField, String selectedValue) {
        this.username = username;
        this.topField = topField;
        try {
            this.selectedValue = Integer.parseInt(selectedValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public ArrayList<Object> startListeningOrders() {
        try {
            ArrayList<Object> data = (ArrayList<Object>) this.objectInputStream.readObject();
            if (data == null) {
                showError("");
            }
            return data;
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            showError("Error Occurred in startListeningOrders in ClientHandler: " + ex.toString());
            return null;
        }
    }

    public boolean receivePermission() {
        return (boolean) Objects.requireNonNull(this.readObject());
    }

    public void sendMyMove(String buttonId) {
        this.sendObject(buttonId);
    }

    public Object[] readMove() {
        return (Object[]) this.readObject();
    }

    public Object[] checkForWinner() {
        return (Object[]) this.readObject();
    }
}
