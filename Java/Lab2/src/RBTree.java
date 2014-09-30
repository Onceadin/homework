import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class RBTree<K extends Comparable<K>> implements IRedBlackTree<K>,
		Iterable<K> {

	private class Node {
		private boolean color;
		private K key;
		private Node left;
		private Node right;
		private Node parent;

		public Node() {
			setColor(false);
			key = null;
			left = null;
			right = null;
			parent = null;
		}

		public void setRed() {
			setColor(true);
		}

		public void setBlack() {
			setColor(false);
		}

		public boolean isRed() {
			return color;
		}

		public void setColor(boolean color) {
			this.color = color;
		}

		public void setKey(K key) {
			this.key = key;
		}
	}

	private Node root;
	private final Node leaf = new Node();
	private int size;

	public RBTree() {
		root = leaf;
		size = 0;
	}

	public int compare(K k1, K k2) {
		return ((Comparable<? super K>) k1).compareTo((K) k2);
	}

	public void leftRotate(Node pv) {
		Node tmp = pv.right;
		pv.right = tmp.left;
		if (tmp.left != leaf) {
			tmp.left.parent = pv;
		}
		if (tmp != leaf) {
			tmp.parent = pv.parent;
		}
		if (pv.parent == leaf) {
			root = tmp;
		} else if (pv == pv.parent.left) {
			pv.parent.left = tmp;
		} else {
			pv.parent.right = tmp;
		}
		tmp.left = pv;
		pv.parent = tmp;
	}

	public void rightRotate(Node pv) {
		Node tmp = pv.left;
		pv.left = tmp.right;
		if (tmp.right != leaf) {
			tmp.right.parent = pv;
		}
		tmp.parent = pv.parent;
		if (pv.parent == leaf) {
			root = tmp;
		} else if (pv == pv.parent.right) {
			pv.parent.right = tmp;
		} else {
			pv.parent.left = tmp;
		}
		tmp.right = pv;
		pv.parent = tmp;
	}

	public void add(Comparable e) {
		size++;
		Node pv = new Node();
		pv.setKey((K) e);
		Node y = new Node();
		if (root != leaf) {
			Node x = root;
			while (x != leaf) {
				y = x;
				if (compare(pv.key, x.key) < 0) {
					x = x.left;
				} else {
					x = x.right;
				}
			}
			pv.parent = y;
			if (y == leaf) {
				root = pv;
			} else if (compare(pv.key, y.key) < 0) {
				y.left = pv;
			} else {
				y.right = pv;
			}
			pv.left = leaf;
			pv.right = leaf;
			pv.setRed();
			fixUpForInsert(pv);
		} else {
			root = pv;
			root.setBlack();
			root.left = leaf;
			root.right = leaf;
			root.parent = leaf;
		}
	}

	public void fixUpForInsert(Node pv) {
		while (pv.parent.isRed()) {
			if (pv.parent == pv.parent.parent.left) {
				Node y = pv.parent.parent.right;
				if (y.isRed()) {
					pv.parent.setBlack();
					y.setBlack();
					pv.parent.parent.setRed();
					pv = pv.parent.parent;
				} else {
					if (pv == pv.parent.right) {
						pv = pv.parent;
						leftRotate(pv);
					}
					pv.parent.setBlack();
					pv.parent.parent.setRed();
					rightRotate(pv.parent.parent);
				}

			} else {
				Node y = pv.parent.parent.left;
				if (y.isRed()) {
					pv.parent.setBlack();
					y.setBlack();
					pv.parent.parent.setRed();
					pv = pv.parent.parent;
				} else {
					if (pv == pv.parent.left) {
						pv = pv.parent;
						rightRotate(pv);
					}
					pv.parent.setBlack();
					pv.parent.parent.setRed();
					leftRotate(pv.parent.parent);
				}
			}
		}
		root.setBlack();
	}

	public Node get(K z) {
		Node x = root;
		Node tmp = leaf;
		while (x != leaf) {
			if (compare(x.key, z) == 0) {
				return (tmp = x);
			} else if (compare(x.key, z) > 0) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		return tmp;
	}

	public boolean remove(Comparable o) {
		if (size < 1)
			throw new NullPointerException("Its empty");
		Node tmp = get((K) o);
		if (tmp == leaf) {
			return false;
		}
		delete(tmp);
		return true;
	}

	public void delete(Node tmp) {
		size--;
		if (tmp.left != leaf && tmp.right != leaf) {
			Node s = successor(tmp);
			tmp.key = s.key;
			tmp = s;
		}
		Node replacement = (tmp.left != leaf ? tmp.left : tmp.right);
		if (replacement != leaf) {
			replacement.parent = tmp.parent;
			if (tmp.parent == leaf) {
				root = replacement;
			} else if (tmp == tmp.parent.left) {
				tmp.parent.left = replacement;
			} else {
				tmp.parent.right = replacement;
			}
			tmp.left = tmp.right = tmp.parent = leaf;
			if (!tmp.isRed()) {
				fixUpForDelate(replacement);
			}
		} else if (tmp.parent == leaf) {
			root = leaf;
		} else {
			if (!tmp.isRed()) {
				fixUpForDelate(tmp);
			}
			if (tmp.parent != leaf) {
				if (tmp == tmp.parent.left) {
					tmp.parent.left = leaf;
				} else if (tmp == tmp.parent.right) {
					tmp.parent.right = leaf;
				}
				tmp.parent = leaf;
			}
		}
	}

	private void fixUpForDelate(Node tmp) {
		while (tmp != root && !tmp.isRed()) {
			if (tmp == tmp.parent.left) {
				Node sib = tmp.parent.right;
				if (sib.isRed()) {
					sib.setBlack();
					tmp.parent.setRed();
					leftRotate(tmp.parent);
					sib = tmp.parent.right;
				}
				if (!sib.left.isRed() && !sib.right.isRed()) {
					sib.setRed();
					tmp = tmp.parent;
				} else {
					if (!sib.right.isRed()) {
						sib.left.setBlack();
						sib.setRed();
						rightRotate(sib);
						sib = tmp.parent.right;
					}
					sib.color = tmp.parent.color;
					tmp.parent.setBlack();
					sib.right.setBlack();
					leftRotate(tmp.parent);
					tmp = root;
				}
			} else {
				Node sib = tmp.parent.left;
				if (sib.isRed()) {
					sib.setBlack();
					tmp.parent.setRed();
					rightRotate(tmp.parent);
					sib = tmp.parent.left;
				}
				if (!sib.right.isRed() && !sib.left.isRed()) {
					sib.setRed();
					tmp = tmp.parent;
				} else {
					if (!sib.left.isRed()) {
						sib.right.setBlack();
						sib.setRed();
						leftRotate(sib);
						sib = tmp.parent.left;
					}
					sib.color = tmp.parent.color;
					tmp.parent.setBlack();
					sib.left.setBlack();
					rightRotate(tmp.parent);
					tmp = root;
				}
			}
		}
		tmp.setBlack();
	}

	private Node successor(Node x) {
		Node tmp;
		if (x == leaf)
			tmp = leaf;
		else if (x.right != leaf) {
			tmp = x.right;
			while (tmp.left != leaf) {
				tmp = tmp.left;
			}
		} else {
			Node p = x.parent;
			tmp = x;
			while (tmp != leaf && p == tmp.right) {
				p = tmp;
				tmp = tmp.parent;
			}
		}
		return tmp;
	}

	public boolean contains(Comparable o) {
		K z = (K) o;
		Node x = root;
		while (x != leaf) {
			if (compare(x.key, z) == 0) {
				return true;
			} else if (compare(x.key, z) > 0) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		return false;
	}
	
	
	private class MyIterator<T> implements Iterator<T> {
		Queue<T> queue=new LinkedList<T> ();
		boolean begin = true;
		public boolean hasNext() {
			if(begin){
				makeQueue(root);
				begin = false;
			}
			return !queue.isEmpty();
		}


		public T next() {
			return (T)queue.poll();
		}
		
		public void makeQueue(Node cur){
			Node tmp = cur;
			if(tmp == leaf)
				return;
			queue.offer((T)tmp.key);
			makeQueue(tmp.right);
			makeQueue(tmp.left);
		}
		
	}

	public Iterator<K> iterator() {
		return new MyIterator<K>();
	}
}
