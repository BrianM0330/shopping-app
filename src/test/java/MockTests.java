import edu.depaul.se433.shoppingapp.Purchase;
import edu.depaul.se433.shoppingapp.PurchaseAgent;
import edu.depaul.se433.shoppingapp.PurchaseDBO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockTests {

    @Test
    @DisplayName("Test Get Purchases")
    void testDBOGet() {
        List<Purchase> purchasesToReturn = new ArrayList<>();

        PurchaseDBO mockDBO = mock(PurchaseDBO.class);

        Purchase purchaseNoFreeShip = Purchase.make(
            "Joseph",
            LocalDate.of(2020, 11, 5),
            25.00,
            "IL",
            "STANDARD"
        );
        purchasesToReturn.add(purchaseNoFreeShip);

        Purchase purchaseFreeShip = Purchase.make(
            "Bernard",
            LocalDate.of(2020, 11, 5),
            75.00,
            "IL",
            "STANDARD"
        );
        purchasesToReturn.add(purchaseFreeShip);

        when(mockDBO.getPurchases()).thenReturn(purchasesToReturn);

        //I used purchaseAgent to invoke some of the DBO methods, I hope it's okay..
        PurchaseAgent agent = new PurchaseAgent(mockDBO);
        assertEquals(purchasesToReturn, agent.getPurchases());
        verify(mockDBO, times(1)).getPurchases();
    }

    @Test
    @DisplayName("Test Average Function")
    void testDBOAverage() {
        List<Purchase> purchasesToReturn = new ArrayList<>();
        PurchaseDBO mockDBO = mock(PurchaseDBO.class);

        Purchase purchaseNoFreeShip = Purchase.make(
                "Joseph",
                LocalDate.of(2020, 11, 5),
                25.00,
                "IL",
                "STANDARD"
        );
        purchasesToReturn.add(purchaseNoFreeShip);

        Purchase purchaseFreeShip = Purchase.make(
                "Bernard",
                LocalDate.of(2020, 11, 5),
                75.00,
                "IL",
                "STANDARD"
        );
        purchasesToReturn.add(purchaseFreeShip);

        Purchase purchaseExpensive = Purchase.make(
                "Chomsky",
                LocalDate.of(2020, 11, 5),
                100000.00,
                "IL",
                "NEXT_DAY"
        );
        purchasesToReturn.add(purchaseExpensive);

        when(mockDBO.getPurchases()).thenReturn(purchasesToReturn);

        PurchaseAgent agent = new PurchaseAgent(mockDBO);
        Double averageToAvoidHardCoding =
                (purchaseFreeShip.getCost() + purchaseNoFreeShip.getCost() + purchaseExpensive.getCost())/ 3;

        assertEquals(averageToAvoidHardCoding, agent.averagePurchase());
    }

    @Test
    @DisplayName("Test DBO Save Function")
    void testDBOSave() {
        List<Purchase> purchasesToReturn = new ArrayList<>();
        PurchaseDBO mockDBO = mock(PurchaseDBO.class);

        Purchase purchaseNoFreeShip = Purchase.make(
                "Joseph",
                LocalDate.of(2020, 11, 5),
                25.00,
                "IL",
                "STANDARD"
        );
        purchasesToReturn.add(purchaseNoFreeShip);

        when(mockDBO.getPurchases()).thenReturn(purchasesToReturn);

        PurchaseAgent agent = new PurchaseAgent(mockDBO);
        agent.save(purchaseNoFreeShip);

        purchaseNoFreeShip.toString();
        purchaseNoFreeShip.getIdNum();
        purchaseNoFreeShip.getState();

        verify(mockDBO, times(1)).savePurchase(purchaseNoFreeShip);
    }
}
