package lists;

import exceptionclasses.EmptyListException;

import exceptionclasses.ElementNotFoundException;

/**
 * <p>Title: LinkedList.java</p>
 *
 * <p>Description: Represents a linked implementation of a list. The front of
 * the list is referenced by first and the back of the list is referenced by last. 
 * This class will be extended to create a specific kind of list.</p>
 *
 * @author <Steff & Ibrahim>
 */


public class LinkedList<E> implements ListADT<E>{
	
	protected int count;
	protected Node<E> first, last;

	/**
	 * default constructor --
	 * Creates an empty list.
	 */
	public LinkedList()
	{
		count = 0;
		first = last = null;
	}
	
	/**
	 * size --
	 * returns a count of the number of items in the list.
	 * @return the number of items currently in the list
	 */
	public int size()
	{
		return count;
	}
	
	/**
	 * isEmpty --
	 * determines whether or not the list is empty.
	 * @return true if this list is empty; false otherwise
	 */
	public boolean isEmpty()
	{
		return count == 0;
	}
	
	/**
	 * first --
	 * returns a reference to the item at the front of the list. The item
	 * is not removed from the list.
	 * @return a reference to the first item in the list
	 * @throws EmptyListException if the list is empty
	 */
	public E first()
	{
		if (isEmpty())
			throw new EmptyListException("List is empty!");

		return first.getItem();
	}
	
	/**
	 * last -- 
	 * returns a reference to the item at the end of the list. The item
	 * is not removed from the list.
	 * @return a reference to the last item in the list
	 * @throws EmptyListException if the list is empty
	 */
	public E last()
	{
		if (isEmpty())
			throw new EmptyListException("List is empty!");

		return last.getItem();
	}
	
	/**
	 * contains --
	 * returns true if the list contains the specified target.
	 * @param target a reference to the item to be located
	 * @return true if the target is found; false otherwise
	 */
	public boolean contains (E target)
	{
		if (isEmpty())
			throw new EmptyListException("List is empty!");

		boolean found = false;

		Node<E> current = first;

		while (current != null && !found)
		{
			if (target.equals(current.getItem()))
				found = true;
			else
				current = current.getNext();
		}

		return found;
	}

	/**
	 * removeFirst -- 
	 * removes and returns the first item in the list.
	 * @return a reference to what was the first item in the list
	 * @throws EmptyListException if the list is empty
	 */
	public E removeFirst()
	{
		if (isEmpty()) 
		{
			throw new EmptyListException("List is empty!");
		}
		
		E removedItem = first.getItem();
		
		//Move first pointer to the next node
		first = first.getNext();
		count--;
		
		//If list becomes empty, update last pointer
		
		if (isEmpty())
		{
			last = null;
		}
		
		return removedItem;
		
	}
	
	/**
	 * removeLast -- 
	 * removes and returns the last item in the list.
	 * @return the item removed from the end of the list
	 * @throws EmptyListException if the list is empty
	 */
	//THIS METHOD DOES WORK AS EXPECTED
	public E removeLast()
	{
		if (isEmpty())
			throw new EmptyListException("List is empty!");

		E removedItem;
		
		//Case 1 ; Only one element in the list
		if (first == last) 
		{
			removedItem = first.getItem();
			first = last = null;
		}
		
		//Case 2; Multiple elements in the list
		else {
			Node<E> current = first;
			Node<E> prev =  null;
			
			//Traverse to find the last node and second - to - last node
			while (current.getNext() != null) 
			{
				prev = current;
				current = current.getNext();
			}
			
			removedItem = current.getItem();
			prev.setNext(null);
			last = prev;
		}

		count--;
		return removedItem;
		
	}

	/**
	 * remove --
	 * locates and removes the target from the list.
	 * @param target a reference to an initialized item containing data 
	 * in its key-field
	 * @return a reference to the removed item
	 * @throws EmptyListException if the list is empty
	 * @throws ElementNotFoundException if the target is not found
	 */
	//THIS METHOD DOES WORK AS EXPECTED
	public E remove (E target)
	{
		if (isEmpty())
			throw new EmptyListException("Target is not found!");

		Node<E> current = first;
		Node<E> prev = null;
		boolean found = false;

		while (!found && current != null)
		{
			if (current.getItem().equals(target))
				found = true;
			else {
				prev = current;
				current = current.getNext();
			}
		}

		
		if (!found)
			throw new ElementNotFoundException("Target is not found!");
		
		//Remove the node
		E removedItem = current.getItem();
		
		if (current == first) 
		{
			//Removing first node
			first = first.getNext();
			if (first == null)
			{
				last = null; 
			}
		} 
		else 
		{
			//Removing middle or last node
			prev.setNext(current.getNext());
			
			if (current == last) {
				last = prev; //Update last if removing last node
			}
		}
	
		count--;
		return removedItem;
	}
	
	/**
	 * toString --
	 * returns a string representation of the list.
	 * @return a reference to a String containing the items in the list
	 */
	public String toString()
	{
		Node<E> current = first;
		String result = new String();

		while (current != null)
		{
			result = result + current.getItem().toString() + "\n";
			current = current.getNext();
		}

		return result;
	}



}
