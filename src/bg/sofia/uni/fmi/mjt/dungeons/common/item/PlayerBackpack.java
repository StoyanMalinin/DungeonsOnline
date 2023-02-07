package bg.sofia.uni.fmi.mjt.dungeons.common.item;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PlayerBackpack implements Backpack {

    private final static int CAPACITY = 10;
    private List<Item> items;

    public PlayerBackpack() {
        items = new LinkedList<>();
    }

    @Override
    public Collection<Item> getItems() {
        return List.copyOf(items);
    }

    @Override
    public boolean addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if (items.size() < CAPACITY) {
            items.add(item);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeItem(Item item) {
        return items.remove(item);
    }
}
