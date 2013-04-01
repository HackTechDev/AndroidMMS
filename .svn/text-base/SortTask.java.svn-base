import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

class SortTask extends RecursiveAction
{
	static AtomicInteger count = new AtomicInteger(1);
	final int tskid;
	final long[] array;
	final int lo;
	final int hi;
	public ForkJoinPool pool;
	
	private int THRESHOLD = 7; // For demo only

	public SortTask(long[] array)
	{
		this.array = array;
		this.lo = 0;
		this.hi = array.length - 1;
		tskid = count.getAndIncrement();
	}

	public SortTask(long[] array, int lo, int hi)
	{
		this.array = array;
		this.lo = lo;
		this.hi = hi;
		tskid = count.getAndIncrement();
	}

	protected void compute()
	{
		if (hi - lo < THRESHOLD)
		{
			sequentiallySort(array, lo, hi);
//			super.
			try
			{
				System.out.println("tskid do =" + tskid+"==="+getQueuedTaskCount());
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
		{
			int pivot = partition(array, lo, hi);
			// System.out.println("\npivot = " + pivot + ", low = " + lo
			// + ", high = " + hi);
			// System.out.println("array" + Arrays.toString(array));
			SortTask t1 = new SortTask(array, lo, pivot - 1);
			SortTask t2 = new SortTask(array, pivot + 1, hi);
			t1.pool = pool;
			t2.pool = pool;
			System.out.println(tskid + " ===> [" + t1.tskid + "," + t2.tskid
					+ "]");
			invokeAll(t1, t2);
		}
	}

	private int partition(long[] array, int lo, int hi)
	{
		long x = array[hi];
		int i = lo - 1;
		for (int j = lo; j < hi; j++)
		{
			if (array[j] <= x)
			{
				i++;
				swap(array, i, j);
			}
		}
		swap(array, i + 1, hi);
		return i + 1;
	}

	private void swap(long[] array, int i, int j)
	{
		if (i != j)
		{
			long temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
	}

	private void sequentiallySort(long[] array, int lo, int hi)
	{
		Arrays.sort(array, lo, hi + 1);
	}
}
