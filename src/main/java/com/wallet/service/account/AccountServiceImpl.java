package com.wallet.service.account;

import com.wallet.dao.user.UserDao;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.domain.dto.user.UserRegistrationDTO;
import com.wallet.domain.entities.User;
import com.wallet.domain.entities.Wallet;
import com.wallet.utility.exceptions.UserAllreadyExistsException;
import com.wallet.utility.exceptions.UserIsNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserDao userDao;
    private final WalletDao walletDao;

    @Override
    public void regUser(UserRegistrationDTO regDTO) throws UserAllreadyExistsException, UserIsNotExistsException {
        User newUser = User.builder()
                .login(regDTO.getLogin())
                .password(regDTO.getPassword())
                .email(regDTO.getEmail())
                .name(regDTO.getName())
                .surname(regDTO.getSurname())
                .build();

        userDao.savePlayer(newUser);
        Optional<User> optionalPlayer = userDao.findPlayer(regDTO.getLogin());
        if (optionalPlayer.isEmpty()) {
            throw new UserIsNotExistsException();
        }
        newUser = optionalPlayer.get();
        regWallet(newUser.getId());
    }

    private void regWallet(long playerId) {
        Wallet wallet = new Wallet(0, playerId, new BigDecimal(0));
        walletDao.saveWallet(wallet);
    }

    @Override
    public ArrayList<Wallet> getWalletsOfUser(String login) throws UserIsNotExistsException {
        System.out.println(login);
        User player = userDao.findPlayer(login).orElseThrow(UserIsNotExistsException::new);
        return walletDao.getWalletsOfUser(player);
    }

}
