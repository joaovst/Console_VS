package prodConsm;

import java.util.Random;


public class Producer implements Runnable
{
	private Buffer buffer;
	int id;
	
	Producer(Buffer buffer, int id)
	{
		this.buffer=buffer;
		this.id=id;
	}
	
	public void produce() 
	{
		try 
		{
			Main.emptyBuffer.acquire();
		} 
		catch (InterruptedException e1) 
		{
			e1.printStackTrace();
		}
		double produced = new Random().nextDouble();
		boolean success = buffer.insert(produced);
		if(success)
		{
			System.out.println("Thread "+id+" produziu "+produced+" com sucesso");				
		}
		else
		{
			System.out.println("Thread "+id+" produziu "+produced+" e o buffer estava cheio");
		}
		try
		{
			Thread.sleep(1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	public void run() 
	{
		while(!Main.isFinished) 
		{
			this.produce();
		}
		System.out.println("O Produtor "+id+" acabou");
	}

}
