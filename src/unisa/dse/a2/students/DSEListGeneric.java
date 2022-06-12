package unisa.dse.a2.students;

import unisa.dse.a2.interfaces.ListGeneric;

/**
 * @author simont
 * @author An Truong
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
		if (other.head == null) {
			this.head = null;
		}
		else {
			//Create a new node
			NodeGeneric<Trade> copiedNode = copyNode(other.head, null, other.head.next);
			copiedList.head = copiedNode;
		}
		
		this.head = copiedList.head;

	}
	
	private NodeGeneric<Trade> copyNode(NodeGeneric<Trade> head, NodeGeneric<Trade> prev, NodeGeneric<Trade> next) {
		if (head == null) {
			return null;
		}
		
		NodeGeneric<Trade> copiedNode = new NodeGeneric<Trade>(next, prev, head.get());
		copiedNode.prev = prev;
		copiedNode.next = copyNode(head.next, copiedNode, next);
		
		return copiedNode;
	}
	
	private boolean isElementIndex(int index) {
        return index >= 0 && index < this.size();
    }
	
	//remove and return the item at the parameter's index
	public Trade remove(int index) {
		if (!isElementIndex(index)) {
			throw new IndexOutOfBoundsException();
		}
		else if (isEmpty()) {
			throw new NullPointerException();
		}
		NodeGeneric<Trade> lastNode = null;
		NodeGeneric<Trade> currentNode = head;
		
		if (index == 0 && head.next != null) {
			Trade token = head.get();
			head.prev = null;
			head = head.next;
			return token;
			
		} else if (index == this.size() - 1 && head.next != null) {
			while (currentNode.next != null) {
				lastNode = currentNode;
				currentNode = currentNode.next;
			}
			lastNode.next = currentNode;
			tail = lastNode;
			
		} else {
			for (int i = 0; i < index; i++) {
				lastNode = currentNode;
				currentNode = currentNode.next;
			}
			
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
		
		while (currentNode != null && !currentNode.get().equals(obj)) {
			currentNode = currentNode.next;
			index++;
		}
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
		
		if (!isElementIndex(index) || isEmpty()) {
			return null;
		}
		
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
		
		while (currentNode != null) {
			finalString += currentNode.get() + " ";
			currentNode = currentNode.next;
		}
		return finalString.trim();
	}
	
	//add the parameter item at of the end of the list
	public boolean add(Trade obj) {
		boolean addedToEnd = true;
		
		NodeGeneric<Trade> currentNode = head;
		
		if (obj != null && isEmpty()) {
			head = new NodeGeneric<Trade>(null, null, obj);
			tail = head;
				
		} else if (obj != null && !isEmpty()) {
				
			while (currentNode.next != null) {
				currentNode = currentNode.next;
			}
			
			tail = currentNode.next;
			currentNode.next = new NodeGeneric<Trade>(null, currentNode, obj);	
			
		} else {
			addedToEnd = false;
			throw new NullPointerException();
			
		} return addedToEnd;
	}

	//add item at parameter's index
	public boolean add(int index, Trade obj) {
		boolean addedToIndex = false;
		
		if (obj == null) {
			throw new NullPointerException();
		} else if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		NodeGeneric<Trade> currentNode = head;
		
		if (index == 0) {
			if (isEmpty()) {
				tail = currentNode;
			}
			
			head = new NodeGeneric<Trade>(currentNode, null, obj);
			
		} 
		else if (index == this.size()) {
			NodeGeneric<Trade> tempNode = new NodeGeneric<Trade>(null, tail, obj);
			tail.next = tempNode;
			tail = tempNode;
		} 
		else {
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
		
		while (currentNode != null && !currentNode.get().equals(obj)) {
			prevNode = currentNode;
			currentNode = currentNode.next;
		}
		
		if (prevNode == null) {
			head = head.next;
			
			if (!isEmpty()) {
				head.prev = null;
			}
		}
		else {
			prevNode.next = currentNode.next;
			
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
	public boolean equals(Object other) {
		boolean isEqual = false;
		
		if (other == null || this.getClass() != other.getClass()) { 
			return isEqual;

		} else {
			DSEListGeneric<Trade> otherDLL = (DSEListGeneric<Trade>) other;
			if (this.size() != otherDLL.size()) { 
				return isEqual;
			}
			
			NodeGeneric<Trade> otherCurrentNode = otherDLL.head;
			NodeGeneric<Trade> currentNode = head;
			
			while (isEmpty()) {
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
