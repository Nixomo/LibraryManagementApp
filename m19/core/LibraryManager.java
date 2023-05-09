package m19.core;

import java.util.List;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import m19.core.exception.MissingFileAssociationException;
import m19.core.exception.BadEntrySpecificationException;
import m19.core.exception.FailedToRegisterUser;
import m19.core.exception.ImportFileException;
import m19.core.exception.InvalidIdNumber;
import m19.core.Library;

// FIXME import other system types
// FIXME import other project (core) types

/**
 * The façade class.
 */
public class LibraryManager {

  private Library _library;
  private String _filename;

  public LibraryManager() {
    _library = new Library();
    _filename = null;
  }
  
  public Integer registerUser(String name, String email) throws FailedToRegisterUser {
    return _library.registerUser(name, email);
  }
  public int getNextUserId() {
    return _library.getNextUserId();
  }

  public int getNextWorkId() {
    return _library.getNextWorkId();
  }

  public int getUsersSize() {
    return _library.getUsersSize();
  }

  public int getWorksSize() {
    return _library.getWorksSize();
  }

  public User showUser(int id) throws InvalidIdNumber{
    return _library.showUser(id);
  }

  public List<User> showUsers() {
    return _library.showUsers();
  }

  public Work showWork(int id) throws InvalidIdNumber{
    return _library.showWork(id);
  }

  public List<Work> showWorks() {
    return _library.showWorks();
  }

  public List<Work> performSearch(String searchKey) {
    return _library.performSearch(searchKey);
  }

  public int getCurrentDate() {
    return _library.getCurrentDate();
  }

  public void advanceDay(int nDays) {
    _library.advanceDay(nDays);
  }

  /**
   * Serialize the persistent state of this application.
   * 
   * @throws MissingFileAssociationException if the name of the file to store the persistent
   *         state has not been set yet.
   * @throws IOException if some error happen during the serialization of the persistent state

   */
  public void save() throws MissingFileAssociationException, IOException, NullPointerException {
    try (FileOutputStream fileOut = new FileOutputStream(_filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

          try {
            out.writeObject(_library);
          } finally {
            out.close();
            fileOut.close();
          }
      
    }
  }

  /**
   * Serialize the persistent state of this application into the specified file.
   * 
   * @param filename the name of the target file
   *
   * @throws MissingFileAssociationException if the name of the file to store the persistent
   *         is not a valid one.
   * @throws IOException if some error happen during the serialization of the persistent state
   */
  public void saveAs(String filename) throws MissingFileAssociationException, IOException {
    _filename = filename;
    save();
  }

  /**
   * Recover the previously serialized persitent state of this application.
   * 
   * @param filename the name of the file containing the perssitente state to recover
   *
   * @throws IOException if there is a reading error while processing the file
   * @throws FileNotFoundException if the file does not exist
   * @throws ClassNotFoundException 
   */
  public void load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {

    try(FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn)) {

          try {
            _library = (Library)in.readObject();
          } finally {
            in.close();
            fileIn.close();
          }
    }
  }

  /**
   * Set the state of this application from a textual representation stored into a file.
   * 
   * @param datafile the filename of the file with the textual represntation of the state of this application.
   * @throws ImportFileException if it happens some error during the parsing of the textual representation.
   */
  public void importFile(String datafile) throws ImportFileException {
    try {
      _library.importFile(datafile);
    } catch (IOException | BadEntrySpecificationException e) {
      throw new ImportFileException(e);
    }
  }
}
