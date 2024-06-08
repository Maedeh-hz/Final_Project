package org.example.service.suggestion;

import org.example.base.service.BaseServiceImpl;
import org.example.model.Suggestion;
import org.example.repository.suggestion.SuggestionRepository;
import org.hibernate.SessionFactory;

public class SuggestionServiceImpl extends BaseServiceImpl<Suggestion, Long, SuggestionRepository>
        implements SuggestionService {
    public SuggestionServiceImpl(SuggestionRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}
