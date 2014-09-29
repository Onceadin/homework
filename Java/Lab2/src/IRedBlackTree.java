/**
 * Коллекция хранит данные в структуре Red-black tree и гарантирует 
 * логарифмическое время вставки, удаления и поиска.
 * 
 * 
 */
public interface IRedBlackTree {
  /**
   * Добавить элемент в дерево
   * @param e
   */
  void add(Comparable e);
  /**
   * Удалить элемент из дерева
   */
  boolean remove(Comparable o);
  /**
   * Возвращает true, если элемент содержится в дереве
   */
  boolean contains(Comparable o);
}