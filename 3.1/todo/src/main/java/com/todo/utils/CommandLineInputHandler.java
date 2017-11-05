package com.todo.utils;



import java.util.Collection;

import com.todo.model.ToDoItem;
import com.todo.repository.InMemoryToDoRepository;
import com.todo.repository.ToDoRepository;
/**
 * 用户交互与命令执行的粘合剂
 * 
 * 
 * @author zhu
 *
 */
public class CommandLineInputHandler {
    private ToDoRepository toDoRepository = new InMemoryToDoRepository();

    /**
     * 输出用户的操作说明
     */
    public void printOptions() {
        System.out.println("\n--- To Do Application ---");
        System.out.println("Please make a choice:");
        System.out.println("(a)ll items");
        System.out.println("(f)ind a specific item");
        System.out.println("(i)nsert a new item");
        System.out.println("(u)pdate an existing item");
        System.out.println("(d)elete an existing item");
        System.out.println("(e)xit");
    }

    /**
     * 读取用户的命令行输入
     * @return
     */
    public String readInput() {
        return System.console().readLine("> ");
    }

    /**
     * 根据用户输入的不同调用不同的方法响应
     * @param input
     */
    public void processInput(CommandLineInput input) {
        if (input == null) {
            handleUnknownInput();
        } else {
            switch (input) {
                case FIND_ALL:
                    printAllToDoItems();
                    break;
                case FIND_BY_ID:
                    printToDoItem();
                    break;
                case INSERT:
                    insertToDoItem();
                    break;
                case UPDATE:
                    updateToDoItem();
                    break;
                case DELETE:
                    deleteToDoItem();
                    break;
                case EXIT:
                    break;
                default:
                    handleUnknownInput();
            }
        }
    }
    /**
     * 提示用户输入需要操作的TODO的id
     * @return
     */
    private Long askForItemId() {
        System.out.println("Please enter the item ID:");
        String input = readInput();
        return Long.parseLong(input);
    }
    /**
     * 提示用户输入TODO的名字
     * @return
     */
    private ToDoItem askForNewToDoAction() {
        ToDoItem toDoItem = new ToDoItem();
        System.out.println("Please enter the name of the item:");
        toDoItem.setName(readInput());
        return toDoItem;
    }
	/**
	 * 查询所有的TODO列表
	 */
    private void printAllToDoItems() {
        Collection<ToDoItem> toDoItems = toDoRepository.findAll();

        if (toDoItems.isEmpty()) {
            System.out.println("Nothing to do. Go relax!");
        } else {
            for (ToDoItem toDoItem : toDoItems) {
                System.out.println(toDoItem);
            }
        }
    }
    /**
     * 打印不为空的TODO
     */
    private void printToDoItem() {
        ToDoItem toDoItem = findToDoItem();

        if (toDoItem != null) {
            System.out.println(toDoItem);
        }
    }
    /**
     * 查询TODO
     * @return
     */
    private ToDoItem findToDoItem() {
        Long id = askForItemId();
        ToDoItem toDoItem = toDoRepository.findById(id);

        if (toDoItem == null) {
            System.err.println("To do item with ID " + id + " could not be found.");
        }

        return toDoItem;
    }
    /**
     * 添加TODO
     */
    private void insertToDoItem() {
        ToDoItem toDoItem = askForNewToDoAction();
        Long id = toDoRepository.insert(toDoItem);
        System.out.println("Successfully inserted to do item with ID " + id + ".");
    }
    /**
     * 修改TODO
     */
    private void updateToDoItem() {
        ToDoItem toDoItem = findToDoItem();

        if (toDoItem != null) {
            System.out.println(toDoItem);
            System.out.println("Please enter the name of the item:");
            toDoItem.setName(readInput());
            System.out.println("Please enter the done status the item:");
            toDoItem.setCompleted(Boolean.parseBoolean(readInput()));
            toDoRepository.update(toDoItem);
            System.out.println("Successfully updated to do item with ID " + toDoItem.getId() + ".");
        }
    }
    /**
     * 删除TODO
     */
    private void deleteToDoItem() {
        ToDoItem toDoItem = findToDoItem();

        if (toDoItem != null) {
            toDoRepository.delete(toDoItem);
            System.out.println("Successfully deleted to do item with ID " + toDoItem.getId() + ".");
        }
    }
    /**
     * 输入了未知的命令
     */
    private void handleUnknownInput() {
        System.out.println("Please select a valid option!");
    }
}