package miller.filesystem;

public class MenuConstants {
  public static final int BYTE_COLS = 16;

  public static final String ERROR_CANT_OPEN = "\nError: cannot open %s for reading";
  public static final String ERROR_COMMAND = "\nInvalid selection...";
  public static final String ERROR_EMPTY_PASS = "\nPassword can't be empty";
  public static final String ERROR_INFO = "\nError: %s";
  public static final String ERROR_NO_CWD = "\nError: you must select a directory before continuing";
  public static final String ERROR_NOT_FOUND = "\nError: %s not found.";
  
  public static final String FILE_INFO = "%s - %s";
  
  public static final String HEX_BYTE = "%02X";
  public static final String HEX_OFFSET = "0x%06X: ";
  
  public static final String INPUT_DIR = "\nInput directory: ";
  public static final String INPUT_FILE = "\nInput file: ";
  public static final String INPUT_OUTPUT_FILE = "\nOutput file: ";
  public static final String INPUT_PASSWORD = "\nInput password: ";

  public static final String HEADER = "\n-- Filesystem Menu --";
  public static final String MENU = "0 - Exit\n"
                                  + "1 - Select directory\n"
                                  + "2 - List directory content (first level)\n"
                                  + "3 - List directory content (all levels)\n"
                                  + "4 - Delete file\n"
                                  + "5 - Display file (hexadecimal view)\n"
                                  + "6 - Encrypt file (XOR with password)\n"
                                  + "7 - Decrypt file (XOR with password)\n"
                                  + "Select action: ";
}
