/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_multiple;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author alexnader eduardo chavez garcia
 */
public class ServerThread extends Thread {
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;
    private String nombreUsuario;
    private boolean superusuario;
    //atributo nombre de cliente
    
    public ServerThread(Socket socket, 
            ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;
        this.nombreUsuario = "";
        this.superusuario = false;
    }
    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
            output = 
             new PrintWriter(socket.getOutputStream(),true);
            String nombre_thread = input.readLine();
            this.currentThread().setName(nombre_thread);
            System.out.println("Nombre del hilo "+ Thread.currentThread().getName());
            while (true) {
                String outputString = input.readLine();
                //split outputString
                String[] splitted = outputString.split(" ");
                //if splitted[0] == privado
                if (splitted[0].equals("privado")) {
                    System.out.println("Mensaje privado a entregar");
                    System.out.println("De:"+nombre_thread+" a:"+splitted[1]);
                    //System.out.println("Mensaje privado: " + outputString);
                    //ciclar lista de threads y buscar el nombre
                    buscarUsuario(splitted);
                } else { 
                    if (splitted[0].equals("server")) {
                        if(splitted[1].equals("login")) {
                           // if (splitted[2].equals())
                        }
                    
                } else {
                    String message = ( "("+Thread.currentThread().getName()+")"+" mensaje: " );
                    if (outputString.equals("salir")) {
                        break;
                    }
                    printToAllClients(nombre_thread + " dice:"+
                            message + outputString);
                    System.out.println("Server recibió de:"+
                            nombre_thread + " :>" +
                            outputString);
                }
                }
            }
            
        } catch (Exception e) {
            System.out.println("ServerThread error:"+e.getMessage());
        }
    }
    private void buscarUsuario(String[] splitted) {
        //ciclamos
        String mensaje =  Thread.currentThread().getName().toString();
        StringBuilder output = new StringBuilder();
        for (int i = 2; i < splitted.length; i++) {
            output.append(splitted[i]+" ");
        }
        
        for (ServerThread serverThread : threadList) {
            if (serverThread.getName().equals(splitted[1])) {
                serverThread.output.println(mensaje + "(privado):" +output);
            }
        }
    }
    private void printToAllClients(String outputString) {
        for (ServerThread serverThread : threadList) {
            serverThread.output.println(outputString);
        }
    }
}
