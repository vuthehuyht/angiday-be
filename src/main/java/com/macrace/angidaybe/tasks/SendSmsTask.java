package com.macrace.angidaybe.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Scope("prototype")
@Slf4j
public class SendSmsTask implements Runnable {
    private String endpoint;
    private String user;
    private String password;
    private String cpCode;
    private String brandName;
    private String receiverPhoneNumber;

    private String message;

    public SendSmsTask(String endpoint, String user, String password, String cpCode, String brandName, String receiverPhoneNumber, String message) {
        this.endpoint = endpoint;
        this.user = user;
        this.password = password;
        this.cpCode = cpCode;
        this.brandName = brandName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.message = message;
    }

    @Override
    public void run() {
        String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:impl=\"http://impl.bulkSms.ws/\">\r\n" +
                "       <soapenv:Header/>\r\n" +
                "       <soapenv:Body>\r\n" +
                "         <impl:wsCpMt>\r\n" +
                "           <User>" + user + "</User>\r\n" +
                "           <Password>" + password + "</Password>\n" +
                "           <CPCode>" + cpCode + "</CPCode>\r\n" +
                "           <RequestID>1</RequestID>\r\n" +
                "           <UserID>" + "84" + receiverPhoneNumber.substring(1, 10) + "</UserID>\r\n" +
                "           <ReceiverID>" + "84" + receiverPhoneNumber.substring(1, 10) + "</ReceiverID>\r\n" +
                "           <ServiceID>" + brandName + "</ServiceID>\r\n" +
                "           <CommandCode>bulksms</CommandCode>\r\n" +
                "           <Content>" + message + "</Content>\r\n" +
                "           <ContentType>0</ContentType>\r\n" +
                "         </impl:wsCpMt>\r\n" +
                "       </soapenv:Body>\r\n" +
                "     </soapenv:Envelope>";
        log.info(callSoapService(xml));
        log.info("Send sms to {}", receiverPhoneNumber);
    }

    private String callSoapService(String soapRequest) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(soapRequest);
            wr.flush();
            wr.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
