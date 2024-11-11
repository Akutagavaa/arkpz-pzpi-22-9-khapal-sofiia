package ua.nure.task1;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;

public class FieldAccessor{
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
}

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
	
	public static void print(Map<?, ?> map, boolean printEmptyBuckets) throws ReflectiveOperationException {
        Object[] tableArray = (Object[]) get(map, TABLE_FIELD_NAME);

        for (int i = 0; i < tableArray.length; i++) {
            Object currentNode = tableArray[i];

            if (currentNode != null) {
                printNode(i, currentNode);
            } else if (printEmptyBuckets) {
                printEmptyBucket(i);
            }
        }
    }

	private static void printNode(int index, Object currentNode) throws ReflectiveOperationException {
        String nodeType = currentNode.getClass().getSimpleName();
        if (nodeType.equals(TREE_NODE)) {
            printTreeNode(index, currentNode);
        } else if (nodeType.equals(LIST_NODE)) {
            printListNode(index, currentNode);
        }
    }

	private static void printListNode(int index, Object listNode) throws ReflectiveOperationException {
		System.out.print("[" + index + "]List: Node|" + listNode + "|");
	
		Object node = listNode;
		while ((node = get(node, "next")) != null) {
			System.out.print(" ==> The next Node of this tree is |" + node + "|");
		}
		System.out.println();
	}

	private static void printTreeNode(Object node, int level, Integer index) throws ReflectiveOperationException {
		if (index != null) {
			System.out.print("[" + index + "]Tree: ");
		}
		printIndent(level);
		System.out.println("TreeNode|" + node + "|");
	
		Object rightNode = get(node, "right");
		if (rightNode != null) {
			printTreeNode(rightNode, level + 1, null);
		}
	
		Object leftNode = get(node, "left");
		if (leftNode != null) {
			printTreeNode(leftNode, level + 1, null);
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
	}
}
