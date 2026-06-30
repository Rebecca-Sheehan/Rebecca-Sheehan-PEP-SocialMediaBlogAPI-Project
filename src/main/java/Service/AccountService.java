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

    public Account createAccount(Account account) throws SQLException {
        return accountDAO.createAccount(account);
    }

   public Account verifyAccount(Account account) throws SQLException {
        return accountDAO.verifyAccount(account);
    }
}
