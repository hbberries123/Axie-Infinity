package axie;

import java.util.List;

public class AxieModel {

    private int id;
    private String name;
    private String image;
    private String type;
    private String url;
    private int breedCount;
    private double price;
    private List<String> abilities;

    public AxieModel() {}

    public AxieModel(int id, String name, String image, String type, int breedCount, double price, List<String> abilities) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
        this.breedCount = breedCount;
        this.url = "https://marketplace.axieinfinity.com/axie/" + id;
        this.price = price;
        this.abilities = abilities;
    }

    public AxieModel(int id, String name, String image, String type, int breedCount, double price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
        this.breedCount = breedCount;
        this.price = price;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    public int getBreedCount() {
        return breedCount;
    }

    public void setBreedCount(int breedCount) {
        this.breedCount = breedCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "AxieModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", breedCount=" + breedCount +
                ", price=" + price +
                ", abilities=" + abilities +
                '}';
    }
}
