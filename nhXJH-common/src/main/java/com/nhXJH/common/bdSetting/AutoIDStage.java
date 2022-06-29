package com.nhXJH.common.bdSetting;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IncrementGenerator;

import java.io.Serializable;

public class AutoIDStage extends IncrementGenerator {
    @Override
    public Serializable generate(SessionImplementor sessionImplementor, Object object) throws HibernateException {
        Serializable id = sessionImplementor.getEntityPersister(null, object)
                .getClassMetadata()
                .getIdentifier(object,sessionImplementor.getEntityMode());
        return id != null ? id : super.generate(sessionImplementor, object);
    }
}