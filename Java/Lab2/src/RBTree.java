import java.util.Comparator;

public class RBTree<K> {

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
	private final Comparator<? super K> comparator;

	public RBTree() {
		root = leaf;
		comparator = null;
	}

	@SuppressWarnings("unchecked")
	final int compare(Object k1, Object k2) {

		return comparator == null ? ((Comparable<? super K>) k1)
				.compareTo((K) k2) : comparator.compare((K) k1, (K) k2);
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

	public void insert(K z) {
		Node pv = new Node();
		pv.setKey(z);
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

	public boolean find(K z) {
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
}
