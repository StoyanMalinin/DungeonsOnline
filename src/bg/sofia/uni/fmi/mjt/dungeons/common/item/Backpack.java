package bg.sofia.uni.fmi.mjt.dungeons.common.item;

import java.util.Collection;

public interface Backpack {
    Collection<Item> getItems();

    boolean addItem(Item item);

    boolean removeItem(Item item);
}
