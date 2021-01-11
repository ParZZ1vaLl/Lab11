package Lab11;


import java.util.TreeSet;

public class SearchEngine {
    private String name;
    private TreeSet<Shop> shops;

    public SearchEngine(String name) {
        this.name = name;

        shops = new TreeSet<Shop>();
    }

    public void addInternetShop(Shop shop) throws Exception {
        if (shop == null) {
            throw new Exception("Shop object can`t be null");
        }

        if (shops.contains(shop)) {
            throw new Exception("Shop is already exists");
        }

        shops.add(shop);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeSet<Shop> getShops() {
        return shops;
    }

    public Shop getShopByName(String findShop) throws Exception {
        var foundShop = shops.stream().filter(f -> f.getName() == findShop).findFirst();

        if (!foundShop.isPresent()) {
            throw new Exception("Shop not found");
        }

        return foundShop.get();
    }

    @Override
    public String toString() {
        return "service:" + name +
                "\ninternetShops:" + shops;
    }
}
