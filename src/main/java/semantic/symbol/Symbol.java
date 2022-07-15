package semantic.symbol;

import codeGenerator.varType;

/**
 * Created by mohammad hosein on 6/28/2015.
 */
public class Symbol{
    private SymbolType type;
    private int address;

    public SymbolType getType() {
        return type;
    }

    public void setType(SymbolType type) {
        this.type = type;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public Symbol(SymbolType type , int address)
    {
        this.type = type;
        this.address = address;
    }

    public varType getVarTypeFromType() {
        switch (getType()) {
            case BOOL:
                return varType.BOOL;
            case INT:
            default:
                return varType.INT;
        }
    }
}
