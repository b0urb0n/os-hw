package miller.paging;

import java.util.ArrayList;

public class PagingOPT extends PagingAlgorithm implements PagingAlgorithmInterface {

  public PagingOPT (ArrayList<Integer> refString) {
    super(refString);
  }

  @Override
  public void simulate(int numOfFrames) {
    ArrayList<Integer> currentFrames;
    Integer newFrame;
    Integer victimFrame;
    Integer victimIndex;
    
    setupFrameTable(numOfFrames);
    
    ArrayList<Integer> remainingFrames = new ArrayList<Integer>();
    for (Integer i: refString) {
      remainingFrames.add(i);
    }
    
    for (int i=0; i<refString.size(); i++) {
      if (i != 0){ // copy previous column to current
        frameTable.add(new ArrayList<Integer>(frameTable.get(i - 1)));
      }
      
      currentFrames = frameTable.get(i);
      newFrame = refString.get(i);
      
      System.out.println(String.format(REFERENCED_MSG, newFrame));
      
      if (currentFrames.contains(newFrame)) { // hit
        System.out.println(String.format(PAGE_HIT_MSG, newFrame));
        pageFaultList.add(-1);
        victimFrameList.add(-1);
      } else {  // page fault
        System.out.println(String.format(PAGE_MISS_MSG, newFrame));
        System.out.println(PAGE_FAULT_MSG);
                        
        if (framesAreFull(currentFrames)) { // swap or load
          victimFrame = getVictimFrame(remainingFrames, currentFrames);
          victimIndex = currentFrames.indexOf(victimFrame);
          
          System.out.println(String.format(SWAP_MSG, newFrame, currentFrames.get(victimIndex)));
          System.out.println(String.format(VICTIM_FRAME_MSG, victimFrame));          
          pageFaultList.add(1);
          victimFrameList.add(victimFrame);
          currentFrames.remove(victimFrame);
          currentFrames.add(victimIndex, newFrame);
        } else {
          System.out.println(String.format(LOAD_MSG, newFrame, 0));
          pageFaultList.add(1);
          victimFrameList.add(-1); 
          currentFrames.add(getFirstEmptyFrame(currentFrames), newFrame);
          currentFrames.remove(currentFrames.size() - 1);
        }
      }
      remainingFrames.remove(newFrame);
    }
  }

  private Integer getVictimFrame(ArrayList<Integer> remainingFrames, ArrayList<Integer> currentFrames) {
    // for OPT this should return the frame that won't be used for the most
    // iterations or the first frame that won't be used again
    ArrayList<Integer> currentRemainingFrames = new ArrayList<Integer>();
    for (Integer frame: currentFrames) {
      currentRemainingFrames.add(frame);
    }
    
    for (Integer frame: remainingFrames) {
      if (currentRemainingFrames.contains(frame)) {
        currentRemainingFrames.remove(frame);
      }
      if (currentRemainingFrames.size() == 1) {
        break;
      }
    }
    return currentRemainingFrames.get(0);
  }
}
