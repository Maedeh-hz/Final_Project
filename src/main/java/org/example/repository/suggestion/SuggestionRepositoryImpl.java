package org.example.repository.suggestion;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.model.Suggestion;
import org.hibernate.SessionFactory;

public class SuggestionRepositoryImpl extends BaseRepositoryImpl<Suggestion, Long>
        implements SuggestionRepository {
    public SuggestionRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Suggestion> getClassName() {
        return Suggestion.class;
    }
}
