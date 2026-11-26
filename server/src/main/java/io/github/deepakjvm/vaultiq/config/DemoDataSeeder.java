package io.github.deepakjvm.vaultiq.config;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.github.deepakjvm.vaultiq.entity.Account;
import io.github.deepakjvm.vaultiq.entity.Transaction;
import io.github.deepakjvm.vaultiq.entity.TransactionType;
import io.github.deepakjvm.vaultiq.entity.User;
import io.github.deepakjvm.vaultiq.repository.AccountRepository;
import io.github.deepakjvm.vaultiq.repository.TransactionRepository;
import io.github.deepakjvm.vaultiq.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DemoDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    public static final String DEMO_USER_1_EMAIL = "alex.morgan@vaultiq.demo";
    public static final String DEMO_USER_2_EMAIL = "sarah.chen@vaultiq.demo";
    public static final String DEMO_USER_1_ACCOUNT = "100001";
    public static final String DEMO_USER_2_ACCOUNT = "100002";
    public static final String DEMO_PASSWORD = "Demo@1234";
    public static final String DEMO_USER_1_PIN = "1234";
    public static final String DEMO_USER_2_PIN = "5678";

    @Override
    @Transactional
    public void run(String... args) {
        try {
            seedDemoData();
        } catch (Exception e) {
            log.error("Failed to seed demo data", e);
        }
    }

    private void seedDemoData() {
        if (userRepository.findByEmail(DEMO_USER_1_EMAIL).isPresent()
                || accountRepository.findByAccountNumber(DEMO_USER_1_ACCOUNT) != null) {
            log.info("Demo user 1 already exists. Skipping demo data seeding.");
            return;
        }

        log.info("Seeding demo users and transactions for production demo...");

        // Create User 1: Alex Morgan
        User user1 = new User();
        user1.setName("Alex Morgan");
        user1.setEmail(DEMO_USER_1_EMAIL);
        user1.setPassword(passwordEncoder.encode(DEMO_PASSWORD));
        user1.setCountryCode("US");
        user1.setPhoneNumber("+12025550143");
        user1.setAddress("742 Evergreen Terrace, Springfield, OR");
        user1 = userRepository.save(user1);

        Account account1 = new Account();
        account1.setAccountNumber(DEMO_USER_1_ACCOUNT);
        account1.setAccountType("Savings");
        account1.setAccountStatus("ACTIVE");
        account1.setBalance(56500.0);
        account1.setBranch("Downtown Metro");
        account1.setIfscCode("VLTQ001");
        account1.setPin(passwordEncoder.encode(DEMO_USER_1_PIN));
        account1.setUser(user1);
        account1 = accountRepository.save(account1);

        user1.setAccount(account1);
        userRepository.save(user1);

        // Create User 2: Sarah Chen
        User user2 = new User();
        user2.setName("Sarah Chen");
        user2.setEmail(DEMO_USER_2_EMAIL);
        user2.setPassword(passwordEncoder.encode(DEMO_PASSWORD));
        user2.setCountryCode("US");
        user2.setPhoneNumber("+12025550188");
        user2.setAddress("456 Silicon Ave, San Francisco, CA");
        user2 = userRepository.save(user2);

        Account account2 = new Account();
        account2.setAccountNumber(DEMO_USER_2_ACCOUNT);
        account2.setAccountType("Savings");
        account2.setAccountStatus("ACTIVE");
        account2.setBalance(45000.0);
        account2.setBranch("Tech District");
        account2.setIfscCode("VLTQ002");
        account2.setPin(passwordEncoder.encode(DEMO_USER_2_PIN));
        account2.setUser(user2);
        account2 = accountRepository.save(account2);

        user2.setAccount(account2);
        userRepository.save(user2);

        // Dates for rich historical and today activity
        LocalDate today = LocalDate.now();
        int curDay = today.getDayOfMonth();

        // Previous month transactions
        LocalDate prevMonth = today.minusMonths(1);
        int prevMonthMax = prevMonth.lengthOfMonth();
        LocalDate prevMonthMid = prevMonth.withDayOfMonth(Math.min(15, prevMonthMax));
        LocalDate prevMonthLate = prevMonth.withDayOfMonth(Math.min(25, prevMonthMax));

        // User 1 last month
        saveTxn(account1, null, TransactionType.CASH_DEPOSIT, 50000.0, toDate(prevMonthMid, 10, 0));
        saveTxn(account1, null, TransactionType.CASH_WITHDRAWAL, 15000.0, toDate(prevMonthMid.plusDays(2), 14, 30));
        // Transfer from User 1 to User 2 last month
        saveTxn(account1, account2, TransactionType.CASH_TRANSFER, 5000.0, toDate(prevMonthLate, 16, 45));

        // User 2 last month
        saveTxn(account2, null, TransactionType.CASH_DEPOSIT, 30000.0, toDate(prevMonthMid, 11, 15));
        saveTxn(account2, null, TransactionType.CASH_WITHDRAWAL, 8000.0, toDate(prevMonthLate.minusDays(2), 13, 0));

        // Current month earlier transactions
        LocalDate dayEarly = today.withDayOfMonth(Math.max(1, curDay > 15 ? 5 : 1));
        LocalDate dayMid = today.withDayOfMonth(Math.max(1, curDay > 8 ? curDay / 2 : 1));
        LocalDate dayRecent = today.withDayOfMonth(Math.max(1, curDay > 2 ? curDay - 2 : 1));

        // User 1 consulting deposit earlier this month
        saveTxn(account1, null, TransactionType.CASH_DEPOSIT, 25000.0, toDate(dayEarly, 9, 30));
        // User 2 design project deposit earlier this month
        saveTxn(account2, null, TransactionType.CASH_DEPOSIT, 15000.0, toDate(dayMid, 15, 0));

        // User 1 withdrawal mid this month
        saveTxn(account1, null, TransactionType.CASH_WITHDRAWAL, 4000.0, toDate(dayMid, 12, 10));

        // Transfer from User 1 to User 2 mid this month
        saveTxn(account1, account2, TransactionType.CASH_TRANSFER, 3000.0, toDate(dayMid, 17, 30));

        // User 2 withdrawal recent
        saveTxn(account2, null, TransactionType.CASH_WITHDRAWAL, 2500.0, toDate(dayRecent, 14, 0));

        // Transfer from User 2 to User 1 recent
        saveTxn(account2, account1, TransactionType.CASH_TRANSFER, 1500.0, toDate(dayRecent, 11, 20));

        // User 1 withdrawal recent
        saveTxn(account1, null, TransactionType.CASH_WITHDRAWAL, 2000.0, toDate(dayRecent, 16, 0));

        // Today's Transactions for both users (ensures today's pie chart immediately has active data!)
        // User 1 today:
        saveTxn(account1, null, TransactionType.CASH_DEPOSIT, 10000.0, toDate(today, 10, 15));
        saveTxn(account1, null, TransactionType.CASH_WITHDRAWAL, 1000.0, toDate(today, 13, 45));

        // User 2 today:
        saveTxn(account2, null, TransactionType.CASH_DEPOSIT, 5000.0, toDate(today, 9, 30));
        saveTxn(account2, null, TransactionType.CASH_WITHDRAWAL, 1000.0, toDate(today, 12, 15));

        log.info("Demo data seeding completed successfully! Seeded users: {} (Acct: {}) and {} (Acct: {})",
                DEMO_USER_1_EMAIL, DEMO_USER_1_ACCOUNT, DEMO_USER_2_EMAIL, DEMO_USER_2_ACCOUNT);
    }

    private void saveTxn(Account source, Account target, TransactionType type, double amount, Date date) {
        Transaction txn = new Transaction();
        txn.setSourceAccount(source);
        txn.setTargetAccount(target);
        txn.setTransactionType(type);
        txn.setAmount(amount);
        txn.setTransactionDate(date);
        transactionRepository.save(txn);
    }

    private Date toDate(LocalDate date, int hour, int minute) {
        return Date.from(date.atTime(hour, minute).atZone(ZoneId.systemDefault()).toInstant());
    }
}
