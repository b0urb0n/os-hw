package lib.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Menu {
  private static final String FILE_INFO = "%s - %s";
  private static final String INPUT_DIR = "\nInput directory: ";
  private static final String INPUT_FILE = "\nInput file: ";
  private static final String INPUT_PASSWORD = "\nInput password: ";
  private static final String ERROR_NOT_FOUND = "\nError: %s not found.";
  private static final String ERROR_COMMAND = "\nInvalid selection...";
  private static final String ERROR_INFO = "\nError: %s";
  private static final String HEADER = "\n-- Filesystem Menu --";
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
  private Path cwd;

  public void display() {
    System.out.println(HEADER);
    System.out.print(MENU);
    
    int cmd = input.nextInt();
    input.nextLine();
    
    parse(cmd);
  }
  
  private void parse(int command) {
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
    System.out.print(INPUT_FILE);
    Path p = Paths.get(input.nextLine());
    
    while (! Files.isDirectory(p, LinkOption.NOFOLLOW_LINKS)){
      System.out.println(String.format(ERROR_NOT_FOUND, p));
      p = Paths.get(input.nextLine());
    }
    
    return p;
  }
  
  private String getPasswordFromInput() {
    System.out.print(INPUT_PASSWORD);
    return new String();
  }

  private void decryptFile() {
    Path f = getPathFromInput();
    String password = getPasswordFromInput();
  }

  private void encryptFile() {
    Path f = getPathFromInput();
    String password = getPasswordFromInput();
  }

  private void displayFile() {
    Path f = getPathFromInput();
    
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
    Stream<Path> children;
    try {
      children = Files.list(path);
      children.forEach(this::printFileInfo);
    } catch (IOException e) {
      // TODO Auto-generated catch block
    }
    // TODO fix this function
  }
  
  private void printFileInfo(Path path) {
    String type = "?";
    String info = path.toString();
    
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
