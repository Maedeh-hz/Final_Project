package org.example.service.wallet;

import org.example.base.service.BaseServiceImpl;
import org.example.model.Wallet;
import org.example.repository.wallet.WalletRepository;
import org.hibernate.SessionFactory;

public class WalletServiceImpl extends BaseServiceImpl<Wallet, Long, WalletRepository>
        implements WalletService {
    public WalletServiceImpl(WalletRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}
