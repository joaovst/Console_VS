package prodConsm;


public class Consumer implements Runnable
{
	Buffer buffer;
	int id;
	Consumer(Buffer buffer, int id)
	{
		this.buffer=buffer;
		this.id=id;
	}
	
	public void consume()
	{
		try 
		{
			Main.fullBuffer.acquire();
		} 
		catch (InterruptedException e1) 
		{
			e1.printStackTrace();
		}
		Double consumed = buffer.withdraw();
		if(consumed != null)
		{
			System.out.println("O consumidor "+id+" consumiu "+consumed);			
		}
		else
		{
			System.out.println("O consumidor "+id+" falhou ao consumir");
		}
		try 
		{
			Thread.sleep(500);	
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
			consume();
		}
		System.out.println("O consumidor "+id +" acabou");
	}

}
