package m19.core;
import java.io.Serializable;

public abstract class Work implements Serializable{
    private int _id;
    private int _price;
    private int _numberCopies;
    private int _numberCopiesAvailable;
    private String _title;
    private Category _category;

    private static final long serialVersionUID = 201901101343L;

    public Work(int id, int price, int numberCopies, String title, Category category) {
        _id = id;
        _price = price;
        _numberCopies = _numberCopiesAvailable = numberCopies;
        _title = title;
        _category = category;
    }

    public int getId() {
        return _id;
    }

    public abstract String getDescription();

    protected String privateDescription(String type) {
        return _id + " - " + _numberCopiesAvailable + " de " + _numberCopies + " - " + type + " - " + _title + " - " + _price + " - " + _category;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Work) {
            Work other = (Work)o;
            return other.hashCode() == this.hashCode();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return _id;
    }
}