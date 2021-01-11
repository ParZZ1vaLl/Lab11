package Lab11;


import java.util.TreeSet;

public class Shop {
	private String name;
	private TreeSet<Item> goods;

	@Override
	public String toString() {
		return name;
	}
	
	public Shop(String name) {
		this.setName(name);

		goods = new TreeSet<Item>();
	}

	public String getName() {
		return name;
	}

	public void setName(String shopName) {
		this.name = shopName;
	}
	
	public void addItem(Item item) throws Exception {
		if (item == null) {
			throw new Exception("Item object can`t be null");
		}

		if (goods.contains(item)) {
			throw new Exception("Item is already exists");
		}

		goods.add(item);
	}
	
	public TreeSet<Item> getItems() {
		return goods;
	}
	
	public Item getItemByName(String searchItemName) throws Exception {
		var foundItem = goods.stream().filter(i -> i.getName().equals(searchItemName)).findFirst();

		if (!foundItem.isPresent()) {
			throw new Exception("Item with name " + searchItemName + " not found");
		}

		return foundItem.get();
	}
}

