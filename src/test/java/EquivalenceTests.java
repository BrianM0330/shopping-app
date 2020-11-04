import edu.depaul.se433.shoppingapp.ShippingType;
import edu.depaul.se433.shoppingapp.TotalCostCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class EquivalenceTests {
    //TODO: Add Boundary Testing

    private TotalCostCalculator calculator;

    @BeforeEach
    void setup() {
        calculator = new TotalCostCalculator();
    }

    @ParameterizedTest
    @MethodSource("weakNormalValues")
    @DisplayName("Weak Normal Equivalence Tests")
    void testWeakNormal() {

    }

    private static Stream<Arguments> weakNormalValues () {
        return Stream.of(
            Arguments.of("IL", ShippingType.STANDARD, 25.00, 36.5),
            Arguments.of("AK", ShippingType.NEXT_DAY, 75.00, 100)
        );
    }

    private static Stream<Arguments> weakRobustValues () {
        return Stream.of(
            Arguments.of("IL", ShippingType.STANDARD, 25.00, 36.5),
            Arguments.of("AK", ShippingType.NEXT_DAY, 75.00, 100),

            //Invalid cases
            Arguments.of("", ShippingType.STANDARD, 25.00, -1),
            Arguments.of("IL", null, 25.00, -1),
            Arguments.of("IL", ShippingType.STANDARD, 0, -1)
        );
    }

    private static Stream<Arguments> strongNormalValues () {
        return Stream.of (
            Arguments.of("IL", ShippingType.STANDARD, 25.00, 36.5),
            Arguments.of("IL", ShippingType.STANDARD, 75.00, 79.5),

            Arguments.of("IL", ShippingType.NEXT_DAY, 25.00, 51.5),
            Arguments.of("IL", ShippingType.NEXT_DAY, 75.00, 104.5),

            Arguments.of("AK", ShippingType.STANDARD, 25.00, 35.00),
            Arguments.of("AK", ShippingType.STANDARD, 75.00, 75.00),

            Arguments.of("AK", ShippingType.NEXT_DAY, 25.00, 50.00),
            Arguments.of("AK", ShippingType.NEXT_DAY, 75.00, 100.00)
        );
    }

    private static Stream<Arguments> strongRobustValues () {
        return Stream.of (
            Arguments.of("IL", ShippingType.STANDARD, 25.00, 36.5),
            Arguments.of("IL", ShippingType.STANDARD, 75.00, 79.5),

            Arguments.of("IL", ShippingType.NEXT_DAY, 25.00, 51.5),
            Arguments.of("IL", ShippingType.NEXT_DAY, 75.00, 104.5),

            Arguments.of("AK", ShippingType.STANDARD, 25.00, 35.00),
            Arguments.of("AK", ShippingType.STANDARD, 75.00, 75.00),

            Arguments.of("AK", ShippingType.NEXT_DAY, 25.00, 50.00),
            Arguments.of("AK", ShippingType.NEXT_DAY, 75.00, 100.00),

            //Invalid Cases
            Arguments.of("", ShippingType.STANDARD, 25.00, -1),
            Arguments.of("", ShippingType.STANDARD, 75.00, -1),
            Arguments.of("", ShippingType.NEXT_DAY, 25.00, -1),
            Arguments.of("", ShippingType.NEXT_DAY, 75.00, -1),

            Arguments.of("IL", null, 25.00, -1),
            Arguments.of("IL", null, 75.00, -1),
            Arguments.of("AK", null, 25.00, -1),
            Arguments.of("AK", null, 75.00, -1),

            Arguments.of("IL", ShippingType.STANDARD, 0, -1),
            Arguments.of("IL", ShippingType.NEXT_DAY, 0, -1),
            Arguments.of("AK", ShippingType.STANDARD, 0, -1),
            Arguments.of("AK", ShippingType.NEXT_DAY, 0, -1)
        );
    }
}
