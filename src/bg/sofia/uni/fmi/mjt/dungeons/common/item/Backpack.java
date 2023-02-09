package bg.sofia.uni.fmi.mjt.dungeons.common.item;

import bg.sofia.uni.fmi.mjt.dungeons.common.Stats;

import java.io.Serializable;
import java.util.Collection;

public interface Backpack extends Serializable {
    Collection<Item> getItems();

    boolean addItem(Item item);

    boolean removeItem(Item item);
    boolean containsItem(Item item);
}
