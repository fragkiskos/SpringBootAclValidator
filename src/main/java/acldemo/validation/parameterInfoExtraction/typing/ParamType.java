package acldemo.validation.parameterInfoExtraction.typing;

import java.lang.reflect.ParameterizedType;

public class ParamType {

    Class<?> type;
    ParameterizedType pType;

    public ParamType(Class<?> type) {
        this.type = type;
    }

    public ParamType(Class<?> type, ParameterizedType pType) {
        this.type = type;
        this.pType = pType;
    }

    public boolean hasParametrizedType(){
        return this.pType != null;
    }

    public Class<?> getType() {
        return type;
    }

    public ParameterizedType getpType() {
        return pType;
    }
}
