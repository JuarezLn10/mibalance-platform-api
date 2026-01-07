package com.jrzln.mibalanceapi.profiles.domain.model.valueobjects;

import com.jrzln.mibalanceapi.profiles.domain.model.exceptions.InvalidRegionException;
import lombok.Getter;

import java.util.Arrays;

/**
 * Enum representing the names of regions.
 */
@Getter
public enum RegionNames {
    AMAZONAS("Amazonas"),
    ANCASH("Ancash"),
    APURIMAC("Apurimac"),
    AREQUIPA("Arequipa"),
    AYACUCHO("Ayacucho"),
    CAJAMARCA("Cajamarca"),
    CALLAO("Callao"),
    CUSCO("Cusco"),
    HUANCAVELICA("Huancavelica"),
    HUANUCO("Huanuco"),
    ICA("Ica"),
    JUNIN("Junin"),
    LA_LIBERTAD("La Libertad"),
    LAMBAYEQUE("Lambayeque"),
    LIMA("Lima"),
    LORETO("Loreto"),
    MADRE_DE_DIOS("Madre de Dios"),
    MOQUEGUA("Moquegua"),
    PASCO("Pasco"),
    PIURA("Piura"),
    PUNO("Puno"),
    SAN_MARTIN("San Martin"),
    TACNA("Tacna"),
    TUMBES("Tumbes"),
    UCAYALI("Ucayali");

    private final String value;

    // Constructor for the enum
    RegionNames(String value) {
        this.value = value;
    }

    /**
     * Converts a string to a RegionNames enum value.
     *
     * @param value the string representation of the region name
     * @return the corresponding RegionNames enum value
     */
    public static RegionNames fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidRegionException("Region name cannot be null or empty");
        }

        return Arrays.stream(values())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() ->
                        new InvalidRegionException("Invalid region name: " + value));
    }
}
