package codegenerator;

import log.Log;
import errorhandler.ErrorHandler;
import scanner.token.Token;
import semantic.symbol.Symbol;
import semantic.symbol.SymbolTable;
import semantic.symbol.SymbolType;

import java.util.Stack;

/**
 * Created by Alireza on 6/27/2015.
 */
public class CodeGenerator {
    private final Memory memory = new Memory();
    private final Stack<Address> addressStack = new Stack<>();
    private final Stack<String> symbolStack = new Stack<>();
    private final Stack<String> callStack = new Stack<>();
    private final SymbolTable symbolTable;
    private final Context context;

    public CodeGenerator() {
        symbolTable = new SymbolTable(memory);
        context = new Context(this);
    }

    public void printMemory() {
        memory.pintCodeBlock();
    }

    public Address getAddressStackLastElement() {
        return addressStack.pop();
    }

    public void pushToAddressStack(Address address) {
        addressStack.push(address);
    }

    public void semanticFunction(int func, Token next) {
        Log.print("codegenerator : " + func);
        this.context.handle(func, next);
    }

    public void defMain(int addressNum) {
        memory.add3AddressCode(addressNum, Operation.JP, new Address(memory.getCurrentCodeBlockAddress(), VarType.ADDRESS), null, null);
        String methodName = "main";
        String className = symbolStack.pop();

        symbolTable.addMethod(className, methodName, memory.getCurrentCodeBlockAddress());

        symbolStack.push(className);
        symbolStack.push(methodName);
    }

    public void checkID() {
        symbolStack.pop();
    }

    public void pid(Token next) {
        if (symbolStack.size() > 1) {
            String methodName = symbolStack.pop();
            String className = symbolStack.pop();
            try {

                Symbol s = symbolTable.get(className, methodName, next.getValue());
                VarType t = s.getVarTypeFromType();
                pushToAddressStack(new Address(s.getAddress(), t));

            } catch (Exception e) {
                pushToAddressStack(new Address(0, VarType.NON));
            }
            symbolStack.push(className);
            symbolStack.push(methodName);
        } else {
            pushToAddressStack(new Address(0, VarType.NON));
        }
        symbolStack.push(next.getValue());
    }

    public Address fPid() {
        Symbol s = symbolTable.get(symbolStack.pop(), symbolStack.pop());
        VarType t = s.getVarTypeFromType();
        return new Address(s.getAddress(), t);
    }

    public void kPid(Token next) {
        pushToAddressStack(symbolTable.get(next.getValue()));
    }

    public void intPid(Token next) {
        pushToAddressStack(new Address(Integer.parseInt(next.getValue()), VarType.INT, TypeAddress.IMMEDIATE));
    }

    public void startCall() {
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();
        symbolTable.startCall(className, methodName);
        callStack.push(className);
        callStack.push(methodName);
    }

    public void call() {
        String methodName = callStack.pop();
        String className = callStack.pop();
        try {
            symbolTable.getNextParam(className, methodName);
            ErrorHandler.printError("The few argument pass for method");
        } catch (IndexOutOfBoundsException ignored) {
        }
        VarType t = VarType.INT;
        switch (symbolTable.getMethodReturnType(className, methodName)) {
            case INT:
                break;
            case BOOL:
                t = VarType.BOOL;
                break;
        }
        Address temp = new Address(memory.getTemp(), t);
        pushToAddressStack(temp);
        memory.add3AddressCode(Operation.ASSIGN, new Address(temp.getNum(), VarType.ADDRESS, TypeAddress.IMMEDIATE), new Address(symbolTable.getMethodReturnAddress(className, methodName), VarType.ADDRESS), null);
        memory.add3AddressCode(Operation.ASSIGN, new Address(memory.getCurrentCodeBlockAddress() + 2, VarType.ADDRESS, TypeAddress.IMMEDIATE), new Address(symbolTable.getMethodCallerAddress(className, methodName), VarType.ADDRESS), null);
        memory.add3AddressCode(Operation.JP, new Address(symbolTable.getMethodAddress(className, methodName), VarType.ADDRESS), null, null);

    }

    public void arg(Address param) {
        String methodName = callStack.pop();
        try {
            Symbol s = symbolTable.getNextParam(callStack.peek(), methodName);
            VarType t = VarType.INT;
            switch (s.getType()) {
                case BOOL:
                    t = VarType.BOOL;
                    break;
                case INT:
                    break;
            }
            if (param.getVarType() != t) {
                ErrorHandler.printError("The argument type isn't match");
            }
            memory.add3AddressCode(Operation.ASSIGN, param, new Address(s.getAddress(), t), null);

        } catch (IndexOutOfBoundsException e) {
            ErrorHandler.printError("Too many arguments pass for method");
        }
        callStack.push(methodName);

    }

    public void assign(Address s1, Address s2) {
        if (s1.getVarType() != s2.getVarType()) {
            ErrorHandler.printError("The type of operands in assign is different ");
        }
        memory.add3AddressCode(Operation.ASSIGN, s1, s2, null);

    }

    public Address add(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), VarType.INT);

        if (s1.getVarType() != VarType.INT || s2.getVarType() != VarType.INT) {
            ErrorHandler.printError("In add two operands must be integer");
        }
        memory.add3AddressCode(Operation.ADD, s1, s2, temp);
        return temp;
    }

    public Address sub(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), VarType.INT);
        if (s1.getVarType() != VarType.INT || s2.getVarType() != VarType.INT) {
            ErrorHandler.printError("In sub two operands must be integer");
        }
        memory.add3AddressCode(Operation.SUB, s1, s2, temp);
        return temp;
    }

    public Address mult(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), VarType.INT);
        if (s1.getVarType() != VarType.INT || s2.getVarType() != VarType.INT) {
            ErrorHandler.printError("In mult two operands must be integer");
        }
        memory.add3AddressCode(Operation.MULT, s1, s2, temp);
        return temp;
    }

    public void label() {
        pushToAddressStack(new Address(memory.getCurrentCodeBlockAddress(), VarType.ADDRESS));
    }

    public void save() {
        pushToAddressStack(new Address(memory.saveMemory(), VarType.ADDRESS));
    }

    public void whileDef(Address address1, Address address2, Address address3) {
        memory.add3AddressCode(address1.getNum(), Operation.JPF, address2, new Address(memory.getCurrentCodeBlockAddress() + 1, VarType.ADDRESS), null);
        memory.add3AddressCode(Operation.JP, address3, null, null);
    }

    public Address jpfSave(Address address, Address address2) {
        Address save = new Address(memory.saveMemory(), VarType.ADDRESS);
        memory.add3AddressCode(address.getNum(), Operation.JPF, address2, new Address(memory.getCurrentCodeBlockAddress(), VarType.ADDRESS), null);
        return save;
    }

    public void jpHere(Address address) {
        memory.add3AddressCode(address.getNum(), Operation.JP, new Address(memory.getCurrentCodeBlockAddress(), VarType.ADDRESS), null, null);
    }

    public void print(Address address) {
        memory.add3AddressCode(Operation.PRINT, address, null, null);
    }

    public Address equal(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), VarType.BOOL);
        if (s1.getVarType() != s2.getVarType()) {
            ErrorHandler.printError("The type of operands in equal operator is different");
        }
        memory.add3AddressCode(Operation.EQ, s1, s2, temp);
        return temp;
    }

    public Address lessThan(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), VarType.BOOL);
        if (s1.getVarType() != VarType.INT || s2.getVarType() != VarType.INT) {
            ErrorHandler.printError("The type of operands in less than operator is different");
        }
        memory.add3AddressCode(Operation.LT, s1, s2, temp);
        return temp;
    }

    public Address and(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), VarType.BOOL);
        if (s1.getVarType() != VarType.BOOL || s2.getVarType() != VarType.BOOL) {
            ErrorHandler.printError("In and operator the operands must be boolean");
        }
        memory.add3AddressCode(Operation.AND, s1, s2, temp);
        return temp;

    }

    public Address not(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), VarType.BOOL);
        if (s1.getVarType() != VarType.BOOL) {
            ErrorHandler.printError("In not operator the operand must be boolean");
        }
        memory.add3AddressCode(Operation.NOT, s1, s2, temp);
        return temp;

    }

    public void defClass() {
        getAddressStackLastElement();
        symbolTable.addClass(symbolStack.peek());
    }

    public void defMethod() {
        getAddressStackLastElement();
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();

        symbolTable.addMethod(className, methodName, memory.getCurrentCodeBlockAddress());

        symbolStack.push(className);
        symbolStack.push(methodName);

    }

    public void popClass() {
        symbolStack.pop();
    }

    public void extend() {
        getAddressStackLastElement();
        symbolTable.setSuperClass(symbolStack.pop(), symbolStack.peek());
    }

    public void defField() {
        getAddressStackLastElement();
        symbolTable.addField(symbolStack.pop(), symbolStack.peek());
    }

    public void defVar() {
        getAddressStackLastElement();

        String var = symbolStack.pop();
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();

        symbolTable.addMethodLocalVariable(className, methodName, var);

        symbolStack.push(className);
        symbolStack.push(methodName);
    }

    public void methodReturn(Address s) {
        String methodName = symbolStack.pop();
        SymbolType t = symbolTable.getMethodReturnType(symbolStack.peek(), methodName);
        VarType temp = VarType.INT;
        switch (t) {
            case INT:
                break;
            case BOOL:
                temp = VarType.BOOL;
                break;
        }
        if (s.getVarType() != temp) {
            ErrorHandler.printError("The type of method and return address was not match");
        }
        memory.add3AddressCode(Operation.ASSIGN, s, new Address(symbolTable.getMethodReturnAddress(symbolStack.peek(), methodName), VarType.ADDRESS, TypeAddress.INDIRECT), null);
        memory.add3AddressCode(Operation.JP, new Address(symbolTable.getMethodCallerAddress(symbolStack.peek(), methodName), VarType.ADDRESS), null, null);
    }

    public void defParam() {
        getAddressStackLastElement();
        String param = symbolStack.pop();
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();

        symbolTable.addMethodParameter(className, methodName, param);

        symbolStack.push(className);
        symbolStack.push(methodName);
    }

    public void lastTypeBool() {
        symbolTable.setLastType(SymbolType.BOOL);
    }

    public void lastTypeInt() {
        symbolTable.setLastType(SymbolType.INT);
    }

}
