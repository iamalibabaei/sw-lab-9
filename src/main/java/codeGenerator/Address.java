package codeGenerator;

/**
 * Created by mohammad hosein on 6/28/2015.
 */
public class Address {
    public int num;
    public TypeAddress Type;
    public varType varType;

    public int getNum() {
        return num;
    }

    public TypeAddress getType() {
        return Type;
    }

    public void setType(TypeAddress type) {
        Type = type;
    }

    public codeGenerator.varType getVarType() {
        return varType;
    }

    public Address(int num,varType varType, TypeAddress Type) {
        this.num = num;
        this.Type = Type;
        this.varType = varType;
    }

    public Address(int num,varType varType) {
        this.num = num;
        this.Type = TypeAddress.DIRECT;
        this.varType = varType;
    }
    public String toString(){
        switch (Type){
            case DIRECT:
                return num+"";
            case INDIRECT:
                return "@"+num;
            case IMMEDIATE:
                return "#"+num;
        }
        return num+"";
    }
}
