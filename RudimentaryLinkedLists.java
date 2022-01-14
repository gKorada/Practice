package listClasses;

import java.util.*;


public class SortedLinkedList<T> extends BasicLinkedList<T> {
	private Comparator<T> classComparator;
	
	public SortedLinkedList(Comparator<T> comparator) {
		super();
		classComparator = comparator;
		
	}
	
	public SortedLinkedList<T> add(T element){
		Node add = new Node(element);
		if(head == null) {
			head = add;
			tail = add;
			listSize++;
			return this;
		}else {
			if(classComparator.compare((T)head.data, element) > 0) {
				super.addToFront(element);
				listSize++;
				return this;
			}else if(classComparator.compare((T)tail.data, element) < 0) {
				super.addToEnd(element);
				listSize++;
				return this;
			}else {
				Node temp = head;
				while(classComparator.compare((T)temp.next.data, element) < 0) {
					temp = temp.next;
				}
				add.next = temp.next;
				temp = add;
				listSize++;
				return this;
			}
		}
	}
	
	public SortedLinkedList<T> remove(T targetData){
		super.remove(targetData, classComparator);
		return this;
	}
	
	public SortedLinkedList<T> addToFront(T data){
		throw new UnsupportedOperationException("Invalid Operation for sorted List");
	}
	
	public SortedLinkedList<T> addToEnd(T data){
		throw new UnsupportedOperationException("Invalid Operation for sorted List");
	}
