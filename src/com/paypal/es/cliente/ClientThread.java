package com.paypal.es.cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread {
	private Socket socket;
	private BufferedReader entrada;
	private PrintWriter salida;
	private static int counter = 0;
	private int id = counter++;
	private static int threadsCount = 0;
	private static int PORT = 8080;

	public static int getThreadsCount() {
		return threadsCount;
	}

	public ClientThread(InetAddress addr) {
		threadsCount++;
		try {
			socket = new Socket(addr, ClientThread.PORT);
		} catch (IOException e) {
			System.err.println("The socket failed");
		}
		try {
			entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			start();
		} catch (IOException e) {
			// If any other error occurs, the socket must be closed:
			try {
				socket.close();
			} catch (IOException e2) {
				System.err.println("Socket not closed");
			}
		}
	}

	public void run() {
		try {
			while (true) {
				System.out.println("Bienvenid@ a Paypal por favor ponga un Nombre para su cuenta");
				Scanner lector = new Scanner(System.in);
				String nombre = lector.nextLine();
				salida.println("INICIAR");
				String mensajeOk = entrada.readLine();
				if (mensajeOk.equals("OK")) {
					salida.println(nombre);
					String mensajeBienvenida = entrada.readLine();
					System.out.println(mensajeBienvenida);
					int opcionNum = 0;
					while (opcionNum != 4) {
						System.out.println("Eliga una de las opciones a operar (Ponga el número solo)");
						System.out.println();
						System.out.println("1.Ingresar");
						System.out.println("2.Retirar");
						System.out.println("3.Último movimiento");
						System.out.println("4.Salir");
						String opcion = lector.nextLine();
						try {
							opcionNum = Integer.parseInt(opcion);

							if (opcionNum >= 1 && opcionNum <= 4) {
								if (opcionNum == 1) {
									salida.println("ING");
									String valor = entrada.readLine();
									if (valor.equals("OK")) {
										Boolean correcto = false;
										int cantidadNum = 0;
										while (correcto != true) {
											System.out.println("Ingrese la cantidad a Ingresar en su cuenta Paypal");
											String cantidad = lector.nextLine();
											try {
												cantidadNum = Integer.parseInt(cantidad);
												correcto = true;
											} catch (NumberFormatException excepcion) {
												System.out.println("Por favor ponga un numero");
											}
										}
										salida.println(String.valueOf(cantidadNum));
										String mensajeRecibido = entrada.readLine();
										System.out.println(mensajeRecibido);
									} else {
										System.out.println(
												"No se ha podido conectar con el servidor intentalo más tarde");
									}
								}
								if (opcionNum == 2) {
									salida.println("RET");
									String valor = entrada.readLine();
									if (valor.equals("OK")) {
										Boolean correcto = false;
										int cantidadNum = 0;
										while (correcto != true) {
											System.out.println("Ingrese la cantidad a Ingresar en su cuenta Paypal");
											String cantidad = lector.nextLine();
											try {
												cantidadNum = Integer.parseInt(cantidad);
												correcto = true;
											} catch (NumberFormatException excepcion) {
												System.out.println("Por favor ponga un numero");
											}
										}
										salida.println(String.valueOf(cantidadNum));
										String mensajeRecibido = entrada.readLine();
										System.out.println(mensajeRecibido);
									} else {
										System.out.println(
												"No se ha podido conectar con el servidor intentalo más tarde");
									}
								}
								if (opcionNum == 3) {
									salida.println("UM");
									String mensajeRecibido = entrada.readLine();
									System.out.println(mensajeRecibido);
								}
								if (opcionNum == 4) {
									salida.println("SALIR");
									String mensaje = entrada.readLine();
									salida.println("OK");
									String mensaje2 = entrada.readLine();
									System.out.println(mensaje);
									System.out.println(mensaje2);
									break;
								}
							} else {
								System.out.println("Por favor eliga un numero entre 1 y 4 ambos incluidos");
							}
						} catch (NumberFormatException excepcion) {
							System.out.println("Por favor ponga un numero");
						}
					}
				} else {
					System.out.println("Error no se ha podido iniciar el arranque del servidor");
				}
				break;
			}
		} catch (IOException e) {
			System.err.println("I/O Exception");
		} finally {
			try {
				System.out.println("Cerrando sesión en el cajero");
				socket.close();
			} catch (IOException e) {
				System.err.println("Socket not closed");
				threadsCount--;
			}
		}
	}

}
