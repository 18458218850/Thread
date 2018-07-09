package com.queue;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列，队列中的元素需要实现Comparable和Delayed，存储元素时，可以设置在队列中存放的时间，
 * DelayQueue一般适用于定时任务和缓存系统设计
 * 
 * 场景：考试时间为120分钟，30分钟后才可交卷，当时间到了，或学生都交完卷了考试结束。
 *		这个场景中几个点需要注意：
 *		考试时间为120分钟，30分钟后才可交卷，初始化考生完成试卷时间最小应为30分钟
 *		对于能够在120分钟内交卷的考生，如何实现这些考生交卷
 *		对于120分钟内没有完成考试的考生，在120分钟考试时间到后需要让他们强制交卷
 *		在所有的考生都交完卷后，需要将控制线程关闭
 * @author lijh
 *
 */
public class MyDelayQueue2 {

    public static void main(String[] args) throws InterruptedException {
        int studentNumber = 20;
        CountDownLatch countDownLatch = new CountDownLatch(studentNumber);
        DelayQueue<Student> students = new DelayQueue<Student>();
        Random random = new Random();
        for (int i = 0; i < studentNumber; i++) {
            students.put(new Student("student"+(i+1), 30+random.nextInt(120),countDownLatch));
        }
        Thread teacherThread =new Thread(new Teacher(students)); 
        //初始化一个120分钟的Student对象，当超过120分钟，则会终止Teacher线程，强制交卷，
        //所以当Student对象的考试时间超过120分钟时，会强制交卷
        students.put(new EndExam(students, 120,countDownLatch,teacherThread));
        teacherThread.start();
        countDownLatch.await();
        System.out.println(" 考试时间到，全部交卷！");  
    }

}

class Student implements Runnable,Delayed{

    private String name;
    private long workTime;//
    private long submitTime;//延迟时间
    private boolean isForce = false;
    private CountDownLatch countDownLatch;
    
    public Student(){}
    
    public Student(String name,long workTime,CountDownLatch countDownLatch){
        this.name = name;
        this.workTime = workTime;
        this.submitTime = TimeUnit.NANOSECONDS.convert(workTime, TimeUnit.NANOSECONDS)+System.nanoTime();
        this.countDownLatch = countDownLatch;
    }
    
    /**
     * 根据学生考试时间排序
     * 	前面初始化了一个考试时间为120分钟的学生，当延迟队列取到这个学生时，结束Teacher线程，强制交卷
     */
    @Override
    public int compareTo(Delayed o) {
        if(o == null || ! (o instanceof Student)) return 1;
        if(o == this) return 0; 
        Student s = (Student)o;
        if (this.workTime > s.workTime) {
            return 1;
        }else if (this.workTime == s.workTime) {
            return 0;
        }else {
            return -1;
        }
    }

    /**
     * 返回剩余延迟时间，当剩余延迟时间为0或者负数时，代表延迟时间用尽了。
     */
    @Override
    public long getDelay(TimeUnit unit) {
    	//System.out.println(submitTime);
        return unit.convert(submitTime - System.nanoTime(),  TimeUnit.NANOSECONDS);
        //System.out.println(unit.convert(System.nanoTime()-submitTime,  TimeUnit.NANOSECONDS));
        //return unit.convert(System.nanoTime()-submitTime,  TimeUnit.NANOSECONDS);
    }

    @Override
    public void run() {
        if (isForce) {
            System.out.println(name + " 交卷, 希望用时" + workTime + "分钟"+" ,实际用时 120分钟" );
        }else {
            System.out.println(name + " 交卷, 希望用时" + workTime + "分钟"+" ,实际用时 "+workTime +" 分钟");  
        }
        countDownLatch.countDown();
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean isForce) {
        this.isForce = isForce;
    }
    
}

/**
 * 此线程会结束Teacher线程
 * @author lijh
 *
 */
class EndExam extends Student{

    private DelayQueue<Student> students;
    private CountDownLatch countDownLatch;
    private Thread teacherThread;
    
    public EndExam(DelayQueue<Student> students, long workTime, CountDownLatch countDownLatch,Thread teacherThread) {
        super("强制收卷", workTime,countDownLatch);
        this.students = students;
        this.countDownLatch = countDownLatch;
        this.teacherThread = teacherThread;
    }
    
    
    
    @Override
    public void run() {
        
        teacherThread.interrupt();
        Student tmpStudent;
        for (Iterator<Student> iterator2 = students.iterator(); iterator2.hasNext();) {
            tmpStudent = iterator2.next();
            tmpStudent.setForce(true);
            //调用Student线程的run方法，打印考试时间
            tmpStudent.run();
        }
        countDownLatch.countDown();
    }
    
}

/**
 * 监督学生考试线程
 * @author lijh
 *
 */
class Teacher implements Runnable{

    private DelayQueue<Student> students;
    public Teacher(DelayQueue<Student> students){
        this.students = students;
    }
    
    @Override
    public void run() {
        try {
            System.out.println(" test start");
            while(!Thread.interrupted()){
                students.take().run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}