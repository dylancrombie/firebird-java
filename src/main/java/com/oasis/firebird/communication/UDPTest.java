package com.oasis.firebird.communication;

public class UDPTest {

	public static void main(String[] args) {
		
		UDPCommunication communication = new UDPCommunication("41.160.168.86", 30303, 30303);
		
		communication.startServer(new UDPCommunication.UDPReceiver() {
			
			@Override
			public void onPacketReceived(String payload) {
				
				System.out.println(payload);
				
			}

			@Override
			public void onError(String message) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		communication.sendPacket("00010010+27762721809    ", new UDPCommunication.ErrorHandler() {
			
			@Override
			public void onError(String message) {
				// TODO Auto-generated method stub
				
			}
		});

		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

//		communication.stopServer();
		
	}

}
