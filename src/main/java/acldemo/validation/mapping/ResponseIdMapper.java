package acldemo.validation.mapping;

import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.parameterInfoExtraction.typing.ParamType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResponseIdMapper implements IdMapper {

    CommonIdMapper cmaper;

    public ResponseIdMapper() {
        cmaper = new CommonIdMapper(this::parsePlainList);
    }

    @Override
    public List<Long> mapToLongs(ParamType paramType,
                                 Object argValue,
                                 Optional<CustomIdMapper> userDefinedMapper) throws GetIdInvocationFailException, UnSupportedMappingException {
//        if(paramType.getType().equals(ResponseEntity.class)){
//            ResponseEntity entity = (ResponseEntity) argValue;
//            final Object body = entity.getBody();
//        }
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
        value.replace("[","");
        value.replace("]","");
        for(String v:value.split(",")){
            ids.add(Long.parseLong(v));
        }
        return ids;
    }

}
