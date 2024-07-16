package ir.maedehhz.final_project_spring.mapper.wallet;

import ir.maedehhz.final_project_spring.dto.wallet.WalletSaveResponse;
import ir.maedehhz.final_project_spring.model.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WalletMapper {

    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    WalletSaveResponse modelToWalletSaveResponse(Wallet wallet);
}
