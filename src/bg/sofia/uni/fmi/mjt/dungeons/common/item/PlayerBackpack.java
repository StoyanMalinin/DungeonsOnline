package bg.sofia.uni.fmi.mjt.dungeons.common.item;

import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PlayerBackpack implements Backpack, Serializable {

    private final static int CAPACITY = 10;
    private List<Item> items;

    public PlayerBackpack() {
        items = new ArrayList<>();
    }

    @Override
    public List<Item> getItems() {
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

    @Override
    public boolean containsItem(Item item) {
        return items.contains(item);
    }

    public boolean isFull() {
        return items.size() == CAPACITY;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Player backpack: " + System.lineSeparator());
        for (Item item : items) {
            builder.append(item.getName());
            builder.append(System.lineSeparator());
        }
        builder.append("End of backpack" + System.lineSeparator());

        return builder.toString();
    }
}
