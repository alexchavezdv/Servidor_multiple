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
 * @author 
 * ALEXANDER EDUARDO CHAVEZ GARCIA 
 * DAVID TADEO ROBLES LARA
 */
public class Servidor_multiple {

    /**
     * @param args the command line arguments
     * 
     * 
     */
       /*
      Instrucciones para ejecutar el codigo  
    
    1. En la primer maquina crear un folder que se llame  books y tener el archivo con las urls 
    2. En la segunda maquina crear un folder que se llame books y tener el archivo de las stop words
    3. En la primer maquina corremos la clase servidor_multiple
    4. En la segunda maquina corremos Cliente_multiple2 y Cliente_multiple3 con los argumentos books txt english_sw.txt 
       y agregamos la ip del servidor al que se conectara
    5. Despues en la primer maquina corremos Cliente_multiple que sera el cliente que hara las peticiones
    Una vez que corramos todas las clases nos mostrara los resultados en la clase Cliente_multiple con el tiempo
    total de ejecucion
    
     */
    public static boolean ciclo = true;
    public static int numeroClientes = 1;
    public static String password = "123456";

    public static void main(String[] args) {
        ArrayList<ServerThread> threadList = new ArrayList<>();
        try (ServerSocket serversocket = new ServerSocket(5000)) {
            InetAddress localIP = InetAddress.getLocalHost();
            System.out.println("Server IP" + localIP);
            while (ciclo) {
                Socket socket = serversocket.accept();
                String nombre="Cliente"+String.valueOf(numeroClientes);
                ServerThread serverThread  = new ServerThread(socket, threadList,nombre);
                threadList.add(serverThread);
                serverThread.start();
                numeroClientes++;
            }
        } catch (Exception e) {
            System.out.println("Main. Error:" + e.getMessage());
        }

    }

    public void setCiclo(boolean valor) {
        this.ciclo = valor;
    }

}
