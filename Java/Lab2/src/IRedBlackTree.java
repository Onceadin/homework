/**
 * Коллекция хранит данные в структуре Red-black tree и гарантирует 
 * логарифмическое время вставки, удаления и поиска.
 * 
 * 
 */
public interface IRedBlackTree<K extends Comparable<K>> {
  /**
   * Добавить элемент в дерево
   * @param e
   */
  void add(K e);
  /**
   * Удалить элемент из дерева
   */
  boolean remove(K o);
  /**
   * Возвращает true, если элемент содержится в дереве
   */
  boolean contains(K o);
}