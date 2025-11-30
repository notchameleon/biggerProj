package lists;
import exceptionclasses.ElementNotFoundException;
import exceptionclasses.EmptyListException;
/**
 * <p>Title: ArrayList.java</p>
 *
 * <p>Description: Represents an array implementation of a list. The front of
 * the list is kept at array index 0. This class will be extended
 * to create a specific kind of list.</p>
 *
 */
public class ArrayList<E> implements ListADT<E>
{
   protected final int DEFAULT_CAPACITY = 100;
   protected int count;
   protected E[] contents;

  /**
   * default constructor --
   * creates an empty list using the default capacity.
   */
  @SuppressWarnings("unchecked")
  public ArrayList()
  {
      count = 0;
      contents = (E[])(new Object[DEFAULT_CAPACITY]);
  }

  /**
   * parameterized constructor --
   * creates an empty list using the specified capacity.
   * @param initialCapacity the initial size of the list as specified by the user
   */
  @SuppressWarnings("unchecked")
  public ArrayList (int initialCapacity)
  {
      count = 0;
      contents = (E[])(new Object[initialCapacity]);
  }

  /**
   * removeLast --
   * removes and returns the last item in this list.
   * @return a reference to what was the last item in the list
   * @throws EmptyListException if the list is empty
   */
  @Override
public E removeLast () throws EmptyListException
  {
    if (isEmpty()) {
		throw new EmptyListException ("ArrayList is empty!");
	}

    count--;
    E result = contents[count];

    return result;
  }

  /**
   * removeFirst --
   * removes and returns the first element in this list.
   * @return a reference to what was the first item in the list
   * @throws EmptyListException if the list is empty
   */
  @Override
public E removeFirst()
  {
	  if (isEmpty()) {
		throw new EmptyListException("ArrayList is empty!");
	  }

	    E result = contents[0];

	    // Shift all elements left
	    for (int i = 0; i < count - 1; i++) {
	        contents[i] = contents[i + 1];
	    }

	    count--;
	    return result;
}

  /**
   * remove --
   * removes and returns the specified element.
   * @param element the item to be removed from the list
   * @return a reference to the item removed from the list
   * @throws ElementNotFoundException if the target is not in the list
   */
  @Override
public E remove (E target)
  {
	  int index = find(target);
	    if (index == -1) {
	        throw new ElementNotFoundException("target is not in the list");
	    }

	    E result = contents[index];

	    // Shift elements left
	    for (int i = index; i < count - 1; i++) {
	        contents[i] = contents[i + 1];
	    }

	    count--;
	    return result;
  }

  /**
   * first --
   * returns a reference to the element at the front of this list. The element
   * is not removed from the list.
   * @return a reference to the first item in the list
   * @throws EmptyListException if the list is empty
   */
  @Override
public E first() throws EmptyListException
  {
      if (isEmpty()) {
		throw new EmptyListException  ("ArrayList is empty!");
	  }

      return contents[0];
  }

  /**
   * last --
   * returns a reference to the element at the rear of this list. The element
   * is not removed from the list.
   * @return a reference to the last item in the list
   * @throws EmptyListException if the list is empty
   */
  @Override
public E last()
  {
      if (isEmpty()) {
		throw new EmptyListException ("ArrayList is empty!");
	  }

      return contents[count-1];
  }

  /**
   * contains --
   * returns true if this list contains the specified element.
   * @param target a reference to the item to be located
   * @return true if the target is found; false otherwise
   */
  @Override
public boolean contains (E target)
  {
      return find(target) != -1;
  }

  /**
   * find --
   * returns the array index of the specified element.
   * @param target a reference to the item to be located
   * @return the array index of the specified element if it is found; -1 if it
   * is not found
   */
  protected int find (E target)
  {
      for (int i=0; i<count; i++) {
		if (target.equals(contents[i])) {
			return i;
		}
	  }

      return -1;
  }

  /**
   * isEmpty --
   * determines whether or not the list is empty.
   * @return true if this list is empty; false otherwise
   */
  @Override
public boolean isEmpty()
  {
      return count == 0;
  }

  /**
   * size --
   * returns a count of the number of items in this list.
   * @return the number of items currently in the list
   */
  @Override
public int size()
  {
      return count;
  }

  /**
   * toString --
   * returns a string representation of this list.
   * @return a reference to a String containing the items in the list
   */
  @Override
public String toString()
  {
      String result = "";

      for (int i=0; i < count; i++) {
		result = result + contents[i].toString() + "\n";
	  }

      return result;
  }

  /**
   * expandCapacity --
   * creates a new array to store the contents of this list with
   * twice the capacity of the old one.
   */
  @SuppressWarnings("unchecked")
  protected void expandCapacity()
  {
      E[] larger = (E[])(new Object[contents.length*2]);

      for (int i=0; i < count; i++) {
		larger[i] = contents[i];
	  }

      contents = larger;
  }
}
