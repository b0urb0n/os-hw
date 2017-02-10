package miller.thread;

import java.util.concurrent.Semaphore;

public class ThreadSync {
  public static final int NUM_THREADS = 3;
  public static final int MAX_ITERATION = 5;

  private static Semaphore s = new Semaphore(1);
  
  public static void main(String[] args) {    
    for (int i=1; i<=NUM_THREADS; i++){
      Thread t = new Thread(new CustomThread(i, s));            
      t.start();
    }
  }
}
