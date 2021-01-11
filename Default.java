package Lab11;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toSet;

public class Default {
    private static SearchEngine searchEngine;

    public static void main(String[] args) {
        searchEngine = createTestSearchEngine();
        System.out.println(searchEngine);

        System.out.println("Cheapest item with name item4 costs: " + findCheapestItemByName("item4"));
        System.out.println(getShopsWithMinimumPriceItems(findCheapestItemByName("item4")));
        System.out.println(isExistShopWithPerfectPrice(new double[]{250.0, 250.0, 250.0, 250.0, 250.0}));
    }

    private static boolean isExistShopWithPerfectPrice(double[] perfectPrices) {
        boolean flag = true;

        for (Iterator<Shop> it = searchEngine.getShops().iterator(); it.hasNext(); ) {
            flag = true;

            var prices = ((Shop) it.next()).getItems().stream().map(i -> i.getPrice()).collect(toSet());

            for (int i = 0; i < prices.size(); i++) {
                if (!(prices.iterator(i) >= perfectPrices[i])) continue;
                flag = false;
            }
        }

        return flag;
    }

    private static TreeSet<Shop> getShopsWithMinimumPriceItems(double minPrice) {
        var selectedShops = new TreeSet<Shop>();

        for (Iterator it = searchEngine.getShops().iterator(); it.hasNext(); ) {
            var s = (Shop) it.next();

            if (((Item) s.getItems().stream().min(new ItemsComparatorByPrice()).get()).getPrice().equals(minPrice)) {
                selectedShops.add(s);
            }
        }

        return selectedShops;
    }

    private static double findCheapestItemByName(String itemName) {
        var foundItems = new TreeSet<Item>();

        for (var s : searchEngine.getShops()) {
            try {
                foundItems.add(s.getItemByName(itemName));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        var cheapestItem = (Item) foundItems.stream().min(new ItemsComparatorByPrice()).get();

        return cheapestItem.getPrice();
    }

    private static SearchEngine createTestSearchEngine() {
        var engine = new SearchEngine("Google");

        for (int i = 0; i < 10; i++) {
            try {
                var shop = createTestShop();

                for (int j = 0; j < 5; j++) {
                    shop.addItem(createItem(j));
                }

                engine.addInternetShop(shop);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        return engine;
    }

    private static Shop createTestShop() {
        return new Shop(UUID.randomUUID().toString().substring(0, 7));
    }

    private static Item createItem(int number) {
        return new Item("item" + number,
                ThreadLocalRandom.current().nextInt(10, 100));
    }

    static class ItemsComparatorByPrice implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            Item item1 = (Item) o1;
            Item item2 = (Item) o2;

            return item1.getPrice().compareTo(item2.getPrice());
        }
    }

}

