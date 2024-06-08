package org.example.repository.wallet;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.model.Wallet;
import org.hibernate.SessionFactory;

public class WalletRepositoryImpl extends BaseRepositoryImpl<Wallet, Long>
        implements WalletRepository {
    public WalletRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Wallet> getClassName() {
        return Wallet.class;
    }
}
