package bank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

import static bank.App.emf;

@RestController
public class SpringWeb {

    @GetMapping("/transactions")
    public List<Operations> showTransactions() {
        emf = Persistence.createEntityManagerFactory("Bank");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT o FROM Operations o", Operations.class);
        List<Operations> list = (List<Operations>) query.getResultList();
        em.close();
        return list;
    }

    @GetMapping("/total-amount-of-money")
    public String getTotalBalanceOfMoney() {
        emf = Persistence.createEntityManagerFactory("Bank");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT o FROM BankAccount o", BankAccount.class);
        List<BankAccount> list = (List<BankAccount>) query.getResultList();
        double moneyInUAH = 0;
        double moneyInUSD = 0;
        double moneyInEURO = 0;
        for (BankAccount ba : list) {
            moneyInUAH = moneyInUAH + ba.getMoneyInUAH();
            moneyInUSD = moneyInUSD + ba.getMoneyInUSD();
            moneyInEURO = moneyInEURO + ba.getMoneyInEURO();
        }
        String result = "Money in UAH = " + moneyInUAH + " | Money in USD = "
                + moneyInUSD + " | Money in EURO = " + moneyInEURO;
        em.close();
        return result;
    }
}