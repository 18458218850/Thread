package com.zuokai.thread0427;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {
	
	public static void main(String[] args) {
		//ForkJoinPool 继承了AbstractExecutorService,AbstractExecutorService实现了ExecutorService
		//ExecutorService.submit()提交一个返回值的任务用于执行，返回一个表示任务的未决结果的 Future。
		ForkJoinPool fjp = new ForkJoinPool();
		CountTask ct = new CountTask(1,100);
		//异步执行任务，并立即返回一个Future 对象
		Future<Integer> f = fjp.submit(ct);
		try {
			//得到结果,会阻塞，等待任务执行完成
			System.out.println("结果=="+f.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("main end");
	}

}

//继承RecursiveTask<Object>类用于有返回值的任务
class CountTask extends RecursiveTask<Integer>{
	private static final long serialVersionUID = 1L;
	public static final int threshold = 2; //阈值 ，当前任务大于阈值时，会继续细分
    private int start;  
    private int end;  
    public CountTask(int start, int end) {  
        this.start = start;  
        this.end = end;  
    }  
  
    @Override  
    protected Integer compute() {  
    	
        int sum = 0; 
        //如果任务足够小就计算任务  
        boolean canCompute = (end - start) <= threshold;  
        if (canCompute) {  
            for (int i = start; i <= end; i++) {  
                sum += i; 
            }  
        } else {  
            // 如果任务大于阈值，就分裂成两个子任务计算  
            int middle = (start + end) / 2;
            CountTask leftTask = new CountTask(start, middle);  
            CountTask rightTask = new CountTask(middle + 1, end);  
            // 执行子任务  
            leftTask.fork();  
            rightTask.fork();  
            //System.out.println(Thread.currentThread().getName());  
            //等待任务执行结束合并其结果  
            int leftResult = leftTask.join();  
            int rightResult = rightTask.join();  
            //合并子任务  
            sum = leftResult + rightResult;  
        }  
        return sum;  
    }  
	
}
