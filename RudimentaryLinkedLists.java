package listClasses;

import java.util.*;

public class BasicLinkedList<T> implements Iterable<T> {

	/* Node definition */
	protected class Node {
		protected T data;
		protected Node next;

		protected Node(T data) {
			this.data = data;
			next = null;
		}
	}

	
	protected Node head, tail;

	
	protected int listSize;

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new BasicLinkedList.classIterator();
	}

	public BasicLinkedList() {
		head = null;
		tail = null;
		listSize = 0;
	}
	
	public BasicLinkedList(BasicLinkedList<T> original) {
		
		Node curr = original.head;
		
		
		
		
		while(curr != null) {
			if(curr == original.head) {
				T temphead = (T)original.head.data;
				this.addToFront(temphead);
				curr = curr.next;
				
			}else {
				T temp = (T)curr.data;
				this.addToEnd(temp);
				curr = curr.next;
			}
		}
		
	}

	public int getSize() {
		return listSize;
	}

	public BasicLinkedList<T> addToEnd(T data) {
		Node temp = new Node(data);

		if (head == null) {
			head = temp;
			tail = temp;
			listSize++;
		} else {
			tail.next = temp;
			tail = temp;
			listSize++;

		}

		return this;

	}

	public BasicLinkedList<T> addToFront(T data) {
		Node temp = new Node(data);

		if (tail == null) {
			head = temp;
			tail = temp;
			listSize++;
		} else {
			temp.next = head;
			head = temp;
			listSize++;
		}

		return this;
	}

	public T getFirst() {
		if (head != null) {
			T temp = (T) head.data;
			return temp;
		} else {
			return null;
		}
	}

	public T getLast() {
		if (head != null) {
			T temp = (T) tail.data;
			return temp;
		} else {
			return null;
		}
	}

	public T retrieveFirstElement() {
		if (head != null) {
			if (listSize > 1) {
				T answer = (T) head.data;
				head = head.next;

				listSize--;
				return answer;
			} else {
				T answer = (T)head.data;
				head = null;
				tail = null;
				listSize = 0;
				return answer;
			}
		}else {
			return null;
		}

	}

	public T retrieveLastElement() {
		Node prev = null;
		Node curr = head;
		if (head != null) {
			if (listSize > 1) {
				while (curr != tail) {
					if (curr.next == tail) {
						T answer = (T) curr.next.data;
						tail = curr;
						tail.next = null;
						listSize--;
						return answer;
					} else {
						prev = curr;
						curr = curr.next;
					}
				}
				return null;
			} else {
				T answer = (T) tail.data;
				tail = null;
				head = null;
				listSize = 0;
				return answer;
			}
		} else {
			return null;
		}

	}

	public BasicLinkedList<T> remove(T targetData, Comparator<T> comparator) {
		if (comparator.compare(targetData, (T) head.data) == 0) {
			retrieveFirstElement();
			return this;
		} else if (comparator.compare(targetData, (T) tail.data) == 0) {
			retrieveLastElement();
			return this;
		} else {
			Node prev = null;
			Node curr = head;
			while (curr != null) {
				if (comparator.compare(targetData, (T)curr.data) == 0) {
					prev.next = curr.next;
				} else {
					prev = curr;
					curr = curr.next;
				}
			}

			return this;
		}

	}

	public ArrayList<T> getReverseArrayList() {
		ArrayList<T> temp = new ArrayList<T>();
		return temp;
	}
	
	

	public BasicLinkedList<T> getReverseList() {
		BasicLinkedList<T> temp = new BasicLinkedList<T>(this);
		Node finalHead = reverseListAux(temp.head);
		Node finalTail = this.head;
		temp.head = finalHead;
		temp.tail = finalTail;
		return temp;


	}

	private Node reverseListAux(Node curr) {
		if (curr == null) {
			return null;
		}else if(curr.next == null) {
			return curr;
		}else {
			Node next = curr.next;
			curr.next = null;
			Node contin = reverseListAux(next);
			next.next = curr;
			return contin;
			
		}
	}

	class classIterator implements Iterator<T> {
		public Node changer = head;

		@Override
		public boolean hasNext() {
			if (changer != null) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public T next() {
			if (hasNext()) {
				T answer = (T) changer.data;
				changer = changer.next;
				return answer;
			} else {
				throw new NoSuchElementException();
			}
		}

	}

}
