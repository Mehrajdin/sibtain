package com.sibtain.security.vo;

public class ClientRegistrationSuccessResponse {
    private ClientDetailsVO registeredClientDetails;
    private String allertMessage;
    private String clientId;
    private String clientSecret;


    public ClientRegistrationSuccessResponse(ClientDetailsVO registeredClientDetails) {
        this.registeredClientDetails = registeredClientDetails;
        this.allertMessage ="Please store the client details, " +
                "you wont be able see the client secret latter";
    }

    public ClientRegistrationSuccessResponse() {
        this.allertMessage ="Please store the client details, " +
                "you wont be able see the client secret latter";
    }

    public ClientDetailsVO getRegisteredClientDetails() {
        return registeredClientDetails;
    }

    public String getAllertMessage() {
        return allertMessage;
    }

    public void setRegisteredClientDetails(ClientDetailsVO registeredClientDetails) {
        this.registeredClientDetails = registeredClientDetails;
    }

    public void setAllertMessage(String allertMessage) {
        this.allertMessage = allertMessage;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
