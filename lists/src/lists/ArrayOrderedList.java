package lists;
import exceptionclasses.ElementNotFoundException;



/*
 * ArrayOrderedList
 *
 * Represents an array implementation of an ordered list.
 *
 *
 */

public class ArrayOrderedList<E> extends ArrayList<E> implements OrderedListADT<E>
{
	/**
	 * default constructor --
	 * creates an empty list using the default capacity.
	 */
	public ArrayOrderedList()
	{
		super();
	}

	/**
	 * Creates an empty list using the specified capacity.
	 * @param initialCapacity the initial size of the list as specified by the user
	 */
	public ArrayOrderedList (int initialCapacity)
	{
		super(initialCapacity);
	}

	/**
	 * add --
	 * adds the specified Comparable item to this list, keeping
	 * the items in sorted order. The size of the list should be expanded when full.
	 * @param newItem a reference to the new item to be added to the list
	 */
	@Override
	public void add(E item)
	{
		if(count == contents.length) {
			expandCapacity();
		}

		int scan = 0;
		Comparable<Object> compItem = (Comparable<Object>) item;


		while (scan < count && compItem.compareTo(contents[scan]) > 0)
        {
            scan++;
        }

        // 3. Shift elements right from the end to 'scan'
        for (int i = count; i > scan; i--)
        {
            contents[i] = contents[i - 1];
        }

        // 4. Insert the new item
        contents[scan] = item;

        // 5. Update count
        count++;

	}

	/**
	 * find --
	 * overrides the find method in the parent class, ArrayList. The efficiency of this
	 * method is improved by terminating the linear search once you have gone beyond the
	 * position where the item might be found.
	 * @param target a reference to the item to locate
	 * @return the index of the specified target if it is found; -1 if it
	 * is not found
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected int find(E target)
	{
		for (int i = 0; i < count; i++)
		{
			E current = (E) contents[i];
			int comparison = ((Comparable<E>) current).compareTo(target);
			
			if (comparison == 0)
			{
				return i;
			} 
			
			else if (comparison > 0)
			{
				return -1;
			}
		}
		return -1;
	}

	/**
	 * binSearch --
	 * locates and returns the target from the list if it is found
	 * @param target a reference to an "initialized" item containing a value
	 * for the key-field
	 * @return a reference to the item from the list if found
	 * @throws an ElementNotFoundException if the target is not found

	binSearch(E target, int first, int last)
	if (first > last) 
		throw enfe
	int mid = (first + last) / 2;
	
	int result = ((Comparable<E>) midElement).compareTo(target)

	 *
	 */

	
	
	
	@SuppressWarnings("unchecked")
	public E binSearch(E target)
	{
		int low = 0;
		int high = count - 1;
		
		while (low <= high)
		{
			int mid = low + (high - low) / 2;
			E midElement = (E) contents[mid];
			
			int comparison = ((Comparable<E>) midElement).compareTo(target);
			
			if (comparison == 0)
			{
				//Found target = return actual element from the list.
				return midElement;
			}
			
			else if (comparison < 0)
			
			{
				//Target is in the right half
				low = mid + 1;
			} 
			
			else
			
			{
				//Target is in the left half
				high = mid - 1;
			}
		}
		
		throw new ElementNotFoundException("Element not found in the list");
	}
}
