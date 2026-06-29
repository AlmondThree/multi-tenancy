package com.multitenancy.systems.util.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;

public class FormattedIdGenerator extends SequenceStyleGenerator {

    private static final String PREFIX = "REQ-";
    private static final String NUMBER_FORMAT = "%05d"; // Left-padded with zeros up to 5 digits

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // Fetch the next raw numeric value from the underlying database sequence
        Long nextValue = (Long) super.generate(session, object);

        // Format into a string (e.g., REQ-00001)
        return PREFIX + String.format(NUMBER_FORMAT, nextValue);
    }

}
