package h08;

import javax.swing.plaf.IconUIResource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * Represents a bank. A bank offers accounts to its customers and allows them to transfer money to other accounts.
 */
public class Bank {

    /**
     * The default capacity of a bank.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The default capacity of a transaction history.
     */
    private static final int DEFAULT_TRANSACTION_CAPACITY = 10;

    /**
     * The name of the bank.
     */
    private final String name;

    /**
     * The BIC of the bank.
     */
    private final int bic;

    /**
     * The banks to which this bank can transfer money.
     */
    private Bank[] transferableBanks;

    /**
     * The accounts of the bank.
     */
    private final Account[] accounts;

    /**
     * The capacity of the bank.
     */
    private final int capacity;

    /**
     * The size (number of accounts) of the bank.
     */
    private int size = 0;

    /**
     * The capacity of the transaction history.
     */
    private int transactionHistoryCapacity = DEFAULT_TRANSACTION_CAPACITY;


    /**
     * Constructs a new bank with the specified name, BIC and capacity.
     *
     * @param name     the name of the bank
     * @param bic      the BIC of the bank
     * @param capacity the capacity of the bank
     */
    public Bank(String name, int bic, int capacity) {
        this.name = name;
        this.bic = bic;
        this.accounts = new Account[capacity];
        this.capacity = capacity;
        this.transferableBanks = new Bank[0];
    }

    /**
     * Constructs a new bank with the specified name, BIC and default capacity of {@value DEFAULT_CAPACITY}.
     *
     * @param name the name of the bank
     * @param bic  the BIC of the bank
     */
    public Bank(String name, int bic) {
        this(name, bic, DEFAULT_CAPACITY);
    }

    /**
     * Returns the name of the bank.
     *
     * @return the name of the bank
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the BIC of the bank.
     *
     * @return the BIC of the bank
     */
    public int getBic() {
        return bic;
    }

    /**
     * Returns the available accounts of the bank.
     *
     * @return the available accounts of the bank
     */
    public Account[] getAccounts() {
        Account[] availableAccounts = new Account[size];
        System.arraycopy(accounts, 0, availableAccounts, 0, size);
        return availableAccounts;
    }

    /**
     * Returns the transferable banks of the bank.
     *
     * @return the transferable banks of the bank
     */
    public Bank[] getTransferableBanks() {
        return transferableBanks;
    }

    /**
     * Returns the capacity of the bank.
     *
     * @return the capacity of the bank
     */
    public int capacity() {
        return capacity;
    }

    /**
     * Returns the size (number of accounts) of the bank.
     *
     * @return the size (number of accounts) of the bank
     */
    public int size() {
        return size;
    }

    /**
     * Returns the capacity of the transaction history.
     *
     * @return the capacity of the transaction history
     */
    public int transactionCapacity() {
        return transactionHistoryCapacity;
    }

    /**
     * Sets the capacity of the transaction history.
     *
     * @param transactionHistoryCapacity the capacity of the transaction history
     */
    public void setTransactionHistoryCapacity(int transactionHistoryCapacity) {
        this.transactionHistoryCapacity = transactionHistoryCapacity;
        for (int i = 0; i < size; i++) {
            Account account = accounts[i];
            account.setHistory(new TransactionHistory(account.getHistory(), transactionHistoryCapacity));
        }
    }

    /**
     * Returns {@code true} if the specified IBAN is already used by an account of the bank.
     *
     * @param iban the IBAN to check
     * @return {@code true} if the specified IBAN is already used by an account of the bank
     */
    protected boolean isIbanAlreadyUsed(long iban) {
        for (int i = 0; i < size; i++) {
            if(this.accounts[i].getIban() == iban){
                return true;
            }
        }
        return false;
    }

    /**
     * Generates an IBAN.
     *
     * @param seed the seed to generate the IBAN
     * @return the generated IBAN
     */
    protected long generateIban(Customer customer, long seed) {
        long iban = Math.abs(customer.hashCode() * seed);
        while(true){
            if(!isIbanAlreadyUsed(iban)){
                return iban;
            }
            else {
                iban = Math.abs(customer.hashCode() * seed);
            }
        }
    }

    /**
     * Adds the specified account to the bank.
     *
     * @throws IllegalStateException if the bank is full
     */
    public void add(Customer customer) {
        for (int i = 0; i < this.accounts.length; i++) {
            if(this.accounts[i] == null){
                long iban = generateIban(customer, java.lang.System.nanoTime());
                Account account = new Account(customer, iban, 0, this, new TransactionHistory(this.transactionHistoryCapacity));
                this.accounts[i] = account;
                this.size++;
                return;
            }
        }
        throw new IllegalStateException("Bank is full");
    }

    /**
     * Adds the specified bank to the transferable banks of the bank.
     *
     * @param bank the bank to add
     * @throws IllegalArgumentException if the bank is already in the transferable banks
     */
    public void add(Bank bank) {
        for (Bank transferableBank : transferableBanks) {
            if (transferableBank.getBic() == bank.getBic()) {
                throw new IllegalArgumentException("Cannot add duplicates!");
            }
        }
        Bank[] newTransferableBanks = new Bank[transferableBanks.length + 1];
        System.arraycopy(transferableBanks, 0, newTransferableBanks, 0, transferableBanks.length);
        newTransferableBanks[transferableBanks.length] = bank;
        transferableBanks = newTransferableBanks;
    }

    /**
     * Returns the account with the specified IBAN.
     *
     * @param iban the IBAN of the account
     * @return the account with the specified IBAN
     * @throws NoSuchElementException if the account with the specified IBAN does not exist
     */
    public int getAccountIndex(long iban) {
        for (int i = 0; i < size; i++) {
            if (accounts[i].getIban() == iban)
                return i;
        }
        throw new NoSuchElementException(String.valueOf(iban));
    }

    /**
     * Removes the account with the specified IBAN from the bank.
     *
     * @param iban the IBAN of the account to remove
     * @return the removed account
     * @throws NoSuchElementException if the account with the specified IBAN does not exist
     */
    public Account remove(long iban) {

        assert iban >= 0;
        Account oldAccount = null;

        for (int i = 0; i < this.accounts.length; i++) {
            if (this.accounts[i].getIban() == iban){
                oldAccount = this.accounts[i];
                for (int j = i; j < this.accounts.length; j++) {
                    if((j+1)<this.accounts.length) {
                        this.accounts[j] = this.accounts[j + 1];
                    }
                    else{
                        this.accounts[j+1]=null;
                    }
                }
            }
        }

        if(oldAccount != null){
            size--;
            return oldAccount;
        }
        else {
            throw new NoSuchElementException(String.valueOf(iban));
        }
    }

    /**
     * Returns the index of the bank with the specified BIC.
     *
     * @param bic the BIC of the bank
     * @return the index of the bank with the specified BIC
     * @throws NoSuchElementException if the bank with the specified BIC does not exist
     */
    private int getBankIndex(int bic) {
        for (int i = 0; i < transferableBanks.length; i++) {
            if (transferableBanks[i].getBic() == bic)
                return i;
        }
        throw new NoSuchElementException(String.valueOf(bic));
    }

    /**
     * Returns the bank with the specified BIC.
     *
     * @param bic the BIC of the bank
     * @return the bank with the specified BIC
     * @throws NoSuchElementException if the bank with the specified BIC does not exist
     */
    private Bank getBank(int bic) {
        return transferableBanks[getBankIndex(bic)];
    }

    /**
     * Removes the bank with the specified BIC from the transferable banks of the bank.
     *
     * @param bic the BIC of the bank to remove
     * @return the removed bank
     */
    public Bank remove(int bic) {
        assert bic >= 0;
        int index = getBankIndex(bic);
        Bank removedBank = transferableBanks[index];
        Bank[] newTransferableBanks = new Bank[transferableBanks.length - 1];
        System.arraycopy(transferableBanks, 0, newTransferableBanks, 0, index);
        System.arraycopy(transferableBanks, index + 1, newTransferableBanks, index, transferableBanks.length - index - 1);
        transferableBanks = newTransferableBanks;
        return removedBank;
    }

    /**
     * Deposits the specified amount to the account with the specified IBAN.
     *
     * @param iban   the IBAN of the account
     * @param amount the amount to deposit
     * @throws IllegalArgumentException if the amount is zero or negative
     * @throws NoSuchElementException   if the account with the specified IBAN does not exist
     */
    public void deposit(long iban, double amount) {
        if(amount <= 0){
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < size; i++) {
            if(this.accounts[i].getIban() == iban){
                this.accounts[i].setBalance(this.accounts[i].getBalance() + amount);
                return;
            }
        }
        throw new NoSuchElementException(String.valueOf(iban));
    }

    /**
     * Withdraws the specified amount from the account with the specified IBAN.
     *
     * @param iban   the IBAN of the account
     * @param amount the amount to withdraw
     * @throws IllegalArgumentException if the amount is zero or negative or if the new balance would be negative
     * @throws NoSuchElementException   if the account with the specified IBAN does not exist
     */
    public void withdraw(long iban, double amount) {
        if(amount <= 0){
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < size; i++) {
            if(this.accounts[i].getIban() == iban){
                if((this.accounts[i].getBalance()-amount)>=0) {
                    this.accounts[i].setBalance(this.accounts[i].getBalance() - amount);
                    return;
                }
                else {
                    throw new IllegalArgumentException();
                }
            }
        }
        throw new NoSuchElementException(String.valueOf(iban));
    }

    /**
     * Generates a transaction number.
     *
     * @return the generated transaction number
     */
    protected long generateTransactionNumber() {
        return System.currentTimeMillis();
    }

    /**
     * Transfers the specified amount from the account with the specified IBAN to the account with the specified IBAN.
     *
     * @param senderIBAN   the IBAN of the sender account
     * @param receiverIBAN the IBAN of the receiver account
     * @param receiverBIC  the BIC of the receiver bank
     * @param amount       the amount to transfer
     * @param description  the description of the transaction
     * @return the status of the transaction
     */
    public Status transfer(long senderIBAN, long receiverIBAN, int receiverBIC, double amount, String description) throws TransactionException {
        //5.3
        Account receiver = null;
        Account sender = null;


        for (int i = 0; i < size; i++) {
            if(this.accounts[i].getIban()==senderIBAN){
                sender = this.accounts[i];
            }
        }
        if(receiverBIC != this.getBic()) {
            for (int i = 0; i < transferableBanks.length; i++) {
                if (transferableBanks[i].getBic() == receiverBIC) {
                    for (int j = 0; j < transferableBanks[i].size; j++) {
                        if (transferableBanks[i].getAccounts()[j].getIban() == receiverIBAN) {
                            receiver = transferableBanks[i].getAccounts()[j];
                        }
                    }
                }
            }
        }
        else {
            for (int i = 0; i < size; i++) {
                if(this.accounts[i].getIban()==receiverIBAN){
                    receiver = this.accounts[i];
                }
            }
        }

        if(sender == null || receiver==null){
            return Status.CANCELLED;
        }

        long transactionNumber = generateTransactionNumber();

        LocalDate currentTime = LocalDate.now();

        Transaction transaction = new Transaction(sender, receiver, amount, transactionNumber, description, currentTime, Status.OPEN);

        sender.getHistory().add(transaction);
        receiver.getHistory().add(transaction);


        try {
            this.withdraw(senderIBAN, amount);
            if(receiverBIC==this.getBic()) {
                this.deposit(receiverIBAN, amount);
            }
            else{
                for (int i = 0; i < transferableBanks.length; i++) {
                    if (transferableBanks[i].getBic() == receiverBIC) {
                        transferableBanks[i].deposit(receiverIBAN, amount);
                    }
                }
            }
            transaction = new Transaction(sender, receiver, amount, transactionNumber, description, currentTime, Status.CLOSED);
            sender.getHistory().update(transaction);
            receiver.getHistory().update(transaction);
        }
        catch(Exception e) {
            transaction = new Transaction(sender, receiver, amount, transactionNumber, description, currentTime, Status.CANCELLED);
            sender.getHistory().update(transaction);
            receiver.getHistory().update(transaction);
            return Status.CANCELLED;
        }


        return transaction.status();
    }

    /**
     * Checks for opened transactions and cancels them if they are older than 2 or 4 weeks. If the transaction is older
     * than 2 weeks, the user is notified by transferring the amount to the target account again.
     *
     * @return the open transactions
     */
    public Transaction[] checkOpenTransactions() throws TransactionException {
        //5.4
        int numberOfOpenTransactions = 0;
        int numberOfTransactionsToBeCanceled = 0;
        for (int i = 0; i < this.accounts.length; i++) {
            for (int j = 0; j < this.accounts[i].getHistory().getTransactions().length; j++) {
                if(this.accounts[i].getHistory().getTransactions()[j].status()==Status.OPEN){
                    numberOfOpenTransactions++;
                    if(accounts[i].getHistory().getTransactions()[j].date().compareTo(LocalDate.now())>28){
                        numberOfTransactionsToBeCanceled++;
                    }
                }
            }
        }

        Transaction[] canceledTransactions = new Transaction[numberOfTransactionsToBeCanceled];
        Transaction[] transactions = new Transaction[numberOfOpenTransactions];
        int index = 0;
        int index2 = 0;
        for (int i = 0; i < this.accounts.length; i++) {
            Transaction[] accountTransactions = this.accounts[i].getHistory().getTransactions();
            for (int j = 0; j < accountTransactions.length; j++) {
                if(accountTransactions[j].status()==Status.OPEN){
                    transactions[index]= accountTransactions[j];
                    index++;
                    if(accountTransactions[j].date().compareTo(LocalDate.now())>14){
                        Transaction transaction = new Transaction(accountTransactions[j].sourceAccount(), accountTransactions[j].targetAccount(),
                            accountTransactions[j].amount(), accountTransactions[j].transactionNumber(), accountTransactions[j].description(),
                            accountTransactions[j].date(), Status.CANCELLED);
                        this.accounts[i].getHistory().update(transaction);

                        this.transfer(accountTransactions[j].sourceAccount().getIban(),
                            accountTransactions[j].targetAccount().getIban(), this.accounts[i].getBank().bic,
                            accountTransactions[j].amount(), accountTransactions[j].description());
                    }
                    if(accountTransactions[j].date().compareTo(LocalDate.now())>28){
                        Transaction transaction = new Transaction(accountTransactions[j].sourceAccount(), accountTransactions[j].targetAccount(),
                            accountTransactions[j].amount(), accountTransactions[j].transactionNumber(), accountTransactions[j].description(),
                            accountTransactions[j].date(), Status.CANCELLED);
                        this.accounts[i].getHistory().update(transaction);
                        canceledTransactions[index2] = accountTransactions[j];
                        index2++;
                    }
                }
            }
        }

        if(numberOfTransactionsToBeCanceled>0){
            throw new TransactionException(canceledTransactions);
        }

        return transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return getBic() == bank.getBic()
            && capacity() == bank.capacity()
            && size() == bank.size()
            && Objects.equals(getName(), bank.getName())
            && Arrays.equals(getAccounts(), bank.getAccounts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBic(), capacity(), size(), Arrays.hashCode(getAccounts()));
    }

    @Override
    public String toString() {
        return "Bank{" +
            "name='" + name + '\'' +
            ", bic=" + bic +
            ", capacity=" + capacity +
            ", size=" + size +
            '}';
    }

}
