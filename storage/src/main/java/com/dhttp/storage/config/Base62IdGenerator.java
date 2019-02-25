package com.dhttp.storage.config;

import com.devskiller.friendly_id.FriendlyId;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * Creates a Base62 string from a generated uuid
 */
public class Base62IdGenerator implements IdentifierGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
		return FriendlyId.createFriendlyId();
	}
}
