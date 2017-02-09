package lib.filesystem;

import java.util.Scanner;

public class Menu {
  private static final String INVALID_SELECTION = "\nInvalid selection...";
  private static final String HEADER = "\n-- Miller - Filesystem Menu --";
  private static final String MENU = "0 – Exit\n"
                                   + "1 – Select directory\n"
                                   + "2 – List directory content (first level)\n"
                                   + "3 – List directory content (all levels)\n"
                                   + "4 – Delete file\n"
                                   + "5 – Display file (hexadecimal view)\n"
                                   + "6 – Encrypt file (XOR with password)\n"
                                   + "7 – Decrypt file (XOR with password)\n"
                                   + "Select action: ";
  
  private Scanner input = new Scanner(System.in);
  
  public void display() {
    System.out.println(HEADER);
    System.out.print(MENU);
    
    int cmd = input.nextInt();
    input.nextLine();
    
    parse(cmd);
  }
  
  public void parse(int command) {
    switch(command){
    case 0:
      System.exit(0);
      break;
    case 1:
      selectDirectory();
      break;
    case 2:
      listDirectory(false);
      break;
    case 3:
      listDirectory(true);
      break;
    case 4:
      deleteFile();
      break;
    case 5:
      displayFile();
      break;
    case 6:
      encryptFile();
      break;
    case 7:
      decryptFile();
      break;
    default:
      System.out.println(INVALID_SELECTION);
      break;
    }
  }

  private void decryptFile() {
    // TODO Auto-generated method stub
    
  }

  private void encryptFile() {
    // TODO Auto-generated method stub
    
  }

  private void displayFile() {
    // TODO Auto-generated method stub
    
  }

  private void deleteFile() {
    // TODO Auto-generated method stub
    
  }

  private void listDirectory(boolean b) {
    // TODO Auto-generated method stub
    
  }

  private void selectDirectory() {
    // TODO Auto-generated method stub
    
  }
}
