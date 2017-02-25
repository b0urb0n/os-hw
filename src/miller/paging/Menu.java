package miller.paging;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Menu {
  private static final int RANDOM_FRAME_MAX = 10;
  private static final Random RAND = new Random();
  
  private Integer frameLimit = null;
  private Scanner input = new Scanner(System.in);
  private ArrayList<Integer> refString = null;
  
  public Menu (Integer frameLimit) {
    this.frameLimit = frameLimit;
  }
  
  public void display() {
    System.out.println(MenuConstants.HEADER);
    System.out.print(MenuConstants.MENU);
    
    try {
      int cmd = Integer.parseInt(input.nextLine());
      parse(cmd);
    } catch (NumberFormatException e) {
      System.out.println(MenuConstants.ERROR_COMMAND);
    }
  }
  
  private void parse(int command) {
    if (refString == null && command != 0 && command != 1 && command != 2) {
      System.out.println(MenuConstants.ERROR_NO_REF_STRING);
      return;
    }
    
    switch(command){
    case 0:
      System.exit(0);
      break;
    case 1:
      readReferenceStringFromUser();
      break;
    case 2:
      generateRandomReferenceString();
      break;
    case 3:
      System.out.println(refString.toString());
      break;
    case 4:
      simulateFIFO();
      break;
    case 5:
      simulateOPT();
      break;
    case 6:
      simulateLRU();
      break;
    case 7:
      simulateLFU();
      break;
    default:
      break;
    }
  }

  private void simulateLFU() {
    PagingLFU lfu = new PagingLFU(refString);
    lfu.simulate(frameLimit);
    System.out.println(lfu.toString());
  }

  private void simulateLRU() {
    PagingLRU lru = new PagingLRU(refString);
    lru.simulate(frameLimit);
    System.out.println(lru.toString());
  }

  private void simulateOPT() {
    PagingOPT opt = new PagingOPT(refString);
    opt.simulate(frameLimit);
    System.out.println(opt.toString());
  }

  private void simulateFIFO() {
    PagingFIFO fifo = new PagingFIFO(refString);
    fifo.simulate(frameLimit);
    System.out.println(fifo.toString());
  }

  private void generateRandomReferenceString() {
    String in = "";
    
    while (true) {
      System.out.print(MenuConstants.INPUT_REF_STRING_LENGTH);
      in = input.nextLine();
      
      if (in.length() > 0) {
        try {
          refString = getRandomReferenceString(Integer.valueOf(in));          
        } catch (Exception e) {
          System.out.println(String.format(MenuConstants.ERROR_INFO, e.getMessage()));
          continue;
        }
        
        return;
      }
    }
  }

  private ArrayList<Integer> getRandomReferenceString(Integer count) {
    ArrayList<Integer> templ = new ArrayList<Integer>();
    for (int i=0; i<count; i++) {
      templ.add(RAND.nextInt(RANDOM_FRAME_MAX));
    }
    return templ;
  }

  private void readReferenceStringFromUser() {
    String in = "";
    
    while (true) {
      System.out.print(MenuConstants.INPUT_REF_STRING);
      in = input.nextLine();
      
      if (in.length() > 0) {
        ArrayList<Integer> templ = new ArrayList<Integer>();
        
        try {
          for (String s: in.split(" ")) templ.add(Integer.valueOf(s));
        } catch (Exception e) {
          System.out.println(String.format(MenuConstants.ERROR_INFO, e.getMessage()));
          continue;
        }
        
        refString = templ;
        return;
      } else {
        System.out.println(MenuConstants.ERROR_EMPTY_INPUT);
      }
    }
  }
}