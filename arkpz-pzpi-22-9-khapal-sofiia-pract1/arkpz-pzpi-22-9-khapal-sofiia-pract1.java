package ua.nure.task1;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;

public class MapViewer {

	private static final int INDENT_SPACES = 7;
    private static final String TREE_NODE = "TreeNode";
    private static final String LIST_NODE = "Node";

	private static Object get(Object obj, String fieldName) {
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(obj);
		} catch (NoSuchFieldException e) {
			System.err.println("Field '" + fieldName + "' not found in the object: " + e.getMessage());
		} catch (IllegalAccessException e) {
			System.err.println("Access to the field '" + fieldName + "' is denied: " + e.getMessage());
		}
		return null;
	}

	/**
	*Виводить вміст переданої мапи з дерева і списків, показуючи кожен елемент окремо.
	* Порожні комірки можуть бути також виведені, якщо параметр `printEmptyBuckets` дорівнює true.
	*/
	public static void print(Map<?, ?> map, boolean printEmptyBuckets) throws ReflectiveOperationException {
		// Отримуємо масив елементів таблиці з мапи
		Object[] tableArray = (Object[]) get(map, TABLE_FIELD_NAME);
		
		// Перебираємо кожен елемент таблиці
		for (int i = 0; i < tableArray.length; i++) {
			Object currentNode = tableArray[i];
			
			// Якщо комірка не порожня, визначаємо тип елемента (дерево або список)
			if (currentNode != null) {
				if (currentNode.getClass().getSimpleName().equals(TREE_NODE)) {
					System.out.print("[" + i + "]Tree: "); // Вивід для дерева
					printTreeNode(currentNode, 0);
				} else if (currentNode.getClass().getSimpleName().equals(LIST_NODE)) {
					System.out.print("[" + i + "]List: "); // Вивід для списку
					printListNode(currentNode);
					System.out.println(); // Перехід на новий рядок після списку
				}
			} else {
				// Вивід порожніх комірок, якщо це дозволено параметром
				if (printEmptyBuckets) {
					System.out.print("[" + i + "]null");
					System.out.println(); // Порожній рядок для порожніх комірок
				}
			}
		}
	}
	
	private static void printListNode(Object o) throws ReflectiveOperationException {
		 System.out.print("Node|"+ o+ "|");
			Object node = o;

			while (( node = get(node, "next")) != null) {
				System.out.print(" ==> " + "The next Node of this tree is |"
				    + node+ "|");
			}
	}

	private static void printTreeNode(Object node, int level) {
		printIndent(level);
		System.out.println("TreeNode|" + node + "|");
		
		Object rightNode = get(node, "right");
		if (rightNode != null) {
			printTreeNode(rightNode, level + 1);
		}
		
		Object leftNode = get(node, "left");
		if (leftNode != null) {
			printTreeNode(leftNode, level + 1);
		}
	}
	

private static void printIndent(int level) {
	 if (level == 0) {
	        return;  
	    }
	    
	 for (int i = 0; i < level; i++) {
	        System.out.print("       "); 
	    }

	    System.out.print("-- "); 
}}
