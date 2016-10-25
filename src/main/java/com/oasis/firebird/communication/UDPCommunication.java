package com.oasis.firebird.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPCommunication {

	private String remoteUrl;
	private Integer remotePort;
	private Integer serverPort;

	private ExecutorService executorService;
	private DatagramSocket socket;
	private boolean active = false;

	public UDPCommunication(String remoteUrl, Integer remotePort, Integer serverPort) {
		super();
		this.remoteUrl = remoteUrl;
		this.remotePort = remotePort;
		this.serverPort = serverPort;

		executorService = Executors.newCachedThreadPool();

	}

	public void startServer(final UDPReceiver receiver) {

		executorService.execute(new Runnable() {

			@Override
			public void run() {
				
				try {
					
					active = true;
					
					socket = new DatagramSocket(serverPort);
					
					byte[] receiveData = null;
					
					while (active) {
						
						receiveData = new byte[512];
						
						DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
						socket.receive(receivePacket);

						receiver.onPacketReceived(new String(receivePacket.getData()));
						
					}
					
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				System.out.println("Server stopped");

			}
		});
		
		System.out.println("Server running on with remote url " + remoteUrl);

	}
	
	public void stopServer() {
		
		socket.close();
		active = false;
		
	}

	public void sendPacket(final String payload, final ErrorHandler errorHandler) {

		executorService.execute(new Runnable() {

			@Override
			public void run() {

				try {
					
					InetAddress IPAddress = InetAddress.getByName(remoteUrl);

					byte[] sendData = payload.getBytes();
					
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, remotePort);
					socket.send(sendPacket);
					
					System.out.println("SENT UDP: " + payload);

				} catch (SocketException e) {
					e.printStackTrace();
					errorHandler.onError("The network could not be reached. Please make sure you have access to the internet and try again.");
				} catch (UnknownHostException e) {
					e.printStackTrace();
					errorHandler.onError("The server IP could not be resolved. Please make sure you have entered the correct address.");
				} catch (IOException e) {
					e.printStackTrace();
					errorHandler.onError("Failed to send packet. Please again and if the problem persists contact your administrator.");
				}

			}
		});

	}

	public interface ErrorHandler {
		
		void onError(String message);
		
	}
	
	public interface UDPReceiver extends ErrorHandler {

		void onPacketReceived(String payload);

	}

}
