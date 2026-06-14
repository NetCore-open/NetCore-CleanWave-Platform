package com.netcore.cleanwave.platform.profiles.infrastructure.persistence.jpa.converters;

import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.EmailAddress;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts email addresses between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class EmailAddressPersistenceConverter implements AttributeConverter<EmailAddress, String> {

    @Override
    public String convertToDatabaseColumn(EmailAddress emailAddress) {
        return emailAddress == null ? null : emailAddress.address();
    }

    @Override
    public EmailAddress convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new EmailAddress(dbData);
    }
}

