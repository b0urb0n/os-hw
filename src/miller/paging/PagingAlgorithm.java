package miller.paging;

import java.util.ArrayList;
import java.util.Collections;

public class PagingAlgorithm {
  protected static final String FRAME_FORMAT = "Frame-%d";
  protected static final String NAME_FORMAT = "%13s:";
  protected static final String PAGE_FAULT = "Page Fault";
  protected static final String REFERENCE = "Reference";
  protected static final String SINGLE_DIGIT = "%2d ";
  protected static final String SPACES = "   ";
  protected static final String TOTAL_PAGE_FAULTS = "Total Page Faults: %d";
  protected static final String VICTIM_FRAME = "Victim Frame";
  
  protected static final String NO_PAGE_FAULT_MSG = " * No page fault.";
  protected static final String NO_VICTIM_FRAME_MSG = " * No victim frame.";
  protected static final String PAGE_HIT_MSG = " * Page %d already loaded.";
  protected static final String PAGE_MISS_MSG = " * Page %d not loaded.";
  protected static final String REFERENCED_MSG = "\nPage %d referenced.";
  protected static final String SWAP_MSG = " * Swapping frame %d in, and frame %d out.";
  protected static final String LOAD_MSG = " * Loading page %d into empty frame %d";
  protected static final String PAGE_FAULT_MSG = " * Page fault generated";
  protected static final String VICTIM_FRAME_MSG = " * Victim frame is frame %d";
  
  protected ArrayList<Integer> refString;
  protected ArrayList<ArrayList<Integer>> table;
  
  protected ArrayList<Integer> pageFaultList = new ArrayList<Integer>();
  protected ArrayList<Integer> victimFrameList = new ArrayList<Integer>();
  
  public PagingAlgorithm () {}
  
  public PagingAlgorithm (ArrayList<Integer> refString) {
    this.refString = refString;
  }
  
  protected void setup (int numOfFrames) {
    table = new ArrayList<ArrayList<Integer>>();
    table.add(new ArrayList<Integer>(Collections.nCopies(numOfFrames, -1)));
  }
  
  public ArrayList<Integer> getRefString() {
    return refString;
  }
  public void setRefString (ArrayList<Integer> ref) {
    refString = ref;
  }
  
  public ArrayList<ArrayList<Integer>> getTable () {
    return table;
  }
  
  public boolean framesAreFull (ArrayList<Integer> frames) {
    for (int i=0; i<frames.size(); i++) {
      if (frames.get(i) == -1) {
        return false;
      }
    }
    return true;
  }
  
  public Integer getFirstEmptyFrame (ArrayList<Integer> frames) {
    for (int i=0; i<frames.size(); i++) {
      if (frames.get(i) == -1) {
        return i;
      }
    }
    return -1;
  }
  
  public String toString () {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    sb.append(getReferenceString());
    sb.append(getFrameRows());
    sb.append(getPageFaults());
    sb.append(getVictimFrames());
    sb.append("\n");
    sb.append("\n");
    sb.append(String.format(TOTAL_PAGE_FAULTS, getTotalPageFaults()));
    return sb.toString();
  }

  private String getReferenceString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format(NAME_FORMAT, REFERENCE));
    for (Integer i: refString) {
      if (i == -1) {
        sb.append(SPACES);
      } else {
        sb.append(String.format(SINGLE_DIGIT, i));
      }
    }
    sb.append("\n");
    return sb.toString();
  }

  private String getFrameRows() {
    StringBuilder sb = new StringBuilder();
    for (int i=0; i<table.get(0).size(); i++) {
      sb.append(String.format(NAME_FORMAT, String.format(FRAME_FORMAT, i)));
      
      for (ArrayList<Integer> currentFrames: table) {
        if (currentFrames.get(i) == -1) {
          sb.append(SPACES);
        } else {
          sb.append(String.format(SINGLE_DIGIT, currentFrames.get(i)));
        }
      }
      
      sb.append("\n");
    }
    return sb.toString();
  }

  private String getPageFaults() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format(NAME_FORMAT, PAGE_FAULT));
    for (Integer i: pageFaultList) {
      if (i == -1) {
        sb.append(SPACES);
      } else {
        sb.append(String.format(SINGLE_DIGIT, i));
      }
    }
    sb.append("\n");
    return sb.toString();
  }

  private String getVictimFrames() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format(NAME_FORMAT, VICTIM_FRAME));
    for (Integer i: victimFrameList) {
      if (i == -1) {
        sb.append(SPACES);
      } else {
        sb.append(String.format(SINGLE_DIGIT, i));
      }
    }
    return sb.toString();
  }

  private Integer getTotalPageFaults() {
    Integer count = 0;
    for (Integer pf: pageFaultList) {
      if (pf == 1)
        count++;
    }
    return count;
  }
}
