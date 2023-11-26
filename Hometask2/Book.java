public class Book {
    private String name;
    private Author author;
    private double price;
    private int qty;

    // Конструктор
    public Book(String name, Author author, double price, int qty) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.qty = qty;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    // Сеттеры
    public void setPrice(double price) {
        this.price = price;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    // Метод toString
    @Override
    public String toString() {
        return "\"" + name + "\" от " + author.toString();
    }
}
