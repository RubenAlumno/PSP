
package com.paypal.es.server;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
	int cuenta = 0;
	String ultimoMovimiento = "";
	String usuarioUltimoMovimiento = "";
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    public ClientHandler(Socket s)
            throws IOException {
        socket = s;
        entrada
                = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        salida
                = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream())),
                                            true);
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
            	String iniciarServer = entrada.readLine();
				if(iniciarServer.equals("INICIAR")) {
					salida.println("OK");
					String nombre = entrada.readLine();
					String opcion = "ENTRAR";
					salida.println("Bienvenid@ "+nombre+" ¿Qué desea hacer?");
					while (opcion!="SALIR") {
						opcion = "";
						opcion = entrada.readLine();
						if(opcion.equals("ING")) {
							salida.println("OK");
							String cantidad = entrada.readLine();
							int cantidadNum = Integer.parseInt(cantidad);
							cuenta = cuenta + cantidadNum;
							usuarioUltimoMovimiento = nombre;
							ultimoMovimiento = " realizó un/a INGRESO de "+cantidad+". SALDO ACTUAL: ";
							salida.println("Operación correcta, actualmente tiene: "+String.valueOf(cuenta)+" € en su cuenta Paypal");
						}
						if(opcion.equals("RET")) {
							salida.println("OK");
							String cantidad = entrada.readLine();
							int cantidadNum = Integer.parseInt(cantidad);
							String mensaje = "";
							if(cuenta<cantidadNum) {
								mensaje = "No se ha efectuado la retirada. No hay saldo suficiente en la cuenta.";
								salida.println(mensaje);
							}else {
								usuarioUltimoMovimiento = nombre;
								ultimoMovimiento = " realizó un/a RETIRADA de "+cantidad+". SALDO ACTUAL: ";
								cuenta = cuenta - cantidadNum;
								mensaje = "Intentando retirar de su cuenta bancaria: "+cantidad+"€. Su retirada ha sido exictosa, toma tus "+cantidad+"€. Vuelve cuando quieras. Estado de la cuenta actualmente: "+String.valueOf(cuenta)+"€";
								salida.println(mensaje);
							}
						}
						if(opcion.equals("UM")) {
							salida.println(usuarioUltimoMovimiento+ultimoMovimiento+String.valueOf(cuenta)+"€");
						}
						if(opcion.equals("SALIR")) {
							break;
						}
					}
					salida.println("Hasta la próxima "+nombre);
					String okMensajeCliente = entrada.readLine();
					if(okMensajeCliente.equals("OK")) {
						salida.println("SALIR-OK");
					}
					break;
				}else {
					salida.println("SESION-NO-INICIADA");
				}
            }
            System.out.println("Closing...");
        } catch (IOException e) {
            System.err.println("I/O Exception");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }
}
