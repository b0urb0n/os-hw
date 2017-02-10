package miller.banker;

import java.util.ArrayList;

public class BankersProblemProcess {
  private String name;
  private ArrayList<Integer> maxClaimList;
  public ArrayList<Integer> allocationList;
  
  private boolean done = false;
    
  
  public BankersProblemProcess(String name, ArrayList<Integer> maxClaimList, ArrayList<Integer> allocationList){
    this.name = name;
    this.maxClaimList = maxClaimList;
    this.allocationList = allocationList;
  }
  
  public String getName(){
    return name;
  }
  
  public Boolean isDone(){
    return done;
  }
  
  public void done(){
    done = true;
  }
  
  public ArrayList<Integer> getAllocationList(){
    return allocationList;
  }
  
  public ArrayList<Integer> getMaxClaimList(){
    return maxClaimList;
  }
  
  public ArrayList<Integer> getNeed(){
    ArrayList<Integer> temp = new ArrayList<Integer>();
    for (int i=0; i<maxClaimList.size(); i++){
      temp.add(maxClaimList.get(i) - allocationList.get(i));
    }
    return temp;
  }
  
  public void setAllocationList(ArrayList<Integer> allocationList){
    this.allocationList = allocationList;
  }
  
  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append(getName() + ":");
    sb.append("\t" + getMaxClaimList().toString());
    sb.append("\t" + getAllocationList().toString());
    sb.append("\t" + getNeed().toString());
    
    return sb.toString();
  }
}
