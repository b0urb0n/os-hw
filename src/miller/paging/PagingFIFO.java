package miller.paging;

import java.util.ArrayList;

public class PagingFIFO extends PagingAlgorithm implements PagingAlgorithmInterface {  
  public PagingFIFO (ArrayList<Integer> refString) {
    super(refString);
  }
  
  @Override
  public void simulate (int numOfFrames) {
    setup(numOfFrames);
    
    for (int i=0; i<refString.size(); i++) {
      if (i != 0){ // copy previous column to current
        table.add(new ArrayList<Integer>(table.get(i - 1)));
      }
      
      ArrayList<Integer> currentFrames = table.get(i);
      Integer page = refString.get(i);
      
      System.out.println(String.format(REFERENCED_MSG, page));
      
      if (currentFrames.contains(page)) {
        System.out.println(String.format(PAGE_HIT_MSG, page));
        pageFault.add(-1);
        victimFrame.add(-1);
      } else {
        System.out.println(String.format(PAGE_MISS_MSG, page));
        System.out.println(PAGE_FAULT_MSG);
        // determine if we need to swap a frame
        boolean allFramesFull = true;
        for (int j=0; j<currentFrames.size(); j++) {
          if (currentFrames.get(j) == -1) {
            allFramesFull = false;
            break;
          }
        }
        
        if (allFramesFull) {
          System.out.println(String.format(SWAP_MSG, page, currentFrames.get(currentFrames.size() - 1)));
          System.out.println(String.format(VICTIM_FRAME_MSG, currentFrames.get(currentFrames.size() - 1)));          
          pageFault.add(1);
          victimFrame.add(currentFrames.get(currentFrames.size() - 1));
          currentFrames.add(0, page);
          currentFrames.remove(currentFrames.size() - 1);
        } else {
          System.out.println(String.format(LOAD_MSG, page, 0));
          pageFault.add(1);
          victimFrame.add(-1); 
          currentFrames.add(0, page);
          currentFrames.remove(currentFrames.size() - 1);
        }
      }
    }
  }  
}
