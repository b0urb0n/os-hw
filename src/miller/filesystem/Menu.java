package miller.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Menu {
  private static final int BYTE_COLS = 16;
  private static final String FILE_INFO = "%s - %s";
  private static final String INPUT_DIR = "\nInput directory: ";
  private static final String INPUT_FILE = "\nInput file: ";
  private static final String INPUT_PASSWORD = "\nInput password: ";
  private static final String ERROR_NO_CWD = "\nError: you must select a directory before continuing";
  private static final String ERROR_CANT_OPEN = "\nError: cannot open %s for reading";
  private static final String ERROR_NOT_FOUND = "\nError: %s not found.";
  private static final String ERROR_COMMAND = "\nInvalid selection...";
  private static final String ERROR_INFO = "\nError: %s";
  private static final String HEADER = "\n-- Filesystem Menu --";
  private static final String MENU = "0 - Exit\n"
                                   + "1 - Select directory\n"
                                   + "2 - List directory content (first level)\n"
                                   + "3 - List directory content (all levels)\n"
                                   + "4 - Delete file\n"
                                   + "5 - Display file (hexadecimal view)\n"
                                   + "6 - Encrypt file (XOR with password)\n"
                                   + "7 - Decrypt file (XOR with password)\n"
                                   + "Select action: ";
  
  private Scanner input = new Scanner(System.in);
  private Path cwd = null;

  public void display() {
    System.out.println(HEADER);
    System.out.print(MENU);

    try{
      int cmd = Integer.parseInt(input.nextLine());
      parse(cmd);
    } catch (NumberFormatException e) {
      System.out.println(ERROR_COMMAND);
      return;
    } 
  }
  
  private void parse(int command) {
    if (cwd == null && command != 1 && command != 0) {
      System.out.println(ERROR_NO_CWD);
      return;
    }
    switch(command){
    case 0:
      System.exit(0);
      break;
    case 1:
      selectDirectory();
      break;
    case 2:
      listDirectory(cwd, false);
      break;
    case 3:
      listDirectory(cwd, true);
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
      System.out.println(ERROR_COMMAND);
      break;
    }
  }
  
  private Path getPathFromInput() {
    Path p = null;
    String in;
        
    while (true){
      System.out.print(INPUT_FILE);
      in = input.nextLine();
      if (in.startsWith("/")) {
        p = Paths.get(in);
      } else {
        p = Paths.get(cwd.toString(), in);
      }
      
      if (Files.isRegularFile(p)) {
        return p;
      }
      
      System.out.println(String.format(ERROR_NOT_FOUND, p.toString()));
    }
  }
  
  private String getPasswordFromInput() {
    System.out.print(INPUT_PASSWORD);
    return input.nextLine();
  }

  private void decryptFile() {
    Path f = getPathFromInput();
    String password = getPasswordFromInput();
    
    //TODO xor file with password
  }

  private void encryptFile() {
    Path f = getPathFromInput();
    String password = getPasswordFromInput();
    
    //TODO xor file with password
  }

  private void displayFile() {
    int count = 0;
    StringBuilder sb = new StringBuilder();
    Path f = getPathFromInput();
    
    try {
      byte[] bytes = Files.readAllBytes(f);
      for (byte b : bytes){
        sb.append(String.format("%02X", b));
        count++;
        
        if (count >= BYTE_COLS) {
          count = 0;
          sb.append("\n");
        } else {
          sb.append(" ");
        }
      }
    } catch (IOException e) {
      System.out.println(String.format(ERROR_CANT_OPEN, f.toString()));
    } finally {
      System.out.println(sb.toString());
    }
  }

  private void deleteFile() {
    Path f = getPathFromInput();
    
    try{
      Files.delete(f);
    } catch (IOException e) {
      System.out.println(String.format(ERROR_INFO, e.toString()));
    }
  }

  private void listDirectory(Path path, boolean recurse) {
    Stream<Path> children = null;
    try {
      if (recurse) {
        children = Files.walk(path);
      } else {
        children = Files.walk(path, 1);
      }
    } catch (IOException e) {
      System.out.println(String.format(ERROR_INFO, e.toString()));
    } finally {
      children.forEach(this::printFileInfo);
    }
  }
  
  private void printFileInfo(Path path) {
    String type = "?";
    String info = cwd.relativize(path).toString();
    
    if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
      type = "D";
    } else if (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)){
      type = "F";
    } else if (Files.isSymbolicLink(path)) {
      type = "L";
      try {
        info = info + " -> " + Files.readSymbolicLink(path).toString();
      } catch (IOException e) {
        info = info + " -> Error reading link";
      }
    }
    
    if (info.length() > 0)  // omit "." (cwd)
      System.out.println(String.format(FILE_INFO, type, info));
  }

  private void selectDirectory() {
    System.out.print(INPUT_DIR);
    Path d = Paths.get(input.nextLine());
    if (Files.isDirectory(d, LinkOption.NOFOLLOW_LINKS)) {
      this.cwd = d;
    } else {
      System.out.println(String.format(ERROR_NOT_FOUND, d));
    }
  }
}
