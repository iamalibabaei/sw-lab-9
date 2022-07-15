package codeGenerator;

import Log.Log;
import errorHandler.ErrorHandler;
import scanner.token.Token;
import semantic.symbol.Symbol;
import semantic.symbol.SymbolTable;
import semantic.symbol.SymbolType;

import java.util.Stack;

/**
 * Created by Alireza on 6/27/2015.
 */
public class CodeGenerator {
    private Memory memory = new Memory();
    private Stack<Address> ss = new Stack<Address>();
    private Stack<String> symbolStack = new Stack<>();
    private Stack<String> callStack = new Stack<>();
    private SymbolTable symbolTable;

    public CodeGenerator() {
        symbolTable = new SymbolTable(memory);
        //TODO
    }
    public void printMemory()
    {
        memory.pintCodeBlock();
    }

    public Address getAddressStackLastElement() {
        return ss.pop();
    }

    public void pushToAddressStack(Address address) {
        ss.push(address);
    }

    public void semanticFunction(int func, Token next) {
        Log.print("codegenerator : " + func);
        switch (func) {
            case 0:
                return;
            case 1:
                checkID();
                break;
            case 2:
                pid(next);
                break;
            case 3:
                getAddressStackLastElement();
                getAddressStackLastElement();
                pushToAddressStack(fpid());
                break;
            case 4:
                kpid(next);
                break;
            case 5:
                intpid(next);
                break;
            case 6:
                getAddressStackLastElement();
                getAddressStackLastElement();
                startCall();
                break;
            case 7:
                call();
                break;
            case 8:
                arg(getAddressStackLastElement());
                break;
            case 9:
                assign(getAddressStackLastElement(), getAddressStackLastElement());
                break;
            case 10:
                pushToAddressStack(add(getAddressStackLastElement(), getAddressStackLastElement()));
                break;
            case 11:
                pushToAddressStack(sub(getAddressStackLastElement(), getAddressStackLastElement()));
                break;
            case 12:
                pushToAddressStack(mult(getAddressStackLastElement(), getAddressStackLastElement()));
                break;
            case 13:
                label();
                break;
            case 14:
                save();
                break;
            case 15:
                _while(getAddressStackLastElement(), getAddressStackLastElement(), getAddressStackLastElement());
                break;
            case 16:
                pushToAddressStack(jpf_save(getAddressStackLastElement(), getAddressStackLastElement()));
                break;
            case 17:
                jpHere(getAddressStackLastElement());
                break;
            case 18:
                print(getAddressStackLastElement());
                break;
            case 19:
                pushToAddressStack(equal(getAddressStackLastElement(), getAddressStackLastElement()));
                break;
            case 20:
                pushToAddressStack(less_than(getAddressStackLastElement(), getAddressStackLastElement()));
                break;
            case 21:
                pushToAddressStack(and(getAddressStackLastElement(), getAddressStackLastElement()));
                break;
            case 22:
                pushToAddressStack(not(getAddressStackLastElement(), getAddressStackLastElement()));
                break;
            case 23:
                defClass();
                break;
            case 24:
                defMethod();
                break;
            case 25:
                popClass();
                break;
            case 26:
                extend();
                break;
            case 27:
                defField();
                break;
            case 28:
                defVar();
                break;
            case 29:
                methodReturn(getAddressStackLastElement());
                break;
            case 30:
                defParam();
                break;
            case 31:
                lastTypeBool();
                break;
            case 32:
                lastTypeInt();
                break;
            case 33:
                defMain(getAddressStackLastElement().getNum());
                break;
        }
    }

    private void defMain(int addressNum) {
        memory.add3AddressCode(addressNum, Operation.JP, new Address(memory.getCurrentCodeBlockAddress(), varType.Address), null, null);
        String methodName = "main";
        String className = symbolStack.pop();

        symbolTable.addMethod(className, methodName, memory.getCurrentCodeBlockAddress());

        symbolStack.push(className);
        symbolStack.push(methodName);
    }

    //    public void spid(Token next){
//        symbolStack.push(next.value);
//    }
    public void checkID() {
        symbolStack.pop();
        if (ss.peek().getVarType() == varType.Non) {
            //TODO : error
        }
    }

    public void pid(Token next) {
        if (symbolStack.size() > 1) {
            String methodName = symbolStack.pop();
            String className = symbolStack.pop();
            try {

                Symbol s = symbolTable.get(className, methodName, next.getValue());
                varType t = s.getVarTypeFromType();
                pushToAddressStack(new Address(s.getAddress(), t));

            } catch (Exception e) {
                pushToAddressStack(new Address(0, varType.Non));
            }
            symbolStack.push(className);
            symbolStack.push(methodName);
        } else {
            pushToAddressStack(new Address(0, varType.Non));
        }
        symbolStack.push(next.getValue());
    }

    public Address fpid() {
        Symbol s = symbolTable.get(symbolStack.pop(), symbolStack.pop());
        varType t = s.getVarTypeFromType();
        return new Address(s.getAddress(), t);
    }

    public void kpid(Token next) {
        pushToAddressStack(symbolTable.get(next.getValue()));
    }

    public void intpid(Token next) {
        pushToAddressStack(new Address(Integer.parseInt(next.getValue()), varType.Int, TypeAddress.Imidiate));
    }

    public void startCall() {
        //TODO: method ok
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();
        symbolTable.startCall(className, methodName);
        callStack.push(className);
        callStack.push(methodName);

        //symbolStack.push(methodName);
    }

    public void call() {
        //TODO: method ok
        String methodName = callStack.pop();
        String className = callStack.pop();
        try {
            symbolTable.getNextParam(className, methodName);
            ErrorHandler.printError("The few argument pass for method");
        } catch (IndexOutOfBoundsException e) {}
            varType t = varType.Int;
            switch (symbolTable.getMethodReturnType(className, methodName))
            {
                case Int:
                    t = varType.Int;
                    break;
                case Bool:
                    t = varType.Bool;
                    break;
            }
            Address temp = new Address(memory.getTemp(),t);
            pushToAddressStack(temp);
            memory.add3AddressCode(Operation.ASSIGN, new Address(temp.getNum(), varType.Address, TypeAddress.Imidiate), new Address(symbolTable.getMethodReturnAddress(className, methodName), varType.Address), null);
            memory.add3AddressCode(Operation.ASSIGN, new Address(memory.getCurrentCodeBlockAddress() + 2, varType.Address, TypeAddress.Imidiate), new Address(symbolTable.getMethodCallerAddress(className, methodName), varType.Address), null);
            memory.add3AddressCode(Operation.JP, new Address(symbolTable.getMethodAddress(className, methodName), varType.Address), null, null);

            //symbolStack.pop();


    }

    public void arg(Address param) {
        //TODO: method ok

        String methodName = callStack.pop();
//        String className = symbolStack.pop();
        try {
            Symbol s = symbolTable.getNextParam(callStack.peek(), methodName);
            varType t = varType.Int;
            switch (s.getType()) {
                case Bool:
                    t = varType.Bool;
                    break;
                case Int:
                    t = varType.Int;
                    break;
            }
            if (param.getVarType() != t) {
                ErrorHandler.printError("The argument type isn't match");
            }
            memory.add3AddressCode(Operation.ASSIGN, param, new Address(s.getAddress(), t), null);

//        symbolStack.push(className);

        } catch (IndexOutOfBoundsException e) {
            ErrorHandler.printError("Too many arguments pass for method");
        }
        callStack.push(methodName);

    }

    public void assign(Address s1, Address s2) {

//        try {
            if (s1.getVarType() != s2.getVarType()) {
                ErrorHandler.printError("The type of operands in assign is different ");
            }
//        }catch (NullPointerException d)
//        {
//            d.printStackTrace();
//        }
            memory.add3AddressCode(Operation.ASSIGN, s1, s2, null);

    }

    public Address add(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), varType.Int);

        if (s1.getVarType() != varType.Int || s2.getVarType() != varType.Int) {
            ErrorHandler.printError("In add two operands must be integer");
        }
        memory.add3AddressCode(Operation.ADD, s1, s2, temp);
        return temp;
    }

    public Address sub(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), varType.Int);
        if (s1.getVarType() != varType.Int || s2.getVarType() != varType.Int) {
            ErrorHandler.printError("In sub two operands must be integer");
        }
        memory.add3AddressCode(Operation.SUB, s1, s2, temp);
        return temp;
    }

    public Address mult(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), varType.Int);
        if (s1.getVarType() != varType.Int || s2.getVarType() != varType.Int) {
            ErrorHandler.printError("In mult two operands must be integer");
        }
        memory.add3AddressCode(Operation.MULT, s1, s2, temp);
//        memory.saveMemory();
        return temp;
    }

    public void label() {
        pushToAddressStack(new Address(memory.getCurrentCodeBlockAddress(), varType.Address));
    }

    public void save() {
        pushToAddressStack(new Address(memory.saveMemory(), varType.Address));
    }

    public void _while(Address address1, Address address2, Address address3) {
        memory.add3AddressCode(address1.getNum(), Operation.JPF, address2, new Address(memory.getCurrentCodeBlockAddress() + 1, varType.Address), null);
        memory.add3AddressCode(Operation.JP, address3, null, null);
    }

    public Address jpf_save(Address address, Address address2) {
        Address save = new Address(memory.saveMemory(), varType.Address);
        memory.add3AddressCode(address.getNum(), Operation.JPF, address2, new Address(memory.getCurrentCodeBlockAddress(), varType.Address), null);
        return save;
    }

    public void jpHere(Address address) {
        memory.add3AddressCode(address.getNum(), Operation.JP, new Address(memory.getCurrentCodeBlockAddress(), varType.Address), null, null);
    }

    public void print(Address address) {
        memory.add3AddressCode(Operation.PRINT, address, null, null);
    }

    public Address equal(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), varType.Bool);
        if (s1.getVarType() != s2.getVarType()) {
            ErrorHandler.printError("The type of operands in equal operator is different");
        }
        memory.add3AddressCode(Operation.EQ, s1, s2, temp);
        return temp;
    }

    public Address less_than(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), varType.Bool);
        if (s1.getVarType() != varType.Int || s2.getVarType() != varType.Int) {
            ErrorHandler.printError("The type of operands in less than operator is different");
        }
        memory.add3AddressCode(Operation.LT, s1, s2, temp);
        return temp;
    }

    public Address and(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), varType.Bool);
        if (s1.getVarType() != varType.Bool || s2.getVarType() != varType.Bool) {
            ErrorHandler.printError("In and operator the operands must be boolean");
        }
        memory.add3AddressCode(Operation.AND, s1, s2, temp);
        return temp;

    }

    public Address not(Address s1, Address s2) {
        Address temp = new Address(memory.getTemp(), varType.Bool);
        if (s1.getVarType() != varType.Bool) {
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
        //TODO : call ok

        String methodName = symbolStack.pop();
        SymbolType t = symbolTable.getMethodReturnType(symbolStack.peek(), methodName);
        varType temp = varType.Int;
        switch (t) {
            case Int:
                break;
            case Bool:
                temp = varType.Bool;
        }
        if (s.getVarType() != temp) {
            ErrorHandler.printError("The type of method and return address was not match");
        }
        memory.add3AddressCode(Operation.ASSIGN, s, new Address(symbolTable.getMethodReturnAddress(symbolStack.peek(), methodName), varType.Address, TypeAddress.Indirect), null);
        memory.add3AddressCode(Operation.JP, new Address(symbolTable.getMethodCallerAddress(symbolStack.peek(), methodName), varType.Address), null, null);

        //symbolStack.pop();

    }

    public void defParam() {
        //TODO : call Ok
        getAddressStackLastElement();
        String param = symbolStack.pop();
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();

        symbolTable.addMethodParameter(className, methodName, param);

        symbolStack.push(className);
        symbolStack.push(methodName);
    }

    public void lastTypeBool() {
        symbolTable.setLastType(SymbolType.Bool);
    }

    public void lastTypeInt() {
        symbolTable.setLastType(SymbolType.Int);
    }

    public void main() {

    }

}
