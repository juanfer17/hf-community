package com.hfcommunity.hf_community_hub.config;

import com.hfcommunity.hf_community_hub.common.enums.ModalityEnum;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ModalityIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (!parameter.getParameterType().equals(Long.class)) return false;
        if (!parameter.hasParameterAnnotation(PathVariable.class)) return false;

        PathVariable ann = parameter.getParameterAnnotation(PathVariable.class);
        String pathVariableName = ann.value();
        if (pathVariableName == null || pathVariableName.isEmpty()) {
            pathVariableName = parameter.getParameterName();
        }

        boolean supported = "modality".equals(pathVariableName);

        System.out.println("supportsParameter called for param: " + parameter.getParameterName() + ", annotation value: " + pathVariableName + ", supported: " + supported);

        return supported;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        String uri = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
        String[] parts = uri.split("/");
        String modalityName = parts[2];

        System.out.println("Resolviendo modalidad: " + modalityName);

        return ModalityEnum.fromName(modalityName).getId();
    }

}
