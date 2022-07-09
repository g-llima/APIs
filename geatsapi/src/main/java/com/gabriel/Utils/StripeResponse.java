package com.gabriel.Utils;

import lombok.Data;

@Data
public class StripeResponse {

    private String sessionUrl;

    public StripeResponse(String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }
}
