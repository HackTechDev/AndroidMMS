import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class SortTaskTest
{
	private static final int NARRAY = 100; // For demo only
	long[] array = new long[NARRAY];
	Random rand = new Random();

	@Before
	public void setUp()
	{
		for (int i = 0; i < array.length; i++)
		{
			array[i] = rand.nextLong() % 100; // For demo only
		}
		System.out.println("Initial Array: " + Arrays.toString(array));
	}

	@Test
	public void testSort() throws Exception
	{
		try
		{
			Thread.sleep(5*1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ForkJoinPool fjpool = new ForkJoinPool();
		SortTask sort = new SortTask(array);
		sort.pool = fjpool;
		
		fjpool.submit(sort);
		fjpool.shutdown();

		fjpool.awaitTermination(3000, TimeUnit.SECONDS);

		assertTrue(checkSorted(array));
	}

	boolean checkSorted(long[] a)
	{
		for (int i = 0; i < a.length - 1; i++)
		{
			if (a[i] > (a[i + 1]))
			{
				return false;
			}
		}
		return true;
	}
}
