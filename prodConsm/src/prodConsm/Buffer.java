package prodConsm;


public class Buffer 
{
	private double [] buffer;
	private int capacity; 
	private int firstItem; 
	private int lastItem; 
	private int fullness;
	
	Buffer(int capacity)
	{
		buffer = new double[capacity];
		this.capacity = capacity;
		firstItem = 0;	
		lastItem = 0;
		fullness = 0;
	}
	
	public boolean insert(double item) 
	{
		boolean success;
		try 
		{
			Main.mutex.acquire();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}	
		
		if(lastItem< capacity) 
		{
			buffer[lastItem] = item;
			lastItem++;
			fullness++;	
			success = true;
		}
		else
		{
			success=false;
		}
		
		if(lastItem>=capacity)
		{
			lastItem=0;
			Main.fullBuffer.release(capacity);
		}
		Main.mutex.release();
		return success;	
	}
	
	public Double withdraw() 
	{
		try 
		{
			Main.mutex.acquire();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		Double result=null;			
		if(fullness > 0) 		
		{
			result = buffer[firstItem];
			firstItem++;
			fullness--;
			if(firstItem>=capacity)
			{
				firstItem = 0;
			}
		}		
		if(fullness==0) 
		{
			Main.emptyBuffer.release(capacity);
		}
		Main.mutex.release();
		return result;		
	}
}
