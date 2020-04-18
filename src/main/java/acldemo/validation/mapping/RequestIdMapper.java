package acldemo.validation.mapping;

import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.parameterInfoExtraction.typing.ParamType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestIdMapper implements IdMapper{
    CommonIdMapper cmaper;

    public RequestIdMapper() {
        cmaper = new CommonIdMapper(this::parsePlainList);
    }

    public List<Long> mapToLongs(ParamType paramType,
                                 Object argValue,
                                 Optional<CustomIdMapper> userDefinedMapper) throws GetIdInvocationFailException, UnSupportedMappingException {
        try{
            return cmaper.mapToLongs(paramType,argValue,userDefinedMapper);
        }catch (Exception ex){
            if(ex instanceof GetIdInvocationFailException){
                throw ex;
            }else{
                throw new UnSupportedMappingException();
            }
        }

    }


    List<Long> parsePlainList(String value) {
        List<Long> ids = new ArrayList<>();
        for(String v:value.split(",")){
            ids.add(Long.parseLong(v));
        }
        return ids;
    }
}
