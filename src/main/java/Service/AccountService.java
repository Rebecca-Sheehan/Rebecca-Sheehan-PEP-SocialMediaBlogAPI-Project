package Service;

import Model.Account;
import DAO.AccountDAO;
import java.sql.SQLException;

// How do I handle exceptions in the service layer? If I throw them to the controller layer, how do I handle them there?

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account createAccount(Account account) {
        try {
            if (accountDAO.checkUsername(account) == false)
                return null;
            String textU = account.getUsername();
            if (textU == null || textU.length() == 0)
                return null;
            String textP = account.getPassword();
            if (textP == null || textP.length() < 4)
                return null;
            return accountDAO.createAccount(account);
        } catch (SQLException e) {
            return null;
        }
    }

   public Account verifyAccount(Account account) {
        try {
            return accountDAO.verifyAccount(account);
        } catch (SQLException e) {
            return null;
        }
    }
}
