package prodConsm;

import java.util.concurrent.Semaphore;

public class Main
{
	private static int bufferCapacity;
	private static Long finishTime;
	public static int consumers;
	public static int producers;
	public static boolean isFinished;
	public static Buffer buffer;
	public static Semaphore mutex;
	public static Semaphore fullBuffer;
	public static Semaphore emptyBuffer;

	public static void main(String[] args) 
	{
		bufferCapacity=8;
		isFinished = false;
		buffer = new Buffer(bufferCapacity);
		mutex = new Semaphore(1);
		if(args.length!=3)
		{
			System.out.println("Numero de parametros insuficiente");
			System.out.println("Entre com o tempo de execução, numero de produtores, numero de consumidores");
			return;
		}
		finishTime = Long.parseLong(args[0]);
		producers = Integer.parseInt(args[1]);
		consumers = Integer.parseInt(args[2]);		
		
		fullBuffer = new Semaphore(bufferCapacity);
		emptyBuffer = new Semaphore(bufferCapacity);
		try
		{
			fullBuffer.acquire(bufferCapacity);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
			
		for(int i = 0; i < producers;i++) 
		{
			Producer producer = new Producer(buffer,i);
			new Thread(producer).start();
		}
		for(int i = 0; i < consumers;i++) 
		{
			Consumer consumer = new Consumer(buffer,i);
			new Thread(consumer).start();
		}
		try {
		Thread.sleep(finishTime);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("A main thread finalizou");
		
		isFinished = true;
		return;
		

	}

}
