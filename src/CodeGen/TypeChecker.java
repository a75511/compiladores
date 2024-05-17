package CodeGen;

public class TypeChecker {

    public static Type genericOperationCheck(Type type1, Type type2) {
        Type finalType;
        if (type1 == Type.REAL && type2 == Type.INT || type1 == Type.INT && type2 == Type.REAL || type1 == Type.REAL && type2 == Type.REAL)
            finalType = Type.REAL;

        else if (type1 == Type.INT && type2 == Type.INT)
            finalType = Type.INT;

        else
            finalType = Type.ERRO;
        return finalType;
    }

    public static Type addCheck(Type type1, Type type2) {
        Type finalType;
        if (type1 == Type.ERRO || type2 == Type.ERRO)
            finalType = Type.ERRO;

        else if (type1 == Type.STRING || type2 == Type.STRING)
            finalType = Type.STRING;

        else
            finalType = genericOperationCheck(type1, type2);
        return finalType;
    }

    public static Type equalCheck(Type type1, Type type2) {
        Type finalType;
        if (type1 == Type.ERRO || type2 == Type.ERRO)
            finalType = Type.ERRO;

        else if (type1 == type2)
            finalType = Type.BOOL;

        else if (type1 == Type.REAL && type2 == Type.INT || type1 == Type.INT && type2 == Type.REAL)
            finalType = Type.BOOL;

        else
            finalType = Type.ERRO;
        return finalType;
    }

    public static Type equalCheckGenerator(Type type1, Type type2) {
        Type finalType;
        if (type1 == Type.ERRO || type2 == Type.ERRO)
            finalType = Type.ERRO;

        else if (type1 == Type.INT && type2 == Type.INT)
            finalType = Type.INT;

        else if (type1 == Type.REAL || type2 == Type.REAL)
            finalType = Type.REAL;

        else if (type1 == Type.BOOL && type2 == Type.BOOL)
            finalType = Type.BOOL;

        else if (type1 == Type.STRING && type2 == Type.STRING)
            finalType = Type.STRING;

        else
            finalType = Type.ERRO;
        return finalType;
    }

    public static Type BinaryOperationCheck(Type type1, Type type2) {
        Type finalType;
        if (type1 == Type.ERRO || type2 == Type.ERRO)
            finalType = Type.ERRO;
        else if (type1 == Type.BOOL && type2 == Type.BOOL)
            finalType = Type.BOOL;
        else
            finalType = Type.ERRO;
        return finalType;
    }

    public static Type RelOpCheck(Type type1, Type type2) {
        Type finalType;
        if (type1 == Type.ERRO || type2 == Type.ERRO)
            finalType = Type.ERRO;
        else if (type1 == Type.INT && type2 == Type.INT || type1 == Type.REAL && type2 == Type.REAL || type1 == Type.INT && type2 == Type.REAL || type1 == Type.REAL && type2 == Type.INT)
            finalType = Type.BOOL;
        else
            finalType = Type.ERRO;
        return finalType;
    }

    public static Type RelOpCheckGenerator(Type type1, Type type2) {
        Type finalType;
        if (type1 == Type.ERRO || type2 == Type.ERRO)
            finalType = Type.ERRO;
        else if (type1 == Type.REAL || type2 == Type.REAL)
            finalType = Type.REAL;
        else if (type1 == Type.INT && type2 == Type.INT)
            finalType = Type.INT;
        else
            finalType = Type.ERRO;
        return finalType;
    }
    public static Type UnaryCheck(Type type) {
        Type finalType;
        if (type == Type.ERRO)
            finalType = Type.ERRO;
        else if (type == Type.INT || type == Type.REAL)
            finalType = type;
        else
            finalType = Type.ERRO;
        return finalType;
    }

    public static Type NotCheck(Type type) {
        Type finalType;
        if (type == Type.ERRO)
            finalType = Type.ERRO;
        else if (type == Type.BOOL)
            finalType = Type.BOOL;
        else
            finalType = Type.ERRO;
        return finalType;
    }

    public static Type modCheck(Type type1, Type type2) {
        Type finalType;
        if (type1 == Type.ERRO || type2 == Type.ERRO)
            finalType = Type.ERRO;
        else if (type1 == Type.INT && type2 == Type.INT)
            finalType = Type.INT;
        else
            finalType = Type.ERRO;
        return finalType;
    }
}
