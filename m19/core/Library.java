package m19.core;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.List;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import pt.tecnico.po.ui.Command;

import m19.core.exception.BadEntrySpecificationException;
import m19.core.exception.FailedToRegisterUser;
import m19.core.exception.InvalidIdNumber;

/**
 * Class that represents the library as a whole.
 */
public class Library implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201901101348L;

  // FIXME define attributes
  private Date _date;
  private Parser _parser;

  private Map<Integer, User> _users;
  private Map<Integer, Work> _works;

  private int _nextWorkId;
  private int _nextUserId;

  private String _filename = "/statefile.ser";
  
  public Library() {
    _date = new Date();
    _parser = new Parser(this);
    _users = new HashMap<>();
    _works = new HashMap<>();
    _nextWorkId = _nextUserId = 0;
  }

  public int getNextUserId() {
    return _nextUserId;
  }

  public int getNextWorkId() {
    return _nextWorkId;
  }

  public int getUsersSize() {
    return _users.size();
  }

  public int getWorksSize() {
    return _works.size();
  }

  public int addUser(User u) {
    _users.put(_nextUserId, u);
    return _nextUserId++;
  }

  public int addWork(Work w) {
    _works.put(_nextWorkId, w);
    return _nextWorkId++;
  }

  public Integer registerUser(String name, String email) throws FailedToRegisterUser{
    if (name.length() != 0 && email.length() != 0) {
      return addUser(new User(name, email,_nextUserId));
    } else {
      throw new FailedToRegisterUser();
    }
  }

  public User showUser(int id) throws InvalidIdNumber{
    if (id < 0 || id >= _users.size())
      throw new InvalidIdNumber();
    else
      return _users.get(id);
  }

  public List<User> showUsers() {
    List<User> temp = new ArrayList<User>(_users.values());
    temp.sort(Comparator.comparing(User::getLowerName));
    return temp;
  }

  public Work showWork(int id) throws InvalidIdNumber{
    if (id < 0 || id >= _works.size())
      throw new InvalidIdNumber();
    else
      return _works.get(id);
  }

  public List<Work> showWorks() {
    List<Work> temp = new ArrayList<Work>(_works.values());
    temp.sort(Comparator.comparing(Work::getId));
    return temp;
  }

  public List<Work> performSearch(String searchKey) {
    List<Work> works = new ArrayList<Work>(_works.values());
    List<Work> temp = new ArrayList<Work>();

    for (Work w : works) {
      if (w.getDescription().toLowerCase().contains(searchKey.toLowerCase())) {
        temp.add(w);
      }
    }

    temp.sort(Comparator.comparing(Work::getId));
    return temp;
  }

  public int getCurrentDate() {
    return _date.getCurrentDate();
  }

  public void advanceDay(int nDays) {
    if (nDays > 0)
      _date.advanceDay(nDays);
  }

  /**
   * Read the text input file at the beginning of the program and populates the
   * instances of the various possible types (books, DVDs, users).
   * 
   * @param filename
   *          name of the file to load
   * @throws BadEntrySpecificationException
   * @throws IOException
   */
  void importFile(String filename) throws BadEntrySpecificationException, IOException {
    _parser.parseFile(filename);
  }

}
