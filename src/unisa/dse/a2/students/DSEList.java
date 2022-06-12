package unisa.dse.a2.students;

import unisa.dse.a2.interfaces.List;

/**
 * @author simont
 * @author An Truong - truan004
 */
public class DSEList implements List {
	
	public Node head;
	private Node tail;

	public DSEList() {
		head = null;
		tail = null;
	}
	
	public DSEList(Node head_) {
		this.head = head_;
	}
	
	//Takes a list then adds each element into a new list
	public DSEList(DSEList other) { // Copy constructor. 
		DSEList copiedList = new DSEList();
		if (other.head == null) {
			this.head = null;
		}
		else {
			//Create a new node
			Node copiedNode = copyNode(other.head, null, other.head.next);
			copiedList.head = copiedNode;
		}
		
		this.head = copiedList.head;
	}
	
	/**
	 * @return copied Node
	 * @param current Node
	 * @param previous Node
	 * @param next Node
	 */
	private Node copyNode(Node node, Node prev, Node next) {
		if (node == null) {
			return null;
		}
		
		
		Node copiedNode = new Node(next, prev, node.getString());
		copiedNode.prev = prev;
		copiedNode.next = copyNode(node.next, copiedNode, next);
		
		return copiedNode;
	}
	
	private boolean isElementIndex(int index) {
        return index >= 0 && index < this.size();
    }
	
	
	//remove the String at the parameter's index
	public String remove(int index) {
		if (!isElementIndex(index)) {
			throw new IndexOutOfBoundsException();
		}
		else if (isEmpty()) {
			throw new NullPointerException();
		}
		Node lastNode = null;
		Node currentNode = head;
		
		if (index == 0 && head.next != null) {
			String token = head.getString();
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
		
		return currentNode.getString();
		}
		// returns the index of the String parameter
	public int indexOf(String obj) {
		int index = 0;
		Node currentNode = head;
		
		while (currentNode != null && !currentNode.getString().equals(obj)) {
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
	
	//returns String at parameter's index
		public String get(int index) {
			Node currentNode = head;
			if (!isElementIndex(index) || isEmpty()) {
				return null;
			}
			
			int i = 0;
			while(i < index){
				currentNode = currentNode.next;
				i++;
			}
			return currentNode.getString();
		}
		
	//checks if there is a list
	public boolean isEmpty() {
		return head == null;
	}

	//return the size of the list
	public int size() {
		int size = 0;
		Node currentNode = head;
		while (currentNode != null) {
			currentNode = currentNode.next;
			size++;
		}
		return size;
	}
	
	//Take each element of the list then writes them to a string 
	@Override
	public String toString() {
		Node currentNode = head;
		String finalString = "";
		
		while (currentNode != null) {
			finalString += currentNode.getString() + " ";
			currentNode = currentNode.next;
		}
		return finalString.trim();
	}

	//add the parameter String at the end of the list
	public boolean add(String obj) {
		boolean addedToEnd = true;
		
		Node currentNode = head;
		
		if (obj != null && isEmpty()) {
			head = new Node(null, null, obj);
			tail = head;
				
		} else if (obj != null && !isEmpty()) {
				
			while (currentNode.next != null) {
				currentNode = currentNode.next;
			}
			
			tail = currentNode.next;
			currentNode.next = new Node(null, currentNode, obj);	
			
		} else {
			addedToEnd = false;
			throw new NullPointerException();
			
		} return addedToEnd;
		}
	
	//add String at parameter's index
	public boolean add(int index, String obj) {
		boolean addedToIndex = false;
		
		if (obj == null) {
			throw new NullPointerException();
		} else if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		Node currentNode = head;
		
		if (index == 0) {
			if (isEmpty()) {
				tail = currentNode;
			}
			
			head = new Node(currentNode, null, obj);
			
		} 
		else if (index == this.size()) {
			Node tempNode = new Node(null, tail, obj);
			tail.next = tempNode;
			tail = tempNode;
		} 
		else {
			for (int i = 0; i < index && currentNode != null; i++) {
				currentNode = currentNode.next;
			}
			Node tempNode = new Node(currentNode, currentNode.prev, obj);
			
			tempNode.prev.next = tempNode;
			tempNode.prev = tempNode;
		}
		
		addedToIndex = true;
		return addedToIndex;
	}
	
	//searches list for parameter's String return true if found
	public boolean contains(String obj) {
		boolean elementFounded = false;
		
		if (obj == null) {
			throw new NullPointerException();
		}
		
		Node currentNode = head;
		
		while (currentNode != null) {
			if (currentNode.getString().equals(obj)) {
				elementFounded = true;
				return elementFounded;
			}
			
			currentNode = currentNode.next;
		}
		return elementFounded;
	}

	//removes the parameter's String from the list
	public boolean remove(String obj) {
		boolean removed = false;
		
		if (obj == null || isEmpty()) {
			throw new NullPointerException();
		}
		
		Node prevNode = null;
		Node currentNode = head;
		
		while (currentNode != null && !currentNode.getString().equals(obj)) {
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
			DSEList otherDLL = (DSEList) other;
			if (this.size() != otherDLL.size()) { 
				return isEqual;
			}
			
			Node otherCurrentNode = otherDLL.head;
			Node currentNode = head;
			
			while (isEmpty()) {
				if (!(currentNode.getString().equals(otherCurrentNode.getString()))) {
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
