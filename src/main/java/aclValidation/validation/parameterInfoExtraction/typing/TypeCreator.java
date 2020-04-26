package aclValidation.validation.parameterInfoExtraction.typing;

import org.springframework.http.ResponseEntity;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

public class TypeCreator {

    public ParamType getParamType(Parameter p) {
        if (Collection.class.isAssignableFrom(p.getType())) {
            return new ParamType(p.getType(), (ParameterizedType) p.getParameterizedType());
        } else {
            return new ParamType(p.getType());
        }
    }

    public ParamType getParamType(Class<?> returnType, Type genericType) throws ClassNotFoundException {
        if (Collection.class.isAssignableFrom(returnType)) {
            return new ParamType(returnType, (ParameterizedType) genericType);
        } else if (returnType.equals(ResponseEntity.class)) {
            Type type = ((ParameterizedType) genericType).getActualTypeArguments()[0];
            if (type instanceof ParameterizedType) {
                ParameterizedType ptype = (ParameterizedType) type;
                Class<?> aClass = getClass(ptype.getRawType().getTypeName());
                return new ParamType(aClass, ptype);
            } else {
                return new ParamType(getClass(type.getTypeName()));
            }

        } else {
            return new ParamType(returnType);
        }

    }


    public Class<?> getClass(String name) throws ClassNotFoundException {
        Class<?> act = Class.forName(name);
        return act;
    }
}
