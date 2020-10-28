# 一、多线程

## 1.实现方式

1.Runnable，2.Thread，3.Callable

第三个的实现方法

```java
RandomCallable rc =new RandomCallable();
FutureTask ft = new FutureTask(rc);
Thread t = new Thread(ft);
```

## 2.生命周期

新生-->就绪-->运行-->死亡，或者从"运行"-->"阻塞"-->"就绪";

## 3.线程控制

1.join()阻塞当前的线程，等待join的线程运行完成后，继续运行当前线程

2.sleep()休眠当前线程，不释放锁

3.yield()从运行到就绪 <font color=blue>运行->调度，等待调度再运行，不会到阻塞队列</font>，

4.setDaemom()，将此线程设置当前线程的守护线程

```java
runnerThread.setDaemon(true);
runnerThread.start();
```

5.**中断线程**

`thread.stop();`强制停止，不推荐。

`thread.interrupt()`打断线程，征得同意。

```java
class Runner extends Thread{
    @Override
    public void run() {
        int i=1;
        while(!isInterrupted()){
            System.out.println("乌龟跑到了第"+i+++"米");
        }
    }
}
//使用isInterrupted()来判断当前的线程是否接收到.interrupt()方法传来的打断信号
```

# 二、线程同步

### 1.安全问题

死锁：多个线程共享多个资源；多个线程都需要其他线程的资源；每个线程又不愿放弃自己的资源

> 线程安全三要素：原子性，可见性与有序性

### 2.Lock锁与Synchronized同步锁

|  **区别**  |                     同步锁synchronized                     |                     Lock锁                     |
| :--------: | :--------------------------------------------------------: | :--------------------------------------------: |
|  可重入性  |                          可重入锁                          |                    可重入锁                    |
|   锁级别   |                 是一个关键字，JVM级别的锁                  |            是一个接口，JDK级别的锁             |
|   锁方案   |                取决于JVM底层的实现，不灵活                 |        可以提供多种锁方案供选择，更灵活        |
|  异常处理  |                    发生异常会自动释放锁                    | 发生异常不会自动释放锁，需要在finally中释放锁  |
| 隐式/显式  |                           隐式锁                           |                     显式锁                     |
| 独占/共享  |                           独占锁                           | ReentrantLock、WriteLock 独占锁ReadLock 共享锁 |
|  响应中断  | 不可响应中断，没有得到锁的线程会一直等待下去，直到获取到锁 |      等待的线程可以响应中断，提前结束等待      |
|  上锁内容  |                  可以锁方法、可以锁代码块                  |                 只可以锁代码块                 |
| 获取锁状态 |                      不知道是否获取锁                      |     可以知道是否获取锁（tryLock的返回值）      |
|  性能高低  | 重量级锁，性能低(难道坐以待毙吗？改一下虚拟机底层如何？？) |   轻量级锁，性能高，尤其是竞争资源非常激烈时   |

### 3.volatile关键字

意为：易变的，不稳定的

用volatile修饰的变量可以保证线程之间的可见性，并且避免指令重排，<font color="red">但是无法保证操作的原子性</font>

可见性：一个线程对于这个变量的修改，在另一个线程能立即看见。

### 4.CAS问题与ABA问题

cas(change and swap/set)比较并交换/修改，直接采用操作系统底层的技术，native方法，其独特点在于，其操作是原子性的，不会被其他指令妨碍。

ABA问题：线程1使用CAS操作变量X，打算把值由A修改外B。需要首先获取其初始值为A，修改为B之前先先判断此时其值是否还是A（可能在此期间其他线程已经修改了），如果不是A说明被修改过了，要重新执行下一个CAS操作。如果是A，就说明中间没有被修改过，可以修改为B了。但是问题就在于完全有可能中间有一个线程B通过CAS操作将A修改为B，然后线程B或另外一个线程C将内容由B修改回A，此时的A已经其实不是线程A读取的那个A了。

如果ABA问题需要解决的话（也可能无所谓），可以通过时间戳的方式来解决。同时设计一个属性，记录每次修改的时间、或者记录每次修改的版本（版本递增），获取的时候同时获取两个属性的值，比较的时候也同时比较两个属性的值，就可以解决这个问题了。

# 三、线程通信

线程通信借助同步监视器，通过同步监视器控制线程的状态。

应用场景：生产者消费者

```java
//生产者
synchronized (product){
    if(!product.ready){
        if(product.color=="白"){
            product.color = "黄";
            product.type = "月饼";
        }else{
            product.color = "白";
            product.type = "馒头";
        }
        product.ready=true;
        System.out.println("生产了一个"+product.color+"色的"+product.type);
    }
    product.notify();//月饼生产好了，通知“消费者”
    try {
        product.wait();//自己进入休眠状态
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
//消费者
synchronized (product){
    if(product.ready) {
        System.out.println("消费了一个"+product.color+"色的"+product.type);
        product.ready = false;
    }
    product.notify();//月饼消费完了，通知生产者
    try {
        product.wait();//自己进入休眠状态
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

### 线程间通信的方法：

同步监视器：

wait(),wait(time)：

​			线程会让CPU进入阻塞状态，也会放弃锁，进入等待此对象的等待锁定池

​			只能在同步控制方法或者同步控制块里面使用。

notify()通知：唤醒一个在这个对象监视器上的等待的线程；

notify()通知：唤醒全部在这个对象监视器上的等待的线程；

### 完整生命周期：



![image-20201017102100496](C:\Users\邓小兵\AppData\Roaming\Typora\typora-user-images\image-20201017102100496.png)

### 线程通信：同步方法方式

• 同步方法的同步监视器都是this，所以需要将produce()和consume()放入一个类Product中，保证是同一把锁

• 必须调用this的wait()、notify()、notifyAll()方法，this可以省略，因为同步监视器是this。

```java
//产品类：
class Produce{
    String color;
    String name;
    boolean isReady;
	public synchronized void produce(){//同步监视器
        if(!isReady){
            if(color="白色"){
                color="紫色";
                name="甘薯";
            }else{
                name="馒头";
            }
            System.out.println("生产了"+color+"的"+name);
        }
        this.notify();//通知持有这个锁其他线程
        try {
            this.wait();
        } catch (InterruptedException e) {
            this.wait();//自己休眠（其实两个this.可以不写）
        }
    }
	public synchronized void consume(){
        if(isReady){
           System.out.println("消费了"+color+"的"+name); 
            isReady = false;
        }
        this.notify();//通知持有这个锁其他线程
		try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

> 注意：这种方式在有多个消费者和生产者的情况下，可能会出现消费这唤醒消费者的情况。

### 线程通信：Lock锁实现方式

```java
class Produce{
    String color;
    String name;
    boolean isReady;
    Lock lock = new ReentrantLock();//可重入锁
    Condition producerCondition = lock.newCondition();
    Condition consumerCondition = lock.newCondition();
    //构造方法省略
    public void produce(){
        lock.lock();
        try {
            if(!ready){
                if(color=="白"){
                    color = "绿";
                    type = "绿豆糕";
                }else{
                    color = "白";
                    type = "糍粑";
                }
                ready=true;
                System.out.println(Thread.currentThread().getName()+"制造了"+color+"的"+type);
            }
            consumerCondition.signal();
            producerCondition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
    public void consume(){
        lock.lock();
        try {
            if(ready){
                System.out.println("\t"+Thread.currentThread().getName()+"消费了"+color+"的"+type);
                ready = false;
            }
            producerCondition.signal();
            consumerCondition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
```

Runnable类

```java
class ProduceThread implements Runnable{
    Goods goods;
    ProduceThread(Goods goods){
        this.goods = goods;
    }
    @Override
    public void run() {
        while(true){
            goods.produce();
        }
    }
}
class ConsumeThread implements Runnable{
    Goods goods;
    ConsumeThread(Goods goods){
        this.goods = goods;
    }
    @Override
    public void run() {
        while(true){
            goods.consume();
        }
    }
}
```

测试类

```java
Goods goods = new Goods(false,"白色","糍粑");
ProduceThread produceThread = new ProduceThread(goods);
ConsumeThread consumeThread = new ConsumeThread(goods);
new Thread(produceThread,"生产者").start();
new Thread(produceThread,"生产者").start();
new Thread(produceThread,"生产者").start();

new Thread(consumeThread,"消费者").start();
new Thread(consumeThread,"消费者").start();
new Thread(consumeThread,"消费者").start();
```

实际结果

```java
生产者制造了绿的绿豆糕
	消费者消费了绿的绿豆糕
生产者制造了白的糍粑
	消费者消费了白的糍粑
生产者制造了绿的绿豆糕
	消费者消费了绿的绿豆糕
生产者制造了白的糍粑
	消费者消费了白的糍粑
```

### condition

在java1.5版本出现，相比于Object类中的wait(),notify()，Condition中的await(),signal(),线程协作会更加安全和高效