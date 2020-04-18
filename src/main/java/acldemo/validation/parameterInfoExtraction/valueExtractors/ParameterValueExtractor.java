package acldemo.validation.parameterInfoExtraction.valueExtractors;

import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.UnSupportedMappingException;

import java.util.List;

public interface ParameterValueExtractor {

    List<Long> extractIds() throws GetIdInvocationFailException, UnSupportedMappingException;
}
