package currency_converter;

import org.junit.jupiter.api.Test;
import use_case.currency_converter.*;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyConverterInteractorTest {

    /**
     * Fake fetcher that does NOT call the real API.
     */
    static class FakeRateFetcher extends CurrencyRateFetcher {
        private final double rate;

        FakeRateFetcher(double rate) {
            super("TBD"); // disables API calls
            this.rate = rate;
        }

        @Override
        public double getRate(String from, String to) {
            return rate;
        }
    }

    /**
     * Fake presenter to capture output.
     */
    static class FakePresenter implements CurrencyConverterOutputBoundary {
        CurrencyConverterOutputData success;
        String error;

        @Override
        public void present(CurrencyConverterOutputData outputData) {
            this.success = outputData;
        }

        @Override
        public void presentError(String errorMessage) {
            this.error = errorMessage;
        }
    }

    @Test
    void testSuccessfulConversion() {
        FakePresenter presenter = new FakePresenter();
        FakeRateFetcher fetcher = new FakeRateFetcher(2.0); // rate for test
        CurrencyConverterInteractor interactor =
                new CurrencyConverterInteractor(presenter, fetcher);

        CurrencyConverterInputData input =
                new CurrencyConverterInputData(10.0, "USD", "CAD");

        interactor.convert(input);

        assertNull(presenter.error);
        assertNotNull(presenter.success);
        assertEquals(20.0, presenter.success.convertedAmount, 0.0001);
        assertEquals("Converted successfully!", presenter.success.message);
    }

    @Test
    void testInvalidRate() {
        FakePresenter presenter = new FakePresenter();
        FakeRateFetcher fetcher = new FakeRateFetcher(-1.0);
        CurrencyConverterInteractor interactor =
                new CurrencyConverterInteractor(presenter, fetcher);

        CurrencyConverterInputData input =
                new CurrencyConverterInputData(10.0, "AAA", "BBB");

        interactor.convert(input);

        assertNull(presenter.success);
        assertEquals("Invalid currency or conversion rate not available.", presenter.error);
    }

    @Test
    void testZeroRate() {
        FakePresenter presenter = new FakePresenter();
        FakeRateFetcher fetcher = new FakeRateFetcher(0.0);
        CurrencyConverterInteractor interactor =
                new CurrencyConverterInteractor(presenter, fetcher);

        CurrencyConverterInputData input =
                new CurrencyConverterInputData(10.0, "USD", "CAD");

        interactor.convert(input);

        assertNull(presenter.success);
        assertEquals("Invalid currency or conversion rate not available.", presenter.error);
    }
}
