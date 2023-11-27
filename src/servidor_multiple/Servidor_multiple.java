/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_multiple;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author ALEXANDER EDUARDO CHAVEZ GARCIA
 */

/*
1. menajes privados
2.broalcast
3.salir de ciclo en server




*/
public class Servidor_multiple {

    /**
     * @param args the command line arguments
     */
    public static boolean ciclo = true;
    public static int clientes=0;
    public static String password = "123456";
    public static void main(String[] args) {
        ArrayList<ServerThread> threadList = new ArrayList<>();
        try (ServerSocket serversocket = new ServerSocket(5000)) {
            InetAddress localIP = InetAddress.getLocalHost();
            System.out.println("Server IP"+localIP);
            while(ciclo) {
                Socket socket = serversocket.accept();
                ServerThread serverThread = 
                        new ServerThread(socket, threadList);
                threadList.add(serverThread);
                serverThread.start();
            }
        } catch (Exception e) {
            System.out.println("Main. Error:"+e.getMessage());
        }
        
    }
    public void setCiclo(boolean valor) {
        this.ciclo = valor;
    }
    
}
