package com.gabriel.Services;

import com.gabriel.Models.Product;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StripeService {

    @Value("${stripe.apikey}")
    String stripeKey;

    public Session createSession(List<Product> produtos) throws StripeException {
        String successUrl = "${BASE_URL}payment/success";
        String failureUrl = "${BASE_URL}payment/failed";
        Stripe.apiKey = stripeKey;

        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();

        for (Product product : produtos)
            sessionItemList.add(createSessionLineItem(product));

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureUrl)
                .setSuccessUrl(successUrl)
                .addAllLineItem(sessionItemList)
                .build();

        return Session.create(params);
    }

    private SessionCreateParams.LineItem createSessionLineItem(Product product) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(product))
                .setQuantity((long) product.getQuantity())
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(Product product) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("brl")
                .setUnitAmountDecimal(product.getPrice())
                .setProductData(createProductData(product))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(Product product) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(product.getProduct_name())
                .setDescription(product.getDescription())
                .addImage(product.getImg_url())
                .build();
    }
}
