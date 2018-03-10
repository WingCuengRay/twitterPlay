package lyc;

public interface AccountFactory {
    /**
     * Create a connection for an account with authentication
     *
     * @param auths the authentication message for this account
     * @return the connection for this account, null if failure.
     */
    public Connection createAccount(String []auths) ;
}
