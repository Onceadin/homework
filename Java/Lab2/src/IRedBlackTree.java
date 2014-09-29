/**
 * ��������� ������ ������ � ��������� Red-black tree � ����������� 
 * ��������������� ����� �������, �������� � ������.
 * 
 * 
 */
public interface IRedBlackTree {
  /**
   * �������� ������� � ������
   * @param e
   */
  void add(Comparable e);
  /**
   * ������� ������� �� ������
   */
  boolean remove(Comparable o);
  /**
   * ���������� true, ���� ������� ���������� � ������
   */
  boolean contains(Comparable o);
}