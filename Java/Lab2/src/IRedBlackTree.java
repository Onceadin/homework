/**
 * ��������� ������ ������ � ��������� Red-black tree � ����������� 
 * ��������������� ����� �������, �������� � ������.
 * 
 * 
 */
public interface IRedBlackTree<K extends Comparable<K>> {
  /**
   * �������� ������� � ������
   * @param e
   */
  void add(K e);
  /**
   * ������� ������� �� ������
   */
  boolean remove(K o);
  /**
   * ���������� true, ���� ������� ���������� � ������
   */
  boolean contains(K o);
}