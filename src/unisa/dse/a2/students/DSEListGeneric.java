package unisa.dse.a2.students;

import unisa.dse.a2.interfaces.ListGeneric;

/**
 * @author simont
 * @author An Truong - truan004
 */
public class DSEListGeneric<Trade> implements ListGeneric<Trade> {
	
	public NodeGeneric<Trade> head;
	private NodeGeneric<Trade> tail;

	public DSEListGeneric() {
		head = null;
		tail = null;
	}
	public DSEListGeneric(NodeGeneric<Trade> head_) {
		this.head = head_;
	}
	
	//Takes a list then adds each element into a new list
	public DSEListGeneric(DSEListGeneric<Trade> other) { // Copy constructor. 
		DSEListGeneric<Trade> copiedList = new DSEListGeneric<Trade>();
		
		// assign new copied chain of node to the copiedList
		// check if other node is empty
		if (other.head == null) {
			this.head = null;
		}
		else {
			// create a new node calling copyNode()
			NodeGeneric<Trade> copiedNode = copyNode(other.head, null, other.head.next);
			
			// set copied node to copied list's head
			copiedList.head = copiedNode;
		}
		
		this.head = copiedList.head;

	}
	
	/**
	 * Copy Node using recursion
	 * @return copied Node
	 * 
	 */
	private NodeGeneric<Trade> copyNode(NodeGeneric<Trade> head, NodeGeneric<Trade> prev, NodeGeneric<Trade> next) {
		if (head == null) {
			return null;
		}
		
		// create new node passing original node data
		NodeGeneric<Trade> copiedNode = new NodeGeneric<Trade>(next, prev, head.get());
		
		// point new node prev to prev node from parameter (copy prev)
		copiedNode.prev = prev;
		
		// point new node next to next node of it recursively (copy next)
		copiedNode.next = copyNode(head.next, copiedNode, next);
		
		return copiedNode;
	}
	
	/**
	 * Validate whether the index of element is in range (0, size - 1)
	 * @return boolean
	 * 
	 */
	private boolean isElementIndex(int index) {
        return index >= 0 && index < this.size();
    }
	
	//remove and return the item at the parameter's index
	public Trade remove(int index) {
		
		// throw exception to index in invalid range 
		if (!isElementIndex(index)) {
			throw new IndexOutOfBoundsException();
			
		} // throw exception to empty list
		else if (isEmpty()) {
			throw new NullPointerException();
		}
		
		// temporary node to keep track of nodes in list
		NodeGeneric<Trade> lastNode = null;
		NodeGeneric<Trade> currentNode = head;
		
		// remove at index 0 (head)
		if (index == 0 && head.next != null) {
			Trade token = head.get();
			
			// move head to the next node
			head.prev = null;
			head = head.next;
			return token;
		
		// remove at tail position
		} else if (index == this.size() - 1 && head.next != null) {
			while (currentNode.next != null) {
				lastNode = currentNode;
				currentNode = currentNode.next;
			}
			
			// move tail to the second last node
			lastNode.next = currentNode;
			tail = lastNode;
			
		} else {
			for (int i = 0; i < index; i++) {
				lastNode = currentNode;
				currentNode = currentNode.next;
			}
			
			// link 2 nodes around the given node with each other
			// move given node to the next one
			if (currentNode.next != null) {
				lastNode.next = currentNode.next;
				currentNode.next.prev = lastNode;
			}
		}
		
		return currentNode.get();
	}

	//returns the index of the String parameter 
	public int indexOf(Trade obj) {
		int index = 0;
		NodeGeneric<Trade> currentNode = head;
		
		// search for the node containing data
		while (currentNode != null && !currentNode.get().equals(obj)) {
			currentNode = currentNode.next;
			index++;
		}
		
		// return -1 if node is null
		if (currentNode == null) {
			return - 1;
		}
		else {
			return index;
		}
	}
	
	//returns item at parameter's index
	public Trade get(int index) {
		NodeGeneric<Trade> currentNode = head;
		
		// validate index out of range and empty list
		if (!isElementIndex(index) || isEmpty()) {
			return null;
		}
		
		// loop through the list until reach the given index
		int i = 0;
		while(i < index){
			currentNode = currentNode.next;
			i++;
		}
		return currentNode.get();
	}

	//checks if there is a list
	public boolean isEmpty() {
		return head == null;
	}

	//return the size of the list
	public int size() {
		int size = 0;
		NodeGeneric<Trade> currentNode = head;
		
		// loop through and count total nodes of list
		while (currentNode != null) {
			currentNode = currentNode.next;
			size++;
		}
		return size;
	}
	
	//Take each element of the list a writes them to a string 
	@Override
	public String toString() {
		NodeGeneric<Trade> currentNode = head;
		String finalString = "";
		
		// loop through the list and convert the data of every nodes into 1 string 
		while (currentNode != null) {
			finalString += currentNode.get() + " ";
			currentNode = currentNode.next;
		}
		// get rid of white spaces at 2 ends of string
		return finalString.trim();
	}
	
	//add the parameter item at of the end of the list
	public boolean add(Trade obj) {
		boolean addedToEnd = true;
		
		NodeGeneric<Trade> currentNode = head;
		
		// obj not null and list is empty --> add first node 
		if (obj != null && isEmpty()) {
			head = new NodeGeneric<Trade>(null, null, obj);
			tail = head;
				
		// obj not null and list not empty --> add new node to tail
		} else if (obj != null && !isEmpty()) {
				
			while (currentNode.next != null) {
				currentNode = currentNode.next;
			}
			
			tail = currentNode.next;
			currentNode.next = new NodeGeneric<Trade>(null, currentNode, obj);	
			
		} else {
			// can't add to list
			addedToEnd = false;
			throw new NullPointerException();
			
		} return addedToEnd;
	}

	//add item at parameter's index
	public boolean add(int index, Trade obj) {
		boolean addedToIndex = false;
		
		// validate index out of range and empty obj
		if (obj == null) {
			throw new NullPointerException();
		} else if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		NodeGeneric<Trade> currentNode = head;
		
		// add to start --> head is new node
		if (index == 0) {
			
			// empty list --> tail is head
			if (isEmpty()) {
				tail = currentNode;
			}
			
			head = new NodeGeneric<Trade>(currentNode, null, obj);
			
		} // add to end --> tail is new node
		else if (index == this.size()) {
			NodeGeneric<Trade> tempNode = new NodeGeneric<Trade>(null, tail, obj);
			tail.next = tempNode;
			tail = tempNode;
		} 
		else {
			// add to 0 < index < size --> link node to node (index-1) and (index+1) 
			for (int i = 0; i < index && currentNode != null; i++) {
				currentNode = currentNode.next;
			}
			NodeGeneric<Trade> tempNode = new NodeGeneric<Trade>(currentNode, currentNode.prev, obj);
			
			tempNode.prev.next = tempNode;
			tempNode.prev = tempNode;
		}
		
		addedToIndex = true;
		return addedToIndex;
	}

	//searches list for parameter's String return true if found
	public boolean contains(Trade obj) {
		boolean elementFounded = false;
		
		if (obj == null) {
			throw new NullPointerException();
		}
		
		NodeGeneric<Trade> currentNode = head;
		
		// loop through list, validate data if exist in list
		while (currentNode != null) {
			if (currentNode.get().equals(obj)) {
				elementFounded = true;
				return elementFounded;
			}
			
			currentNode = currentNode.next;
		}
		return elementFounded;
	}

	//removes the parameter's item form the list
	public boolean remove(Trade obj) {
		boolean removed = false;
		
		if (obj == null || isEmpty()) {
			throw new NullPointerException();
		}
		
		NodeGeneric<Trade> prevNode = null;
		NodeGeneric<Trade> currentNode = head;
		
		// search for node store that data
		while (currentNode != null && !currentNode.get().equals(obj)) {
			prevNode = currentNode;
			currentNode = currentNode.next;
		}
		
		// if the removed node is head
		if (prevNode == null) {
			head = head.next;
			
			if (!isEmpty()) {
				head.prev = null;
			}
		}
		else {
			prevNode.next = currentNode.next;
			
			// if the removed node is tail
			if (prevNode.next != null) {
				prevNode.next.prev = prevNode;
			}
		}
		
		removed = true;
		return removed;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	// compare 2 list objects
	public boolean equals(Object other) {
		boolean isEqual = false;
		
		// if null  or from different classes
		if (other == null || this.getClass() != other.getClass()) { 
			return isEqual;

		} else {
			DSEListGeneric<Trade> otherDLL = (DSEListGeneric<Trade>) other;
			
			// if not same size
			if (this.size() != otherDLL.size()) { 
				return isEqual;
			}
			
			NodeGeneric<Trade> otherCurrentNode = otherDLL.head;
			NodeGeneric<Trade> currentNode = head;
			
			// loop through 2 lists, if not same nodes with same data
			while (!isEmpty()) {
				if (!(currentNode.get().equals(otherCurrentNode.get()))) {
					return isEqual;
				}
				
				otherCurrentNode = otherCurrentNode.next;
				currentNode = currentNode.next;
			}
			
			isEqual = true;
			return isEqual;
		}
	}
}
