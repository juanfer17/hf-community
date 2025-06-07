package com.hfcommunity.hf_community_hub.common.enums;

import java.util.Arrays;

public enum ModalityEnum {
    AIC(1L),
    HFA(2L),
    OHB(3L),
    HES(4L);

    private final Long id;

    ModalityEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static ModalityEnum fromName(String name) {
        return Arrays.stream(values())
                .filter(m -> m.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Modalidad no válida: " + name));
    }
}
