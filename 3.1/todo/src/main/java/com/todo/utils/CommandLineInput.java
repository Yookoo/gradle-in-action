package com.todo.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * 用户输入的枚举类
 * @author zhu
 *
 */
public enum CommandLineInput {
    FIND_ALL('a'), FIND_BY_ID('f'), INSERT('i'), UPDATE('u'), DELETE('d'), EXIT('e');

    private final static Map<Character, CommandLineInput> INPUTS;
    /**
     * 在静态块中初始化
     */
    static {
        INPUTS = new HashMap<Character, CommandLineInput>();

        for (CommandLineInput input : values()) {
            INPUTS.put(input.getShortCmd(), input);
        }
    }

    private final char shortCmd;
    /**
     * 构造方法
     * @param shortCmd
     */
    private CommandLineInput(char shortCmd) {
        this.shortCmd = shortCmd;
    }
    /**
     * get方法
     * @return
     */
    public char getShortCmd() {
        return shortCmd;
    }
    
    public static CommandLineInput getCommandLineInputForInput(char input) {
        return INPUTS.get(input);
    }
}