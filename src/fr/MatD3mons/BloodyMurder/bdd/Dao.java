package fr.MatD3mons.BloodyMurder.bdd;

import java.util.List;

public interface Dao<K, T> {
    void update(K key, T dto);
    T create(K key);
    void delete(K key);
    T get(K key);
}
