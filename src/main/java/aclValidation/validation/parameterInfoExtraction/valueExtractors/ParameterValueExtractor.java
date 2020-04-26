package aclValidation.validation.parameterInfoExtraction.valueExtractors;

import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.UnSupportedMappingException;

import java.util.List;

public interface ParameterValueExtractor {

    List<Long> extractIds() throws GetIdInvocationFailException, UnSupportedMappingException;
}
